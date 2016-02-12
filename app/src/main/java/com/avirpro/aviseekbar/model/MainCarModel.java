package com.avirpro.aviseekbar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sandy on 12/11/15.
 */
public class MainCarModel implements Parcelable{

    private String title;
    private int price;
    private int averagePrice;
    private int liabilityInsurancePerDay;
    private int cdwPerDay;
    private int nmrSeats;
    private String swagTitle;
    private String priceCol;
    private String metaTitle;


    protected MainCarModel(Parcel in) {
        this.title = in.readString();
        this.price = in.readInt();
        this.averagePrice = in.readInt();
        this.liabilityInsurancePerDay = in.readInt();
        this.cdwPerDay = in.readInt();
        this.nmrSeats = in.readInt();
        this.swagTitle = in.readString();
        this.priceCol = in.readString();
        this.metaTitle = in.readString();

    }

    public static final Creator<MainCarModel> CREATOR = new Creator<MainCarModel>() {
        @Override
        public MainCarModel createFromParcel(Parcel in) {
            return new MainCarModel(in);
        }

        @Override
        public MainCarModel[] newArray(int size) {
            return new MainCarModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.price);
        dest.writeInt(this.averagePrice);
        dest.writeInt(this.liabilityInsurancePerDay);
        dest.writeInt(this.cdwPerDay);
        dest.writeInt(this.nmrSeats);
        dest.writeString(this.swagTitle);
        dest.writeString(this.priceCol);
        dest.writeString(this.metaTitle);
    }

    public String getCarFullType(){
        return  priceCol;
    }

    public String getSwagTitle(){
        return  swagTitle;
    }

    public String getMetaTitle(){
        return  metaTitle;
    }

    public String getNumberSeats(){
        return  Integer.toString(nmrSeats);
    }

    public String getCarType(){

        return (getCarFullType().split("_"))[0];
    }

    public int getCarPrice(){
        return  averagePrice;
    }

    public String getFinalPrice(){

        return "$" + Integer.toString(averagePrice);
    }
}
