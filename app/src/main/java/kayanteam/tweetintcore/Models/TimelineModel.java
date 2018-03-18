package kayanteam.tweetintcore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mosta on 3/16/2018.
 */

public class TimelineModel {
    @SerializedName("text")
    @Expose
    public final String text;

    public TimelineModel(String text) {
        this.text = text;
     }
}
