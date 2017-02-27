package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ItemOutstandingBookingsBinding;
import com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 1/22/17.
 */

class OutstandingAdapter extends RecyclerView.Adapter<OutstandingAdapter.ViewHolder> {

  List<SoldTicketList> list = new ArrayList<SoldTicketList>();

  public OutstandingAdapter() {
  }

  public void replaceList(List<SoldTicketList> list) {
    this.list = list;
    notifyDataSetChanged();
  }


  public void clear(){
    this.list.clear();
    notifyDataSetChanged();
  }
  @Override public int getItemCount() {
    return list.size();
  }

  public SoldTicketList getItem(int position) {
    return list.get(position);
  }

  Context context;

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_outstanding_bookings, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    String from = getItem(position).getFrom().trim();
    from = from.substring(from.indexOf("(") + 1);
    from = from.substring(0, from.indexOf(")"));

    String to = getItem(position).getTo().trim();
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
        Intent showVoucher = new Intent(context, PDFBusActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("from_intent", "from_threeday_sales");
        bundle.putString("Operator_Name", getItem(position).getOperator());
        bundle.putString("from_to", getItem(position).getFrom() + "-" + getItem(position).getTo());
        bundle.putString("date", getItem(position).getDepartureDate());
        bundle.putString("time", getItem(position).getDepartureDate());
        bundle.putString("classes", getItem(position).getBusclass());
        bundle.putString("selected_seat", getItem(position).getSeatNo());
        bundle.putString("ticket_price", getItem(position).getTotalAmount());
        bundle.putString("sale_order_no", getItem(position).getId());
        bundle.putString("ConfirmDate", getItem(position).getOrderdate());
        bundle.putString("ConfirmTime", getItem(position).getTime());
        bundle.putString("BuyerName", getItem(position).getName());
        bundle.putString("BuyerPhone", getItem(position).getPhone());
        bundle.putString("BuyerNRC", getItem(position).getNrcNo());
        bundle.putString("TicketNo", getItem(position).getTicketNo());
        bundle.putString("SeatCount", String.valueOf(getItem(position).getSeatQty()));
        bundle.putString("total_amount", getItem(position).getTotalAmount());
        //bundle.putString("Passengers", getItem(position).()); /
        bundle.putString("operatorPhone", getItem(position).getOperatorPhone());
        bundle.putString("random_tickets", getItem(position).getTicketNo());
        showVoucher.putExtras(bundle);
        context.startActivity(showVoucher);
      }
    });
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ItemOutstandingBookingsBinding binding;

    public ViewHolder(View itemView) {
      super(itemView);
      binding = ItemOutstandingBookingsBinding.bind(itemView);
    }
  }
}
