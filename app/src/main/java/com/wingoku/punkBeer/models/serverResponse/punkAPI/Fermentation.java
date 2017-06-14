
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fermentation implements Parcelable
{

    @SerializedName("temp")
    @Expose
    private Temp_ temp;
    public final static Creator<Fermentation> CREATOR = new Creator<Fermentation>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Fermentation createFromParcel(Parcel in) {
            Fermentation instance = new Fermentation();
            instance.temp = ((Temp_) in.readValue((Temp_.class.getClassLoader())));
            return instance;
        }

        public Fermentation[] newArray(int size) {
            return (new Fermentation[size]);
        }

    }
    ;

    public Temp_ getTemp() {
        return temp;
    }

    public void setTemp(Temp_ temp) {
        this.temp = temp;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(temp);
    }

    public int describeContents() {
        return  0;
    }

}
