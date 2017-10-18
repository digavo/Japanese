package com.example.digavo.japanese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String theme, color;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPref.getString("appTheme", "");
        color = sharedPref.getString("appColor", "");
        boolean firstRun = sharedPref.getBoolean("FirstRun", true);
        Log.i("MAIN_start",firstRun+" ");
        if (firstRun){
            color="1"; theme="1";
            DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this);
            try {
                myDbHelper.createDataBase();
            } catch (IOException ioe) {
                throw new Error("Unable to create database");
            }
            sharedPref.edit().putString("appTheme", "1").commit();
            sharedPref.edit().putString("appColor", "1").commit();
            sharedPref.edit().putBoolean("FirstRun", false).commit();
        }
        if(theme.compareTo("1")==0 && color.compareTo("1")==0)
            setTheme(R.style.LightOrange);
        else if(theme.compareTo("2")==0 && color.compareTo("1")==0)
            setTheme(R.style.DarkOrange);
        else if (theme.compareTo("1")==0 && color.compareTo("2")==0)
            setTheme(R.style.LightBlue);
        else if (theme.compareTo("2")==0 && color.compareTo("2")==0)
            setTheme(R.style.DarkBlue);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //PreferenceManager.setDefaultValues(this, R.xml.options, false);

        // ------- Buttons --------------
        ((Button)findViewById(R.id.bt1)).setOnClickListener(grammarClick);
        ((Button)findViewById(R.id.bt2)).setOnClickListener(grammarClick);
        ((Button)findViewById(R.id.bt3)).setOnClickListener(vocabularyClick);
        ((Button)findViewById(R.id.bt4)).setOnClickListener(grammarClick);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.options) {
            Intent showIntent = new Intent(MainActivity.this, OptionsActivity.class);
            Bundle myDataBundle = new Bundle();
            myDataBundle.putString("option","new");
            showIntent.putExtras(myDataBundle);
            startActivityForResult(showIntent, 10);
        }

        return super.onOptionsItemSelected(item);
    }
    View.OnClickListener vocabularyClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent showIntent = new Intent(MainActivity.this, VocabularyMenu.class);
            startActivity(showIntent);
        }
    };
    View.OnClickListener grammarClick = new View.OnClickListener() {
        public void onClick(View v) {

            String tag = "";
            try{
                tag=v.getTag().toString();
            }
            catch (Exception ex){
                Log.i("BUTTON",ex.toString());
                return;
            }
            if (tag.equals("")) return;

            Intent showIntent = new Intent(MainActivity.this, ActivityGrammar.class);
            Bundle myDataBundle = new Bundle();
            myDataBundle.putString("category",tag);
            showIntent.putExtras(myDataBundle);
            startActivity(showIntent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String theme2 = sharedPref.getString("appTheme", "");
        String color2 = sharedPref.getString("appColor", "");
        if (!theme2.equals(theme) || !color2.equals(color))
            recreate();
    }
}
