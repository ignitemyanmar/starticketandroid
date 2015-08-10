package com.ignite.mm.ticketing.user;

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
import com.ignite.mm.ticketing.user.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;
import com.thuongnh.zprogresshud.ZProgressHUD;

@SuppressLint("SimpleDateFormat") public class BusConfirmActivity extends BaseActivity {

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
	private String BusOccurence = "0";
	private String Intents;
	private LinearLayout extra_city_container;
	private Spinner sp_extra_city;
	private List<ExtraCity> extraCity;
	protected String ExtraCityID = "0";
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
	private String Permit_agent_id;
	private String permit_operator_group_id;
	private String permit_operator_id;
	private String permit_access_token;
	private String permit_ip;
	private String Nrc = "";
	private Spinner spn_expire_month;
	private Spinner spn_expire_year;
	private RadioButton radio_onilnePayment;
	private RadioButton radio_cashOnShop;
	private RadioButton radio_cashOnDelivery;
	private String FromCity;
	private String ToCity;
	private String Operator_Name;
	private String from_to;
	private String time;
	private String classes;
	private String date;
	private String Price;
	private String ConfirmDate;
	private String ConfirmTime;
	private BundleListObjSeats seat_List;
	
	private Integer isBooking = 0;
	private String Selected_seats;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nrc_activity);
		
		btn_confirm = (FButton) findViewById(R.id.btn_confirm);
		btn_confirm.setButtonColor(getResources().getColor(R.color.yellow));
		btn_confirm.setShadowEnabled(true);
		btn_confirm.setShadowHeight(3);
		btn_confirm.setCornerRadius(7);
		
		radio_onilnePayment = (RadioButton)findViewById(R.id.radio_onilnePayment);
		radio_cashOnShop = (RadioButton)findViewById(R.id.radio_cashOnShop);
		radio_cashOnDelivery = (RadioButton)findViewById(R.id.radio_cashOnDelivery);
		
		connectionDetector = new ConnectionDetector(this);
		
		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.GONE);
			actionBarNoti.setText(NotifyBooking.toString());
		}
		
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
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitle(bundle.getString("from_to")+" ["+bundle.getString("Operator_Name")+"]");
            toolbar.setSubtitle(bundle.getString("date")+" ["+bundle.getString("time")+"] "
    				+bundle.getString("classes"));
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
			Price  = bundle.getString("Price");
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
		}
		
		//Get only 06:00 AM format
		String timeformat = null;
		try {
			if (time.length() == 8) {
				timeformat = time;
			}else if (time.length() < 8) {
				timeformat = "0"+time;
			}else if (time.length() > 8) {
				timeformat = time.substring(0, 8);
			}
		} catch (StringIndexOutOfBoundsException e) {
			// TODO: handle exception
			Log.i("", "Time Out Of Bound Exception: "+e);
		}
		
		SimpleDateFormat serverFormat = new SimpleDateFormat("hh:mm aa");
		Date timeTochange = null;
		try {
			if (timeformat != null) {
				timeTochange = serverFormat.parse(timeformat);
				Log.i("", "Server Time Format: "+serverFormat.format(timeTochange));
			}
			
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			Log.i("", "Server Time Exception: "+e2);
			e2.printStackTrace();
		}
		
		//Show/Hide of Cash on Delivery & Cash on Shop
		String deptTime = date+" "+serverFormat.format(timeTochange);
		
		//Today+24hr
		SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
		Date deptDateTime = null;
		try {
			deptDateTime = formatter.parse(deptTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("", "Dept date time: "+deptDateTime+", today+24hr: "+nowFormat.format(cal.getTime()));
		
		if (cal.getTime().compareTo(deptDateTime) >= 0) {
			radio_cashOnDelivery.setEnabled(false);
			radio_cashOnShop.setEnabled(false);
		}else {
			radio_cashOnDelivery.setEnabled(true);
			radio_cashOnShop.setEnabled(true);
		}
		
		Log.i("", "Permit_agent_id : "+Permit_agent_id);
		
		edt_buyer = (MaterialEditText) findViewById(R.id.edt_buyer);	
		if (Name != null) {
			edt_buyer.setText(Name);
		}
		edt_nrc_no = (MaterialAutoCompleteTextView) findViewById(R.id.edt_nrc_no);
		if (Nrc != null) {
			edt_nrc_no.setText(Nrc);
		}
		edt_phone = (MaterialEditText) findViewById(R.id.edt_phone);
		if (Phone != null) {
			edt_phone.setText(Phone);
		}
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
		
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add("မွတ္ ခ်က္ အမ်ိဳးအစား  ေရြးရန္");
		remarkTypes.add("လမ္းၾကိဳ");
		remarkTypes.add("ေတာင္းရန္");
		remarkTypes.add("ခံု ေရႊ႕ ရန္");
		remarkTypes.add("Date Change ရန္");
		remarkTypes.add("စီးျဖတ္");
		remarkTypes.add("ေတာင္းေရာင္း");
		remarkTypes.add("ဆက္သြား");
		
		ArrayAdapter<String> remarkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, remarkTypes);
		sp_remark_type.setAdapter(remarkAdapter);
		sp_remark_type.setOnItemSelectedListener(remarkTypeSelectedListener);
		
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
			skDetector.showErrorMessage();
		}
		
		btn_confirm.setOnClickListener(clickListener);
		
		LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lps.setMargins(0, 10, 0, 0);
		
		if (Selected_seats != null) {
			selectedSeat = Selected_seats.split(",");
		}
		
		Random random = new Random();
		
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
				
				CheckBox chk_free = new CheckBox(this);
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
				layout_checkbox.addView(chk_discount);
				layout_ticket_no_container.addView(layout_checkbox, lps);
				
				
				LinearLayout layout_free_ticket = new LinearLayout(this);
				//layout_free_ticket.setId(i+1 * 150);
				lst_layout_free_ticket.add(layout_free_ticket);
				layout_free_ticket.setVisibility(View.GONE);
				
				RadioButton rdo_free_pro = new RadioButton(this);
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
				layout_ticket_no_container.addView(layout_free_ticket);

				MaterialEditText edt_discount = new MaterialEditText(this);
				edt_discount.setHint("Enter the discount amount.");
				edt_discount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
				// edt_discount.setId(i+1 * 400);
				lst_discount_edt.add(edt_discount);
				edt_discount.setVisibility(View.GONE);
				layout_ticket_no_container.addView(edt_discount);

				MaterialEditText ticket_no = new MaterialEditText(this);
				ticket_no.setInputType(InputType.TYPE_CLASS_NUMBER);
				// ticket_no.setId(i+1);
				lst_ticket_no.add(ticket_no);
				ticket_no.setSingleLine(true);
				ticket_no.setBackgroundResource(R.drawable.apptheme_edit_text_holo_light);
				layout_ticket_no_container.addView(ticket_no, lps);
			}
		}

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
		
		extraCity = new ArrayList<ExtraCity>();
		extraCity.add(new ExtraCity("0", "0", "0", "0", "0", "Select City", "", ""));
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
	
	private OnItemSelectedListener remarkTypeSelectedListener = new OnItemSelectedListener() {

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
	};
	String param;
	protected String ExtraCityName;
	protected String ExtraCityPrice = "0";
	
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
	
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
				
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
	protected String CustName;
	protected String CustPhone;

	private void comfirmOrder() {
		/*dialog = new ZProgressHUD(BusConfirmActivity.this);
		dialog.show();*/
		
		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
		
		for (int i = 0; i < selectedSeat.length; i++) {
			//MaterialEditText ticket_no = (MaterialEditText)findViewById(i+1);
			//CheckBox free_ticket = (CheckBox) findViewById(i+1 * 100);
			String free_ticket_remark = null;
			if (lst_free_chk.get(i).isChecked()) {
				RadioGroup rdo_gp_free = lst_rdo_gp_free.get(i);
				int rdoId = rdo_gp_free.getCheckedRadioButtonId();
				RadioButton rdo_free = (RadioButton) findViewById(rdoId);
				free_ticket_remark = rdo_free.getText().toString();
			}
			
			String discount = "0";
			if (lst_discount_chk.get(i).isChecked()) {
				MaterialEditText edt_discount = lst_discount_edt.get(i);
				if (edt_discount.getText().toString().length() == 0) {
					dialog.dismiss();
					edt_discount.setError("Please enter the discount amount.");
					return;
				}
				discount = edt_discount.getText().toString();
			}
			
			seats.add(new ConfirmSeat(BusOccurence, selectedSeat[i].toString(),
					edt_buyer.getText().toString(), edt_nrc_no.getText()
							.toString(), lst_ticket_no.get(i).getText()
							.toString(), lst_free_chk.get(i).isChecked(),
					free_ticket_remark, Integer.valueOf(discount)));
			
		}
		
		SharedPreferences pref_old_sale = this.getApplicationContext().getSharedPreferences("old_sale", Activity.MODE_PRIVATE);
		String working_date = pref_old_sale.getString("working_date", null);
		
		//if(user_type.equals("Agent")){
			AgentID = String.valueOf(AppLoginUser.getId());
		//}
		Log.i("", "Ticket Arrays: "+seats.toString());
		
		Log.i("", "Param (Confirm) to encrypt: "
				+"access: "+permit_access_token+
				", SaleOrderNo: "+0+
				", Reference no: "+edt_ref_invoice_no.getText().toString()+
				", AgentID: "+Permit_agent_id+
				", Agent Name: "+auto_txt_agent.getText().toString()+
				", Customer: "+edt_buyer.getText().toString()+
				", Phone: "+edt_phone.getText().toString()+
				", Remark : "+edt_remark.getText().toString()+
				", Nrc: "+edt_nrc_no.getText().toString()+
				", Extra city id: "+ExtraCityID.toString()+
				", Seats: "+seats.toString()+
				", Cash or not: "+rdo_cash_down.isChecked()+
				", Local or Foreign: "+rdo_local.isChecked()+
				", Order date: "+
				", Device id: "+DeviceUtil.getInstance(this).getID()+
				", isbooking: "+"0");
		
		//Do Encrypt of Params				
		String param = MCrypt.getInstance()
				.encrypt(
				SecureParam.postSaleConfirmParam(permit_access_token
				, "0", edt_ref_invoice_no.getText().toString()
				, Permit_agent_id, auto_txt_agent.getText().toString()
				, edt_buyer.getText().toString()
				, edt_phone.getText().toString(), edt_nrc_no.getText().toString()
				, "0", edt_remark.getText().toString()
				, ExtraCityID.toString(),  MCrypt.getInstance()
				.encrypt(seats.toString())
				, rdo_cash_down.isChecked() == true ? "1" : "2"
				, rdo_local.isChecked() == true ? "local" : "foreign"
				, "0", DeviceUtil.getInstance(this).getID(), "0",String.valueOf(AppLoginUser.getId())));
				
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("param", param));
		
		Log.i("","Hello param (for confirm) : "+ param);
		
		bundle.putString("BuyerName", edt_buyer.getText().toString());
		bundle.putString("BuyerPhone", edt_phone.getText().toString());
		bundle.putString("BuyerNRC", edt_nrc_no.getText().toString());
		
		String ticketNo = "";
		for (int j = 0; j < seats.size(); j++) {
			
/*			if (j == seats.size()-1) {
				ticketNo += seats.get(j).getTicket_no()+" (seat no. "+seats.get(j).getSeat_no()+")";
			}else {
				ticketNo += seats.get(j).getTicket_no()+" (seat no. "+seats.get(j).getSeat_no()+"), <br/>";
			}*/
			
			if (j == seats.size()-1) {
				ticketNo += seats.get(j).getTicket_no();
			}else {
				ticketNo += seats.get(j).getTicket_no()+",";
			}
			/*String styledText = "This is <font color='red'>simple</font>.";
			textView.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);*/
		}

		bundle.putString("TicketNo", ticketNo);
		bundle.putString("time", bundle.getString("time"));
		
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				String jsonData = msg.getData().getString("data");
				
				if (jsonData != null) {
					try {
						Log.i("","Hello Response Confirm Data:"+ jsonData);
						
						JSONObject jsonObj = new JSONObject(jsonData);
						JSONObject orderObj = jsonObj.getJSONObject("order");
						total_amount = orderObj.getString("total_amount");
						
						if(!jsonObj.getBoolean("status") && jsonObj.getString("device_id").equals(DeviceUtil.getInstance(BusConfirmActivity.this).getID())){
							SKToastMessage.showMessage(BusConfirmActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားေသာေၾကာင့္ သင္ မွာ ေသာ လက္ မွတ္ မ်ား မရ ႏုိင္ေတာ့ပါ။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
							dialog.dismissWithFailure();
						}else{
							//Store Data into Online Sale Database
							JSONArray arr = orderObj.getJSONArray("saleitems");
							//for (int i = 0; i < arr.length(); i++) {
								JSONObject obj = arr.getJSONObject(0);
								ticket_price = obj.getString("price");								
							//}
							
							//Store Sale on City Mart DB
							postOnlineSaleConfirm();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Log.i("", "Response confirm is null!");
					dialog.dismissWithFailure();
				}
			}
		};
		
		//bundle.getString("permit_ip")
		HttpConnection lt = new HttpConnection(handler, "POST",
				"http://"+bundle.getString("permit_ip")+"/sale/comfirm", params);
		lt.execute();
		
		Log.i("", "Permit IP: "+bundle.getString("permit_ip"));
	}
	
	/**
	 *  Store data into Online Sale Database (starticketmyanmar.com)
	 */
	protected void postOnlineSaleConfirm() {
		// TODO Auto-generated method stub
		Log.i("", "SaleOrderNo: "+"0"+", Op-Id: "+permit_operator_id+", User code no: "+AppLoginUser.getCodeNo()
				+", Token: "+AppLoginUser.getAccessToken());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		//NetworkEngine.setIP("128.199.255.246");
		NetworkEngine.getInstance().postOnlineSaleDB("0", permit_operator_id, AppLoginUser.getCodeNo()
				, AppLoginUser.getAccessToken(), ExtraCityName, "0931247515"
				, "Saw Maine K", "No.50, Lanthit Street, Lanmadaw Tsp, Yangon", "Lanmadaw Tsp"
				, "10", "0", "", "", "", "", "", new Callback<Response>() {
			
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							
							Log.i("", "Error: "+arg0.getResponse().getStatus());

						}
						dialog.dismissWithFailure();
					}

					public void success(Response arg0, Response arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							
							SKToastMessage.showMessage(BusConfirmActivity.this, "လက္ မွတ္ ျဖတ္ ျပီးပါၿပီ !", SKToastMessage.SUCCESS);
							closeAllActivities();
							startActivity(new Intent(BusConfirmActivity.this, SaleTicketActivity.class).putExtras(bundle));
							//Show Voucher		
							if(Intents.equals("booking")){
								bundle.putString("from_intent", "booking");								
							}
							
							bundle.putString("extra_city", ExtraCityName);
							bundle.putString("ticket_price", ticket_price);
							bundle.putString("total_amount", total_amount);
							
							//startActivity(new Intent(BusConfirmActivity.this, PDFBusActivity.class).putExtras(bundle));
							dialog.dismissWithSuccess();
							finish();
							
							Log.i("", "Server Response1: "+arg0.getStatus()+", "+arg0.getReason()+", "+arg0.getBody());
							Log.i("", "Server Response: "+arg1.getStatus()+", "+arg1.getReason()+", "+arg1.getBody());
						}
					}
				});
	}

	public boolean checkFieldsAgent()
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
    		edt_phone.setError("Enter only start with '09 (or) 01'");
    		return false;
		}
    	
/*    	List<String> listTicketNo = new ArrayList<String>();
    	for (int i = 0; i < lst_ticket_no.size(); i++) {
    		listTicketNo.add(lst_ticket_no.get(i).getText().toString());
    		
    		if (lst_ticket_no.get(i).getText().toString().length() == 0) {
    			lst_ticket_no.get(i).setError("Enter Ticket No");
    			return false;
			}
		}*/
    	
    	//Set List doesn't accept duplicate values, & if (Not duplicate), return true
    	                                            // if (duplicate), return false
/*    	final Set<String> set1 = new HashSet<String>();

    	for (String ticketNo : listTicketNo) {
    		//If Duplicate Tickets are exists in List 
    		if (!set1.add(ticketNo)) {
    			SKToastMessage.showMessage(BusConfirmActivity.this, "လက္ မွတ္ နံပါတ္  "+ticketNo+" တူေနပါသည္ တျခား နံပါတ္ ေျပာင္းထည့္ပါ", SKToastMessage.ERROR);
    			return false;
    		}
    	}*/
    	
    	return true;
   }
	
	public static Set<String> findDuplicates(List<String> listTicket) {
	 
	final Set<String> duplicate = new HashSet<String>();
	final Set<String> set1 = new HashSet<String>();

	for (String ticketNo : listTicket) {
		if (!set1.add(ticketNo)) {
			duplicate.add(ticketNo);
		}
	}
	return duplicate;
}
	
	public boolean checkFieldsOperator()
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
   }
	
	private String fromPayment;
	
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
						
						Bundle bundle = new Bundle();
						bundle.putString("price", Price);
						bundle.putString("seat_count", String.valueOf(selectedSeat.length));
						bundle.putString("agentgroup_id", permit_operator_group_id);
						bundle.putString("operator_id", permit_operator_id);
						
						/*bundle.putString("selectedSeats", Selected_seats);
						bundle.putString("busOccurence", BusOccurence);
						bundle.putString("permit_access_token", permit_access_token);
						bundle.putString("Permit_agent_id", Permit_agent_id);
						bundle.putString("permit_ip", permit_ip);
						bundle.putString("BuyerName", edt_buyer.getText().toString());
						bundle.putString("BuyerPhone", edt_phone.getText().toString());
						bundle.putString("BuyerNRC", edt_nrc_no.getText().toString());
						
						bundle.putString("FromCity", FromCity);
						bundle.putString("ToCity", ToCity);
						bundle.putString("Operator_Name", Operator_Name);
						bundle.putString("from_to", from_to);
						bundle.putString("time", time);
						bundle.putString("classes", classes);
						bundle.putString("date", date);
						bundle.putString("ConfirmDate", ConfirmDate);
						bundle.putString("ConfirmTime", ConfirmTime);
						*/
						if (radio_onilnePayment.isChecked()) {
							fromPayment = "Pay with Online";
							postSale(fromPayment);
						}else if (radio_cashOnShop.isChecked()) {	//Booking (Pay @Store)
							isBooking = 1;
							fromPayment = "Cash on Shop";
							postSale(fromPayment);
						}else {				//Booking (Pay On Delivery)
							fromPayment = "Cash on Delivery";
							postSale(fromPayment);
						}
					}else {
						skDetector.showErrorMessage();
					}
				}
			}
		}
	};
	
	public void postSale(final String fromPayment)
	{
		dialog = new ZProgressHUD(BusConfirmActivity.this);
		dialog.show();

		//Do Encrypt of Params
		String param = MCrypt.getInstance().encrypt(SecureParam.postSaleParam(permit_access_token
					, permit_operator_id, Permit_agent_id, CustName, CustPhone, "0"
					, "", permit_operator_group_id, MCrypt.getInstance()
					.encrypt(seat_List.getSeatsList().toString()), BusOccurence, date, FromCity, ToCity, String.valueOf(AppLoginUser
					.getId()), DeviceUtil.getInstance(this).getID(), isBooking.toString(),
					String.valueOf(AppLoginUser.getId()),"true"));
		
		
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", param));       
        
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				
				String jsonData = msg.getData().getString("data");
				
				Log.i("ans:","Server Response: "+jsonData);
				
				try {
					
					JSONObject jsonObject = null;
					
					if (jsonData != null) {
						jsonObject = new JSONObject(jsonData);
					}
					String SeatLists = "";
					
					if (jsonObject != null) {
						if(jsonObject.getString("status").equals("1")){
							
							if(jsonObject.getBoolean("can_buy") && jsonObject.getString("device_id").equals(DeviceUtil.getInstance(BusConfirmActivity.this).getID())){
			        			
								//Get Seats No. including (,)
		        				JSONArray jsonArray = jsonObject.getJSONArray("tickets");	        					        			
		        				
		        				for(int i=0; i<jsonArray.length(); i++){
		        					JSONObject obj = jsonArray.getJSONObject(i);
		        					if (i == jsonArray.length() - 1) {
		        						SeatLists += obj.getString("seat_no");
									}else {
										SeatLists += obj.getString("seat_no")+",";
									}
		        				}
		        				
		        				//Buy Ticket
								if(isBooking == 0){
			        				Intent nextScreen = new Intent(BusConfirmActivity.this, PaymentActivity.class);
			        				
				    				Bundle bundle = new Bundle();
				    				bundle.putString("from_payment", fromPayment);
				    				bundle.putString("sale_order_no", jsonObject.getString("sale_order_no"));
									bundle.putString("price", Price);
									bundle.putString("seat_count", String.valueOf(selectedSeat.length));
									bundle.putString("agentgroup_id", permit_operator_group_id);
									bundle.putString("operator_id", permit_operator_id);
									bundle.putString("Selected_seats", SeatLists);
									bundle.putString("busOccurence", BusOccurence);
									bundle.putString("permit_access_token", permit_access_token);
									bundle.putString("Permit_agent_id", Permit_agent_id);
									bundle.putString("permit_ip", permit_ip);
									bundle.putString("BuyerName", edt_buyer.getText().toString());
									bundle.putString("BuyerPhone", edt_phone.getText().toString());
									bundle.putString("BuyerNRC", edt_nrc_no.getText().toString());
									bundle.putString("FromCity", FromCity);
									bundle.putString("ToCity", ToCity);
									bundle.putString("Operator_Name", Operator_Name);
									bundle.putString("from_to", from_to);
									bundle.putString("time", time);
									bundle.putString("classes", classes);
									bundle.putString("date", date);
									bundle.putString("ConfirmDate", ConfirmDate);
									bundle.putString("ConfirmTime", ConfirmTime);
									bundle.putString("ExtraCityID", ExtraCityID);
									bundle.putString("ExtraCityName", ExtraCityName);
									bundle.putString("ExtraCityPrice", ExtraCityPrice);
				    				
				    				nextScreen.putExtras(bundle);
				    				startActivity(nextScreen);
			        			}else{ 
			        				//Booking Finished!
			        				isBooking = 0;
			        				postOnlineSale(jsonObject.getString("sale_order_no"), SeatLists, jsonObject, fromPayment);
			        			}

			        		}else{
			        			dialog.dismissWithFailure();
			        			SKToastMessage.showMessage(BusConfirmActivity.this, "သင္ မွာယူေသာ လက္ မွတ္ မ်ားမွာ စကၠန္႔ပုိင္း အတြင္း တစ္ျခားသူ ယူသြားေသာေၾကာင့္ သင္ မွာ ေသာ လက္ မွတ္ မ်ား မရ ႏုိင္ေတာ့ပါ။ ေက်းဇူးျပဳ၍ တျခား လက္ မွတ္ မ်ား ျပန္ေရြးေပးပါ။", SKToastMessage.ERROR);
			        			closeAllActivities();
			        			startActivity(new Intent(BusConfirmActivity.this, SaleTicketActivity.class));
			        		}
						}else{
							isBooking = 0;
							dialog.dismissWithFailure();
							SKToastMessage.showMessage(BusConfirmActivity.this, "အခ်ိန္ ေနာက္ က် ေနသည့္ အတြက္ ၀ယ္ လုိ႔ မရပါ", SKToastMessage.ERROR);
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
	
	/**
	 *  Store Booking into Online Sale Database (starticketmyanmar.com)
	 */
	private void postOnlineSale(String orderNo, final String SeatLists, final JSONObject jsonObject, String paymentType) {
		// TODO Auto-generated method stub
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postOnlineSaleDB(
				orderNo
				, permit_operator_id
				, AppLoginUser.getCodeNo()
				, AppLoginUser.getAccessToken(), ExtraCityName, AppLoginUser.getPhone()
				, AppLoginUser.getUserName(), AppLoginUser.getAddress(), ""
				, "0", "0", "", "", "", "1"
				, paymentType, new Callback<Response>() {
			
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "Error: "+arg0.getResponse().getStatus());
						}
						dialog.dismissWithFailure();
					}

					public void success(Response arg0, Response arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							
		    				try {
/*		    					Bundle bundle = new Bundle();
		        				bundle.putString("Operator_Name", BusSeats.get(0).getOperator());			    				
			    				bundle.putString("from_to", From+"-"+To);
			    				bundle.putString("time", Time);
			    				bundle.putString("classes", BusClasses);
			    				bundle.putString("date", changeDateString(Date));
			    				bundle.putString("selected_seat",  SeatLists);
								bundle.putString("sale_order_no", jsonObject.getString("sale_order_no"));
								bundle.putString("bus_occurence", BusSeats.get(0).getSeat_plan().get(0).getId().toString());
			    				bundle.putString("ticket_price", BusSeats.get(0).getSeat_plan().get(0).getPrice()+"");
		        				bundle.putString("ConfirmDate", todayDate);
		        				bundle.putString("ConfirmTime", todayTime);
		        				bundle.putString("CustomerName", AppLoginUser.getUserName());
		        				bundle.putString("BuyerName",CustName);
		        				bundle.putString("BuyerPhone",CustPhone);
		        				bundle.putString("BuyerNRC","-");
		        				//Get Seat Count
		        				String[] seats = SeatLists.split(",");
		        				bundle.putString("SeatCount", seats.length+"");
			    				bundle.putString("permit_ip", permit_ip);
			    				bundle.putString("permit_access_token", permit_access_token);
			    				
			    				Integer amount = BusSeats.get(0).getSeat_plan().get(0).getPrice() *  seats.length;
			    				bundle.putString("total_amount", amount.toString());*/
			    				//OrderDateTime = bundle.getString("order_date");
			    				
		        				//Show Voucher
		        				//startActivity(new Intent(BusSelectSeatActivity.this, PDFBusActivity.class).putExtras(bundle));
		    					
		    					Log.i("", "Booking status: "+arg1.getStatus()+", reson: "+arg1.getReason());
		    					
		    					Bundle bundle = new Bundle();
		        				bundle.putString("payment_type", "Cash on Shop");
		    					
		    					startActivity(new Intent(BusConfirmActivity.this, ThankYouActivity.class).putExtras(bundle));
		    					
		    					dialog.dismissWithSuccess();
		    					
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}
	
	private void getAgent(){
		
		SharedPreferences pref = this.getApplicationContext().getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		String user_id = pref.getString("user_id", null);
		
		String param = MCrypt.getInstance().encrypt(SecureParam.getAllAgentParam(accessToken,user_id));
		NetworkEngine.getInstance().getAllAgent(param, new Callback<Response>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					agentList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Agent>>(){}.getType());
					
					if (agentList != null && agentList.size() > 0) {
						
						agentListAdapter = new ArrayAdapter<Agent>(BusConfirmActivity.this, android.R.layout.simple_dropdown_item_1line, agentList);
					    auto_txt_agent.setAdapter(agentListAdapter);
					    
					    for(int i=0; i< agentList.size(); i++){
					    	if(agentList.get(i).getId().equals(AgentID)){
					    		auto_txt_agent.setText(agentList.get(i).getName().toString());
					    	}
					    }
					    auto_txt_agent.setOnItemClickListener(new OnItemClickListener() {

							public void onItemClick(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {
								// TODO Auto-generated method stub
								Log.i("", "Hello Selected Agent ID = "+ ((Agent)arg0.getAdapter().getItem(arg2)).getId());
					           	AgentID = ((Agent)arg0.getAdapter().getItem(arg2)).getId();
							}
						});
					}
				}
			}
		});
	}
}
