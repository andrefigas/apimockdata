package wiki.kotlin.mockapi.controllers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpMockInterceptor implements Interceptor {
    public final static int DELAY_DEFAULT_MIN = 500;
    public final static int DELAY_DEFAULT_MAX = 1500;
    private Redirect mRedirect;
    private Context mContext;
    private int mMinDelayMilliseconds;
    private int mMaxDelayMilliseconds;

    public OkHttpMockInterceptor(Context context) {
        mContext = context;
        mMinDelayMilliseconds = DELAY_DEFAULT_MIN;
        mMaxDelayMilliseconds = DELAY_DEFAULT_MAX;

    }

    public OkHttpMockInterceptor(Context context, Redirect redirect) {
        this(context);
        mRedirect = redirect;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String responseString = loadAssetTextAsString(mContext,((mRedirect == null) ?
                getRoute(chain).substring(1) : mRedirect.onInterceptedRoute(getRoute(chain).substring(1), chain))
                        + ".json");
        try {
            Thread.sleep(Math.abs(new Random()
                    .nextInt() % (mMaxDelayMilliseconds - mMinDelayMilliseconds))
                    + mMinDelayMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString))
                .addHeader("content-type", "application/json")
                .build();
    }

    public String getRoute(Chain chain) {
        Request request = chain.request();

        HttpUrl url = request.url();

        String sym = "";
        String query = url.encodedQuery() == null ? "" : url.encodedQuery();
        if (!query.equals(""))
            sym = "/";
        return url.encodedPath() + sym + query;
    }

    public static String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e(context.getPackageName(), "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(context.getPackageName(), "Error closing asset " + name);
                }
            }
        }
        return null;
    }


    public interface Redirect {

        String onInterceptedRoute(String route, Chain chain);

    }
}