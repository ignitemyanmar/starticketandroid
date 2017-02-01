package com.ignite.mdm.ticketing.custom.listview.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ignite.mdm.ticketing.agent.callcenter.PDFBusActivity;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.sqlite.database.model.ThreeDaySale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThreeDaySalesLvAdapter extends BaseAdapter {

  private LayoutInflater mInflater;
  private List<ThreeDaySale> listItem;
  private Activity aty;

  public ThreeDaySalesLvAdapter(Activity aty, List<ThreeDaySale> _list) {
    this.aty = aty;
    mInflater = LayoutInflater.from(aty);
    listItem = _list;
  }

  public int getCount() {
    // TODO Auto-generated method stub
    return listItem.size();
  }

  public ThreeDaySale getItem(int position) {
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

      convertView = mInflater.inflate(R.layout.item_outstanding_bookings, null);
      //

      holder.date = (TextView) convertView.findViewById(R.id.date);
      holder.place = (TextView) convertView.findViewById(R.id.place);
      holder.day = (TextView) convertView.findViewById(R.id.day);
      holder.day_qualifier = (TextView) convertView.findViewById(R.id.day_qualifier);
      holder.buy_date = (TextView) convertView.findViewById(R.id.buy_date);
      holder.ref = (TextView) convertView.findViewById(R.id.ref);
      holder.description = (TextView) convertView.findViewById(R.id.description);

      //holder.txt_booking_user = (TextView)convertView.findViewById(R.id.txt_booking_user);
      //holder.txt_seller_name = (TextView)convertView.findViewById(R.id.txt_seller_name);
      //holder.txt_sale_date = (TextView) convertView.findViewById(R.id.txt_sale_date);
      //holder.txt_customer_name = (TextView) convertView.findViewById(R.id.txt_customer_name);
      //holder.txt_trip_operator = (TextView) convertView.findViewById(R.id.txt_trip_operator);
      //holder.txt_trip_date_time_class = (TextView) convertView.findViewById(R.id.txt_trip_date_time_class);
      //holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seats);
      //holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
      //holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
      //holder.btn_print = (Button) convertView.findViewById(R.id.btn_print);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    //08-01-2017
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date convertedDate;
    try {

      convertedDate = dateFormat.parse(getItem(position).getDepartureDate());
      SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
      Calendar cal = Calendar.getInstance();

      holder.date.setText(
          format2.format(convertedDate).split("-")[1] + " " + format2.format(convertedDate)
              .split("-")[0]);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String from = getItem(position).getTrip().split("=>")[0].trim();
    from = from.substring(from.indexOf("(") + 1);
    from = from.substring(0, from.indexOf(")"));

    String to = getItem(position).getTrip().split("=>")[1].trim();
    to = to.substring(to.indexOf("(") + 1);
    to = to.substring(0, to.indexOf(")"));

    holder.place.setText(from + " - " + to);
    holder.day.setText(getItem(position).getTime().split(" ")[0]);

    if (getItem(position).getTime().split(" ")[1].equalsIgnoreCase("PM")) {
      holder.day_qualifier.setText("ညေန");
    } else {
      holder.day_qualifier.setText("မနက္");
    }
    holder.buy_date.setText("ဝယ္ယူသည့္ေန႕ - " + getItem(position).getDate());
    holder.ref.setText(getItem(position).getTicketNo());
    holder.description.setText(getItem(position).getCustomerName()
        + "|"
        + getItem(position).getTicketQty()
        + "seats"
        + "|Total - "
        + getItem(position).getPrice());
    //if (getItem(position).getBookinguser() != null) {
    //	holder.txt_booking_user.setText(getItem(position).getBookinguser().getName());
    //}

    //holder.txt_seller_name.setText(getItem(position).getSeller().getName());
    //holder.txt_sale_date.setText(aty.getResources().getString(R.string.strmm_sale_date)+" : "+getItem(position).getDate());
    //holder.txt_customer_name.setText(getItem(position).getCustomerName()+" ["+getItem(position).getCustomerPhone()+"]");
    //holder.txt_trip_operator.setText(getItem(position).getTrip()+" ["+getItem(position).getOperator()+"]");
    //holder.txt_trip_date_time_class.setText(getItem(position).getDepartureDate()+" - "
    //		+getItem(position).getTime()+" "+getItem(position).getClass_()+" ["+getItem(position).getPrice()+" Ks]");
    //holder.txt_seats.setText(getItem(position).getSeatNo());
    ////holder.txt_price.setText("Price :  "+getItem(position).getPrice()+" Ks");
    //holder.txt_amount.setText(getItem(position).getTotalAmount()+" Ks");

    convertView.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent showVoucher = new Intent(aty.getApplication(), PDFBusActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("from_intent", "from_threeday_sales");
        bundle.putString("Operator_Name", getItem(position).getOperator());
        bundle.putString("from_to", getItem(position).getTrip());
        bundle.putString("date", getItem(position).getDepartureDate());
        bundle.putString("time", getItem(position).getTime());
        bundle.putString("classes", getItem(position).getClass_());
        bundle.putString("selected_seat", getItem(position).getSeatNo());
        bundle.putString("ticket_price", getItem(position).getPrice());
        bundle.putString("sale_order_no", getItem(position).getOrderId());
        bundle.putString("ConfirmDate", getItem(position).getDate());
        bundle.putString("ConfirmTime", "");
        bundle.putString("BuyerName", getItem(position).getCustomerName());
        bundle.putString("BuyerPhone", getItem(position).getCustomerPhone());
        bundle.putString("BuyerNRC", getItem(position).getCustomerNrc());
        bundle.putString("TicketNo", getItem(position).getTicketNo());
        bundle.putString("SeatCount", String.valueOf(getItem(position).getTicketQty()));
        bundle.putString("total_amount", getItem(position).getTotalAmount());
        bundle.putString("Passengers", getItem(position).getPassengers());
        bundle.putString("operatorPhone", getItem(position).getOperator_phone());
        bundle.putString("random_tickets", getItem(position).getTicketNo());
        showVoucher.putExtras(bundle);
        aty.startActivity(showVoucher);
      }
    });

    return convertView;
  }

  static class ViewHolder {
    TextView date, place, day, day_qualifier, buy_date, ref, description;
    //TextView txt_sale_date, txt_customer_name, txt_trip_operator, txt_trip_date_time_class, txt_seats, txt_price, txt_amount;
    //TextView txt_seller_name, txt_booking_user;
    //Button btn_print;
  }
}
