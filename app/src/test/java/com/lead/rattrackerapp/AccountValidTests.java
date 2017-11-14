package com.lead.rattrackerapp;

import com.lead.rattrackerapp.Model.Accounts.Account;
import com.lead.rattrackerapp.Model.Accounts.AccountList;
import com.lead.rattrackerapp.Model.Accounts.AccountType;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by natek on 11/13/2017.
 *
 * Testing the function "accountCorrect" in AccountList.java
 */

public class AccountValidTests {

    private AccountList mylist;

    /**
     * Sets up tests
     * @throws Exception .
     */
    @Before
    public void setUp() throws Exception {
        mylist = new AccountList();
        mylist.createAccount(new Account("rwaters@gatech.edu", "mostAG1LE", AccountType.ADMIN, false));
        mylist.createAccount(new Account("jbond@mi6.co.uk", "007shaken", AccountType.USER, true));
    }

    /**
     * Tests a negative amount
     */
    @Test
    public void testNonexistant() {
        boolean success = false;
        try {
            success = AccountList.accountCorrect("thedude", "coolrug");
        } catch (Exception e) {
            assertEquals("Account with this email does not exist", Exception.class, e.getClass());
        }
        assertEquals(success, false);
    }

    /**
     * Tests a negative amount
     */
    @Test
    public void testWrongPassword() {
        boolean success = false;
        try {
            success = AccountList.accountCorrect("rwaters@gatech.edu", "skool4chumpz");
        } catch (Exception e) {
            assertEquals("Incorrect password", Exception.class, e.getClass());
        }
        assertEquals(success, false);
    }

    /**
     * Tests a negative amount
     */
    @Test
    public void testLocked() throws Exception {
        boolean success = false;
        try {
            success = AccountList.accountCorrect("jbond@mi6.co.uk", "007shaken");
        } catch (Exception e) {
            assertEquals("Account is locked", Exception.class, e.getClass());
        }
        assertEquals(success, false);
    }

    /**
     * Tests a negative amount
     */
    @Test
    public void testLogin() throws Exception {
        boolean success = false;
        success = AccountList.accountCorrect("rwaters@gatech.edu", "mostAG1LE");
        assert(success);
    }
}
