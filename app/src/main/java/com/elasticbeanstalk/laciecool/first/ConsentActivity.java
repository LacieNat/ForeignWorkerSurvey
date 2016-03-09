package com.elasticbeanstalk.laciecool.first;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ConsentActivity extends Activity {
    SignatureView sigView;
    EditText nameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);

        if(sp.getInt("lang", 0) ==0)
            setContentView(R.layout.activity_consent);
        else
            setContentView(R.layout.activity_consent_bahasa);

        sigView = (SignatureView) findViewById(R.id.signature_canvas);
        nameView = (EditText) findViewById(R.id.name);

        sigView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN /*&& nameFlag*/) {
                    Button b = (Button) findViewById(R.id.btn_next);
                    b.setEnabled(true);
                }

                return false;
            }
        });

//        nameView.setOnKeyListener(new View.OnKeyListener() {
//
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN && sigFlag) {
//                    if(keyCode == 4) {  //if key code is backspace
//                        if (nameView.getText().equals("")) {
//                            nameFlag = false;
//                            Button b = (Button) findViewById(R.id.btn_next);
//                            b.setEnabled(false);
//                        }
//                    } else {
//                        nameFlag = true;
//                        Button b = (Button) findViewById(R.id.btn_next);
//                        b.setEnabled(true);
//                    }
//                }
//
//                return false;
//            }
//        });

        Button b = (Button) findViewById(R.id.btn_next);
        b.setEnabled(false);
    }

    public void clear(View view) {
        sigView = (SignatureView) findViewById(R.id.signature_canvas);
        Button b = (Button) findViewById(R.id.btn_next);
        b.setEnabled(false);
        sigView.clearCanvas();
    }

    public void nextClick(View view) {
        String pid = getSharedPreferences("sessionData", MODE_PRIVATE).getString("pid", "");

        //save name
        if(!pid.equals("")) {
            CollectedResults cr = CollectedResults.getInstance(this);
            SQLiteDatabase db = cr.getWritableDatabase();
            ContentValues val = new ContentValues();
            EditText et = (EditText) findViewById(R.id.name);

            val.put("name", et.getText().toString());
            db.update("RESULTS", val, "pid=?", new String[]{pid});
        }

        Intent i = new Intent(this, SurveyQnsDisplayActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consent, menu);
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
