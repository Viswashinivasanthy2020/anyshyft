package com.demo.anyshyft.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.anyshyft.HttpPostRequestTask;
import com.demo.anyshyft.model.Personaldetails1DataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Personaldetails1ViewModel extends ViewModel {
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<Personaldetails1DataModel> personal1DatamodelMutableLiveData;
    public MutableLiveData<Personaldetails1DataModel> getPersonal1DatamodelMutableLiveData() {
        if(personal1DatamodelMutableLiveData==null)
        {
            personal1DatamodelMutableLiveData= new MutableLiveData<>();
        }
        return personal1DatamodelMutableLiveData;
    }
    public void onClick(View v)  {
        Personaldetails1DataModel profileDatamodel=new  Personaldetails1DataModel(name.getValue());
        personal1DatamodelMutableLiveData.setValue(profileDatamodel);
        try {
            postData_SaveNext();
        } catch (JSONException e) {
            e.printStackTrace();
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
