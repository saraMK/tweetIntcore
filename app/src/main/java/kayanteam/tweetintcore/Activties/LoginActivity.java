package kayanteam.tweetintcore.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import kayanteam.tweetintcore.R;
import kayanteam.tweetintcore.Utiles.SharedPrefrnceFile;

public class LoginActivity extends AppCompatActivity {


    private TwitterLoginButton loginButton;
    private SharedPrefrnceFile sharedPrefrnceFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login);

        sharedPrefrnceFile  = new SharedPrefrnceFile(this);

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                sharedPrefrnceFile.saveSharedValue("token",token);
                sharedPrefrnceFile.saveSharedValue("secret",secret);
                login(session);

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d("exception", exception.toString());
            }
        });


    }

    private void login(TwitterSession session) {
        Log.d("sesssion", session.getUserId() + "\n" + session.getUserName());
        sharedPrefrnceFile.saveSharedValue("userID",session.getUserId()+"");
        sharedPrefrnceFile.saveSharedValue("usedName",session.getUserName()+"");
        Intent intent = new Intent(this, FollowersActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
