package com.vikramezhil.droidcalendarview;

/**
 * Droid Calendar Dates Listener
 *
 * @author Vikram Ezhil
 */

interface OnDCDatesListener
{
    /**
     * Sends an update on the clicked date
     *
     * @param date The date which was clicked
     */
    void onDateClicked(String date);

    /**
     * Sends an update if there was an exception
     *
     * @param e The exception
     */
    void onDateError(Exception e);
}
