package sol_5pecia1.expense_manager.data;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by sol on 2016-12-21.
 */

public interface AccountModel {
    long addAccount(@NonNull Money money, @NonNull String classification
            , @NonNull Calendar saveDate, @NonNull String besides);
}
