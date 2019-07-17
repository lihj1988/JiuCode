package com.jiuwang.buyer.goods.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.GoodsDetailsActivty;

/**
 * 图文详情里的规格参数的Fragment
 */
public class GoodsConfigFragment extends Fragment {
    public GoodsDetailsActivty activity;
    public ListView lv_config;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (GoodsDetailsActivty) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_config, null);
        lv_config = (ListView) view.findViewById(R.id.lv_config);
        lv_config.setFocusable(false);
//        Bundle arguments = getArguments();
//        GoodsDetailsBean goods = (GoodsDetailsBean) arguments.getSerializable("goods");
//        if(goods!=null){
//            List<GoodsDetailsBean.ConfigInfoBean> data = new ArrayList<>();
//            data.addAll(goods.getConfig_info());
//            lv_config.setAdapter(new GoodsConfigAdapter(activity, data));
//        }

        return view;
    }
}
