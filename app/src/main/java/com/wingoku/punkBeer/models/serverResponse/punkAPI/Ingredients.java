
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Ingredients implements Parcelable
{

    @SerializedName("malt")
    @Expose
    private List<Malt> malt = new ArrayList<Malt>();
    @SerializedName("hops")
    @Expose
    private List<Hop> hops = new ArrayList<Hop>();
    @SerializedName("yeast")
    @Expose
    private String yeast;
    public final static Creator<Ingredients> CREATOR = new Creator<Ingredients>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Ingredients createFromParcel(Parcel in) {
            Ingredients instance = new Ingredients();
            in.readList(instance.malt, (com.wingoku.punkBeer.models.serverResponse.punkAPI.Malt.class.getClassLoader()));
            in.readList(instance.hops, (Hop.class.getClassLoader()));
            instance.yeast = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Ingredients[] newArray(int size) {
            return (new Ingredients[size]);
        }

    }
    ;

    public List<Malt> getMalt() {
        return malt;
    }

    public void setMalt(List<Malt> malt) {
        this.malt = malt;
    }

    public List<Hop> getHops() {
        return hops;
    }

    public void setHops(List<Hop> hops) {
        this.hops = hops;
    }

    public String getYeast() {
        return yeast;
    }

    public void setYeast(String yeast) {
        this.yeast = yeast;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(malt);
        dest.writeList(hops);
        dest.writeValue(yeast);
    }

    public int describeContents() {
        return  0;
    }

}
