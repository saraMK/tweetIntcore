package kayanteam.tweetintcore;

import java.util.List;

import kayanteam.tweetintcore.Models.TimelineModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mosta on 3/17/2018.
 */

public interface CustomService {
    @GET("/1.1/statuses/user_timeline/list.json")
    Call<List<TimelineModel>> show(@Query("user_id") String userId , @Query("count") Integer var3 );
}
