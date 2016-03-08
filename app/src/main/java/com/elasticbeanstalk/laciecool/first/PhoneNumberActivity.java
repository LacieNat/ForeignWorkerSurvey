package com.elasticbeanstalk.laciecool.first;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PhoneNumberActivity extends Activity {
    private String[] phoneNums;
    int randNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_phone_num_info);
        phoneNums = new String[] {"81942076", "81947478", "81740112", "81967559", "82548102"};
        TextView tv = (TextView) findViewById(R.id.savePhoneNum);

        randNum = getSharedPreferences("sessionData", MODE_PRIVATE).getInt("phoneRandNum", -1);
        tv.setText(phoneNums[randNum-1]);
    }

    public void savePhoneNumNext(View v) {
        Intent i = getIntent();
        i.setClass(this, TreatmentInfoActivity.class);

        startActivity(i);
    }
}
