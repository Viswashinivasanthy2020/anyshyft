package com.demo.anyshyft.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.demo.anyshyft.R;

public class Personaldetails2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Toolbar topbar;
    String[] gender_list={"Male","Female"};
    int img_icon[] = {R.drawable.ic_baseline_male_24, R.drawable.ic_outline_female_24 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetails2);
        topbar = findViewById(R.id.topAppBar);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Spinner spin = (Spinner) findViewById(R.id.gender);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),img_icon,gender_list);
        spin.setAdapter(customAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void onClick_save(View v)
    {
        Intent in=new Intent(Personaldetails2.this,Personalinformation.class);
        startActivity(in);
    }
}