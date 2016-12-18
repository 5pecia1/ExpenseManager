package sol_5pecia1.expense_manager.setting;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.view.preference.MoneyEditTextPreference;
import sol_5pecia1.expense_manager.view.preference.MoneyEditTextPreferenceDialogFragmentCompat;
import sol_5pecia1.expense_manager.view.preference.StringArrayPickerPreference;
import sol_5pecia1.expense_manager.view.preference.StringArrayPickerPreferenceDialogFragmentCompat;

/**
 * Created by 5pecia1 on 2016-11-16.
 */
public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment = null;

        if (preference instanceof StringArrayPickerPreference) {
            dialogFragment
                    = StringArrayPickerPreferenceDialogFragmentCompat
                    .newInstance(preference.getKey());
        } else if (preference instanceof MoneyEditTextPreference) {
            dialogFragment
                    = MoneyEditTextPreferenceDialogFragmentCompat
                    .newInstance(preference.getKey());
        }

        if (dialogFragment  != null) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager()
                    , "android.support.v7.preference" +
                            ".PreferenceFragment.DIALOG");
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
