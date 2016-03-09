package com.elasticbeanstalk.laciecool.first;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    static final int PICK_CONTACT_REQUEST = 1;
    private SignatureView sigView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.language);
    }

    public void langSelect(View view) {
        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);
        int id = ((RadioGroup) this.findViewById(R.id.language)).getCheckedRadioButtonId();
        SharedPreferences.Editor e = sp.edit();

        //English: 0 and Bahasa:1
        if(id == R.id.english) {
            e.putInt("lang", 0);
        } else {
            e.putInt("lang", 1);
        }

        e.commit();

        Intent i = new Intent(this, IntroductionActivity.class);
        startActivity(i);
    }

//    public void agreeClick(View view) {
//        setContentView(R.layout.signature);
//        sigView = (SignatureView) findViewById(R.id.signature_canvas);
//
//        sigView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN /*&& nameFlag*/) {
//                    Button b = (Button) findViewById(R.id.btn_next);
//                    b.setEnabled(true);
//                }
//
//                return false;
//            }
//        });

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

//        Button b = (Button) findViewById(R.id.btn_next);
//        b.setEnabled(false);
//    }

    public void backClick(View view) {
        setContentView(R.layout.activity_main);
    }

    public void clear(View view) {
        sigView = (SignatureView) findViewById(R.id.signature_canvas);
        Button b = (Button) findViewById(R.id.btn_next);
        b.setEnabled(false);
        sigView.clearCanvas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void nextClick(View view) {
        Intent i = new Intent(this, SurveyQnsDisplayActivity.class);
        startActivity(i);
    }
//
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//
//    }

//    public void sendEmail(View view) {
//        EditText et = (EditText) findViewById(R.id.addresses);
//        String addresses = et.getText().toString();
//        EditText et2 = (EditText) findViewById(R.id.subject);
//        String subject = et2.getText().toString();
//
//        composeEmail(addresses, subject);
//    }

//    public void composeEmail(String addresses, String subject) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        intent.putExtra(EMAIL_SENT, "mailto:" + addresses);
//        startActivity(intent);
//
//    }

//    public void pickContact(View view) {
//        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_CONTACT_REQUEST) {
            // Perform a query to the contact's content provider for the contact's name
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[] {ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
            if (cursor.moveToFirst()) { // True if the cursor is not empty
                int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(columnIndex);
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText(name);
            }
        }
    }
//
//    public void startService(View view) {
//        startService(new Intent(getBaseContext(), Keylogger.class));
//    }
//
//    public void stopService(View view) {
//        stopService(new Intent(getBaseContext(), Keylogger.class));
//    }
}
