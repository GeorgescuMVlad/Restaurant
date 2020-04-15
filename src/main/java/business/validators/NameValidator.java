package business.validators;

public class NameValidator {
	
	/**
	 * Class that implements the validator for the name of a product Should contain only letters and space
	 * @param p the name to be checked
	 * @return a flag which tells if the product is valid or not
	 */
	public static int validate(String p)
	{
		 char ch;
         for(int i=0; i<p.length(); i++)
         {
             ch=p.charAt(i);
             if(!(ch>='a' && ch<='z') && !(ch>='A' && ch<='Z') && !(ch==' '))
             {
                 return 0;
             }    
         }
         return 1;
	}

}
