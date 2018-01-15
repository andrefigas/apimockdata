package wiki.kotlin.mockapi.services;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import wiki.kotlin.mockapi.BuildConfig;
import wiki.kotlin.mockapi.api.ApiBaseResponse;
import wiki.kotlin.mockapi.models.Credentials;
import wiki.kotlin.mockapi.models.UserModel;

public interface UsersService {

    @POST(BuildConfig.ROUTE_LOGIN)
    Observable<ApiBaseResponse<UserModel>> login(@Body Credentials credentials);

}
