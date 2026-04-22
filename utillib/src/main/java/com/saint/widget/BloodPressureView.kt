package com.saint.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.saint.util.R
import androidx.core.graphics.toColorInt

class BloodPressureView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    // 颜色配置
    private var leftColor = "#E91E63".toColorInt()
    private var rightColor = "#FF9800".toColorInt()

    // 文字大小
    private var textSize = 14f

    // 进度条高度
    private var barHeight = 20f

    // 圆点半径
    private var circleRadius = 4f

    // 斜切宽度
    private var cutSize = 4f
    private var cutSpace = 45f // 默认间距

    // 左侧进度比例（0~1）
    private var leftRatio = 0.7f
    private var leftText: String? = "收缩压"
    private var rightText: String? = "舒张压"
    private var circlePadding = 4f
    private var indicatorHeight = 6f
    private var indicatorWidth = 20f
    private var indicatorPadding = 10f
    private var indicatorBarPadding = 10f//指示文字与进度样式间隔

    // 预定义的绘制对象
    private val textBounds = Rect()
    private val leftIndicatorRect = RectF()
    private val rightIndicatorRect = RectF()
    private val leftPath = Path()
    private val rightPath = Path()

    // 绘制用 Paint 对象
    private val leftPaint = Paint().apply {
        isAntiAlias = true
    }

    private val leftIndicatorPaint = Paint().apply {
        isAntiAlias = true
    }

    private val rightIndicatorPaint = Paint().apply {
        isAntiAlias = true
    }

    private val rightPaint = Paint().apply {
        isAntiAlias = true
    }

    private val circlePaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }

    private val labelPaint = Paint().apply {
        isAntiAlias = true
    }

    init {
        setWillNotDraw(false)

        // 获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BloodPressureView)
        try {
            leftColor = typedArray.getColor(R.styleable.BloodPressureView_leftColor, leftColor)
            rightColor = typedArray.getColor(R.styleable.BloodPressureView_rightColor, rightColor)
            leftText = typedArray.getString(R.styleable.BloodPressureView_leftText)
            rightText = typedArray.getString(R.styleable.BloodPressureView_rightText)
            textSize = typedArray.getDimension(R.styleable.BloodPressureView_textSize, textSize)
            barHeight = typedArray.getDimension(R.styleable.BloodPressureView_barHeight, barHeight)
            circleRadius = typedArray.getDimension(R.styleable.BloodPressureView_circleRadius, circleRadius)
            circlePadding = typedArray.getDimension(R.styleable.BloodPressureView_circlePadding, circlePadding)
            cutSize = typedArray.getDimension(R.styleable.BloodPressureView_cutSize, cutSize)
            leftRatio = typedArray.getFloat(R.styleable.BloodPressureView_leftRatio, leftRatio)
            indicatorWidth = typedArray.getDimension(R.styleable.BloodPressureView_indicatorWidth, indicatorWidth)
            indicatorHeight = typedArray.getDimension(R.styleable.BloodPressureView_indicatorHeight, indicatorHeight)
            indicatorPadding = typedArray.getDimension(R.styleable.BloodPressureView_indicatorPadding, indicatorPadding)
            indicatorBarPadding = typedArray.getDimension(R.styleable.BloodPressureView_indicatorBarPadding, indicatorBarPadding)
            cutSpace = typedArray.getFloat(R.styleable.BloodPressureView_cutSpace, cutSpace)
        } finally {
            typedArray.recycle()
        }

        // 初始化 Paint
        leftPaint.color = leftColor
        leftIndicatorPaint.color = leftColor
        leftIndicatorPaint.strokeCap = Paint.Cap.ROUND
        rightPaint.color = rightColor
        rightIndicatorPaint.color = rightColor
        rightIndicatorPaint.strokeCap = Paint.Cap.ROUND
        labelPaint.textSize = textSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var top = 0f
        // 绘制文字标签 及指示
        if (leftText != null && rightText != null) {
            labelPaint.getTextBounds(leftText, 0, leftText!!.length, textBounds)
            labelPaint.color = leftColor
            val textXLeft = indicatorWidth + 10f // 文字从最左侧开始
            val textYLeft = textBounds.height().toFloat()
            canvas.drawText(leftText!!, textXLeft, textYLeft, labelPaint)

            // 计算指示矩形的顶部和底部 Y 坐标
            val topY = textBounds.height() / 2f
            val bottomY = topY + indicatorHeight

            // 左侧颜色指示（圆角矩形）
            leftIndicatorRect.set(0f, topY, indicatorWidth, bottomY)
            canvas.drawRoundRect(leftIndicatorRect, 2f, 2f, leftIndicatorPaint)

            // 右侧颜色指示和文字
            labelPaint.getTextBounds(rightText, 0, rightText!!.length, textBounds)
            labelPaint.color = rightColor
            val textXRight = width - textBounds.width() - 10f
            canvas.drawText(rightText!!, textXRight, textYLeft, labelPaint)

            // 右侧颜色指示（圆角矩形）
            rightIndicatorRect.set(textXRight - indicatorWidth - 10f, topY, textXRight - 10f, bottomY)
            canvas.drawRoundRect(rightIndicatorRect, 2f, 2f, rightIndicatorPaint)
            top = textBounds.height() + indicatorBarPadding
        }

        val width = width
        val halfHeight = barHeight / 2
        val leftWidth = width * leftRatio
        val barBottom = top + barHeight

        // 绘制左侧区域
        canvas.drawCircle(halfHeight, top + halfHeight, halfHeight, leftPaint)

        // 更新左侧路径
        leftPath.rewind() // 清除之前的路径数据
        leftPath.moveTo(halfHeight, top)
        leftPath.lineTo(leftWidth + cutSpace, top)
        leftPath.lineTo(leftWidth, top + barHeight)
        leftPath.lineTo(halfHeight, top + barHeight)
        leftPath.close()
        canvas.drawPath(leftPath, leftPaint)

        // 绘制右侧区域
        canvas.drawCircle(width - halfHeight, top + halfHeight, halfHeight, rightPaint)

        // 更新右侧路径
        rightPath.rewind() // 清除之前的路径数据
        rightPath.moveTo(leftWidth + cutSpace + cutSize, top)
        rightPath.lineTo(width.toFloat() - halfHeight, top)
        rightPath.lineTo(width.toFloat() - halfHeight, top + barHeight)
        rightPath.lineTo(leftWidth + cutSize, top + barHeight)
        rightPath.close()
        canvas.drawPath(rightPath, rightPaint)

        // 绘制两端圆点
        canvas.drawCircle(halfHeight, top + halfHeight, circleRadius, circlePaint)
        canvas.drawCircle(width - halfHeight, top + halfHeight, circleRadius, circlePaint)
    }
}