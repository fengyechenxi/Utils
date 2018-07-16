package com.fycx.utils;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * rxjava工具类
 * 验证码按钮的倒计时
 */
public class RxUtils {

    public static final int COUNT_DOWN_SECOND = 60;

    private RxUtils(){
        throw new AssertionError();
    }

    /**
     * 验证码按钮倒计时
     * @param button
     * @param second
     */
    public static void verificationCountDown(final Button button, final int second, final View.OnClickListener onClickListener){
        RxView.clicks(button)
                .subscribeOn(AndroidSchedulers.mainThread())
                .throttleFirst(second, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (onClickListener != null) {
                            onClickListener.onClick(button);
                        }
                        Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                                .take(second)
                                .subscribe(new Observer<Long>() {
                                    @Override
                                    public void onCompleted() {
                                        RxTextView.text(button).call("获取验证码");
                                        RxView.enabled(button).call(true);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("", e.toString());
                                    }

                                    @Override
                                    public void onNext(Long aLong) {
                                        RxView.enabled(button).call(false);
                                        RxTextView.text(button).call("剩余" + (second - aLong) + "秒");
                                    }
                                });
                    }
                });
    }


}
