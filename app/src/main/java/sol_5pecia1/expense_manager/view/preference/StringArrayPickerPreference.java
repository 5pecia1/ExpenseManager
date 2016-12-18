package sol_5pecia1.expense_manager.view.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;

import lombok.Getter;
import lombok.Setter;
import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-17.
 */
public class StringArrayPickerPreference extends DialogPreference {

    private final static int DEFAULT_VALUE = 0;
    private final static String DEFAULT_SUMMARY = "%s";

    @Getter @Setter
    private String[] items;
    private String summaryFormat;
    @Getter
    private int selectedIndex;
    private int dialogLayoutResId = R.layout.preference_number_picker;

    public StringArrayPickerPreference(Context context) {
        this(context, null);
    }
    public StringArrayPickerPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }
    public StringArrayPickerPreference(Context context, AttributeSet attrs
            , int defStyleAttr) {
        this(context, attrs, defStyleAttr, defStyleAttr);
    }
    public StringArrayPickerPreference(Context context, AttributeSet attrs
            , int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setDialogLayoutResource(R.layout.dialog_input_money);

        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs
                , R.styleable.StringArrayPickerPreference
        );

        int id = typedArray.getResourceId(
                R.styleable.StringArrayPickerPreference_items
                , 0
        );
        CharSequence summary = getSummary();

        summaryFormat = (summary == null)
                ? StringArrayPickerPreference.DEFAULT_SUMMARY
                : summary.toString();

        String[] items = (id  != 0)
                ? getContext().getResources().getStringArray(id)
                : new String[]{};

        setItems(items);

        typedArray.recycle();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, DEFAULT_VALUE);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue
            , Object defaultValue) {
        setSelectedIndex(restorePersistedValue
                ? getPersistedInt(selectedIndex)
                : (int)defaultValue
        );
    }

    @Override
    public int getDialogLayoutResource() {
        return dialogLayoutResId;
    }

    public String getSelectedItem() {
        return items[getSelectedIndex()];
    }

    protected void setSelectedIndex(int index) {
        selectedIndex = index;
        persistInt(index);

        setSummary(String.format(summaryFormat, getSelectedItem()));
    }
}
