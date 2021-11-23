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
import com.skyscape.refreshview.Type;

public abstract class NetDisconnectedView extends FrameLayout {
    private Context mContext;
    private SmartRefreshLayout mSmartRefreshLayout;
    private View mContentView;
    private View mRetryView;

    protected abstract View setRetryView();
    protected abstract SmartRefreshLayout setSmartRefreshLayout ();
    protected abstract @LayoutRes int setContentView();

    public NetDisconnectedView(@NonNull Context context) {
        this(context,null);
    }

    public NetDisconnectedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NetDisconnectedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        initView();
    }

    public View getContentView() {
        return mContentView;
    }


    private void initView() {
        mContentView = inflate(mContext, setContentView(), this);
        mRetryView=setRetryView();
        mSmartRefreshLayout=setSmartRefreshLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mRetryView==null||mSmartRefreshLayout==null||mContentView==null){
            Log.e(NetDisconnectedView.class.getName(), "onTouchEvent: --> 请检查view是否设置");
            return false;
        }
        float x = mRetryView.getX();
        float y = mRetryView.getY();
        int w = mRetryView.getWidth();
        int h = mRetryView.getHeight();
        //判断是否在view点击范围内（点击范围扩展100）
        boolean touched = event.getX() < x + w + 100 && event.getX() > x - 100 &&
                event.getY() < y + h + 100 && event.getY() > y - 100;
        if (touched) {
            mSmartRefreshLayout .autoRefresh();
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
