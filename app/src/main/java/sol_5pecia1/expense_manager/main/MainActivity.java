package sol_5pecia1.expense_manager.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.freeze.horizontalrefreshlayout.lib.HorizontalRefreshLayout;
import com.freeze.horizontalrefreshlayout.lib.RefreshCallBack;
import com.freeze.horizontalrefreshlayout.lib.RefreshHeader;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.data.Configure;
import sol_5pecia1.expense_manager.data.PreferenceModel;
import sol_5pecia1.expense_manager.main.Add.AddFragment;
import sol_5pecia1.expense_manager.main.Chart.account.AccountFragment;
import sol_5pecia1.expense_manager.main.Information.InformationFragment;
import sol_5pecia1.expense_manager.setting.SettingActivity;

public class MainActivity extends AppCompatActivity
        implements MainContract.MainView {
    private final static List<BaseFragment> MAIN_FRAGMENT = Arrays.asList(
            new AccountFragment()
            , new InformationFragment()
            , new AddFragment()
    );

    private final static int FIRST_FRAGMENT_POSITION = 1;
    private final static int ADD_FRAGMENT_POSITION = 2;

    @BindView(R.id.left_day)
    TextView tvLeftDay;

    @BindView(R.id.refresh)
    HorizontalRefreshLayout refreshLayout;

    @BindView(R.id.container)
    ViewPager viewPager;

    private SectionsPagerAdapter sectionsPagerAdapter;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences preferences
                = PreferenceManager.getDefaultSharedPreferences(this);
        Resources resources = getResources();
        PreferenceModel preferenceModel = new Configure(preferences, resources);
        presenter = new MainPresenter(this, preferenceModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter
                = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(FIRST_FRAGMENT_POSITION);

        refreshLayout.setEnable(true);
        refreshLayout.setRefreshMode(
                HorizontalRefreshLayout.MODE_UNDER_FOLLOW_DRAG
        );
        refreshLayout.setRefreshHeader(new SaveRefresh()
                , HorizontalRefreshLayout.END
        );

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < MAIN_FRAGMENT.size(); i++) {
            tabLayout.getTabAt(i).setIcon(MAIN_FRAGMENT.get(i).getIcon());
        }

        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setLeftDay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLeftDay(String day) {
        tvLeftDay.setText(
                getApplicationContext().getString(R.string.left_day, day)
        );
    }


    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position
                    , float positionOffset
                    , int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment currentFragment = sectionsPagerAdapter.getItem(position);

                if (currentFragment instanceof InformationFragment) {
                    ((InformationFragment) currentFragment).refreshView();
                    View currentFocus = getCurrentFocus();
                    if (currentFocus  != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
                } else if (currentFragment instanceof AddFragment) {
                    ((AddFragment) currentFragment).initView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        refreshLayout.setRefreshCallback(new RefreshCallBack() {
            @Override
            public void onLeftRefreshing() {

            }

            @Override
            public void onRightRefreshing() {
                refreshLayout.postDelayed(() -> {
                    refreshLayout.onRefreshComplete();
                    viewPager.setCurrentItem(FIRST_FRAGMENT_POSITION);
                }, 100);
            }
        });
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MAIN_FRAGMENT.get(position);
        }

        @Override
        public int getCount() {
            return MAIN_FRAGMENT.size();
        }
    }

    private class SaveRefresh implements RefreshHeader {

        private TextView textView;

        @NonNull
        @Override
        public View getView(ViewGroup viewGroup) {
            View view
                    = LayoutInflater.from(getBaseContext())
                    .inflate(com.freeze.horizontalrefreshlayout.lib
                            .R.layout.widget_refresh_header
                            , viewGroup
                            , false
                    );
            textView
                    = (TextView) view
                    .findViewById(com.freeze.horizontalrefreshlayout.lib
                            .R.id.text
                    );

            return view;
        }

        @Override
        public void onStart(int i, View view) {
            textView.setText(R.string.save_finish);
        }

        @Override
        public void onDragging(float v, float v1, View view) {
            textView.setText(R.string.want_save);
        }

        @Override
        public void onReadyToRelease(View view) {
            textView.setText(R.string.save);
        }

        @Override
        public void onRefreshing(View view) {
            textView.setText(R.string.saving);

            MainContract.AddView addView
                    = (MainContract.AddView)
                    MAIN_FRAGMENT.get(ADD_FRAGMENT_POSITION);
            addView.save();
        }
    }
}