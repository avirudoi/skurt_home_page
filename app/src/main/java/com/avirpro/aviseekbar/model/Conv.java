package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Avi Rudoi
 */
public class Conv implements Parcelable{

    private Standard STANDARD;

    protected Conv(Parcel in) {
        this.STANDARD = in.readParcelable(Standard.class.getClassLoader());
    }

    public static final Creator<Conv> CREATOR = new Creator<Conv>() {
        @Override
        public Conv createFromParcel(Parcel in) {
            return new Conv(in);
        }

        @Override
        public Conv[] newArray(int size) {
            return new Conv[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.STANDARD, 0);
    }

    public Standard getStandardData(){
        return STANDARD;
    }

}
