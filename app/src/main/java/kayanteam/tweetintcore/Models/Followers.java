package kayanteam.tweetintcore.Models;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by mosta on 3/15/2018.
 */

public class Followers {
    @SerializedName("users")
    public final List<User> users;
    @SerializedName("next_cursor_str")
    public final String nextcusor;



    public Followers(List<User> users, String nextcusor ) {
        this.users = users;
        this.nextcusor=nextcusor;

    }

}
