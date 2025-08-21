package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetAllAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Account> all_Customer_accounts = new ArrayList<>();

    // ————————————————————
    // Constructors
    // ————————————————————

    public GetAllAccounts() { }

    public GetAllAccounts(List<Account> accounts) {
        this.all_Customer_accounts = accounts;
    }

    // ————————————————————
    // Getters & Setters
    // ————————————————————

    public List<Account> getAll_accounts() {
        return all_Customer_accounts;
    }

    public void setAll_accounts(List<Account> all_Customer_accounts) {
        this.all_Customer_accounts = all_Customer_accounts;
    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public String toString() {
        return "GetAllAccounts{" +
                "all_Customer_accounts=" + (all_Customer_accounts != null ? all_Customer_accounts.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetAllAccounts)) return false;
        GetAllAccounts that = (GetAllAccounts) o;
        return Objects.equals(all_Customer_accounts, that.all_Customer_accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(all_Customer_accounts);
    }
}
