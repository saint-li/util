package com.saint.util.base;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
//    View itemView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <T extends View> T findView(int viewId) {
        return (T) itemView.findViewById(viewId);
    }
}

