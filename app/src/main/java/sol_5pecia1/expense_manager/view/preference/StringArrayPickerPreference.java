package sol_5pecia1.expense_manager.view.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import lombok.Getter;
import lombok.Setter;
import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-17.
 */
public class StringArrayPickerPreference extends DialogPreference {


    private final static String DEFAULT_VALUE = "1";
    private final static String DEFAULT_SUMMARY = "";
    @Getter @Setter
    private String[] items;
    private String summaryFormat;

    public StringArrayPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public StringArrayPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StringArrayPickerPreference(Context context) {

        this(context, null);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return super.onGetDefaultValue(a, index);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        Log.e("Test","restorePersistedValue : " + restorePersistedValue + "\ndefaultValue : " + defaultValue );
//        if (restorePersistedValue) {
//            setDefaultValue(StringArrayPickerPreference.DEFAULT_VALUE);
//        } else {
//        }
    }

    @Override
    public boolean shouldDisableDependents() {
        return super.shouldDisableDependents();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attributeSet
                , R.styleable.StringArrayPickerPreference
        );

        final int id = typedArray.getResourceId(R.styleable.StringArrayPickerPreference_items, 0);
        String summary = getSummary().toString();

        summaryFormat = (summary == null)
                ? StringArrayPickerPreference.DEFAULT_SUMMARY
                : summary;

        String[] items = (id  != 0)
                ? getContext().getResources().getStringArray(id)
                : new String[]{};

        setItems(items);

        typedArray.recycle();
    }

    public void updatePreference(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

}
