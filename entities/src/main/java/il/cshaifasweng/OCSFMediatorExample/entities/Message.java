package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "messages_table")
public class Message implements Serializable { //  consider adding serialVersionUID for Serializable classes

    @Id
    private int messageID; // ️ no @GeneratedValue → IDs must be assigned manually

    @Column(name = "customer_ID")
    private int customerID; // could be a FK to Customer entity in DB

    @Column(name = "Message_Txt")
    private String msgText; // consider @Lob if messages can be long

    public Message() { } // required by JPA

    public Message(int messageID, int customerID, String msgText) {
        this.messageID = messageID;
        this.customerID = customerID;
        this.msgText = msgText;
    }

    // getters
    public int getMessageID() { return messageID; }
    public int getCustomerID() { return customerID; }
    public String getMsgText() { return msgText; }

    // setters
    public void setMessageID(int messageID) { this.messageID = messageID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public void setMsgText(String msgText) { this.msgText = msgText; }

    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", customerID=" + customerID +
                ", msgText='" + msgText + '\'' +
                '}';
    }
}
