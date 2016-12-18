package sol_5pecia1.expense_manager.view.preference;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.view.View;
import android.widget.NumberPicker;

import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-19.
 */
public class StringArrayPickerPreferenceDialogFragmentCompat
        extends PreferenceDialogFragmentCompat {
    NumberPicker numberPicker;

    public static StringArrayPickerPreferenceDialogFragmentCompat newInstance(
            @NonNull String key) {
        final StringArrayPickerPreferenceDialogFragmentCompat fragment
                = new StringArrayPickerPreferenceDialogFragmentCompat();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        numberPicker = (NumberPicker)view.findViewById(R.id.numberPicker);

        if (numberPicker == null) {
            throw new IllegalStateException("Dialog view must contain" +
                " a NumberPicker with id 'edit'");
        }

        int selectedIndex = -1;
        String[] items = null;
        DialogPreference preference = getPreference();
        if (preference instanceof StringArrayPickerPreference) {
            selectedIndex
                    = ((StringArrayPickerPreference) preference)
                    .getSelectedIndex();
            items = ((StringArrayPickerPreference) preference).getItems();
        }

        if (items  != null) {
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(items.length - 1);
            numberPicker.setWrapSelectorWheel(false);
            numberPicker.setDisplayedValues(items);
        }


        if (selectedIndex  != -1) {
            numberPicker.setValue(selectedIndex);
        }
    }
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            DialogPreference preference = getPreference();

            if (preference instanceof StringArrayPickerPreference) {
                StringArrayPickerPreference stringArrayPickerPreference
                        = (StringArrayPickerPreference) preference;

                int value = numberPicker.getValue();

                if (preference.callChangeListener(value)) {
                    stringArrayPickerPreference.setSelectedIndex(value);
                }
            }
        }
    }
}
