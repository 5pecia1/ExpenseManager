package sol_5pecia1.expense_manager.view.preference;

import android.content.Context;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.preference.AndroidResources;
import android.support.v7.preference.EditTextPreference;
import android.text.TextUtils;
import android.util.AttributeSet;

import sol_5pecia1.expense_manager.data.Money;

/**
 * Created by sol on 2016-12-18.
 */

public class MoneyEditTextPreference extends EditTextPreference {
    private final static String DEFAULT_SUMMARY = "%s";

    private String summaryFormat;

    public MoneyEditTextPreference(Context context) {
        this(context, null);
    }
    public MoneyEditTextPreference(Context context, AttributeSet attrs) {
        this(context
                , attrs
                , TypedArrayUtils.getAttr(
                        context
                        , android.support.v7.preference
                                .R.attr.editTextPreferenceStyle
                        , AndroidResources.ANDROID_R_EDITTEXT_PREFERENCE_STYLE
                )
        );
    }
    public MoneyEditTextPreference(Context context
            , AttributeSet attrs
            , int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }
    public MoneyEditTextPreference(Context context, AttributeSet attrs
            , int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        CharSequence summary = getSummary();

        summaryFormat = (summary == null)
                ? MoneyEditTextPreference.DEFAULT_SUMMARY
                : summary.toString();
    }

    public Money getMoney() {
        return new Money(getText());
    }

    @Override
    public void setText(String text) {
        super.setText(TextUtils.isEmpty(text)
                ? new Money().toString()
                : text);

        setSummary(String.format(summaryFormat, getMoney()));
    }
}
