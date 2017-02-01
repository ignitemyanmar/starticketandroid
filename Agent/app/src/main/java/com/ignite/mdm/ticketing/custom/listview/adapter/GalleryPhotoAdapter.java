package com.ignite.mdm.ticketing.custom.listview.adapter;

import com.ignite.mdm.ticketing.agent.callcenter.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryPhotoAdapter extends BaseAdapter {

	private Context mContext; 
	
	public GalleryPhotoAdapter(Context context) {
		super();
		this.mContext = context;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mImageIds.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imgView = new ImageView(mContext);
		imgView.setImageResource(mImageIds[position]);
		imgView.setLayoutParams(new Gallery.LayoutParams(200, 200));
		imgView.setScaleType(ImageView.ScaleType.FIT_XY);
		return imgView;
	}

	   public Integer[] mImageIds = {
	            R.drawable.bagan_200,
	            R.drawable.bagan2_200,
	            R.drawable.bagan_200,
	            R.drawable.bagan2_200,
	            R.drawable.bagan_200,
	            R.drawable.bagan2_200,
	            R.drawable.bagan_200,
	            R.drawable.bagan2_200,
	            R.drawable.bagan_200,
	            R.drawable.bagan2_200,
	    };

}
