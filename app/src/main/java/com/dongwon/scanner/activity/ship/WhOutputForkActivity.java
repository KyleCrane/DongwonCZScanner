package com.dongwon.scanner.activity.ship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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
        btnDate = findViewById(R.id.btnDate);
        listview = findViewById(R.id.forkList);
        btnDateInit();
        loadDataAndSetupUI();
        ListView forkList = (ListView)findViewById(R.id.forkList);
        forkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               WhOutputFork tmp =  (WhOutputFork) forkList.getItemAtPosition(i);
                utils.showDialogue(tmp.getLoctName());
            }
        });
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
                        String monthWithZero;
                        String dayWithZero;
                        if((month+1)<10)
                            monthWithZero = "0" + String.valueOf(month+1);
                        else monthWithZero = String.valueOf(month+1);
                        if(day<10)
                            dayWithZero = "0"+String.valueOf(day);
                        else dayWithZero = String.valueOf(day);
                        btnDate.setText(year + "/" + monthWithZero + "/" + dayWithZero);
                    }
                },year,month,dayOfMonth);
        datePickerDialog.show();
        loadDataAndSetupUI();
    }
    //Date init
    public void btnDateInit() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String monthWithZero;
        String dayWithZero;
        if((month+1)<10)
            monthWithZero = "0" + String.valueOf(month+1);
        else monthWithZero = String.valueOf(month+1);
        if(dayOfMonth<10)
            dayWithZero = "0"+String.valueOf(dayOfMonth);
        else dayWithZero = String.valueOf(dayOfMonth);
        btnDate.setText(year + "/" + monthWithZero + "/" + dayWithZero);
    }
    private void loadDataAndSetupUI() {
        String dtmParam = btnDate.getText().toString().replace("/","");
        Call<List<WhOutputFork>> call = RetrofitClientShip.getInstance()
                .getShipApi()
                .shipOrderList("TPDAM_SHIP_ORDER_LIST_STD_L",dtmParam,"false");
        //.movePalletFromProdToWh("_TEST_PROCEDURE","");
        call.enqueue(new Callback<List<WhOutputFork>>() {
            @Override
            public void onResponse(Call<List<WhOutputFork>> call, Response<List<WhOutputFork>> response) {
                Log.i("MES",Integer.toString(response.code()));
                //     Boolean result = response.body();
                List<WhOutputFork> result = response.body();
             /*   shipProperties.add(new WhOutputFork("123456","HMMC","C"));
                shipProperties.add(new WhOutputFork("99999","KaSK","I"));
                shipProperties.add(new WhOutputFork("88888","KaSK","I"));
                shipProperties.add(new WhOutputFork("5555","MCZ","R"));
                shipProperties.add(new WhOutputFork("4778","KaSK","C"));
                shipProperties.add(new WhOutputFork("879789","KaSK","C"));*/
               /* for(WhOutputFork fork : result){
                    shipProperties.add()
                }*/
                propertyArrayAdapter adapter = new propertyArrayAdapter(getBaseContext(),0,new ArrayList<WhOutputFork>(result));
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
            public void onFailure(Call<List<WhOutputFork>> call, Throwable t) {
             /*   ((WhInputActivity)getActivity()).utils.showDialogue("Error!",t.getMessage());

                HideLoadingSpinner();
                clear();*/
                //      Toast.makeText(getApplicationContext(), "An error has occured\n"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    public void btnSelectOnClick(View view){
        int pos;
        ListView lv =  findViewById(R.id.forkList);
        pos = lv.getCheckedItemPosition();
        WhOutputFork fork = (WhOutputFork) lv.getItemAtPosition(pos);
        if(fork == null)
            utils.showDialogue("Nebyla vybrána žádná položka!");
        else utils.showDialogue(fork.getShipNo());
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

            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
      //      LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.linearLayout2);

            TextView shipNo = (TextView) view.findViewById(R.id.shipNo);
            shipNo.setText(String.valueOf(property.getShipNo()));

            TextView loctName = (TextView) view.findViewById(R.id.loctName);
            loctName.setText(String.valueOf(property.getLoctName()));

            TextView ordrStatus = (TextView) view.findViewById(R.id.ordrStatus);
            ordrStatus.setText(String.valueOf(property.getOrdrStatus()));

            if(property.getOrdrStatus().trim().equals("C"))
                ordrStatus.setBackgroundResource(R.drawable.gradient_success);
//            linearLayout2.setBackgroundResource(R.drawable.gradient_success);

            return view;
        }
    }
}