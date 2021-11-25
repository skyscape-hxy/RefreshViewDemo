package com.skyscape.refreshview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.skyscape.refreshview.BindingAdapter;
import com.skyscape.refreshview.NetUtil;
import com.skyscape.refreshview.R;

import java.util.Arrays;
import java.util.List;

public class RefreshView<T> extends SmartRefreshLayout implements IRefreshView,
        OnRefreshLoadMoreListener {
    private int mCurrentPage = 1;//当前页码
    private int mPageSize = 20;//每页数据条数
    private View mNoDataView;//无数据view
    private View mNetDisconnectView;//无网络view
    private View mErrorView;//请求错误view
    private final Context mContext;
    private FrameLayout mContainer;
    private BindingAdapter<T> mAdapter;

    private RefreshViewListener<T> mRefreshViewListener;
    private RecyclerView mRv;


    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initThis();
    }


    private void initThis() {
        View view = inflate(mContext, R.layout.refreshview, this);
        mRv = view.findViewById(R.id.recyclerView);
        mContainer = view.findViewById(R.id.container);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        setRefreshHeader(new MaterialHeader(mContext));
        setRefreshFooter(new ClassicsFooter(mContext));
        setOnRefreshLoadMoreListener(this);
    }

    public void setRefreshViewListener(RefreshViewListener<T> mRefreshViewListener) {
        this.mRefreshViewListener = mRefreshViewListener;
    }

    @Override
    public void setNetDisconnectedView(View netDisconnectedView) {
        this.mNetDisconnectView = netDisconnectedView;
    }

    @Override
    public void showNetDisconnectedView() {
        if (null == mNetDisconnectView.getParent())
            mContainer.addView(mNetDisconnectView);

    }

    @Override
    public void removeNetDisconnectedView() {
        if (mNetDisconnectView.getParent()!=null) {
            mContainer.removeView(mNetDisconnectView);
        }
    }

    @Override
    public void setNoDataView(View noDataView) {
        this.mNoDataView =  noDataView;
    }

    @Override
    public void showNoDataView() {
        if (null == mNoDataView.getParent())
            mContainer.addView(mNoDataView);
    }

    @Override
    public void removeNoDataView() {
        if (mNoDataView.getParent()!=null) {
            mContainer.removeView(mNoDataView);
        }
    }

    @Override
    public void setErrorView(View errorView) {
        this.mErrorView = errorView;
    }

    @Override
    public void showErrorView() {
        if (null == mErrorView.getParent())
            mContainer.addView(mErrorView);
    }

    @Override
    public void removeErrorView() {
        if (mErrorView.getParent()!=null) {
            mContainer.removeView(mErrorView);
        }
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = (BindingAdapter<T>) adapter;
        mRv.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRv.setLayoutManager(layout);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (NetUtil.isNetworkConnected(mContext)) {
            mRefreshViewListener.requestLoadMore(mCurrentPage, mPageSize, refreshLayout, mAdapter);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (NetUtil.isNetworkConnected(mContext)) {
            removeNetDisconnectedView();
            mCurrentPage = 1;
            mRefreshViewListener.requestRefresh(mCurrentPage, mPageSize, refreshLayout, mAdapter);
        } else {
            if (mAdapter.getItemCount() == 0) {
                showNetDisconnectedView();
            }
            setEnableLoadMore(false);//出现状态页时禁止加载更多
            refreshLayout.finishRefresh();
        }
    }
    public void handleData(List<T> data) {
       removeErrorView();
        if (data != null && data.size() > 0) {//有数据
           removeNoDataView();
            mAdapter.refresh(data);
            mCurrentPage++;
          setEnableLoadMore(true);
        } else {
          showNoDataView();
           setEnableLoadMore(false);
        }
       finishRefresh();
    }

    public void handleError() {
        removeNoDataView();
        if (mAdapter.getItemCount() == 0) {
           showErrorView();
           setEnableLoadMore(false);
        }else {
            setEnableLoadMore(true);
        }
       finishRefresh();
    }

    public interface RefreshViewListener<T> {
        void requestLoadMore(int currentPage, int pageSize, RefreshLayout layout,
                             BindingAdapter<T> adapter);

        void requestRefresh(int currentPage, int pageSize, RefreshLayout layout,
                            BindingAdapter<T> adapter);

    }




}
