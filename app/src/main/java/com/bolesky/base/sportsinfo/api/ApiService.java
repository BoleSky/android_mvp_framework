package com.bolesky.base.sportsinfo.api;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */

public interface ApiService {

    @POST("account/v1/login")
    Observable<String> getData();
}
