package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Avi Rudoi
 */
public class Solid implements Parcelable{

    private Standard STANDARD;
    private Fullsize FULLSIZE;
    private Hybrid HYBRID;



    protected Solid(Parcel in) {
        this.STANDARD = in.readParcelable(Standard.class.getClassLoader());
        this.FULLSIZE = in.readParcelable(Fullsize.class.getClassLoader());
        this.HYBRID = in.readParcelable(Hybrid.class.getClassLoader());
    }

    public static final Creator<Solid> CREATOR = new Creator<Solid>() {
        @Override
        public Solid createFromParcel(Parcel in) {
            return new Solid(in);
        }

        @Override
        public Solid[] newArray(int size) {
            return new Solid[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.STANDARD, 0);
        dest.writeParcelable(this.FULLSIZE, 0);
        dest.writeParcelable(this.HYBRID, 0);
    }

    public Standard getStandardData(){
        return STANDARD;
    }

    public Fullsize getFullSizedData(){
        return FULLSIZE;
    }

    public Hybrid getHybridData(){
        return HYBRID;
    }
}
