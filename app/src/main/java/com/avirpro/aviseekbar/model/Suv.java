package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Avi Rudoi
 */
public class Suv implements Parcelable{

    private Standard STANDARD;
    private Fullsize FULLSIZE;
    private Luxury LUXURY;



    protected Suv(Parcel in) {
        this.STANDARD = in.readParcelable(Standard.class.getClassLoader());
        this.FULLSIZE = in.readParcelable(Fullsize.class.getClassLoader());
        this.LUXURY = in.readParcelable(Luxury.class.getClassLoader());
    }

    public static final Creator<Suv> CREATOR = new Creator<Suv>() {
        @Override
        public Suv createFromParcel(Parcel in) {
            return new Suv(in);
        }

        @Override
        public Suv[] newArray(int size) {
            return new Suv[size];
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
        dest.writeParcelable(this.LUXURY, 0);
    }

    public Standard getStandardData(){
        return STANDARD;
    }

    public Fullsize getFullSizeData(){
        return FULLSIZE;
    }

    public Luxury getLuxuryData(){
        return LUXURY;
    }
}
