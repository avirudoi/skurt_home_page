package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Avi Rudoi
 */
public class Hybrid extends MainCarModel implements Parcelable{



    public static final Creator<Hybrid> CREATOR = new Creator<Hybrid>() {
        @Override
        public Hybrid createFromParcel(Parcel in) {
            return new Hybrid(in);
        }

        @Override
        public Hybrid[] newArray(int size) {
            return new Hybrid[size];
        }
    };

    protected Hybrid(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
