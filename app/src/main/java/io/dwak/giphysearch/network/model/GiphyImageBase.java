package io.dwak.giphysearch.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vrajeevan on 12/10/14.
 */
public class GiphyImageBase {
    @SerializedName("url") String mUrl;
    @SerializedName("width") int mWidth;
    @SerializedName("height") int mHeight;
    @SerializedName("size") long mSize;

    public String getUrl() {
        return mUrl;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public long getSize() {
        return mSize;
    }
}
