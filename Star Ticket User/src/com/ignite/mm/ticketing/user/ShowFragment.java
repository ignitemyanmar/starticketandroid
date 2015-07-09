package com.ignite.mm.ticketing.user;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.ignite.mm.ticketing.custom.listview.adapter.ShowListAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.ShowList;
import com.ignite.mm.ticketing.user.R;

public class ShowFragment extends Fragment{
	private  ArrayList<ShowList> showList;
	private ListView lvShow;
	private View rootView;
	//private String TicketTypeID;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.show_list, container, false); //**
		return rootView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		lvShow = (ListView) rootView.findViewById(R.id.listShow);
		//TicketTypeID = MainFragment.TicketTypeId;
		getData();
	}
	
	private void getData(){
		showList=new ArrayList<ShowList>();
		showList.add(new ShowList("1","The Big Bag In Show"));
		/*dbmanager = new DatabaseShow(getActivity());
		showList = ((DatabaseShow) dbmanager).getAllShow();*/
		//Log.i("menuList","Menu :" +menuList);
		lvShow.setAdapter(new ShowListAdapter(getActivity(), showList));
		lvShow.setOnItemClickListener(itemClickListener);
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
				Intent nextScreen = new Intent(getActivity(), TicketPriceActivity.class);
				Bundle bundle = new Bundle();
			//	bundle.putString("ticketTypeID", TicketTypeID);
				bundle.putString("show_id",  showList.get(position).getShowID()); 
				bundle.putString("showname", showList.get(position).getShowName());
				nextScreen.putExtras(bundle);
				startActivity(nextScreen);
			}
	};
}
