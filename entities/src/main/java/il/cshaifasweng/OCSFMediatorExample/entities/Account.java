package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "accounts_table")
public class Account implements Serializable {

    @Id
    private int accountID;
    @Column(name = "UserID")
    private long ID;
    @Column(name = "Full_Name")
    private String fullName;
    @Column(name = "Address")
    private String address;                 // Privilage 0 = GUEST
    @Column(name = "Email")                 // Privilage 1 = Customer
    private String email;                   // Privilage 2 = Worker
    @Column(name = "Passowrd")              // Privilage 3 = Manager
    private String password;                // Privilage 4 = Chain Manager
    @Column(name = "Phone_Number")
    private long phoneNumber;
    @Column(name = "Credit_Card_Number")
    private long creditCardNumber;
    private int creditMonthExpire;
    private int creditYearExpire;
    @Column(name = "CVV")
    private int ccv;
    @Column(name = "Logged_In")
    private Boolean loggedIn;
    @Column(name = "Shop")
    private int belongShop;
    @Column(name = "Subscription")
    private boolean Subscription;
    @Column(name = "Privilage")
    private int privialge;

    /*
    public Account(String fullName, String address,String email, String password,long phoneNumber, long creditCardNumber,int creditYearExpire,int creditMonthExpire,int ccv,int belongShop)
    {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.creditCardNumber = creditCardNumber;
        this.creditYearExpire = creditYearExpire;
        this.creditMonthExpire = creditMonthExpire;
        this.ccv = ccv;
        this.loggedIn = false;
        this.belongShop = belongShop;
       // this.AccountOrders = null;
    }
     */

    public Account(int accountID, String fullName,long ID, String address, String email, String password, long phoneNumber, long creditCardNumber, int creditMonthExpire, int creditYearExpire, int ccv, Boolean loggedIn, int belongShop,boolean subscription) {
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

    public void setPrivialge(int privialge) {
        this.privialge = privialge;
    }

    public int getPrivialge() {
        return privialge;
    }

    public Account()
    {

    }

    public boolean isSubscription() {
        return Subscription;
    }

    public void setSubscription(boolean subscription) {
        Subscription = subscription;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return creditMonthExpire;
    }

    public int getCreditYearExpire() {
        return creditYearExpire;
    }

    public int getCcv() {
        return ccv;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public int getBelongShop() {
        return belongShop;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    /* public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getWallet() {
        return wallet;
    }*/
}