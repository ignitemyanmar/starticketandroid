package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityThreedaySalesBinding;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.NewSalesAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList;
import com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketModel;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 2/7/17.
 */

public class NewSalesActivity extends BaseSherlockActivity {
  private List<SoldTicketList> lists = new ArrayList<SoldTicketList>();
  private SKConnectionDetector skDetector;

  private List<String> menu_list;
  private Integer selectedBookType;
  private final String LIMIT = "10";

  private Configuration config;

  public void onOptionsItemSelected(int item) {
    switch (item) {
      case R.id.buy_date:
        getSales = 0;
        lists.clear();
        outstandingAdapter.clear();
        getNetworks();

        break;
      case R.id.departure_date:
        getSales = 1;
        lists.clear();
        outstandingAdapter.clear();
        getNetworks();
    }
  }

  ActivityThreedaySalesBinding binding;

  private String token;

  NewSalesAdapter outstandingAdapter = new NewSalesAdapter(this);
  EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;

  SharedPreferences sharedPreferences;
  String access_token;
  String agent_code_no;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_threeday_sales);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setTitle("Sales");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    config = getResources().getConfiguration();
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerView.setHasFixedSize(true);
    sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);
    access_token = sharedPreferences.getString("operator_token", "");
    agent_code_no = sharedPreferences.getString("code_no", "");

    Spinner spinner = (Spinner) findViewById(R.id.txt_spinner_items);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      public void onNothingSelected(AdapterView<?> adapterView) {

      }

      public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if (pos == 0) {
          onOptionsItemSelected(R.id.departure_date);
        } else {
          onOptionsItemSelected(R.id.buy_date);
        }
      }
    });

    binding.txtFromdate.setOnClickListener(clickListener);
    binding.btnSearch.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        lists.clear();
        outstandingAdapter.clear();
        getNetworks();

        endlessRecyclerViewAdapter.onDataReady(true);
      }
    });
    skDetector = SKConnectionDetector.getInstance(this);
    binding.txtTodate.setOnClickListener(clickListener);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    String todayDate = sdf.format(cal.getTime());

    cal.add(Calendar.DAY_OF_MONTH, -10);
    binding.txtFromdate.setText(sdf.format(cal.getTime()));
    binding.txtTodate.setText(todayDate);
    endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, outstandingAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          public void onLoadMoreRequested() {
            if (lists.size() > 0) {
              getNetworks();
            }
          }
        });
    binding.recyclerView.setAdapter(endlessRecyclerViewAdapter);

    SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
    token = sharedPreferences.getString("operator_token", null);
    if (skDetector.isConnectingToInternet()) {
      getNetworks();
      //getThreeDaySales();
    } else {
      Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
  }

  private boolean isLoading = false;

  public boolean equalLists(List<SoldTicketList> one, List<SoldTicketList> two) {
    if (one == null && two == null) {
      return true;
    }

    //to avoid messing the order of the lists we will use a copy
    //as noted in comments by A. R. S.
    one = new ArrayList<SoldTicketList>(one);
    two = new ArrayList<SoldTicketList>(two);

    Collections.sort(one);
    Collections.sort(two);
    return one.equals(two);
  }

  boolean first = true;

  public void getNetworks() {
    //if (!isLoading) {
    //  isLoading = true;


    Log.d("--------->",
        SecureParam.getSales(access_token, agent_code_no, binding.txtFromdate.getText().toString(),
            binding.txtTodate.getText().toString(), getSales + "", lists.size() - 1 + "", "25"));
    String params = MCrypt.getInstance()
        .encrypt(SecureParam.getSales(access_token, agent_code_no,
            binding.txtFromdate.getText().toString(), binding.txtTodate.getText().toString(),
            getSales + "", lists.size() - 1 + "", "25"));

    NetworkEngine.getInstance2().getSales(params, new Callback<SoldTicketModel>() {
      public void failure(RetrofitError retrofitError) {
        //isLoading = false;
        if (lists.isEmpty()) {

          showAlert("Something's Wrong in Server!");
          endlessRecyclerViewAdapter.onDataReady(false);
        }
      }

      public void success(SoldTicketModel outstandingBooking, Response response) {
        if (outstandingBooking != null) {
          if (outstandingBooking.getSoldTicketList().size() <= 0) {
            endlessRecyclerViewAdapter.onDataReady(false);
          } else {
            endlessRecyclerViewAdapter.onDataReady(true);
          }

          for (SoldTicketList soldTicketList : outstandingBooking.getSoldTicketList()) {

            if (!lists.contains(soldTicketList)) {
              lists.add(soldTicketList);
            }
          }
          if (!first && equalLists(outstandingBooking.getSoldTicketList(), lists)) {
            endlessRecyclerViewAdapter.onDataReady(false);
          }
          first = false;
          Collections.sort(lists, new Comparator<SoldTicketList>() {
            public int compare(SoldTicketList o1, SoldTicketList o2) {
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

              try {
                if (dateFormat.parse(o1.getDepartureDate()) == null
                    || dateFormat.parse(o2.getDepartureDate()) == null) {
                  return 0;
                }
                return dateFormat.parse(o2.getDepartureDate())
                    .compareTo(dateFormat.parse(o1.getDepartureDate()));
              } catch (Exception e) {
                return 0;
              }
            }
          });

          outstandingAdapter.replaceList(lists);
        } else {
          endlessRecyclerViewAdapter.onDataReady(false);
        }
      }
    });
    //}
  }

  private View.OnClickListener clickListener = new View.OnClickListener() {

    public void onClick(View v) {
      // TODO Auto-generated method stub
      if (v == binding.btnSearch) {
        if (skDetector.isConnectingToInternet()) {
          lists.clear();
          getNetworks();
        } else {
          Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
      }
      if (v == binding.txtFromdate) {
        final NewCalendarDialog calendarDialog = new NewCalendarDialog(NewSalesActivity.this);

        calendarDialog.txt_calendar_title.setText(R.string.strmm_fromdate_title);
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

            binding.txtFromdate.setText(chooseDate);
            calendarDialog.dismiss();
          }
        });

        calendarDialog.show();
      }
      if (v == binding.txtTodate) {
        final NewCalendarDialog calendarDialog = new NewCalendarDialog(NewSalesActivity.this);

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

  public int getSales = 1;

  /**
   * Get Sales Reports for 3 days
   */

  /**
   * If back arrow button clicked, close this activity.
   */
  @Override public Intent getSupportParentActivityIntent() {
    // TODO Auto-generated me
    // thod stub
    finish();
    return super.getSupportParentActivityIntent();
  }
}