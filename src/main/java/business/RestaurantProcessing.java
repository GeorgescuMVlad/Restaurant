package business;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface containing the methods for administrator and waiter implemented into Restaurant Class
 * @author vladg
 *
 */
public interface RestaurantProcessing {
		public void createMenuItem(MenuItem m);
		public void editMenuItem(MenuItem m, String name, List<MenuItem> items);
		public void deleteMenuItem(MenuItem m);
		
		public void createOrder(Order o, ArrayList<MenuItem> m);
		public void generateBill(Order o, int totalPrice, ArrayList<String> orderedProducts);
}
