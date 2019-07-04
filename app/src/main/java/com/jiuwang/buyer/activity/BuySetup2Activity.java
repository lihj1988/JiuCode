package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.MyToastView;

/*
*
* 作者：lihj
* 作用：购买第二步
*/
public class BuySetup2Activity extends AppCompatActivity {

    private Activity mActivity;
    private MyApplication mApplication;

    private String pay_sn;

    private ImageView backImageView;
    private TextView titleTextView;

    private TextView snTextView;
    private RadioButton aliPayRadioButton;
    private RadioButton wxPayRadioButton;
    private TextView payTextView;

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
        setContentView(R.layout.activity_buy_setup2);
        initView();
        initData();
        initEven();
        getPayment();
    }

    //初始化控件
    private void initView() {

        backImageView = (ImageView) findViewById(R.id.backImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        snTextView = (TextView) findViewById(R.id.snTextView);
        aliPayRadioButton = (RadioButton) findViewById(R.id.aliPayTextView);
        wxPayRadioButton = (RadioButton) findViewById(R.id.wxPayTextView);
        payTextView = (TextView) findViewById(R.id.payTextView);

    }

    //初始化数据
    private void initData() {

        mActivity = this;
        mApplication = (MyApplication) getApplication();

        pay_sn = mActivity.getIntent().getStringExtra("pay_sn");

        if (!mActivity.getIntent().getStringExtra("payment_code").equals("online")) {
            MyToastView.showToast( "不支持的支付方式",mActivity);
            mApplication.finishActivity(mActivity);
        }

        titleTextView.setText("订单支付");
        snTextView.append("：");
        snTextView.append(pay_sn);
        aliPayRadioButton.setVisibility(View.GONE);
        wxPayRadioButton.setVisibility(View.GONE);

    }

    //初始化事件
    private void initEven() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnActivity();
            }
        });

        payTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aliPayRadioButton.isChecked()) {
//                    String link = mApplication.apiUrlString + "act=member_payment&op=pay_new&key="
//                            + mApplication.userKeyString + "&pay_sn=" + pay_sn
//                            + "&password=&rcb_pay=0&pd_pay=0&payment_code=alipay";
//                    Intent intent = new Intent(mActivity, BrowserActivity.class);
//                    intent.putExtra("model", "payment");
//                    intent.putExtra("link", link);
//                    mApplication.startActivity(mActivity, intent);
//                    mApplication.finishActivity(mActivity);
                }
                if (wxPayRadioButton.isChecked()) {
                    MyToastView.showToast( "不支付的支付方式",mActivity);

                }
            }
        });

    }

    //获取可用的支付列表
    private void getPayment() {

        DialogUtil.progress(mActivity);

//        KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//        ajaxParams.putAct("member_payment");
//        ajaxParams.putOp("payment_list");
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
//                            JSONObject jsonObject = new JSONObject(data);
//                            String payment_list = jsonObject.getString("payment_list");
//                            if (payment_list.contains("alipay")) {
//                                aliPayRadioButton.setChecked(true);
//                                aliPayRadioButton.setVisibility(View.VISIBLE);
//                            }
//                            if (payment_list.contains("wx")) {
//                                wxPayRadioButton.setVisibility(View.VISIBLE);
//                                if (aliPayRadioButton.getVisibility() == View.GONE) {
//                                    wxPayRadioButton.setChecked(true);
//                                }
//                            }
//                        } catch (JSONException e) {
//                            getPaymentFailure();
//                            e.printStackTrace();
//                        }
//                    } else {
//                        getPaymentFailure();
//                    }
//                } else {
//                    getPaymentFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                DialogUtil.cancel();
//                getPaymentFailure();
//            }
//        });

    }

    //更换地址失败
    private void getPaymentFailure() {

        DialogUtil.query(
                mActivity,
                "确认您的选择",
                "读取数据失败，是否重试？",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.cancel();
                        getPayment();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mApplication.finishActivity(mActivity);
                        DialogUtil.cancel();
                    }
                }
        );

    }

    //返回&销毁Activity
    private void returnActivity() {

        DialogUtil.query(
                mActivity,
                "确认您的选择",
                "取消支付？",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.cancel();
                        mApplication.finishActivity(mActivity);
                    }
                }
        );

    }

}
