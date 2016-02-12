package com.avirpro.aviseekbar.network;

/**
 * Created by Avi Rudoi
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import com.avirpro.aviseekbar.listener.ApiListener;
import com.avirpro.aviseekbar.model.MainModelArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FetchData {

    String charset = "UTF-8";
    HttpURLConnection urlConnection;
    StringBuilder result;
    URL urlObj;

    final static String URL = "http://skurtapp.parseapp.com/catalog.json";


    private WeakReference<ApiListener> mApiListener = new WeakReference<>(null);
    BufferedReader reader = null;

    /**
     * Constructor to initiate the web service call
     * @param context
     */
    public void fetchRentCarData(Context context) {

        try {
            urlObj = new URL(URL);
            urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.connect();
            InputStream stream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                result.append(line);
            }
            processApiResponse(urlConnection.getResponseCode(),result.toString());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            //very important to check for not null here
            if(urlConnection !=null){
                urlConnection.disconnect();
            }
        }
    }

    /**
     * Helper method to trigger the listeners with the appropriate data
     * @param statusCode
     * @param response
     */
    private void processApiResponse (int statusCode, String response) {
        ;
        if (getApiListener() != null) {
            if (statusCode == 200 || statusCode == 201) {
                Log.d("SMARTLIFE_LIB", (response==null) ? "NULL RESPONSE" : response);
                Gson gson = new Gson();
                try {
                    getApiListener().onSuccess(gson.fromJson(response, MainModelArray.class));
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if(statusCode == -1) {
                getApiListener().onFailure("Default failure message");
            } else {
                getApiListener().onFailure("Get Failure message from response");
            }
        }
    }

    public void setApiListener(ApiListener listener) {
        mApiListener = new WeakReference<>(listener);
    }

    public ApiListener getApiListener() {
        return mApiListener.get();
    }

}
