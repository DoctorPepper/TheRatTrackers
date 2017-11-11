package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateSelectionScreen extends AppCompatActivity {

    private DatePicker startDateInput;
    private DatePicker endDateInput;
    private String target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection_screen);

        target = getIntent().getStringExtra("destination");

        startDateInput = (DatePicker) findViewById(R.id.date_start_input);
        endDateInput = (DatePicker) findViewById(R.id.date_end_input);
        Button searchButton = (Button) findViewById(R.id.searchSightings);
        Button cancelButton = (Button) findViewById(R.id.cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateSelectionScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLongDateFromPicker(endDateInput) >= getLongDateFromPicker(startDateInput)){
                    if (target.equals("map")) {
                        Intent intent = new Intent(DateSelectionScreen.this, MapsActivity.class);
                        intent.putExtra("start", getLongDateFromPicker(startDateInput));
                        intent.putExtra("end", getLongDateFromPicker(endDateInput));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(DateSelectionScreen.this, GraphActivity.class);
                        intent.putExtra("start", getLongDateFromPicker(startDateInput));
                        intent.putExtra("end", getLongDateFromPicker(endDateInput) + 86400000);
                                    // + 1 day in milliseconds so end date is inclusive
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(DateSelectionScreen.this, R.string.end_date_greater_start_date,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Gets the long value of date from the DatePicker
     *
     * @param datePicker the DatePicker object
     * @return the date of the calender
     */
    private static long getLongDateFromPicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int hour = 0;
        int minute = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        Date date = calendar.getTime();
        String dateString = DateFormat.format("MM/dd/yyyy hh:mm:ss a", date).toString();
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        try {
            Date dateObj = f.parse(dateString);
            return dateObj.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
