package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avi Rudoi
 */
public class MainModelArray implements Parcelable{


    private Solid SOLID;
    private Luxe LUXE;
    private Suv  SUV;
    private Conv CONV;




    protected MainModelArray(Parcel in) {
        this.SOLID = in.readParcelable(Solid.class.getClassLoader());
        this.LUXE = in.readParcelable(Luxe.class.getClassLoader());
        this.SUV = in.readParcelable(Suv.class.getClassLoader());
        this.CONV = in.readParcelable(Conv.class.getClassLoader());

    }

    public static final Creator<MainModelArray> CREATOR = new Creator<MainModelArray>() {
        @Override
        public MainModelArray createFromParcel(Parcel in) {
            return new MainModelArray(in);
        }

        @Override
        public MainModelArray[] newArray(int size) {
            return new MainModelArray[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.SOLID, 0);
        dest.writeParcelable(this.LUXE, 0);
        dest.writeParcelable(this.SUV, 0);
        dest.writeParcelable(this.CONV, 0);
    }

    public Solid getSolidCars(){
        return SOLID;
    }


    public Luxe getLuxeCars(){
        return LUXE;
    }

    public Suv getSuvCars(){
        return SUV;
    }

    public Conv getConvCars(){
        return CONV;
    }
}
