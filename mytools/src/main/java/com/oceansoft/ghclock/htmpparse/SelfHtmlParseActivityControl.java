package com.oceansoft.ghclock.htmpparse;

import android.os.Handler;

import com.oceansoft.ghclock.commonutil.MSGProxy;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2016/1/26.
 */
public class SelfHtmlParseActivityControl {


	private MSGProxy msgProxy;

	public SelfHtmlParseActivityControl(Handler handler) {
		this.msgProxy = new MSGProxy(handler);
	}

	public static HttpClient ConnecttoServer(String strEmail, String strPassword) {

		//实例化HttpClient
		HttpClient client = new HttpClient();
		//Stream页面里面有Host地址 端口是80
		client.getHostConfiguration().setHost("http://easybug.org", 80);
		try {
			//用目标地址 实例一个POST方法
			PostMethod post = new PostMethod("http://easybug.org/Member/Login");
			//将需要的键值对写出来
			NameValuePair email = new NameValuePair("email", strEmail);
			NameValuePair password = new NameValuePair("password", strPassword);

			//给POST方法加入上述键值对
			post.setRequestBody(new NameValuePair[]{email, password});
			//执行POST方法
			client.executeMethod(post);
			//将POST返回的数据以流的形式读入，再把输入流流至一个buff缓冲字节数组
			//StreamTool类是我自己写的一个工具类，其内容将在下文附出
			Header[] headers = post.getResponseHeaders("Set-Cookie");

			byte[] buff = StreamTools.readInputStream(post.getResponseBodyAsStream());
			//将返回的内容格式化为String存在html中
			String html = new String(buff);
			//任务完成了，释放连接
			post.releaseConnection();
			if (!html.contains("ProjectList")) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return client;
	}
	
	public String getAllBugContent(HttpClient client) {
		String result = "";
		try {
			GetMethod get = new GetMethod("http://easybug.org/Bug/AllBug/17137");

			result = getHtmlContent(client, get);

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return result;
	}
	public String getFilterBugContent(HttpClient client, BugFilterModel filter) {
		String result = "";
		try {
			PostMethod post = new PostMethod("http://easybug.org/Bug/AllBug/17137");
			NameValuePair bugStatus = new NameValuePair("bugStatus", filter.getBugStatus());
			NameValuePair handler = new NameValuePair("handler", filter.getHandler());
			NameValuePair keyWord = new NameValuePair("keyWord", filter.getKeyWord());
			NameValuePair member = new NameValuePair("member", filter.getMember());
			NameValuePair moudle = new NameValuePair("moudle", filter.getMoudle());
			NameValuePair orderBy = new NameValuePair("orderBy", filter.getOrderBy());
			NameValuePair pageIndex = new NameValuePair("pageIndex", filter.getPageIndex());
			NameValuePair pid = new NameValuePair("pid", filter.getPid());
			NameValuePair priority = new NameValuePair("priority", filter.getPriority());
			NameValuePair version = new NameValuePair("version", filter.getVersion());
			post.setRequestBody(new NameValuePair[]{bugStatus, handler, keyWord, member, moudle, orderBy, pageIndex, pid, priority, version});
			
			result = postHtmlContent(client, post);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return result;
	}
	public String postHtmlContent(HttpClient client, PostMethod post){

		String result = "";
		try {
			client.executeMethod(post);
			byte[] buff = StreamTools.readInputStream(post.getResponseBodyAsStream());
			//将返回的内容格式化为String存在html中
			result = new String(buff);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		if(result.contains("ERROR")){
			return "";
		}
		return result;
	}
	public String getHtmlContent(HttpClient client, GetMethod get){

		String result = "";
		try {
			client.executeMethod(get);
			byte[] buff = StreamTools.readInputStream(get.getResponseBodyAsStream());
			//将返回的内容格式化为String存在html中
			result = new String(buff);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		if(result.contains("ERROR")){
			return "";
		}
		return result;
	}
	
	
	public List<BugContentModel> getBugEnvityValues(String htmlString) {
		
		List<BugContentModel> resultList = new ArrayList<BugContentModel>();
		//开始使用Jsoup
		//Jsoup支援一个Document类   将刚才的html转化成Document
		Document document = Jsoup.parse(htmlString);
		//一个Document又由elements组成  我们选择”tr”开头的标签，存入 trs元素群中
		Elements tablbs = document.select("table");
		if(tablbs.size() <= 0 ){
			return resultList;
		}
		Element bugTable = null;
		for(Element table : tablbs){
			if(table.attr("class").contains("Bug box")){
				bugTable = table;
			}
			continue;
		}
		if(null == bugTable){
			return resultList;
		}
		Elements trs = bugTable.select("tr");
		if(trs.size() <= 1 ){
			return resultList;
		}
		for (Element tr : trs){
			if(tr.attr("class").contains("BugTitle")){
				continue;
			}
			Elements tds = tr.select("td");
			if(tds.size() <=0){
				continue;
			}
			resultList.add(new BugContentModel(tds));
		}
		return resultList;
	}
	
	public int getTotalPageNum(String htmlString){
		Pattern p = Pattern.compile("href=\"javascript\\:GO\\([0-9]*\\)");
		Matcher m = p.matcher(htmlString);
		int totalPage = 1;
		try {
			while (m.find()) {
				String orderStr = m.group();
				int startIndex = orderStr.indexOf("(");
				int endIndex = orderStr.indexOf(")");
				int pageNum = Integer.parseInt(orderStr.substring(startIndex, endIndex));
				totalPage = totalPage > pageNum ? totalPage : pageNum;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return totalPage;
	}
	
	public HashMap<String, SelectorModel> getFiltersMap(String htmlString){
		HashMap<String,SelectorModel> filtersMap = new HashMap<String,SelectorModel>();
		Document document = Jsoup.parse(htmlString);
		Elements selects = document.select("select");
		if(selects.size() <= 0 ){
			return filtersMap;
		}
		for (Element eleSelect: selects) {
			if((eleSelect.id() == null ||eleSelect.id().equals(""))
				&&(eleSelect.attr("name") == null  ||eleSelect.attr("name").equals(""))){
				continue;
			}
			String selectKey = eleSelect.id();
			if(null == selectKey || selectKey.equals("")){
				selectKey = eleSelect.attr("name");
			}
			Elements options =eleSelect.select("option");
			if(selects.size() <= 0 ){
				continue;
			}
			ArrayList<OptionModel> optionSet= new ArrayList<OptionModel>();
			for(Element elementOption: options){
				
				String value = elementOption.attr("value");
				String text =  elementOption.text();
				if(null == value){
					continue;
				}
				optionSet.add(new OptionModel(value,text));
			}
			if(optionSet.size()<0){
				continue;
			}
			filtersMap.put(selectKey,new SelectorModel(selectKey,optionSet) );
		}

		//获取排序情况
		Pattern p = Pattern.compile("href=\"javascript:OrderByThis\\(\'\\w*\'\\)");
		Matcher m = p.matcher(htmlString);
		ArrayList<OptionModel> orderOptions =  new ArrayList<>();
		while (m.find()){
			String orderStr = m.group();
			int startkind = orderStr.indexOf("('");
			int endKind = orderStr.indexOf("')");
			String order = orderStr.substring(startkind+2,endKind);
			orderOptions.add(new OptionModel(order,""));
		}
		filtersMap.put("orderBy",new SelectorModel("orderBy",orderOptions));
		
		
		return filtersMap;
	}

}
