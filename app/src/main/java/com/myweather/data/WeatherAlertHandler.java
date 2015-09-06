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
            //URL oracle = new URL("http://weatherpennapps.cloudapp.net/weatheritems/rest/witems?wkey=" + c + "&tval=" + num);
            URL oracle = new URL("http://10.59.86.55:8080/weatheritems/rest/witems?wkey=" + c + "&tval=" + num);
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
    }
}
