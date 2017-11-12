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

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class SightingListDateRangeTest {

    private static SightingList real;
    private List<Sighting> fullTestList;
    private List<Sighting> emptyTestList;
    private List<Sighting> halfTestList;
    private List<Sighting> sizeOneTestList;

    @Before
    public void setUp() throws Exception {
        DatabaseReference mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        real = SightingList.create_Instance();

        fullTestList = new ArrayList<>();
        emptyTestList = new ArrayList<>();
        halfTestList = new ArrayList<>();
        sizeOneTestList = new ArrayList<>();
        real.reset();
        for (int i = 0; i < 26; i++) {
            Sighting s = new Sighting(i, "0", "test", "30000", "" + ('a' + i),
                    "" + ('a' + i + 32), Borough.NONE, 0, 0, i);
            real.addSighting(s);
            fullTestList.add(s);
            if (i >= 13) {
                halfTestList.add(s);
            }
            if (i == 25) {
                sizeOneTestList.add(s);
            }
        }
    }

    @Test
    public void testFullListRange() {
        List<Sighting> fullList = real.getDateRangeDate(0, 26);
        assertEquals(fullTestList.size(), fullList.size());
    }

    @Test
    public void testHalfListRange() {
        List<Sighting> halfList = real.getDateRangeDate(13, 27);
        assertEquals(halfTestList.size(), halfList.size());
    }

    @Test
    public void testOneElementList() {
        List<Sighting> lastEle = real.getDateRangeDate(25, 27);
        assertEquals(sizeOneTestList.size(), lastEle.size());
    }

}
