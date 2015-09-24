package com.ignite.mm.ticketing.custom.listview.adapter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.model.Promotion;
import com.ignite.mm.ticketing.starticket.R;


import android.app.Activity;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PromotionAdapter extends BaseAdapter {
	
	private List<Promotion> listOperator;
	private TextView txtTitle;
	private LayoutInflater mInflater;
	private Activity aty;
	
	public PromotionAdapter(Activity aty,List<Promotion> arg0) {
		super();
		// TODO Auto-generated constructor stub
		this.mInflater= LayoutInflater.from(aty);
		this.aty = aty;
		this.listOperator = arg0;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listOperator.size();
	}

	public Promotion getItem(int index) {
		// TODO Auto-generated method stub
		return listOperator.get(index);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_promotion, null);
			
			holder = new ViewHolder();
			
			holder.txt_operator_promo = (TextView) convertView.findViewById(R.id.txt_operator_promo);
			holder.txt_buy_amount = (TextView)convertView.findViewById(R.id.txt_buy_amount);
			holder.txt_points = (TextView)convertView.findViewById(R.id.txt_points);
			holder.txt_gift_money_amount = (TextView)convertView.findViewById(R.id.txt_gift_money_amount);
			holder.txt_gift_parcel = (TextView)convertView.findViewById(R.id.txt_gift_parcel);
			
			holder.layout_points = (LinearLayout)convertView.findViewById(R.id.layout_points);
			holder.layout_gift_money = (LinearLayout)convertView.findViewById(R.id.layout_gift_money);
			
			holder.txt_plus = (TextView)convertView.findViewById(R.id.txt_plus);
			
			convertView.setTag(holder);
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		if (getItem(position).getOperatorName() == null) {
			holder.txt_operator_promo.setText("မႏၱလာမင္း");
		}else {
			holder.txt_operator_promo.setText(getItem(position).getOperatorName());
		}
				
		//Change (0,000,000) format
		NumberFormat nf = NumberFormat.getInstance();
		String startAmount = nf.format(Integer.valueOf(getItem(position).getStartAmount()));
		String endAmount = nf.format(Integer.valueOf(getItem(position).getEndAmount()));
				
		holder.txt_buy_amount.setText(changeDateString(getItem(position).getStartDate())+"  မွ  "
										+changeDateString(getItem(position).getEndDate())+" ရက္ေန႔ထိ (က်ပ္ "
										+Html.fromHtml("<b>"+startAmount+"</b>")+" မွ က်ပ္ "
										+Html.fromHtml("<b>"+endAmount+"</b>")+") ဖုိး ၀ယ္ယူ လွ်င္ ~");
		
		if (getItem(position).getPoints() != null && !getItem(position).getPoints().equals("")) {
			if (Integer.valueOf(getItem(position).getPoints()) > 0) {
				holder.layout_points.setVisibility(View.VISIBLE);
				holder.txt_points.setText(getItem(position).getPoints());
			}else {
				holder.layout_points.setVisibility(View.GONE);
			}
		}
		
		if (getItem(position).getGiftMoney() != null && !getItem(position).getGiftMoney().equals("")) {
			if (Integer.valueOf(getItem(position).getGiftMoney()) > 0) {
				holder.layout_gift_money.setVisibility(View.VISIBLE);
				holder.txt_gift_money_amount.setText(getItem(position).getGiftMoney());
			}else {
				holder.layout_gift_money.setVisibility(View.GONE);
			}
		}
		
		if (getItem(position).getGiftParcel() != null && !getItem(position).getGiftParcel().equals("")) {
			holder.txt_gift_parcel.setVisibility(View.VISIBLE);
			holder.txt_gift_parcel.setText("***"+getItem(position).getGiftParcel());
		}else {
			holder.txt_gift_parcel.setVisibility(View.GONE);
		}
		
		if (getItem(position).getPoints() != null && getItem(position).getGiftMoney() != null) {
			if (!getItem(position).getPoints().equals("") && !getItem(position).getGiftMoney().equals("")) {
				if (Integer.valueOf(getItem(position).getPoints()) > 0 && Integer.valueOf(getItem(position).getGiftMoney()) > 0) {
					holder.txt_plus.setVisibility(View.VISIBLE);
				}else {
					holder.txt_plus.setVisibility(View.GONE);
				}
			}
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_operator_promo, txt_buy_amount, txt_points, txt_gift_money_amount, txt_gift_parcel, txt_plus;
		LinearLayout layout_points, layout_gift_money;
	}
	
	public static String changeDateString(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DateFormat.format("dd-MMMM-yyyy", StartDate).toString();
	}
}

