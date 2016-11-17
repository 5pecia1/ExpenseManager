package sol_5pecia1.expense_manager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;

import lombok.Getter;
import sol_5pecia1.expense_manager.R;
import sol_5pecia1.expense_manager.data.Money;

/**
 * Created by 5pecia1 on 2016-11-11.
 */
public class MoneyFormatView extends TextView{
    public final static String STRING_FORMAT_ARGUMENT = "%s";
    @Getter
    private Money money;
    @Getter
    private String defaultFormat;
    private String format;

    public MoneyFormatView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MoneyFormatView);

        int typedMoney = typedArray.getInt(R.styleable.MoneyFormatView_money, Money.DEFAULT_MONEY);
        String inputFormat = typedArray.getString(R.styleable.MoneyFormatView_format);

        money = new Money(typedMoney);
        defaultFormat = format = (inputFormat == null)
                ? context.getString(R.string.default_money_format)
                : inputFormat;


        setMoney(money);
    }

    public void setMoney(@NonNull Money money) {
        setMoney(money, format);
    }

    public void setMoney(@NonNull Money money, @NonNull String format) {
        this.money = money;
        this.format = format;

        String[] splitFormat = format.split("%.{0,2}s");

        if (splitFormat.length > 1) {

            ArrayList<String> args = new ArrayList<>(splitFormat.length - 1);
            for (int i = 0; i < splitFormat.length - 1; i++) {
                args.add(money.toString());
            }

            String[] argsArray = new String[args.size()];
            argsArray = args.toArray(argsArray);
            setText(String.format(format, argsArray));
        }
    }
}
