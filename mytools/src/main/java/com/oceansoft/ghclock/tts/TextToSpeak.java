package com.oceansoft.ghclock.tts;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextToSpeak {
	private TextToSpeech mTextToSpeech = null; 
	public TextToSpeak(Context context, final String text){
		mTextToSpeech = new TextToSpeech(context,new TextToSpeech.OnInitListener()
	    {
			public void onInit(int status) 
	        {
	           try {
	        	   if(status == TextToSpeech.SUCCESS)
		            {
		                //设置朗读语言
		                int supported = mTextToSpeech.setLanguage(Locale.CHINA);
		                if((supported != TextToSpeech.LANG_AVAILABLE)&&(supported != TextToSpeech.LANG_COUNTRY_AVAILABLE))
		                {
		                	//Toast.makeText(context, "不支持当前语言！", Toast.LENGTH_SHORT).show();
		                }
		                mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
		            }
				} catch (Exception e) {
					// TODO: handle exception
				}
	           
	       }
	    });  
	}
	
}
