package wiki.kotlin.mockapi.controllers;

import okhttp3.OkHttpClient;
import wiki.kotlin.mockapi.api.BaseClient;

public class ClientProvider {

    private static volatile OkHttpClient sClient;

    public synchronized static OkHttpClient getInstance() {
        if(sClient== null){
            sClient = new BaseClient().getInstance().build();
        }

        return sClient;
    }

}
