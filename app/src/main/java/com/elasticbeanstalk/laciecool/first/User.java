package com.elasticbeanstalk.laciecool.first;

import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by lacie on 1/24/16.
 */
public class User {
    private String pid;
    private int date;
    private int startTime;
    private HashMap<String, String> introAns;
//    private String name;
//    private ArrayList<String> surveyAns;
//    private int randNum;
//    private int endTime;

    public User(int userId, int tablet) {

        Date d = new Date();
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));

        DateFormat dfTime = new SimpleDateFormat("HHmm");
        dfTime.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));

        this.date = Integer.parseInt(df.format(d));
        this.startTime = Integer.parseInt(dfTime.format(d));
        this.introAns = new HashMap<String,String>();
//        this.surveyAns = new ArrayList<String>();

        this.pid = generatePid(userId, tablet);
    }

    public int getDate() {
        return this.date;
    }

    public String generatePid(int userId, int tablet) {
        String u = Integer.toString(userId);
        if(u.length()<2) {
            u = "0" + u;
        }

        String t = Integer.toString(tablet);
        if (t.length()<2) {
            t = "0" + t;
        }

        Date d = new Date();
        DateFormat df = new SimpleDateFormat("ddMM");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));

        return t + df.format(d) + u;
    }

    public void addIntroAns(int index, int res) {
        this.introAns.put(Integer.toString(index), Integer.toString(res));
    }

    public HashMap<String, String> getIntroAns() {
        return this.introAns;
    }

//    public boolean addSurveyAns(String res) {
//        return surveyAns.add(res);
//    }

    public String getPid() {
        return pid;
    }

    public int getStartTime() {
        return startTime;
    }

//    public void setRandNum(int randNum) {
//        this.randNum = randNum;
//    }
//
//    public void setEndTime() {
//        Date d = new Date();
//        DateFormat df = new SimpleDateFormat("HHmm");
//        this.endTime = Integer.parseInt(df.format(d));
//    }
}
