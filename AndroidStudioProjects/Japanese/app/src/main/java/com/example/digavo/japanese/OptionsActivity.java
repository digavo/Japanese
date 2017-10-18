package com.example.digavo.japanese;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class OptionsActivity extends AppCompatActivity {
    private static int RESULT;
    private Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPref.getString("appTheme", "");
        String color = sharedPref.getString("appColor", "");
        if(theme.compareTo("1")==0 && color.compareTo("1")==0)
            setTheme(R.style.LightOrange);
        else if(theme.compareTo("2")==0 && color.compareTo("1")==0)
            setTheme(R.style.DarkOrange);
        else if (theme.compareTo("1")==0 && color.compareTo("2")==0)
            setTheme(R.style.LightBlue);
        else if (theme.compareTo("2")==0 && color.compareTo("2")==0)
            setTheme(R.style.DarkBlue);
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.options, false);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment1()).commit();
        myIntent = getIntent();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*@Override
    public void onBackPressed() {
        Log.i("OPRIONS_back", myIntent.toString() + " "+ RESULT);
        setResult(RESULT, myIntent);
        RESULT=RESULT_CANCELED;
        super.onBackPressed();
    }*/

    public static class PrefFragment1 extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String value = sharedPref.getString("appTheme", "");setPreferenceScreen(null);
            addPreferencesFromResource(R.xml.options);
            Preference pref = findPreference("appTheme");
            pref.setSummary((value.compareTo("1")==0)? "Light": "Dark");
            pref = findPreference("appColor");
            value = sharedPref.getString("appColor", "");
            pref.setSummary((value.compareTo("1")==0)? "Orange":"Blue");
            pref = findPreference("appLetter");
            value = sharedPref.getString("appLetter", "");
            pref.setSummary((value.compareTo("1")==0)? "Romaji":"Hiragana");

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference pref = findPreference(key);
            String sum="",val = sharedPreferences.getString(key, "");
            switch (key){
                case "appTheme":
                    switch (val){
                        case "1":
                            sum="Light";
                            break;
                        case "2":
                            sum="Dark";
                            break;
                    }
                    if (pref.getSummary()!=sum){
                        pref.setSummary(sum);
                        RESULT = RESULT_OK;
                        getActivity().recreate();
                    }
                    /*getActivity().finish();
                    final Intent intent = getActivity().getIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(intent);*/
                    break;
                case "appColor":
                    switch (val){
                        case "1":
                            sum="Orange";
                            break;
                        case "2":
                            sum="Blue";
                            break;
                    }
                    if (pref.getSummary()!=sum){
                        pref.setSummary(sum);
                        RESULT = RESULT_OK;
                        getActivity().recreate();
                    }
                    break;
                case "appLetter":
                    switch (val){
                        case "1":
                            sum="Romaji";
                            break;
                        case "2":
                            sum="Hiragana";
                            break;
                    }
                    if (pref.getSummary()!=sum){
                        pref.setSummary(sum);
                        RESULT = RESULT_OK;
                        getActivity().recreate();
                    }
                    break;
            }
        }
        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
