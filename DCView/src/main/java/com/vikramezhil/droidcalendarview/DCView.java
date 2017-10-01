package com.vikramezhil.droidcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Droid Calendar View
 *
 * Created by Vikram Ezhil on 01/10/17.
 *
 * email: vikram.ezhil.1988@gmail.com
 */

public class DCView extends LinearLayout implements OnDCDatesListener
{
    private final float maxHeaderTextSize = 22f;
    private final float maxDaysTextSize = 22f;
    private final float maxPrevNextButtonSize = 30f;
    private LinearLayout dcHeaderLayout;
    private TextView previous;
    private TextView dcHeader;
    private TextView next;
    private DCGridView dcGV;
    private DCGridViewAdapter dcGridViewAdapter;
    private List<DCData> dcDataList = new ArrayList<>();
    private int dcPosition = 0;
    private OnDCListener onDCListener;

    // MARK: LinearLayout Constructor Methods

    public DCView(Context context)
    {
        super(context);

        init(context);
    }

    public DCView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init(context);
    }

    public DCView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    // MARK: Droid Calendar Listener

    /**
     * Sets the Droid Calendar Listener
     *
     * @param onDCListener The implement class instance to initialize the interface
     */
    public void setOnDCListener(OnDCListener onDCListener)
    {
        this.onDCListener = onDCListener;

        if(onDCListener != null && dcPosition >= 0 && dcPosition < dcDataList.size())
        {
            // Sending an update with the on screen droid calendar data
            onDCListener.onDCScreenData(dcDataList.get(dcPosition).dates);
        }
    }

    // MARK: Droid Calendar Private Methods

    private void init(Context context)
    {
        View rootView = inflate(context, R.layout.dc, this);

        dcHeaderLayout = rootView.findViewById(R.id.dcHeaderLayout);
        dcGV = rootView.findViewById(R.id.dcGV);

        dcGridViewAdapter = new DCGridViewAdapter(context, this);
        dcGV.setAdapter(dcGridViewAdapter);

        previous = rootView.findViewById(R.id.previous);
        previous.setTypeface(DCUtil.getFATypeface(context));
        previous.setOnClickListener(new OnClickListener() {
            public void onClick(View view)
            {
                // Moving the calendar to previous month
                moveCalendar(dcPosition - 1);
            }
        });

        dcHeader = rootView.findViewById(R.id.dcHeader);

        next = rootView.findViewById(R.id.next);
        next.setTypeface(DCUtil.getFATypeface(context));
        next.setOnClickListener(new OnClickListener() {

            public void onClick(View view)
            {
                // Moving the calendar to next month
                moveCalendar(dcPosition + 1);
            }
        });

        // Setting default droid calendar data initially
        setDCData(12, DCFormats.DC_MY_FORMAT, DCFormats.D_FORMAT, DCFormats.DC_DMY_FORMAT, true, Locale.US);
        setDCClickedDate(DCUtil.getCurrentDate(DCFormats.DC_DMY_FORMAT, Locale.US), true);
    }

    /**
     * Moves the Droid Calendar to a particular month
     *
     * @param position The desired month position in Droid Calendar
     */
    private void moveCalendar(int position)
    {
        if(position >= 0 && position < dcDataList.size())
        {
            // Setting the month position
            dcPosition = position;

            // Showing (or) Hiding Next & Previous buttons
            showHidePreviousNext();

            // Setting the month & year header
            dcHeader.setText(dcDataList.get(dcPosition).monthYearHeader);

            // Setting the calendar data in the Droid Calendar adapter
            dcGridViewAdapter.setDcData(dcDataList.get(dcPosition));

            if(onDCListener != null)
            {
                // Sending an update with the on screen droid calendar data
                onDCListener.onDCScreenData(dcDataList.get(dcPosition).dates);
            }
        }
    }

    /**
     * Shows (or) Hides Previous & Next buttons
     */
    private void showHidePreviousNext()
    {
        int nextDecrementPosition = dcPosition - 1;
        previous.setVisibility(nextDecrementPosition < 0 ? View.INVISIBLE : View.VISIBLE);

        int nextIncrementPosition = dcPosition + 1;
        this.next.setVisibility(nextIncrementPosition > dcDataList.size() - 1 ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * Gets the Droid Calendar data list
     *
     * @param startMonthYear The start month & year
     *
     * @param endMonthYear The end month & year
     *
     * @param passedMonthYearRangeFormat The passed month & year format
     *
     * @param monthYearHeaderFormat The desired month & year header format
     *
     * @param displayDateFormat The desired display date format
     *
     * @param clickedDateFormat The desired clicked date format
     *
     * @param locale The date locale
     *
     * @param manualMonthRange True - Manual month range, False - if otherwise
     *
     * @return The Droid Calendar Data
     */
    private List<DCData> getCalendarData(String startMonthYear, String endMonthYear, String passedMonthYearRangeFormat, String monthYearHeaderFormat, String displayDateFormat, String clickedDateFormat, Locale locale, boolean manualMonthRange)
    {
        List<DCData> dcDataList = new ArrayList<>();

        try
        {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(passedMonthYearRangeFormat).withLocale(locale);
            DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(DCFormats.DC_DMY_FORMAT).withLocale(locale);

            DateTime start = dateTimeFormatter.parseDateTime(startMonthYear);
            DateTime end = dateTimeFormatter.parseDateTime(endMonthYear);

            for(DateTime month = start; manualMonthRange ? month.isBefore(end) || month.equals(end) : month.isBefore(end); month = month.plusMonths(1))
            {
                String monthYearHeader = DCUtil.getDateInFormat(dateTimeFormatter.print(month), passedMonthYearRangeFormat, monthYearHeaderFormat, locale, locale);
                DateTime startDate = month.withDayOfMonth(1);
                DateTime endDate = month.plusMonths(1).withDayOfMonth(1).minusDays(1);
                ArrayList<String> dates = new ArrayList<>();

                for(DateTime date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1))
                {
                    dates.add(dateFormatter.print(date));
                }

                dcDataList.add(new DCData(monthYearHeader, monthYearHeaderFormat, displayDateFormat, clickedDateFormat, DCFormats.DC_DMY_FORMAT, dates, recalibrateDates(dates, DCFormats.DC_DMY_FORMAT, locale), locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            dcDataList.clear();
        }

        return dcDataList;
    }

    /**
     * Recalibrate the dates required for Droid Calendar
     *
     * @param datesToBeRecalibrate The dates list to be recalibrate
     *
     * @param dateFormat The dates list format
     *
     * @param locale The date locale
     *
     * @return The recalibrate dates list
     */
    private ArrayList<String> recalibrateDates(ArrayList<String> datesToBeRecalibrate, String dateFormat, Locale locale)
    {
        ArrayList<String> recalibrateDatesList = new ArrayList<>();
        if(datesToBeRecalibrate.size() > 0)
        {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat).withLocale(locale);
            DateTime startDate = dateTimeFormatter.parseDateTime(datesToBeRecalibrate.get(0));
            DateTime endDate = dateTimeFormatter.parseDateTime(datesToBeRecalibrate.get(datesToBeRecalibrate.size() - 1));

            int startWeekPos = startDate.getDayOfWeek();
            int endWeekPos = endDate.getDayOfWeek();

            for(int catx=0;catx<startWeekPos - 1;catx++)
            {
                // Adding empty data before the start date if applicable
                recalibrateDatesList.add(null);
            }

            for(String date: datesToBeRecalibrate)
            {
                // Adding the dates
                recalibrateDatesList.add(date);
            }

            for(int caty=endWeekPos;caty<DCFormats.DAYS_IN_A_WEEK.length;caty++)
            {
                // Adding empty data after the end date if applicable
                recalibrateDatesList.add(null);
            }
        }

        return recalibrateDatesList;
    }

    // MARK: Droid Calendar Public Methods

    /**
     * Sets the Droid Calendar Data
     *
     * @param monthRange The month range
     *
     * @param monthYearHeaderFormat The desired month & year header format
     *
     * @param displayDateFormat The desired display date format
     *
     * @param clickedDateFormat The desired clicked date format
     *
     * @param future True - current & future months will be retrieved, False - current and previous months will be retrieved
     *
     * @param locale The date locale
     */
    public void setDCData(int monthRange, String monthYearHeaderFormat, String displayDateFormat, String clickedDateFormat, boolean future, Locale locale)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DCFormats.DC_MY_FORMAT).withLocale(locale);
        DateTime dateTime;

        String startMonthYear;
        String endMonthYear;

        if(future)
        {
            startMonthYear = DCUtil.getCurrentDate(DCFormats.DC_MY_FORMAT, locale);
            dateTime = dateTimeFormatter.parseDateTime(startMonthYear).plusMonths(monthRange);
            endMonthYear = dateTimeFormatter.print(dateTime);
        }
        else
        {
            endMonthYear = DCUtil.getCurrentDate(DCFormats.DC_MY_FORMAT, locale);
            dateTime = dateTimeFormatter.parseDateTime(endMonthYear).minusMonths(monthRange);
            startMonthYear = dateTimeFormatter.print(dateTime);
        }

        dcDataList = getCalendarData(startMonthYear, endMonthYear, DCFormats.DC_MY_FORMAT, monthYearHeaderFormat, displayDateFormat, clickedDateFormat, locale, false);
        if(dcDataList.size() > 0)
        {
            // Initially moving the calendar to the first position
            moveCalendar(0);
        }
    }

    /**
     * Sets the Droid Calendar Data
     *
     * @param startMonthYear The start range month & year
     *
     * @param endMonthYear The end range month & year
     *
     * @param passedMonthYearRangeFormat The passed month & year range format
     *
     * @param monthYearHeaderFormat The desired month & year header format
     *
     * @param displayDateFormat The desired display date format
     *
     * @param clickedDateFormat The desired clicked date format
     *
     * @param locale The date locale
     */
    public void setDCData(String startMonthYear, String endMonthYear, String passedMonthYearRangeFormat, String monthYearHeaderFormat, String displayDateFormat, String clickedDateFormat, Locale locale)
    {
        dcDataList = getCalendarData(startMonthYear, endMonthYear, passedMonthYearRangeFormat, monthYearHeaderFormat, displayDateFormat, clickedDateFormat, locale, true);
        if(dcDataList.size() > 0)
        {
            // Initially moving the calendar to the first position
            moveCalendar(0);
        }
    }

    /**
     * Sets the Droid Calendar header bg color
     *
     * @param color The desired color
     */
    public void setDCHeaderBGColor(int color)
    {
        dcHeaderLayout.setBackgroundColor(color);
    }

    /**
     * Sets the Droid Calendar header text color
     *
     * @param color The desired color
     */
    public void setDCHeaderTextColor(int color)
    {
        dcHeader.setTextColor(color);
    }

    /**
     * Sets the Droid Calendar header first letter of each word to be capitalized
     */
    public void setDCHeaderCapitalize()
    {
        dcHeader.setText(WordUtils.capitalize(dcHeader.getText().toString().toLowerCase()));
    }

    /**
     * Sets the Droid Calendar header to be fully capitalized
     */
    public void setDCHeaderFullyCapitalize()
    {
        dcHeader.setText(dcHeader.getText().toString().toUpperCase());
    }

    /**
     * Sets the Droid Calendar header text size
     *
     * NOTE: Max size allowed 22f
     *
     * @param size The desired size
     */
    public void setDCHeaderTextSize(float size)
    {
        if(size > maxHeaderTextSize)
        {
            size = maxHeaderTextSize;
        }

        dcHeader.setTextSize(size);
    }

    /**
     * Sets the Droid Calendar header text style (NORMAL, BOLD, ITALIC, BOLD ITALIC)
     *
     * @param style The desired style
     */
    public void setDCHeaderTextStyle(int style)
    {
        dcHeader.setTypeface(null, style);
    }

    /**
     * Sets the Droid Calendar previous button color
     *
     * @param color The desired color
     */
    public void setDCPreviousButtonColor(int color)
    {
        previous.setTextColor(color);
    }

    /**
     * Sets the Droid Calendar previous button size
     *
     * NOTE: Max size allowed 30f
     *
     * @param size The desired size
     */
    public void setDCPreviousButtonSize(float size)
    {
        if(size > maxPrevNextButtonSize)
        {
            size = maxPrevNextButtonSize;
        }

        previous.setTextSize(size);
    }

    /**
     * Sets the Droid Calendar next button color
     *
     * @param color The desired color
     */
    public void setDCNextButtonColor(int color)
    {
        next.setTextColor(color);
    }

    /**
     * Sets the Droid Calendar next button size
     *
     * NOTE: Max size allowed 30f
     *
     * @param size The desired size
     */
    public void setDCNextButtonSize(float size)
    {
        if(size > maxPrevNextButtonSize)
        {
            size = maxPrevNextButtonSize;
        }

        next.setTextSize(size);
    }

    /**
     * Sets the Droid Calendar clickable dates
     *
     * @param clickableDates The desired clickable dates list
     */
    public void setDCClickableDates(List<String> clickableDates)
    {
        dcGridViewAdapter.setClickableDates(clickableDates);
    }

    /**
     * Sets the Droid Calendar clicked date
     *
     * @param clickedDate The date to be clicked
     *
     * @param moveMonthToClickedDate True - moves month to the clicked date, False - if otherwise
     */
    public void setDCClickedDate(String clickedDate, boolean moveMonthToClickedDate)
    {
        dcGridViewAdapter.setClickedDate(clickedDate);

        try
        {
            if(moveMonthToClickedDate && dcDataList != null)
            {
                for(int catx=0;catx<dcDataList.size();catx++)
                {
                    String monthYear = DCUtil.getDateInFormat(clickedDate, dcDataList.get(catx).datesFormat, dcDataList.get(catx).monthYearHeaderFormat, dcDataList.get(catx).locale, dcDataList.get(catx).locale);
                    if(monthYear.equals(dcDataList.get(catx).monthYearHeader))
                    {
                        moveCalendar(catx);

                        break;
                    }
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sets the Droid Calendar days headers layout bg color
     *
     * @param color The desired color
     */
    public void setDCDaysHeadersLayoutBGColor(int color)
    {
        dcGridViewAdapter.setDaysHeadersLayoutBGColor(color);
    }

    /**
     * Sets the Droid Calendar days headers text color
     *
     * @param color The desired color
     */
    public void setDCDaysHeadersTextColor(int color)
    {
        dcGridViewAdapter.setDaysHeadersTextColor(color);
    }

    /**
     * Sets the Droid Calendar days headers text size
     *
     * NOTE: Max size allowed 22f
     *
     * @param size The desired size
     */
    public void setDCDaysHeaderTextSize(float size)
    {
        if(size > maxDaysTextSize)
        {
            size = maxDaysTextSize;
        }

        dcGridViewAdapter.setDaysHeaderTextSize(size);
    }

    /**
     * Sets the Droid Calendar days header text style (NORMAL, BOLD, ITALIC, BOLD ITALIC)
     *
     * @param style The desired style
     */
    public void setDCDaysHeaderTextStyle(int style)
    {
        dcGridViewAdapter.setDaysHeaderTextStyle(style);
    }

    /**
     * Sets the Droid Calendar days layout bg color
     *
     * @param color The desired color
     */
    public void setDCDaysLayoutBGColor(int color)
    {
        dcGridViewAdapter.setDaysLayoutBGColor(color);
    }

    /**
     * Sets the Droid Calendar days text color
     *
     * @param color The desired color
     */
    public void setDCDaysTextColor(int color)
    {
        dcGridViewAdapter.setDaysTextColor(color);
    }

    /**
     * Sets the Droid Calendar days text size
     *
     * NOTE: Max size allowed 22f
     *
     * @param size The desired size
     */
    public void setDCDaysTextSize(float size)
    {
        if(size > maxDaysTextSize)
        {
            size = maxDaysTextSize;
        }

        dcGridViewAdapter.setDaysTextSize(size);
    }

    /**
     * Sets the Droid Calendar text style (NORMAL, BOLD, ITALIC, BOLD ITALIC)
     *
     * @param style The desired style
     */
    public void setDCDaysTextStyle(int style)
    {
        dcGridViewAdapter.setDaysTextStyle(style);
    }

    /**
     * Sets the Droid Calendar clickable days bg color
     *
     * @param color The desired color
     */
    public void setDCClickableDaysBGColor(int color)
    {
        dcGridViewAdapter.setClickableDaysBGColor(color);
    }

    /**
     * Sets the Droid Calendar clickable days text color
     *
     * @param color The desired color
     */
    public void setDCClickableDaysTextColor(int color)
    {
        dcGridViewAdapter.setClickableDaysTextColor(color);
    }

    /**
     * Sets the Droid Calendar clicked day bg color
     *
     * @param color The desired color
     */
    public void setDCClickedDayBGColor(int color)
    {
        dcGridViewAdapter.setClickedDayBGColor(color);
    }

    /**
     * Sets the Droid Calendar clicked day text color
     *
     * @param color The desired color
     */
    public void setDCClickedDayTextColor(int color)
    {
        dcGridViewAdapter.setClickedDayTextColor(color);
    }

    /**
     * Sets the Droid Calendar row separator color
     *
     * @param color The desired color
     */
    public void setDCRowSeparatorColor(int color)
    {
        dcGridViewAdapter.setRowSeparatorColor(color);
    }

    /**
     * Sets the Droid Calendar show row separator status
     *
     * @param showRowSeparator True - show row separator, False - if otherwise
     */
    public void setDCShowRowSeparator(boolean showRowSeparator)
    {
        dcGridViewAdapter.setShowRowSeparator(showRowSeparator);
    }

    /**
     * Sets the Droid Calendar all dates clickable status
     *
     * @param allDatesClickable True - all dates will be clickable, False - if otherwise
     */
    public void setDCAllDatesClickable(boolean allDatesClickable)
    {
        dcGridViewAdapter.setAllDatesClickable(allDatesClickable);
    }

    /**
     * Sets the Droid Calendar to enable only current & future dates
     *
     * NOTE: Applicable only when all dates are clickable
     *
     * @param enableOnlyFutureDates True - Current & future dates will be enabled, False - if otherwise
     */
    public void setDCEnableOnlyFutureDates(boolean enableOnlyFutureDates)
    {
        dcGridViewAdapter.setEnableOnlyFutureDates(enableOnlyFutureDates);
    }

    /**
     * Sets the Droid Calendar expanded status
     *
     * @param expanded True - expanded, False - if otherwise
     */
    public void setDCExpanded(boolean expanded)
    {
        dcGV.setExpanded(expanded);
    }

    // MARK: OnDCDatesListener Methods

    public void onDateClicked(String date)
    {
        if(onDCListener != null)
        {
            onDCListener.onDCDateClicked(date);
        }
    }

    public void onDateError(Exception e)
    {
        if(e != null)
        {
            e.printStackTrace();
        }
    }
}
