package com.parakrama.calq;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class LengthConverterActivity extends AppCompatActivity {

    private EditText etValue;
    private Spinner spFrom, spTo;
    private TextView tvResult;

    private HashMap<String, Double> conversionRates;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_converter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Length Converter");
        }

        etValue = findViewById(R.id.etValue);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);
        tvResult = findViewById(R.id.tvResult);

        conversionRates = new HashMap<>();
        conversionRates.put("Meter", 1.0);
        conversionRates.put("Kilometer", 1000.0);
        conversionRates.put("Centimeter", 0.01);
        conversionRates.put("Millimeter", 0.001);
        conversionRates.put("Inch", 0.0254);
        conversionRates.put("Foot", 0.3048);
        conversionRates.put("Yard", 0.9144);
        conversionRates.put("Mile", 1609.34);

        btnBack = findViewById(R.id.BtnBack);

        btnBack.setOnClickListener(v -> finish());

        String[] units = conversionRates.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);

        etValue.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                convertLength();
            }
        });

        spFrom.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) { convertLength(); }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spTo.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) { convertLength(); }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private void convertLength() {
        String valueStr = etValue.getText().toString();
        if (valueStr.isEmpty()) {
            tvResult.setText("");
            return;
        }

        double value = Double.parseDouble(valueStr);
        String from = spFrom.getSelectedItem().toString();
        String to = spTo.getSelectedItem().toString();

        double result = value * conversionRates.get(from) / conversionRates.get(to);
        tvResult.setText(String.format("%.4f %s", result, to));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
