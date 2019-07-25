package com.jiuwang.buyer.goods.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.constant.NetURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 图文详情webview的Fragment
 */
public class GoodsInfoWebFragment extends Fragment {
	private LayoutInflater inflater;
	private View rootView;

	private GoodsBean good;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.inflater = inflater;
		rootView = inflater.inflate(R.layout.fragment_item_info_web, null);
		Bundle arguments = getArguments();

		good = (GoodsBean) arguments.getSerializable("good");
		initWebView(rootView);
		return rootView;
	}

	public void initWebView(View rootView) {
		String pic_url = good.getPic_url();
		String[] split = pic_url.split(",");
		List<String> picList = new ArrayList<>();

		WebView webView = rootView.findViewById(R.id.wv_detail);
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
		Document doc = null;
		String mHtml = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open(
					"image.html"), "UTF-8"));
			String mLine = reader.readLine();
			while (mLine != null) {
				mHtml += mLine;
				mLine = reader.readLine();
			}
			Log.e("TAG", "mHtml >> " + mHtml);
			doc = Jsoup.parse(mHtml);
		} catch (IOException e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// log the exception
				}
			}
		}
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < split.length; i++) {
			stringBuffer.append("<div><img width=\"100%\" height=\"auto\" src=\"" + NetURL.PIC_BASEURL + split[i] + "\" onclick='javascript:injectObject.close();' /></div>");
		}
		Elements body = doc.select("body");
		webView.addJavascriptInterface(new JsObject(), "injectObject");//第4步骤
		body.html(stringBuffer.toString());
		webView.loadDataWithBaseURL("file:///android_asset/", doc.html(), "text/html", "UTF-8", "");

	}

	class JsObject {
		JsObject() {
		}

		public String toString() {
			return "injectedObject";
		}

		@JavascriptInterface
		public void close() {

		}
	}
}
