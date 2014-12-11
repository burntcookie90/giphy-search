package io.dwak.giphysearch.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vrajeevan on 12/10/14.
 */
public class GiphyImage {
    @SerializedName("original_still") GiphyOriginalStill mGiphyOriginalStill;
    @SerializedName("original") GiphyOriginal mGiphyOriginal;
    @SerializedName("fixed_width") GiphyFixedWidth mGiphyFixedWidth;
    @SerializedName("fixed_height") GiphyFixedHeight mGiphyFixedHeight;
    @SerializedName("fixed_width_downsampled") GiphyFixedWidthDownSampled mGiphyFixedWidthDownSampled;

    public GiphyOriginalStill getGiphyOriginalStill() {
        return mGiphyOriginalStill;
    }

    public GiphyOriginal getGiphyOriginal() {
        return mGiphyOriginal;
    }

    public GiphyFixedWidth getGiphyFixedWidth() {
        return mGiphyFixedWidth;
    }

    public GiphyFixedHeight getGiphyFixedHeight() {
        return mGiphyFixedHeight;
    }

    public GiphyFixedWidthDownSampled getGiphyFixedWidthDownSampled() {
        return mGiphyFixedWidthDownSampled;
    }
}
