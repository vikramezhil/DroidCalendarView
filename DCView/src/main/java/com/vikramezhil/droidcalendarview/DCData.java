package com.vikramezhil.droidcalendarview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Droid Calendar Data
 *
 * @author Vikram Ezhil
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
    Map<String, String> datesSubValues = new HashMap<>();
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

    /**
     * Sets the dates sub values map
     *
     * @param datesSubValues The dates sub value list map
     */
    void setDatesSubValues(Map<String, String> datesSubValues)
    {
        this.datesSubValues = datesSubValues;
    }

    /**
     * Sets the date sub value
     *
     * @param dateKey The date key
     *
     * @param subValue The sub value
     */
    void setDateSubValue(String dateKey, String subValue)
    {
        datesSubValues.put(dateKey, subValue);
    }

    /**
     * Removes the date sub value based on key
     *
     * @param dateKey The date key
     */
    void removeDateSubValue(String dateKey)
    {
        if(datesSubValues.containsKey(dateKey))
        {
            datesSubValues.remove(dateKey);
        }
    }

    /**
     * Clear dates sub values
     */
    void clearDatesSubValues()
    {
        datesSubValues.clear();
    }
}
