package sol_5pecia1.expense_manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class MainAddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_add, container, false);

        return rootView;
    }

    public void showAddDialog() {
        Toast.makeText(getContext(), "show!", Toast.LENGTH_SHORT).show(); //null point가 뜬다..
    }
}
