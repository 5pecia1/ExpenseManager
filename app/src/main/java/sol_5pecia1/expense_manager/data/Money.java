package sol_5pecia1.expense_manager.data;

import android.support.annotation.NonNull;

import lombok.Data;
import sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.MoneyComma;
import sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.exception.InvalidNumberException;
import sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.exception.NotMoneyException;

/**
 * Created by 5pecia1 on 2016-11-12.
 */
@Data
public class Money implements Comparable<Money>{
    public  final static int DEFAULT_MONEY = 0;

    private final static int MAX = 11; //contain comma
    private final static int DIVIDE_UNIT = 3;
    private final int money;

    public Money(int money) {
        this.money = money;
    }

    public Money(String money) {
        int defaultMoney = 0;

        try {
            money = MoneyComma.makePure(money);
            defaultMoney = Integer.valueOf(money);
        } catch (NumberFormatException | NotMoneyException e) {
            e.printStackTrace();
        }
        this.money = defaultMoney;
    }

    @NonNull
    public Money minus(@NonNull Money money) {
        return new Money(this.money - money.getMoney());
    }

    @NonNull
    public Money append(@NonNull String money) {
        Money result = this;

        if ( !(this.toString().length() + money.length() > Money.MAX)) {
            try {
                result = new Money(MoneyComma.append(this.toString(), Money.DIVIDE_UNIT, money));
            } catch (NotMoneyException | InvalidNumberException e) {
                e.printStackTrace();
                result = new Money(Money.DEFAULT_MONEY);
            }
        }
        return result;
    }

    @NonNull
    public Money deleteEnd() {
        try {
            return new Money(MoneyComma.deleteEnd(toString(), Money.DIVIDE_UNIT));
        } catch (NotMoneyException e) {
            e.printStackTrace();
            return new Money(Money.DEFAULT_MONEY);
        }
    }

    @Override
    public String toString() {
        String result = null;

        try {
            result = MoneyComma.divide(Integer.toString(money), Money.DIVIDE_UNIT);
        } catch (NotMoneyException e) {
            e.printStackTrace();
            result = Integer.toString(DEFAULT_MONEY);
        }

        return result;
    }

    @Override
    public int compareTo(Money o) {
        int result = money - o.getMoney();

        if (result > 1) {
            return 1;
        } else if (result == 0) {
            return 0;
        }
        return -1;
    }
}
