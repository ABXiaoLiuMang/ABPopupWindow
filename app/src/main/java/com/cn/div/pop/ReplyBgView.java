package com.cn.div.pop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * create by Dale
 * create on 2019/9/21
 * description:
 */
public class ReplyBgView extends View {
    private final int LEFT = 0;
    private final int RIGHT = 1;
    private final int TOP = 2;
    private final int BOTTOM = 3;

    private int mWidth;
    private int mHeight;

    private Rect mRect;
    private RectF mRectF;
    private Paint mPaint;
    private Path mPathTrg; // Triangular path
    private int mGravity;
    private int mColorBg;
    private float mRectRadius;
    private float mOffset; // Triangle left offset
    private float mTrgHalfWidth; // Triangle bottom length/2
    private float mTrgHeight; // Triangle height

    public ReplyBgView(Context context) {
        this(context, null);
    }

    public ReplyBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReplyBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReplyBgView);
        mGravity = typedArray.getInteger(R.styleable.ReplyBgView_Replygravity, TOP);
        mColorBg = typedArray.getColor(R.styleable.ReplyBgView_replybv_color, ContextCompat.getColor(context, android.R.color.white));
        mRectRadius = typedArray.getDimension(R.styleable.ReplyBgView_radius, SizeUtils.dp2px(3));
        mOffset = typedArray.getDimension(R.styleable.ReplyBgView_offset, SizeUtils.dp2px(6.5f));
        mTrgHalfWidth = typedArray.getDimension(R.styleable.ReplyBgView_trgWidth, SizeUtils.dp2px(6)) / 2;
        mTrgHeight = typedArray.getDimension(R.styleable.ReplyBgView_trgHeight, SizeUtils.dp2px(5));
        typedArray.recycle();
    }

    private void init(@SuppressWarnings("unused") Context context) {
        mPathTrg = new Path();
        mRect = new Rect();
        mRectF = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColorBg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mGravity) {
            case LEFT:
                mPathTrg.moveTo(mTrgHeight, mOffset > 0 ? mOffset : mHeight + mOffset - mTrgHalfWidth * 2);
                mPathTrg.lineTo(0, mOffset > 0 ? mOffset + mTrgHalfWidth : mHeight + mOffset - mTrgHalfWidth);
                mPathTrg.lineTo(mTrgHeight, mOffset > 0 ? mOffset + mTrgHalfWidth * 2 : mHeight + mOffset);
                mPathTrg.close();
                canvas.drawPath(mPathTrg, mPaint);

                mRect.set((int) mTrgHeight, 0, mWidth, mHeight);
                mRectF.set(mRect);
                canvas.drawRoundRect(mRectF, mRectRadius, mRectRadius, mPaint);
                break;

            case RIGHT:
                mPathTrg.moveTo(mWidth - mTrgHeight, mOffset > 0 ? mOffset : mHeight + mOffset - mTrgHalfWidth * 2);
                mPathTrg.lineTo(mWidth, mOffset > 0 ? mOffset + mTrgHalfWidth : mHeight + mOffset - mTrgHalfWidth);
                mPathTrg.lineTo(mWidth - mTrgHeight, mOffset > 0 ? mOffset + mTrgHalfWidth * 2 : mHeight + mOffset);
                mPathTrg.close();
                canvas.drawPath(mPathTrg, mPaint);

                mRect.set(0, 0, (int) (mWidth - mTrgHeight), mHeight);
                mRectF.set(mRect);
                canvas.drawRoundRect(mRectF, mRectRadius, mRectRadius, mPaint);
                break;

            case TOP:
                mPathTrg.moveTo(mOffset > 0 ? mOffset : mWidth + mOffset - mTrgHalfWidth * 2, mTrgHeight);
                mPathTrg.lineTo(mOffset > 0 ? mOffset + mTrgHalfWidth : mWidth + mOffset - mTrgHalfWidth, 0);
                mPathTrg.lineTo(mOffset > 0 ? mOffset + mTrgHalfWidth * 2 : mWidth + mOffset, mTrgHeight);
                mPathTrg.close();
                canvas.drawPath(mPathTrg, mPaint);

                mRect.set(0, (int) mTrgHeight, mWidth, mHeight);
                mRectF.set(mRect);
                canvas.drawRoundRect(mRectF, mRectRadius, mRectRadius, mPaint);
                break;

            case BOTTOM:
                mPathTrg.moveTo(mOffset > 0 ? mOffset : mWidth + mOffset - mTrgHalfWidth * 2, mHeight - mTrgHeight);
                mPathTrg.lineTo(mOffset > 0 ? mOffset + mTrgHalfWidth : mWidth + mOffset - mTrgHalfWidth, mHeight);
                mPathTrg.lineTo(mOffset > 0 ? mOffset + mTrgHalfWidth * 2 : mWidth + mOffset, mHeight - mTrgHeight);
                mPathTrg.close();
                canvas.drawPath(mPathTrg, mPaint);

                mRect.set(0, 0, mWidth, (int) (mHeight - mTrgHeight));
                mRectF.set(mRect);
                canvas.drawRoundRect(mRectF, mRectRadius, mRectRadius, mPaint);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }
}

