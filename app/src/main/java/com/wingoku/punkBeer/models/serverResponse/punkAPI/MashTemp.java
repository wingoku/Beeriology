
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MashTemp implements Parcelable
{

    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("duration")
    @Expose
    private int duration;
    public final static Creator<MashTemp> CREATOR = new Creator<MashTemp>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MashTemp createFromParcel(Parcel in) {
            MashTemp instance = new MashTemp();
            instance.temp = ((Temp) in.readValue((Temp.class.getClassLoader())));
            instance.duration = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public MashTemp[] newArray(int size) {
            return (new MashTemp[size]);
        }

    }
    ;

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(temp);
        dest.writeValue(duration);
    }

    public int describeContents() {
        return  0;
    }

}
