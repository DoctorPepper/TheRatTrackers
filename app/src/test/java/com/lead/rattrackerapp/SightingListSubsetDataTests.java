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
import java.util.Collections;

/**
 * Created by Ryan Power on 11/11/2017.
 *
 * Tests SightingList's getSubsetData() method
 * NOTE: A SightingList is displayed in reverse order, while a subset has natural
 * ordering. Lists are reversed to remedy this issue.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class SightingListSubsetDataTests {


    private static SightingList list;
    private List<Sighting> fullTestList;
    private List<Sighting> emptyTestList;
    private List<Sighting> middleTestList;
    private List<Sighting> sizeOneTestList;
    private List<Sighting> beginningTestList;
    private List<Sighting> endTestList;

    /**
     * Sets up tests
     *
     * @throws Exception .
     */
    @Before
    public void setUp() throws Exception {
        DatabaseReference mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        list = SightingList.create_Instance();
        fullTestList = new ArrayList<>();
        emptyTestList = new ArrayList<>();
        middleTestList = new ArrayList<>();
        sizeOneTestList = new ArrayList<>();
        beginningTestList = new ArrayList<>();
        endTestList = new ArrayList<>();
        list.reset();
        for (int i = 0; i < 26; i++) {
            Sighting s = new Sighting(i, "11/11/2017", "Street", "30000", "" + ('a' + i),
                    "" + ('a' + i + 32), Borough.NONE, 0, 0, 0);
            list.addSighting(s);
            fullTestList.add(0, s);
            if (i >= 7 && i < 20) {
                middleTestList.add(0, s);
            }
            if (i == 20) {
                sizeOneTestList.add(0, s);
            }
            if (i >= 0 && i < 8) {
                beginningTestList.add(0, s);
            }
            if (i >= 21 && i < 26) {
               endTestList.add(0, s);
            }
        }
    }

    /**
     * Tests a typical subset case
     */
    @Test
    public void testNormalSubset() {
        List<Sighting> newList = list.getSubsetData(7, 20);
        Collections.reverse(newList);
        assertEquals("GetSubsetData failed in a typical case where the" +
                    "subset is well within the bounds of the List", middleTestList, newList);
    }
    /**
     * Tests a subset case at the beginning of the list
     */
    @Test
    public void testBeginningSubset() {
        List<Sighting> newList = list.getSubsetData(0, 8);
        Collections.reverse(newList);
        assertEquals("GetSubsetData failed in a typical case where the" +
                     "subset is at the beginning of the List", beginningTestList, newList);
    }
    /**
     * Tests a subset case at the end of the list
     */
    @Test
    public void testEndSubset() {
        List<Sighting> newList = list.getSubsetData(21, 26);
        Collections.reverse(newList);
        assertEquals("GetSubsetData failed in a typical case where the" +
                    "subset is at the end of the List", endTestList, newList);
    }
    /**
     * Tests a subset equivalent to the List's entire size
     */
    @Test
    public void testWholeSubset() {
        List<Sighting> newList = list.getSubsetData(0, 26);
        Collections.reverse(newList);
        assertEquals("GetSubsetData failed in a case where the" +
                "subset is equivalent to the entire list", fullTestList, newList);
    }
    /**
     * Tests a subset case with only one element
     */
    @Test
    public void testSizeOneSubset() {
        List<Sighting> newList = list.getSubsetData(20, 21);
        Collections.reverse(newList);
        assertEquals("GetSubsetData failed in a case where the" +
                "subset has only one element", sizeOneTestList, newList);
    }
    /**
     * Tests an empty subset
     */
    @Test
    public void testEmptySubset () {
        List<Sighting> newList = list.getSubsetData(20, 20);
        Collections.reverse(newList);
        assertEquals("GetSubsetData failed in a case where the" +
                "subset is zero", emptyTestList, newList);
    }
    /**
     * Tests a subset case with illegal indexes
     */
    @Test
    public void testIncorrectSubset () {
        List<Sighting> newList = list.getSubsetData(20, 7);
        Collections.reverse(newList);
        assertEquals("GetSubsetData failed in a case where the" +
                "subset is invalid", emptyTestList, newList);
    }

}