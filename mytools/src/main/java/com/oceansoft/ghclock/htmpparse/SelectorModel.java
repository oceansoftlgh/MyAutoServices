package com.oceansoft.ghclock.htmpparse;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/27.
 */
public class SelectorModel {
	String name;
	ArrayList<OptionModel> options;
	public SelectorModel(String name, ArrayList<OptionModel> optionSet){
		this.name = name;
		if(optionSet.size() > 0) {
			this.options = optionSet;
		}else
		{
			this.options = new ArrayList<OptionModel>();
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<OptionModel> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<OptionModel> options) {
		this.options = options;
	}
}
