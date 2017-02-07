package com.ignite.mdm.ticketing.custom.listview.adapter;

/**
 * Created by user on 2/6/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ignite.mdm.ticketing.agent.callcenter.PDFBusActivity;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ItemOutstandingBookingsBinding;
import com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewSalesAdapter extends RecyclerView.Adapter<NewSalesAdapter.ViewHolder> {

  private LayoutInflater mInflater;
  private List<SoldTicketList> listItem = new ArrayList<>();
  private Activity aty;

  public NewSalesAdapter() {
  }

  public void replaceList(List<SoldTicketList> list) {
    this.listItem = list;
    notifyDataSetChanged();
  }

  public void clear() {
    this.listItem.clear();
    notifyDataSetChanged();
  }


  public SoldTicketList getItem(int position) {
    return listItem.get(position);
  }


  @Override public int getItemCount() {
    return listItem.size();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outstanding_bookings,parent,false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date convertedDate;
    try {

      convertedDate = dateFormat.parse(getItem(position).getDepartureDate());
      SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
      Calendar cal = Calendar.getInstance();

      holder.binding.date.setText(
          format2.format(convertedDate).split("-")[1] + " " + format2.format(convertedDate)
              .split("-")[0]);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String from = getItem(position).getFrom();
    from = from.substring(from.indexOf("(") + 1);
    from = from.substring(0, from.indexOf(")"));

    String to = getItem(position).getTo();
    to = to.substring(to.indexOf("(") + 1);
    to = to.substring(0, to.indexOf(")"));

    holder.binding.place.setText(from + " - " + to);
    holder.binding.day.setText(getItem(position).getTime().split(" ")[0]);

    if (getItem(position).getTime().split(" ")[1].equalsIgnoreCase("PM")) {
      holder.binding.dayQualifier.setText("ညေန");
    } else {
      holder.binding.dayQualifier.setText("မနက္");
    }
    holder.binding.buyDate.setText("ဝယ္ယူသည့္ေန႕ - " + getItem(position).getOrderdate());
    holder.binding.ref.setText(getItem(position).getTicketNo());
    holder.binding.description.setText(getItem(position).getName()
        + "|"
        + getItem(position).getSeatQty()
        + "seats"
        + "|Total - "
        + getItem(position).getTotalAmount());
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

    holder.itemView.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent showVoucher = new Intent(aty.getApplication(), PDFBusActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("from_intent", "from_threeday_sales");
        bundle.putString("Operator_Name", getItem(position).getOperator());
        bundle.putString("from_to", getItem(position).getFrom() + "=>" + getItem(position).getTo());
        bundle.putString("date", getItem(position).getDepartureDate());
        bundle.putString("time", getItem(position).getTime());
        bundle.putString("classes", getItem(position).getBusclass());
        bundle.putString("selected_seat", getItem(position).getSeatNo());
        //bundle.putString("ticket_price", getItem(position).getTotalAmount());
        bundle.putString("sale_order_no", getItem(position).getId());
        bundle.putString("ConfirmDate", getItem(position).getDepartureDate());
        bundle.putString("ConfirmTime", getItem(position).getTime());
        bundle.putString("BuyerName", getItem(position).getName());
        bundle.putString("BuyerPhone", getItem(position).getPhone());
        bundle.putString("BuyerNRC", getItem(position).getNrcNo());
        bundle.putString("TicketNo", getItem(position).getTicketNo());
        bundle.putString("SeatCount", String.valueOf(getItem(position).getSeatQty()));
        bundle.putString("total_amount", getItem(position).getTotalAmount());
        //bundle.putString("Passengers", getItem(position).getPassengers());
        bundle.putString("operatorPhone", getItem(position).getOperatorPhone());
        bundle.putString("random_tickets", getItem(position).getTicketNo());
        showVoucher.putExtras(bundle);
        aty.startActivity(showVoucher);
      }
    });

  }


      //holder.date = (TextView) convertView.findViewById(R.id.date);
      //holder.place = (TextView) convertView.findViewById(R.id.place);
      //holder.day = (TextView) convertView.findViewById(R.id.day);
      //holder.day_qualifier = (TextView) convertView.findViewById(R.id.day_qualifier);
      //holder.buy_date = (TextView) convertView.findViewById(R.id.buy_date);
      //holder.ref = (TextView) convertView.findViewById(R.id.ref);
      //holder.description = (TextView) convertView.findViewById(R.id.description);

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

    //  convertView.setTag(holder);
    //} else {
    //  holder = (ViewHolder) convertView.getTag();
    //}

    //08-01-2017

  //  return convertView;
  //}

  public class ViewHolder extends RecyclerView.ViewHolder {

    ItemOutstandingBookingsBinding binding;

    public ViewHolder(View itemView) {
      super(itemView);
     binding =  ItemOutstandingBookingsBinding.bind(itemView);
    }
    //TextView txt_sale_date, txt_customer_name, txt_trip_operator, txt_trip_date_time_class, txt_seats, txt_price, txt_amount;
    //TextView txt_seller_name, txt_booking_user;
    //Button btn_print;
  }
}
