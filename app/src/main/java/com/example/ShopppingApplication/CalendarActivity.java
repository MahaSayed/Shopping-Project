package com.example.ShopppingApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendar = (CalendarView)findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView calendarView, int year, int month, int day) {
                // Add 1 in month because month
                // index is start with 0
                String date = day + "/" + (month + 1) + "/" + year;

                    Intent i = new Intent();
                    i.putExtra("date" , date);
                    setResult(Activity.RESULT_OK , i);

                finish();

            }
        });
    }
}
