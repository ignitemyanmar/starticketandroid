package com.ignite.mm.ticketing.application;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.google.gson.reflect.TypeToken;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.databinding.DialogChooserPlaceBinding;
import com.ignite.mdm.ticketing.agent.util.Constant;
import com.ignite.mdm.ticketing.clientapi.NetworkEngine;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 1/20/17.
 */

public class DialogChooserPlace extends DialogFragment {
  View view;

  DialogListClick dialogListClick;
  String type = FROM;
  DialogChooserPlaceBinding binding;

  public static String TYPE = "TYPE";

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.dialog_chooser_place, container, false);
    binding = DialogChooserPlaceBinding.bind(view);

    type = getArguments().getString(TYPE);
    final ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
            android.R.id.text1, fromCities.toArray(new String[0]));
    //ArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects) {

    binding.list.setAdapter(adapter);
    binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dialogListClick.onClick(type, fromCities.get(i));
        dismiss();
      }
    });
    binding.searchView.setIconified(false);
    binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      public boolean onQueryTextSubmit(String query) {
        return true;
      }

      public boolean onQueryTextChange(String newText) {
        values.clear();
        values.addAll(ListUtils.search(fromCities, newText));

        ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, values.toArray(new String[0]));
        //ArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            dialogListClick.onClick(type, values.get(i));
            dismiss();
          }
        });
        binding.list.setAdapter(adapter);
        return true;
      }
    });

    return view;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof DialogListClick) {
      dialogListClick = (DialogListClick) context;
    }
  }

  @Override public void onStart() {
    super.onStart();
    if (type == FROM) {
      getFromCities();
    } else {
      getToCities();
    }
  }

  public interface DialogListClick {
    public void onClick(String type, String data);
  }

  public static final String FROM = "FROM";
  public static final String TO = "TO";
  List<String> fromCities = new ArrayList<String>();
  private final List<String> values = new ArrayList<String>();

  private void getFromCities() {
    // TODO Auto-generated method stub
    fromCities = new ArrayList<String>();

    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .getFromCities(((BaseSherlockActivity) getActivity()).AppLoginUser.getAccessToken(),
            Constant.OPERATOR_ID, new Callback<Response>() {

              public void success(Response response, Response arg1) {
                // TODO Auto-generated method stub
                if (response != null) {
                  List<String> fromCityList = new ArrayList<String>();
                  fromCityList =
                      DecompressGZIP.fromBody(response.getBody(), new TypeToken<List<String>>() {
                      }.getType());

                  fromCities.addAll(fromCityList);
                  values.addAll(fromCityList);
                  binding.progress.setVisibility(View.GONE);
                  binding.container.setVisibility(View.VISIBLE);
                  if (fromCities != null && fromCities.size() > 0) {
                    List<String> s = new ArrayList<String>();
                    for(String s0:fromCities){
                          if(s0.contains("Mandalay") || s0.contains("Yangon")){
                            s.add(0,s0);
                          }else {
                            s.add(s0);
                          }
                    }
                    fromCities = s;
                    ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                            android.R.id.text1, fromCities.toArray(new String[0]));
                    binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      public void onItemClick(AdapterView<?> adapterView, View view, int i,
                          long l) {
                        dialogListClick.onClick(type, fromCities.get(i));
                        dismiss();
                      }
                    });
                    binding.list.setAdapter(adapter);
                  }
                }
              }

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  dismiss();
                  Log.i("", "Error: " + arg0.getResponse().getStatus());
                }
              }
            });
  }

  private void getToCities() {
    // TODO Auto-generated method stub

    fromCities = new ArrayList<String>();

    NetworkEngine.setIP("starticketmyanmar.com");
    NetworkEngine.getInstance()
        .getToCities(((BaseSherlockActivity) getActivity()).AppLoginUser.getAccessToken(),
            Constant.OPERATOR_ID, new Callback<Response>() {

              public void success(Response response, Response arg1) {
                // TODO Auto-generated method stub
                if (response != null) {
                  List<String> fromCityList = new ArrayList<String>();
                  fromCityList =
                      DecompressGZIP.fromBody(response.getBody(), new TypeToken<List<String>>() {
                      }.getType());

                  fromCities.addAll(fromCityList);
                  values.addAll(fromCityList);
                  binding.progress.setVisibility(View.GONE);
                  binding.container.setVisibility(View.VISIBLE);
                  if (fromCities != null && fromCities.size() > 0) {

                    List<String> s = new ArrayList<String>();
                    for(String s0:fromCities){
                      if(s0.contains("Mandalay") || s0.contains("Yangon")){
                        s.add(0,s0);
                      }else {
                        s.add(s0);
                      }
                    }
                    fromCities = s;

                    ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                            android.R.id.text1, fromCities.toArray(new String[0]));
                    //ArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects) {

                    binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      public void onItemClick(AdapterView<?> adapterView, View view, int i,
                          long l) {
                        dialogListClick.onClick(type, fromCities.get(i));
                        dismiss();
                      }
                    });
                    binding.list.setAdapter(adapter);
                  }
                }
              }

              public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub
                if (arg0.getResponse() != null) {
                  dismiss();
                  Log.i("", "Error: " + arg0.getResponse().getStatus());
                }
              }
            });
  }
}
