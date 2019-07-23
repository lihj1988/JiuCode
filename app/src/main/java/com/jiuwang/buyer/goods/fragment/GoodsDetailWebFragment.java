package com.jiuwang.buyer.goods.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.GoodsDetailsPicAdapter;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.util.MyList;

import java.util.ArrayList;
import java.util.List;


/**
 * 图文详情webview的Fragment
 */
public class GoodsDetailWebFragment extends Fragment {
	public WebView wv_detail;

	private WebSettings webSettings;
	private LayoutInflater inflater;
	private Bundle arguments;
	private GoodsBean good;
	private MyList lvDetail;
	private LinearLayout ll;
	private List<WebView> webViews;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.inflater = inflater;
		View rootView = inflater.inflate(R.layout.fragment_item_detail_web, null);
		arguments = getArguments();
		webViews = new ArrayList<>();
		good = (GoodsBean) arguments.getSerializable("good");
		initWebView(rootView);
		return rootView;
	}

	public void initWebView(View rootView) {
		ll = rootView.findViewById(R.id.ll);
		lvDetail = rootView.findViewById(R.id.lvDetail);
		String pic_url = good.getPic_url();
		String[] split = pic_url.split(",");
		List<String> picList = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			picList.add(split[i]);
		}
		GoodsDetailsPicAdapter goodsDetailsPicAdapter = new GoodsDetailsPicAdapter(getActivity(), picList);
		lvDetail.setAdapter(goodsDetailsPicAdapter);

//		for (int i = 0; i < split.length; i++) {
//			WebView webView = new WebView(getActivity());
////支持javascript
//			webView.getSettings().setJavaScriptEnabled(false);
//// 设置可以支持缩放
//			webView.getSettings().setSupportZoom(false);
//// 设置出现缩放工具
//			webView.getSettings().setBuiltInZoomControls(false);
//
////自适应屏幕
//			webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//			webView.getSettings().setLoadWithOverviewMode(true);
//			webView.getSettings().setUseWideViewPort(true);
//
//// 设置滚动条不显示
//			webView.setHorizontalScrollBarEnabled(false);
//			webView.setVerticalScrollBarEnabled(false);
//
//// 设置网络图片
//			webView.loadUrl(NetURL.PIC_BASEURL+split[i]);
//// 这里是将 new 出来的 webview 收集，在不使用的时候统一清空
//			webViews.add(webView);
//
//// 设置间距
//			LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//			lineParams.setMargins(0, 10,0,0);
//			webView.setLayoutParams(lineParams);
//			ll.addView(webView);
//		}
	}

}
