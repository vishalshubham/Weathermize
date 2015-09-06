package com.myweather.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vishal on 05/09/2015.
 */
public class WeatherAlertHandler {

    public String getAlertThings(final Context context, String c, String num){

        String wholeLine="";
        Log.d("VC", "Hellllllllllllooooooooooo");
        try{
            URL oracle = new URL("http://weatherpennapps.cloudapp.net/weatheritems/rest/witems?wkey=" + c + "&tval=" + num);
            //URL oracle = new URL("http://10.59.86.55/weatheritems/rest/witems?wkey=" + c + "&tval=" + num);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                wholeLine = wholeLine + inputLine;

            Log.d("VC", "whole line ....................." + wholeLine);

            in.close();
        } catch (Exception e) {
            Log.d("VC", "Mar gaya " + e.getMessage());
            e.printStackTrace();
        }

        return wholeLine;
        /*
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get("http://weatherpennapps.cloudapp.net/weatheritems/rest/witems?wkey=" + c + "&tval=" + num, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    JSONArray obj = null;
                    String s = new String(responseBody);
                    obj = new JSONArray(s);

                    final ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

                    Log.d("VC", "S String is : " + s);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(context, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(context, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code other than '200'
            *//* public void onFailure(int statusCode, Throwable error, String content){}*//*
        });*/
    }
}
