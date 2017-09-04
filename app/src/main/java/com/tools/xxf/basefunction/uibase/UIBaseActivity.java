package com.tools.xxf.basefunction.uibase;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.ocean.motube.BaseActivity;
import com.ocean.motube.R;
import com.ocean.motube.widget.NoDataView;

/**
 * TODO:继承基础Activity，用于Dialog显示等的基础Activity
 *
 * @author XXF
 *         Create Time : 2017/7/13 17:05
 */

public abstract class UIBaseActivity<T> extends
        BaseActivity implements BaseViewContract.BaseView<T> {

    private ProgressDialog sendDialog;
    protected T mPresenter;
    private NoDataView mNoNetWorkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        //创建Prsenter
        mPresenter = createPresenter();
        settoobar();
        //无网络的View
        mNoNetWorkView = new NoDataView(this);

    }
    protected abstract String setToolTitle();
    //设置标题栏
    protected void settoobar() {

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //定义状态栏颜色
            StatusBarUtil.setColor(this, getResources().getColor(R.color.white,null), 5);
        }else {
            //定义状态栏颜色
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorfollowDark), 5);
        }
        if (null != mToolBar) {
            String title = setToolTitle();
            if (TextUtils.isEmpty(title)) {
                title = "";
            }
            mToolBar.setTitle(title);

            setSupportActionBar(mToolBar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    //返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param msg      ：显示的内容
     * @param druation ：显示内容的时间可以设定具体显示多少时间也可以设置下面Snackbar自带的
     *                 Snackbar.LENGTH_SHORT// 短时间显示，然后自动取消
     *                 Snackbar.LENGTH_LONG// 长时间显示，然后自动取消
     *                 Snackbar.LENGTH_INDEFINITE// 不消失显示，除非手动取消
     */
    @Override
    public void showSnackbar(String msg, int druation, final View.OnClickListener listener) {
        Snackbar.make(findViewById(R.id.toolbar), msg, druation)
                .setAction("OK", listener)
                .show();
    }

    @Override
    public void loading(String msg) {
        if (null == sendDialog)
            sendDialog = new ProgressDialog(this);
        sendDialog.setMessage(msg);
        sendDialog.setCanceledOnTouchOutside(false);
        sendDialog.show();
    }

    @Override
    public void loadcomplete() {
        if (sendDialog != null)
            sendDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        if (sendDialog != null) {
            sendDialog.dismiss();
            sendDialog = null;
        }
        if (null != mPresenter) {
            mPresenter = null;
        }
        super.onDestroy();
    }
}