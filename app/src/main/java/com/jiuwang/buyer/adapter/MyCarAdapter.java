package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.CarBean;
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * author：
 * desc：购物车适配器
 * Created by  on  2019/6/20 14:43.
 */

public class MyCarAdapter extends BaseAdapter {
	private Context context;
	private List<CarBean> list;
	private ItemCheckStatusChangeListener itemCheckStatusChangeListener;
	private LoadingDialog loadingDialog;

	public MyCarAdapter(Context context, List<CarBean> list, ItemCheckStatusChangeListener itemCheckStatusChangeListener) {
		this.context = context;
		this.list = list;
		this.itemCheckStatusChangeListener = itemCheckStatusChangeListener;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		final CarBean data = list.get(position);
		if (view == null) {
			view = View.inflate(context, R.layout.item_car_list, null);
			viewHolder = new ViewHolder(view);
			viewHolder.radioButton = (CheckBox) view.findViewById(R.id.check_box);// 是否选中
			viewHolder.radioButton.setChecked(data.ischeck());
			viewHolder.seller = (TextView) view.findViewById(R.id.seller);// 卖家
			viewHolder.linerChild = (LinearLayout) view.findViewById(R.id.linear);
			viewHolder.rl_all = (LinearLayout) view.findViewById(R.id.rl_all);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.seller.setText(data.getGrouping_name());
		final ViewHolder finalViewHolder = viewHolder;
		viewHolder.rl_all.setOnClickListener(new View.OnClickListener() {//选择范围扩大
			@Override
			public void onClick(View v) {
				if (data.ischeck()) {
					data.setIscheck(false);
					finalViewHolder.radioButton.setChecked(false);
					changeAllListCbState(position, false);

				} else {
					data.setIscheck(true);
					for (int i = 0; i < list.size(); i++) {
						if(i==position){
							list.get(i).setIscheck(true);
						}else {
							list.get(i).setIscheck(false);
						}
					}
					finalViewHolder.radioButton.setChecked(true);
					changeAllListCbState(position, true);
//					changeAllListCbState(data.getGoods_detail(), true);

				}

			}
		});
		viewHolder.radioButton.setChecked(data.ischeck());
		viewHolder.linerChild.removeAllViews();
		viewHolder.radioButton.setClickable(false);
		if (data.getGoods_detail() != null) {
			for (int i = 0; i < data.getGoods_detail().size(); i++) {
				final CarGoodsBean carGoodsBean = data.getGoods_detail().get(i);
				View child = View.inflate(context, R.layout.item_car_child, null);
				final CheckBox checkitem_box = (CheckBox) child.findViewById(R.id.checkitem_box);
				LinearLayout ll_all = (LinearLayout) child.findViewById(R.id.ll_all);
				ImageView goodsImageView = (ImageView) child.findViewById(R.id.goodsImageView);//图片
				TextView goodsNameTextView = (TextView) child.findViewById(R.id.goodsNameTextView);
				TextView tvDesc = (TextView) child.findViewById(R.id.tvDesc);
				TextView tvPrice = (TextView) child.findViewById(R.id.tvPrice);
				TextView tvCheapPrice = (TextView) child.findViewById(R.id.tvCheapPrice);
				TextView delTextView = (TextView) child.findViewById(R.id.delTextView);
				final EditText tv_num = (EditText) child.findViewById(R.id.tv_num);// 购物车添加数量
				final ImageView add = (ImageView) child.findViewById(R.id.tv_add);// 添加加号
				final ImageView red = (ImageView) child.findViewById(R.id.tv_reduce);// 减号减少

				add.setTag(i);
				red.setTag(i);
				tv_num.setTag(i);
				delTextView.setTag(i);
				checkitem_box.setTag(i);
				final int finalI1 = i;
				add.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String quantity = String.valueOf(Integer.parseInt(carGoodsBean.getQuantity()) + 1);
						cartInfo(Constant.ACTION_ACT_UPDATA, position, finalI1, quantity);
					}
				});

				red.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (Integer.parseInt(carGoodsBean.getQuantity()) == 1) {
							MyToastView.showToast("商品数量不能少于1件", context);
//							return;
						} else {
							String quantity = String.valueOf(Integer.parseInt(carGoodsBean.getQuantity()) - 1);
							cartInfo(Constant.ACTION_ACT_UPDATA, position, finalI1, quantity);
						}

					}
				});
				delTextView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						cartInfo(Constant.ACTION_ACT_DELETE, position, finalI1, "");
					}
				});
				String[] split = carGoodsBean.getPic_url().split(",");

				CommonUtil.loadImage(context, NetURL.PIC_BASEURL + split[0], goodsImageView);

				goodsNameTextView.setText(carGoodsBean.getGoods_name());
				tvDesc.setText(carGoodsBean.getNotes());
				tvPrice.setText("¥" + carGoodsBean.getPrice() + "元");

				if ("".equals(carGoodsBean.getSale_price())) {
					tvCheapPrice.setVisibility(View.GONE);
				} else {
					tvCheapPrice.setVisibility(View.VISIBLE);
					tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
					tvCheapPrice.setText("¥" + carGoodsBean.getSale_price() + "元");
				}
				checkitem_box.setChecked(carGoodsBean.ischeck());
				tv_num.setText(carGoodsBean.getQuantity());
				final int finalI = i;
				checkitem_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
						list.get(position).getGoods_detail().get(finalI).setIscheck(b);
						if (b) {
							list.get(position).setIscheck(true);
						} else {
							for (int j = 0; j < list.get(position).getGoods_detail().size(); j++) {
								if (list.get(position).getGoods_detail().get(j).ischeck()) {
									list.get(position).setIscheck(true);
									break;
								}
								if (j == list.get(position).getGoods_detail().size() - 1) {
									list.get(position).setIscheck(false);
								}
							}
						}
						notifyDataSetChanged();
						itemCheckStatusChangeListener.checkStatusChange();

					}
				});
				viewHolder.linerChild.addView(child);
			}
		}
		return view;
	}

	private void changeAllListCbState(int postion, boolean b) {
		for (int i = 0; i < list.get(postion).getGoods_detail().size(); i++) {
			list.get(postion).getGoods_detail().get(i).setIscheck(b);
		}
		itemCheckStatusChangeListener.checkStatusChange();
		notifyDataSetChanged();
	}

	/**
	 * 购物车相关操作
	 */
	public void cartInfo(final String act, final int position, final int childPosition, final String quantity) {
		loadingDialog = new LoadingDialog(context);
		loadingDialog.show();
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("act", act);
		hashMap.put("id", list.get(position).getGoods_detail().get(childPosition).getId());
		hashMap.put("quantity", quantity);
		HttpUtils.cartInfo(hashMap, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				MyToastView.showToast(baseResultEntity.getMsg(), context);
//				Intent intent = new Intent();
//				intent.setAction("refreshCar");
//				context.sendBroadcast(intent);
				loadingDialog.dismiss();
				if (act.equals(Constant.ACTION_ACT_DELETE)) {
					list.get(position).getGoods_detail().remove(childPosition);
				} else {
					list.get(position).getGoods_detail().get(childPosition).setQuantity(quantity);
				}
				if(list.get(position).getGoods_detail().size()==0){
					list.remove(position);
					notifyDataSetChanged();
					itemCheckStatusChangeListener.checkStatusChange();
//					Intent intent = new Intent();
//					intent.setAction("refreshCar");
//					context.sendBroadcast(intent);
				}else {
					notifyDataSetChanged();
					itemCheckStatusChangeListener.checkStatusChange();
				}

			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				loadingDialog.dismiss();
				MyToastView.showToast("操作失败", context);
			}
		});
	}

	public interface ItemCheckStatusChangeListener {
		void checkStatusChange();
	}

	static class ViewHolder {
		@Bind(R.id.check_box)
		CheckBox radioButton;
		@Bind(R.id.seller)
		TextView seller;
		@Bind(R.id.rl_all)
		LinearLayout rl_all;
		@Bind(R.id.linear)
		LinearLayout linerChild;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
