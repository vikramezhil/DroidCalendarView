package com.vikramezhil.droidcalendarview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Droid Calendar Grid View Adapter
 *
 * Created by Vikram Ezhil on 01/10/17.
 *
 * email: vikram.ezhil.1988@gmail.com
 */

class DCGridViewAdapter extends BaseAdapter 
{
    private Context context;
    private DCData dcData;
    private List<String> clickableDates = new ArrayList<>();
    private String clickedDate;
    private float daysHeaderTextSize = 18f;
    private float daysTextSize = 18f;
    private int daysHeaderTextStyle = Typeface.NORMAL;
    private int daysTextStyle = Typeface.NORMAL;
    private int daysHeadersLayoutBGColor = Color.parseColor("#F5F5F5");
    private int daysHeadersTextColor = Color.parseColor("#999898");
    private int daysLayoutBGColor = Color.WHITE;
    private int daysTextColor = Color.parseColor("#999898");
    private int clickableDaysBGColor = Color.WHITE;
    private int clickableDaysTextColor = Color.parseColor("#999898");
    private int clickedDayBGColor = Color.parseColor("#00ADEF");
    private int clickedDayTextColor = Color.WHITE;
    private int rowSeparatorColor = Color.parseColor("#F5F5F5");
    private boolean showRowSeparator = true;
    private boolean allDatesClickable = true;
    private boolean enableOnlyFutureDates = false;
    private Locale locale = Locale.US;

    private OnDCDatesListener onDCDatesListener;

    // MARK: View Holder Class

    private static class ViewHolder
    {
        LinearLayout dcParentLayout;
        LinearLayout dcChildLayout;
        TextView dcDayValue;
        View dcRowSeparator;
    }

    // MARK: DCGridViewAdapter Constructor

    /**
     * Droid Calendar Grid View Adapter Constructor
     *
     * @param context The class instance to initialize the context
     *
     * @param onDCDatesListener The class instance to initialize the listener
     */
    DCGridViewAdapter(Context context, OnDCDatesListener onDCDatesListener) 
    {
        this.context = context;
        this.onDCDatesListener = onDCDatesListener;
    }

    // MARK: DCGridViewAdapter Methods

    /**
     * Sets the Droid Calendar Data
     *
     * @param dcData The droid calendar data
     */
    void setDcData(DCData dcData) 
    {
        this.dcData = dcData;

        locale = dcData.locale;

        notifyDataSetChanged();
    }

    /**
     * Sets the clickable dates
     *
     * @param clickableDates The clickable dates
     */
    void setClickableDates(List<String> clickableDates) 
    {
        this.clickableDates = clickableDates;

        notifyDataSetChanged();
    }

    /**
     * Sets the clicked date
     *
     * @param clickedDate The clicked date
     */
    void setClickedDate(String clickedDate) 
    {
        this.clickedDate = clickedDate;

        notifyDataSetChanged();
    }

    /**
     * Sets the days header layout bg color
     *
     * @param daysHeadersLayoutBGColor The days header bg color
     */
    void setDaysHeadersLayoutBGColor(int daysHeadersLayoutBGColor) 
    {
        this.daysHeadersLayoutBGColor = daysHeadersLayoutBGColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the days header text color
     *
     * @param daysHeadersTextColor The days header text color
     */
    void setDaysHeadersTextColor(int daysHeadersTextColor) 
    {
        this.daysHeadersTextColor = daysHeadersTextColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the days header text size
     *
     * @param daysHeaderTextSize The days header text size
     */
    void setDaysHeaderTextSize(float daysHeaderTextSize)
    {
        this.daysHeaderTextSize = daysHeaderTextSize;

        notifyDataSetChanged();
    }

    /**
     * Sets the days header text style
     *
     * @param daysHeaderTextStyle The days header text style
     */
    void setDaysHeaderTextStyle(int daysHeaderTextStyle)
    {
        this.daysHeaderTextStyle = daysHeaderTextStyle;

        notifyDataSetChanged();
    }

    /**
     * Sets the days layout bg color
     *
     * @param daysLayoutBGColor The days layout bg color
     */
    void setDaysLayoutBGColor(int daysLayoutBGColor)
    {
        this.daysLayoutBGColor = daysLayoutBGColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the days text color
     *
     * @param daysTextColor The days text color
     */
    void setDaysTextColor(int daysTextColor)
    {
        this.daysTextColor = daysTextColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the days text size
     *
     * @param daysTextSize The days text size
     */
    void setDaysTextSize(float daysTextSize)
    {
        this.daysTextSize = daysTextSize;

        notifyDataSetChanged();
    }

    /**
     * Sets the days text style
     *
     * @param daysTextStyle The days text style
     */
    void setDaysTextStyle(int daysTextStyle)
    {
        this.daysTextStyle = daysTextStyle;

        notifyDataSetChanged();
    }

    /**
     * Sets the clickable days bg color
     *
     * @param clickableDaysBGColor The clickable days bg color
     */
    void setClickableDaysBGColor(int clickableDaysBGColor)
    {
        this.clickableDaysBGColor = clickableDaysBGColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the clickable days text color
     *
     * @param clickableDaysTextColor The clickable days text color
     */
    void setClickableDaysTextColor(int clickableDaysTextColor)
    {
        this.clickableDaysTextColor = clickableDaysTextColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the clicked day bg color
     *
     * @param clickedDayBGColor The clicked day bg color
     */
    void setClickedDayBGColor(int clickedDayBGColor)
    {
        this.clickedDayBGColor = clickedDayBGColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the clicked day text color
     *
     * @param clickedDayTextColor The clicked day text color
     */
    void setClickedDayTextColor(int clickedDayTextColor)
    {
        this.clickedDayTextColor = clickedDayTextColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the row separator color
     *
     * @param rowSeparatorColor The row separator color
     */
    void setRowSeparatorColor(int rowSeparatorColor)
    {
        this.rowSeparatorColor = rowSeparatorColor;

        notifyDataSetChanged();
    }

    /**
     * Sets the show row separator status
     *
     * @param showRowSeparator True - show row separator, False - if otherwise
     */
    void setShowRowSeparator(boolean showRowSeparator)
    {
        this.showRowSeparator = showRowSeparator;

        notifyDataSetChanged();
    }

    /**
     * Sets all dates clickable status
     *
     * @param allDatesClickable True - all dates are clickable, False - if otherwise
     */
    void setAllDatesClickable(boolean allDatesClickable)
    {
        this.allDatesClickable = allDatesClickable;

        notifyDataSetChanged();
    }

    /**
     * Sets enable only future dates status
     *
     * @param enableOnlyFutureDates True - enables only current & future dates, False - if otherwise
     */
    void setEnableOnlyFutureDates(boolean enableOnlyFutureDates)
    {
        this.enableOnlyFutureDates = enableOnlyFutureDates;

        notifyDataSetChanged();
    }

    // MARK: BaseAdapter Methods

    public int getCount()
    {
        if(dcData != null && dcData.recalibrateDates != null)
        {
            return DCFormats.DAYS_IN_A_WEEK.length + dcData.recalibrateDates.size();
        }
        else
        {
            return DCFormats.DAYS_IN_A_WEEK.length;
        }
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dc_gv_date, parent, false);

            holder.dcParentLayout = convertView.findViewById(R.id.dcParentLayout);
            holder.dcChildLayout = convertView.findViewById(R.id.dcChildLayout);
            holder.dcDayValue = convertView.findViewById(R.id.dcDayValue);
            holder.dcRowSeparator = convertView.findViewById(R.id.dcRowSeparator);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        try
        {
            // Setting the tag position
            holder.dcChildLayout.setTag(position);

            // Setting the view properties

            holder.dcChildLayout.setAlpha(1f);
            holder.dcRowSeparator.setBackgroundColor(rowSeparatorColor);

            if(position < DCFormats.DAYS_IN_A_WEEK.length)
            {
                // Days headers

                holder.dcRowSeparator.setVisibility(View.INVISIBLE);
                holder.dcDayValue.setText(DCUtil.getDateInFormat(DCFormats.DAYS_IN_A_WEEK[position], DCFormats.DC_EEEE_FORMAT, DCFormats.DC_EEE_FORMAT, Locale.US, locale).replace(".", ""));
                holder.dcChildLayout.setEnabled(false);
                holder.dcChildLayout.setOnClickListener(null);
                holder.dcParentLayout.setBackgroundColor(daysHeadersLayoutBGColor);
                holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                holder.dcDayValue.setTextColor(daysHeadersTextColor);
                holder.dcDayValue.setTextSize(daysHeaderTextSize);
                holder.dcDayValue.setTypeface(null, daysHeaderTextStyle);
            }
            else
            {
                // Days

                holder.dcRowSeparator.setVisibility(showRowSeparator ? View.VISIBLE : View.INVISIBLE);
                holder.dcParentLayout.setBackgroundColor(daysLayoutBGColor);
                holder.dcDayValue.setTextSize(daysTextSize);
                holder.dcDayValue.setTypeface(null, daysTextStyle);

                int modifiedPosition = position - DCFormats.DAYS_IN_A_WEEK.length;
                if(modifiedPosition >= 0 && modifiedPosition < dcData.recalibrateDates.size())
                {
                    String date = dcData.recalibrateDates.get(modifiedPosition);

                    if(date != null && !date.isEmpty())
                    {
                        String dateVal = DCUtil.getDateInFormat(date, dcData.datesFormat, dcData.displayDateFormat, locale, locale);
                        holder.dcDayValue.setText(dateVal);
                        holder.dcChildLayout.setEnabled(true);
                        holder.dcChildLayout.setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View view)
                            {
                                int clickPosition = ((int) view.getTag()) - DCFormats.DAYS_IN_A_WEEK.length;
                                if(clickPosition >= 0 && clickPosition < dcData.recalibrateDates.size())
                                {
                                    // Getting and setting the clicked date
                                    clickedDate = dcData.recalibrateDates.get(clickPosition);

                                    // Sending back an update with the clicked date
                                    onDCDatesListener.onDateClicked(DCUtil.getDateInFormat(clickedDate, dcData.datesFormat, dcData.clickedDateFormat, locale, locale));

                                    notifyDataSetChanged();
                                }
                            }
                        });

                        if(clickedDate != null && clickedDate.equals(dcData.recalibrateDates.get(modifiedPosition)))
                        {
                            holder.dcChildLayout.setBackgroundColor(clickedDayBGColor);
                            holder.dcDayValue.setTextColor(clickedDayTextColor);
                        }
                        else if((clickableDates == null || clickableDates.size() <= 0) && !allDatesClickable)
                        {
                            holder.dcChildLayout.setEnabled(false);
                            holder.dcChildLayout.setOnClickListener(null);
                            holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                            holder.dcDayValue.setTextColor(daysTextColor);
                        }
                        else if(allDatesClickable)
                        {
                            if(enableOnlyFutureDates && !DCUtil.checkIfDateIsCurrentOrFuture(date, dcData.datesFormat, locale))
                            {
                                holder.dcChildLayout.setAlpha(0.3f);
                                holder.dcChildLayout.setEnabled(false);
                                holder.dcChildLayout.setOnClickListener(null);
                                holder.dcChildLayout.setBackgroundColor(daysLayoutBGColor);
                                holder.dcDayValue.setTextColor(daysTextColor);
                            }
                            else
                            {
                                holder.dcChildLayout.setBackgroundColor(clickableDaysBGColor);
                                holder.dcDayValue.setTextColor(clickableDaysTextColor);
                            }
                        }
                        else if(clickableDates.contains(date))
                        {
                            holder.dcChildLayout.setBackgroundColor(clickableDaysBGColor);
                            holder.dcDayValue.setTextColor(clickableDaysTextColor);
                        }
                        else
                        {
                            holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                            holder.dcDayValue.setTextColor(daysTextColor);
                            holder.dcChildLayout.setEnabled(false);
                            holder.dcChildLayout.setOnClickListener(null);
                            holder.dcChildLayout.setBackgroundColor(daysLayoutBGColor);
                            holder.dcDayValue.setTextColor(daysTextColor);
                        }
                    }
                    else
                    {
                        holder.dcDayValue.setText("");
                        holder.dcChildLayout.setEnabled(false);
                        holder.dcChildLayout.setOnClickListener(null);
                        holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                        holder.dcDayValue.setTextColor(Color.TRANSPARENT);
                    }
                }
            }
        }
        catch (Exception e)
        {
            onDCDatesListener.onDateError(e);
        }

        return convertView;
    }
}

