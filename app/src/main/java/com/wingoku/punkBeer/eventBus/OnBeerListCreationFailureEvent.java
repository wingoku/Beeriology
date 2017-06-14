package com.wingoku.punkBeer.eventBus;

/**
 * Created by Umer on 6/14/2017.
 */

public class OnBeerListCreationFailureEvent {
    private String mError;

    public OnBeerListCreationFailureEvent(String localizedMessage) {
        mError = localizedMessage;
    }

    public String getError() {
        return mError;
    }
}
