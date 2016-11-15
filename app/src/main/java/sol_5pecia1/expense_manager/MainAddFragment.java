package sol_5pecia1.expense_manager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sol_5pecia1.expense_manager.data.Money;
import sol_5pecia1.expense_manager.view.InputMoneyDialog;
import sol_5pecia1.expense_manager.view.MoneyFormatView;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class MainAddFragment extends Fragment implements MainContract.AddView{
    private final static int DEFAULT_EXPENSE = 0;

    @BindView(R.id.addMoney)
    MoneyFormatView mfvAddMoney;

    @BindView(R.id.classification)
    RadioGroup rgClassification;

    @BindView(R.id.date)
    TextView tvDate;

    @BindView(R.id.inputThan)
    EditText etInputThan;

    @BindArray(R.array.classification)
    String[] classificationItems;

    private RadioButton rbDefault;
    private RadioButton rbIncome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_add, container, false);
        ButterKnife.bind(this, rootView);

        addRadioButton();

        setDefaultClassification();

        return rootView;
    }

    @Override
    public void initView() {
        showAddDialog(null, InputMoneyDialog.NOT_SET);

        setDefaultClassification();

        Calendar today = Calendar.getInstance();
        setDateView(
                today.get(Calendar.YEAR)
                , today.get(Calendar.MONTH)
                , today.get(Calendar.DAY_OF_MONTH)
        );

        etInputThan.setText("");
    }

    @OnClick(R.id.addMoney)
    void onAddMoneyClicked() {
        int clickedRadioButtonId = rgClassification.getCheckedRadioButtonId();
        RadioButton clicked = (RadioButton) rgClassification.findViewById(clickedRadioButtonId);

        @InputMoneyDialog.SignType
        int sign = (clicked == rbIncome)
                ? InputMoneyDialog.INCOME
                : InputMoneyDialog.EXPENSE;

        showAddDialog(mfvAddMoney.getMoney(), sign);
    }

    @OnClick(R.id.date)
    void onDateClicked() {
        Calendar today = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity()
                , (v, year, month, dayOfMonth) -> {
                    setDateView(year, month, dayOfMonth);
                }
                , today.get(Calendar.YEAR)
                , today.get(Calendar.MONTH)
                , today.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void addRadioButton() {
        for(int i = 0; i < classificationItems.length; i ++) {
            String item = classificationItems[i];
            RadioButton rb = new RadioButton(getContext());

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);

            rb.setLayoutParams(layoutParams);

            rb.setText(item);
            rgClassification.addView(rb);

            if (i == MainAddFragment.DEFAULT_EXPENSE) {
                rbDefault = rb;
            } else if (i == classificationItems.length - 1) {
                rbIncome = rb;
            }
        }
    }

    private void showAddDialog(@Nullable Money money, @InputMoneyDialog.SignType int sign) {
        InputMoneyDialog inputMoneyDialog = new InputMoneyDialog(getActivity());

        if (money  != null) {
            inputMoneyDialog.setMoney(money);
        }

        if (sign  != InputMoneyDialog.NOT_SET) {
            inputMoneyDialog.setSign(sign);
        }

        inputMoneyDialog.setOnDismissListener(dialog -> {
            mfvAddMoney.setMoney(inputMoneyDialog.getMoney());

            if (inputMoneyDialog.getSign() == InputMoneyDialog.INCOME) {
                rbIncome.setChecked(true);
            } else if (rbIncome.isChecked()){
                rbDefault.setChecked(true);
            }

        });
        inputMoneyDialog.show();
    }

    private void setDateView(int year, int month, int day) {
        String dateFormat = getContext().getString(R.string.date_format);
        String date = String.format(
                dateFormat
                , year
                , month
                , day
        );

        tvDate.setText(date);
    }

    private void setDefaultClassification() {
        rbDefault.setChecked(true);
    }
}
