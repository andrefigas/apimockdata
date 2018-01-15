package wiki.kotlin.mockapi;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import wiki.kotlin.mockapi.api.ApiBaseResponse;
import wiki.kotlin.mockapi.api.RetrofitManager;
import wiki.kotlin.mockapi.models.Credentials;
import wiki.kotlin.mockapi.models.UserModel;
import wiki.kotlin.mockapi.services.UsersService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.go).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView tvLogin = (TextView) findViewById(R.id.login);
        TextView tvPassword = (TextView) findViewById(R.id.password);

        String strLogin = tvLogin.getText().toString();
        String strPassword = tvPassword.getText().toString();

        RetrofitManager.getInstance().create(UsersService.class).login(new Credentials(strLogin, strPassword))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ApiBaseResponse<UserModel>>() {
                    @Override
                    public void accept(ApiBaseResponse<UserModel> response) throws Exception {
                        Toast.makeText(MainActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }
}
