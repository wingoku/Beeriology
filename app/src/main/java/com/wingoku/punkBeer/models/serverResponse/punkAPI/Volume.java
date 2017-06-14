
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Volume implements Parcelable
{

    @SerializedName("value")
    @Expose
    private int value;
    @SerializedName("unit")
    @Expose
    private String unit;
    public final static Creator<Volume> CREATOR = new Creator<Volume>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Volume createFromParcel(Parcel in) {
            Volume instance = new Volume();
            instance.value = ((int) in.readValue((int.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Volume[] newArray(int size) {
            return (new Volume[size]);
        }

    }
    ;

    public int getValue() {
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
