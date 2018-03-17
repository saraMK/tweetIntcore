package kayanteam.tweetintcore.Activties;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kosalgeek.android.caching.FileCacher;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kayanteam.tweetintcore.Adapters.FriendsAdapter;
import kayanteam.tweetintcore.Models.Followers;
import kayanteam.tweetintcore.Models.FolwoersModel;
import kayanteam.tweetintcore.MyTwitterApiClient;
import kayanteam.tweetintcore.R;
import kayanteam.tweetintcore.Utiles.Opertions;
import kayanteam.tweetintcore.Utiles.SharedPrefrnceFile;
import retrofit2.Call;

public class FriendsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{


private SharedPrefrnceFile sharedPrefrnceFile;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageView change_lang;
    private GridLayoutManager gridLayoutManager;
    private FriendsAdapter friendsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String cursor="-1";
    private List<FolwoersModel> userList;
    private String language;

    private FileCacher<List<FolwoersModel>> stringCacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main2);
        sharedPrefrnceFile=new SharedPrefrnceFile(this);
        language=sharedPrefrnceFile.getSharedValue("lang","1");
        sharedPrefrnceFile.set_loclization(language,FriendsActivity.this);
        // init views
        recyclerView=(RecyclerView) findViewById(R.id.frindsActivity_recyclerview);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        change_lang=(ImageView)toolbar.findViewById(R.id.change_language);
        userList=new ArrayList<>();

         stringCacher = new FileCacher<>(FriendsActivity.this, "sometext.txt");




      setlistOrintation(1);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                loadData();
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
        if (gridLayoutManager.findLastCompletelyVisibleItemPosition()==userList.size()-1) {
                    Log.d("scrolling","scrolling");
          //  if cursor.equals("0") mean no iteam to load and it is the end
            if (!cursor.equals("0") )
                if (Opertions.isNetworkAvailable(FriendsActivity.this))
                   loadData();

                }
            }
        });


        change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1 >> en , 2 >>ar
                if (language.equals("1")){
                    language="2";

                }
                    else{
                    language="1";
                  }
                sharedPrefrnceFile.saveSharedValue("lang",language);
                sharedPrefrnceFile.set_loclization(language,FriendsActivity.this);
                Intent intent=new Intent(getApplicationContext(),FriendsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onRefresh() {
// set swipe refresh
        mSwipeRefreshLayout.setRefreshing(true);
        cursor="-1";
        loadData();
    }

    private void loadData() {


        if (Opertions.isNetworkAvailable(this)) {
            Call<Followers> call = new MyTwitterApiClient(Opertions.getSession()).getCustomService2().
                    show(sharedPrefrnceFile.getSharedValue("userID", ""), null, true, true, 10, cursor);
            call.request().url();
            Log.i("Get_success", "" + call.request().url());
            Log.i("Get_success", "" + sharedPrefrnceFile.getSharedValue("userID", "00"));
            Log.i("Get_success", "" + Opertions.getSession());
            call.enqueue(new Callback<Followers>() {
                @Override
                public void success(Result<Followers> result) {

                    Log.i("Get_success", "" + result.data);

                    // as refresher
                    if (cursor.equals("-1"))
                        userList = new ArrayList<>();

                    for (int i=0;i<result.data.users.size();i++){
                        userList.add(new FolwoersModel(result.data.users.get(i).profileImageUrl,result.data.users.get(i).name,
                            result.data.users.get(i).description,result.data.users.get(i).idStr,result.data.users.get(i).profileBackgroundImageUrl));
                        Log.i("Get_success", "" + result.data.users.get(i).idStr);
                    }
                    Log.d("sizelist", userList.size() + "");
                    if (friendsAdapter == null) {
                        setAdapter();
                    } else
                        friendsAdapter.notifyDataSetChanged();


                    cursor = result.data.nextcusor;

                    // Stopping swipe refresh
                    mSwipeRefreshLayout.setRefreshing(false);
                    // cash list
                    try {

                        stringCacher.writeCache(userList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void failure(TwitterException exception) {
                    Log.i("Get_success", "" + exception);
                    // Stopping swipe refresh
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        else {
            try {

                mSwipeRefreshLayout.setRefreshing(false);
                userList = new ArrayList<>();
                userList = stringCacher.readCache();
                Log.d("stringCacher",userList.get(0).getBio());
                 setAdapter();
            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }

    private void setlistOrintation(int numberOfItems){
        gridLayoutManager=new GridLayoutManager(this,numberOfItems);
        recyclerView.setLayoutManager(gridLayoutManager);

    }
    private void setAdapter(){
        friendsAdapter=new FriendsAdapter(userList,FriendsActivity.this);
        recyclerView.setAdapter(friendsAdapter);

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("newConfig","newConfig");
        if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT)
            setlistOrintation(1);
        else
            setlistOrintation(2);

        friendsAdapter=new FriendsAdapter(userList,FriendsActivity.this);
        recyclerView.setAdapter(friendsAdapter);
        friendsAdapter.notifyDataSetChanged();
    }
}