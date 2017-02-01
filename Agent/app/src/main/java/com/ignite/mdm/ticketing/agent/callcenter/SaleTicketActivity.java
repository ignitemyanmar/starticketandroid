package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.agent.util.Constant;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.FromCitiesAdapter;
import com.ignite.mdm.ticketing.custom.listview.adapter.ToCitiesAdapter;
import com.ignite.mdm.ticketing.custom.listview.adapter.TripTimeAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.Times;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DialogChooserPlace;
import com.ignite.mm.ticketing.application.NewCalendarDialog;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SaleTicketActivity extends BaseSherlockActivity
    implements DialogChooserPlace.DialogListClick {

  private ActionBar actionBar;
  private TextView actionBarTitle;

  private TextView actionBarTitle2;
  private ImageButton actionBarBack;
  private Spinner spn_from_trip;
  private Spinner spn_to_trip;
  private Spinner spn_trip_date;
  private Spinner spn_trip_time;
  private Button btn_search;
  private ArrayList<String> fromCities;
  private FromCitiesAdapter fromCitiesAdapter;
  private String selectedFromCity;
  private ProgressDialog dialog;
  private ArrayList<String> toCities;
  private ToCitiesAdapter toCitiesAdapter;
  private String selectedToCity;
  private String selectedTripDate;
  private String currentDate;
  private String tomorrowDate;
  private Button btn_trip_date;
  private List<Times> tripTimes;
  private TripTimeAdapter tripTimeAdapter;
  private SKConnectionDetector skDetector;
  private Configuration config;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sale_new);
    //Title
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
    upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    getSupportActionBar().setHomeAsUpIndicator(upArrow);
    getSupportActionBar().setTitle("Sale Tickets");
    btn_trip_date = (Button) findViewById(R.id.date_btn);
    Button from = (Button) findViewById(R.id.from);
    Button to = (Button) findViewById(R.id.to);
    from.setOnClickListener(new OnClickListener() {
      public void onClick(View view) {
        DialogChooserPlace dialogChooserPlace = new DialogChooserPlace();
        Bundle bundle = new Bundle();
        bundle.putString(DialogChooserPlace.TYPE, DialogChooserPlace.FROM);
        dialogChooserPlace.setArguments(bundle);

        dialogChooserPlace.show(getSupportFragmentManager(), "from");
      }
    });
    to.setOnClickListener(new OnClickListener() {
      public void onClick(View view) {
        DialogChooserPlace dialogChooserPlace = new DialogChooserPlace();
        Bundle bundle = new Bundle();
        bundle.putString(DialogChooserPlace.TYPE, DialogChooserPlace.TO);
        dialogChooserPlace.setArguments(bundle);
        dialogChooserPlace.show(getSupportFragmentManager(), "to");
      }
    });
    btn_trip_date.setOnClickListener(clickListener);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    Log.i("", "Agent Group Name: " + AppLoginUser.getAgentGroupName());

    //spn_from_trip = (Spinner)findViewById(R.id.spn_from_trip);
    //spn_to_trip = (Spinner)findViewById(R.id.spn_to_trip);
    //btn_trip_date = (Button)findViewById(R.id.btn_trip_date);
    //spn_trip_time = (Spinner)findViewById(R.id.spn_trip_time);
    //btn_search = (Button)findViewById(R.id.btn_search);
    //
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    currentDate = sdf.format(cal.getTime());
    btn_search = (Button) findViewById(R.id.fab);
    btn_search.setOnClickListener(clickListener);
    btn_search.setEnabled(false);
    //
    ////Add one day to current date time
    //cal.add(Calendar.DATE, 1);
    tomorrowDate = sdf.format(cal.getTime());
    btn_trip_date.setText(tomorrowDate);
    //

    //TODO fix me
    //skDetector = SKConnectionDetector.getInstance(this);
    //if (skDetector.isConnectingToInternet()) {
    //
    //  dialog = ProgressDialog.show(SaleTicketActivity.this, "", "Please wait ...", true);
    //  dialog.setCancelable(true);
    //
    //  getFromCities();
    //  getToCities();
    //} else {
    //  Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
    //}f
    //TODO Up to here

    //spn_from_trip.setOnItemSelectedListener(fromCityClickListener);
    //spn_to_trip.setOnItemSelectedListener(toCityClickListener);
    //spn_trip_time.setOnItemSelectedListener(tripTimeClickListener);
    //
    //btn_trip_date.setOnClickListener(clickListener);
    //btn_search.setOnClickListener(clickListener);
    //
    //Check Screen Size
    config = getResources().getConfiguration();
  }

  /**
   * If back arrow button clicked, close this activity.
   */
  @Override public Intent getSupportParentActivityIntent() {
    // TODO Auto-generated method stub
    finish();
    return super.getSupportParentActivityIntent();
  }

  private void getTripTime() {
    // TODO Auto-generated method stub

    dialog = ProgressDialog.show(SaleTicketActivity.this, "", "Please wait ...", true);
    dialog.setCancelable(true);

    tripTimes = new ArrayList<Times>();

    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .getTimesByTrip(AppLoginUser.getAccessToken(),
            ((TextView) findViewById(R.id.from)).getText().toString(),
            ((TextView) findViewById(R.id.to)).getText().toString(), Constant.OPERATOR_ID,
            new Callback<Response>() {

              public void success(Response arg0, Response arg1) {
                // TODO Auto-generated method stub

                if (arg0 != null) {
                  List<Times> Times = new ArrayList<Times>();
                  Times = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Times>>() {
                  }.getType());

                  if (Times != null && Times.size() > 0) {
                    tripTimes.add(new Times("Any Time", "", ""));
                    tripTimes.addAll(Times);
                  }

                  if (tripTimes != null && tripTimes.size() > 0) {
                    Log.i("", "Trip Time List: " + tripTimes.toString());
                    tripTimeAdapter = new TripTimeAdapter(SaleTicketActivity.this, tripTimes);
                    spn_trip_time.setAdapter(tripTimeAdapter);
                  }
                }

                dialog.dismiss();
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

  //----------------------------------------- Click Listener ---------------------------------------------------------------------

  private OnItemSelectedListener fromCityClickListener = new OnItemSelectedListener() {

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      // TODO Auto-generated method stub
      if (position > 0) {
        selectedFromCity = fromCities.get(position);
      } else {
        selectedFromCity = "";
      }
    }

    public void onNothingSelected(AdapterView<?> parent) {
      // TODO Auto-generated method stub

    }
  };

  private OnItemSelectedListener toCityClickListener = new OnItemSelectedListener() {

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      // TODO Auto-generated method stub
      if (position > 0) {
        selectedToCity = toCities.get(position);
        if (selectedFromCity.equals("Choose - From City") || selectedToCity.equals(
            "Choose - To City")) {
          SKToastMessage.showMessage(SaleTicketActivity.this,
              getResources().getString(R.string.strmm_choose_cities), SKToastMessage.WARNING);
        } else {
          if (skDetector.isConnectingToInternet()) {
            getTripTime();
          } else {
            skDetector.showErrorMessage();
          }
        }
      } else {
        selectedToCity = "";
      }
    }

    public void onNothingSelected(AdapterView<?> parent) {
      // TODO Auto-generated method stub

    }
  };
  protected String selectedTripTime = "";

  private OnItemSelectedListener tripTimeClickListener = new OnItemSelectedListener() {

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      // TODO Auto-generated method stub
      if (tripTimes != null) {
        if (tripTimes.size() > 0) {
          if (position > 0) {
            selectedTripTime = tripTimes.get(position).getTime();
          } else {
            selectedTripTime = "";
          }
        }
      }
    }

    public void onNothingSelected(AdapterView<?> parent) {
      // TODO Auto-generated method stub

    }
  };

  private OnClickListener clickListener = new OnClickListener() {

    public void onClick(View v) {
      // TODO Auto-generated method stub
      if (v == btn_trip_date) {

        final NewCalendarDialog calendarDialog = new NewCalendarDialog(SaleTicketActivity.this);

        calendarDialog.txt_calendar_title.setText(R.string.str_calendar_title);
        calendarDialog.calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
        calendarDialog.calendar.setLeftArrowMask(
            getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
        calendarDialog.calendar.setRightArrowMask(
            getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.golden_brown));
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

        //calendarDialog.calendar.setFirstDayOfWeek(Calendar.THURSDAY);

        Calendar calendar = Calendar.getInstance();
        //calendarDialog.calendar.setSelectedDate(calendar.getTime());

        //Allow only 15 days to buy in advance for users
        //If not log in yet,
        Log.i("", "Log in id: " + AppLoginUser.getId());
        if (AppLoginUser.getId() == null || AppLoginUser.getId().equals("")) {
          //Add 14 days to current date time
          calendar.add(Calendar.DATE, 14);
          calendarDialog.calendar.state().edit().setMaximumDate(calendar.getTime());
        } else {
          if (AppLoginUser.getRole() != null && !AppLoginUser.getRole().equals("")) {
            if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
              //If Agents
              //Add 14 days to current date time
              calendarDialog.calendar.state().edit().setMinimumDate(calendar.getTime());
              calendar.add(Calendar.DATE, 14);
              calendarDialog.calendar.state().edit().setMaximumDate(calendar.getTime());
            }
          }
        }

        calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {

          private Date today;

          public void choose(String chooseDate) {
            // TODO Auto-generated method stub

            btn_trip_date.setText(chooseDate);
            calendarDialog.dismiss();
          }
        });

        calendarDialog.show();
      }
      if (v == btn_search) {

        if (checkFields()) {
          Bundle bundle = new Bundle();
          bundle.putString("from_city", ((TextView) findViewById(R.id.from)).getText().toString());
          bundle.putString("to_city", ((TextView) findViewById(R.id.to)).getText().toString());
          bundle.putString("trip_date", btn_trip_date.getText().toString());
          bundle.putString("trip_time", selectedTripTime);
          bundle.putBoolean("not_local",
              ((CheckBox) findViewById(R.id.national_switch)).isChecked());

          startActivity(
              new Intent(SaleTicketActivity.this, BusOperatorSeatsActivity.class).putExtras(
                  bundle));
        }
      }
    }
  };

  public boolean checkFields() {

    // TODO Auto-generated method stub
    if ((((TextView) findViewById(R.id.from)).getText()).toString().equals("Choose - From City")) {

      SKToastMessage.showMessage(SaleTicketActivity.this,
          getResources().getString(R.string.strmm_choose_fromcity), SKToastMessage.WARNING);
      return false;
    }
    if ((((TextView) findViewById(R.id.to)).getText()).toString().equals("Choose - To City")) {
      SKToastMessage.showMessage(SaleTicketActivity.this,
          getResources().getString(R.string.strmm_choose_tocity), SKToastMessage.WARNING);
      return false;
    }

    return true;
  }

  public void onClick(String type, String data) {
    if (type.equals(DialogChooserPlace.FROM)) {
      ((TextView) findViewById(R.id.from)).setText(data);
    } else {
      ((TextView) findViewById(R.id.to)).setText(data);
    }
    if (!((TextView) findViewById(R.id.from)).getText()
        .toString()
        .equalsIgnoreCase(getString(R.string.strmm_choose_fromcity)) && !((TextView) findViewById(
        R.id.to)).getText().toString().equalsIgnoreCase(getString(R.string.strmm_choose_tocity))) {
      ((Button) findViewById(R.id.fab)).setEnabled(true);
    }
  }
}
