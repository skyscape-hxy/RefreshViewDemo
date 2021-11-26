package com.skyscape.refreshview.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;


import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.skyscape.refreshview.BindingAdapter;
import com.skyscape.refreshview.BindingViewHolder;
import com.skyscape.refreshview.R;

import java.util.List;

public class RefreshViewUtil<T> {
    private final Context mContext;
    private CallBack<T> mCallBack;
    private RefreshView<T> mRefreshView;
    private BindingAdapter<T> mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public RefreshViewUtil(Context context, ViewGroup parent) {
        this.mContext = context;
        mRefreshView = new RefreshView<>(context);
        parent.addView(mRefreshView);
        initStuView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initStuView() {
        //框架默认状态页
        StatusView netDisconnectedView = new StatusView(mContext);
        StatusView noDataView = new StatusView(mContext);
        StatusView errorView = new StatusView(mContext);
        netDisconnectedView.setCallBack(() -> mRefreshView.autoRefresh());
        noDataView.setCallBack(() -> mRefreshView.autoRefresh());
        errorView.setCallBack(() -> mRefreshView.autoRefresh());
        mRefreshView.setNetDisconnectedView(netDisconnectedView.setStatus(R.drawable.ic_no_net,
                mContext.getString(R.string.no_net_tip)));
        mRefreshView.setNoDataView(noDataView.setStatus(R.drawable.ic_no_data,
                mContext.getString(R.string.no_data_tip)));
        mRefreshView.setErrorView(errorView.setStatus(R.drawable.ic_request_error,
                mContext.getString(R.string.error_tip)));
    }

    public void setCallBack(CallBack<T> mCallBack) {
        this.mCallBack = mCallBack;
    }


    public RefreshViewUtil<T> setAdapter(int br_id, int layoutId) {
        mAdapter = new BindingAdapter<T>(mContext, br_id, layoutId) {
            @Override
            public void setPresentor(BindingViewHolder holder, int position, T data) {
                mCallBack.setPresentor(holder, position, data, mAdapter);
            }
        };
        return this;
    }

    public RefreshViewUtil<T> handleRefresh() {
        mRefreshView.setAdapter(mAdapter);
        mRefreshView.setRefreshViewListener(new RefreshView.RefreshViewListener<T>() {
            @Override
            public void requestLoadMore(int currentPage) {
                mCallBack.requestLoadMore(currentPage, mRefreshView);
            }

            @Override
            public void requestRefresh(int currentPage) {
                mCallBack.requestRefresh(currentPage, mRefreshView);
            }
        });
        mRefreshView.autoRefresh();
        return this;
    }


    public interface CallBack<T> {
        default void requestLoadMore(int currentPage, RefreshView<T> refreshView) {
        }

        void requestRefresh(int currentPage, RefreshView<T> refreshView);

        default void setPresentor(BindingViewHolder holder, int position, T data,
                                  BindingAdapter<T> adapter) {
        }

    }



}
