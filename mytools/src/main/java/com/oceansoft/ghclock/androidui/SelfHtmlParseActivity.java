package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.commonutil.MSGUtil;
import com.oceansoft.ghclock.commonutil.Toaster;
import com.oceansoft.ghclock.commonutil.WorkInfoAgent;
import com.oceansoft.ghclock.htmpparse.BugFilterModel;
import com.oceansoft.ghclock.htmpparse.OptionModel;
import com.oceansoft.ghclock.htmpparse.SelectorModel;
import com.oceansoft.ghclock.htmpparse.SelfHtmlParseActivityControl;

import org.apache.commons.httpclient.HttpClient;

import java.util.HashMap;


/**
 * Created by Administrator on 2016/1/26.
 */
public class SelfHtmlParseActivity extends Activity {
	//widgets
	EditText et_email, et_password;
	EditText et_varilResult;
	Button btn_varil;
	private CheckBox cb_saveUser;

	private Button btn_getAllBug;
	private Button btn_getFilterBug;
	
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
	private Handler mHandler;
	private HttpClient client;

	//varials

	boolean isConnect = false;
	private HashMap<String ,SelectorModel> filters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.lay);
//		setContentView(R.la)
		initWidgets();
		initMembers();
		binEvent();
		initDate();
	}

	private void initDate() {
		try {
			String email = WorkInfoAgent.getRecordInfo(SelfHtmlParseActivity.this, "email");
			String password = WorkInfoAgent.getRecordInfo(SelfHtmlParseActivity.this, "Password");
			if (!"".equals(email)) {
				et_email.setText(email);
				if (!"".equals(password)) {
					et_password.setText(password);
					cb_saveUser.setChecked(true);
					mHandler.sendEmptyMessage(MSGUtil.ACTION_AFTER_INIT);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initMembers() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
					case MSGUtil.SHOW_TOAST_ERROR:
						Toaster.showToast(SelfHtmlParseActivity.this, MSGUtil.error);
						break;
					case MSGUtil.SHOW_TOAST_TIP:
						Toaster.showToast(SelfHtmlParseActivity.this, MSGUtil.tip);
						break;
					case MSGUtil.ACTION_AFTER_INIT:
						connectToServer();
						break;
				}

			}
		};

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
							WorkInfoAgent.saveRecordInfo(SelfHtmlParseActivity.this, "email", et_email.getText().toString());
							WorkInfoAgent.saveRecordInfo(SelfHtmlParseActivity.this, "Password", et_password.getText().toString());
						}
						break;
					case R.id.btn_htmlparse_getallbug:
//						boolean result_allBug = getAllBug();
						break;
					case R.id.btn_htmlparse_getfliterbug:
						boolean result_filterBug = getFilterBug();
						break;
				}
			}
		};

		btn_varil.setOnClickListener(onClickListener);
		btn_getAllBug.setOnClickListener(onClickListener);
		btn_getFilterBug.setOnClickListener(onClickListener);
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
				client = control.ConnecttoServer(email, password);
				if (null == client) {
					MSGUtil.tip = "登录验证失败";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}
				
				String bugAllContent = control.getAllBugContent(client);
				if("".equals(bugAllContent)){
					MSGUtil.tip = "获取内容失败";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return false;
				}
				filters = control.getFiltersMap(bugAllContent);
				return true;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				if (!aBoolean) {
					et_varilResult.setText("连接失败");
					et_varilResult.setTextColor(getResources().getColor(R.color.red));
					isConnect = false;
					
				} else {
					et_varilResult.setText("连接成功");
					et_varilResult.setTextColor(getResources().getColor(R.color.green));

					isConnect = true;
					setFileterToBox();
				}
			}
		}.execute(new Integer[]{0});
		return true;
	}

	private void setFileterToBox() {
		if(filters.size() <= 0){
			return;
		}
		//bugStatus
		spn_bugStatus.setAdapter(new ArrayAdapter<OptionModel>(this,android.R.layout.simple_spinner_item,filters.get("bugStatus").getOptions()));
		//handler
		spn_handler.setAdapter(new ArrayAdapter<OptionModel>(this,android.R.layout.simple_spinner_item,filters.get("handler").getOptions()));
		//keyWord
		//spn.setAdapter(new ArrayAdapter<OptionModel>(this,android.R.layout.simple_spinner_item,filters.get("keyWord").getOptions()));
		//member
		if(filters.containsKey("member"))
		spn_member.setAdapter(new ArrayAdapter<OptionModel>(this,android.R.layout.simple_spinner_item,filters.get("member").getOptions()));
		//moudle
		if(filters.containsKey("moudle"))
		spn_moudle.setAdapter(new ArrayAdapter<OptionModel>(this, android.R.layout.simple_spinner_item, filters.get("moudle").getOptions()));
		//orderBy
		if(filters.containsKey("orderBy"))
		spn_orderBy.setAdapter(new ArrayAdapter<OptionModel>(this, android.R.layout.simple_spinner_item, filters.get("orderBy").getOptions()));
		//pid
		if(filters.containsKey("pid"))
		spn_pid.setAdapter(new ArrayAdapter<OptionModel>(this, android.R.layout.simple_spinner_item, filters.get("pid").getOptions()));
		//priority
		if(filters.containsKey("priority"))
		spn_priority.setAdapter(new ArrayAdapter<OptionModel>(this, android.R.layout.simple_spinner_item, filters.get("priority").getOptions()));
		//version
		if(filters.containsKey("version"))
		spn_version.setAdapter(new ArrayAdapter<OptionModel>(this, android.R.layout.simple_spinner_item, filters.get("version").getOptions()));
	}


	
	/**
	 * 带过滤查询
	 * @return
	 */
	private boolean getFilterBug() {
		final BugFilterModel filter = getFilter();
		new Thread(new Runnable() {
			@Override
			public void run() {

				if(!isConnect || null == client){
					MSGUtil.tip = "未连接到bug";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return;
				}
				String bugFilterContent = control.getFilterBugContent(client, filter);
				if("".equals(bugFilterContent)){
					MSGUtil.tip = "获取内容失败";
					mHandler.sendEmptyMessage(MSGUtil.SHOW_TOAST_TIP);
					return;
				}
				control.getBugEnvityValues(bugFilterContent);
			}
		}).start();
		return true;
	}


	private void initWidgets() {
		setContentView(R.layout.activity_htmlparse_smaple);

		et_email = (EditText) findViewById(R.id.et_htmlparse_emile);
		et_password = (EditText) findViewById(R.id.et_htmlparse_password);
		et_varilResult = (EditText) findViewById(R.id.et_htmlparse_loginverify);
		
		cb_saveUser = (CheckBox) findViewById(R.id.cb_htmlparse_saveuser);
		
		btn_varil = (Button) findViewById(R.id.btn_htmlparse_loginverify);
		btn_getAllBug = (Button) findViewById(R.id.btn_htmlparse_getallbug);
		btn_getFilterBug = (Button) findViewById(R.id.btn_htmlparse_getfliterbug);
		
		spn_bugStatus = (Spinner) findViewById(R.id.spn_htmlparse_bugstat);
		spn_handler = (Spinner) findViewById(R.id.spn_htmlparse_handler);
		spn_member = (Spinner) findViewById(R.id.spn_htmlparse_member);
		spn_moudle = (Spinner) findViewById(R.id.spn_htmlparse_moudle);
		spn_orderBy = (Spinner) findViewById(R.id.spn_htmlparse_orderBy);
		//spn_pageIndex = (Spinner) findViewById(R.id.sp);
		spn_pid  = (Spinner) findViewById(R.id.spn_htmlparse_pid);
		spn_priority = (Spinner) findViewById(R.id.spn_htmlparse_priority);
		spn_version = (Spinner) findViewById(R.id.spn_htmlparse_version);
	}
	private BugFilterModel getFilter() {
		
		BugFilterModel bugFilterModel = new BugFilterModel();
		if(null != spn_bugStatus.getSelectedItem()){
			bugFilterModel.setBugStatus(((OptionModel)spn_bugStatus.getSelectedItem()).getValue());
		}
		if(null != spn_handler.getSelectedItem()){
			bugFilterModel.setHandler(((OptionModel) spn_handler.getSelectedItem()).getValue());
		}
		if(null != spn_member.getSelectedItem()){
			bugFilterModel.setMember(((OptionModel) spn_member.getSelectedItem()).getValue());
		}
		if(null != spn_moudle.getSelectedItem()){
			bugFilterModel.setMoudle(((OptionModel) spn_moudle.getSelectedItem()).getValue());
		}
		if(null != spn_orderBy.getSelectedItem()){
			bugFilterModel.setOrderBy(((OptionModel) spn_orderBy.getSelectedItem()).getValue());
		}
		if(null != spn_pid.getSelectedItem()){
			bugFilterModel.setPid(((OptionModel) spn_pid.getSelectedItem()).getValue());
		}
		if(null != spn_priority.getSelectedItem()){
			bugFilterModel.setPriority(((OptionModel) spn_priority.getSelectedItem()).getValue());
		}
		if(null != spn_version.getSelectedItem()){
			bugFilterModel.setVersion(((OptionModel) spn_version.getSelectedItem()).getValue());
		}
		return bugFilterModel;
	}


}
