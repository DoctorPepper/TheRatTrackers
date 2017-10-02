package com.lead.rattrackerapp.Model;

import java.util.HashMap;

/**
 * Created by ejjac on 10/1/2017.
 */

public class AccountList {
    private static final AccountList ourInstance = new AccountList();
    private static HashMap<String, Account> accounts;

    static AccountList getInstance() {
        return ourInstance;
    }

    private AccountList() {
        accounts = new HashMap<String, Account>();
    }

    public static boolean accountExists(String email) {
        return accounts.containsKey(email);
    }

    public static boolean accountCorrect(String email, String password) throws Exception {
        if (accountExists(email)) {
            Account a = accounts.get(email);
            if (a.getPassword().equals(password)) {
                if (a.getLocked() == false) {
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

    public static void createAccount(Account account) throws Exception {
        if (!accountExists(account.getEmail())) {
            accounts.put(account.getEmail(), account);
        } else {
            throw new Exception("Account with this email already exists");
        }
    }
}
