package com.ignite.mm.ticketing.starticket;

import info.hoang8f.widget.FButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mm.ticketing.custom.listview.adapter.ExtraCityAdapter;
import com.ignite.mm.ticketing.http.connection.HttpConnection;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;
import com.ignite.mm.ticketing.sqlite.database.model.BundleListObjSeats;
import com.ignite.mm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mm.ticketing.sqlite.database.model.ExtraCity;
import com.ignite.mm.ticketing.sqlite.database.model.GoTripInfo;
import com.ignite.mm.ticketing.starticket.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

/**
 * {@link #BusConfirmActivity} is the class to set customer information
 * <p>
 * Private methods
 * (1) {@link #getSupportParentActivityIntent()}
 * (2) {@link #getExtraDestination()}
 * (3) {@link #itemSelectedListener}
 * (4) {@link #checkFieldsAgent()}
 * (5) {@link #clickListener}
 * (5) {@link #postSale(String)}
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public class BusConfirmActivity extends BaseActivity {

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private FButton btn_confirm;
	private MaterialEditText edt_buyer;
	private MaterialAutoCompleteTextView edt_nrc_no;
	private MaterialEditText edt_phone;
	private ZProgressHUD dialog;
	private String[] selectedSeat;
	private LinearLayout layout_ticket_no_container;
	private String AgentID = "0";
	private List<Agent> agents;
	private TextView txt_agent;
	private RadioButton rdo_cash_down;
	private RadioButton rdo_credit;
	private MaterialAutoCompleteTextView auto_txt_agent;
	private List<Agent> agentList;
	private ArrayAdapter<Agent> agentListAdapter;
	private MaterialEditText edt_ref_invoice_no;
	private RadioButton rdo_local;
	private List<String> nrcFormat;
	private ArrayAdapter<String> nrcListAdapter;
	public static String BusOccurence = "0";
	private String Intents;
	private LinearLayout extra_city_container;
	private Spinner sp_extra_city;
	private List<ExtraCity> extraCity;
	public static String ExtraCityID = "0";
	private Integer NotifyBooking;
	private TextView actionBarNoti;
	private MaterialEditText edt_remark;
	private Spinner sp_remark_type;
	private Integer selectedRemarkType;
	private LinearLayout layout_remark;
	private String Name = "";
	private String Phone = "";
	private Bundle bundle;
	private SKConnectionDetector skDetector;
	private ConnectionDetector connectionDetector;
	private String user_type;
	private MaterialEditText ticket_no;
	private List<CheckBox> lst_free_chk = new ArrayList<CheckBox>();
	private List<LinearLayout> lst_layout_free_ticket = new ArrayList<LinearLayout>();
	private List<CheckBox> lst_discount_chk = new ArrayList<CheckBox>();
	private List<MaterialEditText> lst_discount_edt = new ArrayList<MaterialEditText>();
	private List<RadioButton> lst_rdo_free_pro = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_mnt = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_10plus = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_pilgrim = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_spr = new ArrayList<RadioButton>();
	private List<RadioGroup> lst_rdo_gp_free = new ArrayList<RadioGroup>();
	private List<MaterialEditText> lst_ticket_no = new ArrayList<MaterialEditText>();
	private TextView actionBarTitle2;
	public static String Permit_agent_id;
	public static String permit_operator_group_id;
	public static String permit_operator_id;
	public static String permit_access_token;
	public static String permit_ip;
	private String Nrc = "";
	private Spinner spn_expire_month;
	private Spinner spn_expire_year;
	private RadioButton radio_onilnePayment;
	private RadioButton radio_cashOnShop;
	private RadioButton radio_cashOnDelivery;
	public static String FromCity;
	public static String ToCity;
	public static String Operator_Name;
	public static String from_to;
	public static String time;
	public static String classes;
	public static String date;
	public static String Price = "0";
	public static String ConfirmDate;
	public static String ConfirmTime;
	public static BundleListObjSeats seat_List;
	
	private Integer isBooking = 0;
	private String Selected_seats;
	private RadioButton radio_payWithMPU;
	private RadioButton radio_payWithVisaMaster;
	private RadioGroup radioGroup;
	private TextView txt_booking_fee;
	private TextView txt_from_to;
	private TextView txt_dept_date;
	private TextView txt_dept_time;
	private TextView txt_seats;
	private TextView txt_bus_class;
	private TextView txt_price;
	private TextView txt_total_amount;
	private String SeatCount = "0";
	private int trip_type;
	public static String return_date;
	private String FromName;
	private String ToName;
	private TextView txt_trip_info;
	private String goTripInfo_str;
	private GoTripInfo goTripInfo_obj;
	private TextView txt_trip_info_return;
	private TextView txt_to_from_return;
	private TextView txt_return_date;
	private TextView txt_return_time;
	private TextView txt_return_seatNo;
	private TextView txt_return_bus_class;
	private TextView txt_return_price;
	private LinearLayout layout_return_title;
	private LinearLayout layout_return_trip_info;
	private Integer go_seat_count;
	public static Date deptDateTime;
	public static Calendar cal;
	public static String selectedSeatNos = "";
	
	//real seat count
	public static Integer seat_count = 0;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Show View 
		//Trip Information + to set customer info
		setContentView(R.layout.nrc_activity);
		
		txt_trip_info = (TextView)findViewById(R.id.txt_trip_info);
		txt_from_to = (TextView)findViewById(R.id.txt_from_to);
		txt_dept_date = (TextView)findViewById(R.id.txt_dept_date);
		txt_dept_time = (TextView)findViewById(R.id.txt_dept_time);
		txt_seats = (TextView)findViewById(R.id.txt_seats);
		txt_bus_class = (TextView)findViewById(R.id.txt_bus_class);
		txt_price = (TextView)findViewById(R.id.txt_price);
		
		//Return Trip
		layout_return_title = (LinearLayout)findViewById(R.id.layout_return_title);
		layout_return_trip_info = (LinearLayout)findViewById(R.id.layout_return_trip_info);
		txt_trip_info_return = (TextView)findViewById(R.id.txt_trip_info_return);
		txt_to_from_return = (TextView)findViewById(R.id.txt_to_from_return);
		txt_return_date = (TextView)findViewById(R.id.txt_return_date);
		txt_return_time = (TextView)findViewById(R.id.txt_return_time);
		txt_return_seatNo = (TextView)findViewById(R.id.txt_return_seatNo);
		txt_return_bus_class = (TextView)findViewById(R.id.txt_return_bus_class);
		txt_return_price = (TextView)findViewById(R.id.txt_return_price);
		
		txt_total_amount = (TextView)findViewById(R.id.txt_total_amount);
		
		txt_booking_fee = (TextView)findViewById(R.id.txt_booking_fee);
		btn_confirm = (FButton) findViewById(R.id.btn_confirm);
		btn_confirm.setButtonColor(getResources().getColor(R.color.yellow));
		btn_confirm.setShadowEnabled(true);
		btn_confirm.setShadowHeight(3);
		btn_confirm.setCornerRadius(7);
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup_payment);        
/*		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	            // checkedId is the RadioButton selected
	        	Log.i("", "Radio Id: "+checkedId+", Group id: "+group.getId());
	        	
	            if (checkedId == 2131296842) {
					txt_booking_fee.setVisibility(View.VISIBLE);
				}else {
					txt_booking_fee.setVisibility(View.GONE);
				}
	        }
	    });*/
	    
/*		radio_payWithMPU = (RadioButton)findViewById(R.id.radio_payWithMPU);
		radio_payWithVisaMaster = (RadioButton)findViewById(R.id.radio_payWithVisaMaster);
		radio_onilnePayment = (RadioButton)findViewById(R.id.radio_onilnePayment);
		radio_cashOnShop = (RadioButton)findViewById(R.id.radio_cashOnShop);
		radio_cashOnDelivery = (RadioButton)findViewById(R.id.radio_cashOnDelivery);*/
		
		connectionDetector = new ConnectionDetector(this);
		
/*		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.GONE);
			actionBarNoti.setText(NotifyBooking.toString());
		}*/
		
		bundle = getIntent().getExtras();		
		
		if (bundle != null) {
			Log.i("", "Bundle to confirm: "+bundle.toString());
			
			Intents = bundle.getString("from_intent");
			
			if(Intents.equals("booking")){
				AgentID = bundle.getString("agent_id");
				Name = bundle.getString("name");
				Phone = bundle.getString("phone");
				Nrc = bundle.getString("nrc");
			}
		}
		
		//Title
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle("Passenger Info");
           
            this.setSupportActionBar(toolbar);
        }
		
		if (bundle != null) {
			FromCity = bundle.getString("FromCity");
			ToCity = bundle.getString("ToCity");
			Operator_Name = bundle.getString("Operator_Name");
			from_to = bundle.getString("from_to");
			time = bundle.getString("time");
			classes = bundle.getString("classes");
			date = bundle.getString("date");
			
			if (bundle.getString("Price") != null && !bundle.getString("Price").equals("")) {
				Price  = bundle.getString("Price");
			}
			
			ConfirmDate  = bundle.getString("ConfirmDate");
			ConfirmTime  = bundle.getString("ConfirmTime");
			seat_List  = new Gson().fromJson(bundle.getString("seat_List"), BundleListObjSeats.class);
			BusOccurence = bundle.getString("bus_occurence");
			
			Selected_seats = bundle.getString("Selected_seats");
			permit_operator_id = bundle.getString("permit_operator_id");
			permit_operator_group_id = bundle.getString("permit_operator_group_id");
			Permit_agent_id = bundle.getString("permit_agent_id");
			permit_access_token = bundle.getString("permit_access_token");
			permit_ip = bundle.getString("permit_ip");
			
			Log.i("", "Permit IP (bus confirm): "+BusConfirmActivity.permit_ip);
			
			if (bundle.getString("SeatCount") != null && !bundle.getString("SeatCount").equals("")) {
				SeatCount = bundle.getString("SeatCount");
			}
			
			trip_type = bundle.getInt("trip_type");
			return_date = bundle.getString("return_date");
			
			FromName = bundle.getString("FromName");
			ToName = bundle.getString("ToName");
			
			goTripInfo_str = bundle.getString("GoTripInfo");
			goTripInfo_obj = new Gson().fromJson(goTripInfo_str, GoTripInfo.class);
		}
		
		if (goTripInfo_obj != null) {
			Log.i("", "Go Trip Info(Return): "+goTripInfo_obj.toString());
			
		}
		
		Log.i("", "FromCity: "+FromCity
				+", ToCity: "+ToCity
				+", Operator_Name: "+Operator_Name
				+", from_to: "+from_to
				+", time: "+time
				+", classes: "+classes
				+", date: "+date
				+", Price: "+Price
				+", ConfirmDate: "+ConfirmDate
				+", ConfirmTime: "+ConfirmTime
				+", seat_List: "+seat_List.getSeatsList().toString()
				+", BusOccurence: "+BusOccurence
				+", Selected_seats: "+Selected_seats
				+", permit_operator_id: "+permit_operator_id
				+", permit_operator_group_id: "+permit_operator_group_id
				+", Permit_agent_id: "+Permit_agent_id
				+", permit_access_token: "+permit_access_token
				+", permit_ip: "+permit_ip
				+", seat count: "+SeatCount);
		
		
        if (selectedSeatNos.length() > 0)
			selectedSeatNos = "";
        
        for (int j = 0; j < seat_List.getSeatsList().size(); j++) {
        	if (j == seat_List.getSeatsList().size() - 1) {
        		selectedSeatNos += seat_List.getSeatsList().get(j).getSeat_no();
			}else {
				selectedSeatNos += seat_List.getSeatsList().get(j).getSeat_no()+",";
			}
		}
		
        //Trip Info Title
        if (trip_type == 1) 
        	txt_trip_info.setText("Trip Info (one way)");
        
        //Trip Info
		if (Intents.equals("SaleTicket")) {
			//If One Way (or) After Departure Trip...
			layout_return_title.setVisibility(View.GONE);
			layout_return_trip_info.setVisibility(View.GONE);
			
			txt_from_to.setText(from_to+" ["+Operator_Name+"]");
			txt_dept_date.setText(changeDate(date));
			txt_dept_time .setText(time);
			txt_seats.setText(selectedSeatNos);
			txt_bus_class.setText(classes);
			txt_price.setText(Price+" Ks");
			
			if (selectedSeatNos != null && !selectedSeatNos.equals("")) {
				String[] seat_string = selectedSeatNos.split(",");
				seat_count = seat_string.length;
			}
			
			Integer totalAmount = seat_count * Integer.valueOf(Price);
			txt_total_amount.setText("Total Amount: "+totalAmount+" Ks");
			
		}else if (Intents.equals("BusConfirm")) {
			//After Return Trip chosen
			layout_return_title.setVisibility(View.VISIBLE);
			layout_return_trip_info.setVisibility(View.VISIBLE);
			
			//Show Departure Trip Info (again)
			txt_from_to.setText(goTripInfo_obj.getFrom_to()+" ["+goTripInfo_obj.getOperator_Name()+"]");
			txt_dept_date.setText(changeDate(goTripInfo_obj.getDate()));
			txt_dept_time .setText(goTripInfo_obj.getTime());
			txt_seats.setText(goTripInfo_obj.getSelected_seats());
			txt_bus_class.setText(goTripInfo_obj.getClasses());
			txt_price.setText(goTripInfo_obj.getPrice()+" Ks");
			
			//Show Return Trip Info
			txt_to_from_return.setText(from_to+" ["+Operator_Name+"]");
			txt_return_date.setText(changeDate(return_date));
			txt_return_time.setText(time);
			txt_return_seatNo.setText(selectedSeatNos);
			txt_return_bus_class.setText(classes);
			txt_return_price.setText(Price+" Ks");
			
			Integer go_totalAmount = 0;
			
			//Departure Total
			if (goTripInfo_obj.getSelected_seats() != null && !goTripInfo_obj.getSelected_seats().equals("")) {
				String[] seat_string = goTripInfo_obj.getSelected_seats().split(",");
				go_seat_count = seat_string.length;
			}
			if (goTripInfo_obj.getPrice() != null && !goTripInfo_obj.getPrice().equals("")) {
				int goPrice = Integer.valueOf(goTripInfo_obj.getPrice());
				go_totalAmount = go_seat_count * goPrice;
			}
			
			//Return total
			if (selectedSeatNos != null && !selectedSeatNos.equals("")) {
				String[] seat_string = selectedSeatNos.split(",");
				seat_count = seat_string.length;
			}
			Integer totalAmount = seat_count * Integer.valueOf(Price);
			
			//Round Trip Total
			Integer roundTotal = totalAmount + go_totalAmount;
			txt_total_amount.setText("Total Amount : "+roundTotal+" Ks");
		}

		Log.i("", "Permit_agent_id : "+Permit_agent_id);
		
		edt_buyer = (MaterialEditText) findViewById(R.id.edt_buyer);	
		edt_buyer.setText(AppLoginUser.getUserName());
		edt_nrc_no = (MaterialAutoCompleteTextView) findViewById(R.id.edt_nrc_no);
		
		if (Nrc != null) {
			edt_nrc_no.setText(Nrc);
		}
		edt_phone = (MaterialEditText) findViewById(R.id.edt_phone);
		edt_phone.setText(AppLoginUser.getPhone());
		txt_agent = (CustomTextView) findViewById(R.id.txt_seller);
		edt_ref_invoice_no = (MaterialEditText) findViewById(R.id.edt_ref_invoice_no);
		rdo_cash_down = (RadioButton) findViewById(R.id.rdo_cash_down);
		rdo_credit = (RadioButton) findViewById(R.id.rdo_credit);
		rdo_local = (RadioButton) findViewById(R.id.rdo_local);
		extra_city_container = (LinearLayout) findViewById(R.id.extra_city_container);
		sp_extra_city = (Spinner) findViewById(R.id.sp_extra_city);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		sp_remark_type = (Spinner) findViewById(R.id.sp_remark_type);
		edt_remark = (MaterialEditText) findViewById(R.id.edt_remark);
		
		//pre-input NRC info
		nrcFormat = new ArrayList<String>();
		nrcFormat.add("14/Ba Ba La (N)");
		nrcFormat.add("14/Da Na Pha (N)");
		nrcFormat.add("14/Da Da Ya (N)");
		nrcFormat.add("14/Ah Ma Na (N)");
		nrcFormat.add("14/Ha Tha Ta (N)");
		nrcFormat.add("14/Ah Ga Pa (N)");
		nrcFormat.add("14/Ka Ka Hta (N)");
		nrcFormat.add("14/Ka La Na (N)");
		nrcFormat.add("14/Ka Kha Na (N)");
		nrcFormat.add("14/Ka Ka Na (N)");
		nrcFormat.add("14/Ka Pa Na (N)");
		nrcFormat.add("14/La Pa Ta (N)");
		nrcFormat.add("14/La Ma Na (N)");
		nrcFormat.add("14/Ma Ah Pa (N)");
		nrcFormat.add("14/Ma Ma Ka (N)");
		nrcFormat.add("14/Ma Ah Na (N)");
		nrcFormat.add("14/Ma Ma Na (N)");
		nrcFormat.add("14/Ng Pa ta (N)");
		nrcFormat.add("14/Nya Ta Na (N)");
		nrcFormat.add("14/Pa Ta Na (N)");
		nrcFormat.add("14/Pa Ta Na (N)");
		nrcFormat.add("14/Pha Pa Na (N)");
		nrcFormat.add("14/Pha Pa Na (N)");
		nrcFormat.add("14/Wha Kha Ma (N)");
		nrcFormat.add("14/Ya Ka Na (N)");
		nrcFormat.add("14/ Za La Na (N)");
		nrcFormat.add("7/Ba Ka Na (N)");
		nrcFormat.add("7/Da U Na (N)");
		nrcFormat.add("7/Ka Wah Na(N)");
		nrcFormat.add("7/ Ka Ka Na(N)");
		nrcFormat.add("7/Ka Ta Ka (N)");
		nrcFormat.add("7/Na La Pa (N)");
		nrcFormat.add("7/Ah Ta Na (N)");
		nrcFormat.add("7/Pha Ma Na (N)");
		nrcFormat.add("7/Ya Ka Na (N)");
		nrcFormat.add("7/Hta Ta Pa (N)");
		nrcFormat.add("7/Ta Ng Na(N)");
		nrcFormat.add("7/Tha Na Pa (N)");
		nrcFormat.add("7/Wah Ma Na(N)");
		nrcFormat.add("7/Ya Ta Ya (N)");
		nrcFormat.add("7/Ga Pa Ka(N)");
		nrcFormat.add("7/La Pa Da (N)");
		nrcFormat.add("7/Ma La Na (N)");
		nrcFormat.add("7/Ma Na Na (N)");
		nrcFormat.add("7/ Na Ta Lin(N)");
		nrcFormat.add("7/Ah Pha Na (N)");
		nrcFormat.add("7/Pa Ta Na(N)");
		nrcFormat.add("7/Pa Kha Ta (N)");
		nrcFormat.add("7/Pa Ta Ta (N)");
		nrcFormat.add("7/Pa Ma Na (N)");
		nrcFormat.add("7/Ya Ta Na(N)");
		nrcFormat.add("7/Tha Ya Ta (N)");
		nrcFormat.add("7/Tha Ka Na(N)");
		nrcFormat.add("7/Za Ka Na(N)");
		nrcFormat.add("8/Ah La Na(N)");
		nrcFormat.add("8/Kha Ma Na (N)");
		nrcFormat.add("8/ Ga Ka Na (N)");
		nrcFormat.add("8/Ka Ma Na (N)");
		nrcFormat.add("8/Ma Ga Na (N)");
		nrcFormat.add("8 /Ma Ba Na (N)");
		nrcFormat.add("8/Ma Ta Na (N)");
		nrcFormat.add("8/Ma La Na (N)");
		nrcFormat.add("8/Ma Ma Na(N)");
		nrcFormat.add("8/Ma Tha Na(N)");
		nrcFormat.add("8/Na Ma Na(N)");
		nrcFormat.add("8/Nge Pha Na (N)");
		nrcFormat.add("8/Pa Kha Ka (N)");
		nrcFormat.add("8/Pa Ma Na (N)");
		nrcFormat.add("8/Pa Pha Na(N)");
		nrcFormat.add("8/Sa La Na (N)");
		nrcFormat.add("8/Sa Ma Na(N)");
		nrcFormat.add("8/Sa Pha Na(N)");
		nrcFormat.add("8/Sa Ta Ta (N)");
		nrcFormat.add("8/Sa Pa Wah (N)");
		nrcFormat.add("8/Ta Ta Ka(N)");
		nrcFormat.add("8/Tha Ya Na (N)");
		nrcFormat.add("8/Ya Na Kha(N)");
		nrcFormat.add("8/Ya Sa Ga (N)");
		nrcFormat.add("8/Hta La Na(N) ");
		nrcFormat.add("5/Ah Ya Ta(N)");
		nrcFormat.add("5/Ba Ma Na (N)");
		nrcFormat.add("5/ba Ta la (N)");
		nrcFormat.add("5/kha Ah Na(N)");
		nrcFormat.add("5/Kha Ta Na (N)");
		nrcFormat.add("5/ba ka la (N)");
		nrcFormat.add("5/Ah Ta Na(N)");
		nrcFormat.add("5/Ka la Na(N)");
		nrcFormat.add("5/Ka La Wah (N)");
		nrcFormat.add("5/Ka ba La (N)");
		nrcFormat.add("5/Ka Na Na(N)");
		nrcFormat.add("5/Ka Tha Na(N)");
		nrcFormat.add("5/Ka La Na (N)");
		nrcFormat.add("5/Kha U Na (N)");
		nrcFormat.add("5/Ka La Na(N)");
		nrcFormat.add("5/La Ha Na(N)");
		nrcFormat.add("5/La Ya Na(N)");
		nrcFormat.add("5/Ma La Na(N)");
		nrcFormat.add("5/Ma Ma Na (N)");
		nrcFormat.add("5/Ma Ma Na(N)");
		nrcFormat.add("5/(N)");
		nrcFormat.add("5/Pa La Na(N)");
		nrcFormat.add("5/ (N)");
		nrcFormat.add("5/Pa La Ba(N)");
		nrcFormat.add("5/Sa Ka Na (N)");
		nrcFormat.add("5/Sa La Ka (N)");
		nrcFormat.add("5/Ya Ba Na (N)");
		nrcFormat.add("5/Ta Ma Na (N)");
		nrcFormat.add("5/Ta Za Na (N)");
		nrcFormat.add("5/Wah La Na (N)");
		nrcFormat.add("5/Wah Tha Na (N)");
		nrcFormat.add("5/Ya U Na (N)");
		nrcFormat.add("5/Ya Ma Pa (N)");
		nrcFormat.add("6/Ba Ah Na(N)");
		nrcFormat.add("6/Hta Wh Na (N)");
		nrcFormat.add("6/Ka Tha Na (N)");
		nrcFormat.add("6/Ka Sa Na (N)");
		nrcFormat.add("6/La La Na(N)");
		nrcFormat.add("6/Ma Ah Ya (N)");
		nrcFormat.add("6/Pa La Na(N)");
		nrcFormat.add("6/Ta Na Ya (N)");
		nrcFormat.add("6/Tha Ya Kha (N)");
		nrcFormat.add("6/Ya Pha Na (N)");
		nrcFormat.add("6/Ah Ma Ya (N)");
		nrcFormat.add("6/Ah Ma Tha (N)");
		nrcFormat.add("6/Kha Ah Tha (N)");
		nrcFormat.add("6/Kha Ma Tha(N)");
		nrcFormat.add("6/Ka Pa Ta (N)");
		nrcFormat.add("6/Ka Sa Na (N)");
		nrcFormat.add("6/(N)La Wah Na (N)");
		nrcFormat.add("6/Ma Ta Ya (N)");
		nrcFormat.add("6/Ma Ha Ah (N)");
		nrcFormat.add("6/Ma Hla Na (N)");
		nrcFormat.add("6/Ma Hta La (N)");
		nrcFormat.add("6/Ma Ka Na(N)");
		nrcFormat.add("6/ Ma Ka Na(N)");
		nrcFormat.add("6/Ma Tha Na(N)");
		nrcFormat.add("6/Na Hta Ka(N)");
		nrcFormat.add("6/Ng Za Na(N)");
		nrcFormat.add("6/Nya Ah Na(N)");
		nrcFormat.add("6/Pa Tha Ka (N)");
		nrcFormat.add("6/Pa Ba Na (N)");
		nrcFormat.add("6/Pa Ka Ta (N)");
		nrcFormat.add("6/Pa Ma Na (N)");
		nrcFormat.add("6/Pa Ah La (N)");
		nrcFormat.add("6/Sa ka Na (N)");
		nrcFormat.add("6/ Sa Ta Ka(N)");
		nrcFormat.add("6/Ta Da Ah (N)");
		nrcFormat.add("6/Ta Ka Na (N)");
		nrcFormat.add("6/Ta tha Na (N)");
		nrcFormat.add("6/Tha Ba Ka (N)");
		nrcFormat.add("6/Tha Za Na(N)");
		nrcFormat.add("6/Wh Ta Na(N)");
		nrcFormat.add("6/Ya Ma Tha(N)");
		nrcFormat.add("12/Ah La Na(N)");
		nrcFormat.add("12/Ba Ha Na (N)");
		nrcFormat.add("12/Ba Ta Hta (N)");
		nrcFormat.add("12/Ka Ka Ka (N)");
		nrcFormat.add("12/Da Ga Na(N)");
		nrcFormat.add("12/Da  Ga Ya (N)");
		nrcFormat.add("12/Da Ga  Ma(N)");
		nrcFormat.add("12/Sa Ka Na (N)");
		nrcFormat.add("12/ Da Ga Ta (N)");
		nrcFormat.add("12/Da La Na (N)");
		nrcFormat.add("12/Da Pa Na (N)");
		nrcFormat.add("12/La Ma Na(N)");
		nrcFormat.add("12/La Tha Ya(N)");
		nrcFormat.add("12/La Ka Na(N)");
		nrcFormat.add("12/ Ma Ba Na (N)");
		nrcFormat.add("12/Hta Ta Pa(N)");
		nrcFormat.add("12/Ah Sa Na (N)");
		nrcFormat.add("12/Ka Ma Ya (N)");
		nrcFormat.add("12/Ka Ma Na(N)");
		nrcFormat.add("12/Kha  Ya Ka(N)");
		nrcFormat.add("12/Kha Ya Ka (N)");
		nrcFormat.add("12/Ka Ta Ta(N)");
		nrcFormat.add("12/ Ka Ta Na(N)");
		nrcFormat.add("12/Ka Ma Ta(N)");
		nrcFormat.add("12/La Ma Ta (N)");
		nrcFormat.add("12/La Tha Na (N)");
		nrcFormat.add("12/Ma Ya Ka(N)");
		nrcFormat.add("12/Ma Ga Ta (N)");
		nrcFormat.add("12/U Ka Ma (N)");
		nrcFormat.add("12/Pa Ba Ta(N)");
		nrcFormat.add("12/Pa Za Ta (N)");
		nrcFormat.add("12/Sa Kha Na (N)");
		nrcFormat.add("12/Sa Ka Kha(N)");
		nrcFormat.add("12/Sa Kha Na (N)");
		nrcFormat.add("12/Ya Pa Tha(N)");
		nrcFormat.add("12/Ta Ka Na(N)");
		nrcFormat.add("12/Ta Ma Na (N)");
		nrcFormat.add("12/Tha Ka Ta(N)");
		nrcFormat.add("12/Tha La Na(N)");
		nrcFormat.add("12/Tha Ka Ka (N)");
		nrcFormat.add("12/Tha Ga Na (N)");
		nrcFormat.add("12/Ta Ta Na(N)");
		nrcFormat.add("12/Ya Ka Na(N)");
		nrcFormat.add("1/Ba Ma Na (N)");
		nrcFormat.add("1/(N)Kha Pha Na (N)");
		nrcFormat.add("1/Pha Ka Na(N)");
		nrcFormat.add("1/Ah La Na(N)");
		nrcFormat.add("1/(N)Kha La Pha (N)");
		nrcFormat.add("1/Ma Kha Ba(N)");
		nrcFormat.add("1/Ma Sa Na(N)");
		nrcFormat.add("1/Ma Ga Na(N) ");
		nrcFormat.add("1/Ma Nya Na(N)");
		nrcFormat.add("1/Ma Ma Na(N)");
		nrcFormat.add("1/Ma Ka Na(N)");
		nrcFormat.add("1/(N) Na Ma Na (N)");
		nrcFormat.add("1/Pa TA Ah(N)");
		nrcFormat.add("1/Ya Ga Na(N)");
		nrcFormat.add("1/Sa Pa Ya (N)");
		nrcFormat.add("1/Ta Na Na(N)");
		nrcFormat.add("1/(N)Sa La Na (N)");
		nrcFormat.add("1/Wah Ma Na (N)");
		nrcFormat.add("2/Da Mo Sa(N)");
		nrcFormat.add("2/La Ka Na(N)");
		nrcFormat.add("2/Ma Sa Na(N)");
		nrcFormat.add("2/Ma Sa Na(N)");
		nrcFormat.add("2/Ya Ta Na(N)");
		nrcFormat.add("3/Hla Ba Na(N)");
		nrcFormat.add("3/Pha Ah Na(N)");
		nrcFormat.add("3/Pha Pa Na(N)");
		nrcFormat.add("3/Ka Ka Ya(N)");
		nrcFormat.add("3/Ka Ya Sa(N)");
		nrcFormat.add("3/Ma Wah Ta(N)");
		nrcFormat.add("3/Tha Ta Na(N)");
		nrcFormat.add("4/Pha Lan Na(N)");
		nrcFormat.add("4/Ha Kha Na(N)");
		nrcFormat.add("4/Kan Pa let(N)");
		nrcFormat.add("4/Ma Du Pi (N)");
		nrcFormat.add("4/Ma Ta Na(N)");
		nrcFormat.add("4/Pa La Wah (N)");
		nrcFormat.add("4/Ta Ta Na(N)");
		nrcFormat.add("10/Bi La Na(N)");
		nrcFormat.add("10/Kha Sa Na(N)");
		nrcFormat.add("10/Ka Ma Ya(N)");
		nrcFormat.add("10/Ka Hta Na(N)");
		nrcFormat.add("10/Ma La Ma(N)");
		nrcFormat.add("10/Ma Da Na(N)");
		nrcFormat.add("10/Pa Ma Na(N)");
		nrcFormat.add("10/Tha Pha Ya(N)");
		nrcFormat.add("10/Tha Hta Na(N)");
		nrcFormat.add("10/Ya Ma Na(N)");
		nrcFormat.add("10/Bi La Na(N)");
		nrcFormat.add("10/Kha Sa Na(N)");
		nrcFormat.add("10/Ka Ma Ya(N)");
		nrcFormat.add("10/Ka Hta Na(N)");
		nrcFormat.add("10/Ma La Ma(N)");
		nrcFormat.add("10/Ma Da Na(N)");
		nrcFormat.add("10/Pa Ma Na(N)");
		nrcFormat.add("10/Tha Pha Ya(N)");
		nrcFormat.add("10/Tha Hta Na(N)");
		nrcFormat.add("10/Ya Ma Na(N)");
		nrcFormat.add("11/Ah Ma Na(N)");
		nrcFormat.add("11/Ba Tha Ta(N)");
		nrcFormat.add("11/Ga Ma Na(N)");
		nrcFormat.add("11/Ka Pha Na(N)");
		nrcFormat.add("11/Ka Ta Na(N)");
		nrcFormat.add("11/Ma Ta Na(N)");
		nrcFormat.add("11/Ma Pa Na(N)");
		nrcFormat.add("11/Ma U Na(N)");
		nrcFormat.add("11/Ma Pa Na(N)");
		nrcFormat.add("11/Pa Ta Na(N)");
		nrcFormat.add("11/(N)");
		nrcFormat.add("11/Ya Tha Ta(N)");
		nrcFormat.add("11/Sa Ta Na(N)");
		nrcFormat.add("11/Tha Ta Na(N)");
		nrcFormat.add("11/Tha Ta Na(N)");
		nrcFormat.add("13/Ma Sa Na (N)");
		nrcFormat.add("13/Ma La Na (N)");
		nrcFormat.add("13/Ma Pa Na (N)");
		nrcFormat.add("13/Ma Ta Na (N)");
		nrcFormat.add("13/Ma Ya Na (N)");
		nrcFormat.add("13/Ta Kha La (N)");
		nrcFormat.add("13/Ha Pha Na (N)");
		nrcFormat.add("13/Ha Na Na(N)");
		nrcFormat.add("13/Ka Kha (N)");
		nrcFormat.add("13/Ka Kha  (N)");
		nrcFormat.add("13/La Ya Na (N)");
		nrcFormat.add("13/Ma Ba Na (N)");
		nrcFormat.add("13/Ma Sa Na (N)");
		nrcFormat.add("13/Na Sa Na (N)");
		nrcFormat.add("13/Na pha Na (N)");
		nrcFormat.add("13/Na Ma Ta (N)");
		nrcFormat.add("13/Ka La Na (N)");
		nrcFormat.add("13/La La Na(N)");
		nrcFormat.add("13/Ma Ya Na (N)");
		nrcFormat.add("13/Ma Ka Na (N)");
		nrcFormat.add("13/Ma Na Na (N)");
		nrcFormat.add("13/Ma Pa Na (N)");
		nrcFormat.add("13/Na Sa Na (N)");
		nrcFormat.add("13/Nya Ya Na (N)");
		nrcFormat.add("13/Pha Ka Na (N)");
		nrcFormat.add("13/Pa Ta Ya (N)");
		nrcFormat.add("13/Pa La Na (N)");
		nrcFormat.add("13/Ta Ka Ma (N)");
		
/*		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add("မွတ္ ခ်က္ အမ်ိဳးအစား  ေရြးရန္");
		remarkTypes.add("လမ္းၾကိဳ");
		remarkTypes.add("ေတာင္းရန္");
		remarkTypes.add("ခံု ေရႊ႕ ရန္");
		remarkTypes.add("Date Change ရန္");
		remarkTypes.add("စီးျဖတ္");
		remarkTypes.add("ေတာင္းေရာင္း");
		remarkTypes.add("ဆက္သြား");*/
		
/*		ArrayAdapter<String> remarkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, remarkTypes);
		sp_remark_type.setAdapter(remarkAdapter);
		sp_remark_type.setOnItemSelectedListener(remarkTypeSelectedListener);*/
		
		nrcListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nrcFormat);
		edt_nrc_no.setAdapter(nrcListAdapter);
		
		auto_txt_agent = (MaterialAutoCompleteTextView) findViewById(R.id.txt_agent);
		
	    agentList = new ArrayList<Agent>();
		
		layout_ticket_no_container = (LinearLayout) findViewById(R.id.ticket_no_container);
		layout_ticket_no_container.setVisibility(View.GONE);
		
		skDetector = new SKConnectionDetector(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			
			SharedPreferences pref2 = this.getApplicationContext().getSharedPreferences("User", Activity.MODE_PRIVATE);
			
			user_type = pref2.getString("user_type", null);
			
			//if (user_type.equals("operator")) {
				//getAgent();
				getExtraDestination();
			//} else {
				txt_agent.setVisibility(View.GONE);
				auto_txt_agent.setVisibility(View.GONE);
			//}
		}else{
			Toast.makeText(BusConfirmActivity.this, "No Network Connection", Toast.LENGTH_SHORT).show();
		}
		
		btn_confirm.setOnClickListener(clickListener);
		
		LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lps.setMargins(0, 10, 0, 0);
		
		if (Selected_seats != null) {
			selectedSeat = Selected_seats.split(",");
		}
		
		//Random random = new Random();
		
		if (selectedSeat != null) {
			for (int i = 0; i < selectedSeat.length; i++) {
				CustomTextView label = new CustomTextView(this);
				label.setText("လက္ မွတ္ နံပါတ္ "+(i+1)+" [ Seat No."+ selectedSeat[i] +" ]");
				label.setTextSize(18f);
			   // forgot_pswrd.setOnTouchListener(this);
				//LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			    //llp.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
			   // label.setLayoutParams(lps);
			    
				layout_ticket_no_container.addView(label,lps);
				
				LinearLayout layout_checkbox = new LinearLayout(this);
				layout_checkbox.setVisibility(View.GONE);
				
/*				CheckBox chk_free = new CheckBox(this);
				chk_free.setText("Free Ticket");
				lst_free_chk.add(chk_free);
				//chk_free.setId(i+1 * 100);
				//chk_free.setTag(i+1 * 150);
				chk_free.setTag(i);
				chk_free.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
							
						Integer position = (Integer) buttonView.getTag();
						if (isChecked)
							lst_layout_free_ticket.get(position).setVisibility(
									View.VISIBLE);
						else
							lst_layout_free_ticket.get(position).setVisibility(
									View.GONE);
					}
				});
				layout_checkbox.addView(chk_free);
				//layout_ticket_no_container.addView(chk_free,lps);
				
				CheckBox chk_discount = new CheckBox(this);
				chk_discount.setText("Discount   ");
				// chk_discount.setId(i+1 * 300); // For discount check box of
				// discount.
				lst_discount_chk.add(chk_discount);
				// chk_discount.setTag(i+1 * 400);// For edit text of discount
				// amount.
				chk_discount.setTag(i);
				chk_discount
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							public void onCheckedChanged(CompoundButton buttonView,
									boolean isChecked) {
								// TODO Auto-generated method stub
								Integer position = (Integer) buttonView.getTag();
								if (isChecked)
									lst_discount_edt.get(position).setVisibility(
											View.VISIBLE);
								else
									lst_discount_edt.get(position).setVisibility(
											View.GONE);
							}
						});
				layout_checkbox.addView(chk_discount);*/
				
				layout_ticket_no_container.addView(layout_checkbox, lps);
				
				LinearLayout layout_free_ticket = new LinearLayout(this);
				//layout_free_ticket.setId(i+1 * 150);
				lst_layout_free_ticket.add(layout_free_ticket);
				layout_free_ticket.setVisibility(View.GONE);
				
/*				RadioButton rdo_free_pro = new RadioButton(this);
				rdo_free_pro.setText("Promotion");
				rdo_free_pro.setId(random.nextInt(100) + 1);
				lst_rdo_free_pro.add(rdo_free_pro);
				rdo_free_pro.setChecked(true);
				RadioButton rdo_free_mnt = new RadioButton(this);
				rdo_free_mnt.setText("Management");
				rdo_free_mnt.setId(random.nextInt(100) + 2);
				lst_rdo_free_mnt.add(rdo_free_mnt);
				RadioButton rdo_free_10plus = new RadioButton(this);
				rdo_free_10plus.setText("10+");
				rdo_free_10plus.setId(random.nextInt(100) + 3);
				lst_rdo_free_10plus.add(rdo_free_10plus);
				RadioButton rdo_free_pilgrim = new RadioButton(this);
				rdo_free_pilgrim.setText("Pilgrim");
				rdo_free_pilgrim.setId(random.nextInt(100) + 4);
				lst_rdo_free_pilgrim.add(rdo_free_pilgrim);
				RadioButton rdo_free_spr = new RadioButton(this);
				rdo_free_spr.setText("Sponsor");
				rdo_free_spr.setId(random.nextInt(100) + 5);
				lst_rdo_free_spr.add(rdo_free_spr);
				RadioGroup rdo_gp_free = new RadioGroup(this);
				lst_rdo_gp_free.add(rdo_gp_free);
				rdo_gp_free.setOrientation(RadioGroup.HORIZONTAL);
				rdo_gp_free.addView(rdo_free_pro);
				rdo_gp_free.addView(rdo_free_mnt);
				rdo_gp_free.addView(rdo_free_10plus);
				rdo_gp_free.addView(rdo_free_pilgrim);
				rdo_gp_free.addView(rdo_free_spr);
				layout_free_ticket.addView(rdo_gp_free);
				layout_ticket_no_container.addView(layout_free_ticket);*/

				/*MaterialEditText edt_discount = new MaterialEditText(this);
				edt_discount.setHint("Enter the discount amount.");
				edt_discount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
				// edt_discount.setId(i+1 * 400);
				lst_discount_edt.add(edt_discount);
				edt_discount.setVisibility(View.GONE);
				layout_ticket_no_container.addView(edt_discount);*/

				MaterialEditText ticket_no = new MaterialEditText(this);
				ticket_no.setInputType(InputType.TYPE_CLASS_NUMBER);
				// ticket_no.setId(i+1);
				lst_ticket_no.add(ticket_no);
				ticket_no.setSingleLine(true);
				ticket_no.setBackgroundResource(R.drawable.apptheme_edit_text_holo_light);
				layout_ticket_no_container.addView(ticket_no, lps);
			}
		}

		if (selectedSeat != null) {
			if(selectedSeat.length > 1){
				lst_ticket_no.get(0).addTextChangedListener(new TextWatcher() {

					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub
						for (int i = 1; i < selectedSeat.length; i++) {
							lst_ticket_no.get(i).setText(s.toString());
						}
					}

					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub

					}
				});
			}
		}
		
		extraCity = new ArrayList<ExtraCity>();
		extraCity.add(new ExtraCity("0", "0", "0", "0", "0", "Select City", "", ""));
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
/*	private OnItemSelectedListener remarkTypeSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (arg2 == 0) {
				layout_remark.setVisibility(View.GONE);
			} else {
				layout_remark.setVisibility(View.VISIBLE);
			}
			selectedRemarkType = arg2;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};*/
	
	String param;
	public static String ExtraCityName;
	public static String ExtraCityPrice = "0";
	
	/**
	 *  Get Extra Destination Cities and Prices
	 */
	private void getExtraDestination(){
		
		dialog = new ZProgressHUD(BusConfirmActivity.this);
		dialog.show();
		
		param = MCrypt.getInstance().encrypt(SecureParam.getExtraDestinationParam(permit_access_token));
		
		NetworkEngine.setIP(permit_ip);
		NetworkEngine.getInstance().getExtraDestination(param, MCrypt.getInstance().encrypt(BusOccurence),new Callback<Response>() {

					public void success(Response arg0, Response arg1) {
						// TODO Auto-generated method stub
						Log.i("", "Extra Dest(token-param): "+param+", Busoccurance: "+MCrypt.getInstance().encrypt(BusOccurence)+", permit_ip: "+permit_ip);
						
						if (arg0 != null) {
							
							List<ExtraCity> cities = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<ExtraCity>>() {}.getType());
							
							if (cities != null && cities.size() > 0) {
								Log.i("", "extra cities: "+cities.toString());
								
								extra_city_container.setVisibility(View.VISIBLE);
								extraCity.addAll(cities);
								sp_extra_city.setAdapter(new ExtraCityAdapter(
										BusConfirmActivity.this, extraCity));
								sp_extra_city
										.setOnItemSelectedListener(itemSelectedListener);
							}else{
								Log.i("", "extra cities are null!");
								extra_city_container.setVisibility(View.GONE);
							}
						}
						
						dialog.dismissWithSuccess();
					}

					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "Extra city error: "+arg0.getResponse().getStatus());
						}
						
						dialog.dismissWithFailure();
					}
				});
	}
	
	/**
	 *  If Extra City chosen, get Extra city's info
	 */
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
				if (Integer.valueOf(ExtraCityPrice) > 0) 
					ExtraCityPrice = "0";
				
				if (arg2 > 0) {
					ExtraCityID = extraCity.get(arg2).getId();
					ExtraCityName = extraCity.get(arg2).getCity_name();
					ExtraCityPrice  = extraCity.get(arg2).getLocal_price();
					sp_remark_type.setSelection(7);				
					edt_remark.setText(extraCity.get(arg2).getCity_name());
				}else {
					ExtraCityID = "0";
					ExtraCityName = "";
					ExtraCityPrice = "0";
				}
				
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	protected String ticket_price = "0";
	protected String total_amount;
	public static String CustName;
	public static String CustPhone;
	
	/**
	 * @return If customer name, phone no is null, return false. 
	 * If phone no is less than 6 characters and not starts with 09 or 01, return false. 
	 */
	private boolean checkFieldsAgent()
    {
    	if(edt_buyer.getText().toString().length() == 0){
    		edt_buyer.setError("Enter Buyer Name");
    		return false;
    	}
    	
    	if(edt_phone.getText().toString().length() == 0)
    	{
    		edt_phone.setError("Enter Phone Number");
			return false;
		}
    	
    	if(edt_phone.getText().toString().length() < 6)
    	{
    		edt_phone.setError("Enter at least '6' numbers");
			return false;
		}
    	
    	if (!edt_phone.getText().toString().startsWith("09") && !edt_phone.getText().toString().startsWith("01")) {
    		edt_phone.setError("Enter 09####### (or) 01######");
    		return false;
		}
    	
    	return true;
   }
	
/*	public static Set<String> findDuplicates(List<String> listTicket) {
	 
	final Set<String> duplicate = new HashSet<String>();
	final Set<String> set1 = new HashSet<String>();

	for (String ticketNo : listTicket) {
		if (!set1.add(ticketNo)) {
			duplicate.add(ticketNo);
		}
	}
	return duplicate;
}*/
	
/*	public boolean checkFieldsOperator()
    {
    	if(edt_buyer.getText().toString().length() == 0){
    		edt_buyer.setError("Enter Buyer Name");
    		return false;
    	}
    	
    	if(edt_phone.getText().toString().length() == 0)
    	{
    		edt_phone.setError("Enter Phone Number");
			return false;
		}

    	if(edt_phone.getText().toString().length() < 5)
    	{
    		edt_phone.setError("Check Your Phone No");
			return false;
		}
    	
    	if(auto_txt_agent.getText().toString().length() == 0 && Integer.valueOf(AgentID) == 0 ){
    		auto_txt_agent.setError("Enter Agent Name");
    		return false;
    	}
    	
    	return true;
   }*/
	
	private String fromPayment;
	
	/**
	 *  {@code btn_confirm} clicked: {@link #postSale(String)}
	 */
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				onBackPressed();
			}

			if(v == actionBarNoti){
				SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", getToday());
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			}

			if (v == btn_confirm) {
				
				if (checkFieldsAgent()) {
					
					if (skDetector.isConnectingToInternet()) {
						
						CustName = edt_buyer.getText().toString();
						CustPhone = edt_phone.getText().toString();
						
						//Take selected seats into Database
						if (Intents.equals("SaleTicket")) {
							postSale(date);
						}else {
							postSale(return_date);
						}
						
						
					}else {
						skDetector.showErrorMessage();
					}
				}
			}
		}
	};
	
	public static String sale_order_no;
	public static String SeatLists = "";
	public static String TicketLists = "";
	
	/**
	 * Save Selected Seats in Operators Database. 
	 * If one way, go next activity {@link PaymentTypeActivity}. 
	 * If round trip, if return trip not choose yet, go next activity {@link BusOperatorSeatsActivity}, 
	 * if return trip choose finish, go next activity {@link PaymentTypeActivity}.
	 * @param date Date (departure date) or (return date)
	 */
	private void postSale(final String date)
	{
		dialog = new ZProgressHUD(BusConfirmActivity.this);
		dialog.show();

		//Do Encrypt of Params
		String param = MCrypt.getInstance().encrypt(SecureParam.postSaleParam(permit_access_token
					, permit_operator_id, Permit_agent_id, CustName, CustPhone, "0"
					, "", permit_operator_group_id, MCrypt.getInstance()
					.encrypt(seat_List.getSeatsList().toString()), BusOccurence, date, FromCity, ToCity, String.valueOf(AppLoginUser
					.getId()), DeviceUtil.getInstance(this).getID(), "1",
					String.valueOf(AppLoginUser.getId()),"true",ExtraCityID));
		
		
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", param));       
        
		final Handler handler = new Handler() {

			private JSONObject jsonObject;

			public void handleMessage(Message msg) {
				
				String jsonData = msg.getData().getString("data");
				
				Log.i("ans:","Server Response: "+jsonData);
				
				try {
					
					if (jsonData != null) {
						jsonObject = new JSONObject(jsonData);
					}
					
					if (jsonObject != null) {
						if(jsonObject.getString("status").equals("1")){
							
							if(jsonObject.getBoolean("can_buy") && jsonObject.getString("device_id").equals(DeviceUtil.getInstance(BusConfirmActivity.this).getID())){
			        			
								sale_order_no = jsonObject.getString("sale_order_no");
								Log.i("", "Bus confirm(orderno): "+sale_order_no);
								
								//Get Seats No. including (,)
		        				JSONArray jsonArray = jsonObject.getJSONArray("tickets");	        					        			
		        				
		        				/*for(int i=0; i<jsonArray.length(); i++){
		        					JSONObject obj = jsonArray.getJSONObject(i);
		        					if (i == jsonArray.length() - 1) {
		        						SeatLists += obj.getString("seat_no");
									}else {
										SeatLists += obj.getString("seat_no")+",";
									}
		        				}
		        				
		        				Log.i("", "Seat List(bus confirm): "+SeatLists);*/
		        				
		        				if (TicketLists != null) {
		        					TicketLists = "";
								}
		        				
		        				for(int i=0; i<jsonArray.length(); i++){
		        					JSONObject obj = jsonArray.getJSONObject(i);
		        					if (obj.has("ticket_no")) {
										if (i == jsonArray.length() - 1) {
			        						TicketLists += obj.getString("ticket_no");
										}else {
											TicketLists += obj.getString("ticket_no")+",";
										}
		        						
									}else {
										if (i == jsonArray.length() - 1) {
			        						TicketLists += "-";
										}else {
											TicketLists += "-,";
										}
									}
		        				}
		        				
		        				
		        				Log.i("", "Ticket No(bus confirm): "+TicketLists);
								if(isBooking == 0){
									if (trip_type == 1) {
										//If one way 
										Bundle bundle = new Bundle();
										bundle.putInt("trip_type", trip_type);
										bundle.putString("from_intent", Intents);
										startActivity(new Intent(BusConfirmActivity.this, PaymentTypeActivity.class).putExtras(bundle));
									}else if (trip_type == 2){	
										//If Round Trip
										//For Return Trip, Choose (Operator, Time, Class) again 
										if (Intents.equals("SaleTicket")) {
											Bundle bundle = new Bundle();
											bundle.putString("from_intent", "BusConfirm");
											bundle.putInt("trip_type", trip_type);
											bundle.putString("return_date", return_date);
											bundle.putString("FromName", FromName);
											bundle.putString("ToName", ToName);
											bundle.putString("GoTripInfo", new Gson().toJson(new GoTripInfo(sale_order_no, Price, String.valueOf(seat_count)
													, AppLoginUser.getAgentGroupId(), permit_operator_id, selectedSeatNos, TicketLists
													, BusOccurence, permit_access_token, Permit_agent_id, permit_ip, CustName, CustPhone
													, "", FromCity, ToCity, Operator_Name, from_to, time, classes, date, ConfirmDate
													, ConfirmTime, ExtraCityID, ExtraCityName, ExtraCityPrice, return_date, ""
													, TicketLists, permit_operator_id)));
											
											//Not Allow to choose for Go Trip again
											closeAllActivities();
											
											startActivity(new Intent(BusConfirmActivity.this, BusOperatorSeatsActivity.class).putExtras(bundle));
										}else if (Intents.equals("BusConfirm")){
											//After Return Info Choose, Go to Pay
											Bundle bundle = new Bundle();
											bundle.putString("GoTripInfo", new Gson().toJson(goTripInfo_obj));
											
											Log.i("", "permit ip(bus confirm): "+goTripInfo_obj.getPermit_ip());
											
											bundle.putInt("trip_type", trip_type);
											bundle.putString("from_intent", Intents);
											startActivity(new Intent(BusConfirmActivity.this, PaymentTypeActivity.class).putExtras(bundle));
										}
									}
			        			}else{ 
			        				isBooking = 0;
			        			}
			        		}else{
			        			isBooking = 0;
			        			dialog.dismissWithFailure();
			        			SKToastMessage.showMessage(BusConfirmActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
			        			//If Round Trip, Departure Choose Time Again 
			        			if (trip_type == 1) {
									//If one way
			        				closeAllActivities();
			        				startActivity(new Intent(BusConfirmActivity.this, SaleTicketActivity.class));
								}
			        		}
						}else{
							Log.i("", "Khone Kar unfinished(status '0') ...........");
							isBooking = 0;
							dialog.dismissWithFailure();
							SKToastMessage.showMessage(BusConfirmActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားပါသည္။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
							
							if (trip_type == 1) {
								//If one way
		        				closeAllActivities();
		        				startActivity(new Intent(BusConfirmActivity.this, SaleTicketActivity.class));
							}
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		HttpConnection lt = new HttpConnection(handler,"POST", "http://"+ permit_ip +"/sale", params);
		lt.execute();
		
		Log.i("", "Post Sale: "+"http://"+ permit_ip +"/sale"+" , Params: "+params.toString());
	}
}
