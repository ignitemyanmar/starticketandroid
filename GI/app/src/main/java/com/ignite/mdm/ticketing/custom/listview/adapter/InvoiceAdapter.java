package com.ignite.mdm.ticketing.custom.listview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ignite.mdm.ticketing.agent.callcenter.InvoiceDetailActivty;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ItemInvoiceBinding;
import com.ignite.mdm.ticketing.sqlite.database.model.Invoices;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 1/30/17.
 */

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {
  List<Invoices> list = new ArrayList<Invoices>();
  Context context;


  @Override public int getItemCount() {
    return list.size();
  }

  public void replaceList(List list) {
    this.list = list;
    notifyDataSetChanged();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false));
  }

  public void clear() {
    list.clear();
    notifyDataSetChanged();
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.binding.textView2.setText(list.get(position).getInvoiceNo()
    );
    holder.binding.textView6.setText(list.get(position).getTotalAmount().toString());
    holder.binding.textView5.setText("Transaction Id :" + list.get(position).getId());
    holder.binding.textView4.setText(list.get(position).getInvDate());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
          context.startActivity(InvoiceDetailActivty.getIntent(context,list.get(position).getId()));
      }
    });
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private ItemInvoiceBinding binding;

    public ViewHolder(View itemView) {
      super(itemView);
      binding = ItemInvoiceBinding.bind(itemView);

    }
  }
}
