package com.getnerdify.android.notifier.sync;

import com.getnerdify.android.notifier.model.RetrofitUser;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class NotifierService {

    protected NotifierServiceInterface mRestAdapter;

    protected static final String API_END_POINT = "http://notifier.iliux.com/api";

    public NotifierService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(API_END_POINT)
            .build();

        mRestAdapter = restAdapter.create(NotifierServiceInterface.class);
    }

    public interface NotifierServiceInterface {

        @FormUrlEncoded
        @POST("/apiNotifier.php")
        public void login(
            @Field("metodo") String metodo,
            @Field("email") String email,
            @Field("password") String password,
            Callback<RetrofitUser> callback
        );

        @FormUrlEncoded
        @POST("/apiNotifier.php")
        public void signup(
            @Field("metodo") String metodo,
            @Field("nombre") String name,
            @Field("celular") String cellphone,
            @Field("email") String email,
            @Field("password") String password,
            Callback<RetrofitUser> callback
        );

    }

    public void login(RetrofitUser user, Callback<RetrofitUser> callback) {
        mRestAdapter.login("login", user.getEmail(), user.getPassword(), callback);
    }

    public void signup(RetrofitUser user, Callback<RetrofitUser> callback) {
        mRestAdapter.signup("signup", user.getNombre(), user.getCelular(), user.getEmail(), user.getPassword(), callback);
    }

}
