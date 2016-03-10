package com.elasticbeanstalk.laciecool.first;

/**
 * Created by lacie on 1/16/16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.FileOutputStream;
import java.io.IOException;

public class SignatureView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    Context context;
    private float mX, mY;
    private static final float TOLERANCE = 5;


    public SignatureView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        this.setDrawingCacheEnabled(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(mBitmap, 0, 0, paint);
        canvas.drawPath(path, paint);
    }

    private void startTouch(float x, float y) {

        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {
        path.lineTo(x, y);
        mX = x;
        mY = y;
//        float dx = Math.abs(x - mX);
//        float dy = Math.abs(y - mY);
//        if (dx >= TOLERANCE || dy >= TOLERANCE) {
//            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
//            mX = x;
//            mY = y;
//        }

    }

    private void upTouch() {
        path.lineTo(mX, mY);
    }

    public void clearCanvas() {
        path.reset();
        invalidate();
    }

    public void saveImage(String pid) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+pid+".png");
        } catch(IOException e) {
            e.printStackTrace();
        }

        this.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 95, fos);
    }

    public boolean isCanvasEmpty() {
        Bitmap curr = this.getDrawingCache();
        Bitmap emptyBm = Bitmap.createBitmap(curr.getWidth(), curr.getHeight(), curr.getConfig());

        return curr.sameAs(emptyBm);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }

        return true;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float eventX = event.getX();
//        float eventY = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                path.moveTo(eventX, eventY);
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                path.lineTo(eventX, eventY);
//                break;
//            case MotionEvent.ACTION_UP:
//                // nothing to do
//                break;
//            default:
//                return false;
//        }
//
//        // Schedules a repaint.
//        invalidate();
//        return true;
//    }
}