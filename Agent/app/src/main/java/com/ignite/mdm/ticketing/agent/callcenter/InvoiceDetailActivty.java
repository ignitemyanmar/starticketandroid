package com.ignite.mdm.ticketing.agent.callcenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityInvoiceDetailActivtyBinding;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import com.ignite.mdm.ticketing.custom.listview.adapter.InvoiceDetailAdapter;
import com.ignite.mdm.ticketing.sqlite.database.model.InvoiceDetail;
import com.ignite.mdm.ticketing.sqlite.database.model.InvoiceDetailAdapterModel;
import com.ignite.mdm.ticketing.sqlite.database.model.InvoiceDetailModel;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InvoiceDetailActivty extends BaseSherlockActivity {
  public static final String INVOICE_ID = "INVOICE_ID";
  String token;

  public static Intent getIntent(Context context, String id) {
    Intent intent = new Intent(context, InvoiceDetailActivty.class);
    intent.putExtra(INVOICE_ID, id);

    return intent;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  InvoiceDetailAdapter adapter = new InvoiceDetailAdapter();
  ActivityInvoiceDetailActivtyBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice_detail_activty);
    SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);

    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setTitle("Invoice Detail");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    token = sharedPreferences.getString("operator_token", null);
    binding.recyclerView.setAdapter(adapter);
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerView.setHasFixedSize(true);
    binding.recyclerView.setVisibility(View.GONE);
    binding.progress.setVisibility(View.VISIBLE);

    String param = MCrypt.getInstance()
        .encrypt(SecureParam.getInvoiceDetail(token, getIntent().getStringExtra(INVOICE_ID)));
    NetworkEngine.getInstance2().getInvoiceDetail(param, new Callback<InvoiceDetail>() {
      public void success(final InvoiceDetail invoiceDetail, Response response) {

        if (invoiceDetail != null) {
          binding.totalAmount.setText("Total :" + invoiceDetail.getTotalAmount() + " \n MMK");
          binding.date.setText(invoiceDetail.getInvDate());
          binding.invoiceId.setText(invoiceDetail.getInvoiceNo());
          List<InvoiceDetailAdapterModel> list = new ArrayList<InvoiceDetailAdapterModel>();
          for (InvoiceDetailModel idm : invoiceDetail.getTickets().values()) {
            invoiceDetail.getTickets().keySet().toArray();
            InvoiceDetailAdapterModel idam =
                new InvoiceDetailAdapterModel(idm.getClass_(), idm.getCommission(),
                    idm.getCustomerName(), idm.getCustomerPhone(), idm.getDepartureDate(),
                    idm.getFrom(), getKeyByValue(invoiceDetail.getTickets(), idm),
                    idm.getNetAmount(), idm.getRemark(), idm.getSeatNo(), idm.getSeatQty(),
                    idm.getTime(), idm.getTo());
            list.add(idam);
          }
          adapter.setLists(list);
          adapter.setClick(new InvoiceDetailAdapter.Click() {
            @Override public void onClick(int position, View view) {
              InvoiceDetailDialog invoiceDetailDialog = new InvoiceDetailDialog();
              Bundle bundle = new Bundle();
              bundle.putSerializable(InvoiceDetailDialog.BUNDLE,
                  adapter.getItemAtPosition(position));
              invoiceDetailDialog.setArguments(bundle);
              invoiceDetailDialog.show(getSupportFragmentManager(), "INVOICE");
            }
          });
          binding.recyclerView.setVisibility(View.VISIBLE);
          binding.progress.setVisibility(View.GONE);
        } else {
          showAlert("Sorry Error Occured");
        }
      }

      public void failure(RetrofitError retrofitError) {

        binding.recyclerView.setVisibility(View.GONE);
        showAlert("Sorry Error Occured");
        binding.progress.setVisibility(View.GONE);
      }
    });
  }

  public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
    for (Map.Entry<T, E> entry : map.entrySet()) {
      if (Objects.equals(value, entry.getValue())) {
        return entry.getKey();
      }
    }
    return null;
  }
}
