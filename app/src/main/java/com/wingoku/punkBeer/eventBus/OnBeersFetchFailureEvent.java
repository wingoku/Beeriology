package com.wingoku.punkBeer.eventBus;

/**
 * Created by Umer on 6/14/2017.
 */

public class OnBeersFetchFailureEvent {
    private String mError;

    public OnBeersFetchFailureEvent(String error) {
        mError = error;
    }

    public String getError() {
        return mError;
    }
}
