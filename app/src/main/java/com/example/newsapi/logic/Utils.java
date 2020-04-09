package com.example.newsapi.logic;

import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String editDataText(String dateStr) {
        String serverFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String appFormat = "yyyy-MM-dd HH:mm";

        SimpleDateFormat formatter = new SimpleDateFormat(serverFormat, Locale.getDefault());
        Date date;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            return dateStr; // if fails, returns the original string
        }
        formatter = new SimpleDateFormat(appFormat, Locale.getDefault());
        return formatter.format(date);
    }

    public static RotateAnimation configureRotateAnimation() {
        RotateAnimation rotate = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new OvershootInterpolator());
        return rotate;
    }

}
