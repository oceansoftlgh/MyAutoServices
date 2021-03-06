package com.oceansoft.ghclock.htmpparse;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.commonutil.MSGUtil;
import com.oceansoft.ghclock.commonutil.WorkInfoAgent;


/**
 * Created by Administrator on 2016/1/26.
 */
public class SelfHtmlParseSelectorFrement extends BasicFragment {
	public static final int ID = 0x0001;

	private Context mContext;
	//widgets
	EditText et_email, et_password;
	private EditText et_keyword;
	EditText et_varilResult;
	Button btn_varil;
	private CheckBox cb_saveUser;

	private Button btn_saveFilter;
	private Spinner spn_bugStatus;
	private Spinner spn_handler;
	private Spinner spn_member;
	private Spinner spn_moudle;
	private Spinner spn_orderBy;
	private Spinner spn_pageIndex;
	private Spinner spn_pid;
	private Spinner spn_priority;
	private Spinner spn_version;

	//members
	SelfHtmlParseActivityControl control;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_htmlparse_selector, container, false);
		initWidgets(view);
		initMembers();
		binEvent();
		initDate();
		return view;
	}

	private void initDate() {
		try {
			String email = WorkInfoAgent.getRecordInfo(this.getActivity(), "email");
			String password = WorkInfoAgent.getRecordInfo(this.getActivity(), "Password");
			if (!"".equals(email)) {
				et_email.setText(email);
				if (!"".equals(password)) {
					et_password.setText(password);
					cb_saveUser.setChecked(true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		connectToServer();
	}

	private void initMembers() {
		if (null == mHandler) {
			mHandler = new Handler();
		}

		control = new SelfHtmlParseActivityControl(mHandler);
	}

	private void binEvent() {
		View.OnClickListener onClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {

					case R.id.btn_htmlparse_loginverify:
						boolean result_conn = connectToServer();
						if (result_conn && cb_saveUser.isChecked()) {
							WorkInfoAgent.saveRecordInfo(SelfHtmlParseSelectorFrement.this.getActivity(), "email", et_email.getText().toString());
							WorkInfoAgent.saveRecordInfo(SelfHtmlParseSelectorFrement.this.getActivity(), "Password", et_password.getText().toString());
						}
						break;
					case R.id.btn_htmlparse_saveFilter:
						saveFilter();
						changToFragment(ID);
						break;

				}
			}
		};

		btn_varil.setOnClickListener(onClickListener);
		btn_saveFilter.setOnClickListener(onClickListener);
	}

	/**
	 * 连接到服务器
	 *
	 * @return
	 */
	private boolean connectToServer() {
		if (et_email.getText() == null || et_email.getText().toString().trim().equals("")
				|| et_password.getText() == null || et_password.getText().toString().trim().equals("")) {
			MSGUtil.tip = "用户名和密码分不能为空";
			mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
			return false;
		}
		final String email = et_email.getText().toString();
		final String password = et_password.getText().toString();

		new AsyncTask<Integer, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Integer... params) {
				int i = params[0];
				HtmlParseSession.hostClient = control.ConnecttoServer(email, password);
				if (null == HtmlParseSession.hostClient) {
					MSGUtil.tip = "登录验证失败";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}

				String bugAllContent = control.getAllBugContent(HtmlParseSession.hostClient);
				if ("".equals(bugAllContent)) {
					MSGUtil.tip = "获取内容失败";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}
				HtmlParseSession.filters = control.getFiltersMap(bugAllContent);
				return true;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				if (!aBoolean) {
					et_varilResult.setText("连接失败");
					et_varilResult.setTextColor(getResources().getColor(R.color.red));
					HtmlParseSession.isConnect = false;

				} else {
					et_varilResult.setText("连接成功");
					et_varilResult.setTextColor(getResources().getColor(R.color.green));

					HtmlParseSession.isConnect = true;
					setFileterToBox();
					changToFragment(ID);
				}
			}
		}.execute(new Integer[]{0});
		return true;
	}

	private void setFileterToBox() {
		if (HtmlParseSession.filters.size() <= 0) {
			return;
		}
		Context context = this.getActivity();
		//bugStatus
		spn_bugStatus.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("bugStatus").getOptions()));
		//handler
		spn_handler.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("handler").getOptions()));
		//member
		if (HtmlParseSession.filters.containsKey("member"))
			spn_member.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("member").getOptions()));
		//moudle
		if (HtmlParseSession.filters.containsKey("moudle"))
			spn_moudle.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("moudle").getOptions()));
		//pid
		if (HtmlParseSession.filters.containsKey("s_prj"))
			spn_pid.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("s_prj").getOptions()));
		//priority
		if (HtmlParseSession.filters.containsKey("priority"))
			spn_priority.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("priority").getOptions()));
		//version
		if (HtmlParseSession.filters.containsKey("version"))
			spn_version.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("version").getOptions()));
		//orderBy
		if (HtmlParseSession.filters.containsKey("orderBy"))
			spn_orderBy.setAdapter(new ArrayAdapter<OptionModel>(context, android.R.layout.simple_spinner_item, HtmlParseSession.filters.get("orderBy").getOptions()));

	}


	private void saveFilter() {
		HtmlParseSession.bugFilterModel = getFilter();
	}

	@Override
	public void onPause() {
		super.onPause();
		saveFilter();
	}

	private void initWidgets(View view) {

		et_email = (EditText) view.findViewById(R.id.et_htmlparse_emile);
		et_password = (EditText) view.findViewById(R.id.et_htmlparse_password);
		et_varilResult = (EditText) view.findViewById(R.id.et_htmlparse_loginverify);
		et_keyword = (EditText) view.findViewById(R.id.et_htmlparse_selector_keyword);

		cb_saveUser = (CheckBox) view.findViewById(R.id.cb_htmlparse_saveuser);

		btn_varil = (Button) view.findViewById(R.id.btn_htmlparse_loginverify);
		btn_saveFilter = (Button) view.findViewById(R.id.btn_htmlparse_saveFilter);
		

		spn_bugStatus = (Spinner) view.findViewById(R.id.spn_htmlparse_bugstat);
		spn_handler = (Spinner) view.findViewById(R.id.spn_htmlparse_handler);
		spn_member = (Spinner) view.findViewById(R.id.spn_htmlparse_member);
		spn_moudle = (Spinner) view.findViewById(R.id.spn_htmlparse_moudle);
		spn_orderBy = (Spinner) view.findViewById(R.id.spn_htmlparse_orderBy);
		//spn_pageIndex = (Spinner) view.findViewById(R.id.sp);
		spn_pid = (Spinner) view.findViewById(R.id.spn_htmlparse_pid);
		spn_priority = (Spinner) view.findViewById(R.id.spn_htmlparse_priority);
		spn_version = (Spinner) view.findViewById(R.id.spn_htmlparse_version);
	}

	private BugFilterModel getFilter() {

		BugFilterModel bugFilterModel = new BugFilterModel();
		if (null != spn_bugStatus.getSelectedItem()) {
			bugFilterModel.setBugStatus(((OptionModel) spn_bugStatus.getSelectedItem()).getValue());
		}
		if (null != et_keyword.getText()) {
			bugFilterModel.setKeyWord(et_keyword.getText().toString());
		}
		if (null != spn_handler.getSelectedItem()) {
			bugFilterModel.setHandler(((OptionModel) spn_handler.getSelectedItem()).getValue());
		}
		if (null != spn_member.getSelectedItem()) {
			bugFilterModel.setMember(((OptionModel) spn_member.getSelectedItem()).getValue());
		}
		if (null != spn_moudle.getSelectedItem()) {
			bugFilterModel.setMoudle(((OptionModel) spn_moudle.getSelectedItem()).getValue());
		}
		if (null != spn_orderBy.getSelectedItem()) {
			bugFilterModel.setOrderBy(((OptionModel) spn_orderBy.getSelectedItem()).getValue());
		}
		if (null != spn_pid.getSelectedItem()) {
			bugFilterModel.setPid(((OptionModel) spn_pid.getSelectedItem()).getValue());
		}
		if (null != spn_priority.getSelectedItem()) {
			bugFilterModel.setPriority(((OptionModel) spn_priority.getSelectedItem()).getValue());
		}
		if (null != spn_version.getSelectedItem()) {
			bugFilterModel.setVersion(((OptionModel) spn_version.getSelectedItem()).getValue());
		}
		return bugFilterModel;
	}


}
