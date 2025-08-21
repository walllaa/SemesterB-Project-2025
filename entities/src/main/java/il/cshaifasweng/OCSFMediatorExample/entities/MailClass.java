package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class MailClass implements Serializable { //️ class name could be simplified to Mail for clarity, kept as-is for compatibility

    private static final long serialVersionUID = 1L; // recommended for Serializable classes

    private String mail; // consider renaming to email for clearer semantics

    public MailClass(String ml) {
        this.mail = ml; //  no validation here, could add email format check if needed
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String ml) {
        this.mail = ml;
    }

    @Override
    public String toString() {
        return "MailClass{mail='" + mail + "'}";
    }
}
