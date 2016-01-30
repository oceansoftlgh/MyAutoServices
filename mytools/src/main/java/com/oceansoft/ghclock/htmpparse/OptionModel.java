package com.oceansoft.ghclock.htmpparse;

/**
 * Created by Administrator on 2016/1/27.
 */
public class OptionModel {
	String value;
	String text;

	public OptionModel(String value, String text){
		this.value = value;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return value+":"+text;
	}
}
