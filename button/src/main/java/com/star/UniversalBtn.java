package com.star;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * 自定义随意改变Button圆角、直角、按下效果
 * 目的：减少重复造selector、减小图片资源占用使apk变小
 *
 * @Author: star
 * @Email: guimingxing@163.com
 * @Date: 2017/3/28 9:05
 */
public class UniversalBtn extends Button {
    private StateListDrawable selector;
    private int radius;
    private int strokeWidth = 1;//默认描边
    private boolean isSelected;//是否选中
    private Drawable normalDrawable;//正常图片
    private Drawable pressedDrawable;//按下时图片
    //private int normalTextColor;//正常文字颜色
    //private int selectedTextColor;//按下时文字颜色

    public UniversalBtn(Context context) {
        super(context);
    }

    public UniversalBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeSet(context, attrs);
    }

    public UniversalBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(context, attrs);
    }

    private void setAttributeSet(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.universalBtn);
            int normalSolid = a.getColor(R.styleable.universalBtn_normalSolid, Color.TRANSPARENT);
            int pressedSolid = a.getColor(R.styleable.universalBtn_pressedSolid, Color.TRANSPARENT);
            int strokeColor = a.getColor(R.styleable.universalBtn_stroke, Color.TRANSPARENT);
            radius = a.getDimensionPixelSize(R.styleable.universalBtn_roundButtonRadius, 0);
            int leftTopRadius = a.getDimensionPixelSize(R.styleable.universalBtn_roundButtonLeftTopRadius, 0);
            int leftBottomRadius = a.getDimensionPixelSize(R.styleable.universalBtn_roundButtonLeftBottomRadius, 0);
            int rightTopRadius = a.getDimensionPixelSize(R.styleable.universalBtn_roundButtonRightTopRadius, 0);
            int rightBottomRadius = a.getDimensionPixelSize(R.styleable.universalBtn_roundButtonRightBottomRadius, 0);
            normalDrawable = a.getDrawable(R.styleable.universalBtn_normalDrawable);
            pressedDrawable = a.getDrawable(R.styleable.universalBtn_pressedDrawable);
            isSelected = a.getBoolean(R.styleable.universalBtn_isSelected, false);
            int normalTextColor = a.getColor(R.styleable.universalBtn_normalTextColor, 0);
            int selectedTextColor = a.getColor(R.styleable.universalBtn_selectedTextColor, 0);
            strokeWidth = a.getDimensionPixelSize(R.styleable.universalBtn_strokeWidth, 1);
            int normalStrokeColor = a.getColor(R.styleable.universalBtn_normalStroke, Color.TRANSPARENT);
            int pressedStokeColor = a.getColor(R.styleable.universalBtn_pressedStroke, Color.TRANSPARENT);

            a.recycle();
            selector = new StateListDrawable();
            //处理图片的逻辑
            if (normalSolid == Color.TRANSPARENT) {
                if (normalDrawable == null && pressedDrawable != null) {
                    normalDrawable = pressedDrawable;
                    setDrawableSelector();
                } else if (normalDrawable != null && pressedDrawable == null) {
                    pressedDrawable = changeBrightnessBitmap(((BitmapDrawable) normalDrawable).getBitmap());
                    setDrawableSelector();
                } else if (normalDrawable != null && pressedDrawable != null) {
                    setDrawableSelector();
                }
            } else if (normalSolid != Color.TRANSPARENT) {
                //处理颜色的逻辑
                GradientDrawable normalGD = new GradientDrawable();
                normalGD.setColor(normalSolid);
                if (radius != 0) {
                    normalGD.setCornerRadius(radius);
                } else if (leftTopRadius != 0 || leftBottomRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
                    normalGD.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
                }
                if (normalStrokeColor != Color.TRANSPARENT) {
                    normalGD.setStroke(strokeWidth, normalStrokeColor);
                } else {
                    normalGD.setStroke(strokeWidth, strokeColor);
                }
                //设置默认按下颜色背景变高亮
                if (pressedSolid == Color.TRANSPARENT) {
                    pressedSolid = brighter(normalSolid);
                }
                if (pressedSolid != Color.TRANSPARENT || pressedStokeColor != Color.TRANSPARENT) {
                    GradientDrawable pressedGD = new GradientDrawable();
                    pressedGD.setColor(pressedSolid);
                    if (radius != 0) {
                        pressedGD.setCornerRadius(radius);
                    } else if (leftTopRadius != 0 || leftBottomRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
                        pressedGD.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
                    }
                    if (pressedStokeColor != Color.TRANSPARENT) {
                        pressedGD.setStroke(strokeWidth, pressedStokeColor);
                    } else {
                        pressedGD.setStroke(strokeWidth, strokeColor);
                    }
                    if (isSelected) {
                        selector.addState(new int[]{android.R.attr.state_selected}, pressedGD);
                    } else {
                        selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
                    }
                }
                selector.addState(new int[]{}, normalGD);
                setBackgroundDrawable(selector);
            }
            /**
             * 设置文字颜色切换
             */
            if (normalTextColor != 0 && selectedTextColor != 0) {
                int[][] states = new int[3][1];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{android.R.attr.state_pressed};
                states[2] = new int[]{};
                ColorStateList textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, selectedTextColor, normalTextColor});
                setTextColor(textColorSelect);
            }

        }
    }

    /**
     * 设置图片背景正常和按下时的状态
     */
    private void setDrawableSelector() {
        if (isSelected) {
            selector.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
        } else {
            selector.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        }
        selector.addState(new int[]{}, normalDrawable);
        setBackgroundDrawable(selector);
    }
    /**
     * 传入改变亮度前的bitmap，返回改变亮度后的bitmap
     *
     * @param srcBitmap
     * @return
     */
    private Drawable changeBrightnessBitmap(Bitmap srcBitmap) {
        Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int brightness = 60 - 127;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});// 改变亮度
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个Bitmap
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
        return new BitmapDrawable(bmp);
    }

    /**
     * 设置按下高亮的颜色
     *
     * @param color
     * @return
     */
    private int brighter(int color) {
        int d = 20;
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        int incr = maxIncr(r, g, b, d);
        r += incr;
        g += incr;
        b += incr;
        if (incr < d) {
            r = 0xbf;
            g = 0xbf;
            b = 0xbf;
        }
        return Color.rgb(r, g, b);
    }

    private int maxIncr(int r, int g, int b, int i) {
        int mir = maxIncr(r, i);
        int mig = maxIncr(g, i);
        int mib = maxIncr(b, i);
        return Math.min(mir, Math.min(mig, mib));
    }

    private int maxIncr(int c, int i) {
        int nc = c + i;
        if (nc > 255)
            return 255 - c;
        return i;
    }
}
