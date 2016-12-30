package sol_5pecia1.expense_manager.data;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by sol on 2016-12-21.
 */
public interface AccountModel {
    @IntDef({
            Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY
            , Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY
            , Calendar.SATURDAY
    })
    @interface DayOfWeek{}

    long addAccount(@NonNull Money money, @NonNull String classification
            , @NonNull Calendar saveDate, @NonNull String besides);
    @NonNull
    Money getSelectedRangeUsage(@NonNull Calendar start, @NonNull Calendar end);
    @NonNull
    Money getPreviousDayOfWeekAverage(@DayOfWeek int dayOfWeek);
}
