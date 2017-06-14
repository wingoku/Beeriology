
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp_ implements Parcelable
{

    @SerializedName("value")
    @Expose
    private double value;
    @SerializedName("unit")
    @Expose
    private String unit;
    public final static Creator<Temp_> CREATOR = new Creator<Temp_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Temp_ createFromParcel(Parcel in) {
            Temp_ instance = new Temp_();
            instance.value = ((double) in.readValue((double.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Temp_[] newArray(int size) {
            return (new Temp_[size]);
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
