package com.ignite.mm.ticketing.application;

import java.util.ArrayList;
import java.util.List;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ignite.mm.ticketing.gatethein.R;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;
import com.ignite.mm.ticketing.sqlite.database.model.SelectSeatBooking;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class BookingDialog{

	private Button btn_cancel;
	private Button btn_save;
	private Callback mCallback;
	private AutoCompleteTextView edt_change;
	
	private Context ctx;
	private EditText edt_name;
	
	private EditText edt_phone;
	private LinearLayout layout_remark;
	private Spinner sp_remark_type;
	private EditText edt_remark;
	protected int selectedRemarkType;
	private MaterialDialog dialog;
	
	private TextView txt_selected_seats;
	private List<SelectSeatBooking> mSelect_seats;

	public BookingDialog(Context context, List<Agent> list, List<SelectSeatBooking> select_seats) {
		// TODO Auto-generated constructor stub
		agentList = list;
		ctx = context;
		mSelect_seats = select_seats;
		
		View view = View.inflate(context, R.layout.dialog_booking, null);
		dialog = new MaterialDialog.Builder(context)
        .title("Booking Order")
        .customView(view, true)
        .show();
		
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		txt_selected_seats = (TextView)view.findViewById(R.id.txt_selected_seats);
		edt_name = (EditText) view.findViewById(R.id.edt_name);
		edt_phone = (EditText) view.findViewById(R.id.edt_phone);
		edt_change = (AutoCompleteTextView) view.findViewById(R.id.edt_agent);
		layout_remark = (LinearLayout) view.findViewById(R.id.layout_remark);
		sp_remark_type = (Spinner) view.findViewById(R.id.sp_remark_type);
		
		edt_remark = (EditText) view.findViewById(R.id.edt_remark);
		btn_save = (Button) view.findViewById(R.id.btn_change_agent);
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		
		if (mSelect_seats != null && mSelect_seats.size() > 0) {
			
			Log.i("", "select seat (dialog): "+mSelect_seats.size());
			
			String seattime="";
			for (int i = 0; i < mSelect_seats.size(); i++) {
				if (mSelect_seats.size() == mSelect_seats.size() - 1) {
					seattime += mSelect_seats.get(i).getSelect_time()
							+"("+mSelect_seats.get(i).getSelect_seat()+")";
				}else {
					seattime += mSelect_seats.get(i).getSelect_time()
							+"("+mSelect_seats.get(i).getSelect_seat()+"), ";
				}
			}
			txt_selected_seats.setText(seattime+"");
		}
		
		List<String> remarkTypes = new ArrayList<String>();
		remarkTypes.add(ctx.getString(R.string.str_choose_remark));
		remarkTypes.add(ctx.getString(R.string.str_lan_kyo));
		remarkTypes.add(ctx.getString(R.string.str_taung_yan));
		remarkTypes.add(ctx.getString(R.string.str_change_seat));
		remarkTypes.add(ctx.getString(R.string.str_change_date));
		remarkTypes.add(ctx.getString(R.string.str_see_pyat));
		remarkTypes.add(ctx.getString(R.string.str_taung_yaung));
		remarkTypes.add(ctx.getString(R.string.str_sat_go));
		
		ArrayAdapter<String> remarkAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_dropdown_item_1line, remarkTypes);
		sp_remark_type.setAdapter(remarkAdapter);
		sp_remark_type.setOnItemSelectedListener(remarkTypeSelectedListener);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		setupAgents(edt_change);
	}
	
	private OnItemSelectedListener remarkTypeSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2 == 0){
				layout_remark.setVisibility(View.GONE);
			}else{
				layout_remark.setVisibility(View.VISIBLE);
			}
			selectedRemarkType = arg2;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_cancel){
				if(mCallback != null){
					if (dialog != null) {
						dialog.dismiss();
					}
					
					mCallback.onCancel();
				}
			}
			if(v == btn_save){
				if(mCallback != null){
					if(AgentID.length() != 0 && checkInAgent()){
						if (dialog != null) {
							dialog.dismiss();
						}
						
						mCallback.onSave(AgentID,edt_name.getText().toString(),edt_phone.getText().toString(), selectedRemarkType, edt_remark.getText().toString());
					}else{
						edt_change.setError("Please Choose Agents.");
					}
					
				}
			}
		}
	};
	protected String AgentID = "";
	private List<Agent> agentList;
	private AutoCompleteTextView edt_agent;
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	private boolean checkInAgent(){
		boolean isExistAgent = false;
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i).getName().equals(edt_agent.getText().toString())) {
				isExistAgent = true; 
				break;
			}
		}
		
		if(!isExistAgent){
			edt_agent.setText("");
			edt_agent.setError("Please Choose Agent");
			return false;
		}
		return true;
	}
	
	private void setupAgents(AutoCompleteTextView textView){
		ArrayAdapter<Agent> agentListAdapter = new ArrayAdapter<Agent>(ctx, android.R.layout.simple_dropdown_item_1line, agentList);
		this.edt_agent = textView;
		this.edt_agent.setAdapter(agentListAdapter);
		this.edt_agent.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
	           	AgentID = ((Agent)arg0.getAdapter().getItem(arg2)).getId().toString();
			}
		});
	}
	
	public interface Callback{
		void onSave(String agentId, String custName, String custPhone, int remarkType, String remark);
		void onCancel();
	}

}
