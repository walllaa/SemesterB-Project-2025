package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "complaints_table")
@Access(AccessType.FIELD)
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "complaintID")
    private int complaintID;

    @Column(name = "Customer_Id")
    private int CustomerID;

    @Column(name = "Order_Id")
    private int OrderID;

    @Column(name = "Accepted")
    private boolean Accepted; // Update

    @Column(name = "in24Hours")
    private boolean in24Hours;   // Update

    @Column(name = "complaintText", length = 1000)
    private String complaintText;

    @Column(name = "shop_Id")
    private int shopID;

    @Column(name = "AnswerWorker_Id")
    private int answerworkerID;

    @Column(name = "returnedMoney")
    private boolean returnedMoney;

    @Column(name = "returnedmoneyvalue")
    private int returnedmoneyvalue;

    @Column(name = "RecDay")
    private int day;

    @Column(name = "RecMonth")
    private int month;

    @Column(name = "RecYear")
    private int year;

    @Column(name = "Reply", length = 1000)
    private String ReplyText;   // This is the reply text - Update

    // ————————————————————
    // Constructors
    // ————————————————————

    public Complaint() { }

    public Complaint(int complaintID, int customerID, int orderID, boolean accepted, boolean in24Hours,
                     String complaintText, int shopID, int answerworkerID, boolean returnedMoney,
                     int returnedmoneyvalue, int day, int month, int year, String replyText) {
        this.complaintID = complaintID;
        this.CustomerID = customerID;
        this.OrderID = orderID;
        this.Accepted = accepted;
        this.in24Hours = in24Hours;
        this.complaintText = complaintText;
        this.shopID = shopID;
        this.answerworkerID = answerworkerID;
        this.returnedMoney = returnedMoney;
        this.returnedmoneyvalue = returnedmoneyvalue;
        this.day = day;
        this.month = month;
        this.year = year;
        this.ReplyText = replyText;
    }

    // ————————————————————
    // Setters
    // ————————————————————

    public void setComplaintID(int complaintID) { this.complaintID = complaintID; }

    public void setCustomerID(int customerID) { CustomerID = customerID; }

    public void setOrderID(int orderID) { OrderID = orderID; }

    public void setAccepted(boolean accepted) { Accepted = accepted; }

    public void setIn24Hours(boolean in24Hours) { this.in24Hours = in24Hours; }

    public void setComplaintText(String complaintText) { this.complaintText = complaintText != null ? complaintText.trim() : null; }

    public void setShopID(int shopID) { this.shopID = shopID; }

    public void setAnswerworkerID(int answerworkerID) { this.answerworkerID = answerworkerID; }

    public void setReturnedMoney(boolean returnedMoney) { this.returnedMoney = returnedMoney; }

    public void setReturnedmoneyvalue(int returnedmoneyvalue) { this.returnedmoneyvalue = returnedmoneyvalue; }

    public void setDay(int day) { this.day = day; }

    public void setMonth(int month) { this.month = month; }

    public void setYear(int year) { this.year = year; }

    public void setReplyText(String replyText) { ReplyText = replyText != null ? replyText.trim() : null; }

    // ————————————————————
    // Getters
    // ————————————————————

    public int getComplaintID() { return complaintID; }

    public int getCustomerID() { return CustomerID; }

    public int getOrderID() { return OrderID; }

    public boolean isAccepted() { return Accepted; }

    public boolean isIn24Hours() { return in24Hours; }

    public String getComplaintText() { return complaintText; }

    public int getShopID() { return shopID; }

    public int getAnswerworkerID() { return answerworkerID; }

    public boolean isReturnedMoney() { return returnedMoney; }

    public int getReturnedmoneyvalue() { return returnedmoneyvalue; }

    public int getDay() { return day; }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public String getReplyText() { return ReplyText; }

    public String getDate() {
        return this.day + "/" + this.month + "/" + this.year;
    }

    public boolean sameDate(Complaint other) {
        if (other == null) return false;
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintID=" + complaintID +
                ", CustomerID=" + CustomerID +
                ", OrderID=" + OrderID +
                ", Accepted=" + Accepted +
                ", in24Hours=" + in24Hours +
                ", complaintText='" + complaintText + '\'' +
                ", shopID=" + shopID +
                ", answerworkerID=" + answerworkerID +
                ", returnedMoney=" + returnedMoney +
                ", returnedmoneyvalue=" + returnedmoneyvalue +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", ReplyText='" + ReplyText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complaint)) return false;
        Complaint that = (Complaint) o;
        return complaintID == that.complaintID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintID);
    }
}
