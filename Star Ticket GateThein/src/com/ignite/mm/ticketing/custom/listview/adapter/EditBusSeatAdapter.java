package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ignite.mm.ticketing.gatethein.EditBusSelectSeatActivity;
import com.ignite.mm.ticketing.gatethein.R;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;

public class EditBusSeatAdapter extends BaseAdapter{
	 private final Context _context;
	    private final List<Seat_list> list;
		private Callbacks mCallbacks;
		private String userRole;
	  	    
	    public EditBusSeatAdapter(Activity atx, List<Seat_list> seat_list, String userRole)
	    {
	        super();
	        this._context = atx;
	        this.list = seat_list;
	        this.userRole = userRole;
	    }
	    
		public int getCount() {
			// TODO Auto-generated method stub
			return  list.size();
		}
		
		public Seat_list getItem(int position) {
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
	            holder.txt_check_sale = (TextView) convertView.findViewById(R.id.txt_check_sale);
	            holder.txt_close_agent = (TextView) convertView.findViewById(R.id.txt_close_agent);
	            holder.txt_seating_no = (TextView) convertView.findViewById(R.id.txt_seating_no);
	            //holder.cover = (View) convertView.findViewById(R.id.v_cover);
	            convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			switch(list.get(position).getOperatorgroup_color()){
				case 1:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_1);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_1_1);//Orange
					break;
				case 2:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_2);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_2_1);//cyan(sein pyar)
					break;
				case 3:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_3);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_3_1); //yellow // Star Ticket (Khone Pine)
					break;
				case 4:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_4);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_4_1);//maginto(kha yan)
					break;
				case 5:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0_2);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0_1);//Blue (Free seat/Unchecked)
					break;
				default:
					if(list.get(position).getBooking() == 0)
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0);
					else
						holder.seat.setButtonDrawable(R.drawable.rdo_shape_0_1);//Blue (Free seat/Unchecked)
			}
			
			
			//Already Purchase or Booking
			if(list.get(position).getStatus() == 2){
				
				holder.seatNo.setVisibility(View.INVISIBLE);
				
				if(list.get(position).getBooking() == 0)
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_5);
				else
					holder.seat.setButtonDrawable(R.drawable.rdo_shape_5_1);
				
				//Allow to click
				//holder.seat.setEnabled(true);
				
            	if(list.get(position).getCustomerInfo() != null){
            		holder.layout_customer_info.setVisibility(View.VISIBLE);
            		holder.txt_name.setText(list.get(position).getCustomerInfo().getName());
            		holder.txt_phone.setText(list.get(position).getCustomerInfo().getPhone());
            		holder.txt_nrc.setText(list.get(position).getCustomerInfo().getNrcNo());
            		holder.txt_ticket_no.setText(list.get(position).getCustomerInfo().getTicketNo());
            		
            		holder.txt_agent.setText(list.get(position).getCustomerInfo().getAgentName());
            		
            		
            		if (list.get(position).getCustomerInfo().getOwner() != null) {
            			if (list.get(position).getCustomerInfo().getOwner() == 1) {
	            			holder.txt_agent.setTextColor(_context.getResources().getColor(R.color.m_green));
						}else {
							holder.txt_agent.setTextColor(_context.getResources().getColor(R.color.white));
						}
					}
            		
            		holder.txt_seating_no.setText(list.get(position).getSeat_no());
            		//Check Remark
        			if(list.get(position).getRemark_type() != 0 || list.get(position).getDiscount() > 0 || list.get(position).getFree_ticket() > 0 ){
        				
        				if (list.get(position).getRemark_type() == 1) {
        					holder.txt_seating_no.setBackgroundResource(R.color.m_violet);
						}else {
							holder.txt_seating_no.setBackgroundResource(R.color.orange2);
						}
        			}
            	}else{
            		holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	}
            	
            	
				//holder.layout_customer_info.setVisibility(View.INVISIBLE);
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
            }
			
			
/*			if (userRole != null) {
				if (userRole.equals("7")) {

				}
			}*/
			
			//If Check Sale status 1, show test (sit pyi)
			if (list.get(position).getSalecheck() != null) {
				
				Log.i("", "Sale Check Status: "+list.get(position).getSalecheck());
				
				if (list.get(position).getSalecheck().equals("1")) {
	        		holder.txt_check_sale.setText(_context.getResources().getString(R.string.str_check_sale));
				}else {
					holder.txt_check_sale.setText("");
				}
			}else {
				Log.i("", "Sale Check Status: "+list.get(position).getSalecheck());
			}
			
			
			//If star ticket sale
            if(list.get(position).getStatus() == 3){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seat.setChecked(true);
            	holder.seatNo.setText(list.get(position).getSeat_no());
            }
            
            if(list.get(position).getStatus() == 1 || list.get(position).getStatus() == 2){
            	if(list.get(position).getStatus() == 1)
            		holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	
            	holder.seatNo.setText(list.get(position).getSeat_no());
            	holder.txt_close_agent.setText(list.get(position).getOperatorgroup_name());
            	
            	holder.seat.setEnabled(true);
            	
            	holder.seat.setTag(position);
            	holder.seat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							//If checked the seat.
							String[] seleted = EditBusSelectSeatActivity.SelectedSeat.split(",");
							if(!EditBusSelectSeatActivity.SelectedSeat.isEmpty()){
								boolean isExisted = false;
								for (int i = 0; i < seleted.length; i++) {
									if(seleted[i].equals(buttonView.getTag().toString())){
										isExisted = true;
									}
								}
								
								if(!isExisted){
									EditBusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
								}
							}else{
								EditBusSelectSeatActivity.SelectedSeat += buttonView.getTag()+",";
							}
							
							
						}else{
							//If unchecked the seat.
							String[] seleted = EditBusSelectSeatActivity.SelectedSeat.split(",");
							if(!EditBusSelectSeatActivity.SelectedSeat.isEmpty()){
								EditBusSelectSeatActivity.SelectedSeat = "";
								for (int i = 0; i < seleted.length; i++) {
									if(!seleted[i].equals(buttonView.getTag().toString())){
										EditBusSelectSeatActivity.SelectedSeat += seleted[i]+",";
									}
								}
								
							}
						}
						
					}
				});
            }
            
            if(list.get(position).getOperatorgroup_color() == 5){
            	
            	if(list.get(position).getStatus() != 2){
            		holder.layout_customer_info.setVisibility(View.INVISIBLE);
                	holder.seatNo.setText(list.get(position).getSeat_no());
            	}
            	
            	
            }
            
            if(list.get(position).getStatus() == 0){
            	holder.layout_customer_info.setVisibility(View.INVISIBLE);
            	holder.seat.setVisibility(View.INVISIBLE);
            	holder.seatNo.setVisibility(View.INVISIBLE);
            }
            
	        return convertView;
		}
		
		public void setCallbacks(Callbacks callbacks){
			this.mCallbacks = callbacks;
		}
		public interface Callbacks{
			public void onClickEdit(Seat_list list);
		}
		
		static class ViewHolder {
				CheckBox seat;
				RelativeLayout layout_customer_info;
				TextView txt_name;
				TextView txt_phone;
				TextView txt_nrc;
				TextView txt_ticket_no;
				TextView txt_agent;
				TextView txt_close_agent;
				TextView txt_seating_no;
				TextView txt_check_sale;
				View cover;
				TextView seatNo;
		}
}
