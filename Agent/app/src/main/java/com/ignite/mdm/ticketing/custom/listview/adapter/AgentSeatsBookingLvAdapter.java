package com.ignite.mdm.ticketing.custom.listview.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentSeatsBooking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ResourceAsColor") public class AgentSeatsBookingLvAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<AgentSeatsBooking> listItem;
	private Activity aty;
	private Callback mCallback;
	
	public AgentSeatsBookingLvAdapter(Activity aty, List<AgentSeatsBooking> seatList){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = seatList;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public AgentSeatsBooking getItem(int position) {
		return listItem.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
        	convertView = mInflater.inflate(R.layout.list_item_agent_seats_booking, null);
        	
        	holder.txt_status = (TextView)convertView.findViewById(R.id.txt_status);
        	holder.txt_booking_date = (TextView)convertView.findViewById(R.id.txt_booking_date);
        	holder.txt_agent_name = (TextView)convertView.findViewById(R.id.txt_agent_name);
        	holder.txt_trip_operator = (TextView)convertView.findViewById(R.id.txt_trip_operator);
        	holder.txt_trip_date_time_class = (TextView)convertView.findViewById(R.id.txt_trip_date_time_class);
        	holder.txt_seats = (TextView)convertView.findViewById(R.id.txt_seats);
        	holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
        	holder.btn_confirm = (Button)convertView.findViewById(R.id.btn_confirm);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_agent_name.setText(getItem(position).getUser().getName()+" ["
									+getItem(position).getPhone_no()+"]");
		
		holder.txt_booking_date.setText(aty.getResources().getString(R.string.strmm_booking_time)+creatLocalTime(getItem(position).created_at));
		if (getItem(position).getExtra_city() != null) {
			if (getItem(position).getExtra_city().length() > 0) {
				holder.txt_trip_operator.setText(getItem(position).getTrip().getFromName()+"=>"
						+getItem(position).getTrip().getToName()
						+"["+getItem(position).getExtra_city()+"] "
						+" ["+getItem(position).getTrip().getOperator_name()+"]");
			}else {
				holder.txt_trip_operator.setText(getItem(position).getTrip().getFromName()+"=>"
						+getItem(position).getTrip().getToName()
						+" ["+getItem(position).getTrip().getOperator_name()+"]");
			}
		}else {
			holder.txt_trip_operator.setText(getItem(position).getTrip().getFromName()+"=>"
					+getItem(position).getTrip().getToName()
					+" ["+getItem(position).getTrip().getOperator_name()+"]");
		}
		
		
		holder.txt_trip_date_time_class.setText(getItem(position).getDeparture_date()+"  "
				+getItem(position).getTrip().getTime()+" "+getItem(position).getTrip().getClass_name()
				+" ["+getItem(position).getTrip().getPrice()+" Ks]");
		holder.txt_seats.setText(getItem(position).getTotal_seat());
		
		if (getItem(position).getStatus().equals("0")) {
			holder.txt_status.setText("Pending");
			holder.txt_status.setTextColor(aty.getResources().getColor(R.color.blue));
		}
		
		if (getItem(position).getStatus().equals("1")) {
			holder.txt_status.setText("Complete");
			holder.txt_status.setTextColor(aty.getResources().getColor(R.color.green));
		}
		
		if (getItem(position).getStatus().equals("2")) {
			holder.txt_status.setText("Deleted");
			holder.txt_status.setTextColor(aty.getResources().getColor(R.color.golden_brown_dark));
		}
		
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.onDeleteClick(position, getItem(position).getId());
				}
			}
		});
		
		holder.btn_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.onCompleteClick(position, getItem(position).getId());
				}
			}
		});
		
		return convertView;
	}
	
	public Callback getmCallback() {
		return mCallback;
	}

	public void setmCallback(Callback mCallback) {
		this.mCallback = mCallback;
	}

	public interface Callback{
		public void onDeleteClick(Integer pos, String id);
		public void onCompleteClick(Integer pos, String id);
	}
	
	public String creatLocalTime(String strTime) {
		// TODO Auto-generated method stub
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
		    Date convertedDate = new Date();
		    try {
		        convertedDate = dateFormat.parse(strTime);
		    } catch (ParseException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    
		    //Date dNow = new Date( ); // Instantiate a Date object
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(convertedDate);
		    cal.add(Calendar.HOUR, 6);
		    cal.add(Calendar.MINUTE, 30);
		    convertedDate = cal.getTime();
				
		    return dateFormat2.format(convertedDate);
	}
	
	static class ViewHolder {
		TextView txt_booking_date, txt_agent_name, txt_trip_operator, txt_trip_date_time_class, txt_seats, txt_status;
		Button btn_delete, btn_confirm;
	}
}
