package wiki.kotlin.mockapi.controllers;

import okhttp3.OkHttpClient;

public class ClientProvider {

    private static volatile OkHttpClient sClient;

    public synchronized static OkHttpClient getInstance() {
        if (sClient == null) {
            sClient = new ClientTestProvider().getInstance().build();
        }

        return sClient;
    }

}
