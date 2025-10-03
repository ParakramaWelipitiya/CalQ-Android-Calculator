package com.parakrama.calq;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parakrama.calq.ui.fragments.StandardFragment;
import com.parakrama.calq.ui.fragments.ScientificFragment;
import com.parakrama.calq.ui.fragments.ProgrammerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new StandardFragment())
                    .commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;

                int id = item.getItemId();

                if (id == R.id.nav_standard) {
                    selected = new StandardFragment();
                } else if (id == R.id.nav_scientific) {
                    selected = new ScientificFragment();
                } else if (id == R.id.nav_programmer) {
                    selected = new ProgrammerFragment();
                } else if (id == R.id.nav_more) {
                    startActivity(new Intent(MainActivity.this, MoreActivity.class));
                    return true;
                }

                if (selected != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selected)
                            .commit();
                }

                return true;
            }
        });
    }
}
