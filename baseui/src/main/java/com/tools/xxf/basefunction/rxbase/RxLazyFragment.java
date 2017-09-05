package com.tools.xxf.basefunction.rxbase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tools.xxf.basefunction.BaseViewContract;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * TODO:懒加载
 * @author XXF
 * Create Time : 2017/9/5 10:35
 */
public abstract class RxLazyFragment<T extends BaseViewContract.BasePresenter> extends 
        RxBaseFragment<T> {
    private View parentView;
    private FragmentActivity activity;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;
    private Unbinder bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(setLayoutId(), container, false);
        activity = getSupportActivity();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }


    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }


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

    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {
        return (T) parentView.findViewById(id);
    }
}
