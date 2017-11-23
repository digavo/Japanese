package com.example.digavo.japanese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

public class ActivityWords extends AppCompatActivity {
    private Cursor c=null;
    private myAdapter adapter;
    private ListView wordsList;
    private ArrayList<String> wordsEn = new ArrayList<String>();
    private ArrayList<String> wordsJp = new ArrayList<String>();
    private int[] wordsStatus;
    private Button btEn, btEnJp, btJp;
    private Boolean engShow = true, jpnShow = true;
    private static int result;
    private String theme, color, letters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        letters = sharedPref.getString("appLetter", "");
        int indexColumn;
        if (letters.compareTo("1")==0) indexColumn = 2;
        else indexColumn = 3;
        theme = sharedPref.getString("appTheme", "");
        color = sharedPref.getString("appColor", "");
        if(theme.compareTo("1")==0 && color.compareTo("1")==0)
            setTheme(R.style.LightOrange);
        else if(theme.compareTo("2")==0 && color.compareTo("1")==0)
            setTheme(R.style.DarkOrange);
        else if (theme.compareTo("1")==0 && color.compareTo("2")==0)
            setTheme(R.style.LightBlue);
        else if (theme.compareTo("2")==0 && color.compareTo("2")==0)
            setTheme(R.style.DarkBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        wordsList = (ListView) findViewById(R.id.myList);
        btEn = (Button) findViewById(R.id.btEn);
        btEnJp = (Button) findViewById(R.id.btEnJp);
        btJp = (Button) findViewById(R.id.btJp);
        btEn.setBackgroundResource(R.drawable.button);
        btJp.setBackgroundResource(R.drawable.button);
        btEnJp.setBackgroundResource(R.drawable.button_selected);
        // INTENT -----------
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        String category = myBundle.getString("category");
        //myIntent.putExtras(myBundle);
        //setResult(Activity.RESULT_OK, myIntent);

        // DATABASE ---------
        DataBaseHelper myDbHelper = new DataBaseHelper(ActivityWords.this);
        try {
            // GET WORDS --------
            myDbHelper.openDataBase();
            c=myDbHelper.query("Vocabulary", null, "Category = ?", new String[]{category}, null,null, null);
            if(c.moveToFirst()) {
                do {
                    wordsEn.add(c.getString(1));
                    wordsJp.add(c.getString(indexColumn).toLowerCase());
                } while (c.moveToNext());
            }
            wordsStatus = new int[wordsJp.size()];
            Arrays.fill(wordsStatus,0);
        }
        catch(SQLException sqle){
            throw sqle;
        }
        finally {
            myDbHelper.close();
        }
        // LISTVIEW ---------
        String[] it1 = wordsEn.toArray(new String[wordsEn.size()]);
        String[] it2 = wordsJp.toArray(new String[wordsJp.size()]);
        adapter = new myAdapter(this, R.layout.words_layout, it1, it2, wordsStatus);
        wordsList.setAdapter(adapter);
        wordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = wordsList.getChildAt(position - wordsList.getFirstVisiblePosition());
                if(v == null) return;
                if (!engShow){ //jp visible, en invisible
                    if (wordsStatus[position]==0)
                        wordsStatus[position]=2;
                    else wordsStatus[position]=0;
                }
                if (!jpnShow){
                    if (wordsStatus[position]==0)
                        wordsStatus[position]=1;
                    else wordsStatus[position]=0;
                }
                adapter.notifyDataSetChanged();
            }
        });

        // BUTTONS ----------
        btEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    changeVisibility(1);
                }catch (Exception ex){
                    Log.i("VISIBLE1",ex.toString());
                }
                engShow = true;
                jpnShow = false;
                btEn.setBackgroundResource(R.drawable.button_selected);
                btEnJp.setBackgroundResource(R.drawable.button);
                btJp.setBackgroundResource(R.drawable.button);
            }
        });
        btEnJp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    changeVisibility(0);
                }catch (Exception ex){
                    Log.i("VISIBLE0",ex.toString());
                }
                engShow = true;
                jpnShow = true;
                btEn.setBackgroundResource(R.drawable.button);
                btEnJp.setBackgroundResource(R.drawable.button_selected);
                btJp.setBackgroundResource(R.drawable.button);
            }
        });
        btJp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    changeVisibility(2);
                }catch (Exception ex){
                    Log.i("VISIBLE2",ex.toString());
                }
                engShow = false;
                jpnShow = true;
                btEn.setBackgroundResource(R.drawable.button);
                btEnJp.setBackgroundResource(R.drawable.button);
                btJp.setBackgroundResource(R.drawable.button_selected);
            }
        });
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
            Intent showIntent = new Intent(this, OptionsActivity.class);
            Bundle myDataBundle = new Bundle();
            myDataBundle.putString("option","new");
            showIntent.putExtras(myDataBundle);
            startActivityForResult(showIntent, 10);
        }
        /*else if (id == android.R.id.home) {
            onBackPressed();//setResult(RESULT, myIntent); //
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void changeVisibility(int num) {
        for (int i = 0; i < wordsStatus.length; i++)
            wordsStatus[i] = num;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String theme2 = sharedPref.getString("appTheme", "");
        String color2 = sharedPref.getString("appColor", "");
        String letters2 = sharedPref.getString("appLetter", "");
        if (!theme2.equals(theme) || !color2.equals(color) || !letters2.equals(letters))
            recreate();
    }
}