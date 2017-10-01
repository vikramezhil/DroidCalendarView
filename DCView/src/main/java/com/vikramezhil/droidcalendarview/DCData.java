package com.vikramezhil.droidcalendarview;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Droid Calendar Data
 *
 * Created by Vikram Ezhil on 01/10/17.
 *
 * email: vikram.ezhil.1988@gmail.com
 */

class DCData
{
    String monthYearHeader;
    String monthYearHeaderFormat;
    String displayDateFormat;
    String clickedDateFormat;
    String datesFormat;
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> recalibrateDates = new ArrayList<>();
    Locale locale;

    /**
     * Droid Calendar Data Constructor
     *
     * @param monthYearHeader The month-year header
     *
     * @param monthYearHeaderFormat The month-year header format
     *
     * @param displayDateFormat The desired display date format
     *
     * @param clickedDateFormat The desired clicked date format
     *
     * @param datesFormat The dates list format
     *
     * @param dates The dates list
     *
     * @param recalibrateDates The recalibrate dates list
     *
     * @param locale The date locale
     */
    DCData(String monthYearHeader, String monthYearHeaderFormat, String displayDateFormat, String clickedDateFormat, String datesFormat, ArrayList<String> dates, ArrayList<String> recalibrateDates, Locale locale)
    {
        this.monthYearHeader = monthYearHeader;
        this.monthYearHeaderFormat = monthYearHeaderFormat;
        this.displayDateFormat = displayDateFormat;
        this.clickedDateFormat = clickedDateFormat;
        this.datesFormat = datesFormat;
        this.dates = dates;
        this.recalibrateDates = recalibrateDates;
        this.locale = locale;
    }
}
