package com.lead.rattrackerapp.Model.Accounts;

/* The model for a user's account */
class Account {
    private String email;
    private String password;
    private boolean locked;
    private AccountType type;

    public Account(String email, String password) {
        this(email, password, AccountType.USER, false);
    }

    private Account(String email, String password, AccountType type, boolean locked) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.locked = locked;
    }
    /**
     * Get the email of the account
     *
     * @return the email
     *
     */
    String getEmail() {
        return email;
    }

    /**
     * Sets the email of the account
     *
     * @param email The new email that will replace the old one
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the password of the account
     *
     * @return the password
     */
    String getPassword() {
        return password;
    }

    /**
     * Set the password of the account
     *
     * @param password The new password that will replace the old one
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the account type
     *
     * @return the type
     */
    public AccountType getType() {
        return type;
    }

    /**
     * Set the account type
     *
     * @param type The new account type that will replace the old one
     */
    public void setType(AccountType type) {
        this.type = type;
    }

    /**
     * Get the value that describes the "locked" value of the account
     *
     * @return true if the account is locked, false otherwise
     */
    boolean getLocked() {
        return locked;
    }

    /**
     * Set the account as locked or unlocked
     *
     * @param locked The new boolean "locked" value of the account
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Creates the hashcode for this account
     *
     * @return the hashcode
     */
    public int hashcode() {
        return ((email.hashCode() * 17) + password.hashCode());
    }

    /**
     * Determines whether an Account is equal to another Account
     *
     * @param that an object to be compared to another Account
     * @return true if the accounts are equal to each other, false otherwise
     */
    public boolean equals(Object that) {
        if (this == that ) return true;
        if (!(that instanceof Account) ) return false;
        Account other = (Account) that;
        return this.email.equals(other.getEmail())
                && this.password.equals(other.getPassword());
    }
}
