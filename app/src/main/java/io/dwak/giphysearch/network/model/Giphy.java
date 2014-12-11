package io.dwak.giphysearch.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vrajeevan on 12/10/14.
 */
public class Giphy {
    @SerializedName("id") String mId;
    @SerializedName("url") String mUrl;
    @SerializedName("images") GiphyImage mGiphyImage;

    public String getId() {
        return mId;
    }

    public String getUrl() {
        return mUrl;
    }

    public GiphyImage getGiphyImage() {
        return mGiphyImage;
    }
}
