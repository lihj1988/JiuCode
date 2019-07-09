package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.CarBean;
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.MyList;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.constant.NetURL;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.jiuwang.buyer.R.id.checkitem_box;
import static com.jiuwang.buyer.R.id.delTextView;
import static com.jiuwang.buyer.R.id.goodsNameTextView;
import static com.jiuwang.buyer.R.id.tvCheapPrice;
import static com.jiuwang.buyer.R.id.tvDesc;
import static com.jiuwang.buyer.R.id.tvPrice;
import static com.jiuwang.buyer.R.id.tv_num;

/**
 * 作者：lihj
 * 作用：购物车 RecyclerView 适配器
 */
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

	private static String userCode;
	List<CarBean> list;
	List<CarGoodsBean> caritemlist = new ArrayList<>();

	private CheckBox allcheck_box;
	private double allPriceDouble = 0.00; // 选中商品的总价
	private double alldisDouble = 0.00; // 选中商品的总优惠价
	private double itemallpricedouble = 0.00; //单条数据的总金额

	private Context context;
	List<CarGoodsBean> mlist = new ArrayList<>();

	public CartListAdapter(Context context, List<CarBean> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		final CarBean data = list.get(position);
		holder.seller.setText(data.getGrouping_name());
		holder.rl_all.setOnClickListener(new View.OnClickListener() {//选择范围扩大
			@Override
			public void onClick(View v) {
				if (data.ischeck()) {
					data.setIscheck(false);

					changeAllListCbState(data.getGoods_detail(), false);
//					Allmoney();
				} else {
					data.setIscheck(true);
					changeAllListCbState(data.getGoods_detail(), true);
//					Allmoney();
				}

			}
		});
		holder.radioButton.setEnabled(false);
		if (data.getGoods_detail() != null) {
//			for (int i = 0; i < data.getGoods_detail().size(); i++) {
//				final CarGoodsBean carGoodsBean = data.getGoods_detail().get(i);
//				View child = View.inflate(context, R.layout.item_car_child, null);
//				final CheckBox checkitem_box = (CheckBox) child.findViewById(checkitem_box);
//				LinearLayout ll_all = (LinearLayout) child.findViewById(R.id.ll_all);
//				ImageView goodsImageView = (ImageView) child.findViewById(R.id.goodsImageView);//图片
//				TextView goodsNameTextView = (TextView) child.findViewById(goodsNameTextView);
//				TextView tvDesc = (TextView) child.findViewById(tvDesc);
//				TextView tvPrice = (TextView) child.findViewById(tvPrice);
//				TextView tvCheapPrice = (TextView) child.findViewById(tvCheapPrice);
//				TextView delTextView = (TextView) child.findViewById(delTextView);
//				final EditText tv_num = (EditText) child.findViewById(tv_num);// 购物车添加数量
//				final ImageView add = (ImageView) child.findViewById(R.id.tv_add);// 添加加号
//				final ImageView red = (ImageView) child.findViewById(R.id.tv_reduce);// 减号减少
//
//				add.setTag(i);
//				red.setTag(i);
//				tv_num.setTag(i);
//				delTextView.setTag(i);
//				checkitem_box.setTag(i);
//
//				add.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						String quantity = String.valueOf(Integer.parseInt(carGoodsBean.getQuantity()) + 1);
//						cartInfo(Constant.ACTION_ACT_UPDATA, carGoodsBean.getId(), quantity);
//					}
//				});
//				red.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						if (Integer.parseInt(carGoodsBean.getQuantity()) == 1) {
//							MyToastView.showToast("商品数量不能少于1件", context);
////							return;
//						} else {
//							String quantity = String.valueOf(Integer.parseInt(carGoodsBean.getQuantity()) - 1);
//							cartInfo(Constant.ACTION_ACT_UPDATA, carGoodsBean.getId(), quantity);
//						}
//
//					}
//				});
//				delTextView.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						cartInfo(Constant.ACTION_ACT_DELETE, carGoodsBean.getId(), "");
//					}
//				});
//				CommonUtil.loadImage(context, NetURL.PIC_BASEURL + carGoodsBean.getPic_url(), goodsImageView);
//
//				goodsNameTextView.setText(carGoodsBean.getGoods_name());
//				tvDesc.setText(carGoodsBean.getNotes());
//				tvPrice.setText("¥" + carGoodsBean.getPrice() + "元");
//
//				if ("".equals(carGoodsBean.getSale_price())) {
//					tvCheapPrice.setVisibility(View.GONE);
//				} else {
//					tvCheapPrice.setVisibility(View.VISIBLE);
//					tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//					tvCheapPrice.setText("¥" + carGoodsBean.getSale_price() + "元");
//				}
//				checkitem_box.setChecked(carGoodsBean.ischeck());
//				tv_num.setText(carGoodsBean.getQuantity());
//				holder.linerChild.addView(child);
//			}for (int i = 0; i < data.getGoods_detail().size(); i++) {
//				final CarGoodsBean carGoodsBean = data.getGoods_detail().get(i);
//				View child = View.inflate(context, R.layout.item_car_child, null);
//				final CheckBox checkitem_box = (CheckBox) child.findViewById(checkitem_box);
//				LinearLayout ll_all = (LinearLayout) child.findViewById(R.id.ll_all);
//				ImageView goodsImageView = (ImageView) child.findViewById(R.id.goodsImageView);//图片
//				TextView goodsNameTextView = (TextView) child.findViewById(goodsNameTextView);
//				TextView tvDesc = (TextView) child.findViewById(tvDesc);
//				TextView tvPrice = (TextView) child.findViewById(tvPrice);
//				TextView tvCheapPrice = (TextView) child.findViewById(tvCheapPrice);
//				TextView delTextView = (TextView) child.findViewById(delTextView);
//				final EditText tv_num = (EditText) child.findViewById(tv_num);// 购物车添加数量
//				final ImageView add = (ImageView) child.findViewById(R.id.tv_add);// 添加加号
//				final ImageView red = (ImageView) child.findViewById(R.id.tv_reduce);// 减号减少
//
//				add.setTag(i);
//				red.setTag(i);
//				tv_num.setTag(i);
//				delTextView.setTag(i);
//				checkitem_box.setTag(i);
//
//				add.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						String quantity = String.valueOf(Integer.parseInt(carGoodsBean.getQuantity()) + 1);
//						cartInfo(Constant.ACTION_ACT_UPDATA, carGoodsBean.getId(), quantity);
//					}
//				});
//				red.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						if (Integer.parseInt(carGoodsBean.getQuantity()) == 1) {
//							MyToastView.showToast("商品数量不能少于1件", context);
////							return;
//						} else {
//							String quantity = String.valueOf(Integer.parseInt(carGoodsBean.getQuantity()) - 1);
//							cartInfo(Constant.ACTION_ACT_UPDATA, carGoodsBean.getId(), quantity);
//						}
//
//					}
//				});
//				delTextView.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						cartInfo(Constant.ACTION_ACT_DELETE, carGoodsBean.getId(), "");
//					}
//				});
//				CommonUtil.loadImage(context, NetURL.PIC_BASEURL + carGoodsBean.getPic_url(), goodsImageView);
//
//				goodsNameTextView.setText(carGoodsBean.getGoods_name());
//				tvDesc.setText(carGoodsBean.getNotes());
//				tvPrice.setText("¥" + carGoodsBean.getPrice() + "元");
//
//				if ("".equals(carGoodsBean.getSale_price())) {
//					tvCheapPrice.setVisibility(View.GONE);
//				} else {
//					tvCheapPrice.setVisibility(View.VISIBLE);
//					tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//					tvCheapPrice.setText("¥" + carGoodsBean.getSale_price() + "元");
//				}
//				checkitem_box.setChecked(carGoodsBean.ischeck());
//				tv_num.setText(carGoodsBean.getQuantity());
//				holder.linerChild.addView(child);
//			}

			holder.childListView.setAdapter(new BaseAdapter() {
				@Override
				public int getCount() {
					return data.getGoods_detail().size();
				}

				@Override
				public Object getItem(int i) {
					return data.getGoods_detail().get(i);
				}

				@Override
				public long getItemId(int i) {
					return i;
				}

				@Override
				public View getView(final int i, View view, ViewGroup viewGroup) {
					ViewHolder viewHolder = null;
					if (view == null) {
						view = View.inflate(context, R.layout.item_car_child, null);
						viewHolder = new ViewHolder();
						View child = View.inflate(context, R.layout.item_car_child, null);
						viewHolder.checkitem_box = (CheckBox) child.findViewById(checkitem_box);
						viewHolder.ll_all = (LinearLayout) child.findViewById(R.id.ll_all);
						viewHolder.goodsImageView = (ImageView) child.findViewById(R.id.goodsImageView);//图片
						viewHolder.goodsNameTextView = (TextView) child.findViewById(goodsNameTextView);
						viewHolder.tvDesc = (TextView) child.findViewById(tvDesc);
						viewHolder.tvPrice = (TextView) child.findViewById(tvPrice);
						viewHolder.tvCheapPrice = (TextView) child.findViewById(tvCheapPrice);
						viewHolder.delTextView = (TextView) child.findViewById(delTextView);
						viewHolder.tv_num = (EditText) child.findViewById(tv_num);// 购物车添加数量
						viewHolder.add = (ImageView) child.findViewById(R.id.tv_add);// 添加加号
						viewHolder.red = (ImageView) child.findViewById(R.id.tv_reduce);// 减号减少

						view.setTag(viewHolder);
					}else {
						viewHolder = (ViewHolder) view.getTag();
					}
					viewHolder.add.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String quantity = String.valueOf(Integer.parseInt(data.getGoods_detail().get(i).getQuantity()) + 1);
							cartInfo(Constant.ACTION_ACT_UPDATA, data.getGoods_detail().get(i).getId(), quantity);
						}
					});
					viewHolder.red.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (Integer.parseInt(data.getGoods_detail().get(i).getQuantity()) == 1) {
								MyToastView.showToast("商品数量不能少于1件", context);
//							return;
							} else {
								String quantity = String.valueOf(Integer.parseInt(data.getGoods_detail().get(i).getQuantity()) - 1);
								cartInfo(Constant.ACTION_ACT_UPDATA, data.getGoods_detail().get(i).getId(), quantity);
							}

						}
					});
					viewHolder.delTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							cartInfo(Constant.ACTION_ACT_DELETE, data.getGoods_detail().get(i).getId(), "");
						}
					});
					CommonUtil.loadImage(context, NetURL.PIC_BASEURL + data.getGoods_detail().get(i).getPic_url(), viewHolder.goodsImageView);

					viewHolder.goodsNameTextView.setText(data.getGoods_detail().get(i).getGoods_name());
					viewHolder.tvDesc.setText(data.getGoods_detail().get(i).getNotes());
					viewHolder.tvPrice.setText("¥" + data.getGoods_detail().get(i).getPrice() + "元");

					if ("".equals(data.getGoods_detail().get(i).getSale_price())) {
						viewHolder.tvCheapPrice.setVisibility(View.GONE);
					} else {
						viewHolder.tvCheapPrice.setVisibility(View.VISIBLE);
						viewHolder.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
						viewHolder.tvCheapPrice.setText("¥" + data.getGoods_detail().get(i).getSale_price() + "元");
					}
					viewHolder.checkitem_box.setChecked(data.getGoods_detail().get(i).ischeck());
					viewHolder.tv_num.setText(data.getGoods_detail().get(i).getQuantity());

					return view;
				}


				class ViewHolder {
					CheckBox checkitem_box;
					LinearLayout ll_all;
					ImageView goodsImageView;
					TextView goodsNameTextView;
					TextView tvDesc;
					TextView tvPrice;
					TextView tvCheapPrice;
					TextView delTextView;
					EditText tv_num;// 购物车添加数量
					ImageView add;// 添加加号
					ImageView red;// 减号减少
				}

			});

		}


	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
		View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_car_list, group, false);
		return new ViewHolder(view);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		CheckBox radioButton;// 是否选中
		TextView seller;// 卖家
		LinearLayout linerChild;
		LinearLayout rl_all;//选外层布局
		MyList childListView;//选外层布局

		public ViewHolder(View view) {
			super(view);
			radioButton = (CheckBox) view.findViewById(R.id.check_box);// 是否选中
			seller = (TextView) view.findViewById(R.id.seller);// 卖家
			linerChild = (LinearLayout) view.findViewById(R.id.linear);
			rl_all = (LinearLayout) view.findViewById(R.id.rl_all);


		}

	}

//	public void setOnTextWatcherListener(onTextWatcherListener textWatcher) {
//		this.mTextWatcher = textWatcher;
//	}
//
//	public interface onTextWatcherListener {
//		void onTextWatcher();
//	}
//
//	public void setOnDelClickListener(onDelClickListener clickListener) {
//		this.mDelClickListener = clickListener;
//	}
//
//	public interface onDelClickListener {
//		void onDelClick();
//	}


	//设置全选,或者全反选

	public void changeAllListCbState(List<CarGoodsBean> childList, boolean flag) {
		for (int i = 0; i < childList.size(); i++) {
			childList.get(i).setIscheck(flag);
		}
		notifyDataSetChanged();
	}


	private void changeChildStateChecked(int index, String flag) {
		for (int i = 0; i < list.size(); i++) {
			List<CarGoodsBean> myCar_childList = list.get(i).getGoods_detail();
			for (int j = 0; j < myCar_childList.size(); j++) {
				CarGoodsBean childEntity = myCar_childList.get(j);
				if (i == index) {
					if ("1".equals(flag)) {
						childEntity.setIscheck(true);
					}
				} else {
					childEntity.setIscheck(false);
				}
			}
		}
	}

	//判断二级列表是否全部选中
	private boolean isAllChildCbSelected(List<CarGoodsBean> myCar_childList) {
		for (int j = 0; j < myCar_childList.size(); j++) {
			CarGoodsBean childEntity = myCar_childList.get(j);
			if (!childEntity.ischeck()) {
				return false;
			}
		}
		return true;
	}

	//计算购物车下边显示的总件数，总钱数
	private void Allmoney() {
		mlist.clear();
		allPriceDouble = 0.0;
		alldisDouble = 0.0;
//		allnumber = 0;
//		allweight = 0.0;
		itemallpricedouble = 0.0;

		mlist = selectlist();
		if (mlist.size() != 0) {
			for (int i = 0; i < mlist.size(); i++) {
				Double itemprice = Double.parseDouble(mlist.get(i).getSale_price());//单价


//				itemallpricedouble = allwe * itemprice;


				allPriceDouble = allPriceDouble + itemallpricedouble;


			}
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			Double totalcount = Double.parseDouble(nf.format(allPriceDouble));

			Double totaldiscount = Double.parseDouble(nf.format(alldisDouble));
			String strweight = CommonUtil.getTwoDecimal(2, totalcount) + "";
			strweight = CommonUtil.splitString(strweight);
//            String[] split = strweight.split("\\.");
//            for (int i = 0; i < split.length; i++) {
//                if (split[1].length() == 1) {
//                    strweight = strweight + "0";
//                }
//            }
			String strdiscount = CommonUtil.splitString(CommonUtil.getTwoDecimal(2, totaldiscount) + "");
//            String[] split1 = strdiscount.split("\\.");
////            for (int i = 0; i <split1.length ; i++) {
//            if (split1[1].length() == 1) {
//                strdiscount = strdiscount + "0";
//            }
//            }
//			tv_car_total.setText(strweight);
//			tv_number.setText(allnumber + "");
//			tv_weight.setText(String.valueOf(CommonUtil.getTwoDecimal(4, allweight) + ""));
//			tv_cheapPrice.setText(strdiscount);

		} else {
//			tv_car_total.setText("0.00");
//			tv_number.setText("0");
//			tv_weight.setText("0");
//			tv_weight.setText("0.00");

		}
	}

	public List<CarGoodsBean> addlist = new ArrayList<>();

	public List<CarGoodsBean> selectlist() {
		addlist.clear();
		for (int i = 0; i < caritemlist.size(); i++) {
			CarGoodsBean entity = caritemlist.get(i);
			boolean isChecked = entity.ischeck();
			if (isChecked) {
				addlist.add(entity);
			}
		}
		return addlist;
	}

	/**
	 * 购物车相关操作
	 */
	public void cartInfo(String act, String id, String quantity) {
		DialogUtil.progress(MyApplication.currentActivity);
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("act", act);
		hashMap.put("id", id);
		hashMap.put("quantity", quantity);
		HttpUtils.cartInfo(hashMap, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				MyToastView.showToast(baseResultEntity.getMsg(), context);
				Intent intent = new Intent();
				intent.setAction("refreshCar");
				context.sendBroadcast(intent);
//				notifyDataSetChanged();
				DialogUtil.cancel();
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				DialogUtil.cancel();
				MyToastView.showToast("操作失败", context);
			}
		});
	}

}

