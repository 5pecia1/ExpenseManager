package sol_5pecia1.expense_manager.main.Information;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Calendar;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.data.Account;
import sol_5pecia1.expense_manager.data.AccountModel;
import sol_5pecia1.expense_manager.data.Configure;
import sol_5pecia1.expense_manager.data.Money;
import sol_5pecia1.expense_manager.data.PreferenceModel;
import sol_5pecia1.expense_manager.main.BaseFragment;
import sol_5pecia1.expense_manager.main.MainContract;
import sol_5pecia1.expense_manager.view.MoneyFormatView;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class InformationFragment extends BaseFragment
        implements MainContract.InformationView {
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

    @BindArray(R.array.classification)
    String[] classificationItems;

    @BindArray(R.array.day_of_weeks)
    String[] dayOfWeeks;

    private InformationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_information_main, container, false);
        ButterKnife.bind(this, rootView);

        SharedPreferences preferences
                = PreferenceManager.getDefaultSharedPreferences(getContext());
        Resources resources = getResources();
        PreferenceModel preferenceModel = new Configure(preferences, resources);
        AccountModel accountModel =
                new Account(getActivity(), classificationItems);
        presenter
                = new InformationPresenter(this, preferenceModel, accountModel);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    @Override
    public int getIcon() {
        return ICON;
    }

    @Override
    public void refreshView() {
        presenter.refreshView();
    }

    @Override
    public void setTodayBudget(Money todayBudget) {
        mfvTodayBudget.setMoney(todayBudget);
    }

    @Override
    public void setTodayLeft(Money todayBudget, Money todayLeft) {
        mfvTodayLeft.setMoney(todayLeft);

        int progress = (int)(
                ((float)todayLeft.getMoney())
                        / todayBudget.getMoney()
                        * pbTodayLeft.getMax()
        );

        pbTodayLeft.setProgress(progress);
    }

    @Override
    public void setMonthLeft(Money monthBudget, Money monthLeft) {
        mfvMonthLeft.setMoney(monthLeft);

        int progress
                = (int)(
                ((float)monthLeft.getMoney())
                        / monthBudget.getMoney()
                        * pbTodayLeft.getMax()
        );

        pbMonthLeft.setProgress(progress);
    }

    @Override
    public void setMonthAllSpend(Money monthAllSpend) {
        mfvMonthAllSpend.setMoney(monthAllSpend);
    }

    @Override
    public void setDayAverageSpend(Calendar day, Money averageSpend) {
        String format = mfvDayAverageSpend.getDefaultFormat();
        String dayOfWeek = dayOfWeeks[day.get(Calendar.DAY_OF_WEEK) - 1];

        format = String.format(
                format
                , dayOfWeek
                , MoneyFormatView.STRING_FORMAT_ARGUMENT
        );
        mfvDayAverageSpend.setMoney(averageSpend, format);
    }

    @Override
    public void setPlanSpend(Money planSpend) {
        String format = mfvPlanSpend.getDefaultFormat();
        String compare;

        if (planSpend.getMoney() < 0) {
            compare = getString(R.string.more);
            planSpend = new Money(-planSpend.getMoney());
        } else {
            compare = getString(R.string.less);
        }

        format = String.format(
                format
                , MoneyFormatView.STRING_FORMAT_ARGUMENT
                , compare
        );

        mfvPlanSpend.setMoney(planSpend, format);
    }
}
