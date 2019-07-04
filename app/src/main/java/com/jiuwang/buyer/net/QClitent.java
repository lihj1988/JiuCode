package com.jiuwang.buyer.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.NetURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QClitent {

	public static QClitent mQClient;

	private static OkHttpClient.Builder mBuilder;
	private static QNewsService qNewsService;

	public QClitent() {
		initSetting();
	}

	public static QNewsService getInstance() {
		if (mQClient == null) {
			synchronized (QClitent.class) {
				if (mQClient == null) {
					mQClient = new QClitent();
					qNewsService = mQClient.create(QNewsService.class, NetURL.BASEURL);
				}
			}
		}
		return qNewsService;
	}

	/**
	 * 创建相应的服务接口
	 */
	public static <T> T create(Class<T> service, String baseUrl) {
		T a;
		checkNotNull(service, "service is null");
		checkNotNull(baseUrl, "baseUrl is null");
		return new Retrofit.Builder()
				.baseUrl(baseUrl)
				.client(mBuilder.build())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()
				.create(service);
	}


	private static <T> T checkNotNull(T object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
		return object;
	}

	private void initSetting() {
		//初始化OkHttp
		mBuilder = new OkHttpClient.Builder()
				.connectTimeout(9, TimeUnit.SECONDS)    //设置连接超时 9s
				.readTimeout(10, TimeUnit.SECONDS)//设置读取超时 10s
				.cookieJar(new CookieJar() {
					private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

					@Override
					public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
						cookieStore.put(url.host(), cookies);
					}

					@Override
					public List<Cookie> loadForRequest(HttpUrl url) {
						List<Cookie> cookies = cookieStore.get(url.host());
						return cookies != null ? cookies : new ArrayList<Cookie>();
					}
				});
		//设置cookie保持
		CommonUtil.syncIsDebug(MyApplication.getInstance());
		if (CommonUtil.isDebug()) { // 判断是否为debug
			// 如果为 debug 模式，则添加日志拦截器
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			mBuilder.addInterceptor(interceptor);
			mBuilder.addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					Request mRequest = chain.request().newBuilder()
							.header("user-agent", "android")
							.build();
					return chain.proceed(mRequest);
				}
			});
		}

	}
}
