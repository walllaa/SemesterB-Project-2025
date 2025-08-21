package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.Objects;

/*
==================== Code Review Notes (English) ====================
IMPORTANT (should fix / addressed here):
1) Field `exists` is only set in the constructor with boolean parameter. Better to allow setting it in main constructor or provide a setter.
2) Constructor inconsistency: one constructor takes (email, person, pass) but does not allow setting `exists`. The other takes (exists) but not email/person.
   → Combined design might be clearer with a single constructor or multiple overloaded ones that initialize all relevant fields.
3) Boolean getter `getExists()` could be renamed to `isExists()` by convention, but kept as-is for compatibility.

NOT IMPORTANT (optional cleanups):
- Trimmed email/person/password inputs in constructor for normalization.
- Added setter for `exists` to make the object more flexible.
- Added toString(), equals(), hashCode() for better debugging and entity comparison.
- serialVersionUID added for Serializable.
=====================================================================
*/

public class CheckMail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private boolean exists;
    private String person;
    private String password;

    // ————————————————————
    // Constructors
    // ————————————————————

    public CheckMail(String email, String person, String pass) {
        this.email = email != null ? email.trim().toLowerCase() : null;
        this.person = person != null ? person.trim() : null;
        this.password = pass;
    }

    public CheckMail(boolean exists) {
        this.exists = exists;
    }

    // ————————————————————
    // Setters
    // ————————————————————

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    // ————————————————————
    // Getters
    // ————————————————————

    public String getEmail() {
        return email;
    }

    public boolean getExists() {
        return exists;
    }

    public String getPerson() {
        return this.person;
    }

    public String getPassword() {
        return this.password;
    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public String toString() {
        return "CheckMail{" +
                "email='" + email + '\'' +
                ", exists=" + exists +
                ", person='" + person + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckMail)) return false;
        CheckMail that = (CheckMail) o;
        return exists == that.exists &&
                java.util.Objects.equals(email, that.email) &&
                java.util.Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(email, exists, person);
    }
}
