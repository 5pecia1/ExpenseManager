package sol_5pecia1.expense_manager.main.Add;

import android.support.annotation.NonNull;

import java.util.Calendar;

import sol_5pecia1.expense_manager.data.AccountModel;
import sol_5pecia1.expense_manager.data.Money;
import sol_5pecia1.expense_manager.main.MainContract;

/**
 * Created by sol on 2016-12-21.
 */

public class AddPresenter implements MainContract.AddListenter {
    private MainContract.AddView view;
    private AccountModel accountModel;

    public AddPresenter(@NonNull MainContract.AddView view
            , @NonNull AccountModel accountModel) {
        this.view = view;
        this.accountModel = accountModel;
    }

    @Override
    public void save(@NonNull Money money, @NonNull String classification
            , @NonNull Calendar saveDate, @NonNull String besides) {
        accountModel.addAccount(money, classification, saveDate, besides);
    }
}
