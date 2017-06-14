
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hop implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amount")
    @Expose
    private Amount_ amount;
    @SerializedName("add")
    @Expose
    private String add;
    @SerializedName("attribute")
    @Expose
    private String attribute;
    public final static Creator<Hop> CREATOR = new Creator<Hop>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Hop createFromParcel(Parcel in) {
            Hop instance = new Hop();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.amount = ((Amount_) in.readValue((Amount_.class.getClassLoader())));
            instance.add = ((String) in.readValue((String.class.getClassLoader())));
            instance.attribute = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Hop[] newArray(int size) {
            return (new Hop[size]);
        }

    }
    ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Amount_ getAmount() {
        return amount;
    }

    public void setAmount(Amount_ amount) {
        this.amount = amount;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(amount);
        dest.writeValue(add);
        dest.writeValue(attribute);
    }

    public int describeContents() {
        return  0;
    }

}
