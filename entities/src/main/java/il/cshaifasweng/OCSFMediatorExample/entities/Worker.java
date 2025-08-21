package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "workers_table")
public class Worker implements Serializable
{
    @Id
    private int personID;
    @Column(name = "Full_Name")
    private String fullName;
    @Column(name = "Email")
    private String email;
    @Column(name = "Passowrd")
    private String password;
    private boolean loggedIn;
    public Worker(String fullName, String email, String password,int personID)
    {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.personID = personID;
        this.loggedIn = false;
    }
    public Worker() {}

    public int getPersonID() {
        return personID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
