package utilities;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomGenerator {
	
	/*THIS WILL GENERATE RANDOM INTEGER  BETWEEN SPECIFIED RANGE
	 * 
	 */
	public static int randomNumberInGivenRange(int number){
		Random r = new Random();
		int RandomNumber=r.nextInt(number);
		return RandomNumber;
	}
	
	/*THIS WILL GENERATE RANDOM STRING  BETWEEN SPECIFIED RANGE
	 * 
	 */	
	public static String randomNumber(int range){
		String randomNumber=RandomStringUtils.random(range);
		return randomNumber;
	}
	
	/*THIS WILL GENERATE RANDOM STRING  BETWEEN SPECIFIED RANGE
	 * AND CHARACTERS
	 */
	public static String randomNumber(int range, String characters){
		String randomNumber=RandomStringUtils.random(range,characters);
		return randomNumber;
	}
	
	/*THIS WILL GENERATE RANDOM STRING  BETWEEN SPECIFIED RANGE
	 * AND BOOLEAN VALUE
	 */
	public static String randomNumber(int range,int start, int end,boolean letters,boolean number){
		String randomNumber=RandomStringUtils.random(range,start,end,letters,number);
		return randomNumber;
	}
	
	/*THIS WILL GENERATE RANDOM STRING  BETWEEN SPECIFIED RANGE
	 * IT WILL CONTAIN ONLY CHARACTER
	 */
	public static String randomAlphaNumeric(int range){
		String randomNumber=RandomStringUtils.randomAlphanumeric(range);
		return randomNumber;
	}
	
	/*THIS WILL GENERATE RANDOM STRING  BETWEEN SPECIFIED MINIMUN RANGE AND MAXIMUM RANGE
	 * IT WILL CONTAIN ONLY CHARACTER
	 */
	public static String randomAlphaNumeric(int minRange, int maxRange){
		String randomNumber=RandomStringUtils.randomAlphanumeric(minRange,maxRange);
		return randomNumber;
	}
	
	/*THIS WILL GENERATE RANDOM STRING  BETWEEN SPECIFIED MINIMUN RANGE AND MAXIMUM RANGE
	 * IT WILL CONTAIN ONLY NUMBER
	 */
	public static String randomNumeric(int minRange, int maxRange){
		String randomNumber=RandomStringUtils.randomNumeric(minRange,maxRange);
		return randomNumber;
	}
	
	/*THIS WILL GENERATE RANDOM STRING  BETWEEN SPECIFIED RANGE
	 * IT WILL CONTAIN ONLY NUMBER
	 */
	public static String randomNumeric(int range){
		String randomNumber=RandomStringUtils.randomNumeric(range);
		return randomNumber;
	}

}
