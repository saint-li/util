package com.saint.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;

public class DrawableClickEditText extends AppCompatEditText {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private OnClickListener onRightDrawableClick;

    public DrawableClickEditText(Context context) {
        super(context);

    }

    public DrawableClickEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public DrawableClickEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
            if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                    && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())
                    && onRightDrawableClick != null) {
                onRightDrawableClick.onClick(null);
                return true;
            }

        }
        return super.onTouchEvent(event);
    }

    public void setOnRightDrawableClick(OnClickListener onRightDrawableClick) {
        this.onRightDrawableClick = onRightDrawableClick;
    }
}
