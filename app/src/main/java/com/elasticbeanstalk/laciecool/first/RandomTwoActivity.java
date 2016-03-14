package com.elasticbeanstalk.laciecool.first;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RandomTwoActivity extends FragmentActivity {

    SurveyPageAdaptor spa;
    SurveySQL db;
    CollectedResults cr;
    String pid;
    String phoneNum;
    HashMap<String, String> userAns;
    HashMap<String, String> correctAns;
    List<Fragment> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_two);

        //get and set user pid
        Intent preIntent = getIntent();
        pid = getSharedPreferences("sessionData", MODE_PRIVATE).getString("pid", "");


        userAns = (HashMap<String, String>) preIntent.getSerializableExtra("userAns2");
        correctAns = (HashMap<String, String>) preIntent.getSerializableExtra("correctAns");
        phoneNum = preIntent.getStringExtra("phoneNum");

        List<Fragment> fr = getFragments();
        ls = fr;
        spa = new SurveyPageAdaptor(getSupportFragmentManager(), fr);
        cr = CollectedResults.getInstance(this);

        QnsViewPager vp = (QnsViewPager) findViewById(R.id.resultViewpager);
        vp.setAdapter(spa);
    }

    public int getFragmentSize() {
        return ls.size();
    }

    public List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        //SurveySQL.forceDatabaseReload(this);
        boolean isEnglish = getSharedPreferences("sessionData", Context.MODE_PRIVATE).getInt("lang", 0) == 0;

        db = new SurveySQL(this);
        Cursor cQns = db.getSurveyQnsInOrder(7);

        for(int i=0; i<5; i++) {
            String qns = isEnglish?cQns.getString(cQns.getColumnIndexOrThrow("qns")):cQns.getString(cQns.getColumnIndexOrThrow("qnsBahasa"));
            int qnsId = cQns.getInt(cQns.getColumnIndexOrThrow("id"));
            fList.add(RandomTwoActivityFragment.newInstance(qns, userAns.get(Integer.toString(qnsId)), correctAns.get(Integer.toString(qnsId)), i));
            cQns.moveToNext();
        }

        fList.add(RandomTwoActivityFragment.newInstance("", "", "", 5));

        return fList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_random_two, menu);
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
