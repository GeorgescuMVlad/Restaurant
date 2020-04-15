package business;

import java.util.ArrayList;
import java.util.List;

/**
 * Class CompositeProduct which describes the attributes of a composite product, compute price of a composite product and have getters and setters for these attributes
 * @author vladg
 *
 */
public class CompositeProduct extends MenuItem {
	private String name;
	private int price;
	private List<MenuItem> products = new ArrayList<MenuItem>();
	
	public CompositeProduct(String name, List<MenuItem> products)
	{
		this.name=name;
		this.products=products;
	}
	public CompositeProduct() {}
	
	public void addProduct(MenuItem m)
	{
		products.add(m);
	}

	/**
	 * Method to compute the price of a composite product. The list with composite products is being traced and if a composite product
	 * is found, then the method goes deeper for that product and compute its price and then comes back to compute the initial composite item price
	 * @return the computed price of the composite product
	 */
	public int computePrice() {
		int computedPrice=0;
		for(int i=0; i<products.size(); i++)
		{
			computedPrice+=products.get(i).computePrice();
		}
		this.price=computedPrice;
		return computedPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MenuItem> getProducts() {
		return products;
	}

	public void setProducts(List<MenuItem> products) {
		this.products = products;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
