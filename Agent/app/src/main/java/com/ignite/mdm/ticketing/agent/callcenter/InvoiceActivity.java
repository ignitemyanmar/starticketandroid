package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityInvoiceBinding;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.InvoiceAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.Invoices;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.NewCalendarDialog;
import com.ignite.mm.ticketing.application.SecureParam;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;
import com.smk.skconnectiondetector.SKConnectionDetector;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InvoiceActivity extends BaseSherlockActivity {
  ActivityInvoiceBinding binding;
  SKConnectionDetector skDetector;

  List<Invoices> list = new ArrayList<Invoices>();
  String token;
  private Configuration config;
  InvoiceAdapter invoiceAdapter = new InvoiceAdapter();
  EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_invoice);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setTitle("Invoices");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    skDetector = SKConnectionDetector.getInstance(this);
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerView.setHasFixedSize(true);
    binding.recyclerView.setAdapter(invoiceAdapter);
    String currentDate = sdf.format(cal.getTime());
    binding.txtTodate.setText(currentDate);
    SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
    binding.txtTodate.setOnClickListener(clickListener);
    token = sharedPreferences.getString("operator_token", null);


    binding.btnSearch.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        list.clear();
        invoiceAdapter.clear();
        getData();
        endlessRecyclerViewAdapter.onDataReady(true);
      }
    });
    config = getResources().getConfiguration();
    if (skDetector.isConnectingToInternet()) {
    } else {
      Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
    endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, invoiceAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          public void onLoadMoreRequested() {
            getData();
            Log.d("DSADS", "DAS");
          }
        });
    binding.recyclerView.setAdapter(endlessRecyclerViewAdapter);
  }

  private View.OnClickListener clickListener = new View.OnClickListener() {

    public void onClick(View v) {
      if (v == binding.txtTodate) {
        final NewCalendarDialog calendarDialog = new NewCalendarDialog(InvoiceActivity.this);

        calendarDialog.txt_calendar_title.setText(R.string.strmm_todate_title);
        calendarDialog.calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
        calendarDialog.calendar.setLeftArrowMask(
            getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
        calendarDialog.calendar.setRightArrowMask(
            getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.accent));
        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
        calendarDialog.calendar.setTitleFormatter(
            new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        calendarDialog.calendar.setWeekDayFormatter(
            new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

        if (config.smallestScreenWidthDp >= 700) {
          calendarDialog.calendar.setTileSize(
              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
                  getResources().getDisplayMetrics()));
        } else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
          calendarDialog.calendar.setTileSize(
              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                  getResources().getDisplayMetrics()));
        } else if (config.smallestScreenWidthDp < 600) {
          calendarDialog.calendar.setTileSize(
              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36,
                  getResources().getDisplayMetrics()));
        }
        calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
          private Date today;

          public void choose(String chooseDate) {
            // TODO Auto-generated method stub

            binding.txtTodate.setText(chooseDate);
            calendarDialog.dismiss();
          }
        });

        calendarDialog.show();
      }
    }
  };

  public void getData() {
    Log.d("DATA",SecureParam.getInvoices(token, AppLoginUser.getCodeNo(),
        binding.txtTodate.getText().toString(), list.size() - 1 + ""));
    String param = MCrypt.getInstance()
        .encrypt(SecureParam.getInvoices(token, AppLoginUser.getCodeNo(),
            binding.txtTodate.getText().toString(), list.size() - 1 + ""));
    NetworkEngine.getInstance2().getInvoices(param, new Callback<List<Invoices>>() {
      public void success(List<Invoices> invoices, Response response) {
        if (invoices != null) {
          if (invoices.size() > 0) {
            for (Invoices invoice : invoices) {
              if (!list.contains(invoice)) {
                list.add(invoice);
              }
            }
            invoiceAdapter.replaceList(list);
            endlessRecyclerViewAdapter.onDataReady(true);
          } else {
            endlessRecyclerViewAdapter.onDataReady(false);
          }
        }
      }

      public void failure(RetrofitError retrofitError) {

      }
    });
  }
}
