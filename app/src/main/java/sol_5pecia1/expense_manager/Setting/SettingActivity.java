package sol_5pecia1.expense_manager.setting;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-15.
 */
public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.settingList)
    RecyclerView rvSettingList;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);


    }
}
