package com.elasticbeanstalk.laciecool.first;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class IntroductionQnsActivity extends FragmentActivity {
    IntroPageAdaptor spa;
    QnsViewPager vp;
    SurveySQL db;
    User u;
    CollectedResults cr;
    public static int userId = 1;

    private static final String introQns1 = "Todays’ survey will take about 30 minutes. Are you willing to participate in the survey today?";
    private static final String introQns2 = "Will you be able to provide us with the phone number at which we can contact you in 6 months?";
    private static final String introQns3 = "Will you be willing to talk to us via phone in 6 months?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro_qns);
        List<Fragment> fr = getFragments();
        spa = new IntroPageAdaptor(getSupportFragmentManager(), fr);
        vp = (QnsViewPager) findViewById(R.id.viewpagerintro);
        vp.setAdapter(spa);

        u = new User(userId, 1, 1);
        cr = CollectedResults.getInstance(this);
        userId++;

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    public void saveData() {
        SQLiteDatabase db = cr.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("location", u.getLocation());
        v.put("date", u.getDate());
        v.put("startTime", u.getStartTime());

        //insert introanswers
        JSONObject json = new JSONObject(u.getIntroAns());
        v.put("introAns", json.toString());

        int pid = u.getPid();
        int numOfRowsAffected = db.update("RESULTS", v, "pid=?", new String[] { Integer.toString(u.getPid()) });

        if(numOfRowsAffected == 0) {
            v.put("pid", pid);
            db.insert("RESULTS", null, v);
        }

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

    public void introNextQns(View v) {
        int currentItem = vp.getCurrentItem();
        int res = ((IntroFragment)getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpagerintro+":" + currentItem)).getRadioResult();

        if(res == 0) {
            res = 1;
        } else {
            res = 0;
        }

        u.addIntroAns(currentItem, res);

        if(res==0) {
            Intent i = new Intent(this, EndActivity.class);
            startActivity(i);
        } else if(currentItem == 0 || currentItem == 1){
            vp.setCurrentItem(currentItem + 1);
        } else {
            Intent j = new Intent(this, PIActivity.class);
            j.putExtra("pid", u.getPid());
            startActivity(j);
        }
    }

    public void introPrevQns(View v) {
        int currentItem = vp.getCurrentItem();

        if(currentItem == 1 || currentItem == 2){
            vp.setCurrentItem(currentItem - 1);
        }
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(IntroFragment.newInstance(introQns1));
        fList.add(IntroFragment.newInstance(introQns2));
        fList.add(IntroFragment.newInstance(introQns3));

        return fList;
    }

}
