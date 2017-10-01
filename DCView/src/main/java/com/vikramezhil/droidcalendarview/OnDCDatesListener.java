package com.vikramezhil.droidcalendarview;

/**
 * Droid Calendar Dates Listener
 *
 * Created by Vikram Ezhil on 01/10/17.
 *
 * email: vikram.ezhil.1988@gmail.com
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
