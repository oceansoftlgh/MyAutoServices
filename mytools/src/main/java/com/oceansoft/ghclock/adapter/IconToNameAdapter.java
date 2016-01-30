package com.oceansoft.ghclock.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceansoft.ghclock.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
public class IconToNameAdapter extends BaseAdapter {
	
	private List<PackageInfo> appInfos;
	private Context context;
	
	
	public IconToNameAdapter(Context context,List<PackageInfo> appInfos){
		this.context = context;
		this.appInfos = appInfos;
		
	}
	
	
	@Override
	public int getCount() {
		return appInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return appInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(R.layout.listview_iconandname, parent, false);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_app_name);
			holder.img = (ImageView) convertView.findViewById(R.id.img_app_icon);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(appInfos.get(position).packageName.toString());
		holder.img.setImageDrawable(appInfos.get(position).applicationInfo.loadIcon(context.getPackageManager()));
		
		return convertView;
	}

	class  ViewHolder{
		ImageView img;
		TextView tv;
	}
}
