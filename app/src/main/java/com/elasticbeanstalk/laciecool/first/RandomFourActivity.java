package com.elasticbeanstalk.laciecool.first;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class RandomFourActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("sessionData", Context.MODE_PRIVATE);

        if (sp.getInt("lang", 0) == 0) {
            setContentView(R.layout.display_pdf);
        } else {
            setContentView(R.layout.display_pdf_bahasa);
        }

        TouchImageView iv = (TouchImageView) this.findViewById(R.id.displayPdf);
//        iv.setZoom(1);
//        iv.setMaxZoom(5);

        iv.setImageResource(R.drawable.job_vacancy);

        Button b = (Button) this.findViewById(R.id.randomPrev);
        b.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pi, menu);
        return true;
    }

    public void onNext(View v) {
        Intent i = new Intent(this, EndActivity.class);
        startActivity(i);
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
