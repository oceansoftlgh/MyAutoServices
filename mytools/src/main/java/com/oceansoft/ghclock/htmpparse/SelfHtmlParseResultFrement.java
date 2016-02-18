package com.oceansoft.ghclock.htmpparse;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.commonadapter.CommonAdapter;
import com.oceansoft.ghclock.commonadapter.ViewHolder;
import com.oceansoft.ghclock.commonutil.MSGUtil;
import com.oceansoft.ghclock.listviewutil.WaterDropListView;

import java.util.List;

/**
 * Created by Administrator on 2016/2/15.
 */
public class SelfHtmlParseResultFrement extends BasicFragment implements WaterDropListView.IWaterDropListViewListener {
	public static final int ID = 0x0002;
	private boolean isCreated = false;

	//memebers
	private List<BugContentModel> bugContentModels;
	private CommonAdapter<BugContentModel> lvAdapter;

	//widgets
	private WaterDropListView lv_result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isCreated = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_htmlparse_result, container, false);
		initWidgets(view);
		return view;
	}

	private void initWidgets(View view) {
		lv_result = (WaterDropListView) view.findViewById(R.id.lv_htmlparse_result);
		lv_result.setOnWaterDropListViewListener(this);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (!isCreated) {
			return;
		}
		if (isVisibleToUser) {
			getFilterBug();
		}
	}

	/**
	 * 带过滤查询
	 *
	 * @return
	 */
	private void getFilterBug() {
		AsyncTask<Void, Void, Boolean> getFilterBug = new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				SelfHtmlParseActivityControl control = new SelfHtmlParseActivityControl(mHandler);
				if (!HtmlParseSession.isConnect || null == HtmlParseSession.hostClient) {
					MSGUtil.tip = "未连接到bug";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}

				HtmlParseSession.bugFilterModel.setPageIndex("1");


				String bugFilterContent = control.getFilterBugContent(HtmlParseSession.hostClient, HtmlParseSession.bugFilterModel);
				if ("".equals(bugFilterContent)) {
					MSGUtil.tip = "获取内容失败";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}
				HtmlParseSession.totalPage = control.getTotalPageNum(bugFilterContent);

				bugContentModels = control.getBugEnvityValues(bugFilterContent);

				return true;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				if (!aBoolean) {
					return;
				}

				reloadListview();
				lv_result.stopRefresh();

			}
		};
		getFilterBug.execute((Void)null);

	}

	/**
	 * 分页加载
	 */

	private void getMoreFilterBug() {
		AsyncTask getFilterBug = new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				SelfHtmlParseActivityControl control = new SelfHtmlParseActivityControl(mHandler);
				if (!HtmlParseSession.isConnect || null == HtmlParseSession.hostClient) {
					MSGUtil.tip = "未连接到bug";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}

				int currentPage = Integer.parseInt(HtmlParseSession.bugFilterModel.getPageIndex()) + 1;
				if (currentPage > HtmlParseSession.totalPage) {
					MSGUtil.tip = "无更多内容";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}
				HtmlParseSession.bugFilterModel.setPageIndex(currentPage + "");

				String bugFilterContent = control.getFilterBugContent(HtmlParseSession.hostClient, HtmlParseSession.bugFilterModel);
				if ("".equals(bugFilterContent)) {
					MSGUtil.tip = "获取内容失败";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}
				HtmlParseSession.totalPage = control.getTotalPageNum(bugFilterContent);

				List<BugContentModel> newBugContentModels = control.getBugEnvityValues(bugFilterContent);
				bugContentModels.addAll(newBugContentModels);
				return true;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				if (!aBoolean) {
					return;
				}
				loadMoreToListview();
				lv_result.stopLoadMore();
			}
		};
		getFilterBug.execute((Void) null);
	}


	private void reloadListview() {
		lvAdapter = new CommonAdapter<BugContentModel>(this.getActivity(), bugContentModels, R.layout.listview_easybug_bugcontent) {
			@Override
			public void convert(ViewHolder holder, BugContentModel bugContentModel) {
				holder.setText(R.id.lv_tv_easybug_title, bugContentModel.getTitle());
				holder.setText(R.id.lv_tv_easybug_bugstat, bugContentModel.getBugStat());
				holder.setText(R.id.lv_tv_easybug_date, bugContentModel.getDate());
				holder.setText(R.id.lv_tv_easybug_handler, bugContentModel.getMember());
				holder.setText(R.id.lv_tv_easybug_priority, bugContentModel.getPriority());
				holder.setText(R.id.lv_tv_easybug_version, bugContentModel.getVersion());
			}
		};
		lv_result.setAdapter(lvAdapter);
	}

	private void loadMoreToListview() {
		lvAdapter.notifyDataSetChanged();
	}

	@Override
	public void onRefresh() {
		getFilterBug();
	}

	@Override
	public void onLoadMore() {
		getMoreFilterBug();
	}
}
