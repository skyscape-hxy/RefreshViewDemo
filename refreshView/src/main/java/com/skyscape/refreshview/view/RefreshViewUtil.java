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
        //框架默认状态页，根据具体情况进行配置
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

    public RefreshView<T> getRefreshView() {
        return mRefreshView;
    }

    public RefreshViewUtil setCallBack(CallBack<T> mCallBack) {
        this.mCallBack = mCallBack;
        return this;
    }


    public RefreshViewUtil<T> setAdapter(int br_id, int layoutId) {
        mAdapter = new BindingAdapter<T>(mContext, br_id, layoutId) {
            @Override
            public void setPresentor(BindingViewHolder holder, int position, T data) {
//mCallBack.setPresentor();
            }
        };
        return this;
    }

    public RefreshViewUtil<T> handleRefresh() {
        mRefreshView.setAdapter(mAdapter);
        mRefreshView.setRefreshViewListener(new RefreshView.RefreshViewListener<T>() {
            @Override
            public void requestLoadMore(int currentPage, int pageSize, RefreshLayout layout,
                                        BindingAdapter<T> adapter) {
                //mCallBack.requestLoadMore();
            }

            @Override
            public void requestRefresh(int currentPage, int pageSize, RefreshLayout layout,
                                       BindingAdapter<T> adapter) {
                mCallBack.requestRefresh(currentPage, pageSize, RefreshViewUtil.this,
                        mRefreshView, adapter);
            }
        });
        return this;
    }




    public interface CallBack<T> {
        void requestLoadMore(int currentPage, int pageSize, BindingAdapter<T> adapter);

        void requestRefresh(int currentPage, int pageSize, RefreshViewUtil refreshViewUtil,
                            RefreshView refreshView,
                            BindingAdapter<T> adapter);

        void setPresentor(BindingViewHolder holder, T data, int position,
                          BindingAdapter<T> adapter);
    }

}
