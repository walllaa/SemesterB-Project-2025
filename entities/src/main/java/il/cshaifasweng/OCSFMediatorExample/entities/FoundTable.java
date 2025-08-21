package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class FoundTable implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private List<Product> recievedProducts;  // typo kept for compatibility
    private List<Manager> recievedManagers;  // typo kept for compatibility
    private List<Worker> recievedWokers;     // typo kept for compatibility

    // ————————————————————
    // Constructors
    // ————————————————————

    public FoundTable(String msg, List<Product> recievedProds) {
        this.message = msg;
        this.recievedProducts = recievedProds;
    }

    public FoundTable(String msg) {
        this.message = msg;
    }

    // ————————————————————
    // Getters & Setters
    // ————————————————————

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getRecievedProducts() {
        return recievedProducts;
    }

    public void setRecievedProducts(List<Product> recievedProducts) {
        this.recievedProducts = recievedProducts;
    }

    public List<Manager> getRecievedManagers() {
        return this.recievedManagers;
    }

    public void setRecievedManagers(List<Manager> recievedManagers) {
        this.recievedManagers = recievedManagers;
    }

    public List<Worker> getRecievedWokers() {
        return this.recievedWokers;
    }

    public void setRecievedWorkers(List<Worker> recievedWokers) {
        this.recievedWokers = recievedWokers;
    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public String toString() {
        return "FoundTable{" +
                "message='" + message + '\'' +
                ", recievedProducts=" + (recievedProducts != null ? recievedProducts.size() : 0) +
                ", recievedManagers=" + (recievedManagers != null ? recievedManagers.size() : 0) +
                ", recievedWokers=" + (recievedWokers != null ? recievedWokers.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoundTable)) return false;
        FoundTable that = (FoundTable) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(recievedProducts, that.recievedProducts) &&
                Objects.equals(recievedManagers, that.recievedManagers) &&
                Objects.equals(recievedWokers, that.recievedWokers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, recievedProducts, recievedManagers, recievedWokers);
    }
}
