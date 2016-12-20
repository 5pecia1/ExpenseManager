package sol_5pecia1.expense_manager.main;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import sol_5pecia1.expense_manager.data.Money;
import sol_5pecia1.expense_manager.data.PreferenceModel;

/**
 * Created by sol on 2016-12-20.
 */

public class InformationPresenter implements MainContract.InformationListener {
    private MainContract.InformationView view;
    private PreferenceModel preferenceModel;

    public InformationPresenter(@NonNull MainContract.InformationView view
            , @NonNull PreferenceModel preferenceModel) {
        this.view = view;
        this.preferenceModel = preferenceModel;
    }

    @Override
    public void setTodayBudget() {
        view.setTodayBudget(getTodayBudget());
    }

    private Money getTodayBudget() {
        Calendar currentCalender = GregorianCalendar.getInstance();
        int currentDayOfWeek = currentCalender.get(Calendar.DAY_OF_WEEK);
        Money todayBudget;

        if (currentDayOfWeek  != Calendar.SATURDAY
                && currentDayOfWeek  != Calendar.SUNDAY) {
            todayBudget = preferenceModel.getWeekdayBudget();
        } else {
            todayBudget = preferenceModel.getWeekendBudget();
        }

        return todayBudget;
    }
}
