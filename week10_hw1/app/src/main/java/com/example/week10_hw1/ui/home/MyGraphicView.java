package com.example.week10_hw1.ui.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyGraphicView extends View {
    public enum Shape {
        LINE, CIRCLE, RECTANGLE
    }

    private int startX = -1, startY = -1, stopX = -1, stopY = -1;
    private Shape curShape = Shape.LINE;
    private Paint paint;
    private List<DrawableShape> shapes = new ArrayList<>(); // 도형 리스트 추가

    public MyGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                addShape(curShape, startX, startY, stopX, stopY); // 도형 추가
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (DrawableShape shape : shapes) {
            shape.draw(canvas, paint);
        }
    }

    public void setShape(Shape shape) {
        this.curShape = shape;
        shapes.clear(); // 도형 리스트 초기화
        invalidate(); // 화면 갱신
    }

    private void addShape(Shape shapeType, int startX, int startY, int stopX, int stopY) {
        DrawableShape shape;
        switch (shapeType) {
            case LINE:
                shape = new Line(startX, startY, stopX, stopY);
                break;
            case CIRCLE:
                int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2));
                shape = new Circle(startX, startY, radius);
                break;
            case RECTANGLE:
                shape = new Rectangle(startX, startY, stopX, stopY);
                break;
            default:
                return;
        }
        shapes.add(shape);
    }

    private interface DrawableShape {
        void draw(Canvas canvas, Paint paint);
    }

    private static class Line implements DrawableShape {
        private final int startX, startY, stopX, stopY;

        public Line(int startX, int startY, int stopX, int stopY) {
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

    private static class Circle implements DrawableShape {
        private final int cx, cy, radius;

        public Circle(int cx, int cy, int radius) {
            this.cx = cx;
            this.cy = cy;
            this.radius = radius;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    private static class Rectangle implements DrawableShape {
        private final Rect rect;

        public Rectangle(int left, int top, int right, int bottom) {
            this.rect = new Rect(left, top, right, bottom);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            canvas.drawRect(rect, paint);
        }
    }
}