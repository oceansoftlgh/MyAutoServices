package com.oceansoft.ghclock.commonutil;

import android.content.Context;
import android.content.SharedPreferences;

public class WorkInfoAgent {
    	
		public static String db = "mytools";
    	/**	
    	 * 保存信息
    	 * @date 2015-8-4 下午3:06:28
    	 */
    	public static boolean  saveRecordInfo(Context context,String key,String value){
    	    try {
				SharedPreferences shareFile = context.getSharedPreferences(db, Context.MODE_PRIVATE);

				SharedPreferences.Editor editor = shareFile.edit();
				editor.putString(key, value);
				editor.commit();
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
    	}
    	
    	/**
    	 * 获取上次保存的工作模式
    	 * @return OffLine 工作模式
    	 * @date 2015-8-4 下午3:06:56
    	 */
    	public static String getRecordInfo(Context context,String key){
    	    	String result = "";
				try{
					SharedPreferences shareFile = context.getSharedPreferences(db, Context.MODE_PRIVATE);
					result = shareFile.getString(key,"");
				}catch (Exception e){
					e.printStackTrace();
					return "";
				}
    	    
    	      return result;
		}
    	
    	
    
}

