package com.github.wwwlly.net;

import android.annotation.SuppressLint;

import java.io.IOException;
import java.net.Proxy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wkp on 2016/5/6.
 */
public class OkHttpUtils {
    public static final int TIMEOUT = 10;
    public static final int TIMEOUT_SOCKET = 100;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private OkHttpClient mSocketClient;

    private OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(true)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(new NetInterceptor())
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request.Builder builder = chain.request().newBuilder();
//                            builder
//                                    .addHeader("cha", AppInfoUtil.getChannelFromPackage())
//                                    .addHeader("av", AppInfoUtil.getVersionName())
//                                    .addHeader("dvtype", "android")
//                                    .addHeader("dvid", DeviceInfoUtil.getDeviceId())
//                                    .addHeader("shumeiid", ToolBox.getShumeiId());
//                            Request request = builder.build();
//                            return chain.proceed(request);
//                        }
//
//                    })
                    .sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new TrustAllHostnameVerifier());
//            if(AppConfig.getEnvMode() == AppConfig.EnvMode.OP && !SPUtils.getBoolean(SPConstants.SP_NET_STATUS, false)) {
//                builder.proxy(Proxy.NO_PROXY);
//            }
            mOkHttpClient = builder.build();
        } else {
            mOkHttpClient = okHttpClient;
        }
        if (mSocketClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(true)
                    .connectTimeout(TIMEOUT_SOCKET, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_SOCKET, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_SOCKET, TimeUnit.SECONDS)
//                    .addInterceptor(new NetInterceptor())
                    .sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .pingInterval(10, TimeUnit.SECONDS);
            mSocketClient = builder.build();
        }
    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(null);
                }
            }
        }
        return mInstance;
    }

    /**
     * 默认信任所有的证书
     */
    @SuppressLint("TrulyRandom") private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new TrustAllManager() }, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSLSocketFactory;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public OkHttpClient getSocketClient() {
        return mSocketClient;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {

        @Override public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
