package com.parakrama.calq;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class TermsActivity extends AppCompatActivity {

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        btnBack = findViewById(R.id.BtnBack);

        btnBack.setOnClickListener(v -> finish());
    }
}
