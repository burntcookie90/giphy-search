package io.dwak.giphysearch.network;

import io.dwak.giphysearch.network.model.SearchResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by vrajeevan on 12/10/14.
 */
public interface GiphyService {
    @GET("/v1/gifs/search")
    Observable<SearchResponse> search(@Query("q") String searchQuery, @Query("api_key") String apiKey, @Query("limit") int limit);
}
