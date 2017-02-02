package com.ignite.mdm.ticketing.custom.listview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ItemInvoiceDetailBinding;
import com.ignite.mdm.ticketing.sqlite.database.model.InvoiceDetailAdapterModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/1/17.
 */

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.ViewHolder> {
  List<InvoiceDetailAdapterModel> lists = new ArrayList<InvoiceDetailAdapterModel>();

  Context context;
  Click click;

  @Override public int getItemCount() {
    return lists.size();
  }

  public void setLists(List<InvoiceDetailAdapterModel> lists) {
    this.lists = lists;
    notifyDataSetChanged();
  }

  public Click getClick() {
    return click;
  }

  public void setClick(Click click) {
    this.click = click;
  }

  public InvoiceDetailAdapterModel getItemAtPosition(int position) {
    return lists.get(position);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();

    return new ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_invoice_detail, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.binding.invoiceId.setText("#" + lists.get(position).getId());
    holder.binding.money.setText(lists.get(position).getNetAmount() + "MMK");
    holder.binding.commission.setText("Commission : " + lists.get(position).getCommission());
    holder.binding.time.setText(lists.get(position).getTime());
    //holder.binding.nameSeats.setText(lists.get(position).getCustomerName()
    //    + " | "
    //    + lists.get(position).getSeatQty()
    //    + " seats");
    String from = lists.get(position).getFrom().trim();
    from = from.substring(from.indexOf("(") + 1);
    from = from.substring(0, from.indexOf(")"));

    String to = lists.get(position).getTo().trim();
    to = to.substring(to.indexOf("(") + 1);
    to = to.substring(0, to.indexOf(")"));

    holder.binding.city.setText(from + " - " + to);
    holder.binding.leaveDate.setText(lists.get(position).getDepartureDate());
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ItemInvoiceDetailBinding binding;

    public ViewHolder(View itemView) {
      super(itemView);
      binding = ItemInvoiceDetailBinding.bind(itemView);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (click != null) {
            click.onClick(getAdapterPosition(), view);
          }
        }
      });
    }
  }

  public interface Click {
    public void onClick(int position, View view);
  }
}
