package com.skyscape.refreshview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.LayoutRes;


import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.skyscape.refreshview.BindingAdapter;
import com.skyscape.refreshview.BindingViewHolder;
import com.skyscape.refreshview.R;

public class RefreshViewUtil<T> {
    private final Context mContext;
    private CallBack<T> mCallBack;
    private RefreshView<T> mRefreshView;
    private BindingAdapter<T> mAdapter;

    public RefreshViewUtil(Context context, ViewGroup parent) {
        this(context, parent, 0);
    }

    public RefreshViewUtil(Context context, ViewGroup parent, @LayoutRes int noDataLayout) {
        View view;
        this.mContext = context;
        mRefreshView = new RefreshView<>(context);
        parent.addView(mRefreshView);
        if (noDataLayout == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.refresh_nodata, null, false);
        } else {
            view = LayoutInflater.from(context).inflate(noDataLayout, null, false);
        }
        mRefreshView.setNoDataView(view);
        setNetDisconnectedView();


    }

    public void setCallBack(CallBack<T> mCallBack) {
        this.mCallBack = mCallBack;
    }

    private void setNetDisconnectedView() {
        NetDisconnectedView netDisconnectedView = new NetDisconnectedView(mContext);
        netDisconnectedView.setCallBack(() -> mRefreshView.onRefresh(mRefreshView));
        mRefreshView.setNetDisconnectedView(netDisconnectedView);

    }

    public RefreshViewUtil<T> setAdapter(int br_id, int layoutId){
        mAdapter=new BindingAdapter<T>(mContext,br_id,layoutId) {
            @Override
            public void setPresentor(BindingViewHolder holder, int position, T data) {
//mCallBack.setPresentor();
            }
        };
        return this;
    }
    public RefreshViewUtil<T> handleRefresh(){
        mRefreshView.setAdapter(mAdapter);
        mRefreshView.setRefreshViewListener(new RefreshView.RefreshViewListener<T>() {
            @Override
            public void requestLoadMore(int currentPage, int pageSize, RefreshLayout layout, BindingAdapter<T> adapter) {
                //mCallBack.requestLoadMore();
            }

            @Override
            public void requestRefresh(int currentPage, int pageSize, RefreshLayout layout, BindingAdapter<T> adapter) {
                    mCallBack.requestRefresh(currentPage,pageSize,adapter);
            }
        });
        return this;
    }

    public interface CallBack<T>{
        void requestLoadMore(int currentPage, int pageSize,  BindingAdapter<T> adapter);
        void requestRefresh(int currentPage, int pageSize, BindingAdapter<T> adapter);
        void setPresentor(BindingViewHolder holder, T data, int position, BindingAdapter<T> adapter);
    }
}
