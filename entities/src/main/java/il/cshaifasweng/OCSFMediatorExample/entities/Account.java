package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "accounts_table")
@Access(AccessType.FIELD)
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    // ————————————————————
    // Fields & Mapping
    // ————————————————————

    @Id
    @Column(name = "accountID")
    private int accountID;

    @Column(name = "UserID", nullable = false)
    private long ID; // Kept uppercase name for compatibility with existing code

    @Column(name = "Full_Name", nullable = false, length = 120)
    private String fullName;

    @Column(name = "Address", length = 255)
    private String address; // Privilege: 0=GUEST, 1=CUSTOMER, 2=WORKER, 3=MANAGER, 4=CHAIN_MANAGER

    @Column(name = "Email", nullable = false, unique = true, length = 160)
    private String email;

    // NOTE: The DB column is misspelled as "Passowrd"; kept as-is to avoid schema break
    @Column(name = "Passowrd", nullable = false, length = 200)
    private String password;

    @Column(name = "Phone_Number", length = 20)
    private long phoneNumber;

    @Column(name = "Credit_Card_Number", length = 30)
    private long creditCardNumber;

    @Column(name = "creditMonthExpire")
    private Integer creditMonthExpire; // wrapper to allow nulls

    @Column(name = "creditYearExpire")
    private Integer creditYearExpire;  // wrapper to allow nulls

    @Column(name = "CVV")
    private Integer ccv;               // wrapper to allow nulls

    @Column(name = "Logged_In")
    private Boolean loggedIn = Boolean.FALSE;

    @Column(name = "Shop")
    private Integer belongShop;        // wrapper to allow nulls

    @Column(name = "Subscription")
    private boolean Subscription;      // kept for compatibility with existing code

    @Column(name = "Privilage")
    private Integer privilege;         // internal correct spelling; API keeps get/setPrivialge

    // ————————————————————
    // Constructors
    // ————————————————————

    public Account() { /* JPA default */ }

    public Account(int accountID,
                   String fullName,
                   long ID,
                   String address,
                   String email,
                   String password,
                   long phoneNumber,
                   long creditCardNumber,
                   int creditMonthExpire,
                   int creditYearExpire,
                   int ccv,
                   Boolean loggedIn,
                   int belongShop,
                   boolean subscription) {
        this.accountID = accountID;
        this.ID = ID;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.creditCardNumber = creditCardNumber;
        this.creditMonthExpire = creditMonthExpire;
        this.creditYearExpire = creditYearExpire;
        this.ccv = ccv;
        this.loggedIn = loggedIn;
        this.belongShop = belongShop;
        this.Subscription = subscription;
    }

    // ————————————————————
    // Setters (with light normalization)
    // ————————————————————

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName != null ? fullName.trim() : null;
    }

    public void setAddress(String address) {
        this.address = address != null ? address.trim() : null;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim().toLowerCase() : null;
    }

    public void setPassword(String password) {
        this.password = password; // Consider hashing before persist/update in production
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setCreditMonthExpire(int creditMonthExpire) {
        this.creditMonthExpire = creditMonthExpire;
    }

    public void setCreditYearExpire(int creditYearExpire) {
        this.creditYearExpire = creditYearExpire;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setBelongShop(int belongShop) {
        this.belongShop = belongShop;
    }

    public void setSubscription(boolean subscription) {
        Subscription = subscription;
    }

    // Keep misspelled API for compatibility
    public void setPrivialge(int privialge) {
        this.privilege = privialge;
    }

    // Provide both overloads for compatibility
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    // ————————————————————
    // Getters (unchanged signatures)
    // ————————————————————

    public int getAccountID() {
        return accountID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public int getCreditMonthExpire() {
        return creditMonthExpire != null ? creditMonthExpire : 0;
    }

    public int getCreditYearExpire() {
        return creditYearExpire != null ? creditYearExpire : 0;
    }

    public int getCcv() {
        return ccv != null ? ccv : 0;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public int getBelongShop() {
        return belongShop != null ? belongShop : 0;
    }

    public boolean isSubscription() {
        return Subscription;
    }

    public int getPrivialge() {
        return privilege != null ? privilege : 0;
    }

    public long getID() {
        return ID;
    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return accountID == account.accountID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", ID=" + ID +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", loggedIn=" + loggedIn +
                ", belongShop=" + belongShop +
                ", subscription=" + Subscription +
                ", privilege=" + privilege +
                '}';
    }
}
