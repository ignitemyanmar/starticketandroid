package com.ignite.mm.ticketing.agent.callcenter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ignite.mm.ticketing.agent.callcenter.R;

public class MenuFragment extends Fragment {
	private View rootView;
		
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.menu_list, container, false); //**
		return rootView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		MainFragment.aty.finish();
				
	}

}
	

