package com.vikramezhil.droidcalendarviewexample;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.vikramezhil.droidcalendarview.DCView;
import com.vikramezhil.droidcalendarview.OnDCListener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Droid Calendar Example Activity
 *
 * Created by Vikram Ezhil on 01/10/17.
 *
 * email: vikram.ezhil.1988@gmail.com
 */

public class ActivityDC extends Activity
{
    private final String TAG = "ActivityDC";
    private final String MY_FORMAT = "MMMM YYYY";
    private final String D_FORMAT = "d";
    private final String DMY_FORMAT = "d/M/YYYY";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dc);

        final DCView dcView = findViewById(R.id.dcView);
        final int calendarDataPos = (new Random()).nextInt(50);
        if(calendarDataPos % 2 == 0)
        {
            dcView.setDCData(12, MY_FORMAT, D_FORMAT, DMY_FORMAT, true, Locale.US);
            dcView.setDCAllDatesClickable(true);
            dcView.setDCEnableOnlyFutureDates(true);
        }
        else
        {
            dcView.setDCData("Januar 2018", "April 2018", MY_FORMAT, MY_FORMAT, D_FORMAT, DMY_FORMAT, Locale.GERMAN);
            dcView.setDCClickableDates(Arrays.asList("2/1/2018", "25/1/2018", "5/2/2018", "6/2/2018", "27/2/2018", "1/3/2018", "10/3/2018", "31/3/2018", "1/4/2018", "2/4/2018"));
            dcView.setDCClickedDate("2/1/2018", true);
            dcView.setDCAllDatesClickable(false);
            dcView.setDCEnableOnlyFutureDates(false);
        }

        dcView.setDCExpanded(true);
        dcView.setDCHeaderTextSize(22f);
        dcView.setDCDaysHeaderTextSize(20f);
        dcView.setDCDaysTextSize(20f);
        dcView.setDCPreviousButtonSize(30f);
        dcView.setDCNextButtonSize(30f);
        dcView.setOnDCListener(new OnDCListener() {

            public void onDCScreenData(List<String> onScreenDates)
            {
                int[] primaryColors = new int[]{Color.parseColor("#673AB7"), Color.parseColor("#00BCD4"), Color.parseColor("#4CAF50"), Color.parseColor("#9C27B0")};
                int[] secondaryColors = new int[]{Color.parseColor("#D1C4E9"), Color.parseColor("#B2EBF2"), Color.parseColor("#C8E6C9"), Color.parseColor("#E1BEE7")};
                int[] contrastColors = new int[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
                int randomPos = (new Random()).nextInt(primaryColors.length);
                if(randomPos < primaryColors.length)
                {
                    if(calendarDataPos % 2 == 0)
                    {
                        dcView.setDCHeaderCapitalize();
                    }
                    else
                    {
                        dcView.setDCHeaderBGColor(primaryColors[randomPos]);
                        dcView.setDCHeaderTextStyle(Typeface.BOLD);
                        dcView.setDCHeaderTextColor(contrastColors[randomPos]);
                        dcView.setDCPreviousButtonColor(contrastColors[randomPos]);
                        dcView.setDCNextButtonColor(contrastColors[randomPos]);
                        dcView.setDCDaysHeadersLayoutBGColor(secondaryColors[randomPos]);
                        dcView.setDCDaysLayoutBGColor(contrastColors[randomPos]);
                        dcView.setDCClickableDaysBGColor(secondaryColors[randomPos]);
                        dcView.setDCClickableDaysTextColor(contrastColors[randomPos]);
                        dcView.setDCClickedDayBGColor(primaryColors[randomPos]);
                        dcView.setDCClickedDayTextColor(contrastColors[randomPos]);
                        dcView.setDCRowSeparatorColor(secondaryColors[randomPos]);
                        dcView.setDCDaysHeadersTextColor(primaryColors[randomPos]);
                        dcView.setDCDaysTextColor(primaryColors[randomPos]);
                        dcView.setDCDaysHeaderTextStyle(Typeface.NORMAL);
                        dcView.setDCDaysTextStyle(Typeface.NORMAL);
                        dcView.setDCShowRowSeparator(randomPos % 2 == 0);
                    }
                }
            }

            public void onDCDateClicked(String date)
            {
                Log.wtf(TAG, "Clicked date = " + date);
            }
        });
    }
}
