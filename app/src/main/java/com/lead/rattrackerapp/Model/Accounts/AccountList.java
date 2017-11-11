package com.lead.rattrackerapp.Model.Accounts;

import java.util.HashMap;

/* A list of accounts held by the application */
class AccountList {
    private static final AccountList ourInstance = new AccountList();
    private static HashMap<String, Account> accounts;

    /**
     * Get a new instance of AccountList
     *
     * @return the new instance
     */
    static AccountList getInstance() {
        return ourInstance;
    }

    private AccountList() {
        accounts = new HashMap<>();
    }

    /**
     * Returns a boolean of whether or not the account exists
     *
     * @param email The email of the associated account
     * @return true if the account exists, false otherwise
     */
    private static boolean accountExists(String email) {
        return accounts.containsKey(email);
    }

    /**
     * Determines if the specified account is correctly set up
     *
     * @param email The email of the account
     * @param password The password of the account
     *
     * @return true if the account is correct, false otherwise
     */
    public static boolean accountCorrect(String email, String password) throws Exception {
        if (accountExists(email)) {
            Account a = accounts.get(email);
            if (a.getPassword().equals(password)) {
                if (!a.getLocked()) {
                    return true;
                } else {
                    throw new Exception("Account is locked");
                }
            } else {
                throw new Exception("Incorrect password");
            }
        } else {
            throw new Exception("Account with this email does not exist");
        }
    }

    /**
     * Creates a new account
     *
     * @param account The new account to be created
     */
    public static void createAccount(Account account) throws Exception {
        if (!accountExists(account.getEmail())) {
            accounts.put(account.getEmail(), account);
        } else {
            throw new Exception("Account with this email already exists");
        }
    }
}
