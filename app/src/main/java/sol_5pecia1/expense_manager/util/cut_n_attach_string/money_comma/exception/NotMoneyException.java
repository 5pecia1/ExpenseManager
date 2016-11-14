package sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.exception;

public class NotMoneyException extends Exception{
	public NotMoneyException() {
		super("This string expression is not money.");
	}
}
