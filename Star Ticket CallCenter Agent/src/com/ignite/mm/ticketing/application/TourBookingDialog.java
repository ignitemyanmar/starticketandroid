package com.ignite.mm.ticketing.application;

import java.util.ArrayList;
import java.util.List;
import com.ignite.mm.ticketing.agent.callcenter.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TourBookingDialog extends Dialog{

	private EditText edt_booker_ph;
	private Button btn_cancel;
	private Button btn_submit_booking;
	private Callback mCallback;
	private Spinner spn_seat_qty;
	private Context ctx;
	private String selectedSeatQty;

	public TourBookingDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		ctx = context;
		setContentView(R.layout.dialog_tour_booking);
		
		spn_seat_qty = (Spinner)findViewById(R.id.spn_seat_qty);
		edt_booker_ph = (EditText)findViewById(R.id.edt_booker_ph);
		
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		btn_submit_booking = (Button)findViewById(R.id.btn_submit_booking);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_submit_booking.setOnClickListener(clickListener);
		
		final List<String> seatQty = new ArrayList<String>();
		
		for (int i = 1; i < 46; i++) {
			seatQty.add(String.valueOf(i));
		}
		
		ArrayAdapter<String> adapter = new 
				ArrayAdapter<String>(ctx, android.R.layout.simple_dropdown_item_1line, seatQty);
		spn_seat_qty.setAdapter(adapter);
		
		spn_seat_qty.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedSeatQty = seatQty.get(position);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setTitle(R.string.str_tourbooking_title);
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_cancel) {
				if (mCallback != null) {
					dismiss();
					mCallback.onCancel();
				}
			}
			if (v == btn_submit_booking) {
				if (mCallback != null && checkFields()) {
					dismiss();
					mCallback.onTourBook(selectedSeatQty, edt_booker_ph.getText().toString());
				}
			}
		}
	};
	
	private boolean checkFields() {
		// TODO Auto-generated method stub
		if (edt_booker_ph.getText().length() == 0) {
			edt_booker_ph.setError("Please enter phone no.");
			return false;
		}		
		return true;
	}
	
	public Callback getmCallback() {
		return mCallback;
	}


	public void setmCallback(Callback mCallback) {
		this.mCallback = mCallback;
	}


	public interface Callback{
		void onCancel();
		void onTourBook(String seatQty, String agentPhone);
	}
}
