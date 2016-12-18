package sol_5pecia1.expense_manager.view.preference;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreferenceDialogFragmentCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import sol_5pecia1.expense_manager.data.Money;

/**
 * Created by sol on 2016-12-18.
 */

public class MoneyEditTextPreferenceDialogFragmentCompat
        extends EditTextPreferenceDialogFragmentCompat {
    private EditText editText;

    public static MoneyEditTextPreferenceDialogFragmentCompat newInstance(
            String key) {
        final MoneyEditTextPreferenceDialogFragmentCompat fragment
               = new MoneyEditTextPreferenceDialogFragmentCompat();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        editText = (EditText) view.findViewById(android.R.id.edit);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(Money.MAX_LENGTH)
        });

        editText.setSelection(editText.getText().length());
    }
}
