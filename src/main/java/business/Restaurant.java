package business;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class Restaurant which describes the methods for administrator and waiter operations.
 * @see Order
 * @see MenuItem
 * @author vladg
 *
 */
public class Restaurant implements RestaurantProcessing, java.io.Serializable {

	private static final long serialVersionUID = -8611447181504017995L;
	private List<MenuItem> items = new ArrayList<MenuItem>();
	private HashMap<Order, ArrayList<MenuItem>> map = new HashMap<Order, ArrayList<MenuItem>>();
	
	public Restaurant(List<MenuItem> items)
	{
		this.items=items;
	}
	
	public Restaurant() {}	
	
	/**
	 * Method to add a new menu item to the list of items
	 * @param m the menu item to be added
	 */
	public void createMenuItem(MenuItem m) 
	{
		this.items.add(m);
	}

	/**
	 * Method to edit a menu item
	 * @param m the menu item to be edited
	 * @param namess the name of the menu item
	 * @param items the list of menu items which contains the menu item m o be edited
	 */
	public void editMenuItem(MenuItem m, String namess, List<MenuItem> items ) {
		for(int j=0; j<items.size(); j++)
		{
			Class cls = items.get(j).getClass(); 
			if(cls.getName()=="business.BaseProduct"){
			Method met;
			try {
			met = cls.getMethod("getName");
			try {
				if(met.invoke(items.get(j)).toString().equalsIgnoreCase(namess) )
				{
					items.set(j, m);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
				}
			}	
		}	
	}

	/**
	 * Method to delete a menu item from the list of items
	 * @param m the item to be deleted
	 */
	public void deleteMenuItem(MenuItem m) 
	{
		items.remove(m);		
	}

	/**
	 * Method to create/place an order into the hashTabble structure
	 * @param o the key of the map
	 * @param m the values of the map
	 */
	public void createOrder(Order o, ArrayList<MenuItem> m) 
	{
		map.put(o, m);
	}
	
	/**
	 * Method that generates the bill in .txt format for an order
	 * @param o the order on which to generate bill
	 * @param totalPrice the price of that order
	 * @param orderedProducts the list containing the ordered products for that order
	 */
	public void generateBill(Order o, int totalPrice, ArrayList<String> orderedProducts) 
	{
		try {
			PrintWriter out = new PrintWriter("Orders.txt");
			out.println("Order Bill ");
			out.println("Order id: " + o.getOrderId());
			out.println("Order made at date: " + o.getDate());
			out.println("Order made by table: " + o.getTable());
			out.println("Ordered products: " + orderedProducts);
			out.println("The total price of the order is: " + totalPrice); 
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public void setItems(List<MenuItem> items) {
		this.items = items;
	}

	public HashMap<Order, ArrayList<MenuItem>> getMap() {
		return map;
	}

	public void setMap(HashMap<Order, ArrayList<MenuItem>> map) {
		this.map = map;
	}

}
