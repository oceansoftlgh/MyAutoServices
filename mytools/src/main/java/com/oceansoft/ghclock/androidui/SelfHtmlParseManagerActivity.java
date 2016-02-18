package com.oceansoft.ghclock.androidui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.adapter.Find_tab_Adapter;
import com.oceansoft.ghclock.commonutil.MSGUtil;
import com.oceansoft.ghclock.commonutil.Toaster;
import com.oceansoft.ghclock.htmpparse.SelfHtmlParseResultFrement;
import com.oceansoft.ghclock.htmpparse.SelfHtmlParseSelectorFrement;
import com.oceansoft.ghclock.htmpparse.ViewPargerInsideJumpInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/15.
 */
public class SelfHtmlParseManagerActivity  extends FragmentActivity {

	private TabLayout tab_FindFragment_title;                            //定义TabLayout  
	private ViewPager vp_FindFragment_pager;                             //定义viewPager  
	private FragmentPagerAdapter fAdapter;                               //定义adapter  

	private List<Fragment> list_fragment;                                //定义要装fragment的列表  
	private List<String> list_title;                                     //tab名称列表 
	private SelfHtmlParseSelectorFrement selectorFrement;
	private SelfHtmlParseResultFrement resultFrement;
	private ViewPargerInsideJumpInterface vpJumpListener;

	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acticity_tabllayout_smaple);
		initMembers();
		
		initControls();
	}

	private void initMembers() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
					case MSGUtil.SHOW_TOAST_ERROR:
						Toaster.showToast(SelfHtmlParseManagerActivity.this, MSGUtil.error);
						break;
					case MSGUtil.SHOW_TOAST_TIP:
						Toaster.showToast(SelfHtmlParseManagerActivity.this, MSGUtil.tip);
						break;
				}
			}
		};

		vpJumpListener = new ViewPargerInsideJumpInterface() {
			@Override
			public void changFragment(int fragmentID) {
				switch (fragmentID){
					case SelfHtmlParseSelectorFrement.ID:
						vp_FindFragment_pager.setCurrentItem(1);
						break;
					case SelfHtmlParseResultFrement.ID:
						vp_FindFragment_pager.setCurrentItem(0);
						break;
						
					
				}
			}
		};
	}

	/**
	 * @return
	 */
	private void initControls() {
		//初始化标题
		tab_FindFragment_title = (TabLayout)findViewById(R.id.tablayout_htmlparse_manageer);
		//将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用  
		list_title = new ArrayList<>();
		list_title.add("查询条件");
		list_title.add("筛选结果");

		//设置TabLayout的模式  
		tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
		//为TabLayout添加tab名称  
		tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
		tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
		
		
		//初始化viewpapger
		vp_FindFragment_pager = (ViewPager)findViewById(R.id.vp_htmlparse_manager);
		//初始化各fragment  
		selectorFrement = new SelfHtmlParseSelectorFrement();
		selectorFrement.setHandler(mHandler);
		selectorFrement.setOnViewParViewPargerInsideJumpInterface(vpJumpListener);
		
		resultFrement = new SelfHtmlParseResultFrement();
		resultFrement.setHandler(mHandler);
		resultFrement.setOnViewParViewPargerInsideJumpInterface(vpJumpListener);
		
		//将fragment装进列表中  
		list_fragment = new ArrayList<>();
		list_fragment.add(selectorFrement);
		list_fragment.add(resultFrement);
		
		fAdapter = new Find_tab_Adapter(getSupportFragmentManager(),list_fragment,list_title);
		//viewpager加载adapter  
		vp_FindFragment_pager.setAdapter(fAdapter);
		//tab_FindFragment_title.setViewPager(vp_FindFragment_pager);  
		//TabLayout加载viewpager  
		tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
		
		vp_FindFragment_pager.setCurrentItem(0);
		
	}
	
}
