package com.example.henrik.actualappmidyear;

/**
 * Created by Henrik on 1/18/2018.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Function {

    // Project Created by Ferdousur Rahman Shajib
    // www.androstock.com

    private static final String BITTREX_URL = "https://bittrex.com/api/v1.1/public/getticker";


    public interface AsyncResponse {

        void processFinish(boolean success, String message, String ticks);
    }





    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            try {
                jsonWeather = getBitJSON(params[0]);
            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }


            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if(json != null){
                    boolean success = json.getBoolean("success");
                    String message = json.getString("message");
                    String result= json.getJSONArray("result").getJSONObject(0).getString("Last");

                    delegate.processFinish(success,message,result);

                }
            } catch (JSONException e) {
                //Log.e(LOG_TAG, "Cannot process JSON results", e);
            }



        }
    }






    public static JSONObject getBitJSON(String market){
        try {
            URL url = new URL(String.format(BITTREX_URL, market));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful

            return data;
        }catch(Exception e){
            return null;
        }
    }




}
