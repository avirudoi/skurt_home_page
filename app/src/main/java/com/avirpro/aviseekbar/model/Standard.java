package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Avi Rudoi
 */
public class Standard extends MainCarModel implements Parcelable{



    public static final Creator<Standard> CREATOR = new Creator<Standard>() {
        @Override
        public Standard createFromParcel(Parcel in) {
            return new Standard(in);
        }

        @Override
        public Standard[] newArray(int size) {
            return new Standard[size];
        }
    };

    protected Standard(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
