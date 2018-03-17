package kayanteam.tweetintcore.Models;

import java.io.Serializable;

/**
 * Created by mosta on 3/16/2018.
 */

public class FolwoersModel implements Serializable {
    String imgUrl,name,bio,id,bGimage;

    public FolwoersModel(String imgUrl, String name, String bio, String id,String bGimagr) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.bio = bio;
        this.id = id;
        this.bGimage = bGimagr;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getbGimage() {
        return bGimage;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getId() {
        return id;
    }
}
