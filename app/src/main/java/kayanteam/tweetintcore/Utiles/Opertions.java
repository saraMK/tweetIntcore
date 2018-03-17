package kayanteam.tweetintcore.Utiles;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import kayanteam.tweetintcore.R;

/**
 * Created by mosta on 3/15/2018.
 */

public class Opertions {

    public static  String json;

    public static void setJson(String json) {
        Opertions.json = json;
    }

    public static String getJson() {
        return json;
    }

    public static TwitterSession getSession()
    {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return session;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setToolbarValue(AppCompatActivity activity, Toolbar toolbar, String title, String languge , int color){
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.back_en, null);
        if (languge.equals("2"))
            upArrow = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.back_ar, null);
        upArrow.setColorFilter(ContextCompat.getColor(activity,color), PorterDuff.Mode.SRC_ATOP);
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);

        activity.getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(ContextCompat.getColor(activity,color));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(activity,color));
    }
}
