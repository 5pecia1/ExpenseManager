package sol_5pecia1.expense_manager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import lombok.Data;
import sol_5pecia1.expense_manager.R;

/**
 * Created by 5pecia1 on 2016-11-11.
 */
@Data
public class MoneyFormatView extends TextView{
    private String money = "";

    public MoneyFormatView(Context context) {
        this(context, null);
    }

    public MoneyFormatView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);


        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MoneyFormatView);
        String typedMoney = typedArray.getString(R.styleable.MoneyFormatView_money);
        money = (typedMoney == null)? "0": typedMoney;

        setText(context.getString(R.string.money_format, money));
    }
}
