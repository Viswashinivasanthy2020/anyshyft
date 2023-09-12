package com.demo.anyshyft.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.demo.anyshyft.ApiClient;
import com.demo.anyshyft.HttpPostRequestTask;
import com.demo.anyshyft.R;
import com.demo.anyshyft.api.ApiService;
import com.demo.anyshyft.model.LicenceFormdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Personaldetails1 extends AppCompatActivity {
    private static final int REQUEST_GET_SINGLE_FILE = 1;
    private Uri filePath;
    ImageView img_userimage;
    Toolbar topbar;
    List<String> licenseTypes = new ArrayList<>();
    Spinner licensespinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetails1);
        img_userimage = (ImageView) findViewById(R.id.userimage);
        licensespinner=findViewById(R.id.licencestate);
        topbar = findViewById(R.id.topAppBar);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        try {
            postData_license();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void click_savenext(View v) throws JSONException {
        postData_SaveNext();
        Intent in = new Intent(Personaldetails1.this, Personaldetails2.class);
        startActivity(in);
    }

    public void openimage(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    filePath = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(filePath);
                    if (path != null) {
                        File f = new File(path);
                        filePath = Uri.fromFile(f);
                    }
                    // Set the image in ImageView

                    Log.d("filepath", filePath.toString());
                    img_userimage.setImageURI(filePath);

                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }

    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void postData_license() throws JSONException {

        String apiUrl = "https://jobpazi.in/anyshyft1//api/v1/nurse/get-licenseTypes";
        Map<String, String> formData = new HashMap<>();
        formData.put("api_key", "123");
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
                        licenseTypes.add(dataObject.getString("title"));
                        Log.d("888888",licenseTypes.toString());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Personaldetails1.this,android.R.layout.simple_spinner_item , licenseTypes);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    licensespinner.setAdapter(spinnerArrayAdapter);

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
    private void postData_SaveNext() throws JSONException {

        String apiUrl = "https://jobpazi.in/anyshyft1/api/v1/nurse/step1";
        Map<String, String> formData = new HashMap<>();
        formData.put("api_key", "123");
        formData.put("src_step", "step1");
        formData.put("firstname", "test");
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
                    String responsemessage=jsonResponse.getString("message");
                    Log.d("message",responsemessage);
                    Toast.makeText(getApplicationContext(),responsemessage, Toast.LENGTH_SHORT).show();

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