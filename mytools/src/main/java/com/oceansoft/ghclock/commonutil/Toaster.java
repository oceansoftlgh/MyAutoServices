package com.oceansoft.ghclock.commonutil;

import android.content.Context;
import android.widget.Toast;

/** 
 * 防止多次Toast出现提示一直不消失的情况 
 * @date 2015-7-23 上午11:43:17
 */
public class Toaster {
    
    private static Toast mToast = null;  
    
      public static void showToast(Context context,String text) {  
            if(mToast == null) {  
                mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);  
            } else {  
                mToast.setText(text);         
                mToast.setDuration(Toast.LENGTH_SHORT);  
            }  
           mToast.show();  
      }  
     
       public static void cancelToast() {  
              if (mToast != null) {  
                    mToast.cancel();  
               }  
       } 

}
