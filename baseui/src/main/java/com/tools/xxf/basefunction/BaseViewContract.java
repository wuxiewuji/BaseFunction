package com.tools.xxf.basefunction;

import android.support.annotation.LayoutRes;
import com.google.gson.Gson;

/**
 * TODO 基层接口
 *
 * @author XXF
 *         Create Time : 2017/7/14 9:26
 */
public interface BaseViewContract {

    interface BaseView<BasePresenter> {

        BasePresenter createPresenter();

        @LayoutRes
        int setLayoutId();

    }

    interface BasePresenter {
        Gson gson = new Gson();
    }
}
