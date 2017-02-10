package com.ignite.mdm.ticketing.agent.callcenter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mdm.ticketing.custom.listview.adapter.BusClassAdapter;
import com.ignite.mdm.ticketing.custom.listview.adapter.BusSeatAdapter;
import com.ignite.mdm.ticketing.custom.listview.adapter.GroupUserListAdapter;
import com.ignite.mdm.ticketing.http.connection.HttpConnection;
import com.ignite.mdm.ticketing.sqlite.database.model.Agent;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentList;
import com.ignite.mdm.ticketing.sqlite.database.model.BusSeat;
import com.ignite.mdm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mdm.ticketing.sqlite.database.model.ExtraCity;
import com.ignite.mdm.ticketing.sqlite.database.model.OperatorGroupUser;
import com.ignite.mdm.ticketing.sqlite.database.model.Permission;
import com.ignite.mdm.ticketing.sqlite.database.model.ReturnComfrim;
import com.ignite.mdm.ticketing.sqlite.database.model.Seat;
import com.ignite.mdm.ticketing.sqlite.database.model.Seat_list;
import com.ignite.mdm.ticketing.sqlite.database.model.SelectSeat;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.BookingDialog;
import com.ignite.mm.ticketing.application.CloseSeatDialog;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.EditSeatDialog;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.application.TourBookingDialog;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@SuppressLint("SimpleDateFormat") public class BusSelectSeatActivity extends BaseSherlockActivity {

  public static List<BusSeat> Bus_Seat;
  private ListView lvClass;
  private ActionBar actionBar;
  private TextView actionBarTitle;
  private ImageButton actionBarBack;
  private GridView mSeat;
  public static String SelectedSeat;
  protected ArrayList<Seat> Seat;
  protected ProgressDialog dialog;
  private ConnectionDetector connectionDetector;
  private LinearLayout mLoadingView;
  private LinearLayout mNoConnection;
  protected ReturnComfrim returnComfirm;
  private String AgentID = "0";
  private String CustName = "";
  private String CustPhone = "";
  private int RemarkType = 0;
  private String Remark = "";
  private String OperatorID = "0";
  private String FromCity;
  private String ToCity;
  private String Time;
  private String Date;
  private TextView txt_operator;
  private TextView txt_classes;
  private TextView txt_price;
  private TextView txt_dept_date;
  private TextView txt_dept_time;
  private ListView lst_group_user;
  private Button btn_booking;
  private String From;
  private String To;
  private Button btn_now_booking;
  private Integer isBooking = 0;
  private Integer NotifyBooking;
  private TextView actionBarNoti;
  private Button btn_check_out;
  private String Classes;
  protected BookingDialog bookingDialog;
  private List<Seat_list> remarkSeats;
  private ListView lst_remark;
  private String BusClasses;
  private LinearLayout layout_remark;
  private TextView actionBarTitle2;
  private String currentDate;
  private String todayDate;
  private String todayTime;
  private String permit_access_token = "";
  private String permit_ip;
  private String operator_name = "";
  private Date tripDate;
  private Date tDate;
  private Date tripTime;
  private Date tTime;
  private String permit_operator_group_id;
  private String permit_agent_id;
  private String client_operator_id;
  private String tripId = "0";
  public static List<BusSeat> BusSeats;
  public static List<OperatorGroupUser> groupUser = new ArrayList<OperatorGroupUser>();
  public static String CheckOut;
  private Permission permission;
  private String permit_operator_id = "0";
  private BusSeatAdapter seatAdapter;
  protected String permit_operator_phone;
  private Button btn_tour_booking;
  private Button btn_closeseat;
  private Button btn_openseat;
  private List<Agent> agentList;
  private String agentId;
  private String Price;
  private TextView txt_extra_city;
  private String ExtraCity;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);

    //Check Screen Size
    Configuration config = getResources().getConfiguration();
    setContentView(R.layout.bus_seat_list);

    //Get Data from past clicks
    Bundle bundle = getIntent().getExtras();

    if (bundle != null) {
      //permit_access_token = bundle.getString("permit_access_token");
      AgentID = bundle.getString("agent_id");
      OperatorID = bundle.getString("operator_id");

      //Log.e("TAG","~ ----- > operator id : "+OperatorID);

      FromCity = bundle.getString("from_city_id");
      ToCity = bundle.getString("to_city_id");
      From = bundle.getString("from_city");
      To = bundle.getString("to_city");
      Classes = bundle.getString("class_id");
      Time = bundle.getString("trip_time");
      Date = bundle.getString("trip_date");
      //permit_ip = bundle.getString("permit_ip");
      operator_name = bundle.getString("operator_name");
      //permit_operator_group_id = bundle.getString("permit_operator_grou/p_id");
      //permit_agent_id = bundle.getString("permit_agent_id");
      //client_operator_id = bundle.getString("client_operator_id");
      tripId = bundle.getString("tripId");
      Price = bundle.getString("price");
      ExtraCity = bundle.getString("extra_city");
    }

    //Title
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
      toolbar.setTitleTextAppearance(BusSelectSeatActivity.this,
          android.R.attr.textAppearanceSmall);
      toolbar.setSubtitleTextAppearance(BusSelectSeatActivity.this,
          android.R.attr.textAppearanceSmall);
      toolbar.setTitle(From + " - " + To + " [" + operator_name + "]");
      toolbar.setSubtitle(changeDate(Date) + " [" + Time + "]");
      this.setSupportActionBar(toolbar);
    }
    TextView toolbarSubtitle = (TextView) findViewById(R.id.toolbar_subtitle);
    TextView toolbarSubtitle2 = (TextView) findViewById(R.id.toolbar_subtitle2);
    toolbarSubtitle.setText(changeDate(Date) + " [" + Time + "]");
    toolbarSubtitle2.setText(Classes);
    //Log.i("", "(Bus Select Seat) Permit_operator_group_id : "+permit_operator_group_id+", Permit_agent_id : "+permit_agent_id);

    btn_closeseat = (Button) findViewById(R.id.btn_close_seat);
    btn_openseat = (Button) findViewById(R.id.btn_open_seat);
    mSeat = (GridView) findViewById(R.id.grid_seat);
    lst_group_user = (ListView) findViewById(R.id.lst_group_user);
    layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
    connectionDetector = new ConnectionDetector(this);

    if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
      //For Agents
      btn_closeseat.setVisibility(View.GONE);
      btn_openseat.setVisibility(View.GONE);
    }

    btn_closeseat.setOnClickListener(clickListener);
    btn_openseat.setOnClickListener(clickListener);

    //Get TodayDate
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    todayDate = sdf.format(new Date());

    //Get Current Time
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat writeFormat = new SimpleDateFormat("hh:mm aa");
    Calendar cal = Calendar.getInstance();
    Date tTime = null;
    try {
      tTime = sdf2.parse(sdf2.format(cal.getTime()));
    } catch (ParseException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    todayTime = writeFormat.format(tTime);

    //Get only 06:00 AM format
    String time = null;
    try {
      if (Time.length() == 8) {
        time = Time;
      } else if (Time.length() < 8) {
        time = "0" + Time;
      } else if (Time.length() > 8) {
        time = Time.substring(0, 8);
      }
    } catch (StringIndexOutOfBoundsException e) {
      // TODO: handle exception
      Log.i("", "Time Out Of Bound Exception: " + e);
    }

    SimpleDateFormat serverFormat = new SimpleDateFormat("hh:mm aa");
    Date timeTochange = null;
    try {
      if (time != null) {
        timeTochange = serverFormat.parse(time);
        Log.i("", "Server Time Format: " + serverFormat.format(timeTochange));
      }
    } catch (ParseException e2) {
      // TODO Auto-generated catch block
      Log.i("", "Server Time Exception: " + e2);
      e2.printStackTrace();
    }

    SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
    NotifyBooking = notify.getInt("count", 0);
    if (NotifyBooking > 0) {
      actionBarNoti.setVisibility(View.GONE);
      actionBarNoti.setText(NotifyBooking.toString());
    }

    SelectedSeat = "";
    btn_booking = (Button) findViewById(R.id.btn_booking);
    btn_now_booking = (Button) findViewById(R.id.btn_now_booking);
    btn_tour_booking = (Button) findViewById(R.id.btn_tour_booking);

    Log.i("", "");

    if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
      //For Agents
      btn_booking.setVisibility(View.GONE);
      btn_now_booking.setVisibility(View.GONE);
    }

    btn_check_out = (Button) findViewById(R.id.btn_check_out);
    mLoadingView = (LinearLayout) findViewById(R.id.ly_loading);
    mNoConnection = (LinearLayout) findViewById(R.id.no_internet);
    txt_operator = (CustomTextView) findViewById(R.id.txt_operator);
    txt_classes = (CustomTextView) findViewById(R.id.txt_classes);
    txt_price = (CustomTextView) findViewById(R.id.txt_price);
    txt_dept_date = (CustomTextView) findViewById(R.id.txt_departure_date);
    txt_dept_time = (CustomTextView) findViewById(R.id.txt_departure_time);
    txt_extra_city = (TextView) findViewById(R.id.txt_extra_city);
    txt_dept_date.setText(getResources().getString(R.string.strmm_dept_date) + " : " + Date);
    txt_dept_time.setText(getResources().getString(R.string.strmm_dept_time) + " : " + Time);

    if (ExtraCity != null) {
      if (ExtraCity.length() > 0) {
        txt_extra_city.setVisibility(View.VISIBLE);
        txt_extra_city.setText(
            getResources().getString(R.string.strmm_boarding_place) + " " + ExtraCity);
      }
    }

    btn_booking.setOnClickListener(clickListener);
    btn_now_booking.setOnClickListener(clickListener);
    btn_check_out.setOnClickListener(clickListener);
    btn_tour_booking.setOnClickListener(clickListener);
  }

  @Override protected void onResume() {
    // TODO Auto-generated method stub
    super.onResume();
    if (SelectedSeat.length() != 0) finish();
    if (connectionDetector.isConnectingToInternet()) {
      mLoadingView.setVisibility(View.VISIBLE);
      mLoadingView.startAnimation(topInAnimaiton());

      //Get permission & Get seat plan
      getPermission();
    } else {
      Toast.makeText(BusSelectSeatActivity.this, "No Internet Connection", Toast.LENGTH_SHORT)
          .show();
      /*mNoConnection.setVisibility(View.VISIBLE);
      mNoConnection.startAnimation(topInAnimaiton());*/
    }
  }

  private void getAgent() {
    Log.i("", "param agent: " + permit_access_token + ", and: " + AppLoginUser.getId());

    String param = MCrypt.getInstance()
        .encrypt(SecureParam.getAllAgentParam(permit_access_token, permit_operator_id));

    Log.i("", "Param agent: " + param);

    NetworkEngine.setIP(permit_ip);
    NetworkEngine.getInstance().getAllAgent(param, new Callback<Response>() {

      public void failure(RetrofitError arg0) {
        // TODO Auto-generated method stub

      }

      public void success(Response arg0, Response arg1) {
        // TODO Auto-generated method stub

        if (arg0 != null) {
          AgentList agents = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<AgentList>() {
          }.getType());
          agentList = agents.getAgents();
					
	/*				for (int i = 0; i < agentList.size(); i++) {
						agentId = agentList.get(i).getId();
					}*/
          Log.i("", "Agent list: " + agentList.toString());
        } else {
          Log.i("", "agent list is null");
        }
      }
    });
  }

  private void getOperatorGroupUser() {

    Log.i("", "Enter Operator Gourp User!!!!!!!!!!!!!!! "
        + permit_access_token
        + " "
        + AppLoginUser.getId());

    String param = MCrypt.getInstance()
        .encrypt(SecureParam.getOperatorGroupUserParam(permit_access_token, OperatorID));

    Log.i("", "Param Operator g: " + param);

    NetworkEngine.getInstance().getOperatorGroupUser(param, new Callback<Response>() {

      public void failure(RetrofitError arg0) {
        // TODO Auto-generated method stub
        if (arg0 != null) {
          Log.i("", "Error Operator Group: " + arg0.getResponse().getStatus());
          Log.i("",
              "Permit A Token: " + permit_access_token + ", user id: " + AppLoginUser.getId());
        }
      }

      public void success(Response arg0, Response arg1) {
        // TODO Auto-generated method stub

        Log.i("", "Operator Group User (Response): " + arg0.toString());

        if (arg0 != null) {
          groupUser =
              DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorGroupUser>>() {
              }.getType());

          if (groupUser != null && groupUser.size() > 0) {

            Log.i("", "Hello Group User(size): "
                + groupUser.size()
                + "Group User: "
                + groupUser.toString());

            lst_group_user.setAdapter(
                new GroupUserListAdapter(BusSelectSeatActivity.this, groupUser));
            setListViewHeightBasedOnChildren(lst_group_user);
          }
        }
      }
    });
  }

  private Animation topInAnimaiton() {
    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_in);
    anim.reset();
    return anim;
  }

  private Animation topOutAnimaiton() {
    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_out);
    anim.reset();
    return anim;
  }

  private void setupBooking() {
    bookingDialog = new BookingDialog(BusSelectSeatActivity.this, null);
    bookingDialog.setCallbackListener(new BookingDialog.Callback() {

      public void onCancel() {
        // TODO Auto-generated method stub

      }

      public void onSave(String agentId, String custName, String custPhone, int remarkType,
          String remark) {
        // TODO Auto-generated method stub
        isBooking = 1;
        AgentID = "0";
        CustName = custName;
        CustPhone = custPhone;
        RemarkType = remarkType;
        Remark = remark;
        postSale();
      }
    });

    bookingDialog.getWindow()
        .setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    bookingDialog.show();
  }

  private void getPermission() {
    // TODO Auto-generated method stub
    //1. Get Permission
    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .getPermission(AppLoginUser.getAccessToken(), OperatorID, new Callback<Response>() {

          public void failure(RetrofitError arg0) {
            // TODO Auto-generated method stub
            if (arg0.getResponse() != null) {
              Log.i("", "Fail permission: " + arg0.getResponse().getStatus());
              Log.i("", "Trip Operator ID: " + OperatorID);
            }
            Log.i("", "Fail permission: " + arg0.getCause().toString());

            Toast.makeText(BusSelectSeatActivity.this, "No Permission " + arg0.getCause(),
                Toast.LENGTH_SHORT).show();
          }

          public void success(Response arg0, Response arg1) {
            // TODO Auto-generated method stub
            Log.i("", "Trip Operator ID: " + OperatorID);
            if (arg0 != null) {
              permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>() {
              }.getType());

              if (permission != null) {
                permit_ip = permission.getIp();
                permit_access_token = permission.getAccess_token();
                permit_operator_id = permission.getOperator_id();
                permit_operator_group_id = permission.getOperatorgroup_id();
                permit_agent_id = permission.getOnlinesaleagent_id();
                permit_operator_phone = permission.getOperator_phone();

                Log.i("", "Operator phone: " + permit_operator_phone);

                if (connectionDetector.isConnectingToInternet()) {
                  getSeatPlan();
                } else {
                  Toast.makeText(BusSelectSeatActivity.this, "No Internet Connection",
                      Toast.LENGTH_SHORT).show();
                }
              }
            }
          }
        });
  }

  private void getSeatPlan() {

    String param = MCrypt.getInstance()
        .encrypt(
            SecureParam.getSeatPlanParam(permit_access_token, permit_operator_id, tripId, "", "",
                "", Date, ""));

    Log.i("", "Permit token: "
        + permit_access_token
        + ", Operator Id: "
        + permit_operator_id
        + ", Trip Id: "
        + tripId
        + ", Date: "
        + Date);

    Log.i("", "Param to get Seats: " + param);

    //NetworkEngine.setIP("128.199.81.168");
    NetworkEngine.setIP(permit_ip);
    NetworkEngine.getInstance().getItems(param, new Callback<Response>() {

      public void success(Response arg0, Response arg1) {
        // TODO Auto-generated method stub
        // Try to get response body
        if (arg0 != null) {
          SelectedSeat = "";

          Log.i("", "Success Seat Plan: ");

          BusSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<BusSeat>>() {
          }.getType());

          if (BusSeats != null && BusSeats.size() > 0) {
            getData();
            mLoadingView.setVisibility(View.GONE);
            mLoadingView.startAnimation(topOutAnimaiton());
          }
        }

        if (connectionDetector.isConnectingToInternet()) {
          getExtraDestination();
        } else {
          Toast.makeText(BusSelectSeatActivity.this, "No Internet Connection", Toast.LENGTH_SHORT)
              .show();
        }
      }

      public void failure(RetrofitError arg0) {
        // TODO Auto-generated method stub
        Log.i("", "Hello Seat Error: " + arg0.getCause());
        //Log.i("","Hello Seat Error: "+ arg0.getResponse().getBody());
        //Log.i("","Hello Seat Error: "+ arg0.getResponse().getHeaders().toString());
      }
    });
  }

  public void postSale() {
    dialog = ProgressDialog.show(this, "", " Please wait...", true);
    dialog.setCancelable(true);

    List<SelectSeat> seats = new ArrayList<SelectSeat>();

    Log.i("", "Selected Seat: " + SelectedSeat);

    if (!SelectedSeat.equals("") && SelectedSeat.length() > 0) {
      String[] selectedSeat = SelectedSeat.split(",");

      for (int i = 0; i < selectedSeat.length; i++) {
        try {
          seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0).getId(), BusSeats.get(0)
              .getSeat_plan()
              .get(0)
              .getSeat_list()
              .get(Integer.valueOf(selectedSeat[i]))
              .getSeat_no()
              .toString()));
        } catch (Exception e) {
          // TODO: handle exception
          e.printStackTrace();
        }
      }
    }

    final String FromCity = BusSeats.get(0).getSeat_plan().get(0).getFrom().toString();
    final String ToCity = BusSeats.get(0).getSeat_plan().get(0).getTo().toString();

    Log.i("", "Hello From City: "
        + FromCity
        + " , To City : "
        + ToCity
        + " and Select Seat -> "
        + seats.toString());

    AgentID = String.valueOf(AppLoginUser.getId());

    //Do Encrypt of Params
    String param = MCrypt.getInstance()
        .encrypt(SecureParam.postSaleParam(permit_access_token, permit_operator_id, permit_agent_id,
            CustName, CustPhone, String.valueOf(RemarkType), "", permit_operator_group_id,
            MCrypt.getInstance().encrypt(seats.toString()),
            BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date, FromCity, ToCity,
            String.valueOf(AppLoginUser.getId()), DeviceUtil.getInstance(this).getID(),
            isBooking.toString(), String.valueOf(AppLoginUser.getId()), "true"));

    Log.i("", "Param Book: " + param);

    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("param", param));

    final Handler handler = new Handler() {

      public void handleMessage(Message msg) {

        String jsonData = msg.getData().getString("data");

        Log.i("ans:", "Server Response: " + jsonData);

        try {

          JSONObject jsonObject = null;

          if (jsonData != null) {
            jsonObject = new JSONObject(jsonData);
          }
          String SeatLists = "";
          String TicketLists = "";

          if (jsonObject != null) {
            if (jsonObject.getString("status").equals("1")) {

              if (jsonObject.getBoolean("can_buy") && jsonObject.getString("device_id")
                  .equals(DeviceUtil.getInstance(BusSelectSeatActivity.this).getID())) {

                //Get Seats No. including (,)
                JSONArray jsonArray = jsonObject.getJSONArray("tickets");

                for (int i = 0; i < jsonArray.length(); i++) {
                  JSONObject obj = jsonArray.getJSONObject(i);
                  if (i == jsonArray.length() - 1) {
                    SeatLists += obj.getString("seat_no");
                  } else {
                    SeatLists += obj.getString("seat_no") + ",";
                  }
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                  JSONObject obj = jsonArray.getJSONObject(i);
                  if (i == jsonArray.length() - 1) {
                    TicketLists += obj.getString("ticket_no");
                  } else {
                    TicketLists += obj.getString("ticket_no") + ",";
                  }
                }

                Log.i("", "Ticket List: " + TicketLists);

                //Buy Ticket
                if (isBooking == 0) {
                  dialog.dismiss(); //finish can buy ticket
                  Intent nextScreen =
                      new Intent(BusSelectSeatActivity.this, BusConfirmActivity.class);

                  Bundle bundle = new Bundle();
                  bundle.putString("from_intent", "checkout");
                  bundle.putString("Operator_Name", BusSeats.get(0).getOperator());
                  bundle.putString("from_to", From + " => " + To);
                  bundle.putString("time", Time);
                  bundle.putString("classes", BusClasses);
                  bundle.putString("date", Date);
                  bundle.putString("selected_seat", SeatLists);
                  bundle.putString("sale_order_no", jsonObject.getString("sale_order_no"));
                  bundle.putString("bus_occurence",
                      BusSeats.get(0).getSeat_plan().get(0).getId().toString());
                  bundle.putString("Price", BusSeats.get(0).getSeat_plan().get(0).getPrice() + "");
                  bundle.putString("ForeignPrice",
                      BusSeats.get(0).getSeat_plan().get(0).getForeign_price() + "");
                  bundle.putString("ConfirmDate", todayDate);
                  bundle.putString("ConfirmTime", todayTime);
                  bundle.putString("CustomerName", AppLoginUser.getUserName());
                  //Get Seat Count
                  String[] seats = SeatLists.split(",");
                  bundle.putString("SeatCount", seats.length + "");
                  bundle.putString("permit_ip", permit_ip);
                  bundle.putString("permit_access_token", permit_access_token);

                  bundle.putString("permit_operator_group_id", permit_operator_group_id);
                  bundle.putString("permit_agent_id", permit_agent_id);
                  bundle.putString("permit_operator_id", permit_operator_id);

                  bundle.putString("ticket_nos", TicketLists);
                  bundle.putString("operatorPhone", permit_operator_phone);

                  bundle.putString("extra_city", ExtraCity);

                  //bundle.putString("client_operator_id", client_operator_id);

                  nextScreen.putExtras(bundle);
                  startActivity(nextScreen);
                } else { //Booking Finished!
                  isBooking = 0;
                  postSaleConfirm(jsonObject.getString("sale_order_no"), SeatLists, TicketLists,
                      BusSeats.get(0).getSeat_plan().get(0).getId().toString());
                  //postOnlineSale(jsonObject.getString("sale_order_no"), SeatLists, TicketLists, jsonObject);
                }
              } else {
                dialog.dismiss();
                SKToastMessage.showMessage(BusSelectSeatActivity.this,
                    "သင္ မွာယူေသာ လက္ မွ�?္ မ်ားမွာ စက�?�န္႔ပုိင္း အ�?ြင္း �?စ္ျ�?ားသူ ယူသြားပါသည္�?� ေက်းဇူးျပဳ�?? �?ျ�?ား လက္ မွ�?္ မ်ား ျပန္ေရြးေပးပါ�?�",
                    SKToastMessage.ERROR);
                //Get permission & Seat Plan
                getPermission();
              }
            } else {
              isBooking = 0;
              dialog.dismiss();
              SKToastMessage.showMessage(BusSelectSeatActivity.this,
                  "အ�?်ိန္ ေနာက္ က် ေနသည့္ အ�?ြက္ �?�ယ္ လုိ႔ မရပါ", SKToastMessage.ERROR);
            }
          }
        } catch (JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    };

    HttpConnection lt =
        new HttpConnection(handler, "POST", "http://" + permit_ip + "/sale", params);
    lt.execute();
  }

  protected String TicketLists = "";
  private String[] typeTicketArray;

  private void postSaleConfirm(String orderNo, final String SeatLists, final String ticketNos,
      String busOccurance) {
    // TODO Auto-generated method stub
    String[] selectedSeat = SeatLists.split(",");
    //Enter Star Ticket Nos.
    for (int i = 0; i < selectedSeat.length; i++) {
      if (i == selectedSeat.length - 1) {
        TicketLists += 0;
      } else {
        TicketLists += 0 + ",";
      }
    }

    typeTicketArray = TicketLists.split(",");

    List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();

    for (int i = 0; i < selectedSeat.length; i++) {
      seats.add(new ConfirmSeat(busOccurance, selectedSeat[i].toString(), CustName, "",
          typeTicketArray[i].toString(), false, "", 0, ""));
    }

    Log.i("", "Post sale confirm: "
        + permit_access_token
        + ", "
        + orderNo
        + ", "
        + permit_agent_id
        + ", "
        + CustName
        + ", "
        + CustPhone
        + ", "
        + extraCityID
        + ", "
        + seats.toString()
        + ", "
        + DeviceUtil.getInstance(this).getID()
        + ", "
        + String.valueOf(AppLoginUser.getId()));

    //Do Encrypt of Params
    //In remark type para, put selected agent name
    String param = MCrypt.getInstance()
        .encrypt(
            SecureParam.postSaleConfirmParam(permit_access_token, orderNo, "", permit_agent_id, "",
                CustName, CustPhone, "", "", "", extraCityID,
                MCrypt.getInstance().encrypt(seats.toString()), "1", "local", "",
                DeviceUtil.getInstance(this).getID(), "1", String.valueOf(AppLoginUser.getId())));

    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("param", param));

    Log.i("", "Hello param (for confirm) : " + param);

    final Handler handler = new Handler() {

      public void handleMessage(Message msg) {

        String jsonData = msg.getData().getString("data");

        if (jsonData != null) {
          try {
            Log.i("", "Hello Response Confirm Data:" + jsonData);

            JSONObject jsonObj = new JSONObject(jsonData);

            if (!jsonObj.getBoolean("status") && jsonObj.getString("device_id")
                .equals(DeviceUtil.getInstance(BusSelectSeatActivity.this).getID())) {
              dialog.dismiss();
              SKToastMessage.showMessage(BusSelectSeatActivity.this,
                  getResources().getString(R.string.strmm_ticket_soldout), SKToastMessage.ERROR);
              getPermission();
            } else {
              //If Success
              if (dialog != null) {
                dialog.dismiss();
              }

              SKToastMessage.showMessage(BusSelectSeatActivity.this, "Booking Success...",
                  SKToastMessage.SUCCESS);
              closeAllActivities();
              startActivity(new Intent(BusSelectSeatActivity.this, SaleTicketActivity.class));
            }
          } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        } else {
          Log.i("", "Response confirm is null!");
          dialog.dismiss();
        }
      }
    };

    //bundle.getString("permit_ip")
    HttpConnection lt =
        new HttpConnection(handler, "POST", "http://" + permit_ip + "/sale/comfirm", params);
    lt.execute();
  }

  String param;
  private String ExtraCityName;
  private List<ExtraCity> extraCity;
  private String extraCityID = "0";

  /**
   * Get Extra Destination
   */
  private void getExtraDestination() {

    extraCity = new ArrayList<ExtraCity>();
    param = MCrypt.getInstance().encrypt(SecureParam.getExtraDestinationParam(permit_access_token));

    NetworkEngine.setIP(permit_ip);
    NetworkEngine.getInstance()
        .getExtraDestination(param,
            MCrypt.getInstance().encrypt(BusSeats.get(0).getSeat_plan().get(0).getId().toString()),
            new Callback<Response>() {

              public void success(Response arg0, Response arg1) {
                // TODO Auto-generated method stub
                Log.i("",
                    "Extra Dest(token-param): "
                        + param
                        + ", Busoccurance: "
                        + MCrypt.getInstance()
                        .encrypt(BusSeats.get(0).getSeat_plan().get(0).getId().toString())
                        + ", permit_ip: "
                        + permit_ip);

                if (arg0 != null) {

                  List<ExtraCity> cities =
                      DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<ExtraCity>>() {
                      }.getType());

                  if (cities != null && cities.size() > 0) {
                    Log.i("", "extra cities: " + cities.toString());

                    extraCity = cities;

                    for (int i = 0; i < extraCity.size(); i++) {
                      if (extraCity.get(i).getCity_name().equals(ExtraCity)) {
                        extraCityID = extraCity.get(i).getId();
                        break;
                      }
                    }
                  } else {
                    extraCityID = "0";
                    Log.i("", "extra cities are null!");
                  }
                } else {
                  extraCityID = "0";
                  Log.i("", "extra cities are null!");
                }
              }

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  Log.i("", "Extra city error: " + arg0.getResponse().getStatus());
                }
              }
            });
  }

  /**
   * Store booking into Online Sale Database (starticketmyanmar.com)
   */
  private void postOnlineSale(String orderNo, final String SeatLists, final String ticketNos,
      final JSONObject jsonObject) {
    // TODO Auto-generated method stub
    Log.i("", "Ticket No: " + ticketNos);

    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .postOnlineSaleDB(orderNo, permit_operator_id, AppLoginUser.getCodeNo(), 1,
            AppLoginUser.getAccessToken(), "", ticketNos, new Callback<Response>() {

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  Log.i("", "Error: " + arg0.getResponse().getStatus());
                }
                dialog.dismiss();
              }

              public void success(Response arg0, Response arg1) {
                // TODO Auto-generated method stub
                if (arg1 != null) {

                  try {
                    Bundle bundle = new Bundle();
                    bundle.putString("Operator_Name", BusSeats.get(0).getOperator());
                    bundle.putString("from_to", From + "-" + To);
                    bundle.putString("time", Time);
                    bundle.putString("classes", BusClasses);
                    bundle.putString("date", changeDateString(Date));
                    bundle.putString("selected_seat", SeatLists);
                    bundle.putString("sale_order_no", jsonObject.getString("sale_order_no"));
                    bundle.putString("bus_occurence",
                        BusSeats.get(0).getSeat_plan().get(0).getId().toString());
                    bundle.putString("ticket_price",
                        BusSeats.get(0).getSeat_plan().get(0).getPrice() + "");
                    bundle.putString("ConfirmDate", todayDate);
                    bundle.putString("ConfirmTime", todayTime);
                    bundle.putString("CustomerName", AppLoginUser.getUserName());
                    bundle.putString("BuyerName", CustName);
                    bundle.putString("BuyerPhone", CustPhone);
                    bundle.putString("BuyerNRC", "-");
                    //Get Seat Count
                    String[] seats = SeatLists.split(",");
                    bundle.putString("SeatCount", seats.length + "");
                    bundle.putString("permit_ip", permit_ip);
                    bundle.putString("permit_access_token", permit_access_token);

                    Integer amount =
                        BusSeats.get(0).getSeat_plan().get(0).getPrice() * seats.length;
                    bundle.putString("total_amount", amount.toString());

                    bundle.putString("operatorPhone", permit_operator_phone);

                    //OrderDateTime = bundle.getString("order_date");

                    //Show Voucher
                    //startActivity(new Intent(BusSelectSeatActivity.this, PDFBusActivity.class).putExtras(bundle));

                    SKToastMessage.showMessage(BusSelectSeatActivity.this, "Booking Success...",
                        SKToastMessage.SUCCESS);
                    closeAllActivities();
                    startActivity(new Intent(BusSelectSeatActivity.this, SaleTicketActivity.class));
                    //finish();
                    dialog.dismiss();
                  } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                  }
                }
              }
            });
  }

  private void getData() {

    if (BusSeats.size() > 0) {

      Log.i("", "Bus Seats: " + BusSeats.toString());
      //Log.i("", "Seats Plan: "+BusSeats.get(0).getSeat_plan());

      txt_operator.setText(getResources().getString(R.string.strmm_operator_name) + " : " + BusSeats
          .get(0)
          .getOperator());
      txt_classes.setText(
          getResources().getString(R.string.strmm_bus_class) + " : " + BusSeats.get(0)
              .getSeat_plan()
              .get(0)
              .getClasses());
      txt_price.setText(getResources().getString(R.string.strmm_price) + " : " + Price + " Ks");

      BusClasses = BusSeats.get(0).getSeat_plan().get(0).getClasses();
      remarkSeats = new ArrayList<Seat_list>();

      Map<Integer, List<Seat_list>> map = new HashMap<Integer, List<Seat_list>>();
      for (Seat_list remarkSeat : BusSeats.get(0).getSeat_plan().get(0).getSeat_list()) {
        Log.i("", "Seat List: " + remarkSeat.toString());

        if (remarkSeat.getRemark_type() != 0) {
          Integer key = remarkSeat.getRemark_type();
          if (map.containsKey(key)) {
            map.get(key).add(remarkSeat);
          } else {
            List<Seat_list> list = new ArrayList<Seat_list>();
            list.add(remarkSeat);
            map.put(key, list);
          }
        }
      }

      layout_remark.setVisibility(View.GONE);

      //Set Seat List in Grid
      mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(0).getColumn());

      Log.i("", "Seat List (mseat): " + BusSeats.get(0).getSeat_plan().get(0).getSeat_list());

      seatAdapter = new BusSeatAdapter(this, BusSeats.get(0).getSeat_plan().get(0).getSeat_list(),
          AppLoginUser.getRole());
      seatAdapter.setCallbacks(callbacks);
      mSeat.setAdapter(seatAdapter);

      setGridViewHeightBasedOnChildren(mSeat,
          Integer.valueOf(BusSeats.get(0).getSeat_plan().get(0).getColumn()));

      lvClass = (ListView) findViewById(R.id.lvBusClass);
      lvClass.setAdapter(new BusClassAdapter(this, BusSeats.get(0).getSeat_plan()));
      lvClass.setOnItemClickListener(itemClickListener);

      getAgent();
    } else {
      AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
      alertDialog.setMessage("There is no bus yet.");
      alertDialog.setCancelable(false);
      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface dialog, int which) {
          // TODO Auto-generated method stub
          finish();
        }
      });

      alertDialog.show();
    }
  }

  private OnItemClickListener itemClickListener = new OnItemClickListener() {

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      txt_operator.setText("ကားဂိ�?္ :   " + BusSeats.get(0).getOperator());
      txt_classes.setText(
          "ယာဥ္ အမ်ိဳးအစား :  " + BusSeats.get(0).getSeat_plan().get(position).getClasses());
      txt_price.setText(
          "ေစ်း �?ႈန္း :  " + BusSeats.get(0).getSeat_plan().get(position).getPrice() + " Ks");
      mSeat.setNumColumns(BusSeats.get(0).getSeat_plan().get(position).getColumn());
      //mSeat.setAdapter(new BusSeatAdapter(BusSelectSeatActivity.this, BusSeats.get(0).getSeat_plan().get(position).getSeat_list()));
      setGridViewHeightBasedOnChildren(mSeat,
          Integer.valueOf(BusSeats.get(0).getSeat_plan().get(position).getColumn()));
    }
  };

  protected EditSeatDialog editSeatDialog;
  private BusSeatAdapter.Callbacks callbacks = new BusSeatAdapter.Callbacks() {

    public void onClickEdit(final Seat_list list) {
      // TODO Auto-generated method stub
      editSeatDialog = new EditSeatDialog(BusSelectSeatActivity.this);
      editSeatDialog.setName(list.getCustomerInfo().getName());
      editSeatDialog.setPhone(list.getCustomerInfo().getPhone());
      editSeatDialog.setNRC(list.getCustomerInfo().getNrcNo());
      editSeatDialog.setTicketNo(list.getCustomerInfo().getTicketNo());
      editSeatDialog.setCallbackListener(new EditSeatDialog.Callback() {

        private ProgressDialog dialog1;

        public void onEdit() {
          dialog = ProgressDialog.show(BusSelectSeatActivity.this, "", " Please wait...", true);
          dialog.setCancelable(true);
          // TODO Auto-generated method stub
          String param = MCrypt.getInstance()
              .encrypt(SecureParam.editSeatInfoParam(permit_access_token,
                  BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date, list.getSeat_no(),
                  editSeatDialog.getName(), editSeatDialog.getPhone(), editSeatDialog.getNRC(),
                  editSeatDialog.getTicketNo()));
          NetworkEngine.setIP(permit_ip);
          NetworkEngine.getInstance().editSeatInfo(param, new Callback<Response>() {

            public void failure(RetrofitError arg0) {
              // TODO Auto-generated method stub
            }

            public void success(Response arg0, Response arg1) {
              // TODO Auto-generated method stub
              NetworkEngine.setIP("starticketmyanmar.com");
              NetworkEngine.getInstance()
                  .editSeatInfo(AppLoginUser.getAccessToken(),
                      BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date,
                      editSeatDialog.getName(), editSeatDialog.getPhone(), editSeatDialog.getNRC(),
                      editSeatDialog.getTicketNo(), list.getSeat_no(), new Callback<Response>() {

                        public void failure(RetrofitError arg0) {
                          // TODO Auto-generated method stub

                        }

                        public void success(Response arg0, Response arg1) {
                          // TODO Auto-generated method stub
                          onResume();
                          dialog.dismiss();
                          editSeatDialog.dismiss();
                          SKToastMessage.showMessage(BusSelectSeatActivity.this,
                              "Successfully Updated.", SKToastMessage.SUCCESS);
                        }
                      });
            }
          });
        }

        public void onCancel() {

          // TODO Auto-generated method stub
          alertDialog("Are you sure, you want to delete?", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
              // TODO Auto-generated method stub
              dialog.dismiss();
              dialog1 =
                  ProgressDialog.show(BusSelectSeatActivity.this, "", " Please wait...", true);
              dialog1.setCancelable(true);
              String param = MCrypt.getInstance()
                  .encrypt(SecureParam.deleteTicketParam(permit_access_token,
                      BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date,
                      list.getSeat_no(), AppLoginUser.getId()));

              NetworkEngine.setIP(permit_ip);
              NetworkEngine.getInstance().deleteTicket(param, new Callback<Response>() {

                public void success(Response arg0, Response arg1) {
                  // TODO Auto-generated
                  // method stub
                  NetworkEngine.setIP("starticketmyanmar.com");
                  NetworkEngine.getInstance()
                      .deleteSeat(AppLoginUser.getAccessToken(),
                          BusSeats.get(0).getSeat_plan().get(0).getId().toString(), Date,
                          list.getSeat_no(), new Callback<Response>() {

                            public void failure(RetrofitError arg0) {
                              // TODO Auto-generated method stub

                            }

                            public void success(Response arg0, Response arg1) {
                              // TODO Auto-generated method stub
                              Log.i("", "response delete: " + arg1.getReason());

                              onResume();
                              dialog1.dismiss();
                              SKToastMessage.showMessage(BusSelectSeatActivity.this,
                                  "Successfully Deleted.", SKToastMessage.SUCCESS);
                              editSeatDialog.dismiss();
                            }
                          });
                }

                public void failure(RetrofitError arg0) {
                  // TODO Auto-generated
                  // method stub
                  dialog1.dismiss();
                }
              });
            }
          }, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
              // TODO Auto-generated method stub
              dialog.dismiss();
            }
          });
        }
      });

      editSeatDialog.getWindow()
          .setLayout(WindowManager.LayoutParams.FILL_PARENT,
              WindowManager.LayoutParams.WRAP_CONTENT);
      editSeatDialog.show();
    }
  };

  private OnClickListener clickListener = new OnClickListener() {

    public void onClick(View v) {
      if (v == btn_closeseat) {
        //Take Own Seat from operators
        postCloseSeat();
      }

      if (v == btn_openseat) {
        //Give Back own seats to operators
        postOpenSeat();
      }

      if (v == btn_tour_booking) {
        TourBookingDialog tourBookingDialog = new TourBookingDialog(BusSelectSeatActivity.this);
        tourBookingDialog.setmCallback(new TourBookingDialog.Callback() {

          public void onTourBook(String seatQty, String agentPhone) {
            // TODO Auto-generated method stub
            if (connectionDetector.isConnectingToInternet()) {
              dialog = ProgressDialog.show(BusSelectSeatActivity.this, "", " Please wait...", true);

              NetworkEngine.setIP("starticketmyanmar.com");
              NetworkEngine.getInstance()
                  .postTourBooking(AppLoginUser.getId(), tripId, Date, seatQty, agentPhone,
                      ExtraCity, new Callback<JsonObject>() {

                        public void failure(RetrofitError arg0) {
                          // TODO Auto-generated method stub
                          if (arg0.getResponse() != null) {
                            Log.i("", "Error: " + arg0.getCause());
                          }
                          SKToastMessage.showMessage(BusSelectSeatActivity.this,
                              "Connection Time Out", SKToastMessage.ERROR);
                          dialog.dismiss();
                        }

                        public void success(JsonObject arg0, Response arg1) {
                          // TODO Auto-generated method stub
                          Log.i("", "success: " + arg0.toString());

                          SKToastMessage.showMessage(BusSelectSeatActivity.this, "Success Booking",
                              SKToastMessage.SUCCESS);
                          closeAllActivities();
                          startActivity(
                              new Intent(BusSelectSeatActivity.this, SaleTicketActivity.class));

                          dialog.dismiss();
                        }
                      });
            } else {
              connectionDetector.showErrorDialog();
            }
          }

          public void onCancel() {
            // TODO Auto-generated method stub

          }
        });

        tourBookingDialog.getWindow()
            .setLayout(WindowManager.LayoutParams.FILL_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        tourBookingDialog.show();
      }
      if (v == actionBarBack) {
        finish();
      }

      if (v == actionBarNoti) {
        SharedPreferences sharedPreferences = getSharedPreferences("order", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.putString("order_date", getToday());
        editor.commit();
        startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
      }

      if (v == btn_booking) {
        if (connectionDetector.isConnectingToInternet()) {
          SharedPreferences sharedPreferences = getSharedPreferences("order", MODE_PRIVATE);
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.clear();
          editor.commit();
          editor.putString("order_date", Date);
          editor.putString("from", FromCity);
          editor.putString("to", ToCity);
          editor.putString("time", Time);
          editor.commit();

          Bundle bundle = new Bundle();
          bundle.putString("operator_id", OperatorID);
          bundle.putString("trip_id", tripId);
          bundle.putString("from_intent", "BusSelectSeat");
          startActivity(
              new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));
        } else {
          connectionDetector.showErrorDialog();
        }
      }

      if (v == btn_now_booking) {
        if (SelectedSeat.length() != 0) {
          if (connectionDetector.isConnectingToInternet()) {
            setupBooking();
          } else {
            connectionDetector.showErrorDialog();
          }
        } else {
          SKToastMessage.showMessage(BusSelectSeatActivity.this,
              getResources().getString(R.string.strmm_choose_seat), SKToastMessage.ERROR);
        }
      }

      if (v == btn_check_out) {
        if (SelectedSeat.length() != 0) {

          if (connectionDetector.isConnectingToInternet()) {
            postSale();
          } else {
            connectionDetector.showErrorDialog();
          }
        } else {
          SKToastMessage.showMessage(BusSelectSeatActivity.this,
              getResources().getString(R.string.strmm_choose_seat), SKToastMessage.ERROR);
        }
      }
    }
  };

  /**
   * Own Seats take from bus operators
   */
  private void postCloseSeat() {

    //Log.i("", "Agent list: "+agentList.toString());

    if (SelectedSeat.length() > 0) {

      String title = getResources().getString(R.string.str_close_seat);
      String agentName = getResources().getString(R.string.str_agent);
      String btnName = getResources().getString(R.string.str_close_seat);

      CloseSeatDialog closeSeatDialog =
          new CloseSeatDialog(BusSelectSeatActivity.this, agentList, title, agentName, btnName);
      closeSeatDialog.setCallbackListener(new CloseSeatDialog.Callback() {

        public void onSave(String agentId, String remark) {
          // TODO Auto-generated method stub
          dialog = ProgressDialog.show(BusSelectSeatActivity.this, "", "Please wait...", true);
          dialog.setCancelable(true);

          List<SelectSeat> seats = new ArrayList<SelectSeat>();
          String[] selectedSeat = SelectedSeat.split(",");

          for (int i = 0; i < selectedSeat.length; i++) {
            if (selectedSeat[i] != null) {
              seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0).getId(),
                  BusSeats.get(0)
                      .getSeat_plan()
                      .get(0)
                      .getSeat_list()
                      .get(Integer.valueOf(selectedSeat[i]))
                      .getSeat_no()
                      .toString()));
            }
          }

          NetworkEngine.setIP(permit_ip);
          NetworkEngine.getInstance()
              .postCloseSeat(permit_access_token, Date,
                  BusSeats.get(0).getSeat_plan().get(0).getId(), "", seats.toString(), agentId,
                  new Callback<Response>() {

                    public void failure(RetrofitError arg0) {
                      // TODO Auto-generated method stub
                      if (dialog != null) {
                        dialog.dismiss();
                      }
                    }

                    public void success(Response arg0, Response arg1) {
                      // TODO Auto-generated method stub

                      if (dialog != null) {
                        dialog.dismiss();
                      }

                      onResume();
                    }
                  });
        }

        public void onCancel() {
          // TODO Auto-generated method stub

        }
      });
    } else {
      SKToastMessage.showMessage(BusSelectSeatActivity.this,
          getResources().getString(R.string.strmm_choose_seat), SKToastMessage.ERROR);
    }
  }

  /**
   * Own Seat give back to operators
   */
  private void postOpenSeat() {
    if (SelectedSeat.length() > 0) {
      dialog = ProgressDialog.show(BusSelectSeatActivity.this, "", "Please wait...", true);
      dialog.setCancelable(true);

      List<SelectSeat> seats = new ArrayList<SelectSeat>();
      String[] selectedSeat = SelectedSeat.split(",");
      for (int i = 0; i < selectedSeat.length; i++) {
        if (selectedSeat[i] != null) {
          seats.add(new SelectSeat(BusSeats.get(0).getSeat_plan().get(0).getId(), BusSeats.get(0)
              .getSeat_plan()
              .get(0)
              .getSeat_list()
              .get(Integer.valueOf(selectedSeat[i]))
              .getSeat_no()
              .toString()));
        }
      }

      Log.i("", "Hello Seat: " + seats.toString());

      NetworkEngine.getInstance()
          .postOpenSeat(permit_access_token, Date, BusSeats.get(0).getSeat_plan().get(0).getId(),
              seats.toString(), new Callback<Response>() {

                public void failure(RetrofitError arg0) {
                  // TODO Auto-generated method stub
                  if (dialog != null) {
                    dialog.dismiss();
                  }
                }

                public void success(Response arg0, Response arg1) {
                  // TODO Auto-generated method stub
                  if (dialog != null) {
                    dialog.dismiss();
                  }

                  onResume();
                }
              });
    } else {
      SKToastMessage.showMessage(BusSelectSeatActivity.this,
          getResources().getString(R.string.strmm_choose_seat), SKToastMessage.ERROR);
    }
  }

  public void showDialog(String msg) {
    LayoutInflater factory = LayoutInflater.from(this);
    final View msgDialogView = factory.inflate(R.layout.custom_msg_dialog, null);
    final AlertDialog msgDialog = new AlertDialog.Builder(this).create();
    msgDialog.setView(msgDialogView);
    LinearLayout msgContainer = (LinearLayout) msgDialogView.findViewById(R.id.dialog_container);
    try {
      JSONObject data = new JSONObject(msg);
      JSONArray arr = data.getJSONArray("Seat");
      for (int i = 0; i < arr.length(); i++) {
        JSONObject obj = arr.getJSONObject(i);
        TextView txtMsg = new TextView(this);
        txtMsg.setText(obj.getString("seatNo") + " is " + obj.getString("seatStatus"));
        msgContainer.addView(txtMsg);
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    msgDialogView.findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        //your business logic
        msgDialog.dismiss();
      }
    });
    msgDialog.show();
  }

  public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
    ListAdapter listAdapter = gridView.getAdapter();
    if (listAdapter == null) {
      // pre-condition
      return;
    }

    int totalHeight = 0;
    int items = listAdapter.getCount();
    int rows = 0;

    View listItem = listAdapter.getView(0, null, gridView);
    listItem.measure(0, 0);
    totalHeight = listItem.getMeasuredHeight();

    float x = 1;
    if (items > columns) {
      x = items / columns;
      rows = (int) (x + 0);
      totalHeight *= rows;
    }

    ViewGroup.LayoutParams params = gridView.getLayoutParams();
    params.height = totalHeight;
    gridView.setLayoutParams(params);
  }

  public static void setListViewHeightBasedOnChildren(ListView listView) {
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

  @Override protected void onStart() {
    // TODO Auto-generated method stub
    RelativeLayout focuslayout = (RelativeLayout) findViewById(R.id.layout_seat_plan);
    focuslayout.requestFocus();
    super.onStart();
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

