package com.project.newsapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Util {

    public static String getTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date();
        try {
            Date published = sdf.parse(time);

            long mlDiff = Math.abs(published.getTime() - now.getTime());
            long diffDay = TimeUnit.DAYS.convert(mlDiff, TimeUnit.MILLISECONDS);
            long diffHour = TimeUnit.HOURS.convert(mlDiff, TimeUnit.MILLISECONDS);
            long diffMin = TimeUnit.MINUTES.convert(mlDiff, TimeUnit.MILLISECONDS);

            if(diffDay > 0) return diffDay + " days ago";
            else if(diffHour > 0) return diffHour + " hours ago";
            else if(diffMin > 0) return diffMin + " minutes ago";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "Just now";
    }
}

