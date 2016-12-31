package sol_5pecia1.expense_manager.main.Add;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.data.Account;
import sol_5pecia1.expense_manager.data.AccountModel;
import sol_5pecia1.expense_manager.data.Money;
import sol_5pecia1.expense_manager.main.BaseFragment;
import sol_5pecia1.expense_manager.main.MainContract;
import sol_5pecia1.expense_manager.view.InputMoneyDialog;
import sol_5pecia1.expense_manager.view.MoneyFormatView;

/**
 * Created by 5pecia1 on 2016-11-10.
 */
public class AddFragment extends BaseFragment implements MainContract.AddView {
    private final static int ICON = R.drawable.ic_add_white_24dp;
    private final static int DEFAULT_EXPENSE = 0;

    @BindView(R.id.addMoney)
    MoneyFormatView mfvAddMoney;

    @BindView(R.id.classification)
    RadioGroup rgClassification;

    @BindView(R.id.date)
    TextView tvDate;

    @BindView(R.id.inputBesides)
    EditText etInputBesides;

    @BindArray(R.array.classification)
    String[] classificationItems;

    private RadioButton rbDefault;
    private RadioButton rbIncome;

    private AddPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_add, container, false);
        ButterKnife.bind(this, rootView);

        AccountModel accountModel =
                new Account(getActivity(), classificationItems);

        presenter = new AddPresenter(this, accountModel);

        addRadioButton();

        setDefaultClassification();

        return rootView;
    }

    @Override
    public int getIcon() {
        return ICON;
    }

    @Override
    public void initView() {
        showAddDialog(null, InputMoneyDialog.NOT_SET);

        mfvAddMoney.setMoney(new Money());

        setDefaultClassification();

        setDateView(Calendar.getInstance());

        etInputBesides.setText("");
    }

    @Override
    public void save() {
        int clickedRadioButtonId = rgClassification.getCheckedRadioButtonId();
        RadioButton clicked
                = (RadioButton) rgClassification
                .findViewById(clickedRadioButtonId);

        Money money = mfvAddMoney.getMoney();
        String classification = clicked.getText().toString();
        Calendar date = (Calendar) tvDate.getTag();
        String besides = etInputBesides.getText().toString();

        presenter.save(money, classification, date, besides);
    }

    @OnClick(R.id.addMoney)
    void onAddMoneyClicked() {
        int clickedRadioButtonId = rgClassification.getCheckedRadioButtonId();
        RadioButton clicked
                = (RadioButton) rgClassification
                .findViewById(clickedRadioButtonId);

        @InputMoneyDialog.SignType
        int sign = (clicked == rbIncome)
                ? InputMoneyDialog.INCOME
                : InputMoneyDialog.EXPENSE;

        showAddDialog(mfvAddMoney.getMoney(), sign);
    }

    @OnClick(R.id.date)
    void onDateClicked(TextView view) {
        Calendar calendar = (Calendar)view.getTag();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity()
                , (v, year, month, dayOfMonth) -> {
                    Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                    setDateView(cal);
                }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
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

            if (i == AddFragment.DEFAULT_EXPENSE) {
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
            if (inputMoneyDialog.isPositiveClicked()) {
                mfvAddMoney.setMoney(inputMoneyDialog.getMoney());

                if (inputMoneyDialog.getSign() == InputMoneyDialog.INCOME) {
                    rbIncome.setChecked(true);
                } else if (rbIncome.isChecked()) {
                    rbDefault.setChecked(true);
                }
            }
        });
        inputMoneyDialog.show();
    }

    private void setDateView(Calendar calendar) {
        String dateFormat = getContext().getString(R.string.date_format);
        String date = String.format(
                dateFormat
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
        );

        tvDate.setText(date);
        tvDate.setTag(calendar);
    }

    private void setDefaultClassification() {
        rbDefault.setChecked(true);
    }
}
