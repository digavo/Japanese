package com.example.digavo.japanese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class ActivityTest extends AppCompatActivity {
    private Cursor c=null;
    private Intent myIntent;
    private Button btAccept;
    private TextView tvScore, tvTest, tvPrev;
    private EditText etAnswer;
    private String Answer;
    private static int pointsR, pointsW, itter, count = 10, letters, randIndex;
    private static ArrayList<String> lettersH = new ArrayList<String>();
    private static ArrayList<String> lettersR = new ArrayList<String>();
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
        setContentView(R.layout.activity_test);
        btAccept = (Button) findViewById(R.id.btAccept);
        tvScore = (TextView) findViewById(R.id.tvResult);
        tvTest = (TextView) findViewById(R.id.tvTest);
        tvPrev = (TextView) findViewById(R.id.tvPrev);
        etAnswer = (EditText) findViewById(R.id.etAnswer);
        btAccept.setOnClickListener(Accept);

        // INTENT -----------
        myIntent = getIntent();
        //Bundle myBundle = myIntent.getExtras();
        //String category = myBundle.getString("category");

        String category = "Hiragana";
        DataBaseHelper myDbHelper = new DataBaseHelper(ActivityTest.this);
        try {
            // GET LETTERS --------
            myDbHelper.openDataBase();
            c=myDbHelper.query("Vocabulary", null, "Category LIKE ?", new String[]{category+"%"}, null,null, null);
            if(c.moveToFirst()) {
                do {
                    lettersR.add(c.getString(3));
                    lettersH.add(c.getString(4));
                } while (c.moveToNext());
            }
        }
        catch(SQLException sqle){
            throw sqle;
        }
        finally {
            myDbHelper.close();
        }
        letters = lettersH.size();
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();

        randIndex = new Random().nextInt(letters);
        pointsR = 0; pointsW = 0; itter = 1;
        tvScore.setText(itter+" / "+count+"　　　Right: "+pointsR+" / Wrong: "+pointsW);
        tvPrev.setText("");
        tvTest.setText(lettersR.get(randIndex));

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
                Answer = etAnswer.getText().toString();
                etAnswer.setText("");
                if (Answer.compareTo(lettersH.get(randIndex))==0){
                    pointsR++;
                    tvPrev.setText("Previous: "+lettersR.get(randIndex)+" = "+lettersH.get(randIndex) + " You: "+Answer+" = correct");
                }
                else {
                    tvPrev.setText("Previous: "+lettersR.get(randIndex)+" = "+lettersH.get(randIndex) + " You: "+Answer+" = incorrect");
                    pointsW++;
                }
            }
            itter++;
            if (itter > count) {
                itter = count;
                tvTest.setText("END - Accept to begin");
            }
            else {
                randIndex = new Random().nextInt(letters);
                tvTest.setText(lettersR.get(randIndex));
            }
            tvScore.setText(itter+" / "+count+"　　　Right: "+pointsR+" / Wrong: "+pointsW);

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            setResult(RESULT_OK, myIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, myIntent);
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
        super.onResume();
    }

    @Override
    protected void onPause() {
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
        super.onPause();
    }
}
