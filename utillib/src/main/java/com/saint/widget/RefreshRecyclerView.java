package com.saint.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.saint.util.R;

public class RefreshRecyclerView extends LinearLayout {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private OnRefreshLoadMoreListener listener;
    private OnRefreshListener refreshListener;
    private OnLoadMoreListener loadMoreListener;
    private EmptyView emptyView;


    public RefreshRecyclerView(Context context) {
        super(context);
        init();
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View view = inflate(getContext(), R.layout.layout_my_refresh_recycler_view, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (listener != null) listener.onLoadMore(refreshLayout);
                if (loadMoreListener != null) loadMoreListener.onLoadMore(refreshLayout);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (listener != null) listener.onRefresh(refreshLayout);
                if (refreshListener != null) refreshListener.onRefresh(refreshLayout);
            }
        });
    }


    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setListener(OnRefreshLoadMoreListener listener) {
        this.listener = listener;
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        refreshLayout.setEnableLoadMore(false);
        this.refreshListener = refreshListener;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        refreshLayout.setEnableRefresh(false);
        this.loadMoreListener = loadMoreListener;
    }

    public void finishLoad() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
//        showData();
    }

    public void finishLoadWithNetError() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        netError();
    }


    public void autoRefresh() {
        refreshLayout.autoRefresh();
    }

    public void onSuccess() {
        finishLoad();
        if (recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() > 0) {
            recyclerView.setVisibility(VISIBLE);
            emptyView.setVisibility(GONE);
        } else {
            noData();
        }
    }

    public void netError() {
        finishLoad();
        emptyView.setTextTips(R.string.loadDataErrorPullDownRefresh);
        emptyView.setIvTips(R.drawable.network_error);
        recyclerView.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
    }

    public void noData() {
        emptyView.noData();
        recyclerView.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
    }

    public void showError(String msg) {
        emptyView.setTextTips(msg);
        recyclerView.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
    }

    public void finishWithTips(String str, int resId) {
        finishLoad();
        emptyView.tips(str, resId);
        recyclerView.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
    }

    public void setTipsText(String msg) {
        emptyView.setTextTips(msg);
    }

}
