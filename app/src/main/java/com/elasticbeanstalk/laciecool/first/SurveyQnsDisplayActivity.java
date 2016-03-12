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
import android.widget.TextView;
import android.provider.AlarmClock;
import android.net.Uri;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class SurveyQnsDisplayActivity extends FragmentActivity {
    SurveyPageAdaptor spa;
    SurveySQL db;
    HashMap<String, String> surveyQns;

    //randomTwo variables
    HashMap<String, String> randomTwo;
    HashMap<String, String> correctAns;

    //randomThree variables
    HashMap<String, String> randomThree;

    List<Fragment> ls;
    CollectedResults cr;
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        surveyQns = new HashMap<>();
        randomTwo = new HashMap<>();
        randomThree = new HashMap<>();
        correctAns = new HashMap<>();
        cr = CollectedResults.getInstance(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_qns);
        List<Fragment> fr = getFragments();
        spa = new SurveyPageAdaptor(getSupportFragmentManager(), fr);
        ls = fr;

        //get and set user pid
        pid = getSharedPreferences("sessionData", MODE_PRIVATE).getString("pid", "");

        QnsViewPager vp = (QnsViewPager) findViewById(R.id.viewpager);

        vp.setAdapter(spa);

    }

    //on viewpager change, update data
    //on pause, save to database

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

    public void putRandomTwo(String key, String value) {
        randomTwo.put(key, value);
    }

    public void putRandomThree(String key, String value) {
        randomThree.put(key, value);
    }

    public HashMap<String, String> getRandomTwo() {
        return randomTwo;
    }

    public HashMap<String, String> getRandomThree() { return randomThree; }

    public HashMap<String, String> getCorrectAns() {
        return correctAns;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        //initialize database and values
        SQLiteDatabase db = cr.getWritableDatabase();
        ContentValues v = new ContentValues();
        HashMap<String, String> hm = new HashMap<> ();

        //for all fragments
        for(int i=0; i<ls.size(); i++) {
            int answerSize = ((SurveyFragment) ls.get(i)).getAnswers().size();
            String ans = "";

            //if answers is not empty
            if(answerSize>0) {
                for(int j=0; j<answerSize; j++) {
                    ans = ans.equals("")?
                            ((SurveyFragment) ls.get(i)).getAnswers().get(j):
                            ans+"," + ((SurveyFragment) ls.get(i)).getAnswers().get(j);
                }

                //store into map
                hm.put(Integer.toString(i), ans);
                //hm.put(((SurveyFragment) ls.get(i)).getQns(), ans );

            }

            //if answers is empty put no response
            else {
                hm.put(Integer.toString(i), "99");
            }
        }

        JSONObject json = new JSONObject(hm);
        v.put("surveyAns", json.toString());

        db.update("RESULTS", v, "pid=?", new String[]{pid});
    }

    public int getFragmentSize() {
        return ls.size();
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        SurveySQL.forceDatabaseReload(this);
        db = new SurveySQL(this);
        Cursor cGrp = db.getGroupNames();
        Cursor cQns;
        Cursor cOpt;
        int pageIndex = 0;

        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);
        boolean isEnglish = sp.getInt("lang", 0) == 0;

        //*** TODO: INSERT INFO PAGES ****
        //for each group
        //
        for(int i=0; i<cGrp.getCount(); i++) {
        //for(int i=1; i<2; i++){
            String groupName = isEnglish?cGrp.getString(cGrp.getColumnIndexOrThrow("title")):cGrp.getString(cGrp.getColumnIndexOrThrow("titleBahasa"));
            int groupId = cGrp.getInt(cGrp.getColumnIndexOrThrow("id"));

            //groupId = 7;

            cQns = db.getSurveyQnsInOrder(groupId);

            for(int j=0; j<cQns.getCount(); j++) {


                String qns = isEnglish?cQns.getString(cQns.getColumnIndexOrThrow("qns")):cQns.getString(cQns.getColumnIndexOrThrow("qnsBahasa"));
                int qnsId = cQns.getInt(cQns.getColumnIndexOrThrow("id"));
                int grpId = cQns.getInt(cQns.getColumnIndexOrThrow("grp"));
                int order = cQns.getInt(cQns.getColumnIndexOrThrow("ord"));
                int numOfInput = cQns.getInt(cQns.getColumnIndexOrThrow("numOfInput"));
                String units = isEnglish?cQns.getString(cQns.getColumnIndexOrThrow("units")):cQns.getString(cQns.getColumnIndexOrThrow("unitsBahasa"));
                String rating = isEnglish?cQns.getString(cQns.getColumnIndexOrThrow("rating")):cQns.getString(cQns.getColumnIndexOrThrow("ratingBahasa"));
                String optionType = cQns.getString(cQns.getColumnIndexOrThrow("optionType"));
                boolean hasOptions = cQns.getInt(cQns.getColumnIndexOrThrow("hasOptions"))==1?true:false;
                ArrayList<Options> arrOpt = new ArrayList<>();

                cOpt = db.getOptionsFromQnsId(cQns.getInt(cQns.getColumnIndexOrThrow("id")));

                while(cOpt.moveToNext()) {
                    Options o = new Options();
                    o.setAttributes(cOpt.getInt(cOpt.getColumnIndexOrThrow("id")),
                            cOpt.getInt(cOpt.getColumnIndexOrThrow("grp")),
                            cOpt.getInt(cOpt.getColumnIndexOrThrow("qnsId")),
                            isEnglish?cOpt.getString(cOpt.getColumnIndexOrThrow("option")):cOpt.getString(cOpt.getColumnIndexOrThrow("optionBahasa")),
                            cOpt.getString(cOpt.getColumnIndexOrThrow("units")),
                            cOpt.getInt(cOpt.getColumnIndexOrThrow("numOfInput")),
                            cOpt.getInt(cOpt.getColumnIndexOrThrow("value")));

                    arrOpt.add(o);
                }

                if(groupId == 7) {
                    int storeAns = cQns.getInt(cQns.getColumnIndexOrThrow("correctAns"));
                    correctAns.put(Integer.toString(qnsId), Integer.toString(storeAns));
                }

                //grpName, qns, numOfInput, hasOptions, units, hasMulOpts, rating, options
                fList.add(SurveyFragment.newInstance(grpId, qnsId, groupName, qns, numOfInput, hasOptions, optionType, units, rating, arrOpt, pageIndex, order));
                pageIndex++;
                cQns.moveToNext();
            }
            cGrp.moveToNext();
        }

        return fList;
    }

}
