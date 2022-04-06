package com.dongwon.scanner.sql;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientLogin {
    private static RetrofitClientLogin instance = null;
    private Login myApi;

    private RetrofitClientLogin() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Login.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Login.class);
    }

    public static synchronized RetrofitClientLogin getInstance() {
        if (instance == null) {
            instance = new RetrofitClientLogin();
        }
        return instance;
    }

    public Login getLoginApi() {
        return myApi;
    }
}
