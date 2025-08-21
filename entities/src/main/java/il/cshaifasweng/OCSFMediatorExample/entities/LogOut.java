package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class LogOut implements Serializable { // ️ class name should conventionally be Logout (camelCase), kept as-is for compatibility

    private static final long serialVersionUID = 1L; // good practice for Serializable classes

    private String mail; // could be renamed to email for clarity

    public LogOut() { } // default constructor

    public void setMail(String ml) {
        this.mail = ml; // consider validating format if used for real emails
    }

    public String getMail() {
        return this.mail;
    }

    @Override
    public String toString() {
        return "LogOut{mail='" + mail + "'}";
    }
}
