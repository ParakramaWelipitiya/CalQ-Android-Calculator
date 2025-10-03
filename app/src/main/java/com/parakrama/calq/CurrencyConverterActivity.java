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

public class CurrencyConverterActivity extends AppCompatActivity {

    private EditText etAmount;
    private Spinner spFrom, spTo;
    private TextView tvResult;

    private HashMap<String, Double> conversionRates;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Currency Converter");
        }

        etAmount = findViewById(R.id.etAmount);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);
        tvResult = findViewById(R.id.tvResult);

        conversionRates = new HashMap<>();
        conversionRates.put("USD", 1.0);
        conversionRates.put("EUR", 0.93);
        conversionRates.put("GBP", 0.81);
        conversionRates.put("JPY", 144.0);
        conversionRates.put("LKR", 360.0);

        btnBack = findViewById(R.id.BtnBack);

        btnBack.setOnClickListener(v -> finish());

        String[] currencies = conversionRates.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                convertCurrency();
            }
        });

        spFrom.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) { convertCurrency(); }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spTo.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) { convertCurrency(); }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private void convertCurrency() {
        String amountStr = etAmount.getText().toString();
        if (amountStr.isEmpty()) {
            tvResult.setText("");
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String from = spFrom.getSelectedItem().toString();
        String to = spTo.getSelectedItem().toString();

        double result = amount / conversionRates.get(from) * conversionRates.get(to);
        tvResult.setText(String.format("%.4f %s", result, to));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
