package sol_5pecia1.expense_manager.main;

import sol_5pecia1.expense_manager.data.Money;

/**
 * Created by 5pecia1 on 2016-11-12.
 */
public interface MainContract {
    interface MainView {
        void setLeftDay(String day);
    }
    interface InformationView {
        void setTodayBudget(Money todayBudget);
        void setTodayLeft(Money todayBudget, Money todayLeft);
        void setMonthLeft(Money monthBudget, Money monthLeft);
        void setMonthAllSpend(Money monthAllSpend);
        void setDayAverageSpend(String day, Money averageSpend);
        void setPlanSpend(Money planSpend, String compare);
    }
    interface AddView {
        void initView();
    }

    interface MainActionListener {
        void setLeftDay(String settlementKey, String[] dayItems);
    }
}
