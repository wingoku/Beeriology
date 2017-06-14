package com.wingoku.punkBeer.models.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Umer on 6/14/2017.
 */

public class Beers implements Parcelable {

    private List<Beer> beerList;

    public List<Beer> getBeerList() {
        return beerList;
    }

    public void setBeerServerList(List<Beer> beerList) {
        this.beerList = beerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.beerList);
    }

    public Beers() {
        beerList = new ArrayList<>();
    }

    protected Beers(Parcel in) {
        this.beerList = in.createTypedArrayList(Beer.CREATOR);
    }

    public static final Creator<Beers> CREATOR = new Creator<Beers>() {
        @Override
        public Beers createFromParcel(Parcel source) {
            return new Beers(source);
        }

        @Override
        public Beers[] newArray(int size) {
            return new Beers[size];
        }
    };
}