package com.lead.rattrackerapp.Model.Accounts;

/* The model for a user's account */
public class Account {
    private String email;
    private String password;
    private boolean locked;
    private AccountType type;

    public Account(String email, String password) {
        this(email, password, AccountType.USER, false);
    }

    public Account(String email, String password, AccountType type, boolean locked) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.locked = locked;
    }

    String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int hashcode() {
        return ((email.hashCode() * 17) + password.hashCode());
    }

    public boolean equals(Object that) {
        if (this == that ) return true;
        if (!(that instanceof Account) ) return false;
        Account other = (Account) that;
        return this.email.equals(other.getEmail())
                && this.password.equals(other.getPassword());
    }
}
