package com.vikramezhil.droidcalendarview;

import android.graphics.Color;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

/**
 * Droid Calendar Properties
 *
 * @author Vikram Ezhil
 */

class DCProperties
{
    List<String> clickableDates = new ArrayList<>();

    String clickedDate;

    float daysHeaderTextSize = 18f;

    float daysTextSize = 18f;

    float daysSubTextSize = 15f;

    int daysHeaderTextStyle = Typeface.NORMAL;

    int daysTextStyle = Typeface.NORMAL;

    int daysSubTextStyle = Typeface.NORMAL;

    int daysHeadersLayoutBGColor = Color.parseColor("#F5F5F5");

    int daysHeadersTextColor = Color.parseColor("#999898");

    int daysLayoutBGColor = Color.WHITE;

    int daysTextColor = Color.parseColor("#999898");

    int daysSubTextColor = Color.parseColor("#999898");

    int clickableDaysBGColor = Color.WHITE;

    int clickableDaysTextColor = Color.parseColor("#999898");

    int clickedDayBGColor = Color.parseColor("#00ADEF");

    int clickedDayTextColor = Color.WHITE;

    int clickedDaySubTextColor = Color.WHITE;

    int colSeparatorColor = Color.parseColor("#F5F5F5");

    int rowSeparatorColor = Color.parseColor("#F5F5F5");

    boolean showDaysHeaderColSeparator = false;

    boolean showDaysHeaderRowSeparator = false;

    boolean showColSeparator = false;

    boolean showRowSeparator = true;

    boolean showPastAndFutureMonthDates = false;

    boolean showDaySubValue = false;

    boolean allDatesClickable = true;

    boolean enableOnlyFutureDates = false;
}
