
package com.wingoku.punkBeer.models.serverResponse.punkAPI;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BeerServer implements Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("first_brewed")
    @Expose
    private String firstBrewed;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("abv")
    @Expose
    private double abv;
    @SerializedName("ibu")
    @Expose
    private double ibu;
    @SerializedName("target_fg")
    @Expose
    private double targetFg;
    @SerializedName("target_og")
    @Expose
    private double targetOg;
    @SerializedName("ebc")
    @Expose
    private double ebc;
    @SerializedName("srm")
    @Expose
    private double srm;
    @SerializedName("ph")
    @Expose
    private double ph;
    @SerializedName("attenuation_level")
    @Expose
    private double attenuationLevel;
    @SerializedName("volume")
    @Expose
    private Volume volume;
    @SerializedName("boil_volume")
    @Expose
    private BoilVolume boilVolume;
    @SerializedName("method")
    @Expose
    private Method method;
    @SerializedName("ingredients")
    @Expose
    private Ingredients ingredients;
    @SerializedName("food_pairing")
    @Expose
    private List<String> foodPairing = new ArrayList<String>();
    @SerializedName("brewers_tips")
    @Expose
    private String brewersTips;
    @SerializedName("contributed_by")
    @Expose
    private String contributedBy;
    public final static Creator<BeerServer> CREATOR = new Creator<BeerServer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BeerServer createFromParcel(Parcel in) {
            BeerServer instance = new BeerServer();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.tagline = ((String) in.readValue((String.class.getClassLoader())));
            instance.firstBrewed = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.abv = ((double) in.readValue((double.class.getClassLoader())));
            instance.ibu = ((double) in.readValue((double.class.getClassLoader())));
            instance.targetFg = ((double) in.readValue((double.class.getClassLoader())));
            instance.targetOg = ((double) in.readValue((double.class.getClassLoader())));
            instance.ebc = ((double) in.readValue((double.class.getClassLoader())));
            instance.srm = ((double) in.readValue((double.class.getClassLoader())));
            instance.ph = ((double) in.readValue((double.class.getClassLoader())));
            instance.attenuationLevel = ((double) in.readValue((double.class.getClassLoader())));
            instance.volume = ((Volume) in.readValue((Volume.class.getClassLoader())));
            instance.boilVolume = ((BoilVolume) in.readValue((BoilVolume.class.getClassLoader())));
            instance.method = ((Method) in.readValue((Method.class.getClassLoader())));
            instance.ingredients = ((Ingredients) in.readValue((Ingredients.class.getClassLoader())));
            in.readList(instance.foodPairing, (String.class.getClassLoader()));
            instance.brewersTips = ((String) in.readValue((String.class.getClassLoader())));
            instance.contributedBy = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public BeerServer[] newArray(int size) {
            return (new BeerServer[size]);
        }

    }
    ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getFirstBrewed() {
        return firstBrewed;
    }

    public void setFirstBrewed(String firstBrewed) {
        this.firstBrewed = firstBrewed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public double getIbu() {
        return ibu;
    }

    public void setIbu(double ibu) {
        this.ibu = ibu;
    }

    public double getTargetFg() {
        return targetFg;
    }

    public void setTargetFg(double targetFg) {
        this.targetFg = targetFg;
    }

    public double getTargetOg() {
        return targetOg;
    }

    public void setTargetOg(double targetOg) {
        this.targetOg = targetOg;
    }

    public double getEbc() {
        return ebc;
    }

    public void setEbc(double ebc) {
        this.ebc = ebc;
    }

    public double getSrm() {
        return srm;
    }

    public void setSrm(double srm) {
        this.srm = srm;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getAttenuationLevel() {
        return attenuationLevel;
    }

    public void setAttenuationLevel(double attenuationLevel) {
        this.attenuationLevel = attenuationLevel;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public BoilVolume getBoilVolume() {
        return boilVolume;
    }

    public void setBoilVolume(BoilVolume boilVolume) {
        this.boilVolume = boilVolume;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getFoodPairing() {
        return foodPairing;
    }

    public void setFoodPairing(List<String> foodPairing) {
        this.foodPairing = foodPairing;
    }

    public String getBrewersTips() {
        return brewersTips;
    }

    public void setBrewersTips(String brewersTips) {
        this.brewersTips = brewersTips;
    }

    public String getContributedBy() {
        return contributedBy;
    }

    public void setContributedBy(String contributedBy) {
        this.contributedBy = contributedBy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(tagline);
        dest.writeValue(firstBrewed);
        dest.writeValue(description);
        dest.writeValue(imageUrl);
        dest.writeValue(abv);
        dest.writeValue(ibu);
        dest.writeValue(targetFg);
        dest.writeValue(targetOg);
        dest.writeValue(ebc);
        dest.writeValue(srm);
        dest.writeValue(ph);
        dest.writeValue(attenuationLevel);
        dest.writeValue(volume);
        dest.writeValue(boilVolume);
        dest.writeValue(method);
        dest.writeValue(ingredients);
        dest.writeList(foodPairing);
        dest.writeValue(brewersTips);
        dest.writeValue(contributedBy);
    }

    public int describeContents() {
        return  0;
    }

}
