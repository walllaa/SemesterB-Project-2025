package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products_table")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L; // good practice with Serializable

    @Id
    private int productCode;

    @Column(name = "ui_button")
    private String actionBtn;

    @Column(name = "product_name")
    private String title;

    @Column(name = "product_info")
    private String description;

    @Column(name = "product_cost")
    private String cost;

    @Column(name = "product_photo")
    private String photoUrl;

    public Product(int code1, String btn, String title, String desc, String cost) {
        this.productCode = code1;
        this.actionBtn = btn;
        this.title = title;
        this.description = desc;
        this.cost = cost;
    }

    public Product() {}

    // === Getters & Setters
    public int getProductCode() { return productCode; }
    public void setProductCode(int productCode) { this.productCode = productCode; }

    public String getActionBtn() { return actionBtn; }
    public void setActionBtn(String actionBtn) { this.actionBtn = actionBtn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCost() { return cost; }
    public void setCost(String cost) { this.cost = cost; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    // Helper
    public void decrementCode() { this.productCode = this.productCode - 1; }

    // ===== Compatibility bridges (للسيرفر القديم) =====
    public String getName() { return title; }
    public void setName(String v) { this.title = v; }

    public String getDetails() { return description; }
    public void setDetails(String v) { this.description = v; }

    public String getButton() { return actionBtn; }
    public void setButton(String v) { this.actionBtn = v; }

    public String getPrice() { return cost; }
    public void setPrice(String v) { this.cost = v; }

    public String getImage() { return photoUrl; }
    public void setImage(String v) { this.photoUrl = v; }

    public int getID() { return productCode; }
    public int setID(int id) { this.productCode = id; return this.productCode; }

    public void updateid() { this.productCode = this.productCode - 1; }
}
