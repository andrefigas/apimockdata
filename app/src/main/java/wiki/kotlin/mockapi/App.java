package wiki.kotlin.mockapi;


import android.app.Application;

public class App extends Application {

    private volatile static App sApp;

    public synchronized static App getInstance(){
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }
}
