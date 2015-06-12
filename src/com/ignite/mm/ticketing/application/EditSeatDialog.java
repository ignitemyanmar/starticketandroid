package com.ignite.mm.ticketing.application;

import com.ignite.mm.ticketing.callcenter.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditSeatDialog extends Dialog {

	private Button btn_cancel;
	private Button btn_edit;
	private Callback mCallback;
	private EditText edt_name;
	private EditText edt_phone;
	private EditText edt_nrc;
	private EditText edt_ticket_no;

	public EditSeatDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_edit_seat_info);
		edt_name 		= (EditText) findViewById(R.id.edt_name);
		edt_phone 		= (EditText) findViewById(R.id.edt_phone);
		edt_nrc 		= (EditText) findViewById(R.id.edt_nrc);
		edt_ticket_no 	= (EditText) findViewById(R.id.edt_ticket_no);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
			
		btn_cancel.setOnClickListener(clickListener);
		btn_edit.setOnClickListener(clickListener);
		setTitle("Delete | Edit Seat");
	}
	
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
		return edt_nrc.getText().toString();
	}

	public void setNRC(String nRC) {
		edt_nrc.setText(nRC);
	}

	public String getTicketNo() {
		return edt_ticket_no.getText().toString();
	}

	public void setTicketNo(String ticketNo) {
		edt_ticket_no.setText(ticketNo);
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
				if(mCallback != null && checkValue()){
					dismiss();
					mCallback.onEdit();
				}
			}
		}
	};
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	private boolean checkValue(){
		if(edt_name.getText().toString().length() == 0){
			edt_name.setError("Please enter name.");
			edt_name.requestFocus();
			return false;
		}
		if(edt_phone.getText().toString().length() == 0){
			edt_phone.setError("Please enter phone.");
			edt_phone.requestFocus();
			return false;
		}
		
		if(edt_phone.getText().toString().length() < 6)
    	{
			edt_phone.setError("Enter at least '6' numbers");
			edt_phone.requestFocus();
			return false;
		}
    	
		if (!edt_phone.getText().toString().startsWith("09") && !edt_phone.getText().toString().startsWith("01")) {
    		edt_phone.setError("Enter only start with '09 (or) 01'");
    		return false;
		}
		
		return true;
	}
	
	public interface Callback{
		void onEdit();
		void onCancel();
	}

}
