package com.oceansoft.ghclock.htmpparse;

import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2016/1/27.
 */
public class BugContentModel {
	String title;
	String priority;
	String bugStat;
	String member;
	String moudle;
	String version;
	String date;
	public BugContentModel(Elements tds){
		int len = tds.size();
		if(len >= 1){
			title = tds.get(1).text();
		}
		if(len>= 2){
			priority = tds.get(2).text();
		}if(len>= 3){
			bugStat = tds.get(3).text();
		}if(len>= 4){
			member = tds.get(4).text();
		}if(len>= 5){
			moudle = tds.get(5).text();
		}if(len>= 6){
			version = tds.get(6).text();
		}if(len>= 7){
			date = tds.get(7).text();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getBugStat() {
		return bugStat;
	}

	public void setBugStat(String bugStat) {
		this.bugStat = bugStat;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
