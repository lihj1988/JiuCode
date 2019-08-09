package com.jiuwang.buyer.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.AddressActivity;
import com.jiuwang.buyer.activity.BalanceActivity;
import com.jiuwang.buyer.activity.BindAccountActivity;
import com.jiuwang.buyer.activity.CashOutActivity;
import com.jiuwang.buyer.activity.InviteCodeActivity;
import com.jiuwang.buyer.activity.InviteCodeEditActivity;
import com.jiuwang.buyer.activity.InviteManagerActivity;
import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.activity.MainActivity;
import com.jiuwang.buyer.activity.MyProjectActivity;
import com.jiuwang.buyer.activity.OrderActivity;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.UserBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.entity.UserEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.service.UpdateService;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PermissionsUtils;
import com.jiuwang.buyer.util.PreforenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import okhttp3.Call;


public class MineFragment extends Fragment {
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.orderTextView)
	TextView orderTextView;
	@Bind(R.id.orderNumberTextView)
	TextView orderNumberTextView;
	@Bind(R.id.waitPaymentTextView)
	TextView waitPaymentTextView;
	@Bind(R.id.waitPaymentNumberTextView)
	TextView waitPaymentNumberTextView;
	@Bind(R.id.waitPaymentRelativeLayout)
	RelativeLayout waitPaymentRelativeLayout;
	@Bind(R.id.waitDeliverTextView)
	TextView waitDeliverTextView;
	@Bind(R.id.waitDeliverNumberTextView)
	TextView waitDeliverNumberTextView;
	@Bind(R.id.waitDeliverRelativeLayout)
	RelativeLayout waitDeliverRelativeLayout;
	@Bind(R.id.waitReceiptTextView)
	TextView waitReceiptTextView;
	@Bind(R.id.waitReceiptNumberTextView)
	TextView waitReceiptNumberTextView;
	@Bind(R.id.waitReceiptRelativeLayout)
	RelativeLayout waitReceiptRelativeLayout;
	@Bind(R.id.waitEvaluateTextView)
	TextView waitEvaluateTextView;
	@Bind(R.id.waitEvaluateNumberTextView)
	TextView waitEvaluateNumberTextView;
	@Bind(R.id.waitEvaluateRelativeLayout)
	RelativeLayout waitEvaluateRelativeLayout;
	@Bind(R.id.waitRefundRelativeLayout)
	RelativeLayout waitRefundRelativeLayout;
	@Bind(R.id.addressTextView)
	TextView addressTextView;
	@Bind(R.id.settingTextView)
	TextView settingTextView;
	@Bind(R.id.tv_exit)
	TextView tvExit;
	@Bind(R.id.civAuther)
	CircleImageView civAuther;
	//	@Bind(R.id.recharge)
//	TextView recharge;
	@Bind(R.id.userRelativeLayout)
	LinearLayout userRelativeLayout;
	@Bind(R.id.tvUserName)
	TextView tvUserName;
	//	@Bind(R.id.tvMoneyName)
//	TextView tvMoneyName;
	@Bind(R.id.tvBalance)
	TextView tvBalance;
	@Bind(R.id.tvTrialAmount)
	TextView tvTrialAmount;
	@Bind(R.id.tvMyInviteCode)
	TextView tvMyInviteCode;
	@Bind(R.id.tvInviteManager)
	TextView tvInviteManager;
	@Bind(R.id.tvBindInviteCode)
	TextView tvBindInviteCode;
	@Bind(R.id.tvAvailAmount)
	TextView tvAvailAmount;
	@Bind(R.id.llBalance)
	LinearLayout llBalance;
	@Bind(R.id.llAvailAmount)
	LinearLayout llAvailAmount;@Bind(R.id.llBind)
	LinearLayout llBind;
	@Bind(R.id.llTrialAmount)
	LinearLayout llTrialAmount;
	@Bind(R.id.tvMyAccount)
	TextView tvMyAccount;
	@Bind(R.id.peojectTextView)
	TextView peojectTextView;
	@Bind(R.id.trialOrScore)
	TextView trialOrScore;
	private View view;
	private MainActivity mActivity;
	private String userCode;
	ProgressDialog progressDialog;//下载进度条
	private String userName, token;//图片下显示的用户名
	@Bind(R.id.return_img)
	ImageView return_img;//返回
	@Bind(R.id.onclick_layout_right)
	Button onclick_layout_right;//右边筛选

	private UserBean userBean;
	private MyReceiver myReceiver;
	private String invite_code;
	private String account_name;
	private String account_no;
	private String avail_amount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.fragment_mine, null);
		ButterKnife.bind(this, view);
		initData();
		initView();
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("minerefresh");
		getActivity().registerReceiver(myReceiver, filter);
		return view;
	}

	//获取用户数据
	public void initData() {

		if (CommonUtil.getNetworkRequest(getActivity())) {
			HashMap<String, String> map = new HashMap<>();
			map.put("", "");
			HttpUtils.selectUserInfo(map, new Consumer<UserEntity>() {
				@Override
				public void accept(final UserEntity userEntity) throws Exception {

					if (Constant.HTTP_SUCCESS_CODE.equals(userEntity.getCode())) {
						userBean = userEntity.getData().get(0);
						new Handler() {

							@Override
							public void handleMessage(Message msg) {
								tvUserName.setText(userBean.getMobile_number());
								invite_code = userBean.getInvite_code();
								account_name = userBean.getAccount_name();
								account_no = userBean.getAccount_no();
								avail_amount = userBean.getAvail_amount();
								if ("".equals(account_no)) {
									tvMyAccount.setClickable(true);
								} else {
									tvMyAccount.setClickable(false);
									tvMyAccount.setText("我的账号：" + account_name + " " + account_no);
								}
								if ("0".equals(userBean.getIs_trial()) || "".equals(userBean.getTrial_amount()) || "0".equals(userBean.getTrial_amount())) {
									tvTrialAmount.setText("0.00");
									llTrialAmount.setVisibility(View.INVISIBLE);
								} else {
									llTrialAmount.setVisibility(View.VISIBLE);
									tvTrialAmount.setText("" + CommonUtil.decimalFormat(Double.parseDouble(userBean.getTrial_amount()), "0") + "");
								}
								String avail_amount = "";
								if ("".equals(userBean.getAvail_amount())) {
									avail_amount = "";
								} else {
									avail_amount = CommonUtil.decimalFormat(Double.parseDouble(userBean.getAvail_amount()), "0");
								}
								if ("".equals(userBean.getAccount_balance())) {
									tvBalance.setText("0.00");
								} else {
									tvBalance.setText("" + CommonUtil.decimalFormat(Double.parseDouble(userBean.getAccount_balance()), "0") + "");
								}
//								tvMoneyName.setText("账户余额：");
								tvAvailAmount.setText("" + avail_amount + "");
								tvBalance.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										startActivity(new Intent(getActivity(), BalanceActivity.class));
									}
								});
								if("1".equals(userBean.getIs_invited())){
									llBind.setVisibility(View.GONE);
								}else {
									llBind.setVisibility(View.INVISIBLE);
								}

							}
						}.sendEmptyMessage(0);
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(userEntity.getCode())) {
						startActivity(new Intent(getActivity(), LoginActivity.class));
						getActivity().finish();
					} else {

					}
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					MyToastView.showToast(getActivity().getResources().getString(R.string.msg_error), getActivity());
				}
			});
		}

	}

	private void initView() {
		actionbarText.setText("我的");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
		onclick_layout_right.setVisibility(View.INVISIBLE);
		settingTextView.setText("版本号：" + Constant.localVersion);


	}


	//下载软件
	private void uploadVersion(String url) {
		OkHttpUtils.post().tag("down").url(NetURL.BASEURL + url).build()
				.execute(new FileCallBack(NetURL.directory, "jbmj.apk") {
					@Override
					public void inProgress(float progress, long total, int id) {
						super.inProgress(progress, total, id);
						progressDialog.setProgress((int) (100 * progress));
					}

					@Override
					public void onResponse(File response, int id) {
						progressDialog.dismiss();
						Toast.makeText(MyApplication.getInstance(), "下载完成！", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						intent.setDataAndType(Uri.fromFile(new File(NetURL.directory + "jbmj.apk")),
								"application/vnd.android.package-archive");
						MyApplication.getInstance().startActivity(intent);
					}

					@Override
					public void onError(Call call, Exception e, int id) {
						progressDialog.dismiss();
						Toast.makeText(MyApplication.getInstance(), "下载失败！", Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();

	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (MainActivity) activity;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
		getActivity().unregisterReceiver(myReceiver);
	}

	@OnClick({R.id.orderTextView, R.id.waitPaymentRelativeLayout, R.id.waitDeliverRelativeLayout, R.id.waitReceiptRelativeLayout,
			R.id.waitEvaluateRelativeLayout, R.id.waitRefundRelativeLayout, R.id.addressTextView, R.id.settingTextView,
			R.id.tv_exit, R.id.civAuther, R.id.tvMyInviteCode, R.id.tvMyAccount, R.id.tvInviteManager, R.id.llBalance,
			R.id.llAvailAmount, R.id.tvBindInviteCode, R.id.peojectTextView})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.orderTextView:
				Intent intentAll = new Intent(mActivity, OrderActivity.class);
				intentAll.putExtra("position", 0);
				startActivity(intentAll);
				break;
			case R.id.waitPaymentRelativeLayout:
				Intent intent = new Intent(mActivity, OrderActivity.class);
				intent.putExtra("position", 1);
				startActivity(intent);
				break;
			case R.id.waitDeliverRelativeLayout:
				Intent intentWaitDeliver = new Intent(mActivity, OrderActivity.class);
				intentWaitDeliver.putExtra("position", 2);
//				MyApplication.getInstance().startActivityLoginSuccess(mActivity, intentWaitDeliver);
				startActivity(intentWaitDeliver);
				break;
			case R.id.waitReceiptRelativeLayout:
				Intent intentWaitReceipt = new Intent(mActivity, OrderActivity.class);
				intentWaitReceipt.putExtra("position", 3);
				startActivity(intentWaitReceipt);
//				MyApplication.getInstance().startActivityLoginSuccess(mActivity, intentWaitReceipt);
				break;
			case R.id.waitEvaluateRelativeLayout:
				Intent intentWaitEvaluate = new Intent(mActivity, OrderActivity.class);
				intentWaitEvaluate.putExtra("position", 4);
				MyApplication.getInstance().startActivityLoginSuccess(mActivity, intentWaitEvaluate);
				break;
			case R.id.waitRefundRelativeLayout:
				break;
			case R.id.addressTextView:
				Intent intentAddress = new Intent(mActivity, AddressActivity.class);
				startActivity(intentAddress);
				break;
			case R.id.settingTextView:
				AppUtils.getSystemVersion(getActivity(), permissionsResult, "2");
				break;
			case R.id.tv_exit:
				PreforenceUtils.getSharedPreferences("loginInfo");
				PreforenceUtils.setData("userID", "");
				PreforenceUtils.setData("password", "");
				Constant.IS_LOGIN = false;
				PreforenceUtils.setData("isLogin", false);
				PreforenceUtils.setchecklogin(false);
				MyApplication.getInstance().exit();
				MyApplication.getInstance().stopService(new Intent(MyApplication.getInstance(), UpdateService.class));
				Intent intentExit = new Intent(getActivity(), LoginActivity.class);
				startActivity(intentExit);
				getActivity().finish();
				break;
			case R.id.civAuther://头像
				break;
//			case R.id.recharge://充值
//				Intent intentRecharge = new Intent(getActivity(), RechargeActivity.class);
//				intentRecharge.putExtra("type", "mine");
//				getActivity().startActivity(intentRecharge);
//				break;
			case R.id.tvMyInviteCode://我的邀请码
				if (null == invite_code || "".equals(invite_code)) {
					MyToastView.showToast("没有邀请码", getActivity());
				} else {
					Intent intentInviteCode = new Intent(getActivity(), InviteCodeActivity.class);
					intentInviteCode.putExtra("invite_code", invite_code);
					getActivity().startActivity(intentInviteCode);
				}

				break;
			case R.id.tvMyAccount://我的账户

				Intent intentAccount = new Intent(getActivity(), BindAccountActivity.class);
				intentAccount.putExtra("data", userBean);
				getActivity().startActivity(intentAccount);


				break;
			case R.id.tvInviteManager://我的二维码

				Intent intentInviteManager = new Intent(getActivity(), InviteManagerActivity.class);
				getActivity().startActivity(intentInviteManager);

				break;
			case R.id.llBalance:
				startActivity(new Intent(getActivity(), BalanceActivity.class));
				break;
			case R.id.llAvailAmount:
				//提现
				if ("".equals(account_name)) {
					AppUtils.showDialog(getActivity(), "提示", getResources().getString(R.string.bind_account_content), new DialogClickInterface() {
						@Override
						public void onClick() {

							Intent intentAccount = new Intent(getActivity(), BindAccountActivity.class);
							intentAccount.putExtra("data", userBean);
							getActivity().startActivity(intentAccount);

						}

						@Override
						public void nagtiveOnClick() {

						}
					});
				} else {
					Intent intentCashout = new Intent(getActivity(), CashOutActivity.class);
					intentCashout.putExtra("account_name", account_name);
					intentCashout.putExtra("account_no", account_no);
					intentCashout.putExtra("avail_amount", avail_amount);
					getActivity().startActivity(intentCashout);
				}
				break;
			case R.id.tvBindInviteCode:

				Intent intentBindInviteCode = new Intent(getActivity(), InviteCodeEditActivity.class);
				getActivity().startActivity(intentBindInviteCode);
				break;
			case R.id.peojectTextView:

				Intent intentMyProject = new Intent(getActivity(), MyProjectActivity.class);
				getActivity().startActivity(intentMyProject);
				break;
		}
	}


	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					initData();
				}
			}.sendEmptyMessageDelayed(0, 1500);

		}
	}

	PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
		@Override
		public void passPermissons() {
			Intent updateIntent = new Intent(getActivity(), UpdateService.class);
			getActivity().startService(updateIntent);
		}

		@Override
		public void forbitPermissons() {
			Toast.makeText(getActivity(), "权限不通过!", Toast.LENGTH_SHORT).show();
		}
	};


}
