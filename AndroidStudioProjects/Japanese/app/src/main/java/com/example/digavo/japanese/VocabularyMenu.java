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

public class VocabularyMenu extends AppCompatActivity {

    private String theme, color;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPref.getString("appTheme", "");
        color = sharedPref.getString("appColor", "");
        Log.i("MIAN_restart",theme+" + "+color);
        if(theme.compareTo("1")==0 && color.compareTo("1")==0)
            setTheme(R.style.LightOrange);
        else if(theme.compareTo("2")==0 && color.compareTo("1")==0)
            setTheme(R.style.DarkOrange);
        else if (theme.compareTo("1")==0 && color.compareTo("2")==0)
            setTheme(R.style.LightBlue);
        else if (theme.compareTo("2")==0 && color.compareTo("2")==0)
            setTheme(R.style.DarkBlue);
        boolean firstRun = sharedPref.getBoolean("FirstRun", true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vocabulary);
        //PreferenceManager.setDefaultValues(this, R.xml.options, false);

        // ------- Buttons --------------
        ((Button)findViewById(R.id.bt3)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.bt4)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.bt5)).setOnClickListener(categoryClick);

        ((Button)findViewById(R.id.btB1)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btB2)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btB3)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btB4)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btB5)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btB6)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btL1)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btL2)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btL3)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btLf1)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btLf2)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btLf3)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btP1)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btP2)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btP3)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btT1)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btT2)).setOnClickListener(categoryClick);
        ((Button)findViewById(R.id.btT3)).setOnClickListener(categoryClick);

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
            Intent showIntent = new Intent(VocabularyMenu.this, OptionsActivity.class);
            Bundle myDataBundle = new Bundle();
            myDataBundle.putString("option","new");
            showIntent.putExtras(myDataBundle);
            startActivityForResult(showIntent, 10);
        }

        return super.onOptionsItemSelected(item);
    }
    View.OnClickListener categoryClick = new View.OnClickListener() {
        public void onClick(View v) {
            String tag = "";
            try{
                tag=v.getTag().toString();
            }
            catch (Exception ex){
                Log.i("BUTTON",ex.toString());
                return;
            }
            if (tag=="") return;

            Intent showIntent = new Intent(VocabularyMenu.this, ActivityWords.class);
            Bundle myDataBundle = new Bundle();
            myDataBundle.putString("category",tag);
            showIntent.putExtras(myDataBundle);
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

            Intent showIntent = new Intent(VocabularyMenu.this, ActivityGrammar.class);
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
