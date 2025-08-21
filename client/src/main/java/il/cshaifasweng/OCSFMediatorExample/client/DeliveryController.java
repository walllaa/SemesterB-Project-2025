package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Account;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import il.cshaifasweng.OCSFMediatorExample.entities.getAllOrdersMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private Button deliver;
    @FXML private ListView<String> deliveryList;
    @FXML private ComboBox<Integer> listOrders; // موجود في الـ FXML
    @FXML private Label wait;
    @FXML private Button back;

    Account currentUser;
    static List<Order> Orders = new ArrayList<>();

    @FXML
    void ApplyDelivery(ActionEvent event) {
        // أول ضغطة: تحميل الطلبات غير الموصّلة
        if (deliver.getText().equals("Load Pending Orders")) {
            deliveryList.getItems().clear();

            for (Order o : Orders) {
                if (!o.isDelivered()) {
                    String line = String.format(
                            "#%d - Ordered On %02d/%02d/%04d  Prepare For %02d/%02d/%04d",
                            o.getOrderID(),
                            o.getOrderDay(), o.getOrderMonth(), o.getOrderYear(),
                            o.getPrepareDay(),  o.getPrepareMonth(),  o.getPrepareYear()
                    );
                    deliveryList.getItems().add(line);
                }
            }
            deliver.setText("Deliver Selected");
            return;
        }

        // الضغطة الثانية: تحديد العنصر المختار وتحديث حالته
        String selected = deliveryList.getSelectionModel().getSelectedItem();
        if (selected == null || selected.isEmpty()) return;

        // السطر يبدأ بـ "#<id> - ..."
        int hashIdx = selected.indexOf('#');
        int spaceAfterId = selected.indexOf(' ', hashIdx);
        if (hashIdx == -1 || spaceAfterId == -1 || spaceAfterId <= hashIdx + 1) return;

        int selectedId;
        try {
            selectedId = Integer.parseInt(selected.substring(hashIdx + 1, spaceAfterId));
        } catch (NumberFormatException e) {
            return;
        }

        for (Order o : Orders) {
            if (o.getOrderID() == selectedId) {
                o.setDelivered(true);
                try {
                    // إذا البروتوكول عندك يطلب UpdateMessage("order","edit")، عدّله
                    SimpleClient.getClient().sendToServer(o);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        // رجّع زر التحميل ونظّف اللستة
        deliveryList.getItems().clear();
        deliver.setText("Load Pending Orders");
    }

    @FXML
    void openCatalog(ActionEvent event) throws IOException {
        Account recAcc = currentUser;
        PassAccountEvent recievedAcc = new PassAccountEvent(recAcc);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Deliver Privilege: " + recievedAcc.getRecievedAccount().getPrivialge());
                        EventBus.getDefault().post(recievedAcc);
                    }
                }, 4000
        );

        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent roott = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(roott));
        stage.setTitle("Catalog");
        stage.show();

        Stage stagee = (Stage) back.getScene().getWindow();
        stagee.close();
    }

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);

        // طلب جميع الطلبات من السيرفر
        getAllOrdersMessage getOrdersMsg = new getAllOrdersMessage();
        SimpleClient.getClient().sendToServer(getOrdersMsg);

        assert deliver != null : "fx:id=\"deliver\" was not injected: check your FXML file 'delivery.fxml'.";
        assert deliveryList != null : "fx:id=\"deliveryList\" was not injected: check your FXML file 'delivery.fxml'.";
        assert listOrders != null : "fx:id=\"listOrders\" was not injected: check your FXML file 'delivery.fxml'.";
        assert wait != null : "fx:id=\"wait\" was not injected: check your FXML file 'delivery.fxml'.";

        deliver.setDisable(true);
        listOrders.setDisable(true);
        back.setDisable(true);
        wait.setVisible(true);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        deliver.setDisable(false);
                        listOrders.setDisable(false);
                        back.setDisable(false);
                        wait.setVisible(false);
                    }
                }, 4500
        );

        listOrders.setVisible(false);
    }

    @Subscribe
    public void passOrders(PassOrdersFromServer passOrders){
        System.out.println("arrived to subscriber of passOrders in delivery controller!");
        Orders = passOrders.getRecievedOrders();
        System.out.println("Orders Size : " + Orders.size());
    }

    @Subscribe
    public void PassAccountEvent(PassAccountEventDelivery passAcc){
        System.out.println("Arrived To Pass Account - deliveryManager!");
        Account recvAccount = passAcc.getRecievedAccount();
        System.out.println(recvAccount.getPassword());
        System.out.println(recvAccount.getAccountID());
        System.out.println(recvAccount.getEmail());
        System.out.println(recvAccount.getFullName());
        System.out.println(recvAccount.getAddress());
        System.out.println(recvAccount.getCreditCardNumber());
        System.out.println(recvAccount.getCreditMonthExpire());
        currentUser = recvAccount;
    }
}
