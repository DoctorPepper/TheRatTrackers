package com.lead.rattrackerapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lead.rattrackerapp.Model.Sightings.Borough;
import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward Jackson
 *
 * Tests the SightingList class's getSmallData() method.
 */

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class SightingListSmallDataTests {

    private DatabaseReference mockedDatabaseReference;

    static SightingList real;
    List<Sighting> fullTestList;
    List<Sighting> emptyTestList;
    List<Sighting> halfTestList;
    List<Sighting> sizeOneTestList;

    @Before
    public void setUp() throws Exception {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        real = SightingList.getInstance();

        fullTestList = new ArrayList<>();
        emptyTestList = new ArrayList<>();
        halfTestList = new ArrayList<>();
        sizeOneTestList = new ArrayList<>();
        real.reset();
        for (int i = 0; i < 26; i++) {
            Sighting s = new Sighting(i, "11/25/1997", "test", "30000", "" + ('a' + i),
                    "" + ('a' + i + 32), Borough.NONE, 0, 0, 0);
            real.addSighting(s);
            fullTestList.add(0, s);
            if (i >= 13) {
                halfTestList.add(0, s);
            }
            if (i == 25) {
                sizeOneTestList.add(0, s);
            }
        }
    }

    @Test
    public void testAmountNegative() {
        try {
            List<Sighting> actual = real.getSmallData(-1);
        } catch (Exception e) {
            assertEquals("Incorrect exception thrown when amount"
                            + " was negative",
                    IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    public void testSizeOfReturnedList() {
        assertEquals(26, real.size());
        List<Sighting> actual = real.getSmallData(30);
        assertEquals("Size of the returned list is not correct",
                26, actual.size());
        actual = real.getSmallData(26);
        assertEquals("Size of the returned list is not correct",
                26, actual.size());
        actual = real.getSmallData(13);
        assertEquals("Size of the returned list is not correct",
                13, actual.size());
        actual = real.getSmallData(1);
        assertEquals("Size of the returned list is not correct",
                1, actual.size());
        actual = real.getSmallData(0);
        assertEquals("Size of the returned list is not correct",
                0, actual.size());
    }

    @Test
    public void testAmountLargerThanSize() {
        List<Sighting> actual = real.getSmallData(30);
        assertEquals("GetSmallData failed when the amount was"
                        + " greater than the size of the list",
                fullTestList, actual);
    }

    @Test
    public void testAmountIsSize() {
        List<Sighting> actual = real.getSmallData(26);
        assertEquals("GetSmallData failed when the amount was"
                        + " the same as the size of the list",
                fullTestList, actual);
    }



    @Test
    public void testAmountOne() {
        List<Sighting> actual = real.getSmallData(1);
        assertEquals("GetSmallData failed when the amount was"
                        + " one",
                sizeOneTestList, actual);
    }

    @Test
    public void testAmountZero() {
        List<Sighting> actual = real.getSmallData(0);
        assertEquals("GetSmallData failed when the amount was"
                        + " zero",
                emptyTestList, actual);
    }

    @Test
    public void testAmountGeneral() {
        List<Sighting> actual = real.getSmallData(13);
        assertEquals("GetSmallData failed in the general case"
                        + " when the amount was a positive"
                        + " number less than amount",
                halfTestList, actual);
    }
}
