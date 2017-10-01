package com.vikramezhil.droidcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Droid Calendar Grid View
 *
 * Created by Vikram Ezhil on 01/10/17.
 *
 * email: vikram.ezhil.1988@gmail.com
 */

class DCGridView extends GridView
{
    private boolean expanded = false;

    // MARK: GridView Constructors

    public DCGridView(Context context)
    {
        super(context);
    }

    public DCGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DCGridView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    // MARK: GridView Methods

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if(expanded)
        {
            super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,  MeasureSpec.AT_MOST));

            ViewGroup.LayoutParams params = this.getLayoutParams();

            params.height = this.getMeasuredHeight();
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * Sets the expanded status
     *
     * @param expanded The expanded status
     */
    void setExpanded(boolean expanded)
    {
        this.expanded = expanded;

        invalidate();
    }
}
