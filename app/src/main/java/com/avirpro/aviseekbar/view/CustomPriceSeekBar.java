package com.avirpro.aviseekbar.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.avirpro.aviseekbar.R;
import com.avirpro.aviseekbar.adapter.PhasedAdapter;
import com.avirpro.aviseekbar.listener.PhasedListener;
import com.avirpro.aviseekbar.listener.PhasedInteractionListener;

public class CustomPriceSeekBar extends View {

    protected static final int[] STATE_SELECTED = new int[] { android.R.attr.state_selected };
    protected int[] mState = STATE_SELECTED;

    protected boolean mFirstDraw = true;
    protected boolean mUpdateFromPosition = false;
    protected boolean mDrawOnOff = true;
    protected boolean mFixPoint = true;

    protected Drawable mBackgroundDrawable;
    protected RectF mBackgroundPaddingRect;

    protected int mCurrentX, mCurrentY;
    protected int mPivotX, mPivotY;
    protected int mItemHalfWidth, mItemHalfHeight;
    protected int[][] mAnchors;
    protected int mCurrentItem;

    protected int mCurrentMinusXL = 50;
    protected int mCurrentMinusL = 40;
    protected int mCurrentYPlus = 20;
    protected int mCurrentYLargePlus = 120;

    protected PhasedAdapter mAdapter;
    protected PhasedListener mListener;
    protected PhasedInteractionListener mInteractionListener;

    private Paint paint = new Paint();
    private Paint paintSelected = new Paint();
    private Paint paintCarModel = new Paint();


    public CustomPriceSeekBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomPriceSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomPriceSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    /*Method for init the seek bar*/
    protected void init(AttributeSet attrs, int defStyleAttr) {

        //create a new Refc object for draw the background
        mBackgroundPaddingRect = new RectF();
        if (attrs != null) {

            //Make the horizontal scroll view
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PhasedSeekBar, defStyleAttr, 0);

            //grey background of the seek bar
            mBackgroundPaddingRect.left = a.getDimension(R.styleable.PhasedSeekBar_phased_base_margin_left, 0.0f);
            mBackgroundPaddingRect.top = a.getDimension(R.styleable.PhasedSeekBar_phased_base_margin_top, 0.0f);
            mBackgroundPaddingRect.right = a.getDimension(R.styleable.PhasedSeekBar_phased_base_margin_right, 0.0f);
            mBackgroundPaddingRect.bottom = a.getDimension(R.styleable.PhasedSeekBar_phased_base_margin_bottom, 0.0f);

            //item it self of the seek bar that get scroll
            mItemHalfWidth = (int) (a.getDimension(R.styleable.PhasedSeekBar_phased_item_width, 0.0f) / 2.0f);
            mItemHalfHeight = (int) (a.getDimension(R.styleable.PhasedSeekBar_phased_item_width, 0.0f) / 2.0f);

            //drawble color of the seek bar.
            mBackgroundDrawable = a.getDrawable(R.styleable.PhasedSeekBar_phased_base_background);

            //very important to call recycle for memory management
            a.recycle();
        }
    }

    //create this method for a reason I want to pass the value via the xml, and not hard code them in the class.
    protected void configure() {
        Rect rect = new Rect((int) mBackgroundPaddingRect.left,
                (int) mBackgroundPaddingRect.top,
                (int) (getWidth() - mBackgroundPaddingRect.right),
                (int) (getHeight() - mBackgroundPaddingRect.bottom));

        if (mBackgroundDrawable != null) {
            mBackgroundDrawable.setBounds(rect);
        }

        mCurrentX = mPivotX = getWidth() / 2;
        mCurrentY = mPivotY = getHeight() / 2;

        if(mAdapter == null){
            return;
        }

        //count of the number element in the seek bar
        int count = getCount();

        //the width of the seek bar will be based on the item number in the seek bar
        int widthBase = rect.width() / count;
        int widthHalf = widthBase / 2;

         mAnchors = new int[count][2];

        for (int i = 0, j = 1; i < count; i++, j++) {
            mAnchors[i][0] =widthBase * j - widthHalf + rect.left;
            mAnchors[i][1] =mPivotY;
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setFirstDraw(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFirstDraw)
            configure();

        //car prices text
        Typeface carPricedF = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Light.otf");
        paint.setColor(Color.GRAY);
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        paint.setTypeface(carPricedF);

        //car prices selected text
        Typeface carSelectedF = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Medium.otf");
        paintSelected.setColor(Color.WHITE);
        paintSelected.setTextSize(60);
        paintSelected.setAntiAlias(true);
        paintSelected.setTypeface(carSelectedF);

        //car types
        Typeface carTypeF = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Heavy.otf");
        paintCarModel.setColor(Color.BLACK);
        paintCarModel.setTextSize(40);
        paintCarModel.setAntiAlias(true);
        paintCarModel.setTypeface(carTypeF);


        if (mBackgroundDrawable != null){
            mBackgroundDrawable.draw(canvas);
        }

        if (isInEditMode()) return;

        Drawable itemOn;
        StateListDrawable stateListDrawable;

        if(mAdapter == null){
            return;
        }
        int count = getCount();

        if (!mUpdateFromPosition) {
            int distance;
            int minIndex = 0;
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < count; i++) {
                distance = Math.abs(mAnchors[i][0] - mCurrentX);
                if (minDistance > distance) {
                    minIndex = i;
                    minDistance = distance;
                }
            }

            setCurrentItem(minIndex);

            stateListDrawable = mAdapter.getItem(minIndex);

        } else {
            mUpdateFromPosition = false;
            mCurrentX = mAnchors[mCurrentItem][0];
            mCurrentY = mAnchors[mCurrentItem][1];

            stateListDrawable = mAdapter.getItem(mCurrentItem);
        }

        stateListDrawable.setState(mState);
        itemOn = stateListDrawable.getCurrent();

        for(int i = 0; i<count; i++){
            canvas.drawText(mAdapter.getPrice(i), mAnchors[i][0] - mCurrentMinusL, mPivotY+mCurrentYPlus, paint);
            canvas.drawText(mAdapter.getCarType(i), mAnchors[i][0]-mCurrentMinusXL, mPivotY-mCurrentYLargePlus, paintCarModel);
        }

        itemOn.setBounds(
                mCurrentX - mItemHalfWidth,
                mCurrentY - mItemHalfHeight,
                mCurrentX + mItemHalfWidth,
                mCurrentY + mItemHalfHeight);
        itemOn.draw(canvas);


       for(int i = 0; i<count; i++){
            if(mAnchors[i][0] == mCurrentX){
                canvas.drawText(mAdapter.getPrice(i), mCurrentX -mCurrentMinusXL, mCurrentY+mCurrentYPlus, paintSelected);
            }

        }

        setFirstDraw(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = getNormalizedX(event);
        mCurrentY = mPivotY;
        int action = event.getAction();
        mUpdateFromPosition = mFixPoint && action == MotionEvent.ACTION_UP;
        mState = STATE_SELECTED;
        invalidate();

        if (mInteractionListener != null) {
            mInteractionListener.onInteracted(mCurrentX, mCurrentY, mCurrentItem, event);
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }

    protected int getNormalizedX(MotionEvent event) {
        return Math.min(Math.max((int) event.getX(), mItemHalfWidth), getWidth() - mItemHalfWidth);
    }


    protected int getCount() {
        return isInEditMode() ? 3 : mAdapter.getCount();
    }

    public void setAdapter(PhasedAdapter adapter) {
        mAdapter = adapter;
    }

    public void setFirstDraw(boolean firstDraw) {
        mFirstDraw = firstDraw;
    }

    public void setListener(PhasedListener listener) {
        mListener = listener;
    }

    public void setInteractionListener(PhasedInteractionListener interactionListener) {
        mInteractionListener = interactionListener;
    }

    public void setPosition(int position) {
        position = position < 0 ? 0 : position;
        position = position >= mAdapter.getCount() ? mAdapter.getCount() - 1 : position;
        mCurrentItem = position;
        mUpdateFromPosition = true;
        invalidate();
    }


    public boolean isDrawOnOff() {
        return mDrawOnOff;
    }

    public void setDrawOnOff(boolean drawOnOff) {
        mDrawOnOff = drawOnOff;
    }

    public boolean isFixPoint() {
        return mFixPoint;
    }

    public void setFixPoint(boolean fixPoint) {
        mFixPoint = fixPoint;
    }

    public int getCurrentX() {
        return mCurrentX;
    }

    public int getCurrentY() {
        return mCurrentY;
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    protected void setCurrentItem(int currentItem) {
        if (mCurrentItem != currentItem && mListener != null) {
            mListener.onPositionSelected(currentItem);
        }
        mCurrentItem = currentItem;
    }
}
