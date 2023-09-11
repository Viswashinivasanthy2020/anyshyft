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

import com.demo.anyshyft.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Personaldetails1 extends AppCompatActivity {
    private static final int REQUEST_GET_SINGLE_FILE = 1;
    private Uri filePath;
    ImageView img_userimage;
    Toolbar topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetails1);
        img_userimage=(ImageView) findViewById(R.id.userimage);
        topbar = findViewById(R.id.topAppBar);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void click_savenext(View v)
    {
        Intent in=new Intent(Personaldetails1.this,Personaldetails2.class);
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

                    Log.d("filepath",filePath.toString());
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
    private void postlicence_state() {

        Thread thread = new Thread(() -> {
            try {
                URL url = new URL("https://jobpazi.in/anyshyft1//api/v1/nurse/get-licenseTypes");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("api_key", "client_credentials");
                jsonParam.put("vendorId", "1002");
                jsonParam.put("client_id", "1002");
                jsonParam.put("client_secret", "3D9350A8455D09218C3F5362D36A8329184282C55BED4B95FAA5D540534A2FCF021C96E15DF065EB5BC405E2B9E973905F6A251F52DB903D0AFFA1C9B5EEDB35");


                Log.i("JSON", jsonParam.toString());

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());
                BufferedReader br = null;
                if (100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String response;
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    response = sb.toString();
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(jsonObject);
                    SharedPreferences.Editor myEdit = sharedPref.edit();
                    access_token= jsonObject.getJSONObject("model").getString("access_token");
                    //  myEdit.putString("access_token",access_token);
                    // myEdit.apply();
                    //   Log.d(">>>>>>>>", response);
                    Log.d(">>>>>>>>", access_token);

                }
                else {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//                    Toast.makeText(getApplicationContext(), "Internal Error"+ br.toString(), Toast.LENGTH_LONG).show();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        thread.start();
    }

}