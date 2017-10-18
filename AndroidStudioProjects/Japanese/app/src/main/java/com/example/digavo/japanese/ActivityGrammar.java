package com.example.digavo.japanese;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ActivityGrammar extends AppCompatActivity {
    private TextView text1, text2, title;
    private Cursor c=null;
    private Intent myIntent;
    private static int RESULT;
    private String category, theme, color;
    private ArrayList<String> hiragana = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
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
        setContentView(R.layout.activity_grammar);
        text1 = (TextView) findViewById(R.id.tvGr1);
        text2 = (TextView) findViewById(R.id.tvGr2);
        title = (TextView) findViewById(R.id.tvTitle);
        // INTENT -----------
        myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        if (myBundle!=null)
            category = myBundle.getString("category");
        else category = "Hiragana";
        Log.i("GRAMMAR","category: "+category);
        //myIntent.putExtras(myBundle);
        //setResult(Activity.RESULT_OK, myIntent);

        switch (category){
            case "Particles":
                title.setText("Particles");
                text1.setText("か KA?\nの NO\nは WA\nが GA\nと TO\nから KARA\nまで MADE\nも MO\nに NI\nで DE\n\nを O");
                text2.setText("question\n" +
                        "possession\n" +
                        "topic of a sentence\n" +
                        "subject of a sentence\n" +
                        "together with\n" +
                        "start in time or place = from\n" +
                        "end in time or place = to, until\n" +
                        "also / nothing, nobody, nowhere\n" +
                        "location/destination\n" +
                        "as a group / location of an action / instrumental\n" +
                        "direct object of a verb");

                break;
            case "Sentences":
                title.setText("Example Sentences");
                text2.setWidth(0);
                text1.setText("X is Y\nX wa Y des.\n\n"+
                        "X is y?\nX wa Y des ka?\n\n"+
                        "X isn’t Y\nX wa Y dewa arimasen.\n\n"+
                        "This is my pen\nKore wa watashi no pen des.\n\n"+
                        "Our book is this\nWatashitachi no hon wa kore des.\n\n"+
                        "This is our book\nKore wa watashitachi no hon des.\n\n"+
                        "You are person from which country?\nAnata wa dochira no kuni no kata des ka?\n\n"+
                        "I am from Poland (Poland person)\nWatashi wa po-rando no hito des.\n\n"+
                        "Where is your country?\nAnata no kuni wa doko des ka?\n\n"+
                        "My country is Poland\nWatashi no kuni wa po-rando des.\n\n"+
                        "I am Spanish\nWatashi wa speinjin des.\n\n"+
                        "Who are you?\nAnata wa dare des ka?\n\n"+
                        "Your shoe is this or that? Which one?\nAnata no kucu wa kore des ka? Soretomo sore des ka? Dochira des ka?\n\n"+
                        "This book is whose book?\nSono hon wa dare no hon des ka?\n\n"+
                        "Now time is what hour what minute? \nIma jikan wa nan-ji nan pun des ka?\n\n"+

                        "Where is your house?\nAnata no ie wa doko ni arimas ka?\n\n"+
                        "Where is your cat?\nAnata no neko wa doko ni imas ka?\n\n"+
                        "Your cat isn’t in house\nAnata no neko wa ie niwa imasen\n\n"+
                        "Glasses are next to what?\nMegane wa nan no yoko ni arimas ka?\n\n"+
                        "Glasses are where in room?\nMegane wa heya no doko ni arimas ka?\n\n"+
                        "No, my house isn’t at X\nIie, watashi no ie wa X niwa arimasen.\n\n"+
                        "What is there? \nNani ga soko ni arimas ka?\n\n"+

                        "I am going to X\nWatashi wa X ni ikimas.\n\n"+
                        "I am not going to X\nWatashi wa X niwa ikimasen.\n\n"+
                        "Are you going back to house?\nAnata wa ie ni kaerimas ka?\n\n"+
                        "I come to house by foot\nWatashi wa ie made ashi de ikimas.\n\n"+
                        "Mother and you go when to shop?\nAnata to haha wa icu kaimono ni ikimas ka? \n\n"+
                        "Also I go to school\nWatashi mo gakkou ni ikimas.\n\n"+
                        "I go also to school \nWatashi wa gakkou nimo ikimas.\n\n"+
                        "Who goes to shop?\nDare ga mise ni ikimas ka?\n\n"+
                        "My friend goes also where?\nWatashi no tomodachi wa doko nimo ikimasen ka?\n\n"+

                        "Today I swim in swimming pool\nKyou watashi wa suiei o pu-ru de shimas\n\n"+
                        "What are you doing from now ?\nAnata wa korekara nani o shimas ka?\n\n"+
                        "I do studying at school\nWatashi wa gakkou de benkyou o shimas.\n\n"+
                        "No, I don’t drive car\nIie, watashi wa kuruma no unten wa shimasen.\n\n"+
                        "With whom you do shopping?\nAnata wa dare to kaimono o shimas ka?\n\n"+
                        "I do studying also at school \nWatashi wa gakkou demo benkyou o shimas.\n\n"+
                        "I also do studying at school\nWatashi wa gakkou de benkyou mo shimas.\n\n"+
                        "I don’t do anything\nWatashi wa nani mo shimasen.\n\n");
                break;
            case "Hiragana":
                title.setText("Hiragana");
                /*title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent testIntent = new Intent(ActivityGrammar.this, ActivityTest.class);
                        startActivityForResult(testIntent, 20);
                    }
                });*/
                int h1Size = 0, h2Size = 0, h3Size = 0, h4Size = 0;
                // DATABASE ---------
                DataBaseHelper myDbHelper = new DataBaseHelper(ActivityGrammar.this);
                try {
                    // GET WORDS --------
                    myDbHelper.openDataBase();
                    c=myDbHelper.query("Vocabulary",null, "Category LIKE ?", new String[]{"HiraganaA%"}, null,null, null);
                    if(c.moveToFirst()) {
                        do {
                            hiragana.add(c.getString(4));
                            h1Size++;
                        } while (c.moveToNext());
                    }
                    c=myDbHelper.query("Vocabulary", null, "Category LIKE ?", new String[]{"HiraganaB%"}, null,null, null);
                    if(c.moveToFirst()) {
                        do {
                            hiragana.add(c.getString(4));
                            h2Size++;
                        } while (c.moveToNext());
                    }
                    c=myDbHelper.query("Vocabulary", null, "Category LIKE ?", new String[]{"HiraganaC%"}, null,null, null);
                    if(c.moveToFirst()) {
                        do {
                            hiragana.add(c.getString(4));
                            h3Size++;
                        } while (c.moveToNext());
                    }
                    c=myDbHelper.query("Vocabulary", null, "Category LIKE ?", new String[]{"HiraganaD%"}, null,null, null);
                    if(c.moveToFirst()) {
                        do {
                            hiragana.add(c.getString(4));
                            h4Size++;
                        } while (c.moveToNext());
                    }
                }
                catch(SQLException sqle){
                    throw sqle;
                }
                finally {
                    myDbHelper.close();
                }
                LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
                View layoutView = layoutInflater.inflate(R.layout.hiragana_table,(ViewGroup)layout,false);
                layout.addView(layoutView,2);
                String[] H1 = {" ","K","S","T","N","H","M","Y","R","W","*N"};
                String[] H2 = {"G","Z","D","B","P"};
                String[] H3 = {"K","S","T","N","H","M","R"};
                TableLayout ll = (TableLayout) findViewById(R.id.tab1);
                int rowCount = 0;
                for (int i = 0; i <h1Size; ) {

                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView t0 = new TextView(this);
                    t0.setTextSize(18);
                    t0.setWidth(0);
                    t0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t0.setText(H1[rowCount]);
                    row.addView(t0);

                    if (i==h1Size-1){

                        TextView t = new TextView(this);
                        t.setTextSize(30);
                        t.setWidth(0);
                        t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        t.setText(hiragana.get(i));
                        row.addView(t);
                        TableRow.LayoutParams params = (TableRow.LayoutParams)t.getLayoutParams();
                        params.span = 5;
                        t.setLayoutParams(params);
                        ll.addView(row,rowCount+4);
                        break;
                    }
                    for (int j = 0; j < 5; j++){
                        TextView t1 = new TextView(this);
                        t1.setTextSize(30);
                        t1.setWidth(0);
                        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        if (((j==1 || j==3) && H1[rowCount].compareTo("Y")==0) || ((j==1 || j==2 || j==3) && H1[rowCount].compareTo("W")==0)) //to nie pisz nic
                            t1.setText(" ");
                        else {
                            t1.setText(hiragana.get(i));
                            i++;
                        }
                        row.addView(t1);

                    }
                    ll.addView(row,rowCount+4);
                    rowCount++;
                }

                TableLayout l2 = (TableLayout) findViewById(R.id.tab2);
                rowCount = 0;
                for (int i = 0; i <h2Size; ) {

                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView t0 = new TextView(this);
                    t0.setTextSize(18);
                    t0.setWidth(0);
                    t0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t0.setText(H2[rowCount]);
                    row.addView(t0);

                    for (int j = 0; j < 5; j++){
                        TextView t1 = new TextView(this);
                        t1.setTextSize(30);
                        t1.setWidth(0);
                        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        t1.setText(hiragana.get(h1Size+i));
                        row.addView(t1);
                        i++;
                    }

                    l2.addView(row,rowCount+3);
                    rowCount++;
                }
                TableLayout l3 = (TableLayout) findViewById(R.id.tab3);
                rowCount = 0;
                for (int i = 0; i <h3Size; ) {

                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView t0 = new TextView(this);
                    t0.setTextSize(18);
                    t0.setWidth(0);
                    t0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t0.setText(H3[rowCount]);
                    row.addView(t0);

                    for (int j = 0; j < 3; j++){
                        TextView t1 = new TextView(this);
                        t1.setTextSize(30);
                        t1.setWidth(0);
                        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        t1.setText(hiragana.get(h1Size+h2Size+i));
                        row.addView(t1);
                        i++;
                    }
                    l3.addView(row,rowCount+3);
                    rowCount++;
                }
                TableLayout l4 = (TableLayout) findViewById(R.id.tab4);
                rowCount = 0;
                for (int i = 0; i <h4Size; ) {

                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView t0 = new TextView(this);
                    t0.setTextSize(18);
                    t0.setWidth(0);
                    t0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t0.setText(H2[rowCount]);
                    row.addView(t0);

                    for (int j = 0; j < 3; j++){
                        TextView t1 = new TextView(this);
                        t1.setTextSize(30);
                        t1.setWidth(0);
                        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        t1.setText(hiragana.get(h1Size+h2Size+h3Size+i));
                        row.addView(t1);
                        i++;
                    }
                    l4.addView(row,rowCount+3);
                    rowCount++;
                }
                break;
        }
    }
    private void getWords(String category)
    {
        DataBaseHelper myDbHelper = new DataBaseHelper(ActivityGrammar.this);
        // GET WORDS --------
        try {
            myDbHelper.openDataBase();
            c=myDbHelper.query("Vocabulary", null, "Category = ?", new String[]{category}, null,null, null);
            if(c.moveToFirst()) {
                do {
                    text1.setText(text1.getText().toString()+c.getString(1)+"\n");
                    text2.setText(text2.getText().toString()+c.getString(3).toLowerCase()+"\n");
                } while (c.moveToNext());
            }
        }catch(SQLException sqle){
            throw sqle;
        }
        finally {
            myDbHelper.close();
        }
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
        else if (id == android.R.id.home) {
            setResult(RESULT, myIntent); //
        }
        return super.onOptionsItemSelected(item);
    }
    /*Override
    public void onBackPressed() {
        setResult(RESULT, myIntent);
        super.onBackPressed();
    }*/
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10)
            if (resultCode == RESULT_OK) {
                RESULT=RESULT_OK;
                recreate();
            }
            else
                RESULT=RESULT_CANCELED;
        if (requestCode == 20 && resultCode == RESULT_OK) {
            category = "Hiragana";
            //recreate();
        }
    }*/

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
