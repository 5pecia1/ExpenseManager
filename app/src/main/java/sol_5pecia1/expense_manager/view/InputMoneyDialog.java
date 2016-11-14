package sol_5pecia1.expense_manager.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-14.
 */
public class InputMoneyDialog extends AlertDialog.Builder {

    @BindView(R.id.displayMoney)
    MoneyFormatView mfvDisplayMoney;

    @BindDrawable(R.drawable.ic_add_black_24dp)
    Drawable addBlack;
    private Drawable removeBlack;


    public InputMoneyDialog(@NonNull Activity activity) {
        super(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_input_money, null);
        ButterKnife.bind(this, dialogView);

        ImageButton sign = (ImageButton) dialogView.findViewById(R.id.sign);
        removeBlack = sign.getBackground();

        setTitle(R.string.input_money);
        setView(dialogView);


        initListener();
    }

    @Override
    public AlertDialog show() {
        return super.show();
    }

    private void initListener() {
        setPositiveButton(R.string.input, (dialog, which) -> {

        });

        setNegativeButton(R.string.cancel, (dialog, which) -> {

        });
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

    private void appendMoney(String money) {
        mfvDisplayMoney.setMoney(mfvDisplayMoney.getMoney().append(money));
    }

    private void deleteEndMoney() {
        mfvDisplayMoney.setMoney(mfvDisplayMoney.getMoney().deleteEnd());
    }
}
