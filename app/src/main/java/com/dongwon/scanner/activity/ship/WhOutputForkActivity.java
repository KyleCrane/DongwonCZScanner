package com.dongwon.scanner.activity.ship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.dongwon.scanner.R;
import com.dongwon.scanner.model.ship.WhOutputFork;
import com.dongwon.scanner.repository.common.Utils;
import com.dongwon.scanner.sql.ship.RetrofitClientShip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WhOutputForkActivity extends AppCompatActivity {
    public Utils utils;
    Button btnDate;
    private ArrayList<WhOutputFork> shipProperties;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wh_output_fork);
        utils = new Utils(this);
        shipProperties = new ArrayList<WhOutputFork>();
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnDate = findViewById(R.id.btnSelect);
        listview = findViewById(R.id.forkList);
        btnDateInit();
        loadDataAndSetupUI();
    }
    //Back button
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
    //Datepicker on click
    public void btnDateOnClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(WhOutputForkActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        btnDate.setText(year + "/" + (month+1) + "/" + day);
                    }
                },year,month,dayOfMonth);
        datePickerDialog.show();
    }
    //Date init
    public void btnDateInit() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        btnDate.setText(year + "/" + (month+1) + "/" + dayOfMonth);
    }
    private void loadDataAndSetupUI() {
        Call<WhOutputFork> call = RetrofitClientShip.getInstance()
                .getShipApi()
                .shipOrderList("TPDAM_SHIP_ORDER_LIST_STD_L","20220404");
        //.movePalletFromProdToWh("_TEST_PROCEDURE","");
        call.enqueue(new Callback<WhOutputFork>() {
            @Override
            public void onResponse(Call<WhOutputFork> call, Response<WhOutputFork> response) {
                Log.i("MES",Integer.toString(response.code()));
                //     Boolean result = response.body();
                WhOutputFork result = response.body();
                shipProperties.add(new WhOutputFork("123456","HMMC","C"));
                shipProperties.add(new WhOutputFork("99999","KaSK","I"));
                shipProperties.add(new WhOutputFork("88888","KaSK","I"));
                shipProperties.add(new WhOutputFork("5555","MCZ","R"));
                shipProperties.add(new WhOutputFork("4778","KaSK","C"));
                shipProperties.add(new WhOutputFork("879789","KaSK","C"));
                propertyArrayAdapter adapter = new propertyArrayAdapter(getBaseContext(),0,shipProperties);
                listview.setAdapter(adapter);
               /* if(result.getHasError() == true) {
                    ((WhInputActivity)getActivity()).utils.showDialogue("Error!",result.getErrorMessage());
                    HideLoadingSpinner();
                    clear();
                    return;
                }*/
                //   Log.i("MES",response.body().toString());
             /*   WhInputActivity main =  ((WhInputActivity)getActivity());

                TextView partNumber = getView().findViewById(R.id.partNumberVal);
                partNumber.setText(result.getPartId());
                TextView partName = getView().findViewById(R.id.partNameVal);
                partName.setText(result.getPartName());
                TextView qty = getView().findViewById(R.id.qtyVal);
                qty.setText(String.valueOf(result.getQty()));

                HideLoadingSpinner();*/
                //    main.utils.showDialogue(response.body().getName());
                //   JSONObject myObject = response.body();
            }
            @Override
            public void onFailure(Call<WhOutputFork> call, Throwable t) {
             /*   ((WhInputActivity)getActivity()).utils.showDialogue("Error!",t.getMessage());

                HideLoadingSpinner();
                clear();*/
                //      Toast.makeText(getApplicationContext(), "An error has occured\n"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    class propertyArrayAdapter extends ArrayAdapter<WhOutputFork> {
        private Context context;
        private List<WhOutputFork> shipProperties;

        public propertyArrayAdapter(Context context, int resource, ArrayList<WhOutputFork> objects) {
            super(context,resource,objects);
            this.context = context;
            this.shipProperties = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            WhOutputFork property = shipProperties.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.wh_output_fork_list,null);

            TextView shipNo = (TextView) view.findViewById(R.id.shipNo);
            shipNo.setText(String.valueOf(property.getShipNo()));

            TextView loctName = (TextView) view.findViewById(R.id.loctName);
            loctName.setText(String.valueOf(property.getLoctName()));

            TextView ordrStatus = (TextView) view.findViewById(R.id.ordrStatus);
            ordrStatus.setText(String.valueOf(property.getOrdrStatus()));

            return view;
        }
    }
}