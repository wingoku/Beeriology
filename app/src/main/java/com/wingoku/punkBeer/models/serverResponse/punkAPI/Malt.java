
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Malt implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amount")
    @Expose
    private Amount amount;
    public final static Creator<Malt> CREATOR = new Creator<Malt>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Malt createFromParcel(Parcel in) {
            Malt instance = new Malt();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.amount = ((Amount) in.readValue((Amount.class.getClassLoader())));
            return instance;
        }

        public Malt[] newArray(int size) {
            return (new Malt[size]);
        }

    }
    ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(amount);
    }

    public int describeContents() {
        return  0;
    }

}
