package com.avirpro.aviseekbar.activity;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.avirpro.aviseekbar.helper.WidgetHelper;
import com.avirpro.aviseekbar.view.CustomPriceSeekBar;
import com.avirpro.aviseekbar.R;
import com.avirpro.aviseekbar.adapter.SimplePhasedAdapter;
import com.avirpro.aviseekbar.listener.ApiListener;
import com.avirpro.aviseekbar.listener.PhasedInteractionListener;
import com.avirpro.aviseekbar.model.MainModelArray;
import com.avirpro.aviseekbar.network.FetchData;

public class MainActivity extends AppCompatActivity implements ApiListener {


    private ServerCall task = new ServerCall();
    MainModelArray mObject;
    CustomPriceSeekBar psbHorizontal;
    private ImageView ivCarPic;
    private String mMetaTitle, mSwagTitle, mCarSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //made the call to get the information
        task.execute("");
    }

    //set up the widgets of the activity
    private void setupWidget(){
        ivCarPic = (ImageView) findViewById(R.id.ivCarPic);
    }

    //set up the UI of the activity
    private void setUpUi(final MainModelArray object){

        if(object != null){

                //String holder for car price will be show on the seekBar
                String mSolidPrice = null;
                String mLuxePrice = null;
                String mSuvPrice = null;
                String mConvPrice = null;

                //string holder for car type come from the server
                String mCarType1 = null;
                String mCarType2 = null;
                String mCarType3 = null;
                String mCarType4 = null;

            try{
                //fetch car price
                mSolidPrice = object.getSolidCars().getStandardData().getFinalPrice();
                mLuxePrice = object.getLuxeCars().getStandardData().getFinalPrice();
                mSuvPrice = object.getSuvCars().getStandardData().getFinalPrice();
                mConvPrice = object.getConvCars().getStandardData().getFinalPrice();

                //fetch car type
                mCarType1 = object.getSolidCars().getStandardData().getCarType();
                mCarType2 = object.getLuxeCars().getStandardData().getCarType();
                mCarType3 = object.getSuvCars().getStandardData().getCarType();
                mCarType4 = object.getConvCars().getStandardData().getCarType();

            }catch (Exception e) {
                e.printStackTrace();
            }

                psbHorizontal = (CustomPriceSeekBar) findViewById(R.id.cSeekbar);

                //car type list should be equal to car price list
                String[] carType = {mCarType1,mCarType2,mCarType3,mCarType4};

                String[] carPrices = {mSolidPrice,mLuxePrice,mSuvPrice,mConvPrice};

                final Resources resources = getResources();
                psbHorizontal.setAdapter(new SimplePhasedAdapter(resources, new int[]{
                        R.drawable.btn_circle_selector,
                        R.drawable.btn_circle_selector,
                        R.drawable.btn_circle_selector, R.drawable.btn_circle_selector}, carPrices, carType));

                //first time the user open the screen, put the seek bar on the first elemnet
                firstTimeSetUp(object);
                psbHorizontal.setPosition(0);
        }
        //set up the logic for the seek bar when its moving
        psbHorizontal.setInteractionListener(new PhasedInteractionListener() {
            @Override
            public void onInteracted(int x, int y, int position, MotionEvent motionEvent) {
                Log.d("PSB", String.format("onInteracted %d %d %d %d", x, y, position, motionEvent.getAction()));

                if(object != null){
                    if(position == 0){
                        firstTimeSetUp(object);

                    }else if(position == 1){
                        mCarSeat = object.getLuxeCars().getStandardData().getNumberSeats();
                        mSwagTitle = object.getLuxeCars().getStandardData().getSwagTitle();
                        mMetaTitle = object.getLuxeCars().getStandardData().getMetaTitle();

                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvNmrSeats), mCarSeat);
                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvSwagTitle), mSwagTitle);
                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvMetaTitle), mMetaTitle);
                        ivCarPic.setBackgroundResource(R.mipmap.luxe_standard);

                    }else if(position == 2){
                        mCarSeat = object.getSuvCars().getStandardData().getNumberSeats();
                        mSwagTitle = object.getSuvCars().getStandardData().getSwagTitle();
                        mMetaTitle = object.getSuvCars().getStandardData().getMetaTitle();

                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvNmrSeats), mCarSeat);
                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvSwagTitle), mSwagTitle);
                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvMetaTitle), mMetaTitle);
                        ivCarPic.setBackgroundResource(R.mipmap.suv_standard);

                    }else if(position == 3){
                        mCarSeat = object.getConvCars().getStandardData().getNumberSeats();
                        mSwagTitle = object.getConvCars().getStandardData().getSwagTitle();
                        mMetaTitle = object.getConvCars().getStandardData().getMetaTitle();

                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvNmrSeats), mCarSeat);
                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvSwagTitle), mSwagTitle);
                        WidgetHelper.setSafeText((TextView) findViewById(R.id.tvMetaTitle), mMetaTitle);
                        ivCarPic.setBackgroundResource(R.mipmap.conv_standard);
                    }
                }
            }
        });
    }

    public void firstTimeSetUp(MainModelArray object){
        if(object != null){
            mCarSeat = object.getSolidCars().getStandardData().getNumberSeats();
            mSwagTitle = object.getSolidCars().getStandardData().getSwagTitle();
            mMetaTitle = object.getSolidCars().getStandardData().getMetaTitle();

            WidgetHelper.setSafeText((TextView) findViewById(R.id.tvNmrSeats), mCarSeat);
            WidgetHelper.setSafeText((TextView) findViewById(R.id.tvSwagTitle), mSwagTitle);
            WidgetHelper.setSafeText((TextView) findViewById(R.id.tvMetaTitle), mMetaTitle);
        }
        ivCarPic.setBackgroundResource(R.mipmap.solid_standard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
        }

    @Override
    public void onSuccess(MainModelArray feed) {
        mObject = feed;
    }

    @Override
    public void onFailure(String message) {

    }

    /*
   * Fetches data from the URL
   * */
    private void fetchData(boolean forceFetch){
        FetchData fetch = new FetchData();
        fetch.setApiListener(this);
        fetch.fetchRentCarData(MainActivity.this);

    }

        /*
    * AsyncTask class to retrieve CMS data.
    * */
    private class ServerCall extends AsyncTask<String, Void, String> {
        private boolean forceFetch = false;


        @Override
        protected String doInBackground(String... params) {
            fetchData(forceFetch);
            return "Executed";
        }

        /*
        * Set up view widgets when data is accessible
        * */
        @Override
        protected void onPostExecute(String result) {
            setupWidget();
            setUpUi(mObject);
        }
    }
}
