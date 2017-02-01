package com.ignite.mdm.ticketing.sqlite.database.model;

import java.util.Map;

/**
 * Created by user on 2/1/17.
 */

public class Tickets {
  Map<String,InvoiceDetailModel> items;

  public Map<String, InvoiceDetailModel> getItems() {
    return items;
  }

  public void setItems(Map<String, InvoiceDetailModel> items) {
    this.items = items;
  }

  @Override public String toString() {
    return "Tickets{" +
        "items=" + items +
        '}';
  }
}
