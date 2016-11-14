package sol_5pecia1.expense_manager.util.cut_n_attach_string;

/**
 * 
 * @author 5pecia1
 *
 */
public class CutNAttachStringInterval {

	private CutNAttachStringInterval(){
		
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param purposeString
	 * @param divideString
	 * @return
	 */
	public static String makePureString(String purposeString, String divideString){
		return purposeString.replace(divideString, "");
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param purposeString
	 * @param divideString
	 * @param divideInterval
	 * @return
	 */
	public static String divideString(String purposeString, String divideString, int divideInterval){
		int pureStringLength = purposeString.length();
		StringBuilder resultString = new StringBuilder();
		
		if(pureStringLength > divideInterval){
			int stringHeadLength = pureStringLength % divideInterval;
			int insertingCount = pureStringLength / divideInterval - 1;
			
			if(stringHeadLength  != 0){
				String head = purposeString.substring(0, stringHeadLength);
				String tail = purposeString.substring(stringHeadLength);
				resultString.append(head + divideString);
				purposeString = tail;
			}
			
			int i = 0;
			for(;i < insertingCount; i++){
				int start = i * divideInterval;
				String head = purposeString.substring(start, start + divideInterval);
				resultString.append(head + divideString);
			}
			String last = purposeString.substring(i * divideInterval);
			resultString.append(last);
			
		} else{
			resultString.append(purposeString);
		}
		
		return resultString.toString();
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param purposeString
	 * @param divideString
	 * @param divideInterval
	 * @return
	 */
	public static String makePureNDivideString(String purposeString, String divideString, int divideInterval){
		String pureString = makePureString(purposeString, divideString);
		return divideString(pureString, divideString, divideInterval);
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param purposeString
	 * @param divideString
	 * @param divideInterval
	 * @param appendString
	 * @return
	 */
	public static String append(String purposeString, String divideString, int divideInterval, String appendString){
		return makePureNDivideString(purposeString + appendString, divideString, divideInterval);
	}
	
	/**
	 * 
	 * @author 5pecia1
	 *
	 * @param purposeString
	 * @param divideString
	 * @param divideInterval
	 * @return
	 */
	public static String deleteEnd(String purposeString, String divideString, int divideInterval){
		String pureString = makePureString(purposeString, divideString);
		return divideString(pureString.substring(0, pureString.length() - 1), divideString, divideInterval);
	}
}
