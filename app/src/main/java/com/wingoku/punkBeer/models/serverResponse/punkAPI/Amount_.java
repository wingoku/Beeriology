
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Amount_ implements Parcelable
{

    @SerializedName("value")
    @Expose
    private double value;
    @SerializedName("unit")
    @Expose
    private String unit;
    public final static Creator<Amount_> CREATOR = new Creator<Amount_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Amount_ createFromParcel(Parcel in) {
            Amount_ instance = new Amount_();
            instance.value = ((int) in.readValue((int.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Amount_[] newArray(int size) {
            return (new Amount_[size]);
        }

    }
    ;

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
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
