package il.cshaifasweng.OCSFMediatorExample.entities;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class getAllOrdersMessage implements Serializable { // added 18/7

    List<Order> orderList ;
    public getAllOrdersMessage(){

    }
    public void setOrderList(List<Order> givenlistOfOrders){
        orderList = givenlistOfOrders ;
    }
    public List<Order> getOrderList(){
        return orderList;
    }
}
