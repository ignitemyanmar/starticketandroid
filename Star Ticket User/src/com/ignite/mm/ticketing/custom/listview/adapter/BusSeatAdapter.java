package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ignite.mm.ticketing.sqlite.database.model.OperatorGroupUser;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;
import com.ignite.mm.ticketing.starticket.BusSelectSeatActivity;
import com.ignite.mm.ticketing.starticket.R;

public class BusSeatAdapter extends BaseAdapter{
	 private final Context _context;
	    private final List<Seat_list> list;
		private Callbacks mCallbacks;
		//private String userRole;
	    
	  	
	    public BusSeatAdapter(Activity atx, List<Seat_list> seat_list, String userRole)
	    {
	        super();
	        this._context = atx;
	        this.list = seat_list;
	       // this.userRole = userRole;
	       
	    }
		public int getCount() {
			// TODO Auto-generated method stub
			return  list.size();
		}
		
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null)
	        {
	            convertView =LayoutInflater.from(_context).inflate(R.layout.list_item_seat, null);
	            holder = new ViewHolder();
	            holder.seat = (CheckBox) convertView.findViewById(R.id.chk_seat);
	            holder.seatNo = (TextView) convertView.findViewById(R.id.txt_seat_no);
	            holder.layout_customer_info = (RelativeLayout) convertView.findViewById(R.id.layout_customer_info);
	            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
	            holder.txt_phone = (TextView) convertView.findViewById(R.id.txt_phone);
	            holder.txt_nrc = (TextView) convertView.findViewById(R.id.txt_nrc);
	            holder.txt_ticket_no = (TextView) convertView.findViewById(R.id.txt_ticket_no);
	            holder.txt_agent = (TextView) convertView.findViewById(R.id.txt_agent);
	            holder.txt_seating_no = (TextView) convertView.findViewById(R.id.txt_seating_no);
	            holder.cover = (View) convertView.findViewById(R.id.view_cover);
	            holder.seat_no = (TextView)convertView.findViewById(R.id.seat_no);
	            holder.seat_layout = (LinearLayout)convertView.findViewById(R.id.seat_layout);
	            convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			Log.i("", "Seat No: "+list.get(position).getSeat_no()+", Operator Group Color: "+list.get(position).getOperatorgroup_color());
			
/*			switch(list.get(position).getOperatorgroup_color()){
			
				case 1:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_1); 
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_1_1); 
					break;
				case 2:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_2); //Sale 
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_2_1); //Booking
					break;
				case 3:

					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_3);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_3_1);
					break;
				case 4: //Online Sale
					
					Log.i("", "Online Sale .. Enter here!!!!!!!!!!!");
					
					if(list.get(position).getBooking() == 0)
					{
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_4);
					}
					else
					{
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_4_1);
					}
					
					//Get Selected Seats of Online Sales
	            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
	            	
					holder.seat_no.setVisibility(View.INVISIBLE);
	            	
	            	holder.seatNo.setText(list.get(position).getSeat_no());
	            	holder.seat.setEnabled(true);
	            	holder.seat.setTag(position);	
	            	
            		holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if(isChecked){
								//If checked the seat.
								String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
								if(BusSelectSeatActivity.SelectedSeat.length() > 0){
									boolean isExisted = false;
									for (int i = 0; i < seleted.length; i++) {
										if(seleted[i].equals(buttonView.getTag().toString())){
											isExisted = true;
										}
									}
									
									if(!isExisted){
										BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
									}
								}else{
									BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								//If unchecked the seat.
								String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
								if(!BusSelectSeatActivity.SelectedSeat.isEmpty()){
									BusSelectSeatActivity.SelectedSeat = "";
									for (int i = 0; i < seleted.length; i++) {
										if(!seleted[i].equals(buttonView.getTag().toString())){
											BusSelectSeatActivity.SelectedSeat += seleted[i]+",";
										}
									}
									
								}
							}
						}

					});
					break;
				default:
					if(list.get(position).getBooking() == 0)
					{
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0);
					}
					else 
					{
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0_1);
					}					
					
					holder.layout_customer_info.setVisibility(View.INVISIBLE);

					//Gray color (Not allow to click)
					holder.cover.setVisibility(View.VISIBLE);
					
					holder.seat_no.setVisibility(View.VISIBLE);
					holder.seat_no.setText(list.get(position).getSeat_no());
					
					//holder.layout_customer_info.setTag(list.get(position));
	            	holder.layout_customer_info.setOnLongClickListener(new OnLongClickListener() {
						
						public boolean onLongClick(View v) {
							// TODO Auto-generated method stub
							if(mCallbacks != null){
								Seat_list list = (Seat_list) v.getTag();
								mCallbacks.onClickEdit(list);
							}
							return false;
						}
					});
			}*/
			
			
			//Check Online Seats
			if (list.get(position).getOperatorgroup_color() == 1) {
				
				if(list.get(position).getBooking() == 0)
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_1); 
				else
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_1_1); 
				
			}else if (list.get(position).getOperatorgroup_color() == 2) {
				
				if(list.get(position).getBooking() == 0)
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_2); //Sale 
				else
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_2_1); //Booking
				
			}else if (list.get(position).getOperatorgroup_color() == 3) {
				
				if(list.get(position).getBooking() == 0)
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_3);
				else
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_3_1);
				
			}else if (list.get(position).getOperatorgroup_color() >= 4) {	//Online Sale
				
				Log.i("", "Online Sale .. Enter here!!!!!!!!!!!");
				
				if(list.get(position).getBooking() == 0)
				{
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_4);
				}
				else
				{
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_4_1);
				}
				
				//Get Selected Seats of Online Sales
	        	holder.layout_customer_info.setVisibility(View.INVISIBLE);
	        	
				holder.seat_no.setVisibility(View.INVISIBLE);
	        	
	        	holder.seatNo.setText(list.get(position).getSeat_no());
	        	holder.seat.setEnabled(true);
	        	holder.seat.setTag(position);	
	        	
	        	/*for(int i=0; i<jsonArray.length(); i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					if (i == jsonArray.length() - 1) {
						SeatLists += obj.getString("seat_no");
					}else {
						SeatLists += obj.getString("seat_no")+",";
					}
				}*/
	        	
	    		holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						//If checked the seat,
						//If no seats is selected yet, put in SelectedSeat
						//If any selected seats already, plus in SelectedSeats
						if(isChecked){
							//If checked the seat.
							String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
							
							if(BusSelectSeatActivity.SelectedSeat.length() > 0){
								boolean isExisted = false;
								int i = 0;
								for (i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}
						}else{
							//If unchecked the seat.
							//If any selected seats already, clear SelectedSeat
							//If unchecked seats is not included in selected, do not add. 
							String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
							if(!BusSelectSeatActivity.SelectedSeat.isEmpty()){
								BusSelectSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										BusSelectSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
							}
						}
					}
				});
			}else {			//Default
				
				if(list.get(position).getBooking() == 0)
				{
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_0);
				}
				else 
				{
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_0_1);
				}					
				
				holder.layout_customer_info.setVisibility(View.INVISIBLE);

				//Gray color (Not allow to click)
				holder.cover.setVisibility(View.VISIBLE);
				
				holder.seat_no.setVisibility(View.VISIBLE);
				holder.seat_no.setText(list.get(position).getSeat_no());
			}
			
			//Already Purchase or Booking
			if(list.get(position).getStatus() == 2){
				
				Log.i("", "Booking and Purchased!!!!!");
				holder.seat.setEnabled(false);
            	if(list.get(position).getCustomerInfo() != null){
            		holder.layout_customer_info.setVisibility(View.VISIBLE);
            		holder.txt_name.setText(list.get(position).getCustomerInfo().getName());
            		holder.txt_phone.setText(list.get(position).getCustomerInfo().getPhone());
            		holder.txt_nrc.setText(list.get(position).getCustomerInfo().getNrcNo());
            		holder.txt_ticket_no.setText(list.get(position).getCustomerInfo().getTicketNo());
            		holder.txt_agent.setText(list.get(position).getCustomerInfo().getAgentName());
            		holder.txt_seating_no.setText(list.get(position).getSeat_no());
            		
            	}else{
            		holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	}
            	holder.layout_customer_info.setTag(list.get(position));
            	
            	/*holder.layout_customer_info.setOnLongClickListener(new OnLongClickListener() {
					
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						if(mCallbacks != null){
							Seat_list list = (Seat_list) v.getTag();
							mCallbacks.onClickEdit(list);
						}
						return false;
					}
				});*/
            	
            	/*if (Integer.valueOf(userRole) > 3) {
            		holder.layout_customer_info.setOnLongClickListener(new OnLongClickListener() {
    					
    					public boolean onLongClick(View v) {
    						// TODO Auto-generated method stub
    						if(mCallbacks != null){
    							Seat_list list = (Seat_list) v.getTag();
    							mCallbacks.onClickEdit(list);
    						}
    						return false;
    					}
    				});
				}else{
					//do nothing
				}*/
            }
            
            if(list.get(position).getStatus() == 3){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seat.setChecked(true);
            	holder.seatNo.setText(list.get(position).getSeat_no());
            }
            
            if(list.get(position).getStatus() == 1){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seatNo.setText(list.get(position).getSeat_no());
            	holder.seat.setEnabled(true);
            	holder.seat.setTag(position);
            	
/*            	holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							//If checked the seat.
							String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
							if(!BusSelectSeatActivity.SelectedSeat.isEmpty()){
								boolean isExisted = false;
								for (int i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								BusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}
							
							
						}else{
							//If unchecked the seat.
							String[] seleted = BusSelectSeatActivity.SelectedSeat.split(",");
							if(!BusSelectSeatActivity.SelectedSeat.isEmpty()){
								BusSelectSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										BusSelectSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
								
							}
						}
						
					}
				});*/
            	
            	/*if (Integer.valueOf(this.userRole) > 3) {

				}else{
					//Do nothing
				}*/
            }
            
            if(list.get(position).getStatus() == 0){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seat.setVisibility(View.INVISIBLE);
            	holder.seatNo.setVisibility(View.INVISIBLE);
            	holder.seat_no.setVisibility(View.INVISIBLE);
            	holder.cover.setVisibility(View.INVISIBLE);
            }
            
	        return convertView;
		}
		
		public void setCallbacks(Callbacks callbacks){
			this.mCallbacks = callbacks;
		}
		public interface Callbacks{
			public void onClickEdit(Seat_list list);
		}
		
/*		private int getColor(int Id){
			int color = 0;
			for(OperatorGroupUser groupUser: BusSelectSeatActivity.groupUser){
				Log.i("", "Group User's Id: "+groupUser.getId()+", Seat Plan's OperatorGroupId: "+Id);
				if(groupUser.getId() == Id){
					color = groupUser.getColor();
					break;
				}
			}
			
			Log.i("", "OperatorGroupUser's Color status: "+color);
			
			return color;
		}*/
		
		 static class ViewHolder {
				CheckBox seat;
				LinearLayout seat_layout;
				RelativeLayout layout_customer_info;
				TextView txt_name;
				TextView txt_phone;
				TextView txt_nrc;
				TextView txt_ticket_no;
				TextView txt_agent;
				TextView txt_seating_no;
				View cover;
				TextView seatNo, seat_no;
			}
}
