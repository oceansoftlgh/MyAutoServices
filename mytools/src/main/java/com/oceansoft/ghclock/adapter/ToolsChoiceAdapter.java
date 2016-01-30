package com.oceansoft.ghclock.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oceansoft.ghclock.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/26.
 */
public class ToolsChoiceAdapter  extends BaseAdapter{
	
	List<String> items;
	List<String> pkgs;
	Context context;
	public ToolsChoiceAdapter(Context context, List<String> items, List<String> pkgs){
		this.items = items;
		this.pkgs = pkgs;
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(R.layout.listview_toolschoice, parent, false);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_smaple);


			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(items.get(position).toString());

		holder.tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = null;
				try {
					intent = new Intent(context, Class.forName(pkgs.get(position).toString()));

					context.startActivity(intent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		return convertView;
	}
	
	class  ViewHolder{
		TextView tv;
	}
}
