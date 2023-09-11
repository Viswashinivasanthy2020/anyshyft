package com.demo.anyshyft.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.demo.anyshyft.R;

public class Educationaldetails extends AppCompatActivity {
    Toolbar topbar;
    SeekBar seekBar;
    TextView txt_professional,txt_total;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educationaldetails);
        topbar = findViewById(R.id.topAppBar);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        txt_professional=(TextView)findViewById(R.id.professional);
        txt_total=(TextView)findViewById(R.id.total);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void onClick_education(View v)
    {
        Intent in=new Intent(Educationaldetails.this,Personalinformation.class);
        startActivity(in);
    }
    public void click_addmore(View v)
    {
        if(count<3) {
            count++;
            seekBar.setProgress(count);
            txt_professional.setText(count + " Professional Details");
            txt_total.setText("0" + count + "/03");
        }

    }
}