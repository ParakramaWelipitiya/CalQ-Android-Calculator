package com.parakrama.calq;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class TemperatureConverterActivity extends AppCompatActivity {
    private EditText etValue;
    private Spinner spFrom, spTo;
    private TextView tvResult;
    private Button btnConvert;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_converter);

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

        String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
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

                double celsius;
                if (from.equals("Celsius")) celsius = val;
                else if (from.equals("Fahrenheit")) celsius = (val - 32.0) * 5.0 / 9.0;
                else celsius = val - 273.15;

                double result;
                if (to.equals("Celsius")) result = celsius;
                else if (to.equals("Fahrenheit")) result = celsius * 9.0 / 5.0 + 32.0;
                else result = celsius + 273.15;

                tvResult.setText(String.format("%.4f %s", result, to));
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
