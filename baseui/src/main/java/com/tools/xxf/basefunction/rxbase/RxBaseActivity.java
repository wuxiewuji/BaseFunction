package com.tools.xxf.basefunction.rxbase;

import android.os.Bundle;

import com.tools.xxf.basefunction.BaseViewContract;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    }

    /**
     * 初始化views
     *
     * @param savedInstanceState
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
