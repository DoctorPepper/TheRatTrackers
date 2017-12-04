package com.lead.rattrackerapp;

import com.lead.rattrackerapp.Model.Accounts.Account;
import com.lead.rattrackerapp.Model.Accounts.AccountList;
import com.lead.rattrackerapp.Model.Accounts.AccountType;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by power on 12/3/2017.
 *
 * Tests createAccount() in AcountList.java
 */

public class AccountListCreateAccount {

    private AccountList mylist;

    /**
     * Sets up tests
     *
     * @throws Exception .
     */
    @Before
    public void setUp() throws Exception {
        mylist = new AccountList();
        mylist.createAccount(new Account("apowers@gatech.edu", "spy", AccountType.ADMIN, false));
        mylist.createAccount(new Account("wow@gmail.com", "muchwow", AccountType.USER, true));

    }

    /**
     * Tries to create an account already in use
     */
    @Test
    public void testAccountInUse() {
        boolean success = true;
        try {
            AccountList.createAccount(new Account("apowers@gatech.edu", "spy"));
        } catch (Exception e) {
            assertEquals("Account already exists", Exception.class, e.getClass());
            success = false;
        }
        assertEquals(success, false);
    }

    /**
     * Tries to create an account where the username is already in use
     */
    @Test
    public void testUsernameInUse() {
        boolean success = true;
        try {
            AccountList.createAccount(new Account("apowers@gatech.edu", "legitPass"));
        } catch (Exception e) {
            assertEquals("Account already exists", Exception.class, e.getClass());
            success = false;
        }
        assertEquals(success, false);
    }

    /**
     * Creates a normal account
     */
    @Test
    public void testNormalAccount() {
        boolean success = true;
        try {
            AccountList.createAccount(new Account("goodAccount@gatech.edu", "YAY!"));
        } catch (Exception e) {
            assertEquals("Account already exists", Exception.class, e.getClass());
            success = false;
        }
        assertEquals(success, true);
    }

    /**
     * Creates a new account with a password in use (Shouldn't matter)
     */
    @Test
    public void testPasswordInUse() {
        boolean success = true;
        try {
            AccountList.createAccount(new Account("pks@gatech.edu", "muchwow"));
        } catch (Exception e) {
            assertEquals("Account already exists", Exception.class, e.getClass());
            success = false;
        }
        assertEquals(success, true);
    }
}
