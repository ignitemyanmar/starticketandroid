package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.agent.util.Constant;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.OperatorSeatsAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.OperatorSeat;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.smk.skconnectiondetector.SKConnectionDetector;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BusOperatorSeatsActivity extends BaseSherlockActivity {
  private String selectedFromCity = "";
  private String selectedToCity = "";
  private String selectedTripDate = "";
  private String selectedTripTime = "";
  private ActionBar actionBar;
  private TextView actionBarTitle;
  private TextView actionBarTitle2;
  private ImageButton actionBarBack;
  private ListView lv_operator_seats;
  private ProgressDialog dialog;
  private List<OperatorSeat> OperatorSeats;
  private boolean notLocal = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_operator_seats);

    Bundle bundle = getIntent().getExtras();

    if (bundle != null) {
      selectedFromCity = bundle.getString("from_city");
      selectedToCity = bundle.getString("to_city");
      selectedTripDate = bundle.getString("trip_date");
      selectedTripTime = bundle.getString("trip_time");
      notLocal = bundle.getBoolean("not_local");
    }
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
      toolbar.setTitleTextAppearance(BusOperatorSeatsActivity.this,
          android.R.attr.textAppearanceSmall);
      toolbar.setTitle(selectedFromCity + " - " + selectedToCity);
      this.setSupportActionBar(toolbar);
    }

    if (selectedTripTime != null) {
      if (!selectedTripDate.equals("") && selectedTripDate != null) {
        if (!selectedTripTime.equals("") && selectedTripTime != null) {
          toolbar.setSubtitle(changeDate(selectedTripDate) + " [" + selectedTripTime + "]");
        } else if (selectedTripTime.equals("") || selectedTripTime == null) {
          toolbar.setSubtitle(changeDate(selectedTripDate) + " [All Time]");
        }
      } else {
        toolbar.setSubtitle("00/00/0000 [ 00:00:00 ]");
      }
    }

    lv_operator_seats = (ListView) findViewById(R.id.lv_operator_seats);
    lv_operator_seats.setDividerHeight(0);
    lv_operator_seats.setOnItemClickListener(clickListener);

    SKConnectionDetector skDetector = SKConnectionDetector.getInstance(this);
    skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
    if (skDetector.isConnectingToInternet()) {
      getOperatorSeats();
    } else {
      skDetector.showErrorMessage();
    }
  }

  private void getOperatorSeats() {
    // TODO Auto-generated method stub
    dialog = ProgressDialog.show(BusOperatorSeatsActivity.this, "", "Please wait ...", true);
    dialog.setCancelable(true);

    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .postSearch(selectedFromCity, selectedToCity, selectedTripDate, selectedTripTime,
            AppLoginUser.getAccessToken(), Constant.OPERATOR_ID, new Callback<Response>() {

              public void success(Response arg0, Response arg1) {
                // TODO Auto-generated method stub
                if (arg0 != null && arg0.getStatus() == 200) {

                  OperatorSeats =
                      DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorSeat>>() {
                      }.getType());

                  if (OperatorSeats != null && OperatorSeats.size() > 0) {

                    Log.i("", "Operation seat: " + OperatorSeats.toString());

                    final List l = new ArrayList();
                    for(OperatorSeat op  : OperatorSeats){
                      if(op.getPermitseatTotal() - op.getPermitseatSoldtotal()>0){
                        l.add(op);
                      }
                    }
                    lv_operator_seats.setAdapter(
                        new OperatorSeatsAdapter(notLocal,BusOperatorSeatsActivity.this, l));
                    //setListViewHeightBasedOnChildren(lv_operator_seats);
                  } else {
                    showAlert("No Trip!");
                  }
                }

                if (dialog != null) {
                  if (arg1.getStatus() == 403) {
                    Toast.makeText(BusOperatorSeatsActivity.this,
                        "Sorry Error Occur,Please Check your balance", Toast.LENGTH_LONG).show();
                  }
                  dialog.dismiss();
                }
              }

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  Log.i("", "Error: " + arg0.getResponse().getStatus());
                }

                if (dialog != null) {
                  dialog.dismiss();
                }
              }
            });
  }

  private OnItemClickListener clickListener = new OnItemClickListener() {

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      // TODO Auto-generated method stub
      Bundle bundle = new Bundle();
      bundle.putString("operator_id", OperatorSeats.get(position).getOperatorId());
      bundle.putString("operator_name", OperatorSeats.get(position).getOperator());
      bundle.putString("tripId", OperatorSeats.get(position).getId());
      bundle.putString("from_city", OperatorSeats.get(position).getFromName());
      bundle.putString("to_city", OperatorSeats.get(position).getToName());
      bundle.putString("trip_date", selectedTripDate);
      bundle.putString("trip_time", OperatorSeats.get(position).getTime());
      bundle.putString("price", OperatorSeats.get(position).getPrice());
      bundle.putString("extra_city", OperatorSeats.get(position).getExtraCity());
      bundle.putBoolean("not_local", notLocal);
      //notLocal = bundle.getBoolean("not_local");
      startActivity(
          new Intent(BusOperatorSeatsActivity.this, BusSelectSeatActivity.class).putExtras(bundle));
    }
  };

  public void setListViewHeightBasedOnChildren(ListView listView) {
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) {
      // pre-condition
      return;
    }

    int totalHeight = 0;
    for (int i = 0; i < listAdapter.getCount(); i++) {
      View listItem = listAdapter.getView(i, null, listView);
      listItem.measure(0, 0);
      totalHeight += listItem.getMeasuredHeight();
    }

    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    listView.setLayoutParams(params);
    listView.requestLayout();
  }

  /**
   * If back arrow button clicked, close this activity.
   */
  @Override public Intent getSupportParentActivityIntent() {
    // TODO Auto-generated method stub
    finish();
    return super.getSupportParentActivityIntent();
  }
}
