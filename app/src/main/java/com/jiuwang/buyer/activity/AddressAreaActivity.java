package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.AddressAreaListAdapter;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.util.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;

/*
*
* 作者：lihj
* 作用：添加地址时选择地区
*/

public class AddressAreaActivity extends AppCompatActivity {

    private Activity mActivity;
    private MyApplication mApplication;

    private int setup;
    private String province;
    private String province_id;
    private String city;
    private String city_id;
    private String area;
    private String area_id;

    private ImageView backImageView;
    private TextView titleTextView;
    private TextView mTextView;

    private RecyclerView provinceListView;
    private AddressAreaListAdapter provinceAdapter;
    private ArrayList<HashMap<String, String>> provinceArrayList;

    private RecyclerView cityListView;
    private AddressAreaListAdapter cityAdapter;
    private ArrayList<HashMap<String, String>> cityArrayList;

    private RecyclerView areaListView;
    private AddressAreaListAdapter areaAdapter;
    private ArrayList<HashMap<String, String>> areaArrayList;

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
        setContentView(R.layout.activity_address_area);
        initView();
        initData();
        initEven();
        getProvince();
    }

    //初始化控件
    private void initView() {

        backImageView = (ImageView) findViewById(R.id.backImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        mTextView = (TextView) findViewById(R.id.mainTextView);

        provinceListView = (RecyclerView) findViewById(R.id.provinceListView);
        cityListView = (RecyclerView) findViewById(R.id.cityListView);
        areaListView = (RecyclerView) findViewById(R.id.areaListView);

    }

    //初始化数据
    private void initData() {

        mActivity = this;
        mApplication = (MyApplication) getApplication();

        setup = 0;
        province = "";
        province_id = "";
        city = "";
        city_id = "";
        area = "";
        area_id = "";

        titleTextView.setText("请选择区域");

        provinceArrayList = new ArrayList<>();
        provinceAdapter = new AddressAreaListAdapter(provinceArrayList);
        provinceListView.setLayoutManager(new LinearLayoutManager(this));
        provinceListView.setAdapter(provinceAdapter);

        cityArrayList = new ArrayList<>();
        cityAdapter = new AddressAreaListAdapter(cityArrayList);
        cityListView.setLayoutManager(new LinearLayoutManager(this));
        cityListView.setAdapter(cityAdapter);

        areaArrayList = new ArrayList<>();
        areaAdapter = new AddressAreaListAdapter(areaArrayList);
        areaListView.setLayoutManager(new LinearLayoutManager(this));
        areaListView.setAdapter(areaAdapter);

        provinceListView.setVisibility(View.VISIBLE);
        cityListView.setVisibility(View.GONE);
        areaListView.setVisibility(View.GONE);

    }

    //初始化事件
    private void initEven() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnActivity();
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setup != 0) {
                    returnActivity();
                }
            }
        });

        provinceAdapter.setOnItemClickListener(new AddressAreaListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String id, String value) {
                mTextView.setText(value);
                mTextView.append(" -> ");
                province = value;
                province_id = id;
                getCity();
            }
        });

        cityAdapter.setOnItemClickListener(new AddressAreaListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String id, String value) {
                mTextView.append(value);
                mTextView.append(" -> ");
                city = value;
                city_id = id;
                getArea();
            }
        });

        areaAdapter.setOnItemClickListener(new AddressAreaListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String id, String value) {
                mTextView.append(value);
                area = value;
                area_id = id;
                Intent intent = new Intent();
                intent.putExtra("area_info", province + " " + city + " " + area);
                intent.putExtra("province_id", province_id);
                intent.putExtra("province", province);
                intent.putExtra("city_id", city_id);
                intent.putExtra("city", city);
                intent.putExtra("area_id", area_id);
                intent.putExtra("area", area);
                mActivity.setResult(RESULT_OK, intent);
                mApplication.finishActivity(mActivity);
            }
        });

    }

    //读取省份数据
    private void getProvince() {

        setup = 0;
        DialogUtil.progress(mActivity);

//        KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//        ajaxParams.putAct("member_address");
//        ajaxParams.putOp("area_list");
//        ajaxParams.put("area_id", "");
//
//        mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//                super.onSuccess(o);
//                DialogUtil.cancel();
//                if (TextUtil.isJson(o.toString())) {
//                    String error = mApplication.getJsonError(o.toString());
//                    if (TextUtil.isEmpty(error)) {
//                        String data = mApplication.getJsonData(o.toString());
//                        try {
//                            provinceArrayList.clear();
//                            JSONObject jsonObject = new JSONObject(data);
//                            JSONArray jsonArray = new JSONArray(jsonObject.getString("area_list"));
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                provinceArrayList.add(new HashMap<>(TextUtil.jsonObjectToHashMap(jsonArray.get(i).toString())));
//                            }
//                            provinceAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            getProvinceFailure();
//                        }
//                    } else {
//                        getProvinceFailure();
//                    }
//                } else {
//                    getProvinceFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                DialogUtil.cancel();
//                getProvinceFailure();
//            }
//        });
//
//    }
//
//    //读取省份数据失败
//    private void getProvinceFailure() {
//
//        DialogUtil.query(
//                mActivity,
//                "确认您的选择",
//                "数据加载失败，是否重试？",
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DialogUtil.cancel();
//                        getProvince();
//                    }
//                }, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DialogUtil.cancel();
//                        mApplication.finishActivity(mActivity);
//                    }
//                }
//        );

    }

    //读取城市数据
    private void getCity() {

        setup = 1;
        DialogUtil.progress(mActivity);

//        KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//        ajaxParams.putAct("member_address");
//        ajaxParams.putOp("area_list");
//        ajaxParams.put("area_id", province_id);
//
//        mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//                super.onSuccess(o);
//                DialogUtil.cancel();
//                if (TextUtil.isJson(o.toString())) {
//                    String error = mApplication.getJsonError(o.toString());
//                    if (TextUtil.isEmpty(error)) {
//                        String data = mApplication.getJsonData(o.toString());
//                        try {
//                            cityArrayList.clear();
//                            JSONObject jsonObject = new JSONObject(data);
//                            JSONArray jsonArray = new JSONArray(jsonObject.getString("area_list"));
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                cityArrayList.add(new HashMap<>(TextUtil.jsonObjectToHashMap(jsonArray.get(i).toString())));
//                            }
//                            provinceListView.setVisibility(View.GONE);
//                            cityListView.setVisibility(View.VISIBLE);
//                            areaListView.setVisibility(View.GONE);
//                            cityAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            getCityFailure();
//                        }
//                    } else {
//                        getCityFailure();
//                    }
//                } else {
//                    getCityFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                DialogUtil.cancel();
//                getCityFailure();
//            }
//        });

    }

    //读取城市数据失败
    private void getCityFailure() {

        DialogUtil.query(
                mActivity,
                "确认您的选择",
                "数据加载失败，是否重试？",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.cancel();
                        getCity();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.cancel();
                        mApplication.finishActivity(mActivity);
                    }
                }
        );

    }

    //读取区域数据
    private void getArea() {

        setup = 2;
        DialogUtil.progress(mActivity);

//        KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//        ajaxParams.putAct("member_address");
//        ajaxParams.putOp("area_list");
//        ajaxParams.put("area_id", city_id);
//
//        mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//                super.onSuccess(o);
//                DialogUtil.cancel();
//                if (TextUtil.isJson(o.toString())) {
//                    String error = mApplication.getJsonError(o.toString());
//                    if (TextUtil.isEmpty(error)) {
//                        String data = mApplication.getJsonData(o.toString());
//                        try {
//                            areaArrayList.clear();
//                            JSONObject jsonObject = new JSONObject(data);
//                            JSONArray jsonArray = new JSONArray(jsonObject.getString("area_list"));
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                areaArrayList.add(new HashMap<>(TextUtil.jsonObjectToHashMap(jsonArray.get(i).toString())));
//                            }
//                            provinceListView.setVisibility(View.GONE);
//                            areaListView.setVisibility(View.VISIBLE);
//                            cityListView.setVisibility(View.GONE);
//                            areaAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            getAreaFailure();
//                        }
//                    } else {
//                        getAreaFailure();
//                    }
//                } else {
//                    getAreaFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                DialogUtil.cancel();
//                getAreaFailure();
//            }
//        });

    }

    //读取区域数据失败
    private void getAreaFailure() {

        DialogUtil.query(
                mActivity,
                "确认您的选择",
                "数据加载失败，是否重试？",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.cancel();
                        getCity();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.cancel();
                        mApplication.finishActivity(mActivity);
                    }
                }
        );

    }

    //返回&销毁Activity
    private void returnActivity() {

        switch (setup) {
            case 0:
                DialogUtil.query(
                        mActivity,
                        "确认您的选择",
                        "取消选择区域？",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtil.cancel();
                                mApplication.finishActivity(mActivity);
                            }
                        }
                );
                break;
            case 1:
                setup = 0;
                mTextView.setText("请选择区域");
                provinceListView.setVisibility(View.VISIBLE);
                cityListView.setVisibility(View.GONE);
                areaListView.setVisibility(View.GONE);
                break;
            case 2:
                setup = 1;
                mTextView.setText(province);
                mTextView.append(" -> ");
                provinceListView.setVisibility(View.GONE);
                cityListView.setVisibility(View.VISIBLE);
                areaListView.setVisibility(View.GONE);
                break;
        }

    }

}
