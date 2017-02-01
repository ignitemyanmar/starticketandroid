package com.ignite.mdm.ticketing.agent.callcenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
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
import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.util.Constant;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mdm.ticketing.custom.listview.adapter.ExtraCityAdapter;
import com.ignite.mdm.ticketing.custom.listview.adapter.StarTicketAgentAdapter;
import com.ignite.mdm.ticketing.http.connection.HttpConnection;
import com.ignite.mdm.ticketing.sqlite.database.model.Agent;
import com.ignite.mdm.ticketing.sqlite.database.model.Cities;
import com.ignite.mdm.ticketing.sqlite.database.model.ConfirmSeat;
import com.ignite.mdm.ticketing.sqlite.database.model.ExtraCity;
import com.ignite.mdm.ticketing.sqlite.database.model.Saleitem;
import com.ignite.mdm.ticketing.sqlite.database.model.StarTicketAgents;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.smk.custom.view.CustomTextView;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusConfirmActivity extends BaseSherlockActivity {

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private Button btn_confirm;
	private EditText edt_buyer;
	private AutoCompleteTextView edt_nrc_no;
	private EditText edt_phone;
	private ProgressDialog dialog;
	private String SaleOrderNo;
	private String SelectedSeatIndex;
	private String[] selectedSeat;
	private LinearLayout layout_ticket_no_container;
	private String AgentID = "0";
	private List<Agent> agents;
	private TextView txt_agent;
	private RadioButton rdo_cash_down;
	
	private RadioButton rdo_credit;
	private AutoCompleteTextView auto_txt_agent;
	private List<Agent> agentList;
	private ArrayAdapter<Agent> agentListAdapter;
	private EditText edt_ref_invoice_no;
	private RadioButton rdo_local;
	private List<String> nrcFormat;
	private ArrayAdapter<String> nrcListAdapter;
	private String BusOccurence = "0";
	private String Intents;
	private LinearLayout extra_city_container;
	private Spinner sp_extra_city;
	private List<ExtraCity> extraCity;
	private String ExtraCityID = "0";
	private Integer NotifyBooking;
	private TextView actionBarNoti;
	private EditText edt_remark;
	private Spinner sp_remark_type;
	private Integer selectedRemarkType;
	private LinearLayout layout_remark;
	private String Name = "";
	private String Phone = "";
	private Bundle bundle;
	private SKConnectionDetector skDetector;
	private ConnectionDetector connectionDetector;
	private String user_type;
	private EditText ticket_no;
	private List<CheckBox> lst_free_chk = new ArrayList<CheckBox>();
	private List<LinearLayout> lst_layout_free_ticket = new ArrayList<LinearLayout>();
	private List<CheckBox> lst_discount_chk = new ArrayList<CheckBox>();
	private List<EditText> lst_discount_edt = new ArrayList<EditText>();
	private List<RadioButton> lst_rdo_free_pro = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_mnt = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_10plus = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_pilgrim = new ArrayList<RadioButton>();
	private List<RadioButton> lst_rdo_free_spr = new ArrayList<RadioButton>();
	private List<RadioGroup> lst_rdo_gp_free = new ArrayList<RadioGroup>();
	private List<EditText> lst_ticket_no = new ArrayList<EditText>();
	private TextView actionBarTitle2;
	private String Permit_agent_id;
	private String permit_operator_group_id;
	private String permit_operator_id;
	private String permit_access_token;
	private String permit_ip;
	private String Nrc = "";
	private String ticket_nos;
	private String operatorPhone;
	private Spinner spn_starticket_agents;
	private List<StarTicketAgents> starTicketAgents;
	protected String SelectedAgentCodeNo;
	private String[] ticketArray;
	private TextView txt_agentTitle;
	private RadioButton radio_local_price;
	private RadioButton radio_foreign_price;
	private String local_price;
	private String foreign_price;
	private LinearLayout layout_price;
	private String Extra_city;
	
	private RadioGroup mRadioGroupGender;
	private RadioButton mRadioButtonMale;
	private RadioButton mRadioButtonFemale;
	private RadioButton mRadioButtonGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nrc_activity);

		connectionDetector = new ConnectionDetector(this);
		starTicketAgents = new ArrayList<StarTicketAgents>();
		
		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.GONE);
			actionBarNoti.setText(NotifyBooking.toString());
		}
		
		bundle = getIntent().getExtras();		
		
		if (bundle != null) {
			Log.i("", "Bundle to confirm: "+bundle.toString());
			
			ticket_nos = bundle.getString("ticket_nos");
			operatorPhone = bundle.getString("operatorPhone");
			Extra_city = bundle.getString("extra_city");
			
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
        	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        	toolbar.setTitleTextAppearance(BusConfirmActivity.this, android.R.attr.textAppearanceSmall);
        	toolbar.setSubtitleTextAppearance(BusConfirmActivity.this, android.R.attr.textAppearanceSmall);
        	toolbar.setTitle(bundle.getString("from_to")+" ["+bundle.getString("Operator_Name")+"]");
    		toolbar.setSubtitle(bundle.getString("date")+" ["+bundle.getString("time")+"] "+bundle.getString("classes"));
            this.setSupportActionBar(toolbar);
        }
        
		//actionBarTitle.setText(bundle.getString("from_to")+" ["+bundle.getString("Operator_Name")+"]");
		Log.i("", "Date: "+bundle.getString("date"));
		//actionBarTitle2.setText(bundle.getString("date")+" ["+bundle.getString("time")+"] "+bundle.getString("classes"));
		SelectedSeatIndex = bundle.getString("selected_seat");
		
		SaleOrderNo = bundle.getString("sale_order_no");
		BusOccurence = bundle.getString("bus_occurence");
		permit_operator_id = bundle.getString("permit_operator_id");
		
		permit_operator_group_id = bundle.getString("permit_operator_group_id");
		Permit_agent_id = bundle.getString("permit_agent_id");
		permit_access_token = bundle.getString("permit_access_token");
		permit_ip = bundle.getString("permit_ip");
		
		local_price = bundle.getString("Price");
		foreign_price = bundle.getString("ForeignPrice");
		
		Log.i("", "Permit_agent_id : "+Permit_agent_id);
		
		edt_buyer = (EditText) findViewById(R.id.edt_buyer);	
		if (Name != null) {
			edt_buyer.setText(Name);
		}
		edt_nrc_no = (AutoCompleteTextView) findViewById(R.id.edt_nrc_no);
		if (Nrc != null) {
			edt_nrc_no.setText(Nrc);
		}
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		if (Phone != null) {
			edt_phone.setText(Phone);
		}
		
		mRadioGroupGender = (RadioGroup)findViewById(R.id.radioGroup_Gender);
		mRadioButtonMale = (RadioButton)findViewById(R.id.radio_male);
		mRadioButtonFemale = (RadioButton)findViewById(R.id.radio_female);
		mRadioButtonGroup = (RadioButton)findViewById(R.id.radio_group);
		
		spn_starticket_agents = (Spinner)findViewById(R.id.spn_starticket_agents);
		spn_starticket_agents.setOnItemSelectedListener(agentSelectedListener);
		
		txt_agent = (CustomTextView) findViewById(R.id.txt_seller);
		edt_ref_invoice_no = (EditText) findViewById(R.id.edt_ref_invoice_no);
		rdo_cash_down = (RadioButton) findViewById(R.id.rdo_cash_down);
		rdo_credit = (RadioButton) findViewById(R.id.rdo_credit);
		rdo_local = (RadioButton) findViewById(R.id.rdo_local);
		extra_city_container = (LinearLayout) findViewById(R.id.extra_city_container);
		sp_extra_city = (Spinner) findViewById(R.id.sp_extra_city);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		sp_remark_type = (Spinner) findViewById(R.id.sp_remark_type);
		edt_remark = (EditText) findViewById(R.id.edt_remark);
		
		txt_agentTitle = (TextView) findViewById(R.id.txt_agentTitle);
		
		layout_price = (LinearLayout)findViewById(R.id.layout_price);
		radio_local_price = (RadioButton)findViewById(R.id.radio_local_price);
		radio_foreign_price = (RadioButton)findViewById(R.id.radio_foreign_price);
		
		if (local_price != null && foreign_price != null) {
			if (!local_price.equals("") && !foreign_price.equals("")) {
				 if (Integer.valueOf(local_price) > 0 && Integer.valueOf(foreign_price) > 0) {
					 if (!local_price.equals(foreign_price)) {
							layout_price.setVisibility(View.VISIBLE);
							radio_local_price.setText(getResources().getString(R.string.strmm_local)+" "+local_price+" Ks");
							radio_foreign_price.setText(getResources().getString(R.string.strmm_foreign)+" "+foreign_price+" Ks");
						}
				}
			}
		}
		
		//edt_buyer.setText(Name);
		//edt_phone.setText(Phone);
		nrcFormat = prepareNRCFormat();
		
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add("မွ�?္ �?်က္ အမ်ိဳးအစား  ေရြးရန္");
		remarkTypes.add("လမ္း�?�ကိဳ");
		remarkTypes.add("ေ�?ာင္းရန္");
		remarkTypes.add("�?ံု ေရႊ႕ ရန္");
		remarkTypes.add("Date Change ရန္");
		remarkTypes.add("စီးျဖ�?္");
		remarkTypes.add("ေ�?ာင္းေရာင္း");
		remarkTypes.add("ဆက္သြား");
		
		ArrayAdapter<String> remarkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, remarkTypes);
		sp_remark_type.setAdapter(remarkAdapter);
		sp_remark_type.setOnItemSelectedListener(remarkTypeSelectedListener);
		
		nrcListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nrcFormat);
		edt_nrc_no.setAdapter(nrcListAdapter);
		
		auto_txt_agent = (AutoCompleteTextView) findViewById(R.id.txt_agent);
		
	    agentList = new ArrayList<Agent>();
		
		layout_ticket_no_container = (LinearLayout) findViewById(R.id.ticket_no_container);
		
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		
		skDetector = new SKConnectionDetector(this);
		if(skDetector.isConnectingToInternet()){
			
			SharedPreferences pref2 = this.getApplicationContext().getSharedPreferences("User", Activity.MODE_PRIVATE);
			
			user_type = pref2.getString("user_type", null);
			
			//Get Star Ticket's Agents and Extra City
			//If Agent Log in
			if (Integer.valueOf(AppLoginUser.getRole()) <= 3) {
				//For Agents
/*				dialog = ProgressDialog.show(this, "", "Please wait...", true);
				dialog.setCancelable(true);*/
				
				txt_agentTitle.setVisibility(View.VISIBLE);
				spn_starticket_agents.setVisibility(View.VISIBLE);
				
				StarTicketAgents agent = new StarTicketAgents();
				agent.setName(AppLoginUser.getUserName());
				agent.setCodeNo(AppLoginUser.getCodeNo());
				
				Log.i("", "log in user: "+AppLoginUser.getUserName()+", code: "+AppLoginUser.getCodeNo());
				
				starTicketAgents.add(agent);
				spn_starticket_agents.setAdapter(new StarTicketAgentAdapter(
						BusConfirmActivity.this, starTicketAgents));
				
				dialog = ProgressDialog.show(this, "", "Please wait...", true);
				dialog.setCancelable(false);
				getExtraDestination();
			}else {
				//If Call Center log in
				txt_agentTitle.setVisibility(View.VISIBLE);
				spn_starticket_agents.setVisibility(View.VISIBLE);
				
				getStarTicketAgents();
			}
			
			txt_agent.setVisibility(View.GONE);
			auto_txt_agent.setVisibility(View.GONE);
			
		}else{
			Toast.makeText(BusConfirmActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
		}
		
		btn_confirm.setOnClickListener(clickListener);
		
		LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lps.setMargins(0, 10, 0, 0);

		selectedSeat = SelectedSeatIndex.split(",");
		ticketArray = ticket_nos.split(",");
		
		Log.i("", "random ticket no: "+ticket_nos);

		Random random = new Random();
		for (int i = 0; i < selectedSeat.length; i++) {
			CustomTextView label = new CustomTextView(this);
			
			if (ticketArray.length > 0) {
				label.setText(getResources().getString(R.string.strmm_ticket_no2)+"  "+(i+1)+" [Seat No."+ selectedSeat[i] +"],["+ticketArray[i].toString()+"]");
			}else {
				label.setText(getResources().getString(R.string.strmm_ticket_no2)+"  "+(i+1)+" [Seat No."+ selectedSeat[i] +"],[-]");
			}
			
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

			EditText edt_discount = new EditText(this);
			edt_discount.setHint("Enter the discount amount.");
			edt_discount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			// edt_discount.setId(i+1 * 400);
			lst_discount_edt.add(edt_discount);
			edt_discount.setVisibility(View.GONE);
			layout_ticket_no_container.addView(edt_discount);

			EditText ticket_no = new EditText(this);
			ticket_no.setInputType(InputType.TYPE_CLASS_NUMBER);
			// ticket_no.setId(i+1);
			lst_ticket_no.add(ticket_no);
			ticket_no.setSingleLine(true);
			layout_ticket_no_container.addView(ticket_no, lps);
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
		extraCity.add(new ExtraCity("0", "0", "0", "0", "0", "Select Extra City", "", ""));
		
	}
	

	
	/**
	 * Get Star Ticket's Agent List
	 */
	
	private void getStarTicketAgents() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, "", "Please wait...", true);
		dialog.setCancelable(false);
		
		Log.i("", "User's Id: "+AppLoginUser.getId());
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getStarTicketAgents(AppLoginUser.getId(), new Callback<List<StarTicketAgents>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Extra city error: "+arg0.getResponse().getStatus());
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}

			public void success(List<StarTicketAgents> arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.size() > 0) {
					Log.i("", "Star Ticket Agents: "+arg0.toString());
					
					starTicketAgents.addAll(arg0);
					spn_starticket_agents.setAdapter(new StarTicketAgentAdapter(
							BusConfirmActivity.this, starTicketAgents));
					
					for (int i = 0; i < starTicketAgents.size(); i++) {
						
						Log.i("", "Log in User's Name: "+AppLoginUser.getName()+", userName: "+AppLoginUser.getUserName()
								+", agent's name: "+starTicketAgents.get(i).getName());
						
						if (starTicketAgents.get(i).getName().equals(AppLoginUser.getUserName())) {
							spn_starticket_agents.setSelection(i);	
							break;
						}
						
						Log.i("", "agent loop: "+i+", total size: "+starTicketAgents.size());
					}
					
					if (skDetector.isConnectingToInternet()) {
						getExtraDestination();
					}else {
						Toast.makeText(BusConfirmActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
					}
					
				}else {
					Log.i("", "Agent list: "+arg0.toString());
				}
			}
		});
	}

	/**
	 *  Star Ticket Agent Item Click
	 */
	private OnItemSelectedListener agentSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			SelectedAgentCodeNo = starTicketAgents.get(arg2).getCodeNo();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
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
	
	/**
	 *  Get Extra Destination
	 */
	private void getExtraDestination(){
		
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
								
								//extraCity.clear();
								extraCity.addAll(cities);
								sp_extra_city.setAdapter(new ExtraCityAdapter(BusConfirmActivity.this, extraCity));

								for (int i = 0; i < extraCity.size(); i++) {
									if (extraCity.get(i).getCity_name().equals(Extra_city)) {
										sp_extra_city.setSelection(i);	
										extra_city_container.setVisibility(View.VISIBLE);
										break;
									}
								}
								
								sp_extra_city.setOnItemSelectedListener(itemSelectedListener);
							}else{
								Log.i("", "extra cities are null!");
								extra_city_container.setVisibility(View.GONE);
							}
						}
						
						if (dialog != null) {
							dialog.dismiss();
						}
					}

					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "Extra city error: "+arg0.getResponse().getStatus());
						}
						
						if (dialog != null) {
							dialog.dismiss();
						}
					}
				});
	}
	
	/**
	 *  Extra City Item Click 
	 */
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
				if (arg2 > 0) {
					ExtraCityID = extraCity.get(arg2).getId();
					ExtraCityName = extraCity.get(arg2).getCity_name();
					sp_remark_type.setSelection(7);				
					edt_remark.setText(extraCity.get(arg2).getCity_name());
				}else {
					ExtraCityID = "0";
					ExtraCityName = "";
					edt_remark.setText("");
				}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	protected String ticket_price = "0";
	protected String total_amount;

	protected String TicketLists = "";
	private String[] typeTicketArray;
	protected String foreign_price_server;
	
	private void comfirmOrder() {
		
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
		ProgressBar progress = (ProgressBar)dialog.findViewById(android.R.id.progress);
		progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.gray_light),android.graphics.PorterDuff.Mode.SRC_ATOP);
		dialog.setCancelable(false);
		
		Log.i("", "Ticket no: "+ticket_nos);
		
		//Enter Star Ticket Nos.
		for(int i=0; i<selectedSeat.length; i++){
			if (i == selectedSeat.length - 1) {
				TicketLists += lst_ticket_no.get(i).getText().toString();
			}else {
				TicketLists += lst_ticket_no.get(i).getText().toString()+",";
			}
		}
		
		typeTicketArray = TicketLists.split(",");
		
		List<ConfirmSeat> seats = new ArrayList<ConfirmSeat>();
		
		for (int i = 0; i < selectedSeat.length; i++) {
			//EditText ticket_no = (EditText)findViewById(i+1);
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
				EditText edt_discount = lst_discount_edt.get(i);
				if (edt_discount.getText().toString().length() == 0) {
					dialog.dismiss();
					edt_discount.setError("Please enter the discount amount.");
					return;
				}
				discount = edt_discount.getText().toString();
			}
			
			//User's Type ticket
			//lst_ticket_no.get(i).getText().toString()
			
			/*seats.add(new ConfirmSeat(BusOccurence, selectedSeat[i].toString(),
					edt_buyer.getText().toString(), edt_nrc_no.getText()
							.toString(), ticketArray[i].toString(), lst_free_chk.get(i).isChecked(),
					free_ticket_remark, Integer.valueOf(discount)));*/
			
			String  gender = null;
			
			if(mRadioGroupGender.getCheckedRadioButtonId() == R.id.radio_male){
				gender = Constant.MALE;
			}else if (mRadioGroupGender.getCheckedRadioButtonId() == R.id.radio_female){
				gender = Constant.FEMALE;
			}else{
				gender = Constant.GROUP;
			}
			
			Log.e("TAG : ","$ gender : "+gender);
//			if(mRadioButtonMale.isChecked()){
//				gender = Constant.MALE;
//			}
//			
//			if(mRadioButtonFemale.isChecked()){
//				gender = Constant.FEMALE;
//			}
//			
//			if(mRadioButtonGroup.isChecked()){
//				gender = Constant.GROUP;
//			}
			
			seats.add(new ConfirmSeat(BusOccurence, selectedSeat[i].toString(),
			edt_buyer.getText().toString(), edt_nrc_no.getText()
					.toString(), typeTicketArray[i].toString(), lst_free_chk.get(i).isChecked(),
			free_ticket_remark, Integer.valueOf(discount),gender));
			
		}
		
		SharedPreferences pref_old_sale = this.getApplicationContext().getSharedPreferences("old_sale", Activity.MODE_PRIVATE);
		String working_date = pref_old_sale.getString("working_date", null);
		
		//if(user_type.equals("Agent")){
			AgentID = String.valueOf(AppLoginUser.getId());
		//}
		Log.i("", "Ticket Arrays: "+seats.toString());
		
		Log.i("", "Param (Confirm) to encrypt: "
				+"access: "+permit_access_token+
				", SaleOrderNo: "+SaleOrderNo+
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
				", isbooking: "+"0"+
				", agent code no: "+SelectedAgentCodeNo+
				", user id: "+AppLoginUser.getId()
				);
		
		//Do Encrypt of Params		
		//In remark type para, put selected agent name
		String param = MCrypt.getInstance()
				.encrypt(
				SecureParam.postSaleConfirmParam(permit_access_token
				, SaleOrderNo, edt_ref_invoice_no.getText().toString()
				, Permit_agent_id, auto_txt_agent.getText().toString()
				, edt_buyer.getText().toString()
				, edt_phone.getText().toString(), edt_nrc_no.getText().toString()
				, SelectedAgentCodeNo, edt_remark.getText().toString()
				, ExtraCityID,  MCrypt.getInstance()
				.encrypt(seats.toString())
				, rdo_cash_down.isChecked() == true ? "1" : "2"
				, radio_local_price.isChecked() == true ? "local" : "foreign"
				, "", DeviceUtil.getInstance(this).getID(), "0",String.valueOf(AppLoginUser.getId())));
				
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
							SKToastMessage.showMessage(BusConfirmActivity.this, getResources().getString(R.string.strmm_ticket_soldout), SKToastMessage.ERROR);
							dialog.dismiss();
						}else{
							//Store Sale Data into Online Sale Database
							JSONArray arr = orderObj.getJSONArray("saleitems");
							JSONObject obj = arr.getJSONObject(0);
							ticket_price = obj.getString("price");
							foreign_price_server = obj.getString("foreign_price");
							
							Log.i("", "Ticket List to online: "+TicketLists);
								
							//Store Sale on City Mart DB
							//postOnlineSale(TicketLists);
							
							//If Success
							SKToastMessage.showMessage(BusConfirmActivity.this, getResources().getString(R.string.strmm_success_ticket), SKToastMessage.SUCCESS);
							closeAllActivities();
							//Show Voucher		
							
							if(Intents.equals("booking")){
								bundle.putString("from_intent", "booking");								
							}
							
							if (radio_foreign_price.isChecked()) {
								ticket_price = foreign_price_server;
							}
							
							bundle.putString("extra_city", ExtraCityName);
							bundle.putString("ticket_price", ticket_price);
							bundle.putString("total_amount", total_amount);
							bundle.putString("TicketNo", TicketLists);
							bundle.putString("operatorPhone", operatorPhone);
							bundle.putString("random_tickets", ticket_nos);
							
							startActivity(new Intent(BusConfirmActivity.this, PDFBusActivity.class).putExtras(bundle));
							dialog.dismiss();
							finish();
							
							//Log.i("", "Server Response1: "+arg0.getStatus()+", "+arg0.getReason()+", "+arg0.getBody());
							//Log.i("", "Server Response: "+arg1.getStatus()+", "+arg1.getReason()+", "+arg1.getBody());
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Log.i("", "Response confirm is null!");
					dialog.dismiss();
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
	 *  Store saled-data into Online Sale Database (starticketmyanmar.com)
	 *  
	 */
	protected void postOnlineSale(final String ticketNoString) {
		// TODO Auto-generated method stub
		Log.i("", "SaleOrderNo: "+SaleOrderNo+", Op-Id: "+permit_operator_id+", Agent code no: "+SelectedAgentCodeNo
				+", Token: "+AppLoginUser.getAccessToken()
				+", Extra City Name: "+ExtraCityName
				+", Ticket No: "+ticketNoString);
		Log.i("", "Ticket Nos: "+ticketNoString);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		//NetworkEngine.setIP("128.199.255.246");
		NetworkEngine.getInstance().postOnlineSaleDB(SaleOrderNo, permit_operator_id
				, SelectedAgentCodeNo, 0, AppLoginUser.getAccessToken()
				, ExtraCityName, ticketNoString, new Callback<Response>() {
			
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						if (arg0.getResponse() != null) {
							Log.i("", "Error: "+arg0.getResponse().getStatus());

						}
						dialog.dismiss();
					}

					public void success(Response arg0, Response arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							
							SKToastMessage.showMessage(BusConfirmActivity.this, "Order Success", SKToastMessage.SUCCESS);
							closeAllActivities();
							//Show Voucher		
							
							if(Intents.equals("booking")){
								bundle.putString("from_intent", "booking");								
							}
							
							bundle.putString("extra_city", ExtraCityName);
							bundle.putString("ticket_price", ticket_price);
							bundle.putString("total_amount", total_amount);
							bundle.putString("TicketNo", ticketNoString);
							bundle.putString("operatorPhone", operatorPhone);
							
							startActivity(new Intent(BusConfirmActivity.this, PDFBusActivity.class).putExtras(bundle));
							dialog.dismiss();
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
    	
    	List<String> listTicketNo = new ArrayList<String>();
    	for (int i = 0; i < lst_ticket_no.size(); i++) {
    		listTicketNo.add(lst_ticket_no.get(i).getText().toString());
    		
    		if (lst_ticket_no.get(i).getText().toString().length() == 0) {
    			lst_ticket_no.get(i).setError("Enter Ticket No");
    			return false;
			}
		}
    	
    	//Set List doesn't accept duplicate values, & if (Not duplicate), return true
    	                                            // if (duplicate), return false
    	final Set<String> set1 = new HashSet<String>();

    	for (String ticketNo : listTicketNo) {
    		//If Duplicate Tickets are exists in List 
    		if (!set1.add(ticketNo)) {
    			SKToastMessage.showMessage(BusConfirmActivity.this, getResources().getString(R.string.strmm_ticket_no2)+" "+ticketNo+" "+getResources().getString(R.string.strmm_same_ticketno), SKToastMessage.ERROR);
    			return false;
    		}
    	}
    	
    
    	if(mRadioGroupGender.getCheckedRadioButtonId() == -1){
    		SKToastMessage.showMessage(BusConfirmActivity.this,getResources().getString(R.string.strmm_select_gender), SKToastMessage.ERROR);
			return false;
    	}
    	
//    	if (mRadioButtonMale.isChecked()) {
//    		SKToastMessage.showMessage(BusConfirmActivity.this,getResources().getString(R.string.strmm_select_gender), SKToastMessage.ERROR);
//			return false;
//		}
//    	
//    	if (mRadioButtonFemale.isChecked()) {
//    		SKToastMessage.showMessage(BusConfirmActivity.this,getResources().getString(R.string.strmm_select_gender), SKToastMessage.ERROR);
//			return false;
//		}
//    	
//    	if (mRadioButtonGroup.isChecked()) {
//    		SKToastMessage.showMessage(BusConfirmActivity.this,getResources().getString(R.string.strmm_select_gender), SKToastMessage.ERROR);
//			return false;
//		}
    	
    	
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
						Log.i("", "Enter here Agent confirm !!!!!!!!!!!!");
						comfirmOrder();
					}
				}
				
/*				if (user_type.equals("operator")) {
					if (checkFieldsOperator()) {
						if (skDetector.isConnectingToInternet()) {
							Log.i("", "Enter here Operator confirm !!!!!!!!!!!!");
							comfirmOrder();
						}
					}
				}else {
					if (checkFieldsAgent()) {
						if (skDetector.isConnectingToInternet()) {
							Log.i("", "Enter here Agent confirm !!!!!!!!!!!!");
							comfirmOrder();
						}
					}
				}*/

			}
		}
	};
	
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (Intents.equals("booking")) {
			finish();
		} else {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setMessage("Are you sure to exit?");

			alertDialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if (SKConnectionDetector.getInstance(
									BusConfirmActivity.this)
									.isConnectingToInternet()) {
								String param = MCrypt.getInstance().encrypt(SecureParam.deleteSaleOrderParam(permit_access_token));
								
								Log.i("", "Param to delete: "+param+", SaleOrderNo to delete: "+MCrypt.getInstance().encrypt(SaleOrderNo));
								
								NetworkEngine.getInstance().deleteSaleOrder(
										param, MCrypt.getInstance().encrypt(SaleOrderNo),
										new Callback<Response>() {

											public void success(
													Response arg0,
													Response arg1) {
												
											}

											public void failure(
													RetrofitError arg0) {
												// TODO Auto-generated method
												// stub
											}
										});
							}
							finish();
						}
					});

			alertDialog.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							return;
						}
					});

			alertDialog.show();
		}
		// super.onBackPressed();
	}
	
	/**
	 * If back arrow button clicked, close this activity. 
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		onBackPressed();
		//finish();
		return super.getSupportParentActivityIntent();
	}
	private ArrayList<String> prepareNRCFormat() {
		ArrayList<String>nrcList = new ArrayList<String>();
		String [] nrcs = getResources().getStringArray(R.array.nrc_format);
		
		for (String format : nrcs) {
			nrcList.add(format);
		}
		
		return nrcList;
	}
	
	
	
}
