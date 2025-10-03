package com.parakrama.calq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {
    private RadioGroup rgTheme;
    private Button btnFeedback, btnGithub;
    private TextView tvTerms, tvPrivacy;
    private ImageButton btnBack;
    private SharedPreferences prefs;
    private static final String PREFS = "calq_prefs";
    private static final String THEME_KEY = "theme_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int saved = prefs.getInt(THEME_KEY, 1);

        if (saved == 0) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        else if (saved == 1) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rgTheme = findViewById(R.id.rgTheme);
        btnFeedback = findViewById(R.id.btnFeedback);
        btnGithub = findViewById(R.id.btnGithub);
        btnBack = findViewById(R.id.BtnBack);
        tvTerms = findViewById(R.id.tvTerms);
        tvPrivacy = findViewById(R.id.tvPrivacy);

        if (saved == 0) rgTheme.check(R.id.rbFollow);
        else if (saved == 1) rgTheme.check(R.id.rbLight);
        else rgTheme.check(R.id.rbDark);

        rgTheme.setOnCheckedChangeListener((group, checkedId) -> {
            int mode = 1;
            if (checkedId == R.id.rbFollow) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                mode = 0;
            } else if (checkedId == R.id.rbLight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                mode = 1;
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                mode = 2;
            }
            prefs.edit().putInt(THEME_KEY, mode).apply();
        });

        btnFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"parakramawelipitiya00@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "CalQ Feedback");
            startActivity(intent);
        });

        btnGithub.setOnClickListener(v -> {
            String url = "https://github.com/ParakramaWelipitiya";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });

        btnBack.setOnClickListener(v -> finish());

        tvTerms.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, TermsActivity.class);
            startActivity(intent);
        });

        tvPrivacy.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, PrivacyActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
