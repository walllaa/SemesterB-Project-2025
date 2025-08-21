package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class getAllOrdersMessage implements Serializable { // ⚠️ Class name should be PascalCase (GetAllOrdersMessage)

    private static final long serialVersionUID = 1L;

    private List<Order> orderList; // changed to private for encapsulation

    public getAllOrdersMessage() { }

    public getAllOrdersMessage(List<Order> orders) {
        this.orderList = orders; // constructor overload to init directly
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> givenlistOfOrders) {
        this.orderList = givenlistOfOrders;
    }

    @Override
    public String toString() {
        return "getAllOrdersMessage{" +
                "orderList size=" + (orderList != null ? orderList.size() : 0) +
                '}'; //  added size info for debugging
    }

    @Override
    public boolean equals(Object o) { // useful for comparing message objects
        if (this == o) return true;
        if (!(o instanceof getAllOrdersMessage)) return false;
        getAllOrdersMessage that = (getAllOrdersMessage) o;
        return Objects.equals(orderList, that.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderList);
    }
}
