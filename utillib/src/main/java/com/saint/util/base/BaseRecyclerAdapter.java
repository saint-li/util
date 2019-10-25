package com.saint.util.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    public List<T> list;


    public BaseRecyclerAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return initViewHolder(viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int i) {
        setHolderData((VH) holder, i);
    }

    protected abstract VH initViewHolder(ViewGroup viewGroup, int viewType);

    protected abstract void setHolderData(VH holder, int position);

    public View inflaterView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    /**
     * 添加显示的数据
     *
     * @param data List<String>
     */
    public void addData(List<T> data, int page) {
        if (list == null || list.size() == 0 || page == 1) {
            list = data;
        } else {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void refreshData(List<T> data) {
        list = data;
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        if (list != null && list.size() > 0) {
            list.clear();
            notifyDataSetChanged();
        }
    }


}
