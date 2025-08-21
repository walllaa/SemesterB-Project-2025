package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class CheckoutController {

    // Constants for validation patterns and business logic
    private static final Pattern CREDIT_CARD_REGEX = Pattern.compile("^\\d{16}$");
    private static final Pattern CVV_REGEX = Pattern.compile("^\\d{3}$");
    private static final Pattern PHONE_NUM_REGEX = Pattern.compile("^\\d{10}$");
    private static final int DELIVERY_FEE = 15;
    private static final double SUBSCRIPTION_DISCOUNT = 0.9;

    // FXML UI Components
    @FXML private CheckBox anotherMethodBox;
    @FXML private TextField creditNumberField;
    @FXML private Text creditNumberText;
    @FXML private TextField cvvField;
    @FXML private Text cvvText;
    @FXML private ComboBox<Integer> dayCheckout;
    @FXML private CheckBox deliveryBox;
    @FXML private Text deliveryDateCheckout;
    @FXML private ComboBox<Integer> expiryMonth;
    @FXML private Text expiryText;
    @FXML private ComboBox<Integer> expiryYear;
    @FXML private ComboBox<String> hourCheckout;
    @FXML private CheckBox greetingBoxCheckout;
    @FXML private TextField greetingTextCheckout;
    @FXML private ComboBox<Integer> monthCheckout;
    @FXML private Button placeOrderButton;
    @FXML private TextField recepAddressField;
    @FXML private Text recepAddressText;
    @FXML private TextField recepNameField;
    @FXML private Text recepNameText;
    @FXML private TextField recepPhoneField;
    @FXML private Text recepPhoneText;
    @FXML private ComboBox<Integer> yearCheckout;
    @FXML private Button back;
    @FXML private Text noDate;
    @FXML private CheckBox deliverToHome;
    @FXML private Label credit_regex;
    @FXML private Label cvv_regex;
    @FXML private Label phone_regex;
    @FXML private Text noShop;
    @FXML private Text noDateTwo;
    @FXML private ComboBox<String> chooseShopID;

    private Account currentUser;
    private List<Product> cart = new ArrayList<>();

    @FXML
    void initialize() throws MalformedURLException {
        EventBus.getDefault().register(this);
        populateComboBoxes();
        resetUI();

        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                placeOrderButton.setDisable(false);
                back.setDisable(false);
                if (currentUser.getBelongShop() == 0) {
                    chooseShopID.getItems().addAll("ID 0: - Chain", "ID 1: Tiberias, Big Danilof",
                            "ID 2: Haifa, Merkaz Zeiv", "ID 3: Tel Aviv, Ramat Aviv",
                            "ID 4: Eilat, Ice mall", "ID 5: Be'er Sheva, Big Beer Sheva");
                    chooseShopID.setVisible(true);
                } else {
                    chooseShopID.setVisible(false);
                }
            }
        }, 4500);
    }

    private void populateComboBoxes() {
        for (int i = 1; i <= 31; i++) dayCheckout.getItems().add(i);
        for (int i = 1; i <= 12; i++) {
            expiryMonth.getItems().add(i);
            monthCheckout.getItems().add(i);
        }
        for (int i = 2022; i < 2030; i++) {
            yearCheckout.getItems().add(i);
            expiryYear.getItems().add(i);
        }
        for (int hour = 7; hour < 22; hour++) {
            for (int min = 0; min < 60; min += 15) {
                hourCheckout.getItems().add(String.format("%02d:%02d", hour, min));
            }
        }
    }

    private void resetUI() {
        phone_regex.setVisible(false);
        credit_regex.setVisible(false);
        cvv_regex.setVisible(false);
        noDate.setVisible(false);
        noShop.setVisible(false);
        noDateTwo.setVisible(false);
        toggleDeliveryFields(false);
        togglePaymentFields(false);
        greetingTextCheckout.setVisible(false);
        deliverToHome.setSelected(true);
        placeOrderButton.setDisable(true);
        back.setDisable(true);
        chooseShopID.setVisible(false);
    }

    @FXML
    void PlaceOrder(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        Order newOrder = createOrder();
        sendToServer(new UpdateMessage("order", "add"), newOrder);

        Message confirmationMessage = createConfirmationMessage(newOrder);
        sendToServer(new UpdateMessage("message", "add"), confirmationMessage);

        placeOrderButton.setVisible(false);
    }

    private boolean validateInput() {
        boolean isValid = true;
        if (deliveryBox.isSelected() && !PHONE_NUM_REGEX.matcher(recepPhoneField.getText()).matches()) {
            phone_regex.setVisible(true);
            isValid = false;
        }
        if (anotherMethodBox.isSelected()) {
            if (!CREDIT_CARD_REGEX.matcher(creditNumberField.getText()).matches()) {
                credit_regex.setVisible(true);
                isValid = false;
            }
            if (!CVV_REGEX.matcher(cvvField.getText()).matches()) {
                cvv_regex.setVisible(true);
                isValid = false;
            }
            if (expiryMonth.getSelectionModel().isEmpty() || expiryYear.getSelectionModel().isEmpty()) {
                noDateTwo.setVisible(true);
                isValid = false;
            }
        }
        if (dayCheckout.getSelectionModel().isEmpty() || monthCheckout.getSelectionModel().isEmpty()
                || yearCheckout.getSelectionModel().isEmpty() || hourCheckout.getSelectionModel().isEmpty()) {
            noDate.setVisible(true);
            isValid = false;
        }
        if (chooseShopID.isVisible() && chooseShopID.getSelectionModel().isEmpty()) {
            noShop.setVisible(true);
            isValid = false;
        }
        return isValid;
    }

    private Order createOrder() {
        Calendar now = Calendar.getInstance();

        int shopID = (currentUser.getBelongShop() == 0)
                ? getShopIdFromString(chooseShopID.getValue())
                : currentUser.getBelongShop();

        boolean pickUp = deliverToHome.isSelected();
        boolean gift   = deliveryBox.isSelected();

        // بطاقة الائتمان (إما من الحقول أو من حساب المستخدم)
        long ccNumber;
        int ccMonth, ccYear, ccCVV;
        if (anotherMethodBox.isSelected()) {
            ccNumber = Long.parseLong(creditNumberField.getText());
            ccMonth  = expiryMonth.getValue();
            ccYear   = expiryYear.getValue();
            ccCVV    = Integer.parseInt(cvvField.getText());
        } else {
            ccNumber = currentUser.getCreditCardNumber();
            ccMonth  = currentUser.getCreditMonthExpire();
            ccYear   = currentUser.getCreditYearExpire();
            ccCVV    = currentUser.getCcv();
        }

        // ساعة التحضير المختارة من الكومبوبوكس
        String[] t = hourCheckout.getValue().split(":");
        int readyHour   = Integer.parseInt(t[0]);
        int readyMinute = Integer.parseInt(t[1]);

        int total = calculateTotalPrice();

        // بيانات المستلم حسب خيار التوصيل
        String receiverName    = gift ? recepNameField.getText()              : currentUser.getFullName();
        long   receiverPhone   = gift ? Long.parseLong(recepPhoneField.getText())
                : currentUser.getPhoneNumber(); // long
        String receiverAddress = gift ? recepAddressField.getText()           : currentUser.getAddress();

        // العنوان الذي يخزن في حقل الشحن في الكيان عندك
        String shippingAddress = receiverAddress;

        // قائمة المنتجات بسطر واحد
        StringBuilder items = new StringBuilder();
        for (Product p : cart) {
            items.append('%').append(p.getName()).append(" - ").append(p.getPrice()).append('%');
        }

        // ترتيب الوسيطات مطابق تماماً للكونستركتر في Order الذي أرسلته
        return new Order(
                0,                              // purchaseId
                pickUp,                         // isPickup
                shopID,                         // shopCode
                (greetingBoxCheckout.isSelected() ? greetingTextCheckout.getText() : "none"), // greetingMsg
                total,                          // finalCost
                shippingAddress,                // shippingAddress
                currentUser.getAccountID(),     // accountCode
                gift,                           // giftOption
                false,                          // isDelivered
                ccNumber,                       // cardNumber
                dayCheckout.getValue(),         // readyDay
                monthCheckout.getValue(),       // readyMonth
                yearCheckout.getValue(),        // readyYear
                now.get(Calendar.DAY_OF_MONTH), // orderDay
                now.get(Calendar.MONTH) + 1,    // orderMonth
                now.get(Calendar.YEAR),         // orderYear
                ccMonth,                        // cardExpMonth
                ccYear,                         // cardExpYear
                ccCVV,                          // cardCvv
                receiverName,                   // receiverName
                receiverPhone,                  // receiverPhone (long)
                receiverAddress,                // receiverAddress
                items.toString(),               // itemsList
                now.get(Calendar.HOUR_OF_DAY),  // orderHour
                now.get(Calendar.MINUTE),       // orderMinute
                readyHour,                      // readyHour
                readyMinute                     // readyMinute
        );
    }


    private int calculateTotalPrice() {
        int total = cart.stream().mapToInt(p -> Integer.parseInt(p.getPrice())).sum();
        if (deliveryBox.isSelected()) {
            total += DELIVERY_FEE;
        }
        if (currentUser.isSubscription() && total > 50) {
            total = (int) Math.round(total * SUBSCRIPTION_DISCOUNT);
        }
        return total;
    }


    private Message createConfirmationMessage(Order order) {
        Calendar now = Calendar.getInstance();
        String messageText = String.format("%d/%d/%d - %02d:%02d:\nYour Order #%d Has Been Placed!",
                now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), order.getOrderID());

        Message confirm = new Message();
        confirm.setCustomerID(currentUser.getAccountID());
        confirm.setMsgText(messageText);
        return confirm;
    }

    private int getShopIdFromString(String shopString) {
        if (shopString == null || shopString.isEmpty()) {
            return -1; // Or some other default/error value
        }
        return Integer.parseInt(shopString.split(":")[0].replace("ID ", ""));
    }

    @FXML
    void DeliverToMyHome(ActionEvent event) {
        if (deliverToHome.isSelected()) {
            deliveryBox.setSelected(false);
            toggleDeliveryFields(false);
        }
    }

    @FXML
    void deliverySomeone(ActionEvent event) {
        if (deliveryBox.isSelected()) {
            deliverToHome.setSelected(false);
            toggleDeliveryFields(true);
        } else {
            deliverToHome.setSelected(true);
            toggleDeliveryFields(false);
        }
    }

    @FXML
    void useAnotherMethod(ActionEvent event) {
        togglePaymentFields(anotherMethodBox.isSelected());
    }

    @FXML
    void addGreeting(ActionEvent event) {
        greetingTextCheckout.setVisible(greetingBoxCheckout.isSelected());
    }

    private void toggleDeliveryFields(boolean show) {
        recepNameText.setVisible(show);
        recepNameField.setVisible(show);
        recepPhoneText.setVisible(show);
        recepPhoneField.setVisible(show);
        recepAddressText.setVisible(show);
        recepAddressField.setVisible(show);
    }

    private void togglePaymentFields(boolean show) {
        creditNumberField.setVisible(show);
        creditNumberText.setVisible(show);
        expiryText.setVisible(show);
        expiryYear.setVisible(show);
        expiryMonth.setVisible(show);
        cvvText.setVisible(show);
        cvvField.setVisible(show);
    }

    private void sendToServer(UpdateMessage updateMessage, Object entity) {
        if (entity instanceof Order) {
            updateMessage.setOrder((Order) entity);
        } else if (entity instanceof Message) {
            updateMessage.setMessage((Message) entity);
        }
        try {
            SimpleClient.getClient().sendToServer(updateMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openCatalog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent roott = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(roott));
        stage.setTitle("Catalog");
        stage.show();
        Stage stagee = (Stage) back.getScene().getWindow();
        stagee.close();

        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                EventBus.getDefault().post(new PassAccountEvent(currentUser));
            }
        }, 4000);
    }

    @Subscribe
    public void PassAccountEvent(PassAccountEventCheckout passAcc) {
        currentUser = passAcc.getRecievedAccount();
        cart = passAcc.getProductsToCheckout();
    }}