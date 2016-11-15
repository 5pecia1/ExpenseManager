package sol_5pecia1.expense_manager.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class ChartFragment extends BaseFragment {
    private final static int ICON = R.drawable.ic_more_vert_white_24dp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_chart, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("개발중 입니다.");
        return rootView;
    }

    @Override
    public int getIcon() {
        return ICON;
    }
}
