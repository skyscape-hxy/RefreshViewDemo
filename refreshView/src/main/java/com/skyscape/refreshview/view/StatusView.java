package com.skyscape.refreshview.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.skyscape.refreshview.R;

// TODO: 2021/11/25 1.重试的水波纹待实现（暂时没有好的方案），2.引入xml改成addview
public class StatusView extends FrameLayout {

    private CallBack mCallBack;
    private TextView mTvStu;

    public StatusView(@NonNull Context context) {
        this(context, null);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        inflate(getContext(), R.layout.status_view, this);
        mTvStu = findViewById(R.id.tv_status);
        findViewById(R.id.tv_retry).setOnClickListener(v -> {
            if (mCallBack!=null) {
                mCallBack.retry();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public StatusView setStatus(int stuIcon, String stuText){
        mTvStu.setCompoundDrawablesRelativeWithIntrinsicBounds(0,stuIcon,0,0);
        mTvStu.setText(stuText);
        return this;

    }


    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface CallBack {
        void retry();
    }
}
