package com.smk.calender.widget;

import java.util.Calendar;

import com.smk.skcalendar.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class YearsAdapter extends BaseAdapter{
	private Activity mActivity;
	private final Integer[] years;
	private final DateFormat dateFormatter = new DateFormat();
	private Object _calendar;
	private Display display;
	private Point size;
 
	public YearsAdapter(Activity mActivity, Integer[] years){
		this.mActivity = mActivity;
		this.years = years;
		_calendar = Calendar.getInstance();
		this.display = mActivity.getWindowManager().getDefaultDisplay();
		this.size = getDisplaySize(display);
	}
 
	@SuppressWarnings("static-access")
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(mActivity);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.calender_month, null);
			// set value into textview
			TextView txt_year_months = (TextView) gridView.findViewById(R.id.txt_year_month);
			txt_year_months.setText(years[position].toString());
			txt_year_months.setTag(years[position].toString());
			txt_year_months.getLayoutParams().height = size.y / 8;
			Integer year = years[position];
			String currentYear = dateFormatter .format("yyyy", ((Calendar) _calendar).getTime()).toString();
			if (currentYear.equals(year.toString())) {
				txt_year_months.setTextColor(Color.rgb(45, 165, 218));
			}
 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	public int getCount() {
		return years.length;
	}
 
	public Object getItem(int position) {
		return null;
	}
 
	public long getItemId(int position) {
		return 0;
	}
	@SuppressWarnings("deprecation")
	private Point getDisplaySize(final Display display) {
	    final Point point = new Point();
	    try {
	        display.getSize(point);
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        point.x = display.getWidth();
	        point.y = display.getHeight();
	    }
	    return point;
	}
}
