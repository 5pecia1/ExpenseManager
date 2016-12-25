package sol_5pecia1.expense_manager.util;

/**
 * Created by sol on 2016-12-25.
 */

public interface Custom8 {
    @FunctionalInterface
    interface BiFunction<T, U, R>{
        R apply(T start, U end);
    }

    @FunctionalInterface
    interface Function<T, R> {
        R apply(T day);
    }
}
