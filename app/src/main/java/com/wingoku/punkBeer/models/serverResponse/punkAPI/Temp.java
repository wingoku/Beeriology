
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp implements Parcelable
{

    @SerializedName("value")
    @Expose
    private int value;
    @SerializedName("unit")
    @Expose
    private String unit;
    public final static Creator<Temp> CREATOR = new Creator<Temp>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Temp createFromParcel(Parcel in) {
            Temp instance = new Temp();
            instance.value = ((int) in.readValue((int.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Temp[] newArray(int size) {
            return (new Temp[size]);
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
