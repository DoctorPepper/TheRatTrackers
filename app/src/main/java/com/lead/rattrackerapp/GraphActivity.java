package com.lead.rattrackerapp;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    LineChart lineChart;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = (LineChart) findViewById(R.id.graph);
        backButton = (Button) findViewById(R.id.backButton);
        long start = getIntent().getLongExtra("start", 0);
        long end = getIntent().getLongExtra("end", 0);
        Calendar st = new GregorianCalendar();
        st.setTimeInMillis(start);
        Calendar en = new GregorianCalendar();
        en.setTimeInMillis(end);
        List<Sighting> sightingsToDisplay = SightingList.getInstance().getDateRangeDate(start, end);
        List<Entry> entries = convertSightingListToEntries(sightingsToDisplay, start, end);

        LineDataSet dataset = new LineDataSet(entries, "Number of Sightings");
        LineData data = new LineData(dataset);
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawFilled(true);
        lineChart.setData(data);
        lineChart.animateY(5000);

        lineChart.getDescription().setText("Number of Sightings per Month between "
                + (st.get(Calendar.MONTH) + 1) + "/" + st.get(Calendar.DAY_OF_MONTH) + "/" + st.get(Calendar.YEAR)
                + " and " + (en.get(Calendar.MONTH) + 1) + "/" + en.get(Calendar.DAY_OF_MONTH) + "/" + en.get(Calendar.YEAR));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Creates and returns a list of graph entries based on a passed in list
     * of sightings
     *
     * @param sightings the list of sightings
     * @return the list of graph entries
     */
    private static List<Entry> convertSightingListToEntries(List<Sighting> sightings,
                                                            long start, long end) {
        List<Entry> entries = new ArrayList<>();

        Calendar startDate = new GregorianCalendar();
        startDate.setTimeInMillis(start);
        Calendar endDate = new GregorianCalendar();
        endDate.setTimeInMillis(end);

        int numMonths = 12 * (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR))
                + (endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH)) + 1;
        int startMonth = startDate.get(Calendar.MONTH) + (startDate.get(Calendar.YEAR) * 12);
        HashMap<Integer, Integer> months = new HashMap<>(numMonths);
        Calendar tempCal = new GregorianCalendar();
        for (Sighting s : sightings) {
            tempCal.setTimeInMillis(s.getLongDate());
            int m = (tempCal.get(Calendar.MONTH) + (tempCal.get(Calendar.YEAR) * 12)) - startMonth;
            if (!(months.containsKey(m))) {
                months.put(m, 1);
            } else {
                months.put(m, months.get(m) + 1);
            }
        }
        for (int i = 0; i < numMonths; i++) {
            if (months.containsKey(i)) {
                entries.add(new Entry(i, months.get(i)));
                System.err.println(months.get(i) + " at month " + i);
            } else {
                entries.add(new Entry(i, 0));
                System.err.println(0 + " at month " + i);
            }
        }
        return entries;
    }
}
