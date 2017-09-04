package com.tools.xxf.basefunction.uibase;

import android.os.Bundle;

/**
 * TODO 懒加载Fragment
 *
 * @author XXF
 *         Create Time : 2017/9/1 11:33
 */
public abstract class LazyFragment<T extends BaseViewContract.BasePresenter> extends BaseFragment<T> {

    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;


    /**
     * Fragment数据的懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * fragment显示时才加载数据
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * fragment懒加载方法
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initView();
        initLinistener();
        loadData();
        isPrepared = false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    /**
     * 初始化view相关数据
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected abstract void initLinistener();


    /**
     * fragment隐藏
     */
    protected void onInvisible() {
    }

    /**
     * 加载数据
     */
    protected abstract void loadData();

}
