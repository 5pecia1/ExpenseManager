package sol_5pecia1.expense_manager.main;

import android.support.annotation.NonNull;

import java.util.Calendar;

import sol_5pecia1.expense_manager.data.Money;

/**
 * Created by 5pecia1 on 2016-11-12.
 */
public interface MainContract {
    interface MainView {
        void setLeftDay(String day);
    }
    interface InformationView {
        void refreshView();
        void setTodayBudget(Money todayBudget);
        void setTodayLeft(Money todayBudget, Money todayLeft);
        void setMonthLeft(Money monthBudget, Money monthLeft);
        void setMonthAllSpend(Money monthAllSpend);
        void setDayAverageSpend(Calendar day, Money averageSpend);
        void setPlanSpend(Money planSpend);
    }
    interface AddView {
        void initView();
        void save();
    }

    interface MainActionListener {
        void setLeftDay();
    }

    interface InformationListener {
        void refreshView();
        void setTodayBudget();
        void setTodayLeft();
        void setMonthLeft();
        void setMonthAllSpend();
        void setDayAverageSpend();
        void setPlanSpend();
    }

    interface AddListenter {
        void save(@NonNull Money money, @NonNull String classification
            , @NonNull Calendar saveDate, @NonNull String besides);
    }

    interface ChartListener {

    }
}
