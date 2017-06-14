package com.wingoku.punkBeer.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.wingoku.punkBeer.R;

import java.util.Calendar;

/**
 * Created by Umer on 6/14/2017.
 */

public class Utils {
    /**
     * This method will return a Snackbar object
     *
     * @param con Activity/Application context
     * @param view View with which {@link Snackbar} will be attached
     * @return Useable Snackbar object
     */
    public static Snackbar initSnackbar(Context con, View view) {
        final Snackbar snackBar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        snackBar.setAction(con.getString(R.string.ok_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.setActionTextColor(Color.GREEN);

        return snackBar;
    }

    /**
     * Check if network is available
     * @param con Context of the activity/fragment/application
     * @return true/false for network availability and offline respectively
     */
    public static boolean isNetworkAvailable(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * Get date in String
     * @param daysPast number of days to go back to from current date
     * @return returns date string in dd/mm format
     */
    public static String getDate(int daysPast) {
        //Calendar set to the current date
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysPast*-1);

        return calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH);
    }
}
