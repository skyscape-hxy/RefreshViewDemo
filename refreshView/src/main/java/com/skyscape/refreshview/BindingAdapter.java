package com.skyscape.refreshview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BindingAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {
    private Context mContext;
    private int mBr_id;
    private final LayoutInflater mLayoutInflater;
    private List<T> mDataList;

    //设置item里面的view的事件
    public abstract void setPresentor(BindingViewHolder holder, int position, T data);

    @LayoutRes
    private int mLayoutId;

    public BindingAdapter(Context context, int br_id, int layoutId) {
        this.mContext = context;
        this.mBr_id = br_id;
        this.mLayoutId = layoutId;
        this.mDataList = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @NotNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new BindingViewHolder(mLayoutInflater.inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BindingViewHolder holder, int position) {
        //把数据实体类的信息传递给xml文件，同时把item在recycleView中对应位置的数据传过去
        if (mDataList.size() == 0) {
            return;
        }
        holder.getBinding().setVariable(mBr_id, mDataList.get(position));
        holder.getBinding().executePendingBindings();
        setPresentor(holder, position, mDataList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void refresh(Collection<T> list) {
        if (list == null) {
            return;
        }
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMore(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);
        if (position != this.mDataList.size()) {// 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, this.mDataList.size() - position);
        }
    }

    public void clear() {
        this.mDataList.clear();
        notifyDataSetChanged();
    }

    public void notify(int position) {
        notify(position);
    }
}
