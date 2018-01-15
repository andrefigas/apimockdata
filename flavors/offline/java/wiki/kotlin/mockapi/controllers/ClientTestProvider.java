package wiki.kotlin.mockapi.controllers;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import wiki.kotlin.mockapi.App;
import wiki.kotlin.mockapi.BuildConfig;
import wiki.kotlin.mockapi.api.BaseClient;
import wiki.kotlin.mockapi.models.Credentials;

;

public class ClientTestProvider extends BaseClient implements OkHttpMockInterceptor.Redirect {

    @Override
    public OkHttpClient.Builder getInstance() {
        return super.getInstance().addInterceptor(new OkHttpMockInterceptor(App.getInstance(), this));
    }

    @Override
    public String onInterceptedRoute(String route, Interceptor.Chain chain) {
        switch (route) {
            case BuildConfig.ROUTE_LOGIN:
                Credentials credentials = bodyToClass(chain, Credentials.class);
                //comparison test: md5("12345")
                if (!credentials.getPassword().equals("827ccb0eea8a706c4c34a16891f84e7b")) {
                    return BuildConfig.ROUTE_ERROR_CODE_1;
                }
                break;
        }

        return route;
    }
}
