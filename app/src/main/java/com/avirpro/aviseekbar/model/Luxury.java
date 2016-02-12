package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Avi Rudoi
 */
public class Luxury extends MainCarModel implements Parcelable{



    public static final Creator<Luxury> CREATOR = new Creator<Luxury>() {
        @Override
        public Luxury createFromParcel(Parcel in) {
            return new Luxury(in);
        }

        @Override
        public Luxury[] newArray(int size) {
            return new Luxury[size];
        }
    };

    protected Luxury(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
