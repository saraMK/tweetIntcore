package kayanteam.tweetintcore.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.List;

import kayanteam.tweetintcore.Adapters.FriendsAdapter;
import kayanteam.tweetintcore.Models.FolwoersModel;
import kayanteam.tweetintcore.Models.TimelineModel;
import kayanteam.tweetintcore.MyTwitterApiClient;
import kayanteam.tweetintcore.R;
import kayanteam.tweetintcore.Utiles.Opertions;
import kayanteam.tweetintcore.Utiles.SharedPrefrnceFile;
import retrofit2.Call;

public class TimeLineActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageView profilebannerImg;
    private ImageView profileImg;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String imgUrl,imgBgUrl,name,userId,language;
    private List<FolwoersModel>timeLineList;
    private FriendsAdapter friendsAdapter;
    private SharedPrefrnceFile sharedPrefrnceFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_time_line);
        sharedPrefrnceFile=new SharedPrefrnceFile(this);
        language=sharedPrefrnceFile.getSharedValue("lang","1");
        sharedPrefrnceFile.set_loclization(language,this);
        // init views
        toolbar=(Toolbar)findViewById(R.id.anim_toolbar);
        appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        profilebannerImg=(ImageView)findViewById(R.id.header_image);
        profileImg=(ImageView)findViewById(R.id.profile_imge);
        recyclerView=(RecyclerView)findViewById(R.id.scrollableview);
        timeLineList=new ArrayList<>();
        // recieve data
        name=getIntent().getExtras().getString("name");
        imgUrl=getIntent().getExtras().getString("imgUrl");
        imgBgUrl=getIntent().getExtras().getString("imgBgUrl");
        userId=getIntent().getExtras().getString("userId");

        // set toobar data
        Opertions.setToolbarValue(this,toolbar,name,language,R.color.tw__composer_white);

        Picasso.with(this).load(imgUrl).placeholder(R.mipmap.twitter_icon).into(profileImg);
        Picasso.with(this).load(imgBgUrl).placeholder(R.mipmap.twitter_icon).into(profilebannerImg);


        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);



      setTimeLineStatus();

        profilebannerImg.setOnClickListener(this);
        profileImg.setOnClickListener(this);

    }

    private void setTimeLineStatus() {
        Call<List<TimelineModel>> call = new MyTwitterApiClient(Opertions.getSession()).getCustomService().show(userId, 10);
        call.request().url();
        Log.i("Get_success", "" + call.request().url());
        call.enqueue(new Callback<List<TimelineModel>>() {
            @Override
            public void success(Result<List<TimelineModel>> result) {
                try {
                    Log.i("Get_success", "" + result.data);

                    for (int i=0;i<result.data.size();i++){

                        Log.i("Get_success", "" + result.data.get(i).bnner);
                        timeLineList.add(new FolwoersModel(imgUrl,name,result.data.get(i).text,"",imgBgUrl));

                    }
                    friendsAdapter=new FriendsAdapter(timeLineList,TimeLineActivity.this);
                    recyclerView.setAdapter(friendsAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(TwitterException exception) {
                Log.i("Get_success", "" + exception);
            }
        });


    }

    @Override
    public void onClick(View v) {
        String url="";
        if (v==profilebannerImg)
            url=imgBgUrl;
        else if (v==profileImg)
            url=imgUrl;

        Intent intent=new Intent(this,ZoomImageActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
