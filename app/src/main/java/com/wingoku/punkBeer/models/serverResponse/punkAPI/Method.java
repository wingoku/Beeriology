
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Method implements Parcelable
{

    @SerializedName("mash_temp")
    @Expose
    private List<MashTemp> mashTemp = new ArrayList<MashTemp>();
    @SerializedName("fermentation")
    @Expose
    private Fermentation fermentation;
    @SerializedName("twist")
    @Expose
    private Object twist;
    public final static Creator<Method> CREATOR = new Creator<Method>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Method createFromParcel(Parcel in) {
            Method instance = new Method();
            in.readList(instance.mashTemp, (MashTemp.class.getClassLoader()));
            instance.fermentation = ((Fermentation) in.readValue((Fermentation.class.getClassLoader())));
            instance.twist = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public Method[] newArray(int size) {
            return (new Method[size]);
        }

    }
    ;

    public List<MashTemp> getMashTemp() {
        return mashTemp;
    }

    public void setMashTemp(List<MashTemp> mashTemp) {
        this.mashTemp = mashTemp;
    }

    public Fermentation getFermentation() {
        return fermentation;
    }

    public void setFermentation(Fermentation fermentation) {
        this.fermentation = fermentation;
    }

    public Object getTwist() {
        return twist;
    }

    public void setTwist(Object twist) {
        this.twist = twist;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mashTemp);
        dest.writeValue(fermentation);
        dest.writeValue(twist);
    }

    public int describeContents() {
        return  0;
    }

}
