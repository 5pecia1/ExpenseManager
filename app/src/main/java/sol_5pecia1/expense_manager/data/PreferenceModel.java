package sol_5pecia1.expense_manager.data;

/**
 * Created by sol on 2016-12-20.
 */

public interface PreferenceModel {
    Money getWeekdayBudget();
    Money getWeekendBudget();
    int getLeftDay();
}
