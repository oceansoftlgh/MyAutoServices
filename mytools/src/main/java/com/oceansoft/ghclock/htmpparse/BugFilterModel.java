package com.oceansoft.ghclock.htmpparse;

/**
 * Created by Administrator on 2016/1/27.
 */
public class BugFilterModel {
	String bugStatus;
	String handler;
	String keyWord = "";
	String member;
	String moudle;
	String orderBy = "";
	String pageIndex;
	String pid = "17173";
	String priority;

	public String getVersion() {
		
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}

	String version;
	
	public BugFilterModel(){
		this.bugStatus = "0";
		this.handler = "0";
		this.keyWord ="";
		this.member = "0";
		this.moudle = "0";
		this.orderBy = "";
		this.pageIndex = "1";
		this.pid = "17173";
		this.priority = "0";
		this.version = "0";
	}
	
}
