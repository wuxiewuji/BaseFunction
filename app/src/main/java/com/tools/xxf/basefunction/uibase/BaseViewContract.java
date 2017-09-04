package com.tools.xxf.basefunction.uibase;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.google.gson.Gson;

/**
 * TODO 基层接口
 *
 * @author XXF
 *         Create Time : 2017/7/14 9:26
 */
public interface BaseViewContract {

    interface BaseView<BasePresenter> {

        /**
         * 正在加载
         *
         * @param msg ：加载显示的文字
         */
        void loading(String msg);

        /**
         * 加载完成
         */
        void loadcomplete();

        BasePresenter createPresenter();

        /**
         * Snackbar 代替Toast显示消息
         *
         * @param msg      ：显示的内容
         * @param druation ：显示内容的时间
         * @param listener :点击的监听事件
         */
        void showSnackbar(String msg, int druation, View.OnClickListener listener);

        @LayoutRes
        int setLayoutId();

    }

    interface BasePresenter {
        Gson gson = new Gson();
    }
}
