package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.SuccessBean;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;

import io.reactivex.functions.Consumer;


/**
 * Created by lihj on 2018/7/31.
 * 修改密码
 */

public class ChangePassActivity extends BaseActivity  {
//    @Bind(R.id.tv_save)
//    TextView tv_save;
//    @Bind(R.id.check_box)
//    CheckBox check_box;
//    @Bind(R.id.et_oldpass)
//    EditText et_oldpass;
//    @Bind(R.id.et_newpass)
//    EditText et_newpass;
//    @Bind(R.id.et_surepass)
//    EditText et_surepass;
//    @Bind(R.id.onclick_layout_left)
//    RelativeLayout onclick_layout_left;
//    @Bind(R.id.actionbar_text)
//    TextView actionbar_text;
//    @Bind(R.id.onclick_layout_right)
//    Button onclick_layout_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.changepassword);
//        ButterKnife.bind(this);
//        initView();
    }

//    private void initView() {
//        actionbar_text.setText("修改密码");
//        onclick_layout_right.setVisibility(View.GONE);
//        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    //选择状态 显示明文--设置为可见的密码
//                    et_oldpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    et_newpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    et_surepass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                } else {
//                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                    et_oldpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    et_newpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    et_surepass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//
//            }
//        });
//        tv_save.setOnClickListener(this);
//        onclick_layout_left.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_save:
//                oldpass = et_oldpass.getText().toString().trim();
//                newpass = et_newpass.getText().toString().trim();
//                surepass = et_surepass.getText().toString().trim();
//                if (oldpass.equals(PreforenceUtils.getStringData("loginInfo", "password"))) {//判断是否和原密码一致
//                    if (!newpass.equals("")) {
//                        if (newpass.length() >= 8) {//先判断长度是是否至少8位
//                            if (!TextUtils.isEmpty(surepass)) {
//                                if (surepass.equals(newpass)) {//最后判断两次新密码是否是一样
//                                    getData();
//                                } else {
//                                    MyToastView.showToast("两次输入的新密码不一致！", ChangePassActivity.this);
//                                }
//                            } else {
//                                MyToastView.showToast("请输入确认密码！", ChangePassActivity.this);
//                            }
//                        } else {
//                            MyToastView.showToast("密码至少8位以上！", ChangePassActivity.this);
//                        }
//                    } else {
//                        MyToastView.showToast("请输入新密码！", ChangePassActivity.this);
//                    }
//
//                } else {
//                    MyToastView.showToast("输入的原密码不正确！", ChangePassActivity.this);
//                }
//                break;
//            case onclick_layout_left:
//                finish();
//                break;
//        }
//    }

    private String oldpass, newpass, surepass;
    /**
     * 保存修改的密码
     */
//    private LoadingDialog mLoadingDialog;

    private void getData() {
        if (CommonUtil.getNetworkRequest(this)) {
//            mLoadingDialog = CommonUtil.setDialog_wait(ChangePassActivity.this, "10");
            HttpUtils.getchagepass("modifySelfPwd", newpass, oldpass, new Consumer<SuccessBean>() {
                @Override
                public void accept(SuccessBean entity) throws Exception {
//                    mLoadingDialog.dismiss();
                    if ("0".equals(entity.getCode())) {//0成功  1失败 2登录超时 重新登录
                        Intent intent = new Intent(ChangePassActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if ("2".equals(entity.getCode())) {
                        Intent intent = new Intent(ChangePassActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        MyToastView.showToast(entity.getMsg(), ChangePassActivity.this);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
//                    mLoadingDialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
    }
}
