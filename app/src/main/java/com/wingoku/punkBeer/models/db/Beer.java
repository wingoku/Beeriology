package com.wingoku.punkBeer.models.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.wingoku.punkBeer.utils.Utils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by Umer on 6/14/2017.
 */

public class Beer extends RealmObject implements Parcelable {
    
    @PrimaryKey
    private int mId; // id from beerServer response
    private String mName;
    private String mDescription;
    private String mImageUrl;
    private Double mPH;
    private String mVolume;
    private String mContributedBy;
    private String mDBEntryDate;

    public Beer() {
        mDBEntryDate = Utils.getDate(0);
        Timber.d("date is: %s", mDBEntryDate);
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public Double getPH() {
        return mPH;
    }

    public void setPH(Double mPH) {
        this.mPH = mPH;
    }

    public String getVolume() {
        return mVolume;
    }

    public void setVolume(String mVolume) {
        this.mVolume = mVolume;
    }

    public String getContributedBy() {
        return mContributedBy;
    }

    public void setContributedBy(String mContributedBy) {
        this.mContributedBy = mContributedBy;
    }

    public void setDBEntryDate(String date) {
        mDBEntryDate = date;
    }

    public String getDBEntryDate() {
        return mDBEntryDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
        dest.writeString(this.mImageUrl);
        dest.writeDouble(this.mPH);
        dest.writeString(this.mVolume);
        dest.writeString(this.mContributedBy);
        dest.writeString(this.mDBEntryDate);
    }

    protected Beer(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mDescription = in.readString();
        this.mImageUrl = in.readString();
        this.mPH = in.readDouble();
        this.mVolume = in.readString();
        this.mContributedBy = in.readString();
        this.mDBEntryDate = in.readString();
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel source) {
            return new Beer(source);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };
}

/**
 * id:""
 name:""
 description:""
 image_url:""
 ph:""
 volumne String value, unit
 contributed_by:""
 */