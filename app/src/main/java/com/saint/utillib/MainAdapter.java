package com.saint.utillib;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.saint.util.base.BaseRecyclerAdapter;
import com.saint.util.base.BaseViewHolder;
import com.saint.util.listener.OnItemCLick;

import static com.saint.util.util.CacheUtilKt.getCacheSize;

public class MainAdapter extends BaseRecyclerAdapter<String, MainAdapter.MainHolder> {
    private String[] strings;
    private OnItemCLick onItemCLick;
    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
        this.strings = context.getResources().getStringArray(R.array.mainMenu);
    }

    @Override
    protected MainHolder initViewHolder(ViewGroup viewGroup, int viewType) {
        return new MainHolder(inflaterView(viewGroup, R.layout.item_main));
    }

    @Override
    protected void setHolderData(MainHolder holder, int position) {
        String str = strings[position];
        if (!TextUtils.isEmpty(str) && str.startsWith("清除缓存")) {
            holder.tvText.setText("清除缓存：" + getCacheSize(context));
        } else if (!TextUtils.isEmpty(str)) {
            holder.tvText.setText(str);
        } else {
            holder.tvText.setText(R.string.app_name);
        }
        holder.itemView.setOnClickListener(view -> {
            if (onItemCLick != null) onItemCLick.onItemClick(position);
        });
    }

    public void setOnItemCLick(OnItemCLick onItemCLick) {
        this.onItemCLick = onItemCLick;
    }

    @Override
    public int getItemCount() {
        return strings == null ? 0 : strings.length;
    }

    class MainHolder extends BaseViewHolder {
        TextView tvText;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            tvText = findView(R.id.tv_text);
        }
    }
}
