package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class RemovedProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productKey;
    private String note;
    public RemovedProduct(String note, String productKey) {
        this.note = note;
        this.productKey = productKey;
    }

    public RemovedProduct() { }


    public String getNote() {
        return note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String newKey) {
        this.productKey = newKey;
    }

    @Override
    public String toString() {
        return "RemovedProduct{productKey='" + productKey + "', note='" + note + "'}";
    }
}
