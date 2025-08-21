package il.cshaifasweng.OCSFMediatorExample.client; /**
 * Sample Skeleton for 'log_manager.fxml' Controller Class
 */

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

import il.cshaifasweng.OCSFMediatorExample.entities.Account;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import il.cshaifasweng.OCSFMediatorExample.entities.getAllOrdersMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LogManagerController {

    @FXML private Label fieldsError;
    @FXML private Label logError;
    @FXML private Label shopError;
    @FXML private Label shopError2;

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private CategoryAxis Day;
    @FXML private Text FromDateText;
    @FXML private ComboBox<Integer> FromDay;
    @FXML private ComboBox<Integer> FromMonth;
    @FXML private ComboBox<Integer> FromYear;
    @FXML private Button LoadLogButton;
    @FXML private BarChart<String, Integer> LogChart;
    @FXML private ComboBox<String> LogType;
    @FXML private NumberAxis Number;
    @FXML private Text UntilDateText;
    @FXML private ComboBox<Integer> UntilDay;
    @FXML private ComboBox<Integer> UntilMonth;
    @FXML private ComboBox<Integer> UntilYear;
    @FXML private ComboBox<String> chooseShop;
    @FXML private Button goBack;
    @FXML private ComboBox<String> CompareShops;
    @FXML private CheckBox compareButton;
    @FXML private Label wait;

    static List<Order> orders = new ArrayList<>();
    static List<Complaint> complaints = new ArrayList<>();

    int selectedShopID = -1;
    int selectCompareShopID = -1;

    @FXML
    void backToCatalog(ActionEvent event) throws IOException {
        Account recAcc = currentUser;
        PassAccountEvent recievedAcc = new PassAccountEvent(recAcc);
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override public void run() { EventBus.getDefault().post(recievedAcc); }
        }, 4000);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent roott = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(roott));
        stage.setTitle("Catalog");
        stage.show();
        ((Stage) goBack.getScene().getWindow()).close();
    }

    @FXML
    void loadLog(ActionEvent event) {
        logError.setVisible(false);
        shopError.setVisible(false);
        shopError2.setVisible(false);
        fieldsError.setVisible(false);

        if (chooseShop.getSelectionModel().getSelectedIndex() == -1) {
            shopError.setVisible(true);
            return;
        }
        if (LogType.getSelectionModel().getSelectedIndex() == -1) {
            logError.setVisible(true);
            return;
        }
        if (FromDay.getSelectionModel().getSelectedIndex() == -1
                || FromMonth.getSelectionModel().getSelectedIndex() == -1
                || FromYear.getSelectionModel().getSelectedIndex() == -1
                || UntilDay.getSelectionModel().getSelectedIndex() == -1
                || UntilMonth.getSelectionModel().getSelectedIndex() == -1
                || UntilYear.getSelectionModel().getSelectedIndex() == -1) {
            fieldsError.setVisible(true);
            return;
        }
        if (compareButton.isSelected() && CompareShops.getSelectionModel().getSelectedIndex() == -1) {
            shopError2.setVisible(true);
            return;
        }

        switch (String.valueOf(chooseShop.getValue())) {
            case "ID 0: - Chain": selectedShopID = 0; break;
            case "ID 1: Tiberias, Big Danilof": selectedShopID = 1; break;
            case "ID 2: Haifa, Merkaz Zeiv": selectedShopID = 2; break;
            case "ID 3: Tel Aviv, Ramat Aviv": selectedShopID = 3; break;
            case "ID 4: Eilat, Ice mall": selectedShopID = 4; break;
            case "ID 5: Be'er Sheva, Big Beer Sheva": selectedShopID = 5; break;
        }
        if (compareButton.isSelected()) {
            switch (String.valueOf(CompareShops.getValue())) {
                case "ID 0: - Chain": selectCompareShopID = 0; break;
                case "ID 1: Tiberias, Big Danilof": selectCompareShopID = 1; break;
                case "ID 2: Haifa, Merkaz Zeiv": selectCompareShopID = 2; break;
                case "ID 3: Tel Aviv, Ramat Aviv": selectCompareShopID = 3; break;
                case "ID 4: Eilat, Ice mall": selectCompareShopID = 4; break;
                case "ID 5: Be'er Sheva, Big Beer Sheva": selectCompareShopID = 5; break;
            }
        }

        String logType = LogType.getSelectionModel().getSelectedItem();
        List<Order> searchResultOrder = new ArrayList<>();
        List<Complaint> searchResultComplaint = new ArrayList<>();

        LogChart.getData().clear(); // نبدأ من نظيف

        // ملاحظة مهمة: استخدمنا .equals بدل == للمقارنة النصية
        if ("Income Log".equals(logType)) {
            searchResultOrder = searchOrderResult(selectedShopID);
            XYChart.Series<String, Integer> set = aggregateOrdersByDateSum(searchResultOrder);
            LogChart.getData().add(set);

        } else if ("Orders Log".equals(logType)) {
            searchResultOrder = searchOrderResult(selectedShopID);
            XYChart.Series<String, Integer> set = aggregateOrdersByDateCount(searchResultOrder);
            LogChart.getData().add(set);

        } else { // Complaint Log
            searchResultComplaint = searchComplaintResult(selectedShopID);
            XYChart.Series<String, Integer> set = aggregateComplaintsByDateCount(searchResultComplaint);
            LogChart.getData().add(set);
        }

        if (compareButton.isSelected()) {
            if ("Income Log".equals(logType)) {
                XYChart.Series<String, Integer> set2 = aggregateOrdersByDateSum(searchOrderResult(selectCompareShopID));
                set2.setName("Compare");
                LogChart.getData().add(set2);
            } else if ("Orders Log".equals(logType)) {
                XYChart.Series<String, Integer> set2 = aggregateOrdersByDateCount(searchOrderResult(selectCompareShopID));
                set2.setName("Compare");
                LogChart.getData().add(set2);
            } else {
                XYChart.Series<String, Integer> set2 = aggregateComplaintsByDateCount(searchComplaintResult(selectCompareShopID));
                set2.setName("Compare");
                LogChart.getData().add(set2);
            }
        }
    }

    // ===== Helpers for aggregation & date grouping =====

    private XYChart.Series<String, Integer> aggregateOrdersByDateSum(List<Order> list) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        if (list.isEmpty()) return series;

        list.sort(Comparator.comparingInt(this::getOrderYear)
                .thenComparingInt(this::getOrderMonth)
                .thenComparingInt(this::getOrderDay));

        int i = 0;
        while (i < list.size()) {
            int j = i;
            int sum = 0;
            while (j < list.size() && sameOrderDate(list.get(i), list.get(j))) {
                sum += getOrderTotalPrice(list.get(j)); // يستخدم finalCost أو totalPrice حسب المتاح
                j++;
            }
            series.getData().add(new XYChart.Data<>(formatOrderDate(list.get(i)), sum));
            i = j;
        }
        return series;
    }

    private XYChart.Series<String, Integer> aggregateOrdersByDateCount(List<Order> list) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        if (list.isEmpty()) return series;

        list.sort(Comparator.comparingInt(this::getOrderYear)
                .thenComparingInt(this::getOrderMonth)
                .thenComparingInt(this::getOrderDay));

        int i = 0;
        while (i < list.size()) {
            int j = i;
            int count = 0;
            while (j < list.size() && sameOrderDate(list.get(i), list.get(j))) {
                count++;
                j++;
            }
            series.getData().add(new XYChart.Data<>(formatOrderDate(list.get(i)), count));
            i = j;
        }
        return series;
    }

    private XYChart.Series<String, Integer> aggregateComplaintsByDateCount(List<Complaint> list) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        if (list.isEmpty()) return series;

        list.sort(Comparator.comparingInt(Complaint::getYear)
                .thenComparingInt(Complaint::getMonth)
                .thenComparingInt(Complaint::getDay));

        int i = 0;
        while (i < list.size()) {
            int j = i;
            int count = 0;
            while (j < list.size() && list.get(i).sameDate(list.get(j))) {
                count++;
                j++;
            }
            series.getData().add(new XYChart.Data<>(list.get(i).getDate(), count));
            i = j;
        }
        return series;
    }

    private void printList(List<Order> orders) {
        for (Order o : orders) {
            System.out.println(getOrderGreeting(o)); // يدعم greetingMsg أو greeting
        }
    }

    List<Complaint> searchComplaintResult(int shopID)
    {
        int fromD = FromDay.getValue(), untilD = UntilDay.getValue();
        int fromM = FromMonth.getValue(), untilM = UntilMonth.getValue();
        int fromY = FromYear.getValue(), untilY = UntilYear.getValue();

        List<Complaint> res = new ArrayList<>();
        for (Complaint c : complaints) {
            // ضمن الفترة؟
            boolean okYear = c.getYear() >= fromY && c.getYear() <= untilY;
            if (!okYear) continue;

            if (c.getYear() == fromY) {
                if (c.getMonth() < fromM) continue;
                if (c.getMonth() == fromM && c.getDay() < fromD) continue;
            }
            if (c.getYear() == untilY) {
                if (c.getMonth() > untilM) continue;
                if (c.getMonth() == untilM && c.getDay() > untilD) continue;
            }
            // لو فيه فلترة متجر لاحقًا، أضفها هنا (Complaint ما فيه shopID)
            res.add(c);
        }
        return res;
    }

    List<Order> searchOrderResult(int shopID)
    {
        int fromD = FromDay.getValue(), untilD = UntilDay.getValue();
        int fromM = FromMonth.getValue(), untilM = UntilMonth.getValue();
        int fromY = FromYear.getValue(), untilY = UntilYear.getValue();

        List<Order> res = new ArrayList<>();
        for (Order o : orders) {
            if (getOrderShopId(o) != shopID) continue;

            int y = getOrderYear(o), m = getOrderMonth(o), d = getOrderDay(o);

            boolean okYear = y >= fromY && y <= untilY;
            if (!okYear) continue;

            if (y == fromY) {
                if (m < fromM) continue;
                if (m == fromM && d < fromD) continue;
            }
            if (y == untilY) {
                if (m > untilM) continue;
                if (m == untilM && d > untilD) continue;
            }
            res.add(o);
        }
        System.out.println("---");
        for (Order o : res) {
            System.out.println("ID: " + getOrderId(o));
        }
        return res;
    }

    @FXML
    void clickedCompareButton(ActionEvent event)
    {
        CompareShops.setVisible(compareButton.isSelected());
    }

    Account currentUser;
    List<Complaint> allComplaints ;

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
        getAllOrdersMessage getOrdersMsg = new getAllOrdersMessage();
        SimpleClient.getClient().sendToServer(getOrdersMsg);

        CompareShops.setVisible(false);
        fieldsError.setVisible(false);
        logError.setVisible(false);
        shopError.setVisible(false);
        shopError2.setVisible(false);
        compareButton.setVisible(false);

        for (int i = 1; i < 31; i++) { FromDay.getItems().add(i); UntilDay.getItems().add(i); }
        for (int i = 1; i < 13; i++) { FromMonth.getItems().add(i); UntilMonth.getItems().add(i); }
        for (int i = 2022; i < 2030; i++) { FromYear.getItems().add(i); UntilYear.getItems().add(i); }

        LogType.getItems().add("Income Log");
        LogType.getItems().add("Orders Log");
        LogType.getItems().add("Complaint Log");
        Day.setAnimated(false);

        goBack.setDisable(true);
        LoadLogButton.setDisable(true);
        wait.setVisible(true);

        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override public void run() {
                goBack.setDisable(false);
                LoadLogButton.setDisable(false);
                wait.setVisible(false);
                switch (currentUser.getBelongShop()) {
                    case 0:
                        chooseShop.getItems().addAll(
                                "ID 0: - Chain",
                                "ID 1: Tiberias, Big Danilof",
                                "ID 2: Haifa, Merkaz Zeiv",
                                "ID 3: Tel Aviv, Ramat Aviv",
                                "ID 4: Eilat, Ice mall",
                                "ID 5: Be'er Sheva, Big Beer Sheva"
                        );
                        compareButton.setVisible(true);
                        break;
                    case 1: chooseShop.getItems().add("ID 1: Tiberias, Big Danilof"); break;
                    case 2: chooseShop.getItems().add("ID 2: Haifa, Merkaz Zeiv"); break;
                    case 3: chooseShop.getItems().add("ID 3: Tel Aviv, Ramat Aviv"); break;
                    case 4: chooseShop.getItems().add("ID 4: Eilat, Ice mall"); break;
                    case 5: chooseShop.getItems().add("ID 5: Be'er Sheva, Big Beer Sheva"); break;
                }
            }
        }, 4500);

        CompareShops.getItems().addAll(
                "ID 0: - Chain",
                "ID 1: Tiberias, Big Danilof",
                "ID 2: Haifa, Merkaz Zeiv",
                "ID 3: Tel Aviv, Ramat Aviv",
                "ID 4: Eilat, Ice mall",
                "ID 5: Be'er Sheva, Big Beer Sheva"
        );
    }

    @Subscribe
    public void complaintEvent(PassAllComplaintsEvent allComps){
        complaints = new ArrayList<>(allComps.getComplaintsToPass());
        allComplaints = complaints;
    }

    @Subscribe
    public void passOrders(PassOrdersFromServer passOrders){
        orders = new ArrayList<>(passOrders.getRecievedOrders());
    }

    @Subscribe
    public void PassAccountEvent(PassAccountEventLogManager passAcc){
        Account recvAccount = passAcc.getRecievedAccount();
        currentUser = recvAccount;
    }

    // ===== Compatibility helpers with the renamed Order fields =====
    // نحاول أولاً استدعاء Getter بالاسم الجديد/القديم، وإذا غير موجود نقرأ الحقل مباشرةً بالانعكاس.

    private int getOrderId(Order o) { // purchaseId أو orderID
        Integer v = invokeIntGetter(o, "getPurchaseId");
        if (v != null) return v;
        v = invokeIntGetter(o, "getOrderID");
        if (v != null) return v;
        Integer f = readIntField(o, "purchaseId");
        if (f != null) return f;
        f = readIntField(o, "orderID");
        return f != null ? f : -1;
    }

    private int getOrderShopId(Order o) { // shopCode أو shopID
        Integer v = invokeIntGetter(o, "getShopCode");
        if (v != null) return v;
        v = invokeIntGetter(o, "getShopID");
        if (v != null) return v;
        Integer f = readIntField(o, "shopCode");
        if (f != null) return f;
        f = readIntField(o, "shopID");
        return f != null ? f : -1;
    }

    private int getOrderYear(Order o) { // orderYear
        Integer v = invokeIntGetter(o, "getOrderYear");
        if (v != null) return v;
        Integer f = readIntField(o, "orderYear");
        return f != null ? f : -1;
    }

    private int getOrderMonth(Order o) {
        Integer v = invokeIntGetter(o, "getOrderMonth");
        if (v != null) return v;
        Integer f = readIntField(o, "orderMonth");
        return f != null ? f : -1;
    }

    private int getOrderDay(Order o) {
        Integer v = invokeIntGetter(o, "getOrderDay");
        if (v != null) return v;
        Integer f = readIntField(o, "orderDay");
        return f != null ? f : -1;
    }

    private String getOrderGreeting(Order o) { // greetingMsg أو greeting
        String s = invokeStringGetter(o, "getGreetingMsg");
        if (s != null) return s;
        s = invokeStringGetter(o, "getGreeting");
        if (s != null) return s;
        String f = readStringField(o, "greetingMsg");
        if (f != null) return f;
        f = readStringField(o, "greeting");
        return f != null ? f : "";
    }

    private int getOrderTotalPrice(Order o) { // finalCost أو totalPrice
        Integer v = invokeIntGetter(o, "getFinalCost");
        if (v != null) return v;
        v = invokeIntGetter(o, "getTotalPrice");
        if (v != null) return v;
        Integer f = readIntField(o, "finalCost");
        if (f != null) return f;
        f = readIntField(o, "totalPrice");
        return f != null ? f : 0;
    }

    private boolean sameOrderDate(Order a, Order b) {
        return getOrderDay(a) == getOrderDay(b)
                && getOrderMonth(a) == getOrderMonth(b)
                && getOrderYear(a) == getOrderYear(b);
    }

    private String formatOrderDate(Order o) {
        return getOrderDay(o) + "/" + getOrderMonth(o) + "/" + getOrderYear(o);
    }

    private Integer invokeIntGetter(Object target, String method) {
        try {
            Method m = target.getClass().getMethod(method);
            Object val = m.invoke(target);
            if (val instanceof Integer) return (Integer) val;
            if (val instanceof Number) return ((Number) val).intValue();
        } catch (Exception ignored) {}
        return null;
    }

    private String invokeStringGetter(Object target, String method) {
        try {
            Method m = target.getClass().getMethod(method);
            Object val = m.invoke(target);
            return (val != null) ? val.toString() : null;
        } catch (Exception ignored) {}
        return null;
    }

    private Integer readIntField(Object target, String field) {
        try {
            Field f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            Object val = f.get(target);
            if (val instanceof Integer) return (Integer) val;
            if (val instanceof Number) return ((Number) val).intValue();
        } catch (Exception ignored) {}
        return null;
    }

    private String readStringField(Object target, String field) {
        try {
            Field f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            Object val = f.get(target);
            return val != null ? val.toString() : null;
        } catch (Exception ignored) {}
        return null;
    }
}
