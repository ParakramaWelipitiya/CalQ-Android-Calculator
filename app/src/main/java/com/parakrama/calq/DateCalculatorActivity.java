package com.parakrama.calq;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateCalculatorActivity extends AppCompatActivity {

    private Button btnStartDate, btnEndDate, btnBaseDate, btnCalculateNewDate;
    private TextView tvDateDiffResult, tvNewDateResult;
    private EditText etDays;

    private Calendar startDate, endDate, baseDate;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_calculator);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Date Calculator");
        }

        btnStartDate = findViewById(R.id.btnStartDate);
        btnEndDate = findViewById(R.id.btnEndDate);
        tvDateDiffResult = findViewById(R.id.tvDateDiffResult);

        btnBaseDate = findViewById(R.id.btnBaseDate);
        btnCalculateNewDate = findViewById(R.id.btnCalculateNewDate);
        tvNewDateResult = findViewById(R.id.tvNewDateResult);
        etDays = findViewById(R.id.etDays);

        btnStartDate.setOnClickListener(v -> showDatePicker(true));
        btnEndDate.setOnClickListener(v -> showDatePicker(false));
        btnBaseDate.setOnClickListener(v -> showBaseDatePicker());

        btnCalculateNewDate.setOnClickListener(v -> calculateNewDate());

        btnBack = findViewById(R.id.BtnBack);

        btnBack.setOnClickListener(v -> finish());
    }

    private void showDatePicker(boolean isStart) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);
                    if (isStart) {
                        startDate = selected;
                        btnStartDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selected.getTime()));
                    } else {
                        endDate = selected;
                        btnEndDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selected.getTime()));
                    }
                    calculateDateDifference();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private void calculateDateDifference() {
        if (startDate != null && endDate != null) {
            long diffInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            tvDateDiffResult.setText("Difference: " + days + " day(s)");
        }
    }

    private void showBaseDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    baseDate = Calendar.getInstance();
                    baseDate.set(year, month, dayOfMonth);
                    btnBaseDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(baseDate.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private void calculateNewDate() {
        if (baseDate != null && !etDays.getText().toString().isEmpty()) {
            int daysToAdd = Integer.parseInt(etDays.getText().toString());
            Calendar newDate = (Calendar) baseDate.clone();
            newDate.add(Calendar.DAY_OF_MONTH, daysToAdd);
            Date result = newDate.getTime();
            tvNewDateResult.setText("New Date: " + new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(result));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
