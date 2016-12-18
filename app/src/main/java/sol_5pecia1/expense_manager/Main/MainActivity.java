package sol_5pecia1.expense_manager.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.setting.SettingActivity;

public class MainActivity extends AppCompatActivity implements MainContract.MainView{
    private final static List<BaseFragment> MAIN_FRAGMENT = Arrays.asList(
            new ChartFragment()
            , new InformationFragment()
            , new AddFragment()
    );

    private final static int FIRST_FRAGMENT_LOCATION = 1;

    @BindView(R.id.left_day)
    TextView tvLeftDay;

    @BindView(R.id.container)
    ViewPager viewPager;

    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setLeftDay("00");

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(FIRST_FRAGMENT_LOCATION);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        for(int i = 0; i < MAIN_FRAGMENT.size(); i++) {
            tabLayout.getTabAt(i).setIcon(MAIN_FRAGMENT.get(i).getIcon());
        }

        initListener();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
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
        tvLeftDay.setText(getApplicationContext().getString(R.string.left_day, day));
    }


    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment currentFragment = sectionsPagerAdapter.getItem(position);

                if (currentFragment instanceof AddFragment) {
                    AddFragment addFragment = (AddFragment) currentFragment;
                    addFragment.initView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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
}