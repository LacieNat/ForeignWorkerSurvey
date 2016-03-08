package com.elasticbeanstalk.laciecool.first;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreatmentInfoActivity extends Activity {
    int randNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_info);

        randNum = getSharedPreferences("sessionData", MODE_PRIVATE).getInt("randNum", -1);

        LinearLayout lv = (LinearLayout) findViewById(R.id.infoListView);

        TextView tLabour = new TextView(this);
        tLabour.setText("\u2022 Labour regulations");
        tLabour.setTextColor(Color.BLACK);
        tLabour.setTextSize(30);

        TextView tEmp = new TextView(this);
        tEmp.setText("\u2022 Employment conditions");
        tEmp.setTextColor(Color.BLACK);
        tEmp.setTextSize(30);

        TextView tJob = new TextView(this);
        tJob.setText("\u2022 Job vacancies");
        tJob.setTextColor(Color.BLACK);
        tJob.setTextSize(30);

        switch(randNum) {
            case 1:
                lv.addView(tLabour);
                break;
            case 2:
                lv.addView(tEmp);
                break;
            case 3:
                lv.addView(tJob);
                break;
            case 4:
                lv.addView(tLabour);
                lv.addView(tEmp);
                lv.addView(tJob);
                break;
        }
    }

    public void treatmentInfoNext(View v) {
        Intent i = getIntent();
        switch (randNum) {
            case 1:
                i.setClass(this, EndActivity.class);
                break;
            case 2:
                i.setClass(this, RandomTwoActivity.class);
                break;
            case 3:
                i.setClass(this, RandomThreeActivity.class);
                break;
        }

        startActivity(i);
    }

}
