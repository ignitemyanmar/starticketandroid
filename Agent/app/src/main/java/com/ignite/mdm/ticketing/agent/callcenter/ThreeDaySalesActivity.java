//package com.ignite.mdm.ticketing.agent.callcenter;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.support.v7.app.ActionBar;
//import android.support.v7.widget.Toolbar;
//import android.util.TypedValue;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
//import com.ignite.mdm.ticketing.custom.listview.adapter.NewSalesAdapter;
//import com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList;
//import com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketModel;
//import com.ignite.mdm.ticketing.sqlite.database.model.ThreeDaySale;
//import com.ignite.mm.ticketing.application.BaseSherlockActivity;
//import com.ignite.mm.ticketing.application.NewCalendarDialog;
//import com.ignite.mm.ticketing.application.SecureParam;
//import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
//import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
//import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
//import com.smk.skconnectiondetector.SKConnectionDetector;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
//public class ThreeDaySalesActivity extends BaseSherlockActivity {
//
//  private String intents = "";
//  private ActionBar actionBar;
//  private TextView actionBarTitle;
//  private TextView actionBarTitle2;
//  private ImageButton actionBarBack;
//  private ListView lv_threeday_sales;
//  private ProgressDialog dialog;
//  private List<ThreeDaySale> lst_threeday_sale;
//  private SKConnectionDetector skDetector;
//  private Spinner spn_book_type;
//  private List<String> menu_list;
//  private Integer selectedBookType;
//  private TextView txt_fromdate;
//  private TextView txt_todate;
//  private Button btn_search;
//  private Configuration config;
//
//  @Override public boolean onCreateOptionsMenu(Menu menu) {
//    //getMenuInflater().inflate(R.menu.menu_choice, menu);
//    return super.onCreateOptionsMenu(menu);
//  }
//
//  public void onOptionsItemSelected(int item) {
//    if (item == R.id.departure_date) {
//      getSales = 1;
//      dialog.show();
//
//      list.clear();
//      getSales();
//    } else if (item == R.id.buy_date) {
//      getSales = 0;
//      dialog.show();
//      list.clear();
//      getSales();
//    }
//  }
//
//  SharedPreferences sharedPreferences;
//  String access_token;
//  String agent_code_no;
//  public List<SoldTicketList> list = new ArrayList<>();
//
//  @Override protected void onCreate(Bundle savedInstanceState) {
//    // TODO Auto-generated method stub
//    super.onCreate(savedInstanceState);
//
//    setContentView(R.layout.activity_threeday_sales);
//
//    sharedPreferences = getSharedPreferences("User", Activity.MODE_PRIVATE);
//    access_token = sharedPreferences.getString("access_token", "");
//    agent_code_no = sharedPreferences.getString("code_no", "");
//
///*		actionBar = getSupportActionBar();
//    actionBar.setCustomView(R.layout.action_bar);
//		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
//				R.id.action_bar_title);
//		//actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
//				R.id.action_bar_title2);
//		actionBarTitle2.setVisibility(View.GONE);
//		//actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
//				R.id.action_bar_back);
//		actionBarBack.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//		actionBarTitle.setText("3 Days Sales");
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);*/
//
//    //Title
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    if (toolbar != null) {
//      toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
//      toolbar.setTitle(R.string.strmm_sale);
//      this.setSupportActionBar(toolbar);
//    }
//
//    config = getResources().getConfiguration();
//
//    txt_fromdate = (TextView) findViewById(R.id.txt_fromdate);
//    txt_todate = (TextView) findViewById(R.id.txt_todate);
//    btn_search = (Button) findViewById(R.id.btn_search);
//
//    Spinner spinner = (Spinner) findViewById(R.id.txt_spinner_items);
//    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//      public void onNothingSelected(AdapterView<?> adapterView) {
//
//      }
//
//      public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//        // An item was selected. You can retrieve the selected item using
//        // parent.getItemAtPosition(pos)
//        if (pos == 0) {
//          onOptionsItemSelected(R.id.departure_date);
//        } else {
//          onOptionsItemSelected(R.id.buy_date);
//        }
//      }
//    });
//
//
//    skDetector = SKConnectionDetector.getInstance(this);
//
//    btn_search.setOnClickListener(clickListener);
//    txt_fromdate.setOnClickListener(clickListener);
//    txt_todate.setOnClickListener(clickListener);
//
//    //Default 1 month
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    Calendar cal = Calendar.getInstance();
//    String todayDate = sdf.format(cal.getTime());
//
//    //Subtract 3 months from current date
//    cal.add(Calendar.DAY_OF_MONTH, -10);
//    String lastTwoDayDate = sdf.format(cal.getTime());
//
//    //Starts #1 of last three months
//    String[] splitDate = todayDate.split("-");
//
//    //Year, Month, Date
//    txt_fromdate.setText(lastTwoDayDate);
//    txt_todate.setText(todayDate);
//    if (skDetector.isConnectingToInternet()) {
//
//      getSales();
//    } else {
//      Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//    }
//  }
//
//  private OnClickListener clickListener = new OnClickListener() {
//
//    public void onClick(View v) {
//      // TODO Auto-generated method stub
//      if (v == btn_search) {
//        if (skDetector.isConnectingToInternet()) {
//          list.clear();
//          getSales();
//        } else {
//          Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }
//      }
//      if (v == txt_fromdate) {
//        final NewCalendarDialog calendarDialog = new NewCalendarDialog(ThreeDaySalesActivity.this);
//
//        calendarDialog.txt_calendar_title.setText(R.string.strmm_fromdate_title);
//        calendarDialog.calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
//        //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
//        calendarDialog.calendar.setLeftArrowMask(
//            getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
//        calendarDialog.calendar.setRightArrowMask(
//            getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
//        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.accent));
//        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
//        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
//        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
//        calendarDialog.calendar.setTitleFormatter(
//            new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
//        calendarDialog.calendar.setWeekDayFormatter(
//            new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
//
//        if (config.smallestScreenWidthDp >= 700) {
//          calendarDialog.calendar.setTileSize(
//              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
//                  getResources().getDisplayMetrics()));
//        } else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
//          calendarDialog.calendar.setTileSize(
//              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
//                  getResources().getDisplayMetrics()));
//        } else if (config.smallestScreenWidthDp < 600) {
//          calendarDialog.calendar.setTileSize(
//              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36,
//                  getResources().getDisplayMetrics()));
//        }
//
//        calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
//
//          private Date today;
//
//          public void choose(String chooseDate) {
//            // TODO Auto-generated method stub
//
//            txt_fromdate.setText(chooseDate);
//            calendarDialog.dismiss();
//          }
//        });
//
//        calendarDialog.show();
//      }
//      if (v == txt_todate) {
//        final NewCalendarDialog calendarDialog = new NewCalendarDialog(ThreeDaySalesActivity.this);
//
//        calendarDialog.txt_calendar_title.setText(R.string.strmm_todate_title);
//        calendarDialog.calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
//        //  calendarDialog.calendar.setArrowColor(getResources().getColor(R.color.sample_primary));
//        calendarDialog.calendar.setLeftArrowMask(
//            getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
//        calendarDialog.calendar.setRightArrowMask(
//            getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
//        calendarDialog.calendar.setSelectionColor(getResources().getColor(R.color.accent));
//        calendarDialog.calendar.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
//        calendarDialog.calendar.setWeekDayTextAppearance(R.style.CustomWeekDayTextAppearance);
//        calendarDialog.calendar.setDateTextAppearance(R.style.CustomDayTextAppearance);
//        calendarDialog.calendar.setTitleFormatter(
//            new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
//        calendarDialog.calendar.setWeekDayFormatter(
//            new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
//
//        if (config.smallestScreenWidthDp >= 700) {
//          calendarDialog.calendar.setTileSize(
//              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
//                  getResources().getDisplayMetrics()));
//        } else if (config.smallestScreenWidthDp >= 600 && config.smallestScreenWidthDp < 700) {
//          calendarDialog.calendar.setTileSize(
//              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
//                  getResources().getDisplayMetrics()));
//        } else if (config.smallestScreenWidthDp < 600) {
//          calendarDialog.calendar.setTileSize(
//              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36,
//                  getResources().getDisplayMetrics()));
//        }
//
//        calendarDialog.setOnCallbacksListener(new NewCalendarDialog.Callbacks() {
//
//          private Date today;
//
//          public void choose(String chooseDate) {
//            // TODO Auto-generated method stub
//
//            txt_todate.setText(chooseDate);
//            calendarDialog.dismiss();
//          }
//        });
//
//        calendarDialog.show();
//      }
//    }
//  };
//
//  public int getSales = 1;
//
//  public void getSales() {
//    if (list.isEmpty()) {
//      dialog = ProgressDialog.show(ThreeDaySalesActivity.this, "", "Please wait .... ", true);
//      dialog.setCancelable(false);
//    }
//    NetworkEngine.setIP("mdm.starticketmyanmar.com");
//    String params =
//        SecureParam.getSales(access_token, agent_code_no, txt_fromdate.getText().toString(),
//            txt_todate.getText().toString(), getSales + "", list.size() - 1 + "", "25");
//
//    NetworkEngine.getInstance().getSales(params, new Callback<SoldTicketModel>() {
//      public void failure(RetrofitError arg0) {
//        // TODO Auto-generated method stub
//        if (arg0.getResponse() != null) {
//          showAlert("Something's Wrong in Server!");
//        }
//
//        if (dialog.isShowing()) {
//          dialog.dismiss();
//        }
//      }
//
//      public void success(SoldTicketModel arg0, Response arg1) { // TODO Auto-generated method stub
//        if (arg0 != null) {
//          for (SoldTicketList soldTicketList : arg0.getSoldTicketList()) {
//            if (!list.contains(soldTicketList)) {
//              list.add(soldTicketList);
//            }
//          }
//          lv_threeday_sales.setAdapter(new NewSalesAdapter(ThreeDaySalesActivity.this, list));
//          lv_threeday_sales.setDividerHeight(0);
//        }
//        if (dialog.isShowing()) {
//          dialog.dismiss();
//        }
//      }
//    });
//  }
//
//  /**
//   * Get Sales Reports for 3 days
//   */
//  //private void getThreeDaySales() {
//  //  dialog = ProgressDialog.show(ThreeDaySalesActivity.this, "", "Please wait ...", true);
//  //  dialog.setCancelable(true);
//  //  NetworkEngine.setIP("starticketmyanmar.com");
//  //  NetworkEngine.getInstance()
//  //      .getThreeDaySales(AppLoginUser.getAccessToken(), getSales,
//  //          getSharedPreferences("User", MODE_PRIVATE).getString("code_no", ""),
//  //          txt_fromdate.getText().toString(), txt_todate.getText().toString(),
//  //          new Callback<List<ThreeDaySale>>() {
//  //
//  //            public void failure(RetrofitError arg0) {
//  //              // TODO Auto-generated method stub
//  //              if (arg0.getResponse() != null) {
//  //
//  //                showAlert("Something's Wrong in Server!");
//  //              }
//  //
//  //              dialog.dismiss();
//  //            }
//  //
//  //            public void success(List<ThreeDaySale> arg0, Response arg1) {
//  //              // TODO Auto-generated method stub
//  //
//  //              if (arg0 != null) {
//  //
//  //                lst_threeday_sale = arg0;
//  //
//  //                if (lst_threeday_sale != null && lst_threeday_sale.size() > 0) {
//  //                  for (int i = 0; i < lst_threeday_sale.size(); i++) {
//  //                    ThreeDaySale sale = (ThreeDaySale) lst_threeday_sale.get(i);
//  //                    sale.setDepartureDate(
//  //                        changeDate(lst_threeday_sale.get(i).getDepartureDate()));
//  //                  }
//  //                  lv_threeday_sales.setAdapter(
//  //                      new ThreeDaySalesLvAdapter(ThreeDaySalesActivity.this, lst_threeday_sale));
//  //                  lv_threeday_sales.setDividerHeight(0);
//  //                } else {
//  //                  lv_threeday_sales.setAdapter(null);
//  //                  showAlert("No Sales!");
//  //                }
//  //              } else {
//  //                lv_threeday_sales.setAdapter(null);
//  //                showAlert("No Sales!");
//  //              }
//  //              dialog.dismiss();
//  //            }
//  //          });
//  //}
//  //
//  //
//  /*
//   * If back arrow button clicked, close this activity.
//   */
//  @Override public Intent getSupportParentActivityIntent() {
//    // TODO Auto-generated method stub
//    finish();
//    return super.getSupportParentActivityIntent();
//  }
//}
