package com.vikramezhil.droidcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Droid Calendar View
 *
 * @author Vikram Ezhil
 */

public class DCView extends LinearLayout implements OnDCDatesListener
{
    private LinearLayout dcHeaderLayout;
    private TextView previous;
    private TextView dcHeader;
    private TextView next;
    private DCGridView dcGV;
    private DCGridViewAdapter dcGridViewAdapter;
    private List<DCData> dcDataList = new ArrayList<>();
    private DCProperties dcProperties = new DCProperties();
    private OnDCListener onDCListener;
    private GestureDetector gestureDetector;

    // MARK: Droid Calendar Constructor Methods

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

    // MARK: Droid Calendar Touch Methods

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    // MARK: Droid Calendar Listener

    /**
     * Sets the Droid Calendar Listener
     *
     * @param onDCListener The implemented class instance to initialize the interface
     */
    public void setOnDCListener(OnDCListener onDCListener)
    {
        this.onDCListener = onDCListener;

        final int dcPosition = dcProperties.dcPosition;
        if(onDCListener != null && dcPosition >= 0 && dcPosition < dcDataList.size())
        {
            // Sending an update with the on screen droid calendar data
            onDCListener.onDCScreenData(dcPosition, dcDataList.get(dcPosition).dates, dcDataList.get(dcPosition).recalibrateDates);
        }
    }

    // MARK: Droid Calendar Private Methods

    /**
     * Initializes the Droid Calendar View
     *
     * @param context The application context
     */
    private void init(final Context context)
    {
        View rootView = inflate(context, R.layout.dc, this);

        dcHeaderLayout = rootView.findViewById(R.id.dcHeaderLayout);

        previous = rootView.findViewById(R.id.previous);
        previous.setTypeface(DCUtil.getFATypeface(context));
        previous.setOnClickListener(new OnClickListener() {

            public void onClick(View view)
            {
                // Moving the calendar to previous month
                moveCalendar(dcProperties.dcPosition - 1);
            }
        });

        dcHeader = rootView.findViewById(R.id.dcHeader);

        next = rootView.findViewById(R.id.next);
        next.setTypeface(DCUtil.getFATypeface(context));
        next.setOnClickListener(new OnClickListener() {

            public void onClick(View view)
            {
                // Moving the calendar to next month
                moveCalendar(dcProperties.dcPosition + 1);
            }
        });

        // Initializing the gesture detector
        gestureDetector = new GestureDetector(context, new DCSwipeGestureDetector(new OnDCSwipeGestureListener()
        {
            @Override
            public void onDCSwipedLeftToRight()
            {
                if(dcProperties.enableHorizontalSwipe)
                {
                    // Starting the animation if applicable
                    startAnimation(context, dcProperties.horizontalSwipeAnimation, dcProperties.dcPosition - 1);

                    // Go to previous month
                    previous.performClick();
                }
            }

            @Override
            public void onDCSwipedRightToLeft()
            {
                if(dcProperties.enableHorizontalSwipe)
                {
                    // Starting the animation if applicable
                    startAnimation(context, dcProperties.horizontalSwipeAnimation, dcProperties.dcPosition + 1);

                    // Go to next month
                    next.performClick();
                }
            }

            @Override
            public void onDCSwipedTopToBottom()
            {
                if(dcProperties.enableVerticalSwipe)
                {
                    // Starting the animation if applicable
                    startAnimation(context, dcProperties.verticalSwipeAnimation, dcProperties.dcPosition - 1);

                    // Go to previous month
                    previous.performClick();
                }
            }

            @Override
            public void onDCSwipedBottomToTop()
            {
                if(dcProperties.enableVerticalSwipe)
                {
                    // Starting the animation if applicable
                    startAnimation(context, dcProperties.verticalSwipeAnimation, dcProperties.dcPosition + 1);

                    // Go to next month
                    next.performClick();
                }
            }
        }));

        dcGV = rootView.findViewById(R.id.dcGV);
        dcGridViewAdapter = new DCGridViewAdapter(context, dcProperties, this);
        dcGV.setAdapter(dcGridViewAdapter);
        dcGV.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                gestureDetector.onTouchEvent(motionEvent);

                return true;
            }
        });

        // Setting default droid calendar data initially
        setDCData(12, DCFormats.DC_MY_FORMAT, DCFormats.D_FORMAT, true, Locale.US);
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
            dcProperties.dcPosition = position;

            if(dcProperties.showPrevAndNextButtons)
            {
                // Showing (or) Hiding Next & Previous buttons
                showHidePrevAndNextButtons();
            }

            // Setting the month & year header
            dcHeader.setText(dcDataList.get(position).monthYearHeader);

            // Setting the calendar data in the Droid Calendar adapter
            dcGridViewAdapter.setDcData(dcDataList.get(position));

            if(onDCListener != null)
            {
                // Sending an update with the on screen droid calendar data
                onDCListener.onDCScreenData(position, dcDataList.get(position).dates, dcDataList.get(position).recalibrateDates);
            }
        }
    }

    /**
     * Starts the animation
     *
     * @param context The application context
     *
     * @param animation The animation to be set
     *
     * @param position The calendar position
     */
    private void startAnimation(Context context, int animation, int position)
    {
        if(dcProperties.showSwipeAnimation && position >= 0 && position < dcDataList.size())
        {
            dcGV.clearAnimation();
            dcGV.setAnimation(AnimationUtils.loadAnimation(context, animation));
        }
    }

    /**
     * Shows (or) Hides previous and next buttons
     */
    private void showHidePrevAndNextButtons()
    {
        int nextDecrementPosition = dcProperties.dcPosition - 1;
        previous.setVisibility(nextDecrementPosition < 0 ? View.INVISIBLE : View.VISIBLE);

        int nextIncrementPosition = dcProperties.dcPosition + 1;
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
                // recalibrateDatesList.add(null);

                // Adding dates from previous month if applicable
                if(catx == 0)
                {
                    recalibrateDatesList.add(DCUtil.getPreviousDate(datesToBeRecalibrate.get(0), dateFormat, locale));
                }
                else
                {
                    recalibrateDatesList.add(DCUtil.getPreviousDate(recalibrateDatesList.get(recalibrateDatesList.size() - 1), dateFormat, locale));
                }
            }

            if(recalibrateDatesList.size() > 0)
            {
                // Reversing the dates list
                Collections.reverse(recalibrateDatesList);
            }

            for(String date: datesToBeRecalibrate)
            {
                // Adding the dates
                recalibrateDatesList.add(date);
            }

            for(int caty=endWeekPos;caty<DCFormats.DAYS_IN_A_WEEK.length;caty++)
            {
                // Adding empty data after the end date if applicable
                // recalibrateDatesList.add(null);

                // Adding dates from next month if applicable
                recalibrateDatesList.add(DCUtil.getNextDate(recalibrateDatesList.get(recalibrateDatesList.size() - 1), dateFormat, locale));
            }
        }

        return recalibrateDatesList;
    }

    /**
     * Updates the Droid Calendar Properties in the adapter
     */
    private void updateDCPropertiesInAdapter()
    {
        dcGridViewAdapter.setDCProperties(dcProperties);
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
     * @param future True - current & future months will be retrieved, False - current and previous months will be retrieved
     *
     * @param locale The date locale
     */
    public void setDCData(int monthRange, String monthYearHeaderFormat, String displayDateFormat, boolean future, Locale locale)
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

        dcDataList = getCalendarData(startMonthYear, endMonthYear, DCFormats.DC_MY_FORMAT, monthYearHeaderFormat, displayDateFormat, DCFormats.DC_DMY_FORMAT, locale, false);
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
     * @param locale The date locale
     */
    public void setDCData(String startMonthYear, String endMonthYear, String passedMonthYearRangeFormat, String monthYearHeaderFormat, String displayDateFormat, Locale locale)
    {
        dcDataList = getCalendarData(startMonthYear, endMonthYear, passedMonthYearRangeFormat, monthYearHeaderFormat, displayDateFormat, DCFormats.DC_DMY_FORMAT, locale, true);
        if(dcDataList.size() > 0)
        {
            // Initially moving the calendar to the first position
            moveCalendar(0);
        }
    }

    /**
     * Sets the DC Dates sub data
     *
     * @param calendarPosition The calendar position
     *
     * @param date The date value in the calendar, NOTE: Date format should be "d/M/YYYY"
     *
     * @param subValue The sub value for the date
     */
    public void setDCDatesSubData(int calendarPosition, String date, String subValue)
    {
        if(dcDataList != null && calendarPosition < dcDataList.size() && date != null && subValue != null)
        {
            dcDataList.get(calendarPosition).setDateSubValue(date, subValue);

            if(dcProperties.dcPosition == calendarPosition)
            {
                // Setting the calendar data in Droid Calendar Adapter
                dcGridViewAdapter.setDcData(dcDataList.get(calendarPosition));
            }
        }
    }

    /**
     * Removes the DC dates sub data by date
     *
     * @param calendarPosition The calendar position
     *
     * @param date The date value in the calendar, NOTE: Date format should be "d/M/YYYY"
     */
    public void removeDCDatesSubDataByDate(int calendarPosition, String date)
    {
        if(dcDataList != null && calendarPosition < dcDataList.size() && date != null)
        {
            dcDataList.get(calendarPosition).removeDateSubValue(date);

            if(dcProperties.dcPosition == calendarPosition)
            {
                // Setting the calendar data in Droid Calendar Adapter
                dcGridViewAdapter.setDcData(dcDataList.get(calendarPosition));
            }
        }
    }

    /**
     * Clears the DC dates sub data
     *
     * @param calendarPosition The calendar position
     */
    public void clearDCDatesSubData(int calendarPosition)
    {
        if(dcDataList != null && calendarPosition < dcDataList.size())
        {
            dcDataList.get(calendarPosition).clearDatesSubValues();

            if(dcProperties.dcPosition == calendarPosition)
            {
                // Setting the calendar data in Droid Calendar Adapter
                dcGridViewAdapter.setDcData(dcDataList.get(calendarPosition));
            }
        }
    }

    /**
     * Sets the DC Dates sub data
     *
     * @param calendarPosition The calendar position
     *
     * @param datesSubValues The dates & sub values list map, NOTE: Date key format should be "d/M/YYYY"
     */
    public void setDCDatesSubData(int calendarPosition, Map<String, String> datesSubValues)
    {
        if(dcDataList != null && calendarPosition < dcDataList.size() && datesSubValues != null)
        {
            dcDataList.get(calendarPosition).setDatesSubValues(datesSubValues);

            if(dcProperties.dcPosition == calendarPosition)
            {
                // Setting the calendar data in Droid Calendar Adapter
                dcGridViewAdapter.setDcData(dcDataList.get(calendarPosition));
            }
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
     * NOTE: Max size allowed "22f"
     *
     * @param size The desired size
     */
    public void setDCHeaderTextSize(float size)
    {
        if(size > dcProperties.maxHeaderTextSize)
        {
            size = dcProperties.maxHeaderTextSize;
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
     * NOTE: Max size allowed "30f"
     *
     * @param size The desired size
     */
    public void setDCPreviousButtonSize(float size)
    {
        if(size > dcProperties.maxPrevNextButtonSize)
        {
            size = dcProperties.maxPrevNextButtonSize;
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
     * NOTE: Max size allowed "30f"
     *
     * @param size The desired size
     */
    public void setDCNextButtonSize(float size)
    {
        if(size > dcProperties.maxPrevNextButtonSize)
        {
            size = dcProperties.maxPrevNextButtonSize;
        }

        next.setTextSize(size);
    }

    /**
     * Sets the Droid Calendar clickable dates
     *
     * NOTE: Date format should be "d/M/YYYY". Examples - 15/6/1988, 1/1/2017
     *
     * @param clickableDates The desired clickable dates list
     */
    public void setDCClickableDates(List<String> clickableDates)
    {
        dcProperties.clickableDates = clickableDates;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar clicked date
     *
     * NOTE: The passed clicked date format should be "d/M/YYYY". Examples - 15/6/1988, 1/1/2017
     *
     * @param clickedDate The date to be clicked
     *
     * @param moveMonthToClickedDate True - moves calendar month to the clicked date, False - if otherwise
     */
    public void setDCClickedDate(String clickedDate, boolean moveMonthToClickedDate)
    {
        dcProperties.clickedDate = clickedDate;

        updateDCPropertiesInAdapter();

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
        dcProperties.daysHeadersLayoutBGColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days headers text color
     *
     * @param color The desired color
     */
    public void setDCDaysHeadersTextColor(int color)
    {
        dcProperties.daysHeadersTextColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days headers text size
     *
     * NOTE: Max size allowed "22f"
     *
     * @param size The desired size
     */
    public void setDCDaysHeaderTextSize(float size)
    {
        if(size > dcProperties.maxDaysTextSize)
        {
            size = dcProperties.maxDaysTextSize;
        }

        dcProperties.daysHeaderTextSize = size;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days header text style (NORMAL, BOLD, ITALIC, BOLD ITALIC)
     *
     * @param style The desired style
     */
    public void setDCDaysHeaderTextStyle(int style)
    {
        dcProperties.daysHeaderTextStyle = style;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days layout bg color
     *
     * @param color The desired color
     */
    public void setDCDaysLayoutBGColor(int color)
    {
        dcProperties.daysLayoutBGColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days text color
     *
     * @param color The desired color
     */
    public void setDCDaysTextColor(int color)
    {
        dcProperties.daysTextColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days sub text color
     *
     * @param color The desired color
     */
    public void setDCDaysSubTextColor(int color)
    {
        dcProperties.daysSubTextColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days text size
     *
     * NOTE: Max size allowed "22f"
     *
     * @param size The desired size
     */
    public void setDCDaysTextSize(float size)
    {
        if(size > dcProperties.maxDaysTextSize)
        {
            size = dcProperties.maxDaysTextSize;
        }

        dcProperties.daysTextSize = size;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar days sub text size
     *
     * NOTE: Max size allowed "14f"
     *
     * @param size The desired size
     */
    public void setDCDaysSubTextSize(float size)
    {
        if(size > dcProperties.maxDaysSubTextSize)
        {
            size = dcProperties.maxDaysSubTextSize;
        }

        dcProperties.daysSubTextSize = size;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar text style (NORMAL, BOLD, ITALIC, BOLD ITALIC)
     *
     * @param style The desired style
     */
    public void setDCDaysTextStyle(int style)
    {
        dcProperties.daysTextStyle = style;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar sub text style (NORMAL, BOLD, ITALIC, BOLD ITALIC)
     *
     * @param style The desired style
     */
    public void setDCDaysSubTextStyle(int style)
    {
        dcProperties.daysSubTextStyle = style;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar clickable days bg color
     *
     * @param color The desired color
     */
    public void setDCClickableDaysBGColor(int color)
    {
        dcProperties.clickableDaysBGColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar clickable days text color
     *
     * @param color The desired color
     */
    public void setDCClickableDaysTextColor(int color)
    {
        dcProperties.clickableDaysTextColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar clicked day bg color
     *
     * @param color The desired color
     */
    public void setDCClickedDayBGColor(int color)
    {
        dcProperties.clickedDayBGColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar clicked day text color
     *
     * @param color The desired color
     */
    public void setDCClickedDayTextColor(int color)
    {
        dcProperties.clickedDayTextColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar clicked day sub text color
     *
     * @param color The desired color
     */
    public void setDCClickedDaySubTextColor(int color)
    {
        dcProperties.clickedDaySubTextColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar column separator color
     *
     * @param color The desired color
     */
    public void setDCColSeparatorColor(int color)
    {
        dcProperties.colSeparatorColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar row separator color
     *
     * @param color The desired color
     */
    public void setDCRowSeparatorColor(int color)
    {
        dcProperties.rowSeparatorColor = color;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the horizontal swipe animation
     *
     * NOTE: Default animation is "fade in"
     *
     * @param horizontalSwipeAnimation The horizontal swipe animation file
     */
    public void setHorizontalSwipeAnimation(int horizontalSwipeAnimation)
    {
        dcProperties.horizontalSwipeAnimation = horizontalSwipeAnimation;
    }

    /**
     * Sets the vertical swipe animation
     *
     * NOTE: Default animation is "fade in"
     *
     * @param verticalSwipeAnimation The vertical swipe animation file
     */
    public void setVerticalSwipeAnimation(int verticalSwipeAnimation)
    {
        dcProperties.verticalSwipeAnimation = verticalSwipeAnimation;
    }

    /**
     * Sets the show previous and next buttons status
     *
     * NOTE: Default is true
     *
     * @param showPrevAndNextButtons True - show previous and next buttons, False - if otherwise
     */
    public void setShowPrevAndNextButtons(boolean showPrevAndNextButtons)
    {
        dcProperties.showPrevAndNextButtons = showPrevAndNextButtons;

        previous.setVisibility(showPrevAndNextButtons ? VISIBLE : INVISIBLE);
        next.setVisibility(showPrevAndNextButtons ? VISIBLE : INVISIBLE);
    }

    /**
     * Sets the Droid Calendar horizontal swipe status
     *
     * NOTE: Default is false
     *
     * @param enableHorizontalSwipe True - horizontal swipe will be enabled, False - if otherwise
     */
    public void setDCEnableHorizontalSwipe(boolean enableHorizontalSwipe)
    {
        dcProperties.enableHorizontalSwipe = enableHorizontalSwipe;
    }

    /**
     * Sets the Droid Calendar vertical swipe status
     *
     * NOTE: Default is false
     *
     * @param enableVerticalSwipe True - vertical swipe will be enabled, False - if otherwise
     */
    public void setDCEnableVerticalSwipe(boolean enableVerticalSwipe)
    {
        dcProperties.enableVerticalSwipe = enableVerticalSwipe;
    }

    /**
     * Sets the Droid Calendar show swipe animation status
     *
     * NOTE: Default is false
     *
     * @param showSwipeAnimation True - show swipe animation, False - if otherwise
     */
    public void setDCShowSwipeAnimation(boolean showSwipeAnimation)
    {
        dcProperties.showSwipeAnimation = showSwipeAnimation;
    }

    /**
     * Sets the Droid Calendar show column separator status for day headers
     *
     * NOTE: Default is false
     *
     * @param showDaysHeaderColSeparator True - show days header column separator, False - if otherwise
     */
    public void setDCShowDaysHeaderColSeparator(boolean showDaysHeaderColSeparator)
    {
        dcProperties.showDaysHeaderColSeparator = showDaysHeaderColSeparator;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar show column separator status
     *
     * NOTE: Default is false
     *
     * @param showColSeparator True - show column separator, False - if otherwise
     */
    public void setDCShowColSeparator(boolean showColSeparator)
    {
        dcProperties.showColSeparator = showColSeparator;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar show row separator status for day headers
     *
     * NOTE: Default is false
     *
     * @param showDaysHeaderRowSeparator True - show days header row separator, False - if otherwise
     */
    public void setDCShowDaysHeaderRowSeparator(boolean showDaysHeaderRowSeparator)
    {
        dcProperties.showDaysHeaderRowSeparator = showDaysHeaderRowSeparator;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar show row separator status
     *
     * NOTE: Default is true
     *
     * @param showRowSeparator True - show row separator, False - if otherwise
     */
    public void setDCShowRowSeparator(boolean showRowSeparator)
    {
        dcProperties.showRowSeparator = showRowSeparator;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar show past and future month dates status
     *
     * NOTE: Default is false
     *
     * @param showPastAndFutureMonthDates True - shows past and future months dates if applicable, False if otherwise
     */
    public void setDCShowPastAndFutureMonthDates(boolean showPastAndFutureMonthDates)
    {
        dcProperties.showPastAndFutureMonthDates = showPastAndFutureMonthDates;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar show day sub value status
     *
     * NOTE: Default is false
     *
     * @param showDaySubValue True - show day sub value, False - if otherwise
     */
    public void setDCShowDaySubValue(boolean showDaySubValue)
    {
        dcProperties.showDaySubValue = showDaySubValue;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar all dates clickable status
     *
     * NOTE: Default is true
     *
     * @param allDatesClickable True - all dates will be clickable, False - if otherwise
     */
    public void setDCAllDatesClickable(boolean allDatesClickable)
    {
        dcProperties.allDatesClickable = allDatesClickable;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar to enable only current & future dates
     *
     * NOTE: Default is false. Applicable only when all dates are clickable.
     *
     * @param enableOnlyFutureDates True - current & future dates will be enabled, False - if otherwise
     */
    public void setDCEnableOnlyFutureDates(boolean enableOnlyFutureDates)
    {
        dcProperties.enableOnlyFutureDates = enableOnlyFutureDates;

        updateDCPropertiesInAdapter();
    }

    /**
     * Sets the Droid Calendar expanded status
     *
     * NOTE: Default is false
     *
     * @param expanded True - view will not be scrollable, False - view will be scrollable
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