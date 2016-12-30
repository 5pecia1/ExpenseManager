package sol_5pecia1.expense_manager.data;

import java.util.Calendar;

/**
 * Created by sol on 2016-12-20.
 */

public interface PreferenceModel {
    Money getMonthBudget();
    Money getWeekdayBudget();
    Money getWeekendBudget();
    int getLeftDay();
    Calendar getNextSettlement();
    Calendar getCurrentSettlement();
}
