package sol_5pecia1.expense_manager.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.data.Money;
import sol_5pecia1.expense_manager.view.MoneyFormatView;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class InformationFragment extends BaseFragment implements MainContract.InformationView{
    private final static int ICON = R.drawable.ic_assignment_white_24dp;

    @BindView(R.id.today_budget)
    MoneyFormatView mfvTodayBudget;

    @BindView(R.id.today_left)
    MoneyFormatView mfvTodayLeft;

    @BindView(R.id.today_left_progress)
    ProgressBar pbTodayLeft;

    @BindView(R.id.month_left)
    MoneyFormatView mfvMonthLeft;

    @BindView(R.id.month_left_progress)
    ProgressBar pbMonthLeft;

    @BindView(R.id.month_all_spend)
    MoneyFormatView mfvMonthAllSpend;

    @BindView(R.id.day_average_spend)
    MoneyFormatView mfvDayAverageSpend;

    @BindView(R.id.plan_spend)
    MoneyFormatView mfvPlanSpend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_information_main, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public int getIcon() {
        return ICON;
    }

    @Override
    public void setTodayBudget(Money todayBudget) {
        mfvTodayBudget.setMoney(todayBudget);
    }

    @Override
    public void setTodayLeft(Money todayBudget, Money todayLeft) {
        mfvTodayLeft.setMoney(todayBudget);

        Money minusMoney = todayBudget.minus(todayLeft);
        int progress = (int)(minusMoney.getMoney() * pbTodayLeft.getMax() / todayLeft.getMoney());

        pbTodayLeft.setProgress(progress);
    }

    @Override
    public void setMonthLeft(Money monthBudget, Money monthLeft) {
        mfvTodayLeft.setMoney(monthBudget);

        Money minusMoney = monthBudget.minus(monthLeft);
        int progress = (int)(minusMoney.getMoney() * pbTodayLeft.getMax() / monthLeft.getMoney());

        pbTodayLeft.setProgress(progress);
    }

    @Override
    public void setMonthAllSpend(Money monthAllSpend) {
        mfvMonthAllSpend.setMoney(monthAllSpend);
    }

    @Override
    public void setDayAverageSpend(String day, Money averageSpend) {
        String format = mfvDayAverageSpend.getDefaultFormat();
        format = String.format(format, day, MoneyFormatView.STRING_FORMAT_ARGUMENT);
        mfvDayAverageSpend.setMoney(averageSpend, format);
    }

    @Override
    public void setPlanSpend(Money planSpend, String compare) {
        String format = mfvPlanSpend.getDefaultFormat();
        format = String.format(format, MoneyFormatView.STRING_FORMAT_ARGUMENT, compare);
        mfvPlanSpend.setMoney(planSpend, format);
    }
}
