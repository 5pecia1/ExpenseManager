package sol_5pecia1.expense_manager.view.preference;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

/**
 * Created by 5pecia1 on 2016-11-19.
 */
public class StringArrayPickerPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    private final static String ITEMS_KEY = "items";
    private NumberPicker picker;

    public static StringArrayPickerPreferenceDialogFragmentCompat newInstance(
            @NonNull StringArrayPickerPreference preference
            , @NonNull String[] items) {
        StringArrayPickerPreferenceDialogFragmentCompat fragment = new StringArrayPickerPreferenceDialogFragmentCompat();
        Bundle bundle = new Bundle();
        bundle.putString(PreferenceDialogFragmentCompat.ARG_KEY, preference.getKey());
        bundle.putStringArray(StringArrayPickerPreferenceDialogFragmentCompat.ITEMS_KEY, items);
        fragment.setArguments(bundle);//TODO 필요한 값 설정하고 getArguments로 얻기, getpreference로 값 가져오기

        return fragment;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);

        String[] items = getArguments().getStringArray(StringArrayPickerPreferenceDialogFragmentCompat.ITEMS_KEY);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());

        picker.setLayoutParams(layoutParams);
        picker.setMinValue(0);
        picker.setMaxValue(items.length - 1);
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(items);

        builder.setView(picker);

    }

    public StringArrayPickerPreference getStringArrayPickerPreference() {
        return (StringArrayPickerPreference)super.getPreference();
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            String[] items = getArguments().getStringArray(StringArrayPickerPreferenceDialogFragmentCompat.ITEMS_KEY);
            String value = items != null ? items[picker.getValue()] : null;
            StringArrayPickerPreference preference = getStringArrayPickerPreference();

            if (preference.callChangeListener(value)) {
                //TODO
            }
        }
    }
}