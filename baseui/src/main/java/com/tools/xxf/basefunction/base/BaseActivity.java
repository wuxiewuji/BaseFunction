package com.tools.xxf.basefunction.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.tools.xxf.basefunction.BaseViewContract;
import com.tools.xxf.basefunction.R;
import com.tools.xxf.basefunction.staturbar.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * TODO:继承基础Activity，用于Dialog显示等的基础Activity
 *
 * @author XXF
 *         Create Time : 2017/7/13 17:05
 */

public abstract class BaseActivity<T> extends
        AppCompatActivity implements BaseViewContract.BaseView<T> {
    private Unbinder bind;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        //创建Prsenter
        mPresenter = createPresenter();
        //初始化控件绑定框架
        bind = ButterKnife.bind(this);
        setstaturbar();
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
    }
    //设置状态栏颜色
    protected void setstaturbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //定义状态栏颜色
            StatusBarUtil.setColor(this, getResources().getColor(R.color.white,null), 5);
        }else {
            //定义状态栏颜色
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorfollowDark), 5);
        }
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
    public  void initToolBar(){}
    @Override
    protected void onDestroy() {
        if (null != mPresenter) {
            mPresenter = null;
        }
        super.onDestroy();
        bind.unbind();
    }
}