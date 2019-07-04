package com.jiuwang.buyer.spinerwidget;

import android.widget.PopupWindow;


public class SpinerPopWindow extends PopupWindow {

//	private Context mContext;
//	private ListView mListView;
//	private AbstractSpinerAdapter mAdapter;
//	private AbstractSpinerAdapter.IOnItemSelectListener mItemSelectListener;
//	private int flag=0;
//
//
//	public SpinerPopWindow(int flag,Context context)
//	{
//		super(context);
//		mContext = context;
//		this.flag=flag;
//		init();
//	}
//
//
//	public void setItemListener(AbstractSpinerAdapter.IOnItemSelectListener listener){
//		mItemSelectListener = listener;
//	}
//
//	public void setAdatper(AbstractSpinerAdapter adapter){
//		mAdapter = adapter;
//		mListView.setAdapter(mAdapter);
//	}
//
//
//	private void init()
//	{
//		View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
//	    LinearLayout linear_backGround=(LinearLayout) view.findViewById(R.id.back_ground);
//		setContentView(view);
//		mListView = (ListView) view.findViewById(R.id.listview);
//		Resources resources =getContentView().getResources();
//		if(flag==1){
//		Drawable btnDrawable = resources.getDrawable(R.drawable.item);
//		linear_backGround.setBackground(btnDrawable);
//		}else {
//			int btnDrawable = resources.getColor(R.color.app_main_color);
//			mListView.setBackgroundColor(btnDrawable);
//		}
//		setWidth(LayoutParams.WRAP_CONTENT);
//		setHeight(LayoutParams.WRAP_CONTENT);
//		setFocusable(true);
//    	ColorDrawable dw = new ColorDrawable(0x00);
//		setBackgroundDrawable(dw);
//		mListView.setOnItemClickListener(this);
//	}
//
//
//	public <T> void refreshData(List<T> list, int selIndex)
//	{
//		if (list != null && selIndex  != -1)
//		{
//			if (mAdapter != null){
//				mAdapter.refreshData(list, selIndex);
//			}
//		}
//	}
//
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
//		dismiss();
//		if (mItemSelectListener != null){
//			mItemSelectListener.onItemClick(pos);
//		}
//	}


	
}
