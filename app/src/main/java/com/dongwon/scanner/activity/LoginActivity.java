package com.dongwon.scanner.activity;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwon.scanner.BuildConfig;
import com.dongwon.scanner.R;
import com.dongwon.scanner.activity.ship.ShipMainMenu;
import com.dongwon.scanner.repository.common.Utils;
import com.dongwon.scanner.sql.RetrofitClientLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Utils utils;
    Boolean loginSuccess=false;
    String id,password,group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils = new Utils(this);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(false);

        TextView version = findViewById(R.id.buildVersion);
        version.setText("version: " +BuildConfig.VERSION_NAME);
    }
    public void loginOnClick(View v) {
        RadioGroup rgProgramType = this.findViewById(R.id.program_select);
        RadioButton rbProgramType;
        if(rgProgramType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select program !!!\n Vyber program !!!",
                    Toast.LENGTH_LONG).show();
        }
        else {
            id = ((EditText)findViewById(R.id.et_username)).getText().toString();
            password = ((EditText)findViewById(R.id.pw_password)).getText().toString();
            int selectedProgramTypeId = rgProgramType.getCheckedRadioButtonId();
            group = ((RadioButton)findViewById(selectedProgramTypeId)).getText().toString();
            login(id,password,group);
        }
    }
    private void login(String id,String password,String group) {
        Call<Boolean> call = RetrofitClientLogin.getInstance().getLoginApi().getLoginResult(id,password,group);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean result = response.body();
                if(!result) {
                    Toast.makeText(getApplicationContext(), "Login error!", Toast.LENGTH_LONG).show();
                    utils.showDialogue("Login error!","Wrong username/password or you do not have permissions.\nZadali jste špatné uživatelské jméno/heslo nebo nemáte přístup do systému.");
                }
                else {
                    SharedPreferences prefs = getSharedPreferences("com.dongwon.scanner.emp", Context.MODE_PRIVATE);
                    prefs.edit().putString("com.dongwon.scanner.emp.id",id).commit();
                    switch(group) {
                        case "MAT": Toast.makeText(getApplicationContext(), "Prihlaseni uspesne...Program MAT se pripravuje ...", Toast.LENGTH_LONG).show();
                            break;
                        case "QC": Toast.makeText(getApplicationContext(), "Prihlaseni uspesne...Program QC se pripravuje ...", Toast.LENGTH_LONG).show();
                            break;
                        case "PROD": Toast.makeText(getApplicationContext(), "Prihlaseni uspesne...Program PROD se pripravuje ...", Toast.LENGTH_LONG).show();
                            break;
                        case "SHIP":
                            Intent intent = new Intent(getApplicationContext(), ShipMainMenu.class);startActivity(intent);finish();
                           // Intent intent = new Intent(getApplicationContext(), ShipMenuActivity.class);startActivity(intent);finish();
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<Boolean> call,Throwable t) {
                utils.showDialogue("Communication error!",t.getMessage());
                Toast.makeText(getApplicationContext(), "An error has occured\n"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}