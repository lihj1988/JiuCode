package com.jiuwang.buyer.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MyToastView {
	private static Toast mToast;
	public static final int AIRPLAY_MESSAGE_HIDE_TOAST = 1;
	/**
	 * @param text
	 * @param context
	 */
	public static void showToast(String text,Context context) {    
        if(mToast == null) {    
        	try{
                mToast = Toast.makeText(context.getApplicationContext(), text,Toast.LENGTH_LONG);    
                mToast.setGravity(Gravity.CENTER, 0, 0);  
                mToast.show(); 
        	}catch(Exception e){
        		mToast=null;
        		return;
        	}
        } else {    
        	try{
           mToast.setText(text);      
			}catch(Exception e){
			}
         
            mToast.setDuration(Toast.LENGTH_SHORT);    
        }    
        mToast.show();   
    } 

	

	
}
