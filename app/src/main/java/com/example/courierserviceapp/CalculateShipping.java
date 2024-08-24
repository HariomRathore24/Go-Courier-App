package com.example.courierserviceapp;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalculateShipping extends AppCompatActivity {

    private EditText editTextDate;
    private ImageView imageViewCalendar;
    private Calendar calendar;

    private EditText editTextTime;
    private ImageView imageViewClock , backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculate_shipping);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextDate = findViewById(R.id.editTextDate);
        imageViewCalendar = findViewById(R.id.imageViewCalendar);
        calendar = Calendar.getInstance();
        editTextTime = findViewById(R.id.editTextTime);
        imageViewClock = findViewById(R.id.imageViewClock);
        backArrow = findViewById(R.id.backArrow);


        // Set an OnClickListener to handle the back action
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your back action here
                onBackPressed(); // This will simulate pressing the back button
            }
        });
        // Initialize Spinner
        Spinner spinner = findViewById(R.id.spinner);

        // Set up click listeners
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        imageViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        imageViewClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.planets_array, // String array resource containing your dropdown options
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    private void updateDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextDate.setText(sdf.format(calendar.getTime()));
    }

    private void showTimePickerDialog() {
        new TimePickerDialog(this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true)
                .show();
    }

    private final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            updateTime();
        }
    };

    private void updateTime() {
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextTime.setText(sdf.format(calendar.getTime()));
    }
}