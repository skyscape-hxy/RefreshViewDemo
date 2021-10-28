package com.skyscape.refreshview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final T binding;
    public BindingViewHolder(View itemView) {
        super(itemView);
        binding= DataBindingUtil.bind(itemView);

    }

    public T getBinding() {
        return binding;
    }
}
