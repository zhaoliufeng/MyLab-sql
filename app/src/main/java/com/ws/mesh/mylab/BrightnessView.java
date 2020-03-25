package com.ws.mesh.mylab;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class BrightnessView extends View {


    private int radius;
    //射线进度颜色
    private int rayProcessColor;
    //射线背景颜色
    private int rayBgColor;
    //射线长度
    private int rayLength;
    //射线宽度
    private int rayWidth;
    private Paint linePaint;
    private Bitmap bitmap;
    //min 0 max 100
    private int process;

    public BrightnessView(Context context) {
        super(context);
        rayLength = (int) dp2px(15);
        rayWidth = (int) dp2px(10);
        rayProcessColor = Color.RED;
        rayBgColor = Color.CYAN;
        process = 50;
        initPaint();
    }

    public BrightnessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        process = 50;
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BrightnessView, 0, 0);
        rayLength = (int) ta.getDimension(R.styleable.BrightnessView_ray_length, dp2px(15));
        rayWidth = (int) ta.getDimension(R.styleable.BrightnessView_ray_width, dp2px(10));
        rayProcessColor = ta.getColor(R.styleable.BrightnessView_ray_process_color, Color.RED);
        rayBgColor = ta.getColor(R.styleable.BrightnessView_ray_bg_color, Color.CYAN);
        process = ta.getInteger(R.styleable.BrightnessView_process, 50);
        Drawable drawable = ta.getDrawable(R.styleable.BrightnessView_src);
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        initPaint();
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(rayWidth);
        linePaint.setColor(rayProcessColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            //默认半径 单位dp
            radius = (int) dp2px(300 / 2f);
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            radius = widthSize / 2;
        }

        setMeasuredDimension(radius * 2, radius * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRays(canvas);

        if (bitmap != null) {
            Rect srcRect = getSrcRect();
            Rect destRect = getDestRect(); // 获取调整后的bitmap的显示位置
            canvas.drawBitmap(bitmap, srcRect, destRect, linePaint);
        }
    }

    private void drawRays(Canvas canvas) {
        canvas.save();
        canvas.rotate(60, radius, radius);
        for (int i = 1; i <= 25; i++) {
            drawRay(canvas, process < i * 4 ? rayBgColor : rayProcessColor);

        }
        canvas.restore();
    }

    //绘制射线
    private void drawRay(Canvas canvas, int color) {
        linePaint.setColor(color);
        canvas.drawLine(radius, radius + (radius - rayLength), radius, 2 * radius, linePaint);
        canvas.rotate(10, radius, radius);
    }

    private Rect getSrcRect() {
        return new Rect(0, 0, bitmap.getHeight(), bitmap.getWidth());
    }

    private Rect getDestRect() {
        return new Rect((int) 2 * rayLength, (int) 2 * rayLength,
                (int) (radius * 2 - 2 * rayLength), (int) (radius * 2 - 2 * rayLength));
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, Resources.getSystem().getDisplayMetrics());
    }
}
