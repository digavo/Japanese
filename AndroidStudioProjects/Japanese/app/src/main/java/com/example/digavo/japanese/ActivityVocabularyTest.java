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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ActivityVocabularyTest extends AppCompatActivity {
    private TextView tvScore, tvTest, tvPrev;
    private EditText etAnswer;
    private String theme, color;
    private static int pointsR, pointsW, itter, count = 20, words, randIndex;
    private static ArrayList<String> wordsH = new ArrayList<String>();
    private static ArrayList<String> wordsR = new ArrayList<String>();
    private static ArrayList<String> wordsE = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPref.getString("appTheme", "");
        color = sharedPref.getString("appColor", "");
        if (theme.compareTo("1") == 0 && color.compareTo("1") == 0)
            setTheme(R.style.LightOrange);
        else if (theme.compareTo("2") == 0 && color.compareTo("1") == 0)
            setTheme(R.style.DarkOrange);
        else if (theme.compareTo("1") == 0 && color.compareTo("2") == 0)
            setTheme(R.style.LightBlue);
        else if (theme.compareTo("2") == 0 && color.compareTo("2") == 0)
            setTheme(R.style.DarkBlue);
        boolean firstRun = sharedPref.getBoolean("FirstRun", true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_test);
        Button btAccept = (Button) findViewById(R.id.btAccept);
        tvScore = (TextView) findViewById(R.id.tvResult);
        tvTest = (TextView) findViewById(R.id.tvTest);
        tvPrev = (TextView) findViewById(R.id.tvPrev);
        etAnswer = (EditText) findViewById(R.id.etAnswer);
        btAccept.setOnClickListener(Accept);

        //TODO: dodać wybór kategorii
        //TODO: dodać wybór ilości pytań lub nieskończone
        //TODO: dodać zapisywanie wyników w bazie (+ nowe okno statystyk)
        String category = "", notCategory="hiragana";
        DataBaseHelper myDbHelper = new DataBaseHelper(ActivityVocabularyTest.this);
        try {
            // GET WORDS --------
            Cursor c=null;
            myDbHelper.openDataBase();
            if (category.compareTo("")==0)
                c=myDbHelper.query("Vocabulary", null, "Category NOT LIKE ?", new String[]{notCategory+"%"}, null,null, null);
            else
                c=myDbHelper.query("Vocabulary", null, "Category LIKE ?", new String[]{category+"%"}, null,null, null);
            if(c.moveToFirst()) {
                do {
                    wordsE.add(c.getString(1));
                    wordsR.add(c.getString(3));
                    wordsH.add(c.getString(4));
                } while (c.moveToNext());
            }
        }
        catch(SQLException sqle){
            throw sqle;
        }
        finally {
            myDbHelper.close();
        }
        words = wordsH.size();
        randIndex = new Random().nextInt(words);
        pointsR = 0; pointsW = 0; itter = 1;
        tvScore.setText(String.format(Locale.ENGLISH, "%d / %d　　　%s: %d / %s: %d",
                itter, count, R.string.testRight, pointsR, R.string.testWrong, pointsW) );
        tvPrev.setText("");
        tvTest.setText(wordsR.get(randIndex));
    }
    public View.OnClickListener Accept = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tvTest.getText().toString().contains("END")) {
                pointsR = 0; pointsW = 0; itter = 0;
                tvPrev.setText("");
            }
            else //sprawdź
            {
                String Answer = etAnswer.getText().toString().toLowerCase();
                if (Answer.endsWith(" ") && Answer.length()>1) Answer = Answer.substring(0, Answer.length() - 1);
                etAnswer.setText("");
                String userAnswer = (wordsE.get(randIndex)).toLowerCase();
                if (userAnswer.endsWith(" ") &&userAnswer.length()>1) userAnswer = userAnswer.substring(0, userAnswer.length() - 1);
                Log.i("Answer","|"+Answer+"|   |"+ userAnswer+"|");
                if (Answer.compareTo((wordsE.get(randIndex)).toLowerCase())==0){
                    pointsR++;
                    tvPrev.setText(String.format(Locale.ENGLISH,"%s: %s = %s\n%s: %s = %s",
                            R.string.testPrevious,wordsR.get(randIndex),wordsE.get(randIndex),R.string.testYou, R.string.testAnswer, R.string.testCorrect));
                }
                else {
                    tvPrev.setText(String.format(Locale.ENGLISH,"%s: %s = %s\n%s: %s = %s",
                            R.string.testPrevious,wordsR.get(randIndex),wordsE.get(randIndex),R.string.testYou, R.string.testAnswer, R.string.testIncorrect));
                    pointsW++;
                }
            }
            itter++;
            if (itter > count) {
                itter = count;
                tvTest.setText(R.string.testEnd);
            }
            else {
                randIndex = new Random().nextInt(words);
                tvTest.setText(wordsR.get(randIndex));
            }
            tvScore.setText(String.format(Locale.ENGLISH, "%d / %d　　　%s: %d / %s: %d",
                    itter, count, R.string.testRight, pointsR, R.string.testWrong, pointsW) );

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.options) {
            Intent showIntent = new Intent(ActivityVocabularyTest.this, OptionsActivity.class);
            Bundle myDataBundle = new Bundle();
            myDataBundle.putString("option","new");
            showIntent.putExtras(myDataBundle);
            startActivityForResult(showIntent, 10);
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
