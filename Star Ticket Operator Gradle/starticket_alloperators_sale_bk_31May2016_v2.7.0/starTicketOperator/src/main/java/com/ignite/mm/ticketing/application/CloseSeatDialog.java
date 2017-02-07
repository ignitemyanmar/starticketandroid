package com.ignite.mm.ticketing.application;

import java.util.List;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ignite.mm.ticketing.starticketsale.R;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CloseSeatDialog{

	private Button btn_cancel;
	private Button btn_save;
	private Callback mCallback;
	private Context ctx;
	private EditText edt_remark;
	private MaterialDialog dialog;
	protected String AgentID = "";
	private List<Agent> agentList;
	private AutoCompleteTextView edt_agent;
	private TextView txt_seller;
	private String mtitle;
	private String mseller;
	private String mbuttonSave;

	public CloseSeatDialog(Context context, List<Agent> list, String title, String seller, String buttonSave) {
		// TODO Auto-generated constructor stub
		agentList = list;
		mtitle = title;
		mseller = seller;
		mbuttonSave = buttonSave;
		ctx = context;
		
		View view = View.inflate(context, R.layout.dialog_close_seat, null);
		dialog = new MaterialDialog.Builder(context)
        .title(mtitle)
        .customView(view, true)
        .show();
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		txt_seller = (TextView)view.findViewById(R.id.txt_seller);
		txt_seller.setText(mseller);
		
		edt_agent = (AutoCompleteTextView) view.findViewById(R.id.edt_agent);
		edt_remark = (EditText) view.findViewById(R.id.edt_remark);
		
		btn_save = (Button) view.findViewById(R.id.btn_close_seat);
		btn_save.setText(mbuttonSave);
		
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		setupAgents(edt_agent);
	}
	
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
						mCallback.onSave(AgentID,edt_remark.getText().toString());
					}else{
						edt_agent.setError("Please Choose Agents.");
					}
					
				}
			}
		}
	};

	
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
		void onSave(String agentId, String remark);
		void onCancel();
	}

}
