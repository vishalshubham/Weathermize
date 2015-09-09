package com.myweather.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vishal on 05/09/2015.
 */
public class WeatherAlertHandler extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... strings) {
        String urlString=strings[0]; // URL to call

        String wholeLine="";
        Log.d("VC", "Hellllllllllllooooooooooo" + urlString);
        try{
            URL oracle = new URL("http://weatherpennapps.cloudapp.net/weatheritems/rest/witems?wkey=" + "C" + "&tval=" + "2");
            //URL oracle = new URL("http://10.59.86.55:8080/weatheritems/rest/witems?wkey=" + "C" + "&tval=" + "2");
            //Log.d("VC", oracle.toURI().toString());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                wholeLine = wholeLine + inputLine;

            Log.d("VC", "whole lineeeeeeeeeeeeeeeeeee ....................." + wholeLine);

            in.close();
        } catch (Exception e) {
            Log.d("VC", "Mar gaya " + e.getMessage());
            e.printStackTrace();
        }

        return wholeLine;
    }
}
