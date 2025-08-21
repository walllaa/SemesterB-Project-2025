package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "managers_table")
public class Manager implements Serializable { // ⚠️ class is Serializable, added good practice with serialVersionUID

    private static final long serialVersionUID = 1L;

    @Id
    private int personID;

    @Column(name = "Full_Name")
    private String fullName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Passowrd") // ⚠️ DB column has typo "Passowrd", kept as-is to match schema
    private String password;

    @Column(name = "ShopID") // 0 = Chain Manager, else = Shop Manager
    private int ShopID; // ⚠️ field name should be lowercase by convention, kept as-is for compatibility

    private boolean loggedIn;

    public Manager(String fullName, String email, String password, int personID) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.personID = personID;
        this.loggedIn = false; // default to logged out
    }

    public Manager() { } // required by JPA

    // setters
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setShopID(int shopID) { this.ShopID = shopID; }
    public void setPersonID(int personID) { this.personID = personID; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    // getters
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public int getShopID() { return ShopID; }
    public int getPersonID() { return personID; }
    public boolean isLoggedIn() { return loggedIn; }

    @Override
    public String toString() {
        return "Manager{" +
                "personID=" + personID +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ShopID=" + ShopID +
                ", loggedIn=" + loggedIn +
                '}';
    }
}
