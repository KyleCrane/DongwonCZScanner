package com.dongwon.scanner.activity.ship;

import androidx.annotation.NonNull;

import com.dongwon.scanner.fragment.WhInputToMoveFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.dongwon.scanner.R;
import com.dongwon.scanner.fragment.WhInputCancelFragment;
import com.dongwon.scanner.fragment.WhInputInputFragment;
import com.dongwon.scanner.repository.common.Utils;

public class WhInputActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    public Utils utils;
    private FrameLayout progressSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_input);
        utils = new Utils(this);
        progressSpinner = findViewById(R.id.frameProgressSpinner);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.input);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    WhInputInputFragment whInputInputFragment = new WhInputInputFragment();
    WhInputCancelFragment whInputCancelFragment = new WhInputCancelFragment();
    WhInputToMoveFragment whInputToMoveFragment = new WhInputToMoveFragment();
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
             //   Intent intent = new Intent(getApplicationContext(), LoginActivity.class);startActivity(intent);finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.input:
                getSupportFragmentManager().beginTransaction().replace(R.id.main,whInputInputFragment).commit();ProgressSpinnerHide();
                return true;
            case R.id.cancel:
                getSupportFragmentManager().beginTransaction().replace(R.id.main,whInputCancelFragment).commit();ProgressSpinnerHide();
                return true;
            case R.id.toMove:
                getSupportFragmentManager().beginTransaction().replace(R.id.main,whInputToMoveFragment).commit();ProgressSpinnerHide();
                return true;
        }
        return false;
    }
    public void ProgressSpinnerShow() {
        progressSpinner.setVisibility(View.VISIBLE);
    }
    public void ProgressSpinnerHide() {
        progressSpinner.setVisibility(View.GONE);
    }
}