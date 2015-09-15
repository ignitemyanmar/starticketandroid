package com.ignite.mm.ticketing.application;
import java.util.ArrayList;
import java.util.List;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class EnterIPDialog{

	private Button btn_cancel;
	private Button btn_save;
	private Callback mCallback;
	private AutoCompleteTextView edt_ip;
	private MaterialDialog dialog;

	public EnterIPDialog(Context context) {
		// TODO Auto-generated constructor stub
		
		View view = View.inflate(context, R.layout.dialog_ip, null);
		dialog = new MaterialDialog.Builder(context)
        .title("Enter IP Address")
        .customView(view, true)
        .show();
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		edt_ip = (AutoCompleteTextView) view.findViewById(R.id.edt_ip);
		edt_ip.setText(NetworkEngine.ip);
		List<String> lists = new ArrayList<String>();
		lists.add("192.168.1.101");
		lists.add("elite.starticketmyanmar.com");
		lists.add("mdm.starticketmyanmar.com");
		lists.add("shwemandalar.starticketmyanmar.com");
		lists.add("myatmandalartun.starticketmyanmar.com");
		lists.add("arkartha.starticketmyanmar.com");
		lists.add("asiaexpress.starticketmyanmar.com");
		lists.add("moekaungkin.starticketmyanmar.com");
		lists.add("toeyadanar.starticketmyanmar.com");
		lists.add("lumbini.starticketmyanmar.com");
		lists.add("dmt.starticketmyanmar.com");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, lists);
		edt_ip.setAdapter(adapter);
		
		btn_save = (Button) view.findViewById(R.id.btn_ok);
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		
	}
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_cancel){
				if(mCallback != null){
					dialog.dismiss();
					mCallback.onCancel();
				}
			}
			if(v == btn_save){
				if(mCallback != null){
					mCallback.onSetIP(edt_ip.getText().toString());	
					dialog.dismiss();
				}
			}
		}
	};

	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	
	public interface Callback{
		void onSetIP(String ip);
		void onCancel();
	}

}
