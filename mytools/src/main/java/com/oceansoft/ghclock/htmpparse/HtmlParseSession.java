package com.oceansoft.ghclock.htmpparse;

import org.apache.commons.httpclient.HttpClient;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/2/16.
 */
public class HtmlParseSession {
	public static BugFilterModel bugFilterModel = null;
	public static HttpClient hostClient = null;
	
	public static HashMap<String ,SelectorModel> filters = null;
	public static boolean isConnect = false;
	
	public static int totalPage = 0;
	public static int totalRecord = 0;
	public static int recordOfPage = 10;
	
	
}
