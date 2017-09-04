package com.tools.xxf.basefunction.uibase;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author :XXF
 *         packageName :dev.jr.moc.more.base
 *         FileName : BaseFragment
 *         Create-Time :  2017/7/13: 14 :10
 */
public abstract class BaseFragment<T extends BaseViewContract.BasePresenter> extends Fragment
        implements BaseViewContract
        .BaseView<T> {
    protected String classSimpleName = "";
    private ProgressDialog sendDialog;

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
     * @param state
     */
    public abstract void finishCreateView(Bundle state);

    @Override
    public void onResume() {
        super.onResume();
    }

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

    @Override
    public void showSnackbar(String msg, int druation, View.OnClickListener listener) {

    }

    @Override
    public void loading(String msg) {
        if (null == sendDialog && null != getActivity())
            sendDialog = new ProgressDialog(getActivity());
        sendDialog.setMessage(msg);
        sendDialog.show();
    }

    @Override
    public void loadcomplete() {
        if (sendDialog != null)
            sendDialog.dismiss();
    }
}
