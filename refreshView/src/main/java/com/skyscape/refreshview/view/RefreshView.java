package com.skyscape.refreshview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.skyscape.refreshview.Type;

import java.util.List;

public class RefreshView<T> extends SmartRefreshLayout implements IRefreshView, OnRefreshLoadMoreListener {
    private int mCurrentPage = 1;//当前页码
    private int mPageSize = 20;//每页数据条数
    private View mNoDataView;//无数据view
    private NetDisconnectedView mNetDisconnectView;//无网络view
    private View mErrorView;//请求错误view
    private final Context mContext;
    private FrameLayout mContainer;
    private RecyclerView.Adapter mAdapter;

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
    public void setNetDisconnectedView(NetDisconnectedView netDisconnectedView) {

    }

    @Override
    public void showNetDisconnectedView() {

    }

    @Override
    public void removeNetDisconnectedView() {

    }

    @Override
    public void setNoDataView(View noDataView) {

    }

    @Override
    public void showNoDataView() {

    }

    @Override
    public void removeNoDataView() {

    }

    @Override
    public void setErrorView(View errorView) {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void removeErrorView() {

    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mRv.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRv.setLayoutManager(layout);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (NetUtil.isNetworkConnected(mContext)){
            mCurrentPage++;
            mRefreshViewListener.requestLoadMore(mCurrentPage,mPageSize,refreshLayout, (BindingAdapter<T>) mAdapter);
        }else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (NetUtil.isNetworkConnected(mContext)) {
            removeNetDisconnectedView();
            mCurrentPage=1;
            mRefreshViewListener.requestRefresh(mCurrentPage, mPageSize, refreshLayout, (BindingAdapter<T>) mAdapter);
        } else {
            if (mAdapter.getItemCount() == 0) {
                showNetDisconnectedView();
            }
            refreshLayout.finishRefresh();
        }
    }

    public interface RefreshViewListener<T> {
        void requestLoadMore(int currentPage, int pageSize, RefreshLayout layout,
                             BindingAdapter<T> adapter);

        void requestRefresh(int currentPage, int pageSize, RefreshLayout layout,
                            BindingAdapter<T> adapter);

    }


}
