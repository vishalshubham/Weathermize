package com.myweather;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.myweather.data.WeatherAlertHandler;
import com.myweather.sync.SyncAdapter;

public class MainActivity extends ActionBarActivity implements ForecastFragment.Callback {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;
    private String mLocation;
    String data="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("VC", "WIFIIIIIIIIIIIIII" + getCurrentSsid(getApplicationContext()));

        String oracle = "http://weatherpennapps.cloudapp.net/weatheritems/rest/witems?wkey=" + "C" + "&tval=" + "2";
        new WeatherAlertHandler().execute(oracle);
        Log.d("VC", "Data is : " + data);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while(true) {
                    try {
                        Thread.sleep(3000);
                        if(!checkConnectedToDesiredWifi()){
                            Log.d("VC", "Stoppppppeeedddd");
                            break;
                        }
                        Log.d("VC", "Connected");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                NotificationManager NM=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification(R.drawable.ic_push,"Pushed Notification",System.currentTimeMillis()+10000);
                PendingIntent pending=PendingIntent.getActivity(getApplicationContext(), 0, new Intent(),0);
                notify.setLatestEventInfo(getApplicationContext(), "Pushed", "Notification details",pending);
                NM.notify(0, notify);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());

                mBuilder.setContentIntent(pending).setContentTitle("").setContentText("");
                mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                mBuilder.setLights(Color.RED, 3000, 3000);


                Uri notificationsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                mBuilder.setSound(notificationsound);




                Intent i = new Intent(MainActivity.this, ThingsActivity.class);
                SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
                String str = prefs.getString("WHOLE_LINE", "");
                str = processStr(str);
                Log.d("VC", "-----------------------Str -------------" + str);
                i.putExtra("WHOLE_LINE", str);
                startActivity(i);
            }
        }.execute();

        mLocation = Utility.getPreferredLocation(this);

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.weather_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        ForecastFragment forecastFragment =  ((ForecastFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_forecast));
        forecastFragment.setUseTodayLayout(!mTwoPane);

        SyncAdapter.initializeSyncAdapter(this);
    }

    private String processStr(String str) {

        String strResult = "";
        int sunnyIndex = str.indexOf("Clear");
        int rainyIndex = str.indexOf("Rain");
        int cloudIndex = str.indexOf("Clouds");

        Log.d("VC", "Sunny " + sunnyIndex + " Rainy " + rainyIndex + " Cloud " + cloudIndex);

        if(sunnyIndex < rainyIndex && sunnyIndex < cloudIndex && sunnyIndex>=0){
            strResult = strResult + "S";
            Log.d("VC", "It is SSSSSSSSSSSSSSSSSSSS");
        }
        else if(rainyIndex < sunnyIndex && rainyIndex < cloudIndex && rainyIndex>=0){
            strResult = strResult + "R";
            Log.d("VC", "It is RRRRRRRRRRRRRRRRRRRR");
        }
        else if(cloudIndex < rainyIndex && cloudIndex < sunnyIndex && cloudIndex>=0){
            strResult = strResult + "C";
            Log.d("VC", "It is CCCCCCCCCCCCCCCCCCCC");
        }
        else if(sunnyIndex > -1 && rainyIndex == -1 && cloudIndex == -1){
            strResult = strResult + "S";
            Log.d("VC", "It is SSSSSSSSSSS");
        }
        else if(sunnyIndex == -1 && rainyIndex > -1 && cloudIndex == -1){
            strResult = strResult + "R";
            Log.d("VC", "It is RRRRRRRRRRR");
        }
        else if(sunnyIndex == -1 && rainyIndex == -1 && cloudIndex > -1){
            strResult = strResult + "C";
            Log.d("VC", "It is CCCCCCCCCCC");
        }
        else if(sunnyIndex > -1 && rainyIndex > -1 && cloudIndex == -1){
            if(sunnyIndex < rainyIndex){
                strResult = strResult + "S";
                Log.d("VC", "It is SSSS");
            }
            else{
                strResult = strResult + "R";
                Log.d("VC", "It is RRRR");
            }
        }
        else if(sunnyIndex == -1 && rainyIndex > -1 && cloudIndex > -1){
            if(cloudIndex < rainyIndex){
                strResult = strResult + "C";
                Log.d("VC", "It is CCCC");
            }
            else{
                strResult = strResult + "R";
                Log.d("VC", "It is RRRR");
            }
        }
        else if(sunnyIndex > -1 && rainyIndex == -1 && cloudIndex > -1){
            if(sunnyIndex < cloudIndex){
                strResult = strResult + "S";
                Log.d("VC", "It is SSSS");
            }
            else{
                strResult = strResult + "C";
                Log.d("VC", "It is CCCC");
            }
        }
        strResult = strResult + "-";

        int minStart = str.indexOf("min")+5;
        int minEnd = str.indexOf("max")-2;
        int maxStart = str.indexOf("max")+5;
        int maxEnd = str.indexOf("night")-2;

        Log.d("VC", "Min " + str.substring(minStart, minEnd));
        Log.d("VC", "Max " + str.substring(maxStart, maxEnd));

        Double d1 = Double.parseDouble(str.substring(minStart, minEnd));
        int minValue = d1.intValue();
        Double d2 = Double.parseDouble(str.substring(maxStart, maxEnd));
        int maxValue = d2.intValue();

        Log.d("VC", "Min" + minValue + "-");
        Log.d("VC", "Max" + maxValue + "-");

        String data = str.substring(str.indexOf("min"));

        if(maxValue>30){
            strResult = strResult + "30";
        }
        else if(minValue<2){
            strResult = strResult + "02";
        }
        else if(minValue<10){
            strResult = strResult + "10";
        }
        else{
            strResult = strResult + "30";
        }

        return strResult;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String location = Utility.getPreferredLocation(this);
            if (location != null && !location.equals(mLocation)) {
            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
            if ( null != ff ) {
                ff.onLocationChanged();
            }
            DetailFragment df = (DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if ( null != df ) {
                df.onLocationChanged(location);
            }
            mLocation = location;
        }
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }

    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getMacAddress();//getSSID();
            }
        }
        return ssid;
    }

    private final boolean checkConnectedToDesiredWifi() {
        boolean connected = false;

        String desiredMacAddress = "F4:09:D8:43:86:33";
        String desiredNetworkId = "38";

        WifiManager wifiManager =
                (WifiManager)  MainActivity.this.getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifi = wifiManager.getConnectionInfo();
        if (wifi != null) {
            String netId = String.valueOf(wifi.getNetworkId());
            connected = desiredNetworkId.equals(netId);
            Log.d("VC", "Connected   " + connected + " " + wifi.getNetworkId() + " " + desiredNetworkId);
        }

        return connected;
    }
}
