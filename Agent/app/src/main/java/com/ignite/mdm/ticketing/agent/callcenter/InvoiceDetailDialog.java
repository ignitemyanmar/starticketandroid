package com.ignite.mdm.ticketing.agent.callcenter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.DialogInvoiceBinding;
import com.ignite.mdm.ticketing.sqlite.database.model.InvoiceDetailAdapterModel;

/**
 * Created by user on 2/1/17.
 */

public class InvoiceDetailDialog extends DialogFragment {
  public static final String BUNDLE = "BUNDLE";
  DialogInvoiceBinding binding;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_invoice, container, false);
    binding = DataBindingUtil.bind(view);

    InvoiceDetailAdapterModel invoiceDetailAdapterModel =
        (InvoiceDetailAdapterModel) getArguments().getSerializable(BUNDLE);
    binding.name.setText(invoiceDetailAdapterModel.getCustomerName());
    binding.phone.setText(invoiceDetailAdapterModel.getCustomerPhone());
    binding.amount.setText(invoiceDetailAdapterModel.getNetAmount()+"MMK");
    binding.carClass.setText(invoiceDetailAdapterModel.getClass_());
    binding.seats.setText("Seats x "
        + invoiceDetailAdapterModel.getSeatQty()
        + " - "
        + invoiceDetailAdapterModel.getSeatNo());
    binding.remark.setText(invoiceDetailAdapterModel.getRemark());

    return view;
  }
}
