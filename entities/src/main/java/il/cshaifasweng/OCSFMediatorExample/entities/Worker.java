package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workers_table")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int personID;

    @Column(name = "Full_Name")
    private String fullName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Passowrd")
    private String password;

    @Column(name = "Logged_In")
    private boolean loggedIn;

    public Worker() {}

    public Worker(String fullName, String email, String password, int personID) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.personID = personID;
        this.loggedIn = false;
    }

    // ===== Getters / Setters الأساسية =====
    public int getPersonID() { return personID; }
    public void setPersonID(int personID) { this.personID = personID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    // ===== Compatibility bridges (لمنادات بأسماء مختلفة) =====
    // person id (ID / Id)
    public int  getPersonId() { return personID; }
    public void setPersonId(int id) { this.personID = id; }

    // logged in (is/get)
    public boolean getLoggedIn() { return loggedIn; }        // أحياناً بيصير نداء getLoggedIn()
    public void setIsLoggedIn(boolean v) { this.loggedIn = v; } // لبعض الأكواد القديمة

    // full name (احتمال اختلاف كابيتالايزيشن)
    public String getFullname() { return fullName; }
    public void setFullname(String v) { this.fullName = v; }

    // email (احتمال EMail)
    public String getEMail() { return email; }
    public void setEMail(String v) { this.email = v; }
}
