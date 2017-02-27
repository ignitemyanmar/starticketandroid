package com.ignite.mm.ticketing.application;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 1/20/17.
 */

class DialogChooserAdapter extends RecyclerView.Adapter<DialogChooserAdapter.ViewHolder> {

  List<String> list = new ArrayList<String>();

  public DialogChooserAdapter(List<String> list) {
    this.list = list;
  }

  @Override public int getItemCount() {
    return list.size();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {

  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
