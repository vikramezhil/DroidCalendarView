package com.vikramezhil.droidcalendarview;

import java.util.List;

/**
 * Droid Calendar Listener
 *
 * @author Vikram Ezhil
 */

public interface OnDCListener
{
    /**
     * Sends an update with the on screen droid calendar dates
     *
     * @param calendarDates The on screen droid calendar dates
     */
    void onDCScreenData(List<String> calendarDates);

    /**
     * Sends an update when a date is clicked in droid calendar
     *
     * @param date The clicked date
     */
    void onDCDateClicked(String date);
}
