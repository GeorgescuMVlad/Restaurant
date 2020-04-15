package business.validators;

public class PriceValidator {
	/**
	 * Class to implement a validator on the price. The price should contain only digits
	 * @param pr the price to be checked
	 * @return a flag which tells if the price inserted was correct or not
	 */
	public static int validate(String pr)
	{
            char ch;
            for(int i=0; i<pr.length(); i++)
            {
                ch=pr.charAt(i);
                if(!(ch>='0' && ch<='9') )
                {
                    return 0;
                }    
            }
		return 1;
	}
}
