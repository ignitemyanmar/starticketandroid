package com.ignite.mm.ticketing.application;

import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ignite.mm.ticketing.starticket.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.timessquare.CalendarPickerView;

public class CalendarDialog extends Dialog{
	//private MaterialDialog dialog;
	protected String selectedDate;
	private Callbacks mCallback;

	public CalendarDialog(Context context){
		super(context);
		//View view = View.inflate(context, R.layout.dialog_calender, null);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //before     
		
		setContentView(R.layout.dialog_calendar);
		
		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);
		
		final Calendar lastYear = Calendar.getInstance();
	    lastYear.add(Calendar.YEAR, -1);

		CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
		Date today = new Date();
		calendar.init(lastYear.getTime(), nextYear.getTime())
		    .withSelectedDate(today);
		calendar.setCellClickInterceptor(new CalendarPickerView.CellClickInterceptor() {
			
			public boolean onCellClicked(Date date) {
				// TODO Auto-generated method stub
				selectedDate = DateFormat.format("yyyy-MM-dd",date).toString();
				if(mCallback != null){
					mCallback.choose(selectedDate);
				}
				//dialog.dismiss();
				return false;
			}
		});
		
		/*dialog = new MaterialDialog.Builder(context)
        .title("Choose Depature Date")
        .customView(view, true)
        .show();*/
		
		/*DialogPlus dialog = DialogPlus.newDialog(context)
			    .setContentHolder(new ViewHolder(view))
			    .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
			    .setGravity(Gravity.BOTTOM)
			    .create();
			dialog.show();*/
		
	}
	
	public void setOnCallbacksListener(Callbacks callbacks){
		this.mCallback = callbacks;
	}
	
	public interface Callbacks{
		void choose(String selectedDate);
	}
}
