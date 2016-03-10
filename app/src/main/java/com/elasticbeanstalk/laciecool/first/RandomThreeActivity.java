package com.elasticbeanstalk.laciecool.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RandomThreeActivity extends FragmentActivity {

    SurveyPageAdaptor spa;
    SurveySQL db;
    CollectedResults cr;
    String pid;
    HashMap<String, String> userAns;
    List<Fragment> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_three);

        //get and set user pid
        Intent preIntent = getIntent();
        pid = getSharedPreferences("sessionData", MODE_PRIVATE).getString("pid", "");
        userAns = (HashMap<String, String>) preIntent.getSerializableExtra("userAns3");

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
        int pageIndex = 0;
        boolean isEnglish = getSharedPreferences("sessionData", Context.MODE_PRIVATE).getInt("lang", 0) == 0;

        //SurveySQL.forceDatabaseReload(this);
        db = new SurveySQL(this);

        for(String key: userAns.keySet()) {
            Cursor cQns = db.getSurveyQnsFromQnsId(Integer.parseInt(key));
            String qns = isEnglish?cQns.getString(cQns.getColumnIndexOrThrow("qns")):cQns.getString(cQns.getColumnIndexOrThrow("qnsBahasa"));
            int qnsId = cQns.getInt(cQns.getColumnIndexOrThrow("id"));

            for (int i = 0; i < 3; i++) {

                if(!userAns.get(Integer.toString(qnsId)).equals("99") && !userAns.get(Integer.toString(qnsId)).equals("-1")) {
                    fList.add(RandomThreeActivityFragment.newInstance(qnsId, qns, userAns.get(key), i, pageIndex++));
                }

            }

            if(hasPartB(Integer.parseInt(userAns.get(key)))) {
                fList.add(RandomThreeActivityFragment.newInstance(qnsId, qns, userAns.get(key), 3, pageIndex++));
            }

            if(hasPartC(qnsId, Integer.parseInt(userAns.get(key)))) {
                fList.add(RandomThreeActivityFragment.newInstance(qnsId, qns, userAns.get(key), 4, pageIndex++));
            }

            cQns.moveToNext();
        }

        return fList;
    }

    public boolean hasPartB(int ans) {
        boolean hpb = true;
        if (ans == -1 || ans == 99) {
            hpb = false;
        }

        return hpb;
    }

    public boolean hasPartC(int qId, int ans) {
        boolean hpc = true;
        if(qId == 22) {

            if(ans > 700 || ans <=400) {
                hpc = false;
            }
        }

        else if(qId == 81) {

            if(ans<8 || ans >14) {
                hpc = false;
            }
        }

        else if(qId == 21) {

            if(ans >= 4 || ans<=1) {
                hpc = false;
            }
        }

        else {

            if(ans == 5 || ans == 1) {
                hpc = false;
            }
        }

        return hpc;
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
