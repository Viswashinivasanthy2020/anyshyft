package com.demo.anyshyft;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import java.util.Map;

public class HttpPostRequestTask extends AsyncTask<String, Void, String> {

    private Map<String, String> formData;

    public HttpPostRequestTask(Map<String, String> formData) {
        this.formData = formData;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String result = null;

        try {
            // Create a URL object with the API endpoint
            URL url = new URL(params[0]);

            // Open a connection to the URL
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            // Set timeout for the connection
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            // Enable input/output streams for POST data
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            // Create a query string from the formData map
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(entry.getKey());
                postData.append('=');
                postData.append(entry.getValue());
            }

            // Write the POST data to the output stream
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(postData.toString());
            outputStream.flush();
            outputStream.close();

            // Connect to the server
            urlConnection.connect();

            // Check if the response code is 200 (OK)
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Read the response from the server
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
            } else {
                Log.e("HttpPostRequestTask", "HTTP error code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the connections and the reader
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the response data here
        if (result != null) {
            Log.d("HttpPostRequestTask", "Response: " + result);
            // You can parse and process the response as needed
        } else {
            Log.e("HttpPostRequestTask", "No response data");
        }
    }
}
