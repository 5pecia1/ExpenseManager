package sol_5pecia1.expense_manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sol_5pecia1.expense_manager.view.InputMoneyDialog;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class MainAddFragment extends Fragment implements MainContract.AddView{
    private InputMoneyDialog inputMoneyDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_add, container, false);

        return rootView;
    }

    @Override
    public void showAddDialog() {
        inputMoneyDialog = new InputMoneyDialog(getActivity());
        inputMoneyDialog.show();
    }
}
