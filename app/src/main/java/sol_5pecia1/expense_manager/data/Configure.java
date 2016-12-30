package sol_5pecia1.expense_manager.data;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.util.CalendarCalculatorKt;
import sol_5pecia1.expense_manager.view.preference.StringArrayPickerPreference;

/**
 * Created by 5pecia1 on 2016-11-19.
 */
public class Configure implements PreferenceModel {
    private SharedPreferences preferences;
    private Resources resources;

    public Configure(@NonNull SharedPreferences preferences
            , @NonNull Resources resources) {
        this.preferences = preferences;
        this.resources = resources;
    }

    @Override
    public Money getMonthBudget() {
        String budgetKey
                = resources.getString(R.string.preference_month_budget);
        String budget
                = preferences.getString(budgetKey
                , new Money().toString());
        return new Money(budget);
    }

    @Override
    public Money getWeekdayBudget() {
        String weekdayBudgetKey
                = resources.getString(R.string.preference_weekday_budget);
        String weekdayBudget
                = preferences.getString(weekdayBudgetKey
                , new Money().toString()
        );

        return new Money(weekdayBudget);
    }

    @Override
    public Money getWeekendBudget() {
        String weekendBudgetKey
                = resources.getString(R.string.preference_weekend_budget);
        String weekendBudget
                = preferences.getString(weekendBudgetKey
                , new Money().toString());

        return new Money(weekendBudget);
    }

    @Override
    public int getLeftDay() {
        return CalendarCalculatorKt.getSelectedRangeDayCount(
                GregorianCalendar.getInstance()
                , getNextSettlement()
        );
    }

    @Override
    public Calendar getNextSettlement() {
        Calendar currentCalendar = GregorianCalendar.getInstance();

        String[] dayItems = resources.getStringArray(R.array.days);
        int settlementIndex
                = preferences.getInt(
                resources.getString(R.string.preference_settlement_day)
                , StringArrayPickerPreference.DEFAULT_VALUE
        );
        boolean isDigitsOnly
                = TextUtils.isDigitsOnly(dayItems[settlementIndex]);
        int currentActualMaximum
                = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int settlementYear = currentCalendar.get(Calendar.YEAR);
        int settlementMonth = currentCalendar.get(Calendar.MONTH);
        int settlementDay = (isDigitsOnly)
                ? Integer.parseInt(dayItems[settlementIndex])
                : currentActualMaximum;

        if (settlementMonth == Calendar.FEBRUARY
                && settlementDay > currentActualMaximum) {
            settlementDay = currentActualMaximum;
        }

        if ((settlementDay - currentCalendar.get(Calendar.DAY_OF_MONTH)) <= 0) {
             if (settlementMonth < Calendar.DECEMBER) {
                 settlementMonth++;
             } else {
                 settlementMonth = Calendar.JANUARY;
                 settlementYear++;
             }
        }

        Calendar settlementCalender = new GregorianCalendar(
                settlementYear
                , settlementMonth
                , 1
        );

        if ( !isDigitsOnly) {
            settlementDay
                    = settlementCalender
                    .getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        settlementCalender.add(Calendar.DAY_OF_MONTH
                , settlementDay - 1);

        return settlementCalender;
    }

    @Override
    public Calendar getCurrentSettlement() {
        Calendar currentCalendar = GregorianCalendar.getInstance();

        String[] dayItems = resources.getStringArray(R.array.days);
        int settlementIndex
                = preferences.getInt(
                resources.getString(R.string.preference_settlement_day)
                , StringArrayPickerPreference.DEFAULT_VALUE
        );
        boolean isDigitsOnly
                = TextUtils.isDigitsOnly(dayItems[settlementIndex]);
        int currentActualMaximum
                = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int settlementYear = currentCalendar.get(Calendar.YEAR);
        int settlementMonth = currentCalendar.get(Calendar.MONTH);
        int settlementDay = (isDigitsOnly)
                ? Integer.parseInt(dayItems[settlementIndex])
                : currentActualMaximum;

        if (settlementMonth == Calendar.FEBRUARY
                && settlementDay > currentActualMaximum) {
            settlementDay = currentActualMaximum;
        }

        if ((settlementDay - currentCalendar.get(Calendar.DAY_OF_MONTH)) > 0) {
             if (settlementMonth > Calendar.JANUARY) {
                 settlementMonth--;
             } else {
                 settlementMonth = Calendar.DECEMBER;
                 settlementYear--;
             }
        }

        Calendar settlementCalender = new GregorianCalendar(
                settlementYear
                , settlementMonth
                , 1
        );

        if ( !isDigitsOnly) {
            settlementDay
                    = settlementCalender
                    .getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        settlementCalender.add(Calendar.DAY_OF_MONTH
                , settlementDay - 1);

        return settlementCalender;
    }
}
