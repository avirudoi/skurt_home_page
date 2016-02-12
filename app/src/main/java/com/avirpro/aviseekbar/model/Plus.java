package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Avi Rudoi
 */
public class Plus extends MainCarModel implements Parcelable{



    public static final Creator<Plus> CREATOR = new Creator<Plus>() {
        @Override
        public Plus createFromParcel(Parcel in) {
            return new Plus(in);
        }

        @Override
        public Plus[] newArray(int size) {
            return new Plus[size];
        }
    };

    protected Plus(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
