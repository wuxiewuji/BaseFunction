package com.tools.xxf.basefunction.rxbase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tools.xxf.basefunction.BaseViewContract;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * TODO: 处理以为使用Rx导致内存泄漏而使用的 RxFragment
 *
 * @author XXF
 *         Create Time : 2017/9/5 10:02
 */
public abstract class RxBaseFragment<T extends BaseViewContract.BasePresenter> extends RxFragment
        implements BaseViewContract
        .BaseView<T> {
    protected String classSimpleName = "";

    protected View parentView;
    private FragmentActivity activity;

    private Unbinder bind;
    protected T mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(setLayoutId(), container, false);
        activity = getSupportActivity();

        return parentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //创建Prsenter
        mPresenter = createPresenter();
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }

    /**
     * 初始化views
     *
     * @param state ：保存的bundle
     */
    public abstract void finishCreateView(Bundle state);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mPresenter) {
            mPresenter = null;
        }
        bind.unbind();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
        classSimpleName = "lifeCycle    " + getClass().getSimpleName();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;

    }


    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) parentView.findViewById(id);
    }

}
