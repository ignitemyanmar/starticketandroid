
package com.ignite.mm.ticketing.application;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.Window;
import android.widget.TextView;

import com.ignite.mm.ticketing.agent.callcenter.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

public class NewCalendarDialog extends Dialog{
	//private MaterialDialog dialog;
	protected String selectedDate;
	private Callbacks mCallback;
	public static TextView txt_calendar_title;
	public static TextView selected_date;
	public static MaterialCalendarView calendar;

	public NewCalendarDialog(Context context){
		super(context);
		//View view = View.inflate(context, R.layout.dialog_calender, null);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //before     
		
		setContentView(R.layout.dialog_calendar);
		
		txt_calendar_title = (TextView)findViewById(R.id.txt_calendar_title);
		calendar = (MaterialCalendarView) findViewById(R.id.calendarView);
		//selected_date = (TextView)findViewById(R.id.selected_date);
		
		calendar.setOnDateChangedListener(new OnDateChangedListener() {
			
			public void onDateChanged(@NonNull MaterialCalendarView widget,
					@Nullable CalendarDay date) {
				// TODO Auto-generated method stub
				selectedDate = DateFormat.format("yyyy-MM-dd",date.getDate()).toString();
				if(mCallback != null){
					mCallback.choose(selectedDate);
				}
				return;
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
