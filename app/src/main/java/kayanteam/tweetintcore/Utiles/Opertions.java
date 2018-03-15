package kayanteam.tweetintcore.Utiles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by mosta on 3/15/2018.
 */

public class Opertions {


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
}
