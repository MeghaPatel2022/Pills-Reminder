package devs.mulham.horizontalcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.recyclerview.widget.RecyclerView;

import devs.mulham.horizontalcalendar.adapter.HorizontalCalendarBaseAdapter;
import devs.mulham.horizontalcalendar.model.CalendarItemStyle;
import devs.mulham.horizontalcalendar.model.HorizontalCalendarConfig;

/**
 * See {devs.mulham.horizontalcalendar.R.styleable#HorizontalCalendarView HorizontalCalendarView Attributes}
 *
 * @author Mulham-Raee
 * @since v1.0.0
 */
public class HorizontalCalendarView extends RecyclerView {

    private final float FLING_SCALE_DOWN_FACTOR = 0.5f;
    private CalendarItemStyle defaultStyle;
    private CalendarItemStyle selectedItemStyle;
    private HorizontalCalendarConfig config;
    private int shiftCells;

    public HorizontalCalendarView(Context context) {
        super(context);
    }

    public HorizontalCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HorizontalCalendarView,
                0, 0);

        try {
            int textColorNormal = context.getResources().getColor(android.R.color.black);
            int colorTopText = context.getResources().getColor(android.R.color.black);
            int colorMiddleText = context.getResources().getColor(android.R.color.black);
            int colorBottomText = context.getResources().getColor(android.R.color.black);

            int textColorSelected = context.getResources().getColor(android.R.color.white);
            int colorTopTextSelected = context.getResources().getColor(android.R.color.white);
            int colorMiddleTextSelected = context.getResources().getColor(android.R.color.white);
            int colorBottomTextSelected = context.getResources().getColor(android.R.color.white);
            Drawable selectedDateBackground = context.getResources().getDrawable(R.drawable.select_without_bg_1);
            Drawable dateBackground = context.getResources().getDrawable(R.drawable.not_selct_bg_1);

            int selectorColor = a.getColor(R.styleable.HorizontalCalendarView_selectorColor, fetchAccentColor());
            float sizeTopText = getRawSizeValue(a, R.styleable.HorizontalCalendarView_sizeTopText,
                    HorizontalCalendarConfig.DEFAULT_SIZE_TEXT_TOP);
            float sizeMiddleText = getRawSizeValue(a, R.styleable.HorizontalCalendarView_sizeMiddleText,
                    HorizontalCalendarConfig.DEFAULT_SIZE_TEXT_MIDDLE);
            float sizeBottomText = getRawSizeValue(a, R.styleable.HorizontalCalendarView_sizeBottomText,
                    HorizontalCalendarConfig.DEFAULT_SIZE_TEXT_BOTTOM);


            defaultStyle = new CalendarItemStyle(colorTopText, colorMiddleText, colorBottomText, dateBackground);
            selectedItemStyle = new CalendarItemStyle(colorTopTextSelected, colorMiddleTextSelected, colorBottomTextSelected, selectedDateBackground);
            config = new HorizontalCalendarConfig(sizeTopText, sizeMiddleText, sizeBottomText, selectorColor);

        } finally {
            a.recycle();
        }

    }

    /**
     * get the raw value from a complex value ( Ex: complex = 14sp, returns 14)
     */
    private float getRawSizeValue(TypedArray a, int index, float defValue) {
        TypedValue outValue = new TypedValue();
        boolean result = a.getValue(index, outValue);
        if (!result) {
            return defValue;
        }

        return TypedValue.complexToFloat(outValue.data);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX *= FLING_SCALE_DOWN_FACTOR; // (between 0 for no fling, and 1 for normal fling, or more for faster fling).

        return super.fling(velocityX, velocityY);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (isInEditMode()) {
            setMeasuredDimension(widthSpec, 150);
        } else {
            super.onMeasure(widthSpec, heightSpec);
        }

    }

    public float getSmoothScrollSpeed() {
        return getLayoutManager().getSmoothScrollSpeed();
    }

    public void setSmoothScrollSpeed(float smoothScrollSpeed) {
        getLayoutManager().setSmoothScrollSpeed(smoothScrollSpeed);
    }

    @Override
    public HorizontalCalendarBaseAdapter getAdapter() {
        return (HorizontalCalendarBaseAdapter) super.getAdapter();
    }

    @Override
    public HorizontalLayoutManager getLayoutManager() {
        return (HorizontalLayoutManager) super.getLayoutManager();
    }

    private int fetchAccentColor() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public void applyConfigFromLayout(HorizontalCalendar horizontalCalendar) {
        horizontalCalendar.getConfig().setupDefaultValues(config);
        horizontalCalendar.getDefaultStyle().setupDefaultValues(defaultStyle);
        horizontalCalendar.getSelectedItemStyle().setupDefaultValues(selectedItemStyle);

        // clean, not needed anymore
        config = null;
        defaultStyle = null;
        selectedItemStyle = null;

        this.shiftCells = horizontalCalendar.getNumberOfDatesOnScreen() / 2;
//        Log.e("LLLL_Shift: ", horizontalCalendar.getNumberOfDatesOnScreen() + "  shift: " + shiftCells);
    }

    /**
     * @return position of selected date on center of screen
     */
    public int getPositionOfCenterItem() {
        final HorizontalLayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            return -1;
        } else {
            final int firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (firstVisiblePosition == -1) {
                return -1;
            } else {
//                Log.e("LLLL_Shift: ", "firstVisible: " + firstVisiblePosition + "  " + (firstVisiblePosition + shiftCells - 3));
                return firstVisiblePosition + shiftCells - 3;
            }
        }
    }
}
