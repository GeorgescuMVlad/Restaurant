package data;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import business.MenuItem;
import business.Restaurant;

/**
 * Class which contains the methods for serializing and deserializing items to/from file
 * @author vladg
 *
 */
public class RestaurantSerializator{
	/**
	 * Method to serialize the list of menu items into the file
	 * @param items the list of menu items to be serializaed into the file
	 */
	public static void serialization(List<MenuItem> items)
	{
		try
		{
			FileOutputStream fileOut=new FileOutputStream("restaurant.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(items);
			out.close();
			fileOut.close();
		}catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * Method to deserialize the list of menu items from the file
	 * @return items2 the list containing the desearialized items
	 */
	public static List<MenuItem> deserialization()
	{
		List<MenuItem> items2 = new ArrayList<MenuItem>();
		try {
			FileInputStream fileIn=new FileInputStream("restaurant.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			items2=(ArrayList) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i) {
			i.printStackTrace();
		}catch(ClassNotFoundException c) {
			c.printStackTrace();
		}
		return items2;
	}
}
