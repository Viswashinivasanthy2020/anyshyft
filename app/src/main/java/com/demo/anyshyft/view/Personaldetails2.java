package com.demo.anyshyft.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.demo.anyshyft.HttpPostRequestTask;
import com.demo.anyshyft.R;
import com.demo.anyshyft.databinding.ActivityPersonaldetails1Binding;
import com.demo.anyshyft.databinding.ActivityPersonaldetails2Binding;
import com.demo.anyshyft.model.Personaldetails1DataModel;
import com.demo.anyshyft.model.Personaldetails2DataModel;
import com.demo.anyshyft.viewmodel.Personaldetails2ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Personaldetails2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Toolbar topbar;
    String[] gender_list={"Male","Female"};
    int img_icon[] = {R.drawable.ic_baseline_male_24, R.drawable.ic_outline_female_24 };
    String idvalue;
    List<String> statenames = new ArrayList<>();
    List<String> cityname = new ArrayList<>();
    Spinner stateSpinner,citySpinner;
    private Personaldetails2ViewModel personaldetails2ViewModel;
    private ActivityPersonaldetails2Binding activityPersonaldetails2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPersonaldetails2Binding= DataBindingUtil.setContentView(Personaldetails2.this,R.layout.activity_personaldetails2);
        personaldetails2ViewModel = androidx.lifecycle.ViewModelProviders.of(this).get(Personaldetails2ViewModel.class);
        activityPersonaldetails2Binding.setLifecycleOwner(this);
        activityPersonaldetails2Binding.setPersonal2(personaldetails2ViewModel);
        topbar = findViewById(R.id.topAppBar);
        stateSpinner=findViewById(R.id.state);
        citySpinner=findViewById(R.id.city);
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

        try {
            postData_statedetails("https://jobpazi.in/anyshyft1/api/v1/nurse/get-states","233");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.d("idvalue",idvalue);
                    postData_citiesdetail(idvalue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        personaldetails2ViewModel.getPersonal2DatamodelMutableLiveData().observe(this, new Observer<Personaldetails2DataModel>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onChanged(Personaldetails2DataModel dataModel) {
            /*    if(TextUtils.isEmpty(Objects.requireNonNull(dataModel).getAddress()))
                {
                    activityPersonaldetails2Binding.staddress.setError("This field is required");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Profile Saved Successfully", Toast.LENGTH_SHORT | Gravity.CENTER).show();
                    Intent in = new Intent(Personaldetails2.this, Personalinformation.class);
                    startActivity(in);
                }

             */
                Toast.makeText(getApplicationContext(), "Profile Saved Successfully", Toast.LENGTH_SHORT | Gravity.CENTER).show();
                Intent in = new Intent(Personaldetails2.this, Personalinformation.class);
                startActivity(in);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void postData_statedetails(String url,String countrycode) throws JSONException {

        String apiUrl = url;
        Map<String, String> formData = new HashMap<>();
        formData.put("api_key", "123");
        formData.put("country_id", countrycode);
        HttpPostRequestTask httpPostRequestTask = new HttpPostRequestTask(formData);

        String resultString = null;
        try {
            resultString = httpPostRequestTask.execute(apiUrl).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultString != null) {
            // Use the resultString as needed
            Log.d("Response", "Result: " + resultString);
            try {
                JSONObject jsonResponse = new JSONObject(resultString);
                int status = jsonResponse.getInt("status");
                if (status == 200) {
                    JSONArray dataArray = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        idvalue=dataObject.getString("id");
                        statenames.add(dataObject.getString("name"));
                       // Log.d("888888",statenames.toString());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Personaldetails2.this,android.R.layout.simple_spinner_item , statenames);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    stateSpinner.setAdapter(spinnerArrayAdapter);

                } else {

                    String message = jsonResponse.getString("message");
                    Log.e("JSON Parsing", "Status not 200: " + message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON Parsing", "Error parsing JSON");
            }

        } else {

            Log.e("Response", "No response data");
        }


    }
    private void postData_citiesdetail(String id) throws JSONException {

        String apiUrl = "https://jobpazi.in/anyshyft1/api/v1/nurse/get-cities";
        Map<String, String> formData = new HashMap<>();
        formData.put("api_key", "123");
        formData.put("state_id",id);
        HttpPostRequestTask httpPostRequestTask = new HttpPostRequestTask(formData);

        String resultString = null;
        try {
            resultString = httpPostRequestTask.execute(apiUrl).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultString != null) {
            // Use the resultString as needed
            Log.d("Response", "Result: " + resultString);
            try {
                JSONObject jsonResponse = new JSONObject(resultString);
                int status = jsonResponse.getInt("status");
                if (status == 200) {
                    JSONArray dataArray = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        cityname.add(dataObject.getString("name"));

                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Personaldetails2.this,android.R.layout.simple_spinner_item , cityname);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    citySpinner.setAdapter(spinnerArrayAdapter);

                } else {

                    String message = jsonResponse.getString("message");
                    Log.e("JSON Parsing", "Status not 200: " + message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON Parsing", "Error parsing JSON");
            }

        } else {

            Log.e("Response", "No response data");
        }


    }

}