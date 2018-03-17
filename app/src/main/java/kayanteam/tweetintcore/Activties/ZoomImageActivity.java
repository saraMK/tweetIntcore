package kayanteam.tweetintcore.Activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import kayanteam.tweetintcore.R;
import kayanteam.tweetintcore.Utiles.TouchImageView;

public class ZoomImageActivity extends AppCompatActivity {


    TouchImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);

        mImageView = (TouchImageView) findViewById(R.id.zommimageactivity_imageview);

        Picasso.with(this).load(getIntent().getExtras().getString("url")).placeholder(R.mipmap.twitter_icon).into(mImageView);


    }

}
