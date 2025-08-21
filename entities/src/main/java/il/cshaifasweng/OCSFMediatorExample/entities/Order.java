package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders_table")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int purchaseId;

    @Column(name = "Pick_Up")
    private boolean isPickup;

    @Column(name = "Shop_Code")
    private int shopCode;

    @Column(name = "Greeting_Text")
    private String greetingMsg;

    @Column(name = "Final_Price")
    private int finalCost;

    @Column(name = "Delivery_Address")
    private String shippingAddress;

    @Column(name = "Account_Code")
    private int accountCode;

    @Column(name = "Gift_Flag")
    private boolean giftOption;

    @Column(name = "Is_Delivered")
    private boolean isDelivered;

    @Column(name = "Card_Number")
    private long cardNumber;

    @Column(name = "Ready_Day")
    private int readyDay;

    @Column(name = "Ready_Month")
    private int readyMonth;

    @Column(name = "Ready_Year")
    private int readyYear;

    @Column(name = "Ready_Hour")
    private int readyHour;

    @Column(name = "Ready_Minute")
    private int readyMinute;

    @Column(name = "Order_Day")
    private int orderDay;

    @Column(name = "Order_Month")
    private int orderMonth;

    @Column(name = "Order_Year")
    private int orderYear;

    @Column(name = "Order_Hour")
    private int orderHour;

    @Column(name = "Order_Minute")
    private int orderMinute;

    @Column(name = "Card_Exp_Month")
    private int cardExpMonth;

    @Column(name = "Card_Exp_Year")
    private int cardExpYear;

    @Column(name = "Card_CVV")
    private int cardCvv;

    @Column(name = "Receiver_Name")
    private String receiverName;

    @Column(name = "Receiver_Phone")
    private long receiverPhone;

    @Column(name = "Receiver_Address")
    private String receiverAddress;

    @Column(name = "Products_List")
    private String itemsList;

    public Order() {}

    public Order(int purchaseId, boolean isPickup, int shopCode, String greetingMsg, int finalCost,
                 String shippingAddress, int accountCode, boolean giftOption, boolean isDelivered,
                 long cardNumber, int readyDay, int readyMonth, int readyYear,
                 int orderDay, int orderMonth, int orderYear,
                 int cardExpMonth, int cardExpYear, int cardCvv,
                 String receiverName, long receiverPhone, String receiverAddress,
                 String itemsList, int orderHour, int orderMinute,
                 int readyHour, int readyMinute) {

        this.purchaseId = purchaseId;
        this.isPickup = isPickup;
        this.shopCode = shopCode;
        this.greetingMsg = greetingMsg;
        this.finalCost = finalCost;
        this.shippingAddress = shippingAddress;
        this.accountCode = accountCode;
        this.giftOption = giftOption;
        this.isDelivered = isDelivered;
        this.cardNumber = cardNumber;
        this.readyDay = readyDay;
        this.readyMonth = readyMonth;
        this.readyYear = readyYear;
        this.orderDay = orderDay;
        this.orderMonth = orderMonth;
        this.orderYear = orderYear;
        this.cardExpMonth = cardExpMonth;
        this.cardExpYear = cardExpYear;
        this.cardCvv = cardCvv;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
        this.itemsList = itemsList;
        this.orderHour = orderHour;
        this.orderMinute = orderMinute;
        this.readyHour = readyHour;
        this.readyMinute = readyMinute;
    }

    @Override
    public String toString() {
        return "Order{" +
                "purchaseId=" + purchaseId +
                ", isPickup=" + isPickup +
                ", shopCode=" + shopCode +
                ", greetingMsg='" + greetingMsg + '\'' +
                ", finalCost=" + finalCost +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", accountCode=" + accountCode +
                ", giftOption=" + giftOption +
                ", isDelivered=" + isDelivered +
                ", cardNumber=" + cardNumber +
                ", readyDay=" + readyDay +
                ", readyMonth=" + readyMonth +
                ", readyYear=" + readyYear +
                ", orderDay=" + orderDay +
                ", orderMonth=" + orderMonth +
                ", orderYear=" + orderYear +
                ", orderHour=" + orderHour +
                ", orderMinute=" + orderMinute +
                ", cardExpMonth=" + cardExpMonth +
                ", cardExpYear=" + cardExpYear +
                ", cardCvv=" + cardCvv +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone=" + receiverPhone +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", itemsList='" + itemsList + '\'' +
                '}';
    }

    // الأساسية الموجودة
    public int getPurchaseId() { return purchaseId; }
    public void setPurchaseId(int purchaseId) { this.purchaseId = purchaseId; }

    public boolean getIsPickup() { return isPickup; }
    public void setIsPickup(boolean pickup) { isPickup = pickup; }

    public String getGreetingMsg() { return greetingMsg; }
    public void setGreetingMsg(String greetingMsg) { this.greetingMsg = greetingMsg; }

    public int getFinalCost() { return finalCost; }
    public void setFinalCost(int finalCost) { this.finalCost = finalCost; }

    public String getItemsList() { return itemsList; }
    public void setItemsList(String itemsList) { this.itemsList = itemsList; }

    // alias مستخدم بالواجهة القديمة
    public int getOrderID() { return purchaseId; }

    // === إضافات/أسماء قديمة مطلوبة لملفات الواجهة ===

    // حالة التوصيل
    public boolean isDelivered() { return isDelivered; }
    public void setDelivered(boolean delivered) { isDelivered = delivered; }

    // تاريخ الطلب
    public int getOrderDay() { return orderDay; }
    public int getOrderMonth() { return orderMonth; }
    public int getOrderYear() { return orderYear; }
    public int getOrderHour() { return orderHour; }
    public int getOrderMinute() { return orderMinute; }
    // alias للغلط الإملائي الموجود في الكود القديم
    public int getOrderMintue() { return orderMinute; }

    // موعد الجاهزية (Prepare)
    public int getPrepareDay() { return readyDay; }
    public int getPrepareMonth() { return readyMonth; }
    public int getPrepareYear() { return readyYear; }

    // لتجميع تاريخ جاهز كنص إذا احتجتوه
    public String getDate() {
        return String.format("%02d/%02d/%04d", orderDay, orderMonth, orderYear);
    }

    // معلومات الحساب/الفرع/السعر
    public int getAccountID() { return accountCode; }
    public int getShopID() { return shopCode; }
    public int getTotalPrice() { return finalCost; }

    // تفاصيل الدفع
    public long getCreditCardNumber() { return cardNumber; }
    public int getCreditCardExpMonth() { return cardExpMonth; }
    public int getCreditCardExpYear() { return cardExpYear; }
    public int getCreditCardCVV() { return cardCvv; }

    // طريقة الاستلام/هدية
    public boolean isGift() { return giftOption; }
    public boolean isPickUp() { return isPickup; } // alias للاسم القديم

    // بيانات المستقبل
    public String getRecepName() { return receiverName; }
    public String getRecepAddress() { return receiverAddress; }
    public long getRecepPhone() { return receiverPhone; }

    // المنتجات
    public String getProducts() { return itemsList; }

    // التحية
    public String getGreeting() { return greetingMsg; }
}
