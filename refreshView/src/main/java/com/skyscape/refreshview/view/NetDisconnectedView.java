package com.skyscape.refreshview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.skyscape.refreshview.R;
import com.skyscape.refreshview.Type;

public class NetDisconnectedView extends FrameLayout {

    private CallBack mCallBack;

    public NetDisconnectedView(@NonNull Context context) {
        this(context, null);
    }

    public NetDisconnectedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetDisconnectedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        inflate(getContext(), R.layout.refresh_no_net, this);
        findViewById(R.id.tv_retry).setOnClickListener(v -> {
            if (mCallBack!=null) {
                mCallBack.retry();
            }
        });
    }

    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface CallBack {
        void retry();
    }
}
