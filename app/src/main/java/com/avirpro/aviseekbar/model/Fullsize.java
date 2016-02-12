package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Avi Rudoi
 */
public class Fullsize extends MainCarModel implements Parcelable{



    public static final Creator<Fullsize> CREATOR = new Creator<Fullsize>() {
        @Override
        public Fullsize createFromParcel(Parcel in) {
            return new Fullsize(in);
        }

        @Override
        public Fullsize[] newArray(int size) {
            return new Fullsize[size];
        }
    };

    protected Fullsize(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
