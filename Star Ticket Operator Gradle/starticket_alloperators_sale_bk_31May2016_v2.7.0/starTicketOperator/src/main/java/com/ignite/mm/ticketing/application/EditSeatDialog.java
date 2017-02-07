package com.ignite.mm.ticketing.application;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.starticketsale.R;
import com.ignite.mm.ticketing.starticketsale.BusConfirmActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class EditSeatDialog extends Dialog {

	private Button btn_cancel;
	private Button btn_edit;
	private Callback mCallback;
	private EditText edt_name;
	private EditText edt_phone;
	private AutoCompleteTextView autoTxt_nrc;
	private EditText edt_ticket_no;
	private String userRole;
	private Context context;
	private AutoCompleteTextView autoTxt_agent;
	private EditText edt_discount;
	private List<Agent> agentList;
	private ArrayAdapter<Agent> agentListAdapter;
	protected String AgentID = "0";
	private List<String> nrcFormat;
	private ArrayAdapter<String> nrcListAdapter;
	private Spinner sp_remark_type;
	private LinearLayout layout_remark;
	private Integer selectedRemarkType;
	private EditText edt_remark;
	private CheckBox cbox_freeticket;
	private RadioGroup rdogroup_freeticket;
	private RadioButton rdo_promotion;
	private RadioButton rdo_manage;
	private RadioButton rdo_10plus;
	private RadioButton rdo_pilgrim;
	private RadioButton rdo_sponsor;
	private RadioButton rdo_local;
	private RadioButton rdo_foreign;
	private TextView txt_seat_id;
	private String userID;
	private List<Seat_list> Seat_List;
	private Seat_list seat_list;
	public static String free_ticket_remark;
	
	public EditSeatDialog(Context context, String userRole, List<Agent> agentList, String userId, Seat_list list) {
		super(context);
		// TODO Auto-generated constructor stub
		getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		setContentView(R.layout.dialog_edit_seat_info);
		
		this.context = context;
		this.userRole = userRole;
		this.agentList = agentList;
		userID = userId;
		seat_list = list;
		
		txt_seat_id = (TextView)findViewById(R.id.txt_seat_id);
		cbox_freeticket = (CheckBox)findViewById(R.id.cbox_freeticket);
		rdogroup_freeticket = (RadioGroup)findViewById(R.id.rdogroup_freeticket);
		rdo_promotion = (RadioButton)findViewById(R.id.rdo_promotion);
		rdo_manage = (RadioButton)findViewById(R.id.rdo_manage);
		rdo_10plus = (RadioButton)findViewById(R.id.rdo_10plus);
		rdo_pilgrim = (RadioButton)findViewById(R.id.rdo_pilgrim);
		rdo_sponsor = (RadioButton)findViewById(R.id.rdo_sponsor);
		rdo_local = (RadioButton)findViewById(R.id.rdo_local);
		rdo_foreign = (RadioButton)findViewById(R.id.rdo_foreign);
		
		layout_remark = (LinearLayout)findViewById(R.id.layout_remark);
		edt_remark = (EditText) findViewById(R.id.edt_remark);
		sp_remark_type = (Spinner) findViewById(R.id.sp_remark_type);
		edt_name 		= (EditText) findViewById(R.id.edt_name);
		edt_phone 		= (EditText) findViewById(R.id.edt_phone);
		autoTxt_nrc 	= (AutoCompleteTextView) findViewById(R.id.autoTxt_nrc);
		edt_ticket_no 	= (EditText) findViewById(R.id.edt_ticket_no);
		autoTxt_agent 	= (AutoCompleteTextView)findViewById(R.id.autoTxt_agent);
		edt_discount 	= (EditText)findViewById(R.id.edt_discount);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
			
		
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add(this.context.getString(R.string.str_choose_remark));
		remarkTypes.add(this.context.getString(R.string.str_lan_kyo));
		remarkTypes.add(this.context.getString(R.string.str_taung_yan));
		remarkTypes.add(this.context.getString(R.string.str_change_seat));
		remarkTypes.add(this.context.getString(R.string.str_change_date));
		remarkTypes.add(this.context.getString(R.string.str_see_pyat));
		remarkTypes.add(this.context.getString(R.string.str_taung_yaung));
		remarkTypes.add(this.context.getString(R.string.str_sat_go));
		
		ArrayAdapter<String> remarkAdapter = new ArrayAdapter<String>(this.context,
				android.R.layout.simple_dropdown_item_1line, remarkTypes);
		sp_remark_type.setAdapter(remarkAdapter);
		sp_remark_type.setOnItemSelectedListener(remarkTypeSelectedListener);
		
		if (userRole.equals("7")) {
			btn_edit.setVisibility(View.GONE);
			btn_cancel.setVisibility(View.GONE);
		}else {
			btn_edit.setVisibility(View.VISIBLE);
			btn_cancel.setVisibility(View.VISIBLE);
		}
		btn_cancel.setOnClickListener(clickListener);
		btn_edit.setOnClickListener(clickListener);
		
		setupAgents(autoTxt_agent);
		
		setTitle("Delete | Edit Seat");
		
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
		
		nrcListAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_dropdown_item_1line, nrcFormat);
		autoTxt_nrc.setAdapter(nrcListAdapter);
		
		cbox_freeticket.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked)
					rdogroup_freeticket.setVisibility(View.VISIBLE);
				else
					rdogroup_freeticket.setVisibility(View.GONE);
			}
		});
	}
	
	private void setupAgents(AutoCompleteTextView textView){
		ArrayAdapter<Agent> agentListAdapter = new ArrayAdapter<Agent>(context, android.R.layout.simple_dropdown_item_1line, agentList);
		autoTxt_agent = textView;
		autoTxt_agent.setAdapter(agentListAdapter);
		autoTxt_agent.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
	           	AgentID = ((Agent)arg0.getAdapter().getItem(arg2)).getId().toString();
			}
		});
	}
	
	private OnItemSelectedListener remarkTypeSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (arg2 == 0) {
				//If choose remark type, do not show remark text box
				btn_edit.setVisibility(View.VISIBLE);
				txt_seat_id.setVisibility(View.GONE);
				layout_remark.setVisibility(View.GONE);
				
				Log.i("", "booking or not: "+seat_list.getBooking());
				
				if (seat_list.getBooking() == 0) {
					//If sale
					if (!userRole.equals("2")) {
						//If not staff, show delete button (Allow to delete seats)
						Log.i("", "UserId Edit Seat: "+userID);
						btn_cancel.setVisibility(View.VISIBLE);
					}else {
						btn_cancel.setVisibility(View.GONE);
					}
				}else {
					//if booking
					btn_cancel.setVisibility(View.VISIBLE);
				}

			}else if (arg2 == 3 || arg2 == 4) {
				//If Change Seat (or) Change Date, show seat id and remark text box 
				txt_seat_id.setVisibility(View.VISIBLE);
				layout_remark.setVisibility(View.VISIBLE);
				btn_cancel.setVisibility(View.VISIBLE);
				btn_edit.setVisibility(View.GONE);
				edt_remark.setFocusable(true);
				edt_remark.requestFocus();
			}else{
				txt_seat_id.setVisibility(View.GONE);
				btn_edit.setVisibility(View.VISIBLE);
				layout_remark.setVisibility(View.VISIBLE);
				edt_remark.setFocusable(true);
				
				Log.i("", "booking or not2: "+seat_list.getBooking());
				
				if (seat_list.getBooking() == 0) {
					//If sale
					if (!userRole.equals("2")) {
						//If not staff, show delete button (Allow to delete seats)
						Log.i("", "UserId Edit Seat: "+userID);
						btn_cancel.setVisibility(View.VISIBLE);
					}else {
						btn_cancel.setVisibility(View.GONE);
					}
				}else {
					//if booking
					btn_cancel.setVisibility(View.VISIBLE);
				}
				edt_remark.requestFocus();
			}

			selectedRemarkType = arg2;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};
	
	public String getName() {
		return edt_name.getText().toString();
	}

	public void setName(String name) {
		edt_name.setText(name);
	}

	public String getPhone() {
		return edt_phone.getText().toString();
	}

	public void setPhone(String phone) {
		edt_phone.setText(phone);
	}

	public String getNRC() {
		return autoTxt_nrc.getText().toString();
	}

	public void setNRC(String nRC) {
		autoTxt_nrc.setText(nRC);
	}

	public String getAgent() {
		return autoTxt_agent.getText().toString();
	}

	public void setAgent(String agent) {
		autoTxt_agent.setText(agent);
	}
	
	public String getAgentId() {
		return AgentID;
	}
	
	public void setAgentId(String agentId) {
		AgentID = agentId;
	}
	
	public String getDiscount() {
		return edt_discount.getText().toString();
	}

	public void setDiscount(String discount) {
		edt_discount.setText(discount);
	}

	public String getTicketNo() {
		return edt_ticket_no.getText().toString();
	}

	public void setTicketNo(String ticketNo) {
		edt_ticket_no.setText(ticketNo);
	}
	
	public Integer getRemarkType() {
		return selectedRemarkType;
	}

	public void setRemarkType(Integer remarkType) {
		sp_remark_type.setSelection(remarkType);
	}
	
	public String getRemark() {
		return edt_remark.getText().toString();
	}

	public void setRemark(String remark) {
		edt_remark.setText(remark);
	}
	
	public void setSeatIdRemark(Integer seatId){
		//Spanned seatRed = Html.fromHtml("<font color = 'red'>"+seatId+"</font>");
		//Spanned deleteRed = Html.fromHtml("<font color = '#BF4017'>"+context.getString(R.string.str_click_delete)+"</font>");
		
		txt_seat_id.setText(Html.fromHtml(context.getString(R.string.str_seat_id) 
				+" (<font color = '#000000'>"+seatId+"</font>"+") "
				+context.getString(R.string.str_type_seat_id)));
	}

	public void setFreeTicket(Integer isFree) {
		if (isFree == 1) {
			cbox_freeticket.setChecked(true);
		}else if (isFree == 0) {
			cbox_freeticket.setChecked(false);
		}
	}
	
	public Integer getFreeTicket() {
		if (cbox_freeticket.isChecked()) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public String getFreeTicketRemark() {
		int rdoId = rdogroup_freeticket.getCheckedRadioButtonId();
		RadioButton rdo_free = (RadioButton) findViewById(rdoId);
		free_ticket_remark = rdo_free.getText().toString();
		return free_ticket_remark;
	}

	public void setFreeTicketRemark(String freeTicketRemark) {
		switch(freeTicketRemark){
		case "Promotion": 
			rdo_promotion.setChecked(true);
			break;
		case "Management":
			rdo_manage.setChecked(true);
			break;
		case "10+":
			rdo_10plus.setChecked(true);
			break;
		case "Pilgrim":
			rdo_pilgrim.setChecked(true);
			break;
		case "Sponsor":
			rdo_sponsor.setChecked(true);
			break;		
		}
	}

	public void setNationality(String nationality) {
		if (nationality != null) {
			if (nationality.equals("foreign")) {
				rdo_foreign.setChecked(true);
			}else if (nationality.equals("local")) {
				rdo_local.setChecked(true);
			}
		}
	}
	
	public String getNationality() {
		// TODO Auto-generated method stub
		String nation = "";
		
		if (rdo_local.isChecked())
			nation = "local";
		if (rdo_foreign.isChecked())
			nation = "foreign";
		
		return nation;
	}

	public boolean checkFields() {
		if (edt_name.getText().toString().length() == 0) {
			edt_name.setError("Enter Buyer Name");
			return false;
		}

		if (edt_phone.getText().toString().length() == 0) {
			edt_phone.setError("Enter Phone No.");
			return false;
		}
		if (autoTxt_agent.getText().toString().length() == 0) {
			autoTxt_agent.setError("Enter Agent Name");
			return false;
		}
		if (autoTxt_agent.getText().toString().trim().length() == 0) {
			autoTxt_agent.setText("");
			autoTxt_agent.setError("Enter Agent Name");
			return false;
		}
		boolean isExistAgent = false;
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i).getName().equals(autoTxt_agent.getText().toString())) {
				isExistAgent = true; 
				break;
			}
		}
		
		if(!isExistAgent){
			autoTxt_agent.setText("");
			autoTxt_agent.setError("Please Choose Agent");
			return false;
		}
		if (edt_ticket_no.getText().toString().length() == 0) {
			edt_ticket_no.setError("Enter Ticket No.");
			return false;
		}
		if (selectedRemarkType == 1) {
			if (edt_remark.getText().toString().length() == 0) {
				edt_remark.setError("Enter Remark");
				return false;
			}
		}

		return true;
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_cancel){
				if(mCallback != null){
					dismiss();
					mCallback.onCancel();
				}
			}
			
			if(v == btn_edit){
				if(mCallback != null){
					if(AgentID.length() != 0 && checkFields()){
						dismiss();
						mCallback.onEdit();
					}else{
						autoTxt_agent.setError("Please Choose Agents.");
					}
				}
			}
		}
	};
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	public interface Callback{
		void onEdit();
		void onCancel();
	}

}
