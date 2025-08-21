package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GetAllMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Message> messageList;

    // ————————————————————
    // Constructors
    // ————————————————————

    public GetAllMessages() { }

    public GetAllMessages(List<Message> messages) {
        this.messageList = messages;
    }

    // ————————————————————
    // Getters & Setters
    // ————————————————————

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> msgList) {
        this.messageList = msgList;
    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public String toString() {
        return "GetAllMessages{" +
                "messageList size=" + (messageList != null ? messageList.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetAllMessages)) return false;
        GetAllMessages that = (GetAllMessages) o;
        return Objects.equals(messageList, that.messageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageList);
    }
}
