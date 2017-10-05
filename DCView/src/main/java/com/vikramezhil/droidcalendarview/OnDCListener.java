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
     * @param calendarPosition The position of the calendar
     *
     * @param calendarDatesWithPresent The on screen droid calendar dates (present month values)
     *
     * @param calendarDatesWithPastPresentFuture The on screen droid calendar dates (past month values, present month values, future month values)
     */
    void onDCScreenData(int calendarPosition, List<String> calendarDatesWithPresent, List<String> calendarDatesWithPastPresentFuture);

    /**
     * Sends an update when a date is clicked in droid calendar
     *
     * @param date The clicked date
     */
    void onDCDateClicked(String date);
}
