package com.android.tutotials.facebooklogintest;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oussamakaoui on 10/19/17.
 */

public class UtilityClass {

    public static Date getDate(String sdate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


        Date date = null;
        try {
            date = sdf.parse(sdate);
            //Log.d("date",date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
