package kayanteam.tweetintcore.Activties;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kosalgeek.android.caching.FileCacher;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kayanteam.tweetintcore.Adapters.FriendsAdapter;
import kayanteam.tweetintcore.Models.Followers;
import kayanteam.tweetintcore.MyTwitterApiClient;
import kayanteam.tweetintcore.R;
import kayanteam.tweetintcore.Utiles.Opertions;
import kayanteam.tweetintcore.Utiles.SharedPrefrnceFile;
import retrofit2.Call;

public class FriendsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{


private SharedPrefrnceFile sharedPrefrnceFile;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private FriendsAdapter friendsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String cursor="-1";
    private List<User> userList;
    private List<String>userList2;
    private FileCacher<List<String>> stringCacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main2);
        sharedPrefrnceFile=new SharedPrefrnceFile(this);
        recyclerView=(RecyclerView) findViewById(R.id.frindsActivity_recyclerview);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        userList2=new ArrayList<>();

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
            if (!cursor.equals("0"))
                   loadData();

                }
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
                    Log.i("Get_success", "" + result.data.users);
                    Log.i("Get_success", "" + result.data.nextcusor);

                    // as refresher
                    if (cursor.equals("-1"))
                        userList = new ArrayList<User>();

                    userList.addAll(result.data.users);
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
                        userList2.add("bbb");
                        userList2.add("bbb3");
                        stringCacher.writeCache(userList2);
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
                userList2 = new ArrayList<>();
                userList2 = stringCacher.readCache();
                Log.d("stringCacher",userList2.get(0));
                //setAdapter();
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