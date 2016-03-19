package com.elasticbeanstalk.laciecool.first;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;


public class IntroductionQnsActivity extends FragmentActivity {
    IntroPageAdaptor spa;
    QnsViewPager vp;
    User u;
    CollectedResults cr;

    private static final String introQns1 = "Todays’ survey will take about 30 minutes. Are you willing to participate in the survey today?";
    private static final String introQns2 = "Will you be able to provide us with the phone number at which we can contact you in 6 months?";
    private static final String introQns3 = "Will you be willing to talk to us via phone in 6 months?";

    private static final String introQns1Bahasa = "Survei hari ini akan memakan waktu selama kurang lebih 30 menit. Apakah anda bersedia untuk berpartisipasi dalam survei hari ini?";
    private static final String introQns2Bahasa = "Apakah anda dapat memberikan kami nomor telfon yang dapat kami hubungi dalam 6 bulan?";
    private static final String introQns3Bahasa = "Apakah anda dapat menerima telfon kami dalam 6 bulan? ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro_qns);
        List<Fragment> fr = getFragments();
        spa = new IntroPageAdaptor(getSupportFragmentManager(), fr);
        vp = (QnsViewPager) findViewById(R.id.viewpagerintro);
        vp.setAdapter(spa);

        setUserId();
        cr = CollectedResults.getInstance(this);

    }

    public void setUserId() {
        SharedPreferences sp = getSharedPreferences("sessionData", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        int userId = sp.getInt("userId", -1);
        int date = sp.getInt("date", -1);

        //if userId doesnt exist, set as 1
        if(userId == -1) {
            e.putInt("userId", 1);
            userId = 1;
        }

        //if userId exist, check if it is a new day
        else if(date != -1) {
            Date d = new Date();
            DateFormat df = new SimpleDateFormat("dd");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
            int newDate = Integer.parseInt(df.format(d));

            //if it is a new day, reset userId
            if(date != newDate) {
                e.putInt("userId", 1);
                userId = 1;
                setDate();
            }
        }

        else if(date == -1) {
            setDate();
        }



        u = new User(userId, 19);
        e.putInt("userId", userId + 1);
        e.commit();
    }

    public void setDate() {
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("dd");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));

        SharedPreferences sp = getSharedPreferences("sessionData", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        e.putInt("date", Integer.parseInt(df.format(d)));
        e.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    public void saveData() {
        SQLiteDatabase db = cr.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("date", u.getDate());
        v.put("startTime", u.getStartTime());
        v.put("randNum", generateRandNum());
        v.put("phoneRandNum", generatePhoneRandNum());

        //insert introanswers
        JSONObject json = new JSONObject(u.getIntroAns());
        v.put("introAns", json.toString());

        String pid = u.getPid();
        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("pid", pid);
        e.commit();

        int numOfRowsAffected = db.update("RESULTS", v, "pid=?", new String[] { u.getPid()});

        if(numOfRowsAffected == 0) {
            v.put("pid", pid);
            db.insert("RESULTS", null, v);
        }

    }

    public int generateRandNum() {
        Random rand = new Random();
        int randomNum = rand.nextInt(5) + 1;

        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt("randNum", randomNum);
        //e.putInt("randNum", 3);
        e.commit();

        //return 3;
        return randomNum;
    }

    public int generatePhoneRandNum() {
        Random rand = new Random();
        int randomNum = rand.nextInt(5) + 1;

        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt("phoneRandNum", randomNum);
        e.commit();

        return randomNum;
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
        List<Fragment> fList = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);

        if(sp.getInt("lang", 0) == 0) {
            fList.add(IntroFragment.newInstance(introQns1, 0));
            fList.add(IntroFragment.newInstance(introQns2, 1));
            fList.add(IntroFragment.newInstance(introQns3, 2));
        } else {
            fList.add(IntroFragment.newInstance(introQns1Bahasa, 0));
            fList.add(IntroFragment.newInstance(introQns2Bahasa, 1));
            fList.add(IntroFragment.newInstance(introQns3Bahasa, 2));
        }

        return fList;
    }

}
