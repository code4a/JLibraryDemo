package com.code4a.jlibrarydemo.http;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by gaohailong on 2016/5/17.
 */
public interface Code4aService {

    @GET("{key}")
    Observable<String> getAlipayInfo(
            @Path("key") String key
    );

}
