package com.demo.anyshyft.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.anyshyft.R;

public class Personalinformation extends AppCompatActivity {
    Toolbar topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation);
        topbar = findViewById(R.id.topAppBar);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void goPersonal(View v)
    {
        Intent in=new Intent(Personalinformation.this,Personaldetails1.class);
        startActivity(in);
    }
    public void goEducation(View v)
    {
        Intent in=new Intent(Personalinformation.this,Educationaldetails.class);
        startActivity(in);
    }
    public void goPreference(View v)
    {
        Intent in=new Intent(Personalinformation.this,Preferrence.class);
        startActivity(in);
    }

}