package com.dongwon.scanner.sql.ship;

import com.dongwon.scanner.model.ship.WhInput;
import com.dongwon.scanner.model.ship.WhOutputFork;
import com.dongwon.scanner.sql.RetrofitResult;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Ship {

    String BASE_URL = "https://api.dwmic.cz/MES/Dynamic/";
    @POST("CallProcedureSingleResult")
    Call<WhInput> checkMovePalletFromProdToWh(@Query("procedureName") String procedureName, @Query("parameters") String parameters);
    @POST("CallProcedureSingleResult")
    Call<RetrofitResult> movePalletFromProdToWh(@Query("procedureName") String procedureName, @Query("parameters") String parameters);
   /* @POST("CallProcedure")
    Call<WhOutputFork> callProcedure(@Query("procedureName") String procedureName, @Query("parameters") String parameters, @Query("multiTables") String multiTables);*/
    @POST("CallProcedureSingleResult")
    Call<WhOutputFork> shipOrderList(@Query("procedureName") String procedureName, @Query("parameters") String parameters);
}
