package com.oceansoft.ghclock.androidui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.adapter.Find_tab_Adapter;
import com.oceansoft.ghclock.htmpparse.SelfHtmlParseResultFrement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/15.
 */
public class SelfHtmlParseManagerActivity  extends Fragment {

	private TabLayout tab_FindFragment_title;                            //定义TabLayout  
	private ViewPager vp_FindFragment_pager;                             //定义viewPager  
	private FragmentPagerAdapter fAdapter;                               //定义adapter  

	private List<Fragment> list_fragment;                                //定义要装fragment的列表  
	private List<String> list_title;                                     //tab名称列表 
	private SelfHtmlParseManagerActivity selectorFrement;
	private SelfHtmlParseResultFrement resultFrement;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.acticity_tabllayout_smaple,container,false);
		initControls(view);
		
		return view;
	}

	/**
	 * @return
	 */
	private void initControls(View view) {

		tab_FindFragment_title = (TabLayout)view.findViewById(R.id.tablayout_htmlparse_manageer);
		vp_FindFragment_pager = (ViewPager)view.findViewById(R.id.vp_htmlparse_manager);

		//初始化各fragment  
		selectorFrement = new SelfHtmlParseManagerActivity();
		resultFrement = new SelfHtmlParseResultFrement();


		//将fragment装进列表中  
		list_fragment = new ArrayList<>();
		list_fragment.add(selectorFrement);
		list_fragment.add(resultFrement);
		

		//将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用  
		list_title = new ArrayList<>();
		list_title.add("查询条件");
		list_title.add("筛选结果");
		
		//设置TabLayout的模式  
		tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
		//为TabLayout添加tab名称  
		tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
		tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));

		
		fAdapter = new Find_tab_Adapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);

		//viewpager加载adapter  
		vp_FindFragment_pager.setAdapter(fAdapter);
		//tab_FindFragment_title.setViewPager(vp_FindFragment_pager);  
		//TabLayout加载viewpager  
		tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
		//tab_FindFragment_title.set  
		
	}
	
}
