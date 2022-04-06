package com.dongwon.scanner.sql.ship;

import com.dongwon.scanner.sql.Login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientShip {
    private static RetrofitClientShip instance = null;
    private Ship myApi;

    private RetrofitClientShip() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Ship.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Ship.class);
    }

    public static synchronized RetrofitClientShip getInstance() {
        if (instance == null) {
            instance = new RetrofitClientShip();
        }
        return instance;
    }

    public Ship getShipApi() {
        return myApi;
    }
}
