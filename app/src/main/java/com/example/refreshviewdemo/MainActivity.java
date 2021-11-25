package com.example.refreshviewdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.refreshviewdemo.databinding.ActivityMainBinding;
import com.skyscape.refreshview.BindingAdapter;
import com.skyscape.refreshview.BindingViewHolder;
import com.skyscape.refreshview.view.RefreshView;
import com.skyscape.refreshview.view.RefreshViewUtil;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        new RefreshViewUtil<String>(this,binding.container)
                .setAdapter(BR._all,R.layout.item)
                .handleRefresh()
                .setCallBack(new RefreshViewUtil.CallBack<String>() {
                    @Override
                    public void requestLoadMore(int currentPage, int pageSize, BindingAdapter<String> adapter) {

                    }

                    @Override
                    public void requestRefresh(int currentPage, int pageSize,
                                               RefreshViewUtil refreshViewUtil,
                                               RefreshView refreshView,
                                               BindingAdapter<String> adapter) {
//                        refreshViewUtil.handleData(Arrays.asList());
//                        refreshViewUtil.handleError();
                    }



                    @Override
                    public void setPresentor(BindingViewHolder holder, String data, int position, BindingAdapter<String> adapter) {

                    }
                });
    }
}