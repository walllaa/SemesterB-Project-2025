package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class UpdateMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String targetClass;
    private String actionType;
    private String deleteKey;
    private int entityId;

    private Product product;
    private Account account;
    private Worker worker;
    private Manager manager;
    private Order order;
    private Complaint complaint;
    private Message message;

    public UpdateMessage(String targetClass, String actionType) {
        this.targetClass = targetClass;
        this.actionType = actionType;
    }

    // === Complaint ===
    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }

    // === Metadata ===
    public String getTargetClass() { return targetClass; }
    public String getActionType() { return actionType; }

    public String getDeleteKey() { return deleteKey; }
    public void setDeleteKey(String deleteKey) { this.deleteKey = deleteKey; }

    public int getEntityId() { return entityId; }
    public void setEntityId(int entityId) { this.entityId = entityId; }

    // === Entities ===
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Worker getWorker() { return worker; }
    public void setWorker(Worker worker) { this.worker = worker; }

    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Message getMessage() { return message; }
    public void setMessage(Message message) { this.message = message; }

    @Override
    public String toString() {
        return "UpdateMessage{targetClass='" + targetClass +
                "', actionType='" + actionType +
                "', deleteKey='" + deleteKey +
                "', entityId=" + entityId + "}";
    }
}
