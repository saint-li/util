package com.saint.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.saint.util.util.AppUtil;

public class CornersImagView extends ImageView {
    float width, height = 0;
    private int radius = 0;

    public CornersImagView(Context context) {
        super(context);
        width = getWidth();
        height = getHeight();
    }

    public CornersImagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        width = getWidth();
        height = getHeight();
    }

    public CornersImagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        width = getWidth();
        height = getHeight();
    }

    public void setRadius(int dimenRes) {
        this.radius = AppUtil.getDimen(dimenRes);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float y = this.getScrollY();//因为webview是可滚动的，所以它的高度是变化的，每个height地方都需要加上滚动值。不加的话控件的高度不能变更，此圆角构成方法适用于其他的控件，如Imageview，此时无需加y.
        int r = radius;
        if (width > r && height > r) {
            Path path = new Path();
            path.moveTo(r, 0);
            path.lineTo(width - r, 0);
            path.quadTo(width, 0, width, r);
            path.lineTo(width, y + height - r);//1,r改为0
            path.quadTo(width, y + height, width - r, y + height); //2,r改为0
            path.lineTo(r, y + height);//3,r改为0
            path.quadTo(0, height, 0, y + height - r); //4,r改为0   这四处r改为0即可实现上左上右为圆角，否则四角皆为圆角
            path.lineTo(0, r);
            path.quadTo(0, 0, r, 0);
            if (r > 0) {
                canvas.clipPath(path);//将路径闭合构成控件的区域
            }
        }
        super.onDraw(canvas);
    }
}
