package com.saint.utillib;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.saint.util.base.BaseAct;
import com.saint.util.base.BaseRecyclerAdapter;
import com.saint.util.base.BaseViewHolder;
import com.saint.util.binding.BaseBindingAct;
import com.saint.util.listener.OnItemCLick;
import com.saint.util.util.AppUtil;
import com.saint.util.util.toast.AppToast;
import com.saint.utillib.databinding.ActToastBinding;

import java.util.Arrays;

public class ToastAct extends BaseBindingAct<ActToastBinding> implements OnItemCLick {
    private RecyclerView recyclerView;

    @Override
    protected void initTitleView() {
        mActionBar = findViewById(R.id.my_action_bar);
        mActionBar.setTitle(R.string.app_name);
        mActionBar.setLeftListener(view -> finish());
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        ToastTestAdapter adapter = new ToastTestAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addData(Arrays.asList(getResources().getStringArray(R.array.test_toast_items)), 1);
        adapter.setOnItemCLick(this);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                  AppToast.INSTANCE.tShort("苹果");
                break;
            case 1:
                  AppToast.INSTANCE.tShort("芒果");
                break;
            case 2:
                  AppToast.INSTANCE.showAtTop("橘子");
                break;
            case 3:
                  AppToast.INSTANCE.showInCenter("香蕉");
                break;
            case 4:
                  AppToast.INSTANCE.showAtLocation("荔枝", Gravity.LEFT | Gravity.TOP, 10, 90);
                break;
            case 5:
                AppUtil.copyString("荔枝");
                break;
            default:
                break;
        }
    }


    class ToastTestAdapter extends BaseRecyclerAdapter<String, ToastHolder> {
        private OnItemCLick onItemCLick;


        @Override
        protected ToastHolder initViewHolder(ViewGroup viewGroup, int viewType) {
            return new ToastHolder(inflaterView(viewGroup, R.layout.item_main));
        }

        @Override
        protected void setHolderData(ToastHolder holder, int position) {
            String str = list.get(position);
            if (!TextUtils.isEmpty(str)) {
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
    }


    class ToastHolder extends BaseViewHolder {
        TextView tvText;

        public ToastHolder(@NonNull View itemView) {
            super(itemView);
            tvText = findView(R.id.tv_text);
        }
    }
}
