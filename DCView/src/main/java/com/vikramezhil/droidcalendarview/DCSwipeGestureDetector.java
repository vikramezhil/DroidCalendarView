package com.vikramezhil.droidcalendarview;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * Droid Calendar Swipe Gesture Detector
 *
 * @author Vikram Ezhil
 */

class DCSwipeGestureDetector extends SimpleOnGestureListener
{
    private OnDCSwipeGestureListener onDCSwipeGestureListener;

    private MotionEvent mLastOnDownEvent = null;

    /**
     * DCSwipeGestureDetector Constructor
     *
     * @param onDCSwipeGestureListener The implemented class instance to initialize the interface
     */
    DCSwipeGestureDetector(OnDCSwipeGestureListener onDCSwipeGestureListener)
    {
        this.onDCSwipeGestureListener = onDCSwipeGestureListener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        if (e1 == null) e1 = mLastOnDownEvent;

        if(e1 == null || e2 == null) return false;

        try
        {
            if(Math.abs(velocityX) > Math.abs(velocityY))
            {
                if(e1.getX() < e2.getX())
                {
                    onDCSwipeGestureListener.onDCSwipedLeftToRight();
                }
                else if (e1.getX() > e2.getX())
                {
                    onDCSwipeGestureListener.onDCSwipedRightToLeft();
                }
            }
            else
            {
                if (e1.getY() < e2.getY())
                {
                    onDCSwipeGestureListener.onDCSwipedTopToBottom();
                }
                else if (e1.getY() > e2.getY())
                {
                    onDCSwipeGestureListener.onDCSwipedBottomToTop();
                }
            }

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        mLastOnDownEvent = e;

        return true;
    }
}
