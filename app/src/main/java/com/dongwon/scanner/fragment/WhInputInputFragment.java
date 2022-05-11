package com.dongwon.scanner.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dongwon.scanner.R;
import com.dongwon.scanner.activity.ship.WhInputActivity;
import com.dongwon.scanner.model.ship.WhInput;
import com.dongwon.scanner.sql.RetrofitResult;
import com.dongwon.scanner.sql.ship.RetrofitClientShip;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhInputInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhInputInputFragment extends Fragment  {
    TextView scanData;
    IntentFilter filter;
    Boolean ableToSend = false;
    public WhInputInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WhInputInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WhInputInputFragment newInstance(String param1, String param2) {
        WhInputInputFragment fragment = new WhInputInputFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(myBroadcastReceiver);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.dw_action));
        requireActivity().registerReceiver(myBroadcastReceiver,filter);

        View view = inflater.inflate(R.layout.fragment_wh_input_input,container,false);
       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MES","test");
            }
        });*/
       Button confirmButton = (Button) view.findViewById(R.id.btnInput);
       confirmButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.i("MES","Fragment button clicked ...");
               TextView qtyVal = getView().findViewById(R.id.qtyVal);
               TextView partIdVal = getView().findViewById(R.id.partNumberVal);
               if(qtyVal.getText() == "" || partIdVal.getText() == "") {
                   ((WhInputActivity)getActivity()).utils.showDialogue("Error! / Chyba !","Nebyl načten part-number nebo množství kusů v paletě ! ***\n Toto je chyba, která zapříčiňuje hlášku Quantity Errors v SAPu... Nahlašte tento QR kód !!!");
                   HideLoadingSpinner();
                   clear();
                   return;
               }
               else {
                   onClickConfirm();
               }
           }
       });
      //  getActivity().getActionBar().setIcon(R.drawable.ic_input_foreground);
        getActivity().setTitle("WH Input - Vstup");
        return view;
    }
    public void ShowLoadingSpinner() {
        ((WhInputActivity)getActivity()).ProgressSpinnerShow();
    }
    public void HideLoadingSpinner() {
        ((WhInputActivity)getActivity()).ProgressSpinnerHide();
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
                    Log.i("MES",e.getMessage());
                    //
                    // Catch if the UI does not exist when broadcast is received
                    //
                    HideLoadingSpinner();
                }
            }
            scanData = getView().findViewById(R.id.qrCodeVal);
        }
    };
    private void scanProcess(Intent initiatingIntent)
    {
        ShowLoadingSpinner();
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        if(!decodedData.startsWith("DWFP")) {
            ((WhInputActivity)getActivity()).utils.showDialogue("Error! / Chyba !","This is not palette QR code !\nToto není QR kód palety!");
            HideLoadingSpinner();
            clear();
            return;
        }
        // final TextView lblScanSource = (TextView) findViewById(R.id.lblScanSource);
        final TextView qrCodeVal = (TextView) getView().findViewById(R.id.qrCodeVal);
        qrCodeVal.setText(decodedData);
        // final TextView lblScanLabelType = (TextView) findViewById(R.id.lblScanDecoder);
        // lblScanSource.setText(decodedSource + " " + howDataReceived);
     //   lblScanData.setText(decodedData);
        //  lblScanLabelType.setText(decodedLabelType);
        loadDataAndSetupUI();
    }
    private void loadDataAndSetupUI() {
        Call<WhInput> call = RetrofitClientShip.getInstance()
                .getShipApi()
                .checkMovePalletFromProdToWh("TPDAM_PRODUCT_INOUT_STD_L",((TextView) getView().findViewById(R.id.qrCodeVal)).getText() + "&delimiter&FP_INPUT_L");
                //.movePalletFromProdToWh("_TEST_PROCEDURE","");
        call.enqueue(new Callback<WhInput>() {
            @Override
            public void onResponse(Call<WhInput> call, Response<WhInput> response) {
                Log.i("MES",Integer.toString(response.code()));
           //     Boolean result = response.body();
                WhInput result = response.body();
                if(result.getHasError() == true) {
                    ((WhInputActivity)getActivity()).utils.showDialogue("Error!",result.getErrorMessage());
                    HideLoadingSpinner();
                    clear();
                    return;
                }
             //   Log.i("MES",response.body().toString());
                WhInputActivity main =  ((WhInputActivity)getActivity());

                TextView partNumber = getView().findViewById(R.id.partNumberVal);
                partNumber.setText(result.getPartId());
                TextView partName = getView().findViewById(R.id.partNameVal);
                partName.setText(result.getPartName());
                TextView qty = getView().findViewById(R.id.qtyVal);
                qty.setText(String.valueOf(result.getQty()));

                HideLoadingSpinner();
             //    main.utils.showDialogue(response.body().getName());
             //   JSONObject myObject = response.body();
            }
            @Override
            public void onFailure(Call<WhInput> call, Throwable t) {
                ((WhInputActivity)getActivity()).utils.showDialogue("Error!",t.getMessage());

                HideLoadingSpinner();
                clear();
          //      Toast.makeText(getApplicationContext(), "An error has occured\n"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    public void onClickConfirm() {
        try {
            TextView qtyVal = getView().findViewById(R.id.qtyVal);
            TextView partIdVal = getView().findViewById(R.id.partNumberVal);
            if (TextUtils.isEmpty(qtyVal.getText()) || TextUtils.isEmpty(partIdVal.getText())) {
                throw new Exception("Nebyly správně načteny všechny informace QR kódu. Prosím nascanujte jej znovu.");
            }

            SharedPreferences prefs = getActivity().getSharedPreferences("com.dongwon.scanner.emp", Context.MODE_PRIVATE);
            String empId = prefs.getString("com.dongwon.scanner.emp.id", "");
            Call<RetrofitResult> call = RetrofitClientShip.getInstance()
                    .getShipApi()
                    .movePalletFromProdToWh("TPDAM_PRODUCT_INOUT_IN_STD_S",
                            ((TextView) getView().findViewById(R.id.qrCodeVal)).getText()
                                    + "&delimiter&"
                                    + ((TextView) getView().findViewById(R.id.partNumberVal)).getText()
                                    + "&delimiter&"
                                    + empId
                                    + "&delimiter&"
                                    + "P01-01-01"
                                    + "&delimiter&"
                                    + ((TextView) getView().findViewById(R.id.qtyVal)).getText()
                    );
            //.movePalletFromProdToWh("_TEST_PROCEDURE","");
            call.enqueue(new Callback<RetrofitResult>() {
                @Override
                public void onResponse(Call<RetrofitResult> call, Response<RetrofitResult> response) {
                    Log.i("MES", Integer.toString(response.code()));
                    //     Boolean result = response.body();
                    RetrofitResult result = response.body();
                    if (result.getHasError() == true) {
                        ((WhInputActivity) getActivity()).utils.showDialogue("Error!", result.getErrorMessage());
                        HideLoadingSpinner();
                        return;
                    }
                    //   Log.i("MES",response.body().toString());
                    WhInputActivity main = ((WhInputActivity) getActivity());

               /* TextView partNumber = getView().findViewById(R.id.partNumberVal);
                partNumber.setText(result.getPartId());
                TextView partName = getView().findViewById(R.id.partNameVal);
                partName.setText(result.getPartName());
                TextView qty = getView().findViewById(R.id.qtyVal);
                qty.setText(String.valueOf(result.getQty()));*/

                    HideLoadingSpinner();
                    //    main.utils.showDialogue(response.body().getName());
                    //   JSONObject myObject = response.body();
                    Snackbar snackbar = Snackbar.make(getView(), "Paleta přesunuta do skladu.\nPalette moved to warehouse.", Snackbar.LENGTH_LONG);
                    View snackView = snackbar.getView();
                    snackView.setBackgroundColor(getResources().getColor(R.color.success));
                    TextView snackText = (TextView) snackView.findViewById(R.id.snackbar_text);
                    //snackText.setTextSize(getResources().getDimension(R.dimen.font_size_large));
                    snackbar.show();
                }

                @Override
                public void onFailure(Call<RetrofitResult> call, Throwable t) {
                    ((WhInputActivity) getActivity()).utils.showDialogue("Error!", t.getMessage());

                    HideLoadingSpinner();
                    //      Toast.makeText(getApplicationContext(), "An error has occured\n"+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            clear();
        }catch (Exception ex) {
            ((WhInputActivity)getActivity()).utils.showDialogue("Error!",ex.getMessage());
        }
        finally {
            ShowLoadingSpinner();
            clear();
            HideLoadingSpinner();
        }
    }
    private void clear() {
        TextView qrCode = getView().findViewById(R.id.qrCodeVal);
        qrCode.setText("");
        TextView partNumber = getView().findViewById(R.id.partNumberVal);
        partNumber.setText("");
        TextView partName = getView().findViewById(R.id.partNameVal);
        partName.setText("");
        TextView qty = getView().findViewById(R.id.qtyVal);
        qty.setText("");
    }
}