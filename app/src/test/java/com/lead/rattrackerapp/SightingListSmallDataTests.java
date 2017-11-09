package com.lead.rattrackerapp;

import com.lead.rattrackerapp.Model.Sightings.Borough;
import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Edward Jackson
 *
 * Tests the SightingList class's getSmallData() method.
 */

public class SightingListSmallDataTests {

    SightingList testList = SightingList.getInstance();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 26; i++) {
            testList.addSighting(new Sighting(i, "11/25/1997", "test", "30000", "" + ('a' + i),
                    "" + ('a' + i + 32), Borough.NONE, 0, 0, 0));
        }
    }

//    public List<Sighting> getSmallData(int amount) {
//        if (amount < 0) {
//            throw new IllegalArgumentException("Amount cannot be negative");
//        }
//        if (data.size() < amount) {
//            amount = data.size();
//        }
//        List smallData = new ArrayList(amount);
//        for (int i = data.size() - 1; i >= (data.size() - amount); i--) {
//            smallData.add(data.get(i));
//        }
//        return smallData;
//    }

    @Test
    public void tesAmountLargerThanSize() {

    }

    @Test
    public void testAmountNegative() {

    }

    @Test
    public void testAmountZero() {

    }

    @Test
    public void testAmountGeneral() {

    }
}
