package sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma;

import java.util.regex.Pattern;

import sol_5pecia1.expense_manager.util.cut_n_attach_string.CutNAttachStringInterval;
import sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.exception.InvalidNumberException;
import sol_5pecia1.expense_manager.util.cut_n_attach_string.money_comma.exception.NotMoneyException;


/**
 * 
 * @author 5pecia1
 *
 */
public class MoneyComma {
	private final static String COMMA = ",";
	private final static String DOT = ".";
	
	
	private MoneyComma(){
		
	}
	
	private static void checkMoney(String money) throws NotMoneyException {
		if( !Pattern.matches("([0-9"+ COMMA +"]+(.[0-9]+)?)+", money)){
			throw new NotMoneyException();
		}
	}
	
	private static void checkAppendNumber(String money) throws InvalidNumberException {
		if( !Pattern.matches("[0-9]*", money)){
			throw new InvalidNumberException();
		}
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param money
	 * @return
	 * @throws NotMoneyException
	 */
	public static String makePure(String money) throws NotMoneyException{
		checkMoney(money);
		String result = CutNAttachStringInterval.makePureString(money, COMMA);
		return result;
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param money
	 * @param divideUnit
	 * @return
	 * @throws NotMoneyException
	 */
	public static String divide(String money, int divideUnit) throws NotMoneyException{
		checkMoney(money);
		String[] splitDot = money.split("[" + DOT + "]");
		String dividedMoney = CutNAttachStringInterval.makePureNDivideString(splitDot[0], COMMA, divideUnit); 
		return dividedMoney + ((splitDot.length == 2)? DOT + splitDot[1] : "");
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param money
	 * @param divideUnit
	 * @param appendNumber
	 * @return
	 * @throws NotMoneyException
	 * @throws InvalidNumberException
	 */
	public static String append(String money, int divideUnit, String appendNumber) throws NotMoneyException, InvalidNumberException{
		checkMoney(money);
		checkAppendNumber(appendNumber);
		
		String resultMoney = divide(money + appendNumber, divideUnit);

		return resultMoney;
	}
	
	/**
	 * If return is empty string, this method return "0". 
	 * @author 5pecia1
	 *
	 * @param money
	 * @param divideUnit
	 * @return
	 * @throws NotMoneyException
	 */
	public static String deleteEnd(String money, int divideUnit) throws NotMoneyException{
		checkMoney(money);
		
		String[] splitDot = money.split("[" + DOT + "]");
		StringBuilder deletedMoney = new StringBuilder();
		if(splitDot.length == 2){
			deletedMoney.append(splitDot[0]);
			
			if(splitDot[1].length() > 1){
				String deleteEndNumber = splitDot[1].substring(0, splitDot[1].length() - 1);
				deletedMoney.append(DOT + deleteEndNumber);
			}
		} else{
			String pureNumber = makePure(splitDot[0]);
			
			if(pureNumber.length() > 1){
				String deleteEndNumber =  pureNumber.substring(0, pureNumber.length() - 1);
				deletedMoney.append(divide(deleteEndNumber, divideUnit));
			}
		}
		
		if(deletedMoney.toString().equals("")) {
			deletedMoney.append("0");
		}
		return deletedMoney.toString();
	}
}
