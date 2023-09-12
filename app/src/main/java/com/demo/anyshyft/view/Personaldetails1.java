package com.demo.anyshyft.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.demo.anyshyft.HttpPostRequestTask;
import com.demo.anyshyft.R;
import com.demo.anyshyft.databinding.ActivityPersonaldetails1Binding;
import com.demo.anyshyft.model.Personaldetails1DataModel;
import com.demo.anyshyft.viewmodel.Personaldetails1ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Personaldetails1 extends AppCompatActivity {
    private static final int REQUEST_GET_SINGLE_FILE = 1;
    private Uri filePath;
    ImageView img_userimage;
    Toolbar topbar;
    List<String> licenseTypes = new ArrayList<>();
    Spinner licensespinner;
    EditText txtname,txtmail,txt_phoneno,txt_pwd;
    private Personaldetails1ViewModel personaldetails1ViewModel;
    private ActivityPersonaldetails1Binding activityPersonaldetails1Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPersonaldetails1Binding= DataBindingUtil.setContentView(Personaldetails1.this,R.layout.activity_personaldetails1);
        personaldetails1ViewModel = androidx.lifecycle.ViewModelProviders.of(this).get(Personaldetails1ViewModel.class);
        activityPersonaldetails1Binding.setLifecycleOwner(this);
        activityPersonaldetails1Binding.setPersonal1(personaldetails1ViewModel);
        img_userimage = (ImageView) findViewById(R.id.userimage);
        txtname=findViewById(R.id.name);
        txtmail=findViewById(R.id.mail);
        txt_phoneno=findViewById(R.id.phoneno);
        txt_pwd=findViewById(R.id.edit_password);

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
        personaldetails1ViewModel.getPersonal1DatamodelMutableLiveData().observe(this, new Observer<Personaldetails1DataModel>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onChanged(Personaldetails1DataModel dataModel) {
                if(TextUtils.isEmpty(Objects.requireNonNull(txtname.getText())))
                {
                    activityPersonaldetails1Binding.name.setError("name is required");
                }
                else  if(TextUtils.isEmpty(Objects.requireNonNull(txtmail.getText())))
                {
                    activityPersonaldetails1Binding.mail.setError("mail is required");
                }
                else  if(TextUtils.isEmpty(Objects.requireNonNull(txt_phoneno.getText())))
                {
                    activityPersonaldetails1Binding.phoneno.setError("Phone number is required");
                }
                else  if(TextUtils.isEmpty(Objects.requireNonNull(txt_pwd.getText())))
                {
                    activityPersonaldetails1Binding.editPassword.setError("Password is required");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Profile Saved Successfully", Toast.LENGTH_SHORT | Gravity.CENTER).show();
                    Intent in = new Intent(Personaldetails1.this, Personaldetails2.class);
                    startActivity(in);
                }
            }
        });

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

}