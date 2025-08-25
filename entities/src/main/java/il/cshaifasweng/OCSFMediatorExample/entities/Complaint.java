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
    private int complaintId;

    @Column(name = "Customer_Id")
    private int customerId;

    @Column(name = "Order_Id")
    private int orderId;

    @Column(name = "Accepted")
    private boolean resolved;

    @Column(name = "in24Hours")
    private boolean within24Hours;

    @Column(name = "complaintText", length = 1000)
    private String complaintText;

    @Column(name = "shop_Id")
    private int shopId;

    @Column(name = "AnswerWorker_Id")
    private int answerWorkerId;

    @Column(name = "returnedMoney")
    private boolean refunded;

    @Column(name = "returnedmoneyvalue")
    private int refundAmount;

    @Column(name = "RecDay")
    private int day;

    @Column(name = "RecMonth")
    private int month;

    @Column(name = "RecYear")
    private int year;

    @Column(name = "Reply", length = 1000)
    private String replyText;

    // ————————————————————
    // Constructors
    // ————————————————————

    public Complaint() { }

    public Complaint(int complaintId, int customerId, int orderId, boolean resolved, boolean within24Hours,
                     String complaintText, int shopId, int answerWorkerId, boolean refunded,
                     int refundAmount, int day, int month, int year, String replyText) {
        this.complaintId = complaintId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.resolved = resolved;
        this.within24Hours = within24Hours;
        this.complaintText = complaintText;
        this.shopId = shopId;
        this.answerWorkerId = answerWorkerId;
        this.refunded = refunded;
        this.refundAmount = refundAmount;
        this.day = day;
        this.month = month;
        this.year = year;
        this.replyText = replyText;
    }
    // ————————————————————
    // Setters
    // ————————————————————

    public void setComplaintId(int complaintId) { this.complaintId = complaintId; }

    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public void setOrderId(int orderId) { this.orderId = orderId; }

    public void setResolved(boolean resolved) { this.resolved = resolved; }

    public void setWithin24Hours(boolean within24Hours) { this.within24Hours = within24Hours; }

    public void setComplaintText(String complaintText) { this.complaintText = complaintText != null ? complaintText.trim() : null; }

    public void setShopId(int shopId) { this.shopId = shopId; }

    public void setAnswerWorkerId(int answerWorkerId) { this.answerWorkerId = answerWorkerId; }

    public void setRefunded(boolean refunded) { this.refunded = refunded; }

    public void setRefundAmount(int refundAmount) { this.refundAmount = refundAmount; }

    public void setDay(int day) { this.day = day; }

    public void setMonth(int month) { this.month = month; }

    public void setYear(int year) { this.year = year; }

    public void setReplyText(String replyText) { this.replyText = replyText != null ? replyText.trim() : null; }
    // ————————————————————
    // Getters
    // ————————————————————

    public int getComplaintId() { return complaintId; }

    public int getCustomerId() { return customerId; }

    public int getOrderId() { return orderId; }

    public boolean isResolved() { return resolved; }

    public boolean isWithin24Hours() { return within24Hours; }

    public String getComplaintText() { return complaintText; }

    public int getShopId() { return shopId; }

    public int getAnswerWorkerId() { return answerWorkerId; }

    public boolean isRefunded() { return refunded; }

    public int getRefundAmount() { return refundAmount; }

    public int getDay() { return day; }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public String getReplyText() { return replyText; }
    public String getDate() {
        return this.day + "/" + this.month + "/" + this.year;
    }

    public boolean sameDate(Complaint other) {
        if (other == null) return false;
        return this.day == other.day && this.month == other.month && this.year == other.year;    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public String toString() {
        return "complaintId=" + complaintId +
                ", customerId=" + customerId +
                ", orderId=" + orderId +
                ", resolved=" + resolved +
                ", within24Hours=" + within24Hours +
                ", complaintText='" + complaintText + '\'' +
                ", shopId=" + shopId +
                ", answerWorkerId=" + answerWorkerId +
                ", refunded=" + refunded +
                ", refundAmount=" + refundAmount +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", replyText='" + replyText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complaint)) return false;
        Complaint that = (Complaint) o;
        return complaintId == that.complaintId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(complaintId);
    }
}
