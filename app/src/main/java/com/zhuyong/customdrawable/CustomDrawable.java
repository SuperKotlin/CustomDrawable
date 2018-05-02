package com.zhuyong.customdrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 类文件：自定义drawable
 * 作者：zhuyong on 2018/5/2 11:15
 * 邮箱：99305919@qq.com
 * 希望每天叫醒你的不是闹钟而是梦想
 */
public class CustomDrawable extends Drawable {

    private Paint mPaintText;
    private Paint paint;
    private String mText;
    private Context mContext;

    /**
     * 圆的半径，根据文字宽度计算而来
     */
    private float mCircleRadius;
    /**
     * 文字到圆边距,改变此值等于修改圆的padding值
     */
    private float mCircleRadiusMagin = 10;
    /**
     * 扩展角度(1-180°)，即菱形的内角,此值若大于180度则效果不会显示
     */
    private int mExtendAngle = 60;
    /**
     * 上面or下面
     */
    private boolean mIsUp = true;

    private int mBackGroundColor = Color.parseColor("#99000000");
    private int mTextColor = Color.parseColor("#333333");

    public CustomDrawable(Context mContext) {
        this.mContext = mContext;
        init();
    }

    /**
     * 设置上还是下
     *
     * @param mIsUp
     */
    public void setmIsUp(boolean mIsUp) {
        this.mIsUp = mIsUp;
    }

    /**
     * 设置背景颜色
     *
     * @param mBackGroundColor
     */
    public void setmBackGroundColor(int mBackGroundColor) {
        paint.setColor(mBackGroundColor);
    }

    /**
     * 设置绘制文字
     *
     * @param mText
     */
    public void setmText(String mText) {
        this.mText = mText;
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mBackGroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);

        mPaintText = new Paint();
        mPaintText.setColor(mTextColor);
        mPaintText.setTextSize(ScreenUtils.sp2px(mContext, 10));
        mPaintText.setAntiAlias(true);

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        startDraw(canvas);
    }

    private void startDraw(Canvas canvas) {
        //获取文字宽度
        float textWidth = mPaintText.measureText(mText, 0, mText.length());
        mCircleRadius = textWidth / 2 + mCircleRadiusMagin;
        /**
         * 我们限制一下最小值
         */
        if (mCircleRadius < ScreenUtils.dip2px(mContext, 12)) {
            mCircleRadius = ScreenUtils.dip2px(mContext, 12);
        }

        /**
         * 圆的半径有了，接下来确定圆心坐标
         * 根据mExtendAngle和勾股定理计算出菱形对角线一半的长度
         */
        float mDiamondVertical = (float) (Math.cos(Math.PI * (mExtendAngle / 2) / 180) * mCircleRadius);
        float mDiamondHorizontal = (float) (Math.sin(Math.PI * (mExtendAngle / 2) / 180) * mCircleRadius);
        /**
         * 菱形上(下)点坐标即圆心坐标是(0,±mDiamondVertical*2)
         * 菱形左点坐标：(-mDiamondHorizontal,±mDiamondVertical)
         * 菱形右点坐标：(-mDiamondHorizontal,±mDiamondVertical)
         */

        Path path = new Path();
        path.moveTo(-mDiamondHorizontal, mIsUp ? -mDiamondVertical : mDiamondVertical);
        path.lineTo(0, 0);
        path.lineTo(mDiamondHorizontal, mIsUp ? -mDiamondVertical : mDiamondVertical);

        RectF rectF = null;
        if (mIsUp) {
            rectF = new RectF(-mCircleRadius, -(mDiamondVertical * 2 + mCircleRadius)
                    , mCircleRadius, -(2 * mDiamondVertical - mCircleRadius));
            path.addArc(rectF, 90 + mExtendAngle / 2, 360 - mExtendAngle);
        } else {
            rectF = new RectF(-mCircleRadius, 2 * mDiamondVertical - mCircleRadius
                    , mCircleRadius, (mDiamondVertical * 2 + mCircleRadius));
            path.addArc(rectF, -(90 - mExtendAngle / 2), 360 - mExtendAngle);
        }
        canvas.drawPath(path, paint);

        /**
         * 计算文字的坐标点
         */
        float dx = -textWidth / 2;
        Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
        float dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        float baseLine = mIsUp ? (-mDiamondVertical * 2 + dy) : (mDiamondVertical * 2 + dy);
        canvas.drawText(mText, dx, baseLine, mPaintText);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
