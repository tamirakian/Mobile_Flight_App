package com.example.flightmobileapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.flightmobileapp.view_model.ViewModel;

public class Joystick extends View {
    private Paint mPaint = new Paint();
    private float aileron = 400;
    private float elevator = 400;

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawCircle(aileron, elevator, 120, mPaint);
    }

    public void onChange(float aileron, float elevator){
        this.aileron = aileron;
        this.elevator = elevator;
        invalidate();
    }
}
