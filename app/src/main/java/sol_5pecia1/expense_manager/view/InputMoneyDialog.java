package sol_5pecia1.expense_manager.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.data.Money;

/**
 * Created by 5pecia1 on 2016-11-14.
 */
public class InputMoneyDialog extends AlertDialog.Builder {
    public final static int NOT_SET = 0;
    public final static int EXPENSE = 1;
    public final static int INCOME = 2;

    @IntDef({NOT_SET, EXPENSE, INCOME})
    public @interface SignType{}

    @BindView(R.id.displayMoney)
    MoneyFormatView mfvDisplayMoney;

    @BindView(R.id.sign)
    ImageButton ibSign;

    @BindDrawable(R.drawable.ic_add_black_24dp)
    Drawable addBlack;

    Drawable removeBlack;

    @Getter
    private boolean positiveClicked = false;


    public InputMoneyDialog(@NonNull Activity activity) {
        super(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_input_money, null);
        ButterKnife.bind(this, dialogView);

        ImageButton sign = (ImageButton) dialogView.findViewById(R.id.sign);
        removeBlack = sign.getBackground();

        setTitle(R.string.input_money);
        setView(dialogView);


        setButtonName();
    }

    @Override
    public AlertDialog show() {
        return super.show();
    }

    @OnClick(R.id.sign)
    void onSignClicked(ImageButton button) {
        if (button.getBackground() == removeBlack) {
            button.setBackground(addBlack);
        } else {
            button.setBackground(removeBlack);
        }
    }

    @OnClick(R.id.backspace)
    void onBackspaceClicked() {
        deleteEndMoney();
    }

    @OnClick({
            R.id.keyNumber1, R.id.keyNumber2, R.id.keyNumber3
    , R.id.keyNumber4, R.id.keyNumber5, R.id.keyNumber6
    , R.id.keyNumber7, R.id.keyNumber8, R.id.keyNumber9
    , R.id.keyNumber0, R.id.keyNumber00, R.id.keyNumber000
    })
    void onKeyNumberClicked(Button button) {
        appendMoney(button.getText().toString());
    }

    public void setMoney(@NonNull Money money) {
        mfvDisplayMoney.setMoney(money);
    }

    @NonNull
    public Money getMoney() {
        return mfvDisplayMoney.getMoney();
    }

    public void setSign(@SignType int sign) {
        if (sign == InputMoneyDialog.EXPENSE) {
            ibSign.setBackground(removeBlack);
        } else {
            ibSign.setBackground(addBlack);
        }
    }

    @SignType
    public int getSign() {
        if (ibSign.getBackground() == removeBlack) {
            return InputMoneyDialog.EXPENSE;
        } else {
            return InputMoneyDialog.INCOME;
        }
    }

    private void setButtonName() {
        setPositiveButton(R.string.input, (dialog, which) -> {
            positiveClicked = true;
        });

        setNegativeButton(R.string.cancel, (dialog, which) -> {
        });
    }

    private void appendMoney(String money) {
        mfvDisplayMoney.setMoney(mfvDisplayMoney.getMoney().append(money));
    }

    private void deleteEndMoney() {
        mfvDisplayMoney.setMoney(mfvDisplayMoney.getMoney().deleteEnd());
    }
}
