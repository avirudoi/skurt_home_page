package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Avi Rudoi
 */
public class Tesla extends MainCarModel implements Parcelable{



    public static final Creator<Tesla> CREATOR = new Creator<Tesla>() {
        @Override
        public Tesla createFromParcel(Parcel in) {
            return new Tesla(in);
        }

        @Override
        public Tesla[] newArray(int size) {
            return new Tesla[size];
        }
    };

    protected Tesla(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
