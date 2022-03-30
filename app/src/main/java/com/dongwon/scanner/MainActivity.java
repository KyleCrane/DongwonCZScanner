package com.dongwon.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dongwon.scanner.repository.common.Utils;

public class MainActivity extends AppCompatActivity {

    Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utils = new Utils();
       // utils.hideUI(this);

        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.dw_action));
        registerReceiver(myBroadcastReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    public void tmpClick(View view) {

    }
    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();

            //
            // The following is useful for debugging to verify
            // the format of received intents from DataWedge:
            //
            // for (String key : b.keySet())
            // {
            //   Log.v(LOG_TAG, key);
            // }
            //

            if (action.equals(getResources().getString(R.string.dw_action))) {
                //
                //  Received a barcode scan
                //

                try {
                    scanProcess(intent);
                } catch (Exception e) {

                    //
                    // Catch if the UI does not exist when broadcast is received
                    //
                }
            }
        }
        };
    //
    // The section below assumes that a UI exists in which to place the data. A production
    // application would be driving much of the behavior following a scan.
    //
    private void scanProcess(Intent initiatingIntent)
    {
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));

       // final TextView lblScanSource = (TextView) findViewById(R.id.lblScanSource);
        final TextView lblScanData = (TextView) findViewById(R.id.lblScanData);
       // final TextView lblScanLabelType = (TextView) findViewById(R.id.lblScanDecoder);
       // lblScanSource.setText(decodedSource + " " + howDataReceived);
        lblScanData.setText(decodedData);
      //  lblScanLabelType.setText(decodedLabelType);
    }
}