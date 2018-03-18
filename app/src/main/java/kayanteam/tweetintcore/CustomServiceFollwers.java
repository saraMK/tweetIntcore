package kayanteam.tweetintcore;

import kayanteam.tweetintcore.Models.Followers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mosta on 3/15/2018.
 */

public interface CustomServiceFollwers {
    @GET("/1.1/followers/list.json")
    Call<Followers> show(@Query("user_id") String userId, @Query("screen_name") String
            var, @Query("skip_status") Boolean var1, @Query("include_user_entities") Boolean var2, @Query("count") Integer var3, @Query("cursor") String var4);
}

