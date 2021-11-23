package com.skyscape.refreshview.view;

import android.view.View;
import android.widget.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.skyscape.refreshview.Type;

public interface IRefreshView {
    void setNetDisconnectedView(NetDisconnectedView netDisconnectedView);

    void showNetDisconnectedView(Type type);

    void removeNetDisconnectedView();

    void setNoDataView(View noDataView);

    void showNoDataView();

    void removeNoDataView();

    void setErrorView(View errorView);

    void showErrorView();

    void removeErrorView();

    void setAdapter(RecyclerView.Adapter  adapter);

    void setLayoutManager(RecyclerView.LayoutManager layout);


}
