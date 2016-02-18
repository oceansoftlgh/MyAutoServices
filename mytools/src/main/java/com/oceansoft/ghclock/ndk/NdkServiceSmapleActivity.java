package com.oceansoft.ghclock.ndk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oceansoft.ghclock.R;
import com.oceansoft.ndk.ndklib.NativeRuntime;

public class NdkServiceSmapleActivity extends Activity {
    
    TextView text1, text2;
    TextView mTextView01;
    Button btnstart,btnend;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_ndkservice_smaple_main);
            Intent ii = new Intent(getApplicationContext(), ScreenService.class);
            startService(ii);
           text1 = (TextView) findViewById(R.id.tv_ndkservice_main_text1);
           text2 = (TextView) findViewById(R.id.tv_ndkservice_main_text2);
           mTextView01 = (TextView) findViewById(R.id.tv_ndkservice_main_text3);
           btnstart = (Button)findViewById(R.id.btn_ndkservice_main_btnstart);
           btnend = (Button)findViewById(R.id.btn_ndkservice_main_btnend);
            
            text1.setText(NativeRuntime.getInstance().stringFromJNI());

            String executable = "libCommonNdk.so";
            String aliasfile = "helper";
            String parafind = "/data/data/" + getPackageName() + "/" + aliasfile;
           
            String retx ="false";//+ (1==NativeRuntime.getInstance().findProcess(parafind));
            if (retx.length()>0 && !retx.substring(0, 4).equalsIgnoreCase("true")) {
                String r = NativeRuntime.getInstance().RunExecutable(
                        getPackageName(), executable, aliasfile,
                        getPackageName()+"/");
                text2.setText(r);
                mTextView01.setText("create new process " + "true");
            } else {
                mTextView01.setText("exist " + retx.substring(4) + " reload");
                NativeRuntime.getInstance().RunLocalUserCommand(getPackageName(),
                        "kill -9 " + retx.substring(4), null);
                NativeRuntime.getInstance().RunExecutable(getPackageName(),
                        executable, aliasfile, getPackageName()+"/com.oceansoft.ghclock.ndk.HostMonitor");
            }
            
            btnstart.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    (new Thread(new Runnable() {
                        
                        @Override
                        public void run() {
                            try {
                                NativeRuntime.getInstance().startService(getPackageName()+"/com.oceansoft.ghclock.ndk.HostMonitor");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })).start();
                }
            });
            
            btnend.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    try {
                        NativeRuntime.getInstance().stopService();
                    } catch (Exception e) {
                        e.printStackTrace();;
                    }
                }
            });
            (new Thread(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        //NativeRuntime.getInstance().startService(getPackageName()+"/com.qigame.lock.helper.HostMonitor");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            })).start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    
     
}
