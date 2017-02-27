package com.smk.calender.widget;

import java.util.List;

import com.smk.skcalendar.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalenderAdapter extends BaseAdapter
{
    private final Context _context;
    private final List<String> list;
    private TextView gridcell;
	private Display display;
	private Point size; 
    public CalenderAdapter(Activity atx, List<String> list)
    {
        super();
        this._context = atx;
        this.list = list;
        this.display = atx.getWindowManager().getDefaultDisplay();
		this.size = getDisplaySize(display);
       
    }


    public String getItem(int position)
    {
        return list.get(position);
    }

    public int getCount()
    {
        return list.size();
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
    	View row = convertView;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false);
        }

        // Get a reference to the Day gridcell
        gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
        
        // ACCOUNT FOR SPACING

        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];

        // Set the Day GridCell
        gridcell.setText(theday);
        gridcell.getLayoutParams().height = size.y / 12;
        gridcell.setTag(theday + "-" + themonth + "-" + theyear);

         if (day_color[1].equals("NOTTODAY"))
        {
            gridcell.setTextColor(Color.BLACK);
            gridcell.setBackgroundResource(R.drawable.calender_nottoday_cell);
        }
        else if (day_color[1].equals("TODAY"))
        {
            gridcell.setTextColor(Color.WHITE);
            gridcell.setBackgroundResource(R.drawable.calender_today_cell);
        }else{
        	//Leading Day
        	gridcell.setTextColor(Color.LTGRAY);
            gridcell.setBackgroundResource(R.drawable.calender_leadday_cell);
        }
        return row;
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

