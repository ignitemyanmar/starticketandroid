package com.ignite.mm.ticketing.application;

import java.util.List;

import com.ignite.mm.ticketing.agent.callcenter.R;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeliveryCompleteDialog extends Dialog {
	
	private Button btn_cancel;
	private Callback mCallback;
	private Context ctx;
	private EditText edt_password;
	private Button btn_delivery_complete;

	public DeliveryCompleteDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		ctx = context;
		setContentView(R.layout.dialog_delivery_complete);
		edt_password = (EditText) findViewById(R.id.edt_password);
		btn_delivery_complete = (Button) findViewById(R.id.btn_delivery_complete);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_delivery_complete.setOnClickListener(clickListener);
		setTitle("Are you sure Delivery Complete?");
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
			if(v == btn_delivery_complete){
				if(mCallback != null && checkValue()){
					dismiss();
					mCallback.onComplete(edt_password.getText().toString());
				}
			}
		}
	};
	protected String AgentID = "0";

	private List<Agent> agentList;
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}	

	private boolean checkValue(){
		if(edt_password.getText().toString().length() == 0){
			edt_password.setError("Please Enter Your Password");
			edt_password.requestFocus();
			return false;
		}
		
		return true;
	}

	public interface Callback{
		void onComplete(String password);
		void onCancel();
	}

}
