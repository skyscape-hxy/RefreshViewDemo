package com.skyscape.refreshview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.skyscape.refreshview.BindingAdapter;
import com.skyscape.refreshview.NetUtil;
import com.skyscape.refreshview.R;
import com.skyscape.refreshview.Type;

import java.util.List;

public class RefreshView<T> extends SmartRefreshLayout implements IRefreshView {
    //    private View mNoDataView;
//    private NetDisconnectedView mNetDisconnectView;
//    private RecyclerView mRecyclerView;
//    private SmartRefreshLayout mSmartRefreshLayout;
    private Context mContext;
    private FrameLayout mContainer;
    private Adapter mAdapter;

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
        setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
mRefreshViewListener.requestRefresh(0,0,null, (BindingAdapter<T>) mAdapter);
            }
        });

    }

    public void setRefreshViewListener(RefreshViewListener<T> mRefreshViewListener) {
        this.mRefreshViewListener = mRefreshViewListener;
    }

    @Override
    public void setNetDisconnectedView(NetDisconnectedView netDisconnectedView) {

    }

    @Override
    public void showNetDisconnectedView(Type type) {

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
    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        mRv.setAdapter((RecyclerView.Adapter) adapter);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRv.setLayoutManager(layout);
    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void doLoadMore() {

    }

    public interface RefreshViewListener<T> {
        void requestLoadMore(int currentPage, int pageSize, RefreshLayout layout, BindingAdapter<T> adapter);

        void requestRefresh(int currentPage, int pageSize, RefreshLayout layout, BindingAdapter<T> adapter);

    }


}
