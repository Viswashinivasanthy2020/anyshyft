package com.demo.anyshyft.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.anyshyft.HttpPostRequestTask;
import com.demo.anyshyft.model.Personaldetails2DataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Personaldetails2ViewModel extends ViewModel {
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<Personaldetails2DataModel> personal2DatamodelMutableLiveData;
    public MutableLiveData<Personaldetails2DataModel> getPersonal2DatamodelMutableLiveData() {
        if(personal2DatamodelMutableLiveData==null)
        {
            personal2DatamodelMutableLiveData= new MutableLiveData<>();
        }
        return personal2DatamodelMutableLiveData;
    }
    public void onClick(View v) {
        Personaldetails2DataModel profileDatamodel = new Personaldetails2DataModel(address.getValue());
        personal2DatamodelMutableLiveData.setValue(profileDatamodel);
        try {
            postData_SaveNext();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void postData_SaveNext() throws JSONException {

        String apiUrl = "https://jobpazi.in/anyshyft1/api/v1/nurse/step2";
        Map<String, String> formData = new HashMap<>();
        formData.put("api_key", "123");
        formData.put("src_step", "step2");
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
