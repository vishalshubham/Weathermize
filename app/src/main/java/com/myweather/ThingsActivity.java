package com.myweather;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ThingsActivity extends ActionBarActivity {

    public static String WHOLE_LINE = "WHOLE_LINE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things);

        String str = getIntent().getStringExtra(WHOLE_LINE);
        ImageView imageView1 = (ImageView)findViewById(R.id.thing1);
        ImageView imageView2 = (ImageView)findViewById(R.id.thing2);
        ImageView imageView3 = (ImageView)findViewById(R.id.thing3);

        TextView text1 = (TextView)findViewById(R.id.text1);
        TextView text2 = (TextView)findViewById(R.id.text2);
        TextView text3 = (TextView)findViewById(R.id.text3);

        if(str.equals("S-30")){
            imageView1.setImageResource(R.drawable.ic_cream);
            imageView2.setImageResource(R.drawable.ic_umbrella_sunny);
            imageView3.setImageResource(R.drawable.ic_water);
            text1.setText("Suncream");
            text2.setText("Light Umbrella");
            text3.setText("Water");
        }
        else if(str.equals("S-10")){
            imageView1.setImageResource(R.drawable.ic_cream);
            imageView2.setImageResource(R.drawable.ic_umbrella_sunny);
            imageView3.setImageResource(R.drawable.ic_jacket);
            text1.setText("Suncream");
            text2.setText("Light Umbrella");
            text3.setText("Winter Jacket");
        }
        else if(str.equals("S-02")){
            imageView1.setImageResource(R.drawable.ic_cream);
            imageView2.setImageResource(R.drawable.ic_umbrella_sunny);
            imageView3.setImageResource(R.drawable.ic_thermal);
            text1.setText("Suncream");
            text2.setText("Light Umbrella");
            text3.setText("Thermal wear");
        }
        else if(str.equals("C-30")){
            imageView1.setImageResource(R.drawable.ic_hoodie);
            imageView2.setImageResource(R.drawable.ic_cream);
            imageView3.setImageResource(R.drawable.ic_water);
            text1.setText("Hoodie");
            text2.setText("Sun cream for UV protection");
            text3.setText("Water");
        }
        else if(str.equals("C-10")){
            imageView1.setImageResource(R.drawable.ic_hoodie);
            imageView2.setImageResource(R.drawable.ic_cream);
            imageView3.setImageResource(R.drawable.ic_jacket);
            text1.setText("Hoodie");
            text2.setText("Sun cream for UV protection");
            text3.setText("Winter Jacket");
        }
        else if(str.equals("C-02")){
            imageView1.setImageResource(R.drawable.ic_hoodie);
            imageView2.setImageResource(R.drawable.ic_cream);
            imageView3.setImageResource(R.drawable.ic_thermal);
            text1.setText("Hoodie");
            text2.setText("Sun cream for UV protection");
            text3.setText("Thermal wear");
        }
        else if(str.equals("R-30")){
            imageView1.setImageResource(R.drawable.ic_umbrella_rainy);
            imageView2.setImageResource(R.drawable.ic_raincoat);
            imageView3.setImageResource(R.drawable.ic_water);
            text1.setText("Rainy Umbrella");
            text2.setText("Raincoat");
            text3.setText("Water");
        }
        else if(str.equals("R-10")){
            imageView1.setImageResource(R.drawable.ic_umbrella_rainy);
            imageView2.setImageResource(R.drawable.ic_raincoat);
            imageView3.setImageResource(R.drawable.ic_jacket);
            text1.setText("Rainy Umbrella");
            text2.setText("Raincoat");
            text3.setText("Wnter Jacket");
        }
        else if(str.equals("R-02")){
            imageView1.setImageResource(R.drawable.ic_umbrella_rainy);
            imageView2.setImageResource(R.drawable.ic_raincoat);
            imageView3.setImageResource(R.drawable.ic_thermal);
            text1.setText("Rainy Umbrella");
            text2.setText("Raincoat");
            text3.setText("Thermal wear");
        }
        else if(str.equals("W-30")){
            imageView1.setImageResource(R.drawable.ic_goggles);
            imageView2.setImageResource(R.drawable.ic_gloves);
            imageView3.setImageResource(R.drawable.ic_water);
            text1.setText("Goggles");
            text2.setText("Gloves");
            text3.setText("Water");
        }
        else if(str.equals("W-10")){
            imageView1.setImageResource(R.drawable.ic_goggles);
            imageView2.setImageResource(R.drawable.ic_gloves);
            imageView3.setImageResource(R.drawable.ic_jacket);
            text1.setText("Goggles");
            text2.setText("Gloves");
            text3.setText("Winter Jacket");
        }
        else if(str.equals("W-02")){
            imageView1.setImageResource(R.drawable.ic_goggles);
            imageView2.setImageResource(R.drawable.ic_gloves);
            imageView3.setImageResource(R.drawable.ic_thermal);
            text1.setText("Goggles");
            text2.setText("Gloves");
            text3.setText("Thermal wear");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_things, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
