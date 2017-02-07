package com.smk.calender.widget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import com.smk.calender.widget.CalenderAdapter;
import com.smk.calender.widget.WeekDaysAdapter;
import com.smk.skcalendar.R;

import android.app.Activity;
import android.app.Dialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class SKCalender extends Dialog implements OnClickListener,OnItemClickListener {
	private Activity atx;
	private Button currentMonth;
    private Button prevMonth;
    private Button nextMonth;
    private GridView calendarView;
    private GridView weekDay;
    private CalenderAdapter adapter;
    private Calendar _calendar;
    private Integer month, year;
    private static final String dateTemplate = "MMMM yyyy";
    public static String selectedDate = null;
    private final String[] weekdays = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int daysInMonth, currentDayOfMonth, currentWeekDay;
    private List<String> list;
    private static int DAY_OFFSET = 1;
	private GridView yearMonths;
	private Boolean monthClicked = false;
	private Boolean yearClicked = false;
	private Integer[] years;
	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 125;
    @SuppressWarnings("deprecation")
	private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());

	public SKCalender(Activity atx ) {
		super(atx);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calender_view);
		this.atx = atx;
		_calendar = Calendar.getInstance(/*Locale.getDefault()*/);
   		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
				 
		weekDay = (GridView)this.findViewById(R.id.grid_week_day);
		weekDay.setAdapter(new WeekDaysAdapter(atx, weekdays));
		
		yearMonths = (GridView)this.findViewById(R.id.grid_month_year);
		yearMonths.setAdapter(new MonthAdapter(atx, months));
		yearMonths.setOnItemClickListener(this);
					 
		prevMonth = (Button) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);
		
		currentMonth = (Button) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
		currentMonth.setOnClickListener(this);
		
		
		nextMonth = (Button) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);
		
		calendarView = (GridView) this.findViewById(R.id.calendar);
		
		 // Initialised
		printMonth(month, year);
		adapter = new CalenderAdapter(atx, list);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		calendarView.setOnItemClickListener(this);
		
		calendarView.setOnTouchListener(touchListener);
		yearMonths.setOnTouchListener(touchListener);
	}
	private View.OnTouchListener touchListener = new View.OnTouchListener() {
		
		public boolean onTouch(View v, MotionEvent event) {
			detector.onTouchEvent(event);
			
            return false;
		}
	};
	private Callbacks mCallbacks;
	public void onClick(View v){
    	
        if (v == prevMonth){
        	if(yearClicked && monthClicked){
        		
        		setGridCellAdapterToYears(year -= 12); 
        		
        	}else if(!yearClicked && monthClicked){
        		
        		setGridCellAdapterToMonths(--year);
        		
        	}else if(!yearClicked && !monthClicked){
	            if (month <= 1)
	                {
	                    month = 12;
	                    year--;
	                }
	            else
	                {
	                    month--;
	                }
	            setGridCellAdapterToDate(month, year);
        	}
        }
        if (v == nextMonth){
        	if(yearClicked && monthClicked){
        		
        		setGridCellAdapterToYears(year += 12);   
        		
        	}else if(!yearClicked && monthClicked){

        		setGridCellAdapterToMonths(++year);
        		
        	}else if(!yearClicked && !monthClicked){
        	
	            if (month > 11)
	                {
	                    month = 1;
	                    year++;
	                }
	            else
	                {
	                    month++;
	                }
	            setGridCellAdapterToDate(month, year);
        	}
        }
        if (v == currentMonth){

        	yearMonths.setVisibility(GridView.VISIBLE);
        	weekDay.setVisibility(GridView.GONE);
        	calendarView.setVisibility(GridView.GONE);
        	
        	if(!yearClicked && monthClicked){
        		
        		setGridCellAdapterToYears(year);
        		
        	}else if(!yearClicked && !monthClicked){
        		
        		setGridCellAdapterToMonths(year);
        		
        	}
        }

    }
	/**
	 * @param year
	 */
	public void setGridCellAdapterToMonths(Integer year){
		currentMonth.setText(year.toString());
		yearMonths.setAdapter(new MonthAdapter(atx, months));
		monthClicked = true;
	}
	/**
	 * @param year
	 */
	public void setGridCellAdapterToYears(int year){
		years = new Integer[12];
		int y = 0;
		for(Integer i = (year - 6); i < (year + 6); i++){
			years[y] = i;
			y++;
		}
		yearClicked = true;
		currentMonth.setText(years[0].toString() +" - "+ years[11].toString());
		yearMonths.setAdapter(new YearsAdapter(atx, years));
	}
    /**
    *
    * @param month
    * @param year
    */

	public void setGridCellAdapterToDate(int month, int year)
	{
	   printMonth(month, year);
       adapter = new CalenderAdapter(atx, list);
       currentMonth.setText(months[month-1]+" "+year);
       adapter.notifyDataSetChanged();
       calendarView.setAdapter(adapter);
	}
	
	public void onItemClick(AdapterView<?> av, View v, int position, long arg3) {
		// TODO Auto-generated method stub
		if( av == calendarView ){
			if(mCallbacks != null){
				String[] dateString = list.get(position).toString().split("-");
				mCallbacks.onChooseDate(dateString[0]+"-"+dateString[2]+"-"+dateString[3]);
			}
			//Toast.makeText(atx, "Hello Calender Grid Cell : " + list.get(position).toString(), Toast.LENGTH_LONG).show();
		}
		if( av == yearMonths ){
			
			if(!yearClicked && monthClicked){
				//Toast.makeText(atx, "Hello Years & Months Grid Cell : " + months[position], Toast.LENGTH_LONG).show();
				month = position + 1;
				year =Integer.parseInt(currentMonth.getText().toString());
				setGridCellAdapterToDate(month , year);
				yearMonths.setVisibility(GridView.GONE);
	        	weekDay.setVisibility(GridView.VISIBLE);
	        	calendarView.setVisibility(GridView.VISIBLE);
	        	monthClicked = false;
				
			}else if(yearClicked && monthClicked){
				//Toast.makeText(atx, "Hello Years & Months Grid Cell : " + years[position], Toast.LENGTH_LONG).show();
				setGridCellAdapterToMonths(years[position]);
				yearClicked = false;
			}
			
		}
	}
	
	private String getMonthAsString(int i)
    {
        return months[i];
    }

    private int getNumberOfDaysOfMonth(int i)
    {
        return daysOfMonth[i];
    }
    public int getCurrentDayOfMonth(){
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth){
        this.currentDayOfMonth = currentDayOfMonth;
    }
    
    public void setCurrentWeekDay(int currentWeekDay){
        this.currentWeekDay = currentWeekDay;
    }
    	
    public int getCurrentWeekDay(){
        return currentWeekDay;
    }
	/**
     * Prints Month
     *
     * @param mm
     * @param yy
     */
    private void printMonth(int mm, int yy)
    {
        // The number of days to leave blank at
        // the start of this month.
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;
        this.list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
        
        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
        

        if (currentMonth == 11)
        {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        }
        else if (currentMonth == 0)
        {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        }
        else
        {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        // Compute how much to leave before before the first day of the
        // month.
        // getDay() returns 0 for Sunday.
        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1)
        {
            ++daysInMonth;
        }

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++)
        {
            list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++)
        {
            Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
            if (i == getCurrentDayOfMonth())
            {
                list.add(String.valueOf(i) + "-TODAY" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            }
            else
            {
                list.add(String.valueOf(i) + "-NOTTODAY" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            }
        }

        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++)
        {
            list.add(String.valueOf(i + 1) + "-LEADDAY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }
    
    class SwipeGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    // right to left swipe
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    	calendarView.startAnimation(startAnimation());
                    	if(yearClicked && monthClicked){
                    		
                    		setGridCellAdapterToYears(year += 12);   
                    		
                    	}else if(!yearClicked && monthClicked){

                    		setGridCellAdapterToMonths(++year);
                    		
                    	}else if(!yearClicked && !monthClicked){
                    	
            	            if (month > 11)
            	                {
            	                    month = 1;
            	                    year++;
            	                }
            	            else
            	                {
            	                    month++;
            	                }
            	            setGridCellAdapterToDate(month, year);
                    	}    
                    	return true;
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    	calendarView.startAnimation(endAnimation());
                    	if(yearClicked && monthClicked){
                    		
                    		setGridCellAdapterToYears(year -= 12); 
                    		
                    	}else if(!yearClicked && monthClicked){
                    		
                    		setGridCellAdapterToMonths(--year);
                    		
                    	}else if(!yearClicked && !monthClicked){
            	            if (month <= 1)
            	                {
            	                    month = 12;
            	                    year--;
            	                }
            	            else
            	                {
            	                    month--;
            	                }
            	            setGridCellAdapterToDate(month, year);
                    	}    
                        return true;
                    }
                        
                } catch (Exception e) {
                        e.printStackTrace();
                }

                return false;
        }
	}
    
    private Animation startAnimation(){
    	Animation anim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		anim.setInterpolator(new AccelerateInterpolator());
		anim.reset();
		return anim;
    }
    private Animation endAnimation(){
    	Animation anim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1.0f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		anim.setInterpolator(new AccelerateInterpolator());
		anim.reset();
		return anim;
    }
    
    public void setCallbacks(Callbacks listener) {
        mCallbacks = listener;
    }
    public static interface Callbacks {
        public void onChooseDate(String chooseDate);
    }


}
