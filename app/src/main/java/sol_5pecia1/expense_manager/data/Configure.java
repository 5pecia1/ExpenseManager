package sol_5pecia1.expense_manager.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import sol_5pecia1.expense_manager.view.preference.StringArrayPickerPreference;

/**
 * Created by 5pecia1 on 2016-11-19.
 */
public class Configure implements PreferenceModel {
    private SharedPreferences preferences;

    public Configure(@NonNull SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public int getLeftDay(String settlementKey, String[] dayItems) {
        Calendar currentCalender = GregorianCalendar.getInstance();

        int settlementIndex
                = preferences.getInt(
                settlementKey
                , StringArrayPickerPreference.DEFAULT_VALUE
        );
        int settlementDay = (TextUtils.isDigitsOnly(dayItems[settlementIndex]))
                ? Integer.parseInt(dayItems[settlementIndex])
                : currentCalender.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar settlementCalender = new GregorianCalendar(
                currentCalender.get(Calendar.YEAR)
                , currentCalender.get(Calendar.MONTH)
                , settlementDay
        );

        if ((settlementDay - currentCalender.get(Calendar.DAY_OF_MONTH)) <= 0) {
            settlementCalender.add(Calendar.MONTH, 1);
        }

        int leftDay;
        if (currentCalender.get(Calendar.MONTH)
                == settlementCalender.get(Calendar.MONTH)) {
            leftDay
                    = settlementCalender.get(Calendar.DAY_OF_MONTH)
                    - currentCalender.get(Calendar.DAY_OF_MONTH);
        } else {
            int currentMonthLeft
                    = currentCalender.getActualMaximum(Calendar.DAY_OF_MONTH)
                    - currentCalender.get(Calendar.DAY_OF_MONTH);
            leftDay = currentMonthLeft + settlementDay;
        }
        return leftDay;
    }
}
