package com.jiuwang.buyer.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.adapter.OrderListAdapter;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.OrderEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;



/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/20 15:53.
 */

public class OrderChildFragment extends Fragment implements XRecyclerView.LoadingListener{

	@Bind(R.id.listView)
	XRecyclerView listView;
	//公用变量
	public List<OrderBean> orderArrayList; //订单数组
	private int page = 1;
	private OrderListAdapter mAdapter;
	private int position;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_orderchild, null);
		ButterKnife.bind(this, view);
		Bundle arguments = getArguments();
		position = arguments.getInt("position");
		orderArrayList = new ArrayList<>();
		initView();
		initData();
		return view;
	}
	//初始化控件
	private void initView() {

		Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_sample);
		listView.addItemDecoration(listView.new DividerItemDecoration(dividerDrawable));
		AppUtils.initListView(getActivity(), listView, true, true);
		listView.setLoadingListener(this);


	}
	public void initData() {
		selectOrder(position);
	}
	private void selectOrder(int position) {

		if (CommonUtil.getNetworkRequest(getActivity())) {
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("currPage", String.valueOf(page));
			hashMap.put("pageSize", Constant.PAGESIZE);
			if (position == 0) {

			} else if (position == 1) {
				hashMap.put("status", "0");
			} else if (position == 2) {
				hashMap.put("status", "1");
			} else if (position == 3) {
				hashMap.put("status", "2");
			}
			HttpUtils.selectOrder(hashMap, new Consumer<OrderEntity>() {
				@Override
				public void accept(OrderEntity orderEntity) throws Exception {
					if (Constant.HTTP_SUCCESS_CODE.equals(orderEntity.getCode())) {

						if (page == 1) {
							orderArrayList.clear();
						}
						if (orderEntity.getData() != null) {
							for (int i = 0; i < orderEntity.getData().size(); i++) {
								List<OrderBean.DetailListBean> detailsList = new ArrayList<OrderBean.DetailListBean>();
								String[] goods_name = orderEntity.getData().get(i).getGoods_name().split(",");
								String[] quantity = orderEntity.getData().get(i).getQuantity().split(",");
								String[] sale_price = orderEntity.getData().get(i).getSale_price().split(",");
								String[] pic_url = orderEntity.getData().get(i).getPic_url().split(",");
								for (int j = 0; j < goods_name.length; j++) {

									OrderBean.DetailListBean detailListBean = new OrderBean.DetailListBean();
									detailListBean.setGoods_name(goods_name[j]);
									detailListBean.setGoods_num(quantity[j]);
									detailListBean.setGoods_price(sale_price[j]);
									if (pic_url.length < goods_name.length) {
										detailListBean.setPic_url(pic_url[0]);
									}else {

										detailListBean.setPic_url(pic_url[j]);
									}
									detailsList.add(detailListBean);
								}
								orderEntity.getData().get(i).setDetail_list(detailsList);
							}

						}
						orderArrayList.addAll(orderEntity.getData());

						if (mAdapter != null) {
							mAdapter.notifyDataSetChanged();
						} else {
							setAdapter();
						}
						if (page == 1) {
							listView.refreshComplete();
						} else {
							listView.loadMoreComplete();
						}

					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(orderEntity.getCode())) {
						MyToastView.showToast(orderEntity.getMsg(),getActivity());
						Intent intent = new Intent(getActivity(), LoginActivity.class);
						startActivity(intent);
						getActivity().finish();
					}
				}


			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {

				}
			});
		}

	}
	private void setAdapter() {
		mAdapter = new OrderListAdapter(MyApplication.getInstance(), orderArrayList);
		listView.setAdapter(mAdapter);
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@Override
	public void onRefresh() {
		page = 1;
		listView.refreshComplete();
		selectOrder(position);
	}

	@Override
	public void onLoadMore() {
		page++;
		listView.loadMoreComplete();
		selectOrder(position);
	}
}
