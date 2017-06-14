package com.wingoku.punkBeer.interfaces;

import com.wingoku.punkBeer.models.serverResponse.punkAPI.BeerServer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Umer on 6/14/2017.
 */

public interface PunkAPI {

    String BASE_URL = "https://api.punkapi.com/v2/";

    @GET("beers")
    Observable<List<BeerServer>> getBeers(@Query("per_page") int limit, @Query("page") int offset);

    class Factory {
        private static PunkAPI mPunkAPI;

        /**
         * Get Retrofit instance
         *
         * @return Returns RetroFit instance for Network Calls
         */
        public static PunkAPI getInstance(Retrofit retrofit) {
            if (mPunkAPI == null) {
                mPunkAPI = retrofit.create(PunkAPI.class);
                return mPunkAPI;
            } else {
                return mPunkAPI;
            }
        }
    }
}
