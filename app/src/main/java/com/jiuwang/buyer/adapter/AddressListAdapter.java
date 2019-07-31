package com.jiuwang.buyer.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AddressBean;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * 作者：lihj
 * 作用：地址列表适配器
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {

	private onItemClickListener itemClickListener;
	private onItemLongClickListener itemLongClickListener;
	private List<AddressBean> mArrayList;

	public AddressListAdapter(List<AddressBean> arrayList) {
		this.mArrayList = arrayList;
		this.itemClickListener = null;
	}

	@Override
	public int getItemCount() {
		return mArrayList.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {

		final AddressBean addressBean = mArrayList.get(position);

		holder.nameTextView.setText(addressBean.getConsignee_name());

//		if (TextUtil.isEmpty(addressBean.getConsignee_telephone())) {
			holder.phoneTextView.setText(addressBean.getConsignee_telephone());
//		} else {
//			holder.phoneTextView.setText(hashMap.get("tel_phone"));
//		}

//		if (hashMap.get("is_default").equals("1")) {
//			holder.addressTextView.setText("[默认] ");
			holder.addressTextView.setText(addressBean.getDestination());
//		} else {
//			holder.addressTextView.setText(hashMap.get("area_info"));
//		}

//		holder.addressTextView.append(" ");

		holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (itemClickListener != null) {
					itemClickListener.onItemClick(holder.getAdapterPosition());
				}
			}
		});

		holder.mRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (itemLongClickListener != null) {
					itemLongClickListener.onItemLongClick(holder.getAdapterPosition());
				}
				return false;
			}
		});
		if("0".equals(mArrayList.get(position).getIs_default())){
			holder.is_default.setChecked(false);
		}else if("1".equals(mArrayList.get(position).getIs_default())){
			holder.is_default.setChecked(true);
		}
		holder.is_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

				if(b){
					for (int i = 0; i < mArrayList.size(); i++) {
						if(i==position){
							mArrayList.get(i).setIs_default("1");
						}else {
							mArrayList.get(i).setIs_default("0");
						}
					}
					HashMap<String, String> hashMap = new HashMap<>();
					hashMap.put("id",mArrayList.get(position).getId());
					hashMap.put("act","4");
					setAddress(hashMap);

					new Handler().post(new Runnable() {
						@Override
						public void run() {
							// 刷新操作
							notifyDataSetChanged();
						}
					});

//					notifyDataSetChanged();
				}
			}
		});
//		holder.llIsDefault.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//
//				if (!holder.is_default.isChecked()) {
//					holder.is_default.setChecked(true);
//
//
//				}
//			}
//		});

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
		View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_address, group, false);
		return new ViewHolder(view);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public RelativeLayout mRelativeLayout;
		public TextView nameTextView;
		public TextView phoneTextView;
		public TextView addressTextView;
		public LinearLayout llIsDefault;
		public RadioButton is_default;

		public ViewHolder(View view) {
			super(view);

			mRelativeLayout = (RelativeLayout) view.findViewById(R.id.mainRelativeLayout);
			nameTextView = (TextView) view.findViewById(R.id.nameTextView);
			phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
			addressTextView = (TextView) view.findViewById(R.id.addressTextView);
			llIsDefault = (LinearLayout) view.findViewById(R.id.llIsDefault);
			is_default = view.findViewById(R.id.is_default);

		}

	}

	public void setOnItemClickListener(onItemClickListener listener) {
		this.itemClickListener = listener;
	}

	public interface onItemClickListener {
		void onItemClick(int position);
	}

	public void setOnItemLongClickListener(onItemLongClickListener listener) {
		this.itemLongClickListener = listener;
	}

	public interface onItemLongClickListener {
		void onItemLongClick(int position);
	}

	public void setAddress(HashMap<String,String> map){
		HttpUtils.addressInfo(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				MyToastView.showToast(baseResultEntity.getMsg(), MyApplication.getInstance());
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast(MyApplication.getInstance().getResources().getString(R.string.msg_error_operation), MyApplication.getInstance());
			}
		});
	}

}

