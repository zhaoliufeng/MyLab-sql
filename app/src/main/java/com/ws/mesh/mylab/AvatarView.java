package com.ws.mesh.mylab;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class AvatarView extends View {

    private final int DEFAULT_WIDTH = 300;
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Bitmap bitmap;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //强制要求宽高一致
    private int slideLength;
    private Camera camera = new Camera();

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        camera.rotateX(30);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            //默认半径 单位dp
            slideLength = DEFAULT_WIDTH;
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            slideLength = widthSize / 2;
        }

        setMeasuredDimension(slideLength, slideLength);
        bitmap = getAvatar(slideLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveArea = canvas.saveLayer(new RectF(0, 0, slideLength, slideLength), paint);
        canvas.drawCircle(slideLength / 2f, slideLength / 2f, slideLength / 2f, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, dp2px(0), dp2px(0), paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveArea);
    }

    //获取头像图片
    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
