package wiki.kotlin.mockapi.api;

import android.support.graphics.drawable.animated.BuildConfig;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.cert.CertificateException;

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
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;


public class BaseClient {
    public OkHttpClient.Builder getInstance() {
        OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(new StethoInterceptor());

        if (wiki.kotlin.mockapi.BuildConfig.SSL) {
            unsafe(builder);
        }

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpInterceptor = new HttpLoggingInterceptor();
            httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(httpInterceptor)
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request original = chain.request();
                                    Request request = original.newBuilder()
                                            .method(original.method(), original.body())
                                            .build();
                                    return chain.proceed(request);
                                }
                            }
                    );
        }

        return builder;

    }

    private static void unsafe(OkHttpClient.Builder builder) {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            X509TrustManager trustManager = (X509TrustManager) trustAllCerts[0];
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, trustManager);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T> T bodyToClass(Interceptor.Chain chain, Class<T> zClass) {
        try {
            final Request copy = chain.request().newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return new Gson().fromJson(buffer.readUtf8(), zClass);
        } catch (final IOException e) {
            return null;
        }
    }
}
