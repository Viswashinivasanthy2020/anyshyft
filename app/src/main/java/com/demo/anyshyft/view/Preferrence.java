package com.demo.anyshyft.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.demo.anyshyft.R;

import java.util.Calendar;

public class Preferrence extends AppCompatActivity {
    Toolbar topbar;
    TextView txt_date,txt_mon,txt_tue,txt_wed,txt_thu,txt_fri,txt_sat,txt_sun;
    Calendar calendar;
    int year,month,day;
    int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,flag7=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferrence);
        topbar = findViewById(R.id.topAppBar);
        txt_date = findViewById(R.id.date);
        txt_mon = findViewById(R.id.txt_m);
        txt_tue = findViewById(R.id.txt_tu);
        txt_wed = findViewById(R.id.txt_w);
        txt_thu = findViewById(R.id.txt_th);
        txt_fri = findViewById(R.id.txt_f);
        txt_sat = findViewById(R.id.txt_sa);
        txt_sun = findViewById(R.id.txt_su);

        calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void open_calender(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Preferrence.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                txt_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
    public void onClick_perference(View v)
    {
        Intent in=new Intent(Preferrence.this,Personalinformation.class);
        startActivity(in);
    }
public void select_date(View v)
{
    int i = v.getId();
    switch (i) {
        case R.id.txt_m:
            if(flag1==0)
            {
                txt_mon.setTextColor(Color.RED);
                flag1=1;
            }
            else
            {
                txt_mon.setTextColor(Color.BLACK);
                flag1=0;
            }
            break;
        case R.id.txt_tu:
            if(flag2==0)
            {
                txt_tue.setTextColor(Color.RED);
                flag2=1;
            }
            else
            {
                txt_tue.setTextColor(Color.BLACK);
                flag2=0;
            }
            break;
        case R.id.txt_w:
            if(flag3==0)
            {
                txt_wed.setTextColor(Color.RED);
                flag3=1;
            }
            else
            {
                txt_wed.setTextColor(Color.BLACK);
                flag3=0;
            }
            break;
        case R.id.txt_th:
            if(flag4==0)
            {
                txt_thu.setTextColor(Color.RED);
                flag4=1;
            }
            else
            {
                txt_thu.setTextColor(Color.BLACK);
                flag4=0;
            }
            break;
        case R.id.txt_f:
            if(flag5==0)
            {
                txt_fri.setTextColor(Color.RED);
                flag5=1;
            }
            else
            {
                txt_fri.setTextColor(Color.BLACK);
                flag5=0;
            }
            break;
        case R.id.txt_sa:
            if(flag6==0)
            {
                txt_sat.setTextColor(Color.RED);
                flag6=1;
            }
            else
            {
                txt_sat.setTextColor(Color.BLACK);
                flag6=0;
            }
            break;
        case R.id.txt_su:
            if(flag7==0)
            {
                txt_sun.setTextColor(Color.RED);
                flag7=1;
            }
            else
            {
                txt_sun.setTextColor(Color.BLACK);
                flag7=0;
            }
            break;
    }
}
}