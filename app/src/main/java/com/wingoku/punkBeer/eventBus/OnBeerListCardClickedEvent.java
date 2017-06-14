package com.wingoku.punkBeer.eventBus;

/**
 * Created by Umer on 6/14/2017.
 */

public class OnBeerListCardClickedEvent {
    private int mClickedItemPosition;

    public OnBeerListCardClickedEvent(int itemPosition) {
        mClickedItemPosition = itemPosition;
    }

    public int getClickedItemPosition() {
        return mClickedItemPosition;
    }
}
