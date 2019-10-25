package com.saint.util.other;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.saint.util.R;

/**
 * @author KCrason
 * @date 2018/4/28
 */
public class TextClickSpan extends ClickableSpan {

    private Context mContext;

    private boolean mPressed ;
    private View.OnClickListener onClickListener;
    private int colorId;

    public TextClickSpan(Context context,View.OnClickListener onClickListener,  int colorId) {
        this.mContext = context;
        this.onClickListener = onClickListener;
        this.colorId = colorId;
    }

    public void setPressed(boolean isPressed) {
        this.mPressed = isPressed;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.bgColor = mPressed ? ContextCompat.getColor(mContext, R.color.colorAD) : Color.TRANSPARENT;
        ds.setColor(ContextCompat.getColor(mContext, colorId));
//        ds.setTextSize(AppUtil.getDimen(textSize));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View view) {
        if (onClickListener !=null){
            onClickListener.onClick(view);
        }
    }
}
