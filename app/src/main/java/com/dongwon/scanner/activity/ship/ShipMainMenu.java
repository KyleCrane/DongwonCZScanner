package com.dongwon.scanner.activity.ship;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dongwon.scanner.R;
import com.dongwon.scanner.activity.LoginActivity;

public class ShipMainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_main_menu);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);startActivity(intent);finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
      /*  Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);*/
        
    }
    public void whInputClick(View view) {
        Intent intent = new Intent(getApplicationContext(), WhInputActivity.class);
        startActivity(intent);
    }
    public void whOutputClick(View view){
        Intent intent = new Intent(getApplicationContext(),WhOutputForkActivity.class);
        startActivity(intent);
    }
}