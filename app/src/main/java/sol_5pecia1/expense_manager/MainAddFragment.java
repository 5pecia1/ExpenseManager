package sol_5pecia1.expense_manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class MainAddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_add, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("[추가] 개발중...");
        return rootView;
    }
}
