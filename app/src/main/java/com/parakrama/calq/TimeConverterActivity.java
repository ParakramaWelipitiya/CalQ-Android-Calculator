package com.parakrama.calq;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.LinkedHashMap;

public class TimeConverterActivity extends AppCompatActivity {
    private EditText etValue;
    private Spinner spFrom, spTo;
    private TextView tvResult;
    private Button btnConvert;
    private LinkedHashMap<String, Double> map;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_converter);

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

        map = new LinkedHashMap<>();
        map.put("Second", 1.0);
        map.put("Minute", 60.0);
        map.put("Hour", 3600.0);
        map.put("Day", 86400.0);

        String[] units = map.keySet().toArray(new String[0]);
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
                double result = val * map.get(from) / map.get(to);
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
