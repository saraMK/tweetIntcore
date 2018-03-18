package kayanteam.tweetintcore.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import kayanteam.tweetintcore.R;
import kayanteam.tweetintcore.Utiles.SharedPrefrnceFile;

public class SplashActivity extends AppCompatActivity {

    private SharedPrefrnceFile sharedPrefrnceFile;
    private Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPrefrnceFile=new SharedPrefrnceFile(this);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the LoginActivityAct-Activity. */
                if (sharedPrefrnceFile.getSharedValue("userID","").isEmpty())
                 mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                else
                    mainIntent = new Intent(SplashActivity.this,FollowersActivity.class);
                startActivity(mainIntent);
                finish();

            }
        }, 3000);
    }
}
