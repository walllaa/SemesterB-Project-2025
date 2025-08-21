package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders_table")
public class Order implements Serializable {

    @Id
    private int orderID;
    @Column(name = "Pick_Up")
    private boolean pickUp;
    @Column(name = "Shop_ID")
    private int shopID;                     // Privilage 0 = GUEST
    @Column(name = "Greeting")                 // Privilage 1 = Customer
    private String greeting;                   // Privilage 2 = Worker
    @Column(name = "Total_Price")              // Privilage 3 = Manager
    private int totalPrice;                // Privilage 4 = Chain Manager
    @Column(name = "Delivered_Address")
    private String deliveredAddress;
    @Column(name = "Account_ID")
    private int accountID;
    @Column(name = "Gift")
    private boolean gift;
    @Column(name = "Delivered")
    private boolean delivered;
    @Column(name = "CreditNumber")
    private long creditCardNumber;
    @Column(name = "Prepare_Day")
    private int prepareDay;
    @Column(name = "Prepare_Month")
    private int prepareMonth;
    @Column(name = "Prepare_Hour")
    private int prepareHour;
    @Column(name = "Prepare_Minute")
    private int prepareMin;
    @Column(name = "Prepare_Year")
    private int prepareYear;
    @Column(name = "Order_Hour")
    private int OrderHour;
    @Column(name = "Order_Mintue")
    private int OrderMintue;
    @Column(name = "Order_Day")
    private int orderDay;
    @Column(name = "Order_Month")
    private int orderMonth;
    @Column(name = "Order_Year")
    private int orderYear;
    @Column(name = "creditExpMonth")
    private int creditCardExpMonth;
    @Column(name = "creditExpYear")
    private int creditCardExpYear;
    @Column(name = "creditCVV")
    private int creditCardCVV;
    @Column(name = "recepName")
    private String RecepName;
    @Column(name = "recepPhone")
    private long RecepPhone;
    @Column(name = "recepAddress")
    private String RecepAddress;
    @Column(name = "Products")
    private String Products;
    /*private List<Product> products;*/

    public Order(){}

    public Order(int orderID, boolean pickUp, int shopID, String greeting, int totalPrice, String deliveredAddress, int accountID, boolean gift, boolean delivered, int prepareDay, int prepareMonth, int prepareYear, int orderDay, int orderMonth, int orderYear, long creditCardNumber, int creditCardExpMonth, int creditCardExpYear, int creditCardCVV, String recepName, long recepPhone, String recepAddress,String Products,int orderHour,int orderMintue,int prepareHour,int prepareMin) {
        this.orderID = orderID;
        this.pickUp = pickUp;
        this.shopID = shopID;
        this.greeting = greeting;
        this.totalPrice = totalPrice;
        this.deliveredAddress = deliveredAddress;
        this.accountID = accountID;
        this.gift = gift;
        this.delivered = delivered;
        this.creditCardNumber = creditCardNumber;
        this.prepareDay = prepareDay;
        this.prepareMonth = prepareMonth;
        this.prepareYear = prepareYear;
        this.orderDay = orderDay;
        this.orderMonth = orderMonth;
        this.orderYear = orderYear;
        this.creditCardExpMonth = creditCardExpMonth;
        this.creditCardExpYear = creditCardExpYear;
        this.creditCardCVV = creditCardCVV;
        this.creditCardNumber = creditCardNumber;
        this.RecepName = recepName;
        this.RecepPhone = recepPhone;
        this.RecepAddress = recepAddress;
        this.Products = Products;
        this.OrderHour = orderHour;
        this.OrderMintue = orderMintue;
        this.prepareMin = prepareMin;
        this.prepareHour = prepareHour;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", pickUp=" + pickUp +
                ", shopID=" + shopID +
                ", greeting='" + greeting + '\'' +
                ", totalPrice=" + totalPrice +
                ", deliveredAddress='" + deliveredAddress + '\'' +
                ", accountID=" + accountID +
                ", gift=" + gift +
                ", delivered=" + delivered +
                ", creditCardNumber=" + creditCardNumber +
                ", prepareDay=" + prepareDay +
                ", prepareMonth=" + prepareMonth +
                ", prepareYear=" + prepareYear +
                ", orderDay=" + orderDay +
                ", orderMonth=" + orderMonth +
                ", orderYear=" + orderYear +
                ", creditCardExpMonth=" + creditCardExpMonth +
                ", creditCardExpYear=" + creditCardExpYear +
                ", creditCardCVV=" + creditCardCVV +
                ", RecepName='" + RecepName + '\'' +
                ", RecepPhone=" + RecepPhone +
                ", RecepAddress='" + RecepAddress + '\'' +
                '}';
    }

    public String getDate()
    {
        String result = "";
        result = this.orderDay + "/" + this.orderMonth + "/" + this.orderYear;
        return result;
    }
    public boolean sameDate(Order other)
    {
        if(this.orderDay == other.orderDay && this.orderMonth == other.orderMonth && this.orderYear == other.orderYear)
            return true;
        return false;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setPickUp(boolean pickUp) {
        this.pickUp = pickUp;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDeliveredAddress(String deliveredAddress) {
        this.deliveredAddress = deliveredAddress;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setPrepareDay(int prepareDay) {
        this.prepareDay = prepareDay;
    }

    public void setPrepareMonth(int prepareMonth) {
        this.prepareMonth = prepareMonth;
    }

    public void setPrepareYear(int prepareYear) {
        this.prepareYear = prepareYear;
    }

    public void setOrderDay(int orderDay) {
        this.orderDay = orderDay;
    }

    public void setOrderMonth(int orderMonth) {
        this.orderMonth = orderMonth;
    }

    public void setOrderYear(int orderYear) {
        this.orderYear = orderYear;
    }


    public void setCreditCardExpMonth(int creditCardExpMonth) {
        this.creditCardExpMonth = creditCardExpMonth;
    }

    public void setCreditCardExpYear(int creditCardExpYear) {
        this.creditCardExpYear = creditCardExpYear;
    }

    public void setCreditCardCVV(int creditCardCVV) {
        this.creditCardCVV = creditCardCVV;
    }

    public void setRecepName(String recepName) {
        RecepName = recepName;
    }

    public void setRecepPhone(int recepPhone) {
        RecepPhone = recepPhone;
    }

    public void setRecepAddress(String recepAddress) {
        RecepAddress = recepAddress;
    }

    public int getOrderID() {
        return orderID;
    }

    public boolean isPickUp() {
        return pickUp;
    }

    public int getShopID() {
        return shopID;
    }

    public String getGreeting() {
        return greeting;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getDeliveredAddress() {
        return deliveredAddress;
    }

    public int getAccountID() {
        return accountID;
    }

    public boolean isGift() {
        return gift;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public int getPrepareDay() {
        return prepareDay;
    }

    public int getPrepareMonth() {
        return prepareMonth;
    }

    public int getPrepareYear() {
        return prepareYear;
    }

    public int getOrderDay() {
        return orderDay;
    }

    public int getOrderMonth() {
        return orderMonth;
    }

    public int getOrderYear() {
        return orderYear;
    }

    public int getCreditCardExpMonth() {
        return creditCardExpMonth;
    }

    public int getCreditCardExpYear() {
        return creditCardExpYear;
    }

    public int getCreditCardCVV() {
        return creditCardCVV;
    }

    public String getRecepName() {
        return RecepName;
    }

    public long getRecepPhone() {
        return RecepPhone;
    }

    public String getRecepAddress() {
        return RecepAddress;
    }

    public String getProducts() {
        return Products;
    }

    public void setProducts(String products) {
        Products = products;
    }

    public void setOrderHour(int orderHour) {
        OrderHour = orderHour;
    }

    public void setOrderMintue(int orderMintue) {
        OrderMintue = orderMintue;
    }

    public void setRecepPhone(long recepPhone) {
        RecepPhone = recepPhone;
    }

    public int getOrderHour() {
        return OrderHour;
    }

    public int getOrderMintue() {
        return OrderMintue;
    }
}
