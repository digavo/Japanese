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
import android.widget.Button;
import java.util.ArrayList;

public class ActivityGrammar extends AppCompatActivity {
    private Intent myIntent;
    private static int RESULT;
    private String theme, color;
    private ArrayList<String> hiragana = new ArrayList<String>();
    private ArrayList<String> romaji = new ArrayList<String>();
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
        TextView text1 = (TextView) findViewById(R.id.tvGr1);
        TextView text2 = (TextView) findViewById(R.id.tvGr2);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        // INTENT -----------
        myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        String category = " ";
        if (myBundle!=null)
            category = myBundle.getString("category");
        else category = "Hiragana";
        Log.i("GRAMMAR","category: "+category);
        //myIntent.putExtras(myBundle);
        //setResult(Activity.RESULT_OK, myIntent);

        switch (category){
            case "Particles": //TODO: dodać do bazy
                title.setText(R.string.menu3);
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
            case "Sentences": //TODO: dodać zdania do bazy
                title.setText(R.string.menu2);
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
                title.setText(R.string.menu4);
                int[] size = { 0, 0, 0, 0};
                // DATABASE ---------
                Cursor c=null;
                DataBaseHelper myDbHelper = new DataBaseHelper(ActivityGrammar.this);
                try {
                    // GET WORDS --------
                    String[] cat = {"HiraganaA%", "HiraganaB%", "HiraganaC%", "HiraganaD%"};
                    String pom;
                    myDbHelper.openDataBase();
                    for (int i = 0;i < 4; i++)
                    {
                        c=myDbHelper.query("Vocabulary",null, "Category LIKE ?", new String[]{cat[i]}, null,null, null);
                        if(c.moveToFirst()) {
                            do {
                                pom = c.getString(2);
                                if(pom.contains(" "))
                                    pom = pom.substring(0, pom.indexOf(" "));
                                hiragana.add(c.getString(3));
                                romaji.add(pom);
                                size[i]++;
                            } while (c.moveToNext());
                        }
                    }
                }
                catch(SQLException sqle){
                    throw sqle;
                }
                finally {
                    myDbHelper.close();
                }
                LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
                LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layoutView = layoutInflater.inflate(R.layout.hiragana_table,(ViewGroup)layout,false);
                layout.addView(layoutView,2);
                String[][] H = {{" ","K","S","T","N","H","M","Y","R","W","*N"},
                        {"G","Z","D","B","P"}, {"K","S","T","N","H","M","R"}, {"G","Z","D","B","P"}};
                size[1] += size[0]; size[2] += size[1]; size[3] += size[2];

                TableLayout[] lay = {(TableLayout) findViewById(R.id.tab1), (TableLayout) findViewById(R.id.tab2),
                        (TableLayout) findViewById(R.id.tab3), (TableLayout) findViewById(R.id.tab4)};

                for (int s = 0; s<4; s++)
                {
                    int rowCount = 0, pom = (s>0)? size[s-1]:0;
                    for (int i = pom; i < size[s]; )
                    {
                        TableRow row = new TableRow(this);
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                        row.setPadding(0,8,0,8);
                        TextView t0 = new TextView(this);
                        t0.setPadding(8,24,0,0);
                        t0.setTextSize(18);
                        t0.setWidth(0);
                        t0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        t0.setText(H[s][rowCount]);
                        row.addView(t0);

                        if (s==0 && i==size[s]-1) // exception N
                        {
                            TextView t = new TextView(this);
                            t.setTextSize(30);
                            t.setWidth(0);
                            t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            t.setText(hiragana.get(i));
                            row.addView(t);
                            TableRow.LayoutParams params = (TableRow.LayoutParams)t.getLayoutParams();
                            params.span = 5;
                            t.setLayoutParams(params);
                            lay[s].addView(row,rowCount+4);
                            break;
                        }

                        for (int j = 0; j < ((s < 2)? 5 : 3); j++)
                        {
                            View buttonView = layoutInflater.inflate(R.layout.button_layout, row,false);
                            Button txt1 = (Button) buttonView.findViewById(R.id.txt1);
                            TextView txt2 = (TextView) buttonView.findViewById(R.id.txt2);

                            if (s==0 && (((j==1 || j==3) && H[s][rowCount].compareTo("Y")==0)
                                     || ((j==1 || j==2 || j==3) && H[s][rowCount].compareTo("W")==0)))
                            {
                                txt1.setText(" ");
                                txt2.setText(" ");
                            }
                            else {
                                txt1.setText(hiragana.get(i));
                                txt2.setText(romaji.get(i));
                                i++;
                            }
                            row.addView(buttonView);
                        }
                        lay[s].addView(row,rowCount+3);
                        rowCount++;
                    }
                }
                break;
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
