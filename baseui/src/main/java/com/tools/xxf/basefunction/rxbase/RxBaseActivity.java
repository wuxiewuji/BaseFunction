package com.tools.xxf.basefunction.rxbase;

import android.os.Bundle;

import com.tools.xxf.basefunction.BaseViewContract;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * TODO: 处理以为使用Rx导致内存泄漏而使用的 RxAppCompatActivity
 * @author XXF
 * Create Time : 2017/9/5 10:02
 */
public abstract class RxBaseActivity<T extends BaseViewContract.BasePresenter> extends RxAppCompatActivity implements BaseViewContract
        .BaseView<T>{
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(setLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //模拟加载数据 用rx防止内存泄露
        testRxJava();
    }

    private void testRxJava() {
        //绑定方式一  同Activity生命周期
        Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                int i = 0;
                while (i < 1000000000) {
                    i++;
                }
                subscriber.onNext(String.valueOf(i));
                subscriber.onCompleted();
            }
        }).compose(this.<String>bindToLifecycle())//指定同Activity同生命周期
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                    }
                });
        //绑定方式二  指定某个生命周期停止时间订阅
        Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                int i = 0;
                while (i < 1000000000) {
                    i++;
                }
                subscriber.onNext(String.valueOf(i));
                subscriber.onCompleted();
            }
        }).compose(this.<String>bindUntilEvent(ActivityEvent.STOP))//指定STOP的时候停止订阅
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                    }
                });
    }

    /**
     * 初始化views
     *
     * @param savedInstanceState :保存的缓存数据
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化toolbar
     */
    public abstract void initToolBar();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
