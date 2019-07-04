package com.jiuwang.buyer.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.activity.WelcomeActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.NewsEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.view.ADInfo;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.functions.Consumer;


public class CommonUtil {
	//    private static LoadingDialog mLoadingDialog;
	private static WelcomeActivity activity;
	private static LoginActivity lactivity;

	private static Boolean isDebug = null;

	public static boolean isDebug() {
		return isDebug == null ? false : isDebug.booleanValue();
	}

	public static void syncIsDebug(Context context) {
		if (isDebug == null) {
			isDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		}
	}

	public static String splitString(String money) {
		if (!TextUtils.isEmpty(money)) {
			String[] split1 = money.split("\\.");
			if (split1.length > 0) {
				if (split1.length == 1) {
					money = money + ".00";
				} else {
					if (split1[1].length() == 1) {
						money = money + "0";
					}
				}
			}
		}
		return money;
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

	public static String getBigContractType(String status) {
		String contractType = "";
		if ("1".equals(status)) {
			contractType = "直销";
		} else if ("2".equals(status)) {
			contractType = "回库";
		}
		return contractType;
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

	/**
	 * double保留两位小数
	 *
	 * @param num
	 * @return
	 */
	public static double getTwoDecimal(int number, double num) {
		DecimalFormat dFormat = null;
		if (number == 2) {
			dFormat = new DecimalFormat("#.00");
		} else if (number == 3) {
			dFormat = new DecimalFormat("#.000");
		} else if (number == 4) {
			dFormat = new DecimalFormat("#.0000");
		}
		String yearString = dFormat.format(num);
		Double temp = Double.valueOf(yearString);
		return temp;
	}


	/**
	 * 没有网络不能进行下一步请求
	 */
	public static boolean getNetworkRequest(Context mActivity) {
		boolean flag_network = false;
		if (isNetworkAvailable((mActivity))) {
			flag_network = true;
		} else {
			MyToastView.showToast("网络异常，请检查网络", mActivity);
		}
		return flag_network;
	}


	//判断字符串是不是以数字开头
	public static boolean isStartWithNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str.charAt(0) + "");
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	//非负的整数和小数
	public static boolean numberCheck(String str) {
		Pattern pattern = Pattern.compile("[1-9]\\d*\\.?\\d*");
		Matcher isNum = pattern.matcher(str.charAt(0) + "");
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}


	/**
	 * 判断网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

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

	public static boolean IsForNet(String result) {
		String msgcode = null;
		String msg = null;
		try {
			JSONObject jsonOBject = new JSONObject(result);
			msgcode = jsonOBject.getString("msgcode");
			msg = jsonOBject.getString("msg");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (msgcode == null || msgcode.equals("0")) {
			MyToastView.showToast(msg, MyApplication.getInstance());
			return false;
		}
		return true;
	}

	/**
	 * 登录失败结果
	 */
	public static String getServierInfosParser(String result) {
		String msg = null;
		String results = null;
		try {
			JSONObject jsonOBject = new JSONObject(result);
			results = jsonOBject.getString("msgcode");
			msg = jsonOBject.getString("msg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (results == null) {
			return "访问失败";
		}
		if (results.equals("0") || results.equals("0") || results.equals("0")) {
			if (msg == null) {
				return "访问失败";
			} else if (msg.equals("null")) {
				return "访问失败";
			}
			return msg;
		}
		return msg;

	}

	public static void setEditInit(EditText editView) {
		editView.setFocusable(false);
		editView.setCursorVisible(false);
	}

	public static void getFocusable(EditText editView) {
		editView.setCursorVisible(true);
		editView.findFocus();
		editView.setFocusable(true);
		editView.setFocusableInTouchMode(true);
		editView.requestFocus();
	}

	/*
	 * 判断字符串是否为空
	 *
	 * @param text
	 *
	 * @return true null false !null
	 */
	public static boolean isNull(String text) {
		if (text == null || "".equals(text.trim()) || "null".equals(text)
				|| "null".equals(text.trim()) || "<null>".equals(text)) {
			return true;
		}
		return false;
	}

	// 判断JSON返回是否为空
	public static String getStringNodeValue(JSONObject o, String name) {
		// TODO Auto-generated method stub
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getString(name) : "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 判断JSON返回是否为空
	public static JSONObject getStringNoValue(JSONObject o, String name) {
		// TODO Auto-generated method stub
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getJSONObject(name) : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JSONObject getStringJsonObjectValue(JSONObject o, String name) {
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getJSONObject(name) : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean getAppSatus(Context context, String pageName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);
		//判断程序是否在栈顶
		if (list.get(0).topActivity.getPackageName().equals(pageName)) {
			return true;
		} else {
			//判断程序是否在栈里
			for (ActivityManager.RunningTaskInfo info : list) {
				if (info.topActivity.getPackageName().equals(pageName)) {
					return true;
				}
			}
			return false;//栈里找不到，返回false
		}
	}


	/**
	 * 开启activity
	 */
	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

//    public static LoadingDialog setDialog_wait(Context context, String number) {
//        // TODO Auto-generated method stub
//        mLoadingDialog = new LoadingDialog(context);
//        if (mLoadingDialog == null) {
//            mLoadingDialog = new LoadingDialog(context);
//        }
//        if ("1".equals(number)) {
//            mLoadingDialog.setText("正在加载车辆信息");
//        }
//        if ("2".equals(number)) {
//            mLoadingDialog.setText("正在登陆");
//        }
//        if ("3".equals(number)) {
//            mLoadingDialog.setText("正在加载地址信息");
//        }
//        if ("4".equals(number)) {
//            mLoadingDialog.setText("正在加载历史运输单");
//        }
//        if ("5".equals(number)) {
//            mLoadingDialog.setText("正在加载个人信息");
//        }
//        if ("6".equals(number)) {
//            mLoadingDialog.setText("正在加载车长信息");
//        }
//        if ("7".equals(number)) {
//            mLoadingDialog.setText("正在加载车型信息");
//        }
//        if ("8".equals(number)) {
//            mLoadingDialog.setText("正在加载");
//        }
//        if ("9".equals(number)) {
//            mLoadingDialog.setText("正在加载");
//        }
//        if ("10".equals(number)) {
//            mLoadingDialog.setText("正在加载");
//        }
//        if ("11".equals(number)) {
//            mLoadingDialog.setText("正在加载我的常用路线信息");
//        }
//        if ("12".equals(number)) {
//            mLoadingDialog.setText("正在加载");
//        }
//        if ("13".equals(number)) {
//            mLoadingDialog.setText("正在注册");
//        }
//        if ("14".equals(number)) {
//            mLoadingDialog.setText("正在调度");
//        }
//        if ("15".equals(number)) {
//            mLoadingDialog.setText("正在加载调度明细");
//        }
//        if ("16".equals(number)) {
//            mLoadingDialog.setText("正在撤销调度单");
//        }
//        if ("17".equals(number)) {
//            mLoadingDialog.setText("正在登陆...");
//        }
//        mLoadingDialog.setCancelEnable(true);
//        mLoadingDialog.show();
//        return mLoadingDialog;
//    }

	public static boolean isWeight(String doubleNumber) {
		// String numRegex = "(([1-9][0-9]*)\\.([0-9]{2}))|[0]\\.([0-9]{2})";
		String numRegex_number = "^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$";
		String numRegex = "^[\\d&&[^0]]{1}$";
		if (TextUtils.isEmpty(doubleNumber)) {
			return false;
		} else {
			return doubleNumber.matches(numRegex_number)
					|| doubleNumber.matches(numRegex);
		}
	}


	public static boolean isQuantityt(String quantity) {
		String numRegex_number = "^\\+?[1-9][0-9]*$";
		if (TextUtils.isEmpty(quantity)) {
			return false;
		} else {
			return quantity.matches(numRegex_number);
		}
	}

	/**
	 * 手机号格式正确
	 */
	public static boolean isMobileNO(String mobiles) {
	            /*
	             * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
				 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
				 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
				 */
		// String telRegex = "[1][34578]\\d{9}";//
		// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		String telRegex = "^13[0-9]{1}[0-9]{8}|^15[0-9]{1}[0-9]{8}|^17[0-9]{1}[0-9]{8}|^18[0-9]{1}[0-9]{8}|^14[0-9]{1}[0-9]{8}";
		if (TextUtils.isEmpty(mobiles)) {
			return false;
		} else {
			return mobiles.matches(telRegex);
		}
	}

	// 1.欢迎页面调用服务器版本。2.登录页面调用获得服务器当前时间。
	public static boolean getNetworkRequest_welcome(Context mActivity,
	                                                String number) {
		boolean flag_network = false;
		if (isNetworkAvailable((mActivity))) {
			flag_network = true;
		} else {
//            noNet_dialog(mActivity, number);
		}
		return flag_network;
	}

//    protected static void noNet_dialog(final Context mActivity,
//                                       final String number) {
//        // TODO Auto-generated method stub
//        final AlertDialog dlg = new AlertDialog.Builder(mActivity).create();
//        dlg.show();
//        Window window = dlg.getWindow();
//        window.setContentView(R.layout.dialog_prompt);
//
//        if ("1".equals(number) || "3".equals(number)) {
//            activity = (WelcomeActivity) mActivity;
//        }
//
//        if ("2".equals(number)) {
//            lactivity = (LoginActivity) mActivity;
//        }
//
//        TextView tv_grap = (TextView) window.findViewById(R.id.content_update);
//        tv_grap.setText("网络异常请检查网络");
//        Button bt1 = (Button) window.findViewById(R.id.bt1_quxiao);
//        bt1.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                dlg.cancel();
//                if ("1".equals(number)) {
//                    //	activity = (WelcomeActivity) mActivity;
//                    activity.finish();
//                }
//                if ("2".equals(number)) {
//                    //lactivity = (LoginActivity) mActivity;
//                    lactivity.finish();
//                }
//                if ("3".equals(number)) {
//                    activity.finish();
//                }
//            }
//        });
//        Button bt2 = (Button) window.findViewById(R.id.bt2_queding);
//        bt2.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if ("1".equals(number)) {
//                    //activity = (WelcomeActivity) mActivity;
////                    activity.setSystemVersion();
//                }
//                if ("2".equals(number)) {
//                    //lactivity = (LoginActivity) mActivity;
//                    //	lactivity.getCurrentTimes();
//                }
//                if ("3".equals(number)) {
//                    lactivity.getToken();
//                }
//
//            }
//        });
//
//    }

	public static String getFormat(int number_point, double doubleNumber) {
		DecimalFormat df = null;
		if (number_point == 2) {
			df = new DecimalFormat("#.00");
		} else if (number_point == 3) {
			df = new DecimalFormat("#.000");
		} else if (number_point == 4) {
			df = new DecimalFormat("#.0000");
		}
		return df.format(doubleNumber);
	}

	public static String decimalFormat(Double doubleNumber, String flag) {
		DecimalFormat decimalformat = null;
		if ("0".equals(flag)) {
			decimalformat = new DecimalFormat("0.00");
		} else if ("1".equals(flag)) {
			decimalformat = new DecimalFormat("0.000");
		} else if ("2".equals(flag)) {
			decimalformat = new DecimalFormat("#.##");
		}
		String returnString = String.valueOf(decimalformat.format(doubleNumber));
		return returnString;
	}

	/**
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.add(b2).doubleValue());
	}


	public static final int NEWS_IMAGE = 9;//获得新闻资讯页面广告
//    public static Document document;
//
//    public static void refreshHtmlContent(final String html, final WebView webView) {
//        document = Jsoup.parse(html);
//        String body = document.toString();
//        // webView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
//    }


	public static ArrayList<NewsEntity.ResultBean.NewsEntityDatas> getNewsAddImage() {
		return carouselFigureListNews;
	}


	private static List<NewsEntity.ResultBean.NewsEntityDatas> newsList;
	public static ArrayList<NewsEntity.ResultBean.NewsEntityDatas> carouselFigureListNews;
	private static HashMap<String, List<NewsEntity.ResultBean.NewsEntityDatas>> mapList_all = new HashMap<String, List<NewsEntity.ResultBean.NewsEntityDatas>>();
	private static HashMap<String, Adapter> map_adapter = new HashMap<>();

	// 1.1 获取新闻资讯接口
//    public static void getNewsData(final Activity activity,
//                                   final String newsType, final String infoType, final String page,
//                                   final XListView xListView, final String mTitle, final int listSize,
//                                   String userCode, final Handler handler) {
//        if (CommonUtil.getNetworkRequest(activity)) {
////            mLoadingDialog = CommonUtil.setDialog_wait(activity, "10");
//            LoginManager.getLoginManager().getNewsData(newsType, infoType, page, userCode, new AsyncHttpResponseHandler(activity) {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
////                    mLoadingDialog.dismiss();
//                    Gson gson = new Gson();
//                    NewsEntity newsEntity = gson.fromJson(new String(responseBody), NewsEntity.class);
//                    if (newsEntity.getResult() != null) {
//                        newsList = newsEntity.getResult().getData();
//                        carouselFigureListNews = newsEntity.getResult().getCarouselFigureList();
//                        handler.sendEmptyMessage(NEWS_IMAGE);
//                        if ("1".equals(page)) {
//                            for (int i = 0; i < listSize; i++) {
//                                String position = String.valueOf(i);
//                                String position1 = String
//                                        .valueOf(i + 1);
//                                boolean find = false;
//                                if (position1.equals(infoType)) {
//                                    mapList_all.put(position, newsList);
//                                    setNewsDataAdapterPositon(activity,
//                                            xListView, infoType,
//                                            mTitle, newsList, position);
//                                    find = true;
//                                }
//                                if (find) {
//                                    break;
//                                }
//                            }
//                        } else {
//                            for (int i = 0; i < listSize; i++) {
//                                String positon = String.valueOf(i);
//                                String positon1 = String.valueOf(i + 1);
//                                boolean find = false;
//                                if (positon1.equals(infoType)) {
//                                    List<NewsEntity.ResultBean.NewsEntityDatas> newList_positon = mapList_all
//                                            .get(positon);
//                                    newList_positon.addAll(newsList);
//                                    mapList_all.put(positon,
//                                            newList_positon);
//                                    NewsHeadlineAdaptr newsHeadlineAdapter_position = (NewsHeadlineAdaptr) map_adapter
//                                            .get(positon);
//                                    newsHeadlineAdapter_position.notifyDataSetChanged();
//                                    find = true;
//                                }
//                                if (find) {
//                                    break;
//                                }
//                            }
//                        }
//
//                    } else {
//                        Toast.makeText(activity, newsEntity.getMsg(), Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers,
//                                      byte[] responseBody, Throwable error) {
//                    super.onFailure(statusCode, headers, responseBody,
//                            error);
////                    mLoadingDialog.dismiss();
//
//                }
//
//                @Override
//                public void onFinish() {
//                    super.onFinish();
////                    mLoadingDialog.dismiss();
//                }
//            });
//        }
//
//    }

	public static ArrayList<ADInfo> toAdinfo(ArrayList<NewsEntity.ResultBean.NewsEntityDatas> carouselFigureList) {
		ArrayList<ADInfo> infos = new ArrayList();
		for (int i = 0; i < carouselFigureList.size(); i++) {
			ADInfo info = new ADInfo();
			info.setUrl(carouselFigureList.get(i).getImage());
			info.setContent(carouselFigureList.get(i).getContent());
			infos.add(info);
		}
		return infos;
	}


//    private static void setNewsDataAdapterPositon(Activity activity,
//                                                  XListView xListView, String infoType, String mTitle,
//                                                  List<NewsEntity.ResultBean.NewsEntityDatas> newList_positon, String position) {
//        NewsHeadlineAdaptr newsHeadlineAdapter_position = new NewsHeadlineAdaptr(
//                activity, newList_positon, mTitle, infoType, "");
//        xListView.setAdapter(newsHeadlineAdapter_position);
//        xListView.setOnItemClickListener(newsHeadlineAdapter_position);
//        map_adapter.put(position, newsHeadlineAdapter_position);
//    }


	//修改webView加载字体大小
	private static int mCurrentChooseItem;
	private static int mCurrentItem = 1;

	public static void showChooseDialog(final Activity activity, final WebView webView, final String type_detailed) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		String[] items = new String[]{"大号字体", "正常字体", "小号字体"};
		builder.setTitle("字体判断");
		builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mCurrentChooseItem = which;
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			/*   WebSettings settings = webView.getSettings();*/
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (mCurrentChooseItem) {
					case 0:
						if ("3".equals(type_detailed)) {
							setDoumentSize("60", webView);
						} else {
							setDoumentSize("16", webView);
						}
						break;
					case 1:
						if ("3".equals(type_detailed)) {
							setDoumentSize("45", webView);
						} else {
							setDoumentSize("14", webView);
						}
						break;
					case 2:
						if ("3".equals(type_detailed)) {
							setDoumentSize("30", webView);
						} else {
							setDoumentSize("12", webView);
						}
						break;
					default:
						break;
				}
				//保存用户选择的状态
				mCurrentItem = mCurrentChooseItem;
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	private static void setDoumentSize(String size, WebView webView) {
		String fontSize = size + "pt';}()";
		String loadUrl = "javascript:!function(){document.getElementById('contentmain').style['font-size']='" + fontSize;
		webView.loadUrl(loadUrl);
	}

	private static Dialog dialog;// 对话框

//    public static void showShareDialog(Activity activity, IWXAPI api,
//                                       Tencent mTencent, String newsTitle, String author, String htmlUrl,
//                                       String flag_shareQQurlOrText) {
//        dialog = new Dialog(activity, R.style.custom_dialog);
//        dialog.setCanceledOnTouchOutside(true);
//        // 获取对话框的窗口，并设置窗口参数
//        Window win = dialog.getWindow();
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        // 不能写成这样,否则Dialog显示不出来
//        // LayoutParams params = new
//        // LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
//        // 对话框窗口的宽和高
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        // 对话框窗口显示的位置
//        params.x = 120;
//        params.y = 100;
//        win.setAttributes(params);
//        // 设置对话框布局
//        dialog.setContentView(R.layout.custom_dialog);
//        GridView gridView_1 = (GridView) dialog.findViewById(R.id.gridView);
//        gridView_1.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        setAdapter(gridView_1, activity, api, mTencent, newsTitle, author,
//                htmlUrl, flag_shareQQurlOrText);
//        Button bt_cancle = (Button) dialog.findViewById(R.id.login_cancel);
//        RelativeLayout bt_layout = (RelativeLayout) dialog
//                .findViewById(R.id.dialog_layout);
//        bt_cancle.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        bt_layout.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        dialog.show();
//    }

	/**
	 * 隐藏对话框
	 */
	public static void dismiss() {
		// 隐藏对话框之前先判断对话框是否存在，以及是否正在显示
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

//    private static void setAdapter(GridView gridView_1,
//                                   final Activity activity, final IWXAPI api, final Tencent mTencent,
//                                   final String newsTitle, final String author, final String htmlUrl,
//                                   final String flag_shareQQurlOrText) {
//        HomeTextPictureAdapter adapter = null;
//        if ("1".equals(flag_shareQQurlOrText)) {// 区分QQ分享"1"链接url,"2"纯文本
//            adapter = new HomeTextPictureAdapter(NetURL.SHARE_BELOW_TEXT_FIVE,
//                    NetURL.SHARE_IMAGES_FIVE, activity, "2");
//            gridView_1.setNumColumns(4);
//        } else if ("2".equals(flag_shareQQurlOrText)) {
//            adapter = new HomeTextPictureAdapter(NetURL.SHARE_BELOW_TEXT_FOUR,
//                    NetURL.SHARE_IMAGES_FOUR, activity, "2");
//            gridView_1.setNumColumns(3);
//        }
//
//        gridView_1.setAdapter(adapter);
//        gridView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                switch (position) {
//                    case 0:
//                        shareMethodWeiXin("1", api, htmlUrl, newsTitle, author, activity);// 微信好友
//                        break;
//                    case 1:
//                        shareMethodWeiXin("2", api, htmlUrl, newsTitle, author, activity);// 微信朋友圈
//                        break;
//                    case 2:
//                        if ("1".equals(flag_shareQQurlOrText)) {// 区分QQ分享"1"链接url,"2"纯文本
//                            shareToQqOrQqzoneUrl(mTencent, activity, htmlUrl,
//                                    newsTitle, author, "1");
//                        } else if ("2".equals(flag_shareQQurlOrText)) {
//                            shareToQqOrQqzoneText(activity, newsTitle);
//                        }
//                        break;
//                    case 3:
//                        shareToQqOrQqzoneUrl(mTencent, activity, htmlUrl,
//                                newsTitle, author, "2");// QQ空间
//                        break;
//                    case 4:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//    }

	public static void shareToQqOrQqzoneText(Context context, String newsTitle) {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, newsTitle);
		sendIntent.setType("text/plain");
		// sendIntent.setPackage("com.tencent.mobileqq");
		// List<ResolveInfo> list= getShareTargets(mContext);
		try {
			sendIntent.setClassName("com.tencent.mobileqq",
					"com.tencent.mobileqq.activity.JumpActivity");
			Intent chooserIntent = Intent.createChooser(sendIntent, "文本分享到QQ");
			if (chooserIntent == null) {
				return;
			}
			context.startActivity(chooserIntent);
		} catch (Exception e) {
			context.startActivity(sendIntent);
		}
	}


//    private static void shareToQqOrQqzoneUrl(Tencent mTencent,
//                                             final Activity activity, String htmlUrl, String newsTitle, String author,
//                                             String type) {
//        final Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
//                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, newsTitle);
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, author);
//        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, htmlUrl);
//        //params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,);
//        /*
//         * params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
//		 * "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
//		 */
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "兰格钢铁应用");
//        if ("1".equals(type)) {
//            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
//                    QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
//        } else if ("2".equals(type)) {
//            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
//                    QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
//        }
//        mTencent.shareToQQ(activity, params, new BaseUiListener());
//    }

//    private static void shareMethodWeiXin(String flag, IWXAPI api,
//                                          String htmlUrl, String newsTitle, String author, Activity activity) {
//        WXMediaMessage msg = null;
//        if (!CommonUtil.isNull(htmlUrl)) {
//            WXWebpageObject webpage = new WXWebpageObject();
//            webpage.webpageUrl = htmlUrl;
//            msg = new WXMediaMessage(webpage);
//            msg.title = newsTitle;
//            msg.description = author;
//            Bitmap mBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo);
//            Bitmap thumbBmp = Bitmap.createScaledBitmap(mBitmap, 200, 200, true);
//            msg.setThumbImage(thumbBmp);
//            thumbBmp.recycle();
//        } else {
//            WXTextObject textObj = new WXTextObject();
//            textObj.text = newsTitle;
//            msg = new WXMediaMessage();
//            msg.mediaObject = textObj;
//            msg.description = newsTitle;
//        }
//        /*
//         * Bitmap thumb=BitmapFactory.decodeResource(activity.getResources(),
//		 * R.drawable.pic1); msg.thumbData=Util.bmpToByteArray(thumb,true);
//		 */
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis()); // transaction字段用于唯一标识一个请求
//        req.message = msg;
//        req.scene = "1".equals(flag) ? SendMessageToWX.Req.WXSceneSession
//                : SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
//    }
//
//    public static void intPulltoRefreshScroView(PullToRefreshScrollView pulltoRefreshScroView) {
//        //设置下拉刷新和上拉加载
//        pulltoRefreshScroView.setMode(PullToRefreshBase.Mode.BOTH);
//        //刷新
//        ILoadingLayout proxy = pulltoRefreshScroView.getLoadingLayoutProxy(true, false);
//        proxy.setTextTypeface(Typeface.DEFAULT);
//        proxy.setPullLabel("下拉刷新");
//        proxy.setRefreshingLabel("正在刷新");
//        proxy.setReleaseLabel("放开刷新");
//        //加载
//        ILoadingLayout proxy1 = pulltoRefreshScroView.getLoadingLayoutProxy(false, true);
//        proxy1.setTextTypeface(Typeface.DEFAULT);
//        proxy1.setPullLabel("上拉加载");
//        proxy1.setRefreshingLabel("正在加载");
//        proxy1.setReleaseLabel("放开加载");
//    }


	/**
	 * @param phoneNumber
	 * @return boolean 返回类型
	 * @Description: 验证号码 手机号 固话均可
	 * @Title: isPhoneNumberValid
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "((^(13|15|14|17|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
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


	/**
	 * 验证身份证
	 *
	 * @param IDStr
	 * @return
	 * @throws ParseException
	 */
    /*public static String IDCardValidate(String IDStr) throws ParseException {
        String errorInfo = "ok";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================
        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================
        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================
        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================
        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            errorInfo = "ok";
            return errorInfo;
        }
        // =====================(end)=====================
        return errorInfo;
    }*/
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

	public static void fullScreen(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Window window = activity.getWindow();
				View decorView = window.getDecorView();
				//两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
				int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
				decorView.setSystemUiVisibility(option);
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				//设置状态栏为透明，否则在部分手机上会呈现系统默认的浅灰色
				window.setStatusBarColor(Color.TRANSPARENT);
				//导航栏颜色也可以考虑设置为透明色
				//window.setNavigationBarColor(Color.TRANSPARENT);
			} else {
				Window window = activity.getWindow();
				WindowManager.LayoutParams attributes = window.getAttributes();
				int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
				int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
				attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
				window.setAttributes(attributes);
			}
		}
	}

	/**
	 * 获取状态栏高度
	 *
	 * @param context
	 * @return
	 */
	public int getStatusBarHeight(Context context) {
		int result = 0;
		int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
			result = context.getResources().getDimensionPixelOffset(resId);
		}
		return result;
	}

	/**
	 * 设置顶部试图高度
	 *
	 * @param topView
	 * @param context
	 */
	public void setTopView(View topView, Context context) {
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) topView.getLayoutParams();
		layoutParams.height = getStatusBarHeight(context);
		topView.setLayoutParams(layoutParams);
		topView.setLayoutParams(layoutParams);
	}


	public static void loadImage(Context context, String url, ImageView ivPic) {
		Glide.with(context)
				.load(url).error(R.drawable.test)
//				.load(R.drawable.test)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(ivPic);
	}


	public static BaseEntity<LoginEntity> loginEntity;
	public static Throwable throwable;

	public static void login(final String userId, final String password, final LoginCallBack loginCallBack) {
		HashMap<String, String> map = new HashMap<>();
		map.put("userID", userId);
		map.put("password", password);
		HttpUtils.login(map, new Consumer<BaseEntity<LoginEntity>>() {
			@Override
			public void accept(BaseEntity<LoginEntity> loginEntityBaseEntity) throws Exception {
				loginEntity = loginEntityBaseEntity;
				if ("0".equals(loginEntityBaseEntity.getCode())) {
					PreforenceUtils.getSharedPreferences("loginInfo");
					PreforenceUtils.setData("userID", userId);
					PreforenceUtils.setData("password", password);
					PreforenceUtils.setData("isLogin", true);
				}

				loginCallBack.callBack(loginEntity);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				loginCallBack.failCallBack(throwable);
			}
		});
	}

	public interface LoginCallBack {
		abstract void callBack(BaseEntity<LoginEntity> loginEntity);

		abstract void failCallBack(Throwable throwable);
	}

	;

}
