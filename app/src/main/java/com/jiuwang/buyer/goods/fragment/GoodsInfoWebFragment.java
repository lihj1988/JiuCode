package com.jiuwang.buyer.goods.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.goods.adaper.GoodsInfoPicAdapter;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.view.NestedScrollWebView;

import java.util.ArrayList;
import java.util.List;


/**
 * 图文详情webview的Fragment
 */
public class GoodsInfoWebFragment extends Fragment {
    public WebView wv_detail;
    private WebSettings webSettings;
    private LayoutInflater inflater;
    private View rootView;
    private LinearLayout ll;
    private List<NestedScrollWebView> webViews;
    private GoodsBean good;
    private XRecyclerView lvDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_item_info_web, null);
        Bundle arguments = getArguments();
        webViews = new ArrayList<>();
        good = (GoodsBean) arguments.getSerializable("good");
        initWebView(rootView);
        return rootView;
    }

    public void initWebView(View rootView) {
        ll = rootView.findViewById(R.id.ll);
//		lvDetail = rootView.findViewById(R.id.lvDetail);
//        AppUtils.initListView(getActivity(),lvDetail,false,false);
        String pic_url = good.getPic_url();
        String[] split = pic_url.split(",");
        List<String> picList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            picList.add(split[i]);

            NestedScrollWebView webView = new NestedScrollWebView(getActivity());
//支持javascript
            webView.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
            webView.getSettings().setSupportZoom(false);
// 设置出现缩放工具
            webView.getSettings().setBuiltInZoomControls(false);

//自适应屏幕
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);

// 设置滚动条不显示
            webView.setHorizontalScrollBarEnabled(false);
            webView.setVerticalScrollBarEnabled(false);

// 设置网络图片
            webView.loadUrl(NetURL.PIC_BASEURL+split[i]);
// 这里是将 new 出来的 webview 收集，在不使用的时候统一清空
            webViews.add(webView);

// 设置间距
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lineParams.setMargins(0, 0,0,0);
            webView.setLayoutParams(lineParams);   ImageView imageView = new ImageView(getActivity());
////         imageView.setScaleType(ImageView.ScaleType.CENTER);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            CommonUtil.loadImage(getActivity(), NetURL.PIC_BASEURL + split[i],imageView);
            ll.addView(webView);
        }
//        GoodsInfoPicAdapter goodsInfoPicAdapter = new GoodsInfoPicAdapter(getActivity(),picList);
//        lvDetail.setAdapter(goodsInfoPicAdapter);
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
