package com.parakrama.calq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MoreActivity extends AppCompatActivity {

    private Button btnDateCalc, btnCurrency, btnLength, btnWeight, btnTemperature,
            btnVolume, btnArea, btnSpeed, btnTime, btnPower, btnData, btnPressure, btnAngle;
    private ImageButton btnBack, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        btnDateCalc = findViewById(R.id.DateCalc);
        btnCurrency = findViewById(R.id.Currency);
        btnLength = findViewById(R.id.Length);
        btnWeight = findViewById(R.id.Weight);
        btnTemperature = findViewById(R.id.Temperature);
        btnVolume = findViewById(R.id.Volume);
        btnArea = findViewById(R.id.Area);
        btnSpeed = findViewById(R.id.Speed);
        btnTime = findViewById(R.id.time);
        btnPower = findViewById(R.id.power);
        btnData = findViewById(R.id.Data);
        btnPressure = findViewById(R.id.Pressure);
        btnAngle = findViewById(R.id.Angle);

        btnBack = findViewById(R.id.BtnBack);
        btnSettings = findViewById(R.id.imageButton2);

        btnDateCalc.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, DateCalculatorActivity.class)));
        btnCurrency.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, CurrencyConverterActivity.class)));
        btnLength.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, LengthConverterActivity.class)));
        btnWeight.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, WeightConverterActivity.class)));
        btnTemperature.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, TemperatureConverterActivity.class)));
        btnVolume.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, VolumeConverterActivity.class)));
        btnArea.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, AreaConverterActivity.class)));
        btnSpeed.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, SpeedConverterActivity.class)));
        btnTime.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, TimeConverterActivity.class)));
        btnPower.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, PowerConverterActivity.class)));
        btnData.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, DataConverterActivity.class)));
        btnPressure.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, PressureConverterActivity.class)));
        btnAngle.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, AngleConverterActivity.class)));

        btnSettings.setOnClickListener(v -> startActivity(new Intent(MoreActivity.this, SettingsActivity.class)));

        btnBack.setOnClickListener(v -> finish());
    }
}
