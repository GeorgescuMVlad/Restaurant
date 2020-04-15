package business;

/**
 * Class BaseProduct which describes the attributes of a base product, compute price of a base product and have getters and setters for these attributes
 * @author vladg
 *
 */
public class BaseProduct extends MenuItem {
	
	private int price;
	private String name;
	
	public BaseProduct(String name, int price)
	{
		this.name=name;
		this.price=price;	
	}

	public int computePrice() {
		return getPrice();  
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
