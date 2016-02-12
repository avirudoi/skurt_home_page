package com.avirpro.aviseekbar.adapter;

import android.graphics.drawable.StateListDrawable;

public interface PhasedAdapter {

    int getCount();

    StateListDrawable getItem(int position);

    String getPrice(int position);

    String getCarType(int position);


}
