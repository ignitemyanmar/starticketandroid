package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityOutStandingbookingBinding;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.sqlite.database.model.OutstandingBooking;
import com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList;
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

/**
 * Created by user on 1/22/17.
 */

public class OutstandingActivity extends BaseSherlockActivity {

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
        resetSoldAmount();
        break;
      case R.id.departure_date:
        getSales = 1;
        lists.clear();
        outstandingAdapter.clear();
        getNetworks();
        resetSoldAmount();
    }
  }

  public void resetSoldAmount() {

    sold_amount = 0;
    binding.leftMoneyAmount.setText(sold_amount + "Ks");
  }

  ActivityOutStandingbookingBinding binding;

  private String token;

  OutstandingAdapter outstandingAdapter = new OutstandingAdapter();
  EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
  int sold_amount = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_out_standingbooking);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setTitle("Outstanding Bookings");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    config = getResources().getConfiguration();
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerView.setHasFixedSize(true);
    binding.sellAmount.setText(
        getSharedPreferences("User", MODE_PRIVATE).getInt("balance", 0) + "Ks");

    if (getSharedPreferences("User", MODE_PRIVATE).getInt("balance", 0) <= 0) {
      binding.sellAmount.setText("0 Ks");
      //binding.pay.setEnabled(false);
    }
    binding.pay.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), AgentDepositFillActivity.class));
      }
    });
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
    binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0)

        {
          binding.card.setVisibility(View.GONE);
        } else if (dy < 0)

        {
          binding.card.setVisibility(View.VISIBLE);
        }
      }

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }
    });

    binding.btnSearch.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        lists.clear();
        outstandingAdapter.clear();
        getNetworks();
        resetSoldAmount();
        endlessRecyclerViewAdapter.onDataReady(true);
      }
    });
    skDetector = SKConnectionDetector.getInstance(this);
    binding.txtTodate.setOnClickListener(clickListener);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    String todayDate = sdf.format(cal.getTime());
    cal.add(Calendar.DAY_OF_MONTH, -10);
    binding.txtTodate.setText(todayDate);
    endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, outstandingAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          public void onLoadMoreRequested() {
            getNetworks();
            Log.d("DSADS", "DAS");
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

  public void getNetworks() {
    //if (!isLoading) {
    //  isLoading = true;

    String param = MCrypt.getInstance()
        .encrypt(SecureParam.getOutstandingBooking(token, AppLoginUser.getCodeNo(), getSales + "",
            binding.txtTodate.getText().toString(), lists.size() - 1 + "", LIMIT));
    NetworkEngine.getInstance2().getOutstandings(param, new Callback<OutstandingBooking>() {
      public void failure(RetrofitError retrofitError) {
        //isLoading = false;
        if (lists.isEmpty()) {
          showAlert("Something's Wrong in Server!");
        }
      }

      public void success(OutstandingBooking outstandingBooking, Response response) {
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
          setSold_amount(outstandingBooking.getTotalAmount());
          outstandingAdapter.replaceList(lists);
          //isLoading = false;
        }
      }
    });
    //}
  }

  public void setSold_amount(int sold_amount) {
    this.sold_amount = this.sold_amount + sold_amount;
    binding.leftMoneyAmount.setText(this.sold_amount + "Ks");
  }

  private View.OnClickListener clickListener = new View.OnClickListener() {

    public void onClick(View v) {
      if (v == binding.txtTodate) {
        final NewCalendarDialog calendarDialog = new NewCalendarDialog(OutstandingActivity.this);

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
    // TODO Auto-generated method stub
    finish();
    return super.getSupportParentActivityIntent();
  }
}

