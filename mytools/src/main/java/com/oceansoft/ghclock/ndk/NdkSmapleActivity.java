package com.oceansoft.ghclock.ndk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.oceansoft.ndk.ndklib.SecondLib;


/**
 * Created by Administrator on 2015/12/18.
 * 
 * 
 */
 public class NdkSmapleActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
                tv.setTextSize(16);
        setContentView(tv);
        String[] strs = SecondLib.stringMethod();
        tv.setText(strs.toString());
        
    }
}