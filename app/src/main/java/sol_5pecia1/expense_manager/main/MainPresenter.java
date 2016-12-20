package sol_5pecia1.expense_manager.main;

import android.support.annotation.NonNull;

import sol_5pecia1.expense_manager.data.PreferenceModel;

/**
 * Created by sol on 2016-12-19.
 */

public class MainPresenter implements MainContract.MainActionListener {
    private MainContract.MainView mainView;
    private PreferenceModel preferenceModel;

    public MainPresenter(@NonNull MainContract.MainView mainView
            , @NonNull PreferenceModel preferenceModel) {
        this.mainView = mainView;
        this.preferenceModel = preferenceModel;
    }
    @Override
    public void setLeftDay(String settlementKey, String[] dayItems) {
        mainView.setLeftDay(
                String.valueOf(
                        preferenceModel.getLeftDay(settlementKey, dayItems)
                )
        );
    }
}
