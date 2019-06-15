package com.example.pinpassword.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.pinpassword.Model.Point;

import java.util.ArrayList;
import java.util.Date;


public class CanvasView extends View {

    private ArrayList<Point> pointsArray = new ArrayList<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    private static final String TAG = "MyTagCanvasView";

    public ArrayList<Point> getPointsArray() {
        Log.d( TAG, "getPointsArray called");
        return pointsArray;
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        Log.d( TAG, "CanvasView constructor");
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        Log.d( TAG, " onSizeChanged called");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
        //Log.d( TAG, " onDraw called");
    }

    private void startTouch(float x, float y, float fingerSize){
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
        //Log.d( TAG, " startTouch x: " + x +  " Y: " + y  + " Finger: " + fingerSize);
        Point point = new Point();
        point.setxCoordinate(x);
        point.setyCoordinate(y);
        point.setFingerSize(fingerSize);
        Date date= new Date();
        long time = date.getTime();
        point.setTimeStamp(time);
        pointsArray.add(point);
    }
    private void moveTouch(float x, float y, float fingerSize){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        //Log.d( TAG, " moveTouch x: " + x +  " Y: " + y + " Finger: " + fingerSize);
        if( dx >= TOLERANCE || dy >= TOLERANCE){
            mPath.quadTo(mX,mY,(x + mX)/2 , (y + mY)/2);
            mX = x;
            mY = y;
        }

        Point point = new Point();
        point.setxCoordinate(x);
        point.setyCoordinate(y);
        point.setFingerSize(fingerSize);
        Date date= new Date();
        long time = date.getTime();
        point.setTimeStamp(time);
        pointsArray.add(point);
    }
    public void clearCanvas(){
        pointsArray.clear();
        mPath.reset();
        invalidate();
        Log.d( TAG, " clearCanvas called");
    }

    private void upTouch(){
        mPath.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float pressure = event.getPressure();
        float fingerSize = event.getSize();
        //Log.d( TAG, " onTouchEvent X [" + x +  "] Y [" + y  + "] Finger: [" + fingerSize
        //        + "] Count " + pointsArray.size());
        System.out.println(pressure);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y,fingerSize);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y,fingerSize);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }


    public int getArraySize(){
        return pointsArray.size();
    }
}