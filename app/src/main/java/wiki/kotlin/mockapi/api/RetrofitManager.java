package wiki.kotlin.mockapi.api;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wiki.kotlin.mockapi.BuildConfig;
import wiki.kotlin.mockapi.controllers.ClientProvider;

public class RetrofitManager {

    public volatile static Retrofit sInstance;

    public synchronized static Retrofit getInstance() {

        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BuildConfig.ROUTE_API)
                    .client(ClientProvider.getInstance())
                    .build();
        }

        return sInstance;

    }

}
