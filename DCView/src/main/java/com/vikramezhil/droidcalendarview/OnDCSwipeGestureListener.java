package com.vikramezhil.droidcalendarview;

/**
 * Droid Calendar Swipe Gesture Listener
 *
 * @author Vikram Ezhil
 */

interface OnDCSwipeGestureListener
{
    /**
     * Sends an update that left to right swipe was detected
     */
    void onDCSwipedLeftToRight();

    /**
     * Sends an update that right to left swipe was detected
     */
    void onDCSwipedRightToLeft();

    /**
     * Sends an update that top to bottom swipe was detected
     */
    void onDCSwipedTopToBottom();

    /**
     * Sends an update that bottom to top swipe was detected
     */
    void onDCSwipedBottomToTop();
}
