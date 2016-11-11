package sol_5pecia1.expense_manager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import lombok.Getter;
import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-11.
 */
public class MoneyFormatView extends TextView{
    @Getter
    private String money;
    private String format;

    public MoneyFormatView(Context context) {
        this(context, null);
    }

    public MoneyFormatView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MoneyFormatView);

        String typedMoney = typedArray.getString(R.styleable.MoneyFormatView_money);
        String inputFormat = typedArray.getString(R.styleable.MoneyFormatView_format);

        money = (typedMoney == null)? "0" : typedMoney;
        format = (inputFormat == null)
                ? context.getString(R.string.default_money_format)
                : inputFormat;


        setMoney(money);
    }

    public void setMoney(String money) {
        setMoney(money, format);
    }

    public void setMoney(String money, String format) {
        this.money = money;
        this.format = format;

        if (format.split("%s").length != 2) { // prevent exception
            format = String.format(format, money, "%s");
        }

        setText(String.format(format, money));
    }
}
