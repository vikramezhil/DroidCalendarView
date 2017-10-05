package com.vikramezhil.droidcalendarview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Droid Calendar Grid View Adapter
 *
 * @author Vikram Ezhil
 */

class DCGridViewAdapter extends BaseAdapter 
{
    private Context context;
    private DCData dcData;
    private DCProperties dcProperties;
    private OnDCDatesListener onDCDatesListener;

    // MARK: View Holder Class

    private static class ViewHolder
    {
        LinearLayout dcParentLayout, dcChildLayout;
        TextView dcDayValue, dcDaySubValue;
        View dcColSeparator, dcRowSeparator;
    }

    // MARK: DCGridViewAdapter Constructor

    /**
     * Droid Calendar Grid View Adapter Constructor
     *
     * @param context The class instance to initialize the context
     *
     * @param dcProperties The droid calendar properties
     *
     * @param onDCDatesListener The class instance to initialize the listener
     */
    DCGridViewAdapter(Context context, DCProperties dcProperties, OnDCDatesListener onDCDatesListener)
    {
        this.context = context;
        this.dcProperties = dcProperties;
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

        notifyDataSetChanged();
    }

    /**
     * Sets the Droid Calendar properties
     *
     * @param dcProperties The droid calendar properties
     */
    void setDCProperties(DCProperties dcProperties)
    {
        this.dcProperties = dcProperties;

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
            return 0;
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
            holder.dcDaySubValue = convertView.findViewById(R.id.dcDaySubValue);
            holder.dcColSeparator = convertView.findViewById(R.id.dcColSeparator);
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
            holder.dcColSeparator.setBackgroundColor(dcProperties.colSeparatorColor);
            holder.dcRowSeparator.setBackgroundColor(dcProperties.rowSeparatorColor);

            if(position < DCFormats.DAYS_IN_A_WEEK.length)
            {
                // Days headers

                holder.dcColSeparator.setVisibility(dcProperties.showDaysHeaderColSeparator ? View.VISIBLE : View.GONE);
                holder.dcRowSeparator.setVisibility(dcProperties.showDaysHeaderRowSeparator ? View.VISIBLE : View.INVISIBLE);
                holder.dcDaySubValue.setVisibility(View.GONE);
                holder.dcDayValue.setText(DCUtil.getDateInFormat(DCFormats.DAYS_IN_A_WEEK[position], DCFormats.DC_EEEE_FORMAT, DCFormats.DC_EEE_FORMAT, Locale.US, dcData.locale).replace(".", ""));
                holder.dcChildLayout.setEnabled(false);
                holder.dcChildLayout.setOnClickListener(null);
                holder.dcParentLayout.setBackgroundColor(dcProperties.daysHeadersLayoutBGColor);
                holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                holder.dcDayValue.setTextColor(dcProperties.daysHeadersTextColor);
                holder.dcDayValue.setTextSize(dcProperties.daysHeaderTextSize);
                holder.dcDayValue.setTypeface(null, dcProperties.daysHeaderTextStyle);
            }
            else
            {
                // Days

                holder.dcColSeparator.setVisibility(dcProperties.showColSeparator ? View.VISIBLE : View.GONE);
                holder.dcRowSeparator.setVisibility(dcProperties.showRowSeparator ? View.VISIBLE : View.INVISIBLE);
                holder.dcParentLayout.setBackgroundColor(dcProperties.daysLayoutBGColor);
                holder.dcDayValue.setTextSize(dcProperties.daysTextSize);
                holder.dcDaySubValue.setTextSize(dcProperties.daysSubTextSize);
                holder.dcDayValue.setTypeface(null, dcProperties.daysTextStyle);
                holder.dcDaySubValue.setTypeface(null, dcProperties.daysSubTextStyle);

                int modifiedPosition = position - DCFormats.DAYS_IN_A_WEEK.length;
                if(modifiedPosition >= 0 && modifiedPosition < dcData.recalibrateDates.size())
                {
                    String date = dcData.recalibrateDates.get(modifiedPosition);

                    if(date == null || date.isEmpty())
                    {
                        holder.dcDayValue.setText("");
                        holder.dcDaySubValue.setVisibility(dcProperties.showDaySubValue ? View.INVISIBLE : View.GONE);
                        holder.dcChildLayout.setEnabled(false);
                        holder.dcChildLayout.setOnClickListener(null);
                        holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                        holder.dcDayValue.setTextColor(Color.TRANSPARENT);
                        holder.dcDaySubValue.setTextColor(Color.TRANSPARENT);
                    }
                    else
                    {
                        // Setting the date value
                        String dateVal = DCUtil.getDateInFormat(date, dcData.datesFormat, dcData.displayDateFormat, dcData.locale, dcData.locale);
                        holder.dcDayValue.setText(dateVal);

                        // Setting the date sub value
                        holder.dcDaySubValue.setVisibility(dcProperties.showDaySubValue ? View.VISIBLE : View.GONE);
                        if(dcProperties.showDaySubValue)
                        {
                            holder.dcDaySubValue.setText(dcData.datesSubValues.containsKey(date) ? dcData.datesSubValues.get(date) : "");
                        }

                        String convertDateToMonthYear = DCUtil.getDateInFormat(date, dcData.datesFormat, dcData.monthYearHeaderFormat, dcData.locale, dcData.locale);
                        if(!convertDateToMonthYear.toLowerCase().equals(dcData.monthYearHeader.toLowerCase()))
                        {
                            if(dcProperties.showPastAndFutureMonthDates)
                            {
                                holder.dcChildLayout.setAlpha(0.3f);
                                holder.dcChildLayout.setEnabled(false);
                                holder.dcChildLayout.setOnClickListener(null);
                                holder.dcChildLayout.setBackgroundColor(dcProperties.daysLayoutBGColor);
                                holder.dcDayValue.setTextColor(dcProperties.daysTextColor);
                                holder.dcDaySubValue.setTextColor(dcProperties.daysSubTextColor);
                            }
                            else
                            {
                                holder.dcDayValue.setText("");
                                holder.dcDaySubValue.setVisibility(dcProperties.showDaySubValue ? View.INVISIBLE : View.GONE);
                                holder.dcChildLayout.setEnabled(false);
                                holder.dcChildLayout.setOnClickListener(null);
                                holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                                holder.dcDayValue.setTextColor(Color.TRANSPARENT);
                                holder.dcDaySubValue.setTextColor(Color.TRANSPARENT);
                            }
                        }
                        else
                        {
                            holder.dcChildLayout.setEnabled(true);
                            holder.dcChildLayout.setOnClickListener(new View.OnClickListener()
                            {
                                public void onClick(View view)
                                {
                                    int clickPosition = ((int) view.getTag()) - DCFormats.DAYS_IN_A_WEEK.length;
                                    if(clickPosition >= 0 && clickPosition < dcData.recalibrateDates.size())
                                    {
                                        // Getting and setting the clicked date
                                        dcProperties.clickedDate = dcData.recalibrateDates.get(clickPosition);

                                        // Sending back an update with the clicked date
                                        onDCDatesListener.onDateClicked(DCUtil.getDateInFormat(dcProperties.clickedDate, dcData.datesFormat, dcData.clickedDateFormat, dcData.locale, dcData.locale));

                                        notifyDataSetChanged();
                                    }
                                }
                            });

                            if(dcProperties.clickedDate != null && dcProperties.clickedDate.equals(dcData.recalibrateDates.get(modifiedPosition)))
                            {
                                holder.dcChildLayout.setBackgroundColor(dcProperties.clickedDayBGColor);
                                holder.dcDayValue.setTextColor(dcProperties.clickedDayTextColor);
                                holder.dcDaySubValue.setTextColor(dcProperties.clickedDaySubTextColor);
                            }
                            else if((dcProperties.clickableDates == null || dcProperties.clickableDates.size() <= 0) && !dcProperties.allDatesClickable)
                            {
                                holder.dcChildLayout.setEnabled(false);
                                holder.dcChildLayout.setOnClickListener(null);
                                holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                                holder.dcDayValue.setTextColor(dcProperties.daysTextColor);
                                holder.dcDaySubValue.setTextColor(dcProperties.daysSubTextColor);
                            }
                            else if(dcProperties.allDatesClickable)
                            {
                                if(dcProperties.enableOnlyFutureDates && !DCUtil.checkIfDateIsCurrentOrFuture(date, dcData.datesFormat, dcData.locale))
                                {
                                    holder.dcChildLayout.setAlpha(0.3f);
                                    holder.dcChildLayout.setEnabled(false);
                                    holder.dcChildLayout.setOnClickListener(null);
                                    holder.dcChildLayout.setBackgroundColor(dcProperties.daysLayoutBGColor);
                                    holder.dcDayValue.setTextColor(dcProperties.daysTextColor);
                                    holder.dcDaySubValue.setTextColor(dcProperties.daysSubTextColor);
                                }
                                else
                                {
                                    holder.dcChildLayout.setBackgroundColor(dcProperties.clickableDaysBGColor);
                                    holder.dcDayValue.setTextColor(dcProperties.clickableDaysTextColor);
                                    holder.dcDaySubValue.setTextColor(dcProperties.clickableDaysTextColor);
                                }
                            }
                            else if(dcProperties.clickableDates.contains(date))
                            {
                                holder.dcChildLayout.setBackgroundColor(dcProperties.clickableDaysBGColor);
                                holder.dcDayValue.setTextColor(dcProperties.clickableDaysTextColor);
                                holder.dcDaySubValue.setTextColor(dcProperties.clickableDaysTextColor);
                            }
                            else
                            {
                                holder.dcChildLayout.setBackgroundColor(Color.TRANSPARENT);
                                holder.dcChildLayout.setBackgroundColor(dcProperties.daysLayoutBGColor);
                                holder.dcDayValue.setTextColor(dcProperties.daysTextColor);
                                holder.dcDaySubValue.setTextColor(dcProperties.daysSubTextColor);
                                holder.dcChildLayout.setEnabled(false);
                                holder.dcChildLayout.setOnClickListener(null);
                            }
                        }
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

