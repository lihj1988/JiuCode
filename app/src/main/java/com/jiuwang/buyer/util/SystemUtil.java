package com.jiuwang.buyer.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.jiuwang.buyer.base.MyApplication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class SystemUtil {

	    // 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN” 
	   
	    public static String getSystemLanguage() {  
	        return Locale.getDefault().getLanguage();  
	    }  

	    /*
	     * 2018/03/16 
	     * 获取设备Id
	     */
	    public static String getDeviceId() {
			
	    	TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getDeviceId();
		}
	    /**
	     * 2018/03/16 
	     * App版本号
	     * @return
	     */
	    public static String getAppVersion() {
	    	PackageInfo packageInfo = null;
			try {
				packageInfo = MyApplication.getInstance()
						.getPackageManager().getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	    	return packageInfo.versionName;
	    }

	    /** 
	     * 获取当前系统上的语言列表(Locale列表) 
	     * 
	     * @return  语言列表 
	     */  
	    public static Locale[] getSystemLanguageList() {  
	        return Locale.getAvailableLocales();  
	    }  
	  
	    /** 
	     * 获取当前手机系统版本号 
	     * 
	     * @return  系统版本号 
	     */  
	    public static String getSystemVersion() {  
	        return android.os.Build.VERSION.RELEASE;  
	    }  
	  
	    /** 
	     * 获取手机型号 
	     * 
	     * @return  手机型号 
	     */  
	    public static String getSystemModel() {  
	        return android.os.Build.MODEL;  
	    }  
	  
	    /** 
	     * 获取手机厂商 
	     * 
	     * @return  手机厂商 
	     */  
	    public static String getDeviceBrand() {  
	        return android.os.Build.BRAND;  
	    }  
	  
	    /** 
	     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限) 
	     * 
	     * @return  手机IMEI 
	     */  
	    public static String getIMEI(Context ctx) {  
	        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);  
	        if (tm != null) {  
	            return tm.getDeviceId();  
	        }  
	        return null;  
	    }  
	    
	    public static String getPhoneInfo(Context ctx) {  
	    	
	    	return getDeviceBrand()+"-"+getSystemVersion()+"-"+getSystemModel()+"-";
	    }



	public static String getIpAddressString() {
		try {
			for (Enumeration<NetworkInterface> enNetI = NetworkInterface
					.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
				NetworkInterface netI = enNetI.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = netI
						.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "0.0.0.0";
	}
}
