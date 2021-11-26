package com.skyscape.refreshview.view;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface IRefreshView {
    void setNetDisconnectedView(View netDisconnectedView);

    void showNetDisconnectedView();

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
