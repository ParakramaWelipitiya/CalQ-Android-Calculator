package com.parakrama.calq;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.LinkedHashMap;

public class AreaConverterActivity extends AppCompatActivity {
    private EditText etValue;
    private Spinner spFrom, spTo;
    private TextView tvResult;
    private Button btnConvert;
    private LinkedHashMap<String, Double> map;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_converter);

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
        map.put("Square meter", 1.0);
        map.put("Square kilometer", 1_000_000.0);
        map.put("Square centimeter", 0.0001);
        map.put("Hectare", 10_000.0);
        map.put("Acre", 4046.86);
        map.put("Square foot", 0.092903);

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

                double res = val * map.get(from) / map.get(to);
                tvResult.setText(String.format("%.6f %s", res, to));
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
