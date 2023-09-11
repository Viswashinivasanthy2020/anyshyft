package com.demo.anyshyft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.demo.anyshyft.fragment.Account_fragment;
import com.demo.anyshyft.fragment.Home_fragment;
import com.demo.anyshyft.fragment.Message_fragment;
import com.demo.anyshyft.fragment.Myjob_fragrament;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.account);
    }
    Home_fragment home_fragment = new Home_fragment();
    Myjob_fragrament myjob_fragrament = new Myjob_fragrament();
    Message_fragment message_fragment = new Message_fragment();
    Account_fragment account_fragment = new Account_fragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, home_fragment)
                        .commit();
                return true;

            case R.id.myjob:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, myjob_fragrament)
                        .commit();
                return true;

            case R.id.message:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, message_fragment)
                        .commit();
                return true;
            case R.id.account:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, account_fragment)
                        .commit();
                return true;
        }
        return false;
    }
}