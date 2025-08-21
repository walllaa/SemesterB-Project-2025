package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "products_table")
public class Product implements Serializable {

    @Id
    private int id;
    private String button;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_details")
    private String details;
    @Column(name = "product_price")
    private String price;
    @Column(name = "product_image")
    private String image;
   /* @ManyToMany (mappedBy = "products")
    private List<Order> orders;
*/
    public Product(int id, String button, String name,String details, String price) {
        super();
        this.button = button;
        this.name = name;
        this.details = details;
        this.price = price;
        this.id = id;

    }

    public Product(){

    }

    // SETTER & GETTERS
    public void setButton(String new_button){
        this.button = new_button;
    }
    public String getButton(){
        return this.button;
    }
    public void setName(String new_name){
        this.name = new_name;
    }
    public String getName(){
        return this.name;
    }
    public void setPrice(String new_price){
        this.price = new_price;
    }
    public String getPrice(){
        return this.price;
    }
    public void setDetails(String new_details){
        this.details = new_details;
    }
    public String getDetails(){
        return this.details;
    }
    public void setImage(String new_image){
        this.image = new_image;
    }
    public String getImage(){
        return this.image;
    }
    public int setID(int newid){
        this.id = newid;
        return  this.id;
    }
    public  int getID(){

        return this.id;
    }

    public void updateid() {
        this.id=id-1;
    }
}