package sol_5pecia1.expense_manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import sol_5pecia1.expense_manager.view.MoneyFormatView;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class MainInformationFragment extends Fragment {

    @BindView(R.id.today_budget)
    MoneyFormatView mfvTodayBudget;

    @BindView(R.id.today_left)
    MoneyFormatView mfvTodayLeft;

    @BindView(R.id.today_left_progress)
    ProgressBar pTodayLeft;

    @BindView(R.id.month_left)
    MoneyFormatView mfvMonthLeft;

    @BindView(R.id.month_left_progress)
    ProgressBar pMonthLeft;

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

        ButterKnife.bind(rootView);

        return rootView;
    }
}
