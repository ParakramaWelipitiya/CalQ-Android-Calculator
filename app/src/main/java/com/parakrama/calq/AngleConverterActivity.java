package com.parakrama.calq;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AngleConverterActivity extends AppCompatActivity {
    private EditText etValue;
    private Spinner spFrom, spTo;
    private TextView tvResult;
    private Button btnConvert;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angle_converter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etValue = findViewById(R.id.etValue);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);
        tvResult = findViewById(R.id.tvResult);
        btnConvert = findViewById(R.id.btnConvert);
        btnBack = findViewById(R.id.BtnBack);

        btnBack.setOnClickListener(v -> finish());

        String[] units = {"Degree", "Radian", "Gradian"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);

        btnConvert.setOnClickListener(v -> {
            String input = etValue.getText().toString().trim();
            if (input.isEmpty()) {
                tvResult.setText("Enter value");
                return;
            }

            try {
                double val = Double.parseDouble(input);
                String from = spFrom.getSelectedItem().toString();
                String to = spTo.getSelectedItem().toString();

                double deg;
                if (from.equals("Degree")) {
                    deg = val;
                } else if (from.equals("Radian")) {
                    deg = Math.toDegrees(val);
                } else {
                    deg = val * 0.9;
                }

                double result;
                if (to.equals("Degree")) {
                    result = deg;
                } else if (to.equals("Radian")) {
                    result = Math.toRadians(deg);
                } else {
                    result = deg / 0.9;
                }

                tvResult.setText(String.format("%.6f %s", result, to));
            } catch (NumberFormatException e) {
                tvResult.setText("Invalid number");
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
