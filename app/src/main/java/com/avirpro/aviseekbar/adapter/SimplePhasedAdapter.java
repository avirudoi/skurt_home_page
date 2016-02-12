package com.avirpro.aviseekbar.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class SimplePhasedAdapter implements PhasedAdapter {

    protected StateListDrawable[] mItems;

    protected String[] mCarPrice;

    protected String[] mCarType;

    public SimplePhasedAdapter(Resources resources, int[] items, String[] str, String[] carType) {
        int size = items.length;
        mCarPrice = str;
        mCarType = carType;
        mItems = new StateListDrawable[size];
        Drawable drawable;
        for (int i = 0; i < size; i++) {
            drawable = resources.getDrawable(items[i]);
            if (drawable instanceof StateListDrawable) {
                mItems[i] = (StateListDrawable) drawable;
            } else {
                mItems[i] = new StateListDrawable();
                mItems[i].addState(new int[] {}, drawable);
            }
        }
    }



    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public StateListDrawable getItem(int position) {
        return mItems[position];
    }

    @Override
    public String getPrice(int position) {
        return mCarPrice[position];
    }

    @Override
    public String getCarType(int position) {
        return mCarType[position];
    }



}
