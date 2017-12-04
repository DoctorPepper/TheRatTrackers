package com.lead.rattrackerapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lead.rattrackerapp.Model.Sightings.Borough;
import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for SightingList's size() methods
 *
 * Created by power on 12/3/2017.
 */

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class SightingListSize {

    private static SightingList list;
    private static SightingList list2;
    private static SightingList list3;
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
        list2 = SightingList.create_Instance();
        list3 = SightingList.create_Instance();
        for (int i = 0; i < 26; i++) {
            Sighting s = new Sighting(i, "11/11/2017", "Street", "30000", "Town",
                    "Atlanta", Borough.NONE, 0, 0, 0);
            list.addSighting(s);
        }
        for (int i = 0; i < 15; i++) {
            Sighting s = new Sighting(i, "11/11/2017", "Street", "30000", "Town",
                    "Atlanta", Borough.NONE, 0, 0, 0);
            list2.addSighting(s);
        }
    }
    /**
     * Tests the size
     */
    @Test
    public void testSize1() {
        assertEquals(list.size(), 26);
    }

    /**
     * Tests the size
     */
    @Test
    public void testSize2() {
        assertEquals(list2.size(), 15);
    }

    /**
     * Tests the size
     */
    @Test
    public void testSize3() {
        assertEquals(list3.size(), 0);
    }

}
