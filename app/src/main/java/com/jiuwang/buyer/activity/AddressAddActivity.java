package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AreaBean;
import com.jiuwang.buyer.bean.CityBean;
import com.jiuwang.buyer.bean.JsonBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.GetJsonDataUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.TextUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;




/*
*
* 作者:lihj
* 作用：添加收货地址
*
*/


public class AddressAddActivity extends BaseActivity {

	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.return_img)
	ImageView returnImg;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.nameEditText)
	EditText nameEditText;
	@Bind(R.id.phoneEditText)
	EditText phoneEditText;
	@Bind(R.id.proEditText)
	TextView proEditText;
	@Bind(R.id.addressEditText)
	EditText addressEditText;
	@Bind(R.id.defaultCheckBox)
	CheckBox defaultCheckBox;
	@Bind(R.id.bt_submit)
	Button btSubmit;
	private Activity mActivity;
	private MyApplication mApplication;

	private String city_id;
	private String area_id;
	private String area_info;
	private String address;
	private String phone;
	private String name;
	private String is_default;
	private Thread thread;
	private String provcode;
	private String citycode;
	private String areacode;
	private String addressId;


	private static final int MSG_LOAD_DATA = 0x0001;
	private static final int MSG_LOAD_SUCCESS = 0x0002;
	private static final int MSG_LOAD_FAILED = 0x0003;
	private ArrayList<JsonBean> options1Items = new ArrayList<>();
	private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
	private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
	private ArrayList<List<CityBean>> citylist = new ArrayList<>();
	private ArrayList<List<List<AreaBean>>> arealist = new ArrayList<>();
	private ArrayList<String> Items1 = new ArrayList<>();
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_LOAD_DATA:
					if (thread == null) {//如果已创建就不再重新创建子线程了
						thread = new Thread(new Runnable() {
							@Override
							public void run() {
								// 写子线程中的操作,解析省市区数据
								initJsonData();
							}
						});
						thread.start();
					}
					break;
			}
		}
	};
	private String mode;
	private Intent intent;
	private LoadingDialog loadingDialog;

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			returnActivity();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_address_add);
		ButterKnife.bind(this);
		intent = getIntent();
		mode = intent.getStringExtra("mode");
		initView();
		initData();
		initEven();
		mHandler.sendEmptyMessage(MSG_LOAD_DATA);
	}

	//初始化控件
	private void initView() {

		setTopView(topView);
		actionbarText.setText("新建收货地址");
		onclickLayoutRight.setVisibility(View.INVISIBLE);

	}

	//初始化数据
	private void initData() {

		mActivity = this;
		mApplication = (MyApplication) getApplication();
		provcode = "";
		citycode = "";
		areacode = "";
		area_info = "";
		address = "";
		phone = "";
		name = "";
		is_default = "0";
		if (intent != null && mode != null) {
			if (mode.equals("details")) {
				provcode = intent.getStringExtra("provcode");
				citycode = intent.getStringExtra("citycode");
				areacode = intent.getStringExtra("areacode");
				area_info = intent.getStringExtra("area_info");
				address = intent.getStringExtra("address");
				phone = intent.getStringExtra("phone");
				name = intent.getStringExtra("name");
				addressId = intent.getStringExtra("addressId");
				proEditText.setText("");
				actionbarText.setText("修改收货地址");
			}
		}


//		mApplication.startActivity(mActivity, new Intent(mActivity, AddressAreaActivity.class), MyApplication.CODE_CHOOSE_AREA);

	}

	//初始化事件
	private void initEven() {


		defaultCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					is_default = "1";
				} else {
					is_default = "0";
				}
			}
		});


	}

	//添加地址
	private void addAddress() {

		address = addressEditText.getText().toString();
		name = nameEditText.getText().toString();
		phone = phoneEditText.getText().toString();

		if (TextUtil.isEmpty(provcode)) {
			MyToastView.showToast(getString(R.string.address_pro), mActivity);
			btSubmit.setEnabled(true);
			return;
		}

		if (TextUtil.isEmpty(address)) {
			MyToastView.showToast(getString(R.string.address_info), mActivity);
			btSubmit.setEnabled(true);
			return;
		}

		if (TextUtil.isEmpty(name)) {
			MyToastView.showToast(getString(R.string.address_consignee_name), mActivity);
			btSubmit.setEnabled(true);
			return;
		}

		if (TextUtil.isEmpty(phone)) {
			MyToastView.showToast(getString(R.string.address_consignee_telephone), mActivity);
			btSubmit.setEnabled(true);
			return;
		}
		loadingDialog = new LoadingDialog(AddressAddActivity.this);
		loadingDialog.show();
		//添加/修改地址
		if (CommonUtil.getNetworkRequest(AddressAddActivity.this)) {
			HashMap<String, String> hashMap = new HashMap<>();
			if ("details".equals(mode)) {
				hashMap.put("act", Constant.ACTION_ACT_UPDATA);

			} else if ("add".equals(mode)) {
				hashMap.put("act", Constant.ACTION_ACT_ADD);
			}
			hashMap.put("consignee_name", name);
			hashMap.put("consignee_telephone", phone);
			hashMap.put("destination_prov_cd", provcode);
			hashMap.put("destination_city_cd", citycode);
			hashMap.put("destination_area_cd", areacode);
			hashMap.put("destination_address", proEditText.getText().toString() + address);
			hashMap.put("destination", proEditText.getText().toString() + address);
			hashMap.put("is_de", proEditText.getText().toString() + address);
			HttpUtils.addressInfo(hashMap, new Consumer<BaseResultEntity>() {
				@Override
				public void accept(BaseResultEntity baseResultEntity) throws Exception {
					loadingDialog.dismiss();
					if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
						Intent intent = new Intent();
						intent.setAction("refreshAddress");
						sendBroadcast(intent);
						finish();
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
						MyToastView.showToast(baseResultEntity.getMsg(),AddressAddActivity.this);
						Intent intent = new Intent(AddressAddActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					} else {
						btSubmit.setEnabled(true);
					}
					MyToastView.showToast(baseResultEntity.getMsg(), AddressAddActivity.this);
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					btSubmit.setEnabled(true);
					loadingDialog.dismiss();
					MyToastView.showToast(getString(R.string.msg_error_operation), AddressAddActivity.this);
				}
			});
		}

	}

	//返回&销毁Activity
	private void returnActivity() {

		DialogUtil.query(
				mActivity,
				"确认您的选择",
				"取消添加收货地址",
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogUtil.cancel();
						mApplication.finishActivity(mActivity);
					}
				}
		);

	}

	private void initJsonData() {//解析数据

		/**
		 * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
		 * 关键逻辑在于循环体
		 *
		 * */
		String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

		ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

		/**
		 * 添加省份数据
		 *
		 * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
		 * PickerView会通过getPickerViewText方法获取字符串显示出来。
		 */
		options1Items = jsonBean;

		for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
			Items1.add(jsonBean.get(i).getName());
			citylist.add(jsonBean.get(i).getCityList());
			ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
			ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
			ArrayList<List<AreaBean>> AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
			for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
				CityBean cityBean = jsonBean.get(i).getCityList().get(c);
				CityList.add(cityBean.getName());//添加城市
				ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
				ArrayList<AreaBean> area_list = new ArrayList<>();//该城市的所有地区列表
				//如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
				if (jsonBean.get(i).getCityList().get(c).getArea() == null
						|| jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
					AreaBean areaBean = new AreaBean("", "");
					City_AreaList.add("");
					area_list.add(areaBean);
				} else {
					for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
						AreaBean AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
						area_list.add(AreaName);
						City_AreaList.add(AreaName.getName());//添加该城市所有地区数据
					}
				}
				AreaList.add(area_list);
				Province_AreaList.add(City_AreaList);//添加该省所有地区数据
			}
			arealist.add(AreaList);
			// 添加城市数据
			options2Items.add(CityList);
			//添加地区数据
			options3Items.add(Province_AreaList);
		}
		mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
	}

	public ArrayList<JsonBean> parseData(String result) {//Gson 解析
		ArrayList<JsonBean> detail = new ArrayList<>();
		try {
			JSONArray data = new JSONArray(result);
			Gson gson = new Gson();
			for (int i = 0; i < data.length(); i++) {
				JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
				detail.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
		}
		return detail;
	}

	private void showPickerView() {// 弹出选择器
		OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
			@Override
			public void onOptionsSelect(int options1, int options2, int options3, View v) {
				//返回的分别是三个级别的选中位置
				String opt1tx = options1Items.size() > 0 ?
						options1Items.get(options1).getPickerViewText() : "";
				provcode = options1Items.size() > 0 ?
						options1Items.get(options1).getCode() : "0";

				String opt2tx = options2Items.size() > 0
						&& options2Items.get(options1).size() > 0 ?
						options2Items.get(options1).get(options2) : "";
				citycode = citylist.size() > 0
						&& citylist.get(options1).size() > 0 ?
						citylist.get(options1).get(options2).getCode() : "0";

				String opt3tx = options3Items.size() > 0
						&& options3Items.get(options1).size() > 0
						&& options3Items.get(options1).get(options2).size() > 0 ?
						options3Items.get(options1).get(options2).get(options3) : "";
				areacode = arealist.size() > 0
						&& arealist.get(options1).size() > 0
						&& arealist.get(options1).get(options2).size() > 0 ?
						arealist.get(options1).get(options2).get(options3).getCode() : "0";
				String tx = "";
				if ("".equals(opt3tx)) {
					tx = opt1tx + "/" + opt2tx;
				} else {
					tx = opt1tx + "/" + opt2tx + "/" + opt3tx;
				}

				proEditText.setText(tx);
			}
		}).setEmptyText("").setTitleText("城市选择")
				.setDividerColor(Color.BLACK)
				.setTextColorCenter(Color.BLACK) //设置选中项文字颜色
				.setContentTextSize(20)
				.build();

//        pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
		pvOptions.setPicker(Items1, options2Items, options3Items);//三级选择器
		pvOptions.show();
	}

	@OnClick({R.id.onclick_layout_left, R.id.bt_submit, R.id.proEditText})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.bt_submit:
				btSubmit.setEnabled(false);
				addAddress();
				break;
			case R.id.proEditText:
				showPickerView();
				break;
		}
	}
}
