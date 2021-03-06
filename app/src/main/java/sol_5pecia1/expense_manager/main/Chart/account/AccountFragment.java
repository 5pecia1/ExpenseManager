package sol_5pecia1.expense_manager.main.Chart.account;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.main.BaseFragment;
import sol_5pecia1.expense_manager.main.MainContract;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class AccountFragment
        extends BaseFragment
        implements MainContract.AccountView {
    private final static int ICON = R.drawable.ic_more_vert_white_24dp;

    @BindView(R.id.accountView)
    RecyclerView accountView;

    private AccountAdapter accountAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_chart, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        accountAdapter = new AccountAdapter();

        accountView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getIcon() {
        return ICON;
    }


}
