package com.wingoku.punkBeer.eventBus;

import com.wingoku.punkBeer.models.serverResponse.punkAPI.BeerServer;

import java.util.List;

/**
 * Created by Umer on 6/14/2017.
 */

public class OnBeersFetchSuccessEvent {
    private List<BeerServer> mList;

    public OnBeersFetchSuccessEvent(List<BeerServer> beerServerList) {
        mList = beerServerList;
    }

    public List<BeerServer> getBeerList() {
        return mList;
    }
}
