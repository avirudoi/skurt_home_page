package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Avi Rudoi
 */
public class Luxe implements Parcelable{

    private Standard STANDARD;
    private Plus PLUS;
    private Tesla TESLA;



    protected Luxe(Parcel in) {
        this.STANDARD = in.readParcelable(Standard.class.getClassLoader());
        this.PLUS = in.readParcelable(Plus.class.getClassLoader());
        this.TESLA = in.readParcelable(Tesla.class.getClassLoader());
    }


    public static final Creator<Luxe> CREATOR = new Creator<Luxe>() {
        @Override
        public Luxe createFromParcel(Parcel in) {
            return new Luxe(in);
        }

        @Override
        public Luxe[] newArray(int size) {
            return new Luxe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.STANDARD, 0);
        dest.writeParcelable(this.PLUS, 0);
        dest.writeParcelable(this.TESLA, 0);
    }

    public Standard getStandardData(){
        return STANDARD;
    }

    public Plus getPlusdData(){
        return PLUS;
    }

    public Tesla getTesladData(){
        return TESLA;
    }
}
