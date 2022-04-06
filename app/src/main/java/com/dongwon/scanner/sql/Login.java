package com.dongwon.scanner.sql;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Login {

    String BASE_URL = "https://api.dwmic.cz/MES/";
    @POST("Login")
    Call<Boolean> getLoginResult(@Query("id") String id, @Query("password") String password, @Query("group") String group);
}
