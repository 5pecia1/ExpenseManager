package sol_5pecia1.expense_manager.main;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import sol_5pecia1.expense_manager.data.AccountModel;
import sol_5pecia1.expense_manager.data.Money;
import sol_5pecia1.expense_manager.data.PreferenceModel;
import sol_5pecia1.expense_manager.util.CalendarCalculatorKt;

/**
 * Created by sol on 2016-12-20.
 */
//TODO unify variable name
//TODO use set~ method
public class InformationPresenter implements MainContract.InformationListener {
    private MainContract.InformationView view;
    private PreferenceModel preferenceModel;
    private AccountModel accountModel;

    public InformationPresenter(@NonNull MainContract.InformationView view
            , @NonNull PreferenceModel preferenceModel
            , @NonNull AccountModel accountModel) {
        this.view = view;
        this.preferenceModel = preferenceModel;
        this.accountModel = accountModel;
    }

    @Override
    public void refreshView() {
        Calendar today = GregorianCalendar.getInstance();
        Calendar nextSettlement = preferenceModel.getNextSettlement();
        Calendar currentSettlement = preferenceModel.getCurrentSettlement();

        Money todayBudget = getTodayBudget(today, nextSettlement);
        Money todayUsage = accountModel.getSelectedRangeUsage(today, today);
        Money todayLeft = todayBudget.minus(todayUsage);

        Money monthBudget = preferenceModel.getMonthBudget();
        Money monthUsage = accountModel.getSelectedRangeUsage(
                currentSettlement
                , today
        );
        Money monthLeft = monthBudget.minus(monthUsage);

        Money dayAverageSpend
                = accountModel
                .getPreviousDayOfWeekAverage(today.get(Calendar.DAY_OF_WEEK));

        Money planSpend = getPlanSpend(
                today
                , currentSettlement
                , nextSettlement
                , monthBudget
                , monthUsage
        );

        view.setTodayBudget(todayBudget);
        view.setTodayLeft(todayBudget, todayLeft);
        view.setMonthLeft(monthBudget, monthLeft);
        view.setMonthAllSpend(monthUsage);
        view.setDayAverageSpend(today, dayAverageSpend);
        view.setPlanSpend(planSpend);
    }

    @Override
    public void setTodayBudget() {
    }

    @Override
    public void setTodayLeft() {

    }

    @Override
    public void setMonthLeft() {

    }

    @Override
    public void setMonthAllSpend() {

    }

    @Override
    public void setDayAverageSpend() {

    }

    @Override
    public void setPlanSpend() {

    }

    private Money getTodayBudget(Calendar currentCalendar
            , Calendar settlementCalendar) {
        int currentDayOfWeek = currentCalendar.get(Calendar.DAY_OF_WEEK);
        Money expectTodayBudget;

        if (currentDayOfWeek  != Calendar.SATURDAY
                && currentDayOfWeek  != Calendar.SUNDAY) {
            expectTodayBudget = preferenceModel.getWeekdayBudget();
        } else {
            expectTodayBudget = preferenceModel.getWeekendBudget();
        }

        float percentage = getBudgetPercentage(
                expectTodayBudget
                , currentCalendar
                , settlementCalendar
        );

        Calendar currentSettlementCalendar
                = preferenceModel.getCurrentSettlement();
        Calendar lastCountDayCalendar
                = (Calendar) currentCalendar.clone();
        lastCountDayCalendar.add(
                Calendar.DAY_OF_MONTH
                , -1
                );

        // lastCuntDAyCalendar is bigger than currentSettlementCalendar
        Money usageMoney = accountModel.getSelectedRangeUsage(
                currentSettlementCalendar
                , lastCountDayCalendar
        );

        Money monthBudget = preferenceModel.getMonthBudget();
        Money leftBudget
                = monthBudget.minus(usageMoney);

        float today =  leftBudget.getMoney() * percentage;

        Money todayMoney;

        if (today < 0) {
            todayMoney = new Money();
        } else if (today > expectTodayBudget.getMoney()) {
            todayMoney = expectTodayBudget;
        } else {
            todayMoney = new Money((int)today);
        }

        return todayMoney;
    }

    private float getBudgetPercentage(Money budget
            , Calendar currentCalendar
            , Calendar settlementCalendar) {
        int leftWeekday
                = CalendarCalculatorKt
                .getSelectedRangeWeekdayCount(
                        currentCalendar
                        , settlementCalendar
                );
        int leftWeekend
                 = CalendarCalculatorKt
                .getSelectedRangeWeekendCount(
                        currentCalendar
                        , settlementCalendar
                );
        int weekdayBudget = preferenceModel.getWeekdayBudget().getMoney();
        int weekendBudget = preferenceModel.getWeekendBudget().getMoney();

        float percentage = ((float)budget.getMoney())
                / (
                weekdayBudget * leftWeekday
                + weekendBudget* leftWeekend
                );
        return percentage;
    }

    private Money getPlanSpend(Calendar today
            , Calendar currentSettlement
            , Calendar nextSettlement
            , Money monthBudget
            , Money monthUsage) {
        float weekdayPercentage = getBudgetPercentage(
                preferenceModel.getWeekdayBudget()
                , currentSettlement
                , nextSettlement
        );
        float weekendPercentage = getBudgetPercentage(
                preferenceModel.getWeekendBudget()
                , currentSettlement
                , nextSettlement
        );
        Calendar tomorrow = (Calendar)today.clone();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);

        int leftWeekday
                = CalendarCalculatorKt
                .getSelectedRangeWeekdayCount(
                        currentSettlement
                        , tomorrow
                );
        int leftWeekend
                 = CalendarCalculatorKt
                .getSelectedRangeWeekendCount(
                        currentSettlement
                        , tomorrow
                );
        Money budget = new Money((int)(
                monthBudget.getMoney() * weekdayPercentage * leftWeekday
                +  monthBudget.getMoney() * weekendPercentage * leftWeekend));

        return budget.minus(monthUsage);
    }
}
