package kayanteam.tweetintcore;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by mosta on 3/15/2018.
 */




public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

     public CustomServiceFollwers getCustomService2() {
        return getService(CustomServiceFollwers.class);
    }
    public CustomServiceStatus getCustomService() {
        return getService(CustomServiceStatus.class);
    }

}


