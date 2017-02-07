package com.code4a.jlibrarydemo.pay;

import android.util.Log;

import com.code4a.jlibrary.utils.AESUtils;
import com.code4a.jlibrary.utils.GsonTools;
import com.code4a.jlibrarydemo.data.AlipayBean;
import com.code4a.jlibrarydemo.http.Code4aRetrofit;
import com.code4a.jlibrarydemo.http.Code4aService;
import com.code4a.jlibrarydemo.utils.Constants;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by code4a on 2017/2/6.
 */

public class PayInteractorImpl implements PayInteractor {

    private static final String TAG = "InteractorImpl";
    private boolean isCanceled = false;

    @Override
    public void getPayInfo(String payKey, final OnGetFinishedListener listener) {
        isCanceled = false;
        Code4aRetrofit.getRetrofit()
                .create(Code4aService.class)
                .getAlipayInfo(payKey)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>(){

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted ---> : xxxxxxxxxxxxxx ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError ---> : " + e);
                        if(!isCanceled){
                            listener.onFailed();
                        }
                    }

                    @Override
                    public void onNext(String result) {
                        if(!isCanceled){
                            Log.e(TAG, "result : " + result);
                            String decrypt = AESUtils.decrypt(Constants.AESKEY, result.trim());
                            Log.e(TAG, "decrypt : " + decrypt);
                            if(decrypt.contains("{")){
                                AlipayBean alipayBean = GsonTools.getBean(decrypt, AlipayBean.class);
                                listener.onSuccess(alipayBean);
                            }else{
                                listener.onFailed();
                            }
                        }
                    }
                });
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }
}
