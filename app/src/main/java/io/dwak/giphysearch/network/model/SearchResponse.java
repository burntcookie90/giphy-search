package io.dwak.giphysearch.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vrajeevan on 12/10/14.
 */
public class SearchResponse {
    @SerializedName("data") List<Giphy> mGiphyList;

    public List<Giphy> getGiphyList() {
        return mGiphyList;
    }
}
