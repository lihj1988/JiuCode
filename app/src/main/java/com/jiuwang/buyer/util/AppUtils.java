package com.jiuwang.buyer.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.Constant;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class AppUtils {
	private static Boolean isDebug = null;
	private static LoadingDialog mLoadingDialog;


	public static boolean isDebug() {
		return isDebug == null ? false : isDebug.booleanValue();
	}

	public static void syncIsDebug(Context context) {
		if (isDebug == null) {
			isDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		}
	}

	/**
	 * string转成bitmap
	 *
	 * @param st
	 */
	public static Bitmap convertStringToIcon(String st) {
		// OutputStream out;
		Bitmap bitmap = null;
		try {
			// out = new FileOutputStream("/sdcard/aa.jpg");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap =
					BitmapFactory.decodeByteArray(bitmapArray, 0,
							bitmapArray.length);
			// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 没有网络不能进行下一步请求
	 */
	public static boolean getNetworkRequest(Context mActivity) {
		boolean flag_network = false;
		if (isNetworkAvailable((mActivity))) {
			flag_network = true;
		} else {
			AppUtils.showToast("网络异常，请检查网络", mActivity);
		}
		return flag_network;
	}

	/**
	 * 判断网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}


	private static Toast mToast;

	public static void showToast(String text, Context context) {
		if (mToast == null) {
			try {
				mToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
				mToast.setGravity(Gravity.CENTER, 0, 0);
				mToast.show();
			} catch (Exception e) {
				mToast = null;
				return;
			}
		} else {
			try {
				mToast.setText(text);
			} catch (Exception e) {
			}

			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void setEditInit(EditText editView) {
		editView.setFocusable(false);
		editView.setCursorVisible(false);
	}

	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}


	public static boolean isNull(String text) {
		if (text == null || "".equals(text.trim()) || "null".equals(text)
				|| "null".equals(text.trim()) || "<null>".equals(text)) {
			return true;
		}
		return false;
	}

	public static void getFocusable(EditText editView) {
		editView.setCursorVisible(true);
		editView.findFocus();
		editView.setFocusable(true);
		editView.setFocusableInTouchMode(true);
		editView.requestFocus();
	}


	public static String getStatusContract(String status) {
		String statusName = "";
		if ("1".equals(status)) {
			statusName = "未完成 ";
		} else if ("2".equals(status)) {
			statusName = "待支付";
		} else if ("3".equals(status)) {
			statusName = "已过期";
		} else if ("4".equals(status)) {
			statusName = "已生效 ";
		} else if ("5".equals(status)) {
			statusName = "已完成 ";
		} else if ("6".equals(status)) {
			statusName = "已作废 ";
		} else if ("7".equals(status)) {
			statusName = "已终止 ";
		}
		return statusName;
	}

	public static String getPayType(String payType) {
		String payTypeName = "";
		if ("1".equals(payType)) {
			payTypeName = "线下全款";
		} else if ("1".equals(payType)) {
			payTypeName = "预付款支付";
		} else if ("3".equals(payType)) {
			payTypeName = "信用金支付";
		} else if ("4".equals(payType)) {
			payTypeName = "线上支付";
		} else if ("5".equals(payType)) {
			payTypeName = "承兑";
		}
		return payTypeName;
	}

	public static String getStatusBill(String status) {
		String statusName = "";
		if ("0".equals(status)) {
			statusName = "未验单";
		} else if ("1".equals(status)) {
			statusName = "已验单";
		} else if ("2".equals(status)) {
			statusName = "有效提单";
		} else if ("4".equals(status)) {
			statusName = "已撤销 ";
		}
		return statusName;
	}

	public static String getDilveryWay(String status) {
		String dilveryWay = "";
		if ("1".equals(status)) {
			dilveryWay = "自提";
		} else if ("2".equals(status)) {
			dilveryWay = "配送";
		}
		return dilveryWay;
	}

	public static String getStatus(String status) {
		String statusReturn = "";
		if ("0".equals(status)) {
			statusReturn = "草稿";
		} else if ("1".equals(status) || "2".equals(status)) {
			statusReturn = "审核中";
		} else if ("3".equals(status)) {
			statusReturn = "审核通过";
		}
		return statusReturn;
	}

	public static String getTransType(String status) {
		String transType = "";
		if ("1".equals(status)) {
			transType = "汽运";
		} else if ("2".equals(status)) {
			transType = "火运";
		}
		return transType;
	}

	public static String getTransSettleType(String status) {
		String transSettleType = "";
		if ("1".equals(status)) {
			transSettleType = "包到价";
		} else if ("2".equals(status)) {
			transSettleType = "代付";
		} else if ("3".equals(status)) {
			transSettleType = "到付";
		}
		return transSettleType;
	}

	public static String getContrctResult(String status) {
		String contract_result = "";
		if ("0".equals(status)) {
			contract_result = "不通过";
		} else if ("1".equals(status)) {
			contract_result = "审核通过";
		} else if ("2".equals(status)) {
			contract_result = "提交下一节点审核";
		}
		return contract_result;
	}

	public static String getBigContractType(String status) {
		String contractType = "";
		if ("1".equals(status)) {
			contractType = "直销";
		} else if ("2".equals(status)) {
			contractType = "回库";
		}
		return contractType;
	}

	public static String decimalFormat(Double doubleNumber, String flag) {
		DecimalFormat decimalformat = null;
		if ("0".equals(flag)) {
			decimalformat = new DecimalFormat("0.00");
		} else if ("1".equals(flag)) {
			decimalformat = new DecimalFormat("0.000");
		} else if ("2".equals(flag)) {
			decimalformat = new DecimalFormat("#.##");
		} else if ("3".equals(flag)) {
			decimalformat = new DecimalFormat("#.###");
		}
		String returnString = String.valueOf(decimalformat.format(doubleNumber));
		return returnString;
	}


	public static boolean compareEqual(String[] stringArray) {
		Set<String> stringSet = new HashSet<>();//用Set存放不同的字符串
		Map<String, Integer> stringMap = new HashMap<>();//用Map记录相同的元素的个数
		String data;
		for (int i = 0; i < stringArray.length; i++) {
			//取出数组中的数据
			data = stringArray[i];
			//如果Set集合里面有同样的数据，就用Map记录这个数据个数+1
			if (stringSet.contains(data)) {
				stringMap.put(data, stringMap.get(data) + 1);
			} else {
				//否则，如果Set里面没有相同的数据，就放进Set里面，然后用Map记录这个数据个数为1
				stringSet.add(data);//添加不同的数据
				stringMap.put(data, 1);//个数记录为1
			}
		}
		//字符串数组中的""元素的个数
		Object object = stringMap.get("");
		if (object != null) {
			if ((int) object == stringArray.length) {
				return true;
			} else if ((int) object + 1 == stringArray.length) {
				return true;
			}
		}
		if (stringSet.size() == stringArray.length) {
			return true;
		} else {
			return false;
		}
	}

//AppUtils.fileType(fileName)+"/"

	/**
	 * 根据文件名字，判断是文件格式还是 图片格式
	 *
	 * @param name false 是图片格式
	 * true 是文件格式
	 * @return
	 */

	public static boolean isfile(String name) {
		if (!TextUtils.isEmpty(name)) {
			if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".JPG")) {
				return false;
			}
		}
		return true;
	}

	public static String fileType(String filename) {
		String[] split = filename.split("\\.");
		String str = "";
		if ((split != null && split.length > 1)) {
			str = split[split.length - 1];
		} else {
			Toast.makeText(MyApplication.getInstance(), "文件格式不正确！", Toast.LENGTH_LONG).show();
		}
		return str;
	}


//    private static DeleteThread deleteThread;
//    private static boolean isdeleteThreadWorking = false;//判断删除文件的线程是否在执行
//    //超多150个文件删除调用方法，在下载文件之前调用
//    public static void delete() {
//        //检测文件夹中 图片
//        if (!isdeleteThreadWorking) {
//            deleteThread = new DeleteThread();
//            isdeleteThreadWorking = true;
//            deleteThread.start();
//        }
//    }
	/**
	 * 手机中的文件超过150个删除第一个
	 */
//    public static class DeleteThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            File file = new File(NetUrl.directory);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            File[] file2 = file.listFiles();
//            int len = file2.length;
//            if (len >= 150) {
//                for (int i = 0; i < len; i++) {
//                    deleteFile(file2[i].getPath());
//                }
//            }
//            isdeleteThreadWorking = false;
//        }
//    }

	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * @param phoneNumber
	 * @return boolean 返回类型
	 * @Description: 验证号码 手机号 固话均可
	 * @Title: isPhoneNumberValid
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "((^(13|15|14|17|18|19)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
				+ "(^0[3-9] {1}d{2}-?d{7,8}$)|"
				+ "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
				+ "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}


	public static String formatTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = ((ms / (60 * 1000)) - day * 24 * 60 - hour * 60);
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day + "天");
		}
		if (hour > 0) {
			sb.append(hour + "小时");
		}
		if (minute > 0) {
			sb.append(minute + "分");
		}
		if (second > 0) {
			sb.append(second + "秒");
		}
//		if(milliSecond > 0) {
//			sb.append(milliSecond+"毫秒");
//		}
		return sb.toString();
	}

	/**
	 * 验证身份证
	 *
	 * @param
	 * @return
	 * @throws ParseException
	 */
//	public static String IDCardValidate(String IDStr) throws ParseException {
//		String errorInfo = "ok";// 记录错误信息
//		String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
//				"3", "2"};
//		String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
//				"9", "10", "5", "8", "4", "2"};
//		String Ai = "";
//		// ================ 号码的长度 15位或18位 ================
//		if (IDStr.length() != 15 && IDStr.length() != 18) {
//			errorInfo = "身份证号码长度应该为15位或18位。";
//			return errorInfo;
//		}
//		// =======================(end)========================
//		// ================ 数字 除最后以为都为数字 ================
//		if (IDStr.length() == 18) {
//			Ai = IDStr.substring(0, 17);
//		} else if (IDStr.length() == 15) {
//			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
//		}
//		if (isNumeric(Ai) == false) {
//			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
//			return errorInfo;
//		}
//		// =======================(end)========================
//		// ================ 出生年月是否有效 ================
//		String strYear = Ai.substring(6, 10);// 年份
//		String strMonth = Ai.substring(10, 12);// 月份
//		String strDay = Ai.substring(12, 14);// 月份
//		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
//			errorInfo = "身份证生日无效。";
//			return errorInfo;
//		}
//		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
//			errorInfo = "身份证月份无效";
//			return errorInfo;
//		}
//		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
//			errorInfo = "身份证日期无效";
//			return errorInfo;
//		}
//		// =====================(end)=====================
//		// ================ 地区码时候有效 ================
//		Hashtable h = GetAreaCode();
//		if (h.get(Ai.substring(0, 2)) == null) {
//			errorInfo = "身份证地区编码错误。";
//			return errorInfo;
//		}
//		// ==============================================
//		// ================ 判断最后一位的值 ================
//		int TotalmulAiWi = 0;
//		for (int i = 0; i < 17; i++) {
//			TotalmulAiWi = TotalmulAiWi
//					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
//					* Integer.parseInt(Wi[i]);
//		}
//		int modValue = TotalmulAiWi % 11;
//		String strVerifyCode = ValCodeArr[modValue];
//		Ai = Ai + strVerifyCode;
//		if (IDStr.length() == 18) {
//			if (Ai.equals(IDStr) == false) {
//				errorInfo = "身份证无效，不是合法的身份证号码";
//				return errorInfo;
//			}
//		} else {
//			errorInfo = "ok";
//			return errorInfo;
//		}
//		// =====================(end)=====================
//		return errorInfo;
//	}
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	public static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 车牌号校验
	 *
	 * @param carnumber
	 * @return
	 */
	public static boolean isCarnumberNO(String carnumber) {
	 /*
     车牌号格式：汉字 + A-Z + 位A-Z或-
     （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
     */
		String carnumRegex = "[\\u4e00-\\u9fa5][a-zA-Z](([DF](?![a-zA-Z0-9]*[IO])[0-9]{4})|([0-9]{5}[DF]))|^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使领A-Z]{1}[a-zA-Z0-9]{5}[a-zA-Z0-9挂学警港澳]{1}$";
		if ("".equals(carnumber)) return false;
		else return carnumber.matches(carnumRegex);
	}

	//设置xlistview  的分割线处理
	public static void listLinePadding(int listSize, LinearLayout llAll, int position) {
		if (listSize == 1) {
			llAll.setPadding(24, 24, 24, 24);
		} else if (listSize == 2) {
			if (position == 0) {
				llAll.setPadding(24, 24, 24, 12);
			} else if (position == listSize - 1) {
				llAll.setPadding(24, 12, 24, 24);
			}
		} else {
			if (position == listSize - 1) {
				llAll.setPadding(24, 12, 24, 24);
			} else if (position == 0) {
				llAll.setPadding(24, 24, 24, 12);
			} else {
				llAll.setPadding(24, 12, 24, 12);
			}
		}
	}

	public static void initGridViewNothing(int column, XRecyclerView xRecyclerView) {
		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(column,
				StaggeredGridLayoutManager.VERTICAL);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		xRecyclerView.setLayoutManager(layoutManager);
		xRecyclerView.setLoadingMoreEnabled(false);
		xRecyclerView.setPullRefreshEnabled(false);
	}

	public static void initListView(Context context, XRecyclerView xRecyclerView, boolean refresh, boolean load) {
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		xRecyclerView.setLayoutManager(layoutManager);
		xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
		xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
		xRecyclerView.setPullRefreshEnabled(refresh);
		xRecyclerView.setLoadingMoreEnabled(load);
	}

	public static void getReadUriPermission() {
		//Intent intent = new Intent(Intent.ACTION_VIEW);
		Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			File file = FileUtil.updateFile;
			Uri contentUri = FileProvider.getUriForFile(MyApplication.getInstance(), "com.jiuwang.buyer.provider", file);
			//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(FileUtil.updateFile), "application/vnd.android.package-archive");
		}
		MyApplication.getInstance().startActivity(intent);
	}

	public static LoadingDialog setDialog_wait(Context context, String number) {
		LoadingDialog mLoadingDialog = new LoadingDialog(context);
		if ("1".equals(number)) {
			mLoadingDialog.setText("正在加载");
		}
		if ("2".equals(number)) {
			mLoadingDialog.setText("正在登陆");
		}
		if ("3".equals(number)) {
			mLoadingDialog.setText("正在加载");
		}
		if ("4".equals(number)) {
			mLoadingDialog.setText("正在加载");
		}
		mLoadingDialog.setCancelEnable(true);
		mLoadingDialog.show();
		return mLoadingDialog;
	}

	// 获得当前 版本
	public static void getSystemVersion(final Activity activity, final PermissionsUtils.IPermissionsResult permissionsResult, final String come_from) {
//		if (AppUtils.getNetworkRequest(activity)) {
//			mLoadingDialog = AppUtils.setDialog_wait(activity, "1");
//			HashMap<String, String> map = new HashMap<>();
//			HttpUtils.version(map, new Consumer<BaseResultEntity>() {
//				@Override
//				public void accept(BaseResultEntity baseResultEntity) throws Exception {
//					if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
////								HomeEntity homeEntity = baseEntity.getResult();
////								application.url_langde = homeEntity.getDownload_url();
////								Constant.serverVersion = homeEntity.getVersion_android();
//								checkVersion(activity, permissionsResult, come_from);
//							} else {
////								AppUtils.showToast(baseEntity.getMsg(), activity);
//							}
//							mLoadingDialog.dismiss();
//				}
//			}, new Consumer<Throwable>() {
//				@Override
//				public void accept(Throwable throwable) throws Exception {
//					mLoadingDialog.dismiss();
//				}
//			});
//
//		}
	}

	public static void checkVersion(final Activity activity, final PermissionsUtils.IPermissionsResult permissionsResult, String come_from) {
		if (!AppUtils.isNull(Constant.localVersion)
				&& !AppUtils
				.isNull(Constant.serverVersion)) {
			if (Constant.localVersion
					.compareTo(Constant.serverVersion) < 0) {
				// 发现新版本，提示用户更新
				final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.styletest).create();
				dialog.show();
				Window window = dialog.getWindow();
				window.setContentView(R.layout.dialog_normal_one_button);
				RelativeLayout relative_button2 = (RelativeLayout) window.findViewById(R.id.relative_button2);
				Button bt1 = (Button) window.findViewById(R.id.bt1_quxiao);
				Button bt2 = (Button) window.findViewById(R.id.bt2_queding);
				TextView title = (TextView) window.findViewById(R.id.title);
				title.setText("软件升级");
				TextView tv_context = (TextView) window
						.findViewById(R.id.tv_content);
				tv_context.setText("发现新版本,建议立即更新使用.");
				relative_button2.setVisibility(View.VISIBLE);
				bt1.setText("取消");
				bt2.setText("确定");
				bt1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
				bt2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String[] permissions = new String[]{"android.permission.READ_EXTERNAL_STORAGE",
								"android.permission.WRITE_EXTERNAL_STORAGE"};
						PermissionsUtils.getInstance().chekPermissions(activity, permissions, permissionsResult);
						dialog.cancel();
					}
				});
			} else {
				if ("2".equals(come_from)) {
					AppUtils.showToast("您当前版本已是最新", activity);
				}
			}
		}
	}


	public static void showDialog(final Activity activity, String titleText, String content, final DialogClickInterface dialogClickInterface) {

		final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.styletest).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_normal_one_button);
		RelativeLayout relative_button2 = (RelativeLayout) window.findViewById(R.id.relative_button2);
		Button bt1 = (Button) window.findViewById(R.id.bt1_quxiao);
		Button bt2 = (Button) window.findViewById(R.id.bt2_queding);
		TextView title = (TextView) window.findViewById(R.id.title);
		title.setText(titleText);
		TextView tv_context = (TextView) window
				.findViewById(R.id.tv_content);
		tv_context.setText(content);
		relative_button2.setVisibility(View.VISIBLE);
		bt1.setText("取消");
		bt2.setText("确定");
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
				dialogClickInterface.onClick();

			}
		});
	}




}

