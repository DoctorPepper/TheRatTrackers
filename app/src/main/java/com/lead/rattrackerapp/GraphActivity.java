package com.lead.rattrackerapp;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    private Calendar startDate;
    private Calendar endDate;

    private static final String[] mNames = {"Jan", "Feb", "Mar", "April", "May", "June", "July", "Aug",
                        "Sept", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        LineChart lineChart = (LineChart) findViewById(R.id.graph);
        Button backButton = (Button) findViewById(R.id.backButton);

        //initialize calendars
        long start = getIntent().getLongExtra("start", 0);
        long end = getIntent().getLongExtra("end", 0);
        startDate = new GregorianCalendar();
        startDate.setTimeInMillis(start);
        endDate = new GregorianCalendar();
        endDate.setTimeInMillis(end);

        List<Sighting> sightingsToDisplay = SightingList.getInstance().getDateRangeDate(start, end);
        List<Entry> entries = convertSightingListToEntries(sightingsToDisplay);

        LineDataSet dataset = new LineDataSet(entries, "Number of Sightings");
        LineData data = new LineData(dataset);
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawFilled(true);

        lineChart.getLegend().setEnabled(false);
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getAxisLeft().setGranularity(1f);
        lineChart.getAxisRight().setGranularity(1f);
        lineChart.getXAxis().setValueFormatter(new XAxisMonthValueFormatter(getMonthNamesArray()));

        lineChart.moveViewToX(0f);
        //lineChart.moveViewTo(0f, -0.5f, );

        lineChart.setData(data);
        lineChart.animateY(5000);


        lineChart.getDescription().setText("Number of Sightings per Month between "
                + (startDate.get(Calendar.MONTH) + 1) + "/" + startDate.get(Calendar.DAY_OF_MONTH)
                + "/" + startDate.get(Calendar.YEAR)
                + " and "
                + (endDate.get(Calendar.MONTH) + 1) + "/" + endDate.get(Calendar.DAY_OF_MONTH)
                + "/" + endDate.get(Calendar.YEAR));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Creates an array of month and year names within the bounds of the start and end
     * dates that will be the x axis values of the graph
     *
     * @return the array of month and year names
     */
    private String[] getMonthNamesArray() {
        int numMonths = 12 * (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR))
                + (endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH));

        if (numMonths < 2) {
            numMonths = 2;
        }

        String[] monthNames = new String[numMonths];
        for (int i = 0; i < monthNames.length; i++) {
            int currMonth = (startDate.get(Calendar.MONTH) + i) % 12;
            int currYear = ((startDate.get(Calendar.YEAR))) + (startDate.get(Calendar.MONTH) + i) / 12;
            monthNames[i] = mNames[currMonth] + " " + currYear;
        }
        return monthNames;
    }

    /**
     * Creates a list of plot-able entries by counting up the number of sightings by month
     * within the start and end dates
     *
     * @param sightings the list of sightings
     * @return the list of entries to plot
     */
    private List<Entry> convertSightingListToEntries(List<Sighting> sightings) {
        List<Entry> entries = new ArrayList<>();

        int numMonths = 12 * (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR))
                + (endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH));

        if (numMonths < 2) {
            numMonths = 2;
        }

        int startMonth = startDate.get(Calendar.MONTH) + (startDate.get(Calendar.YEAR) * 12);

        SparseIntArray months = new SparseIntArray(numMonths);
        Calendar tempCal = new GregorianCalendar();
        for (Sighting s : sightings) {
            tempCal.setTimeInMillis(s.getLongDate());
            int m = (tempCal.get(Calendar.MONTH) + (tempCal.get(Calendar.YEAR) * 12)) - startMonth;
            if (months.get(m, -1) < 0) {
                months.put(m, 1);
            } else {
                months.put(m, months.get(m) + 1);
            }
        }
        for (int i = 0; i < numMonths; i++) {
            if (months.get(i, -1) >= 0) {
                entries.add(new Entry(i, months.get(i)));
                System.err.println(months.get(i) + " at month " + i);
            } else {
                entries.add(new Entry(i, 0));
                System.err.println(0 + " at month " + i);
            }
        }
        return entries;
    }

    private class XAxisMonthValueFormatter implements IAxisValueFormatter {

        private final String[] monthNames;

        XAxisMonthValueFormatter(String[] values) {
            this.monthNames = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return monthNames[(int) value];
        }
    }
}
