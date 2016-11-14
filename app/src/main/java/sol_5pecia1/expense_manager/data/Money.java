package sol_5pecia1.expense_manager.data;

import lombok.Data;
import sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.MoneyComma;
import sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.exception.NotMoneyException;

/**
 * Created by 5pecia1 on 2016-11-12.
 */
@Data
public class Money implements Comparable<Money>{
    public  final static float DEFAULT_MONEY = 0;
    private final static int DIVIDE_UNIT = 3;
    private float money;

    public Money(float money) {
        this.money = money;
    }

    public Money(String money) {
        try {
            this.money = Float.valueOf(money);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            this.money = 0;
        }
    }

    public Money minus(Money money) {
        return new Money(this.money - money.getMoney());
    }

    @Override
    public String toString() {
        String result = null;

        try {
            result = MoneyComma.divide(Float.toString(money), DIVIDE_UNIT);
        } catch (NotMoneyException e) {
            e.printStackTrace();
            result = Float.toString(DEFAULT_MONEY);
        }
        String[] splitDot = result.split("[.]"); //for korea

        return splitDot[0];
    }

    @Override
    public int compareTo(Money o) {
        float result = money - o.getMoney();

        if (result > 1) {
            return 1;
        } else if (result == 0) {
            return 0;
        }
        return -1;
    }
}
