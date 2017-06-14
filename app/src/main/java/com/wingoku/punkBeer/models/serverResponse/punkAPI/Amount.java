
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Amount implements Parcelable
{

    @SerializedName("value")
    @Expose
    private double value;
    @SerializedName("unit")
    @Expose
    private String unit;
    public final static Creator<Amount> CREATOR = new Creator<Amount>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Amount createFromParcel(Parcel in) {
            Amount instance = new Amount();
            instance.value = ((double) in.readValue((double.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Amount[] newArray(int size) {
            return (new Amount[size]);
        }

    }
    ;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(value);
        dest.writeValue(unit);
    }

    public int describeContents() {
        return  0;
    }

}
