package com.lead.rattrackerapp;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
/**
 * Created by madisonmcroy on 11/13/17.
 */

public class ReportSightingGetLongDateTests {

    private ReportSightingScreen screen;
    private String validDate;
    private long validDateLong;
    private String invalidDate;
    private String empty;
    private String noTimeDate;
    private String noDateTime;

    /**
     * Sets up tests
     * @throws Exception .
     */
    @Before
    public void setUp() throws Exception {
        screen = new ReportSightingScreen();
        validDate = "10/01/2017 12:00:00 AM";
        validDateLong = 1506830400000L;
        invalidDate = "this is not a date";
        empty = "";
        noTimeDate = "10/01/2017";
        noDateTime = "12:00:00 AM";
    }

    /**
     * Tests with a valid date string
     */
    @Test
    public void testValid() {
        assertEquals(screen.getLongDateFromDateString(validDate), validDateLong);
    }

    /**
     * Tests with an invalid date string (not in the correct date/time format)
     */
    @Test
    public void testInvalid() {
        assertEquals(screen.getLongDateFromDateString(invalidDate), 0);
    }

    /**
     * Tests with an empty string
     */
    @Test
    public void testEmpty() {
        assertEquals(screen.getLongDateFromDateString(empty), 0);
    }

    /**
     * Tests with a string that contains a date but not a time
     */
    @Test
    public void testNoTime() {
        assertEquals(screen.getLongDateFromDateString(noTimeDate), 0);
    }

    /**
     * Tests with a string that contains a time but not a date
     */
    @Test
    public void testNoDate() {
        assertEquals(screen.getLongDateFromDateString(noDateTime), 0);
    }

}
