package com.jiuwang.buyer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.jiuwang.buyer.R;


public class LineEdit extends EditText {

    private Paint mPaint;
    private int mLineColor;

    public LineEdit(Context context) {
        super(context);
    }

    public LineEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    public LineEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs) {
        TypedArray attrArrays = context.obtainStyledAttributes(attrs, R.styleable.LineEditeText);

        mPaint = new Paint();
        int lenght = attrArrays.getIndexCount();
        for (int i = 0; i < lenght; i++) {
            int index = attrArrays.getIndex(i);
            switch (index) {
                case R.styleable.LineEditeText_lineColorEt:
                    mLineColor = attrArrays.getColor(index, 0xFFF);
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        canvas.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1, mPaint);
    }
}