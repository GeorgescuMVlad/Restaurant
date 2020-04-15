package presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;
import business.validators.NameValidator;
import business.validators.PriceValidator;
import data.RestaurantSerializator;

/**
 * Class AdministratorGUI is a presentation class which realises the UI and implements the listeners on buttons and constructs the JTable
 * through deserialization when the app is started. The new information added to the table is being saved into a file using serialization.
 * Aslo, whenever it is needed to be decided which kind of MenuItem is the one at a specific moment, the reflexion technique is being used 
 * in order to get the class of that MenuItem(BaseProduct or CompositeProduct since the MenuItem is an abstract class) and to get the methods 
 * that need to be invoked from that class. 
 * @author vladg
 * @see MenuItem
 * @see Restaurant
 */
public class AdministratorGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public JTextField t0=makeTextFields();
	public JTextField t1=makeTextFields();
	public JTextField t2=makeTextFields();
	
	static DefaultTableModel models = new DefaultTableModel();  
	static JTable table = new JTable(models);  
	
    JPanel panel=makePrincipalPanels("Buttons Panel");
    JPanel panelOut=makePrincipalPanels("JTable Data Panel");
    
	SpringLayout springLayout = new SpringLayout();
	
	JFrame frame=makeFrame("Window for Administrator");
    
    JLabel nameLabel=makeLabels("MenuItem name: ");
    JLabel quantityLabel=makeLabels("MenuItem quantity: ");
    JLabel priceLabel=makeLabels("MenuItem price: ");
    
    public JButton addBaseButton=makeButtons("Add Base Item");
    public JButton addCompositeButton=makeButtons("Add Composite Item");
    public JButton editButton=makeButtons("Edit MenuItem");
    public JButton deleteButton=makeButtons("Delete MenuItem");
	public CompositeProduct c=new CompositeProduct();
	static List<MenuItem> items = new ArrayList<MenuItem>();  
	
   /** Constructor to setup the GUI */
   public AdministratorGUI() {        
        frame.add(panel);
        frame.add(panelOut);
        
        panel.add(nameLabel);
        panel.add(t0);
        panel.add(t1);
        panel.add(priceLabel);
        
        panel.add(addBaseButton);
        panel.add(addCompositeButton);
        panel.add(editButton);
        panel.add(deleteButton); 
        
        panel.setLayout(springLayout);
        springLayout.putConstraint(SpringLayout.WEST, t0, 160, SpringLayout.WEST, nameLabel);
   
        springLayout.putConstraint(SpringLayout.SOUTH, t1, 40, SpringLayout.SOUTH, t0);
        springLayout.putConstraint(SpringLayout.WEST, t1, 160, SpringLayout.WEST, quantityLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, priceLabel, 40, SpringLayout.SOUTH, nameLabel);
       
        springLayout.putConstraint(SpringLayout.SOUTH, addBaseButton, 60, SpringLayout.SOUTH, priceLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, addBaseButton, 60, SpringLayout.SOUTH, t1);
        
        springLayout.putConstraint(SpringLayout.SOUTH, addCompositeButton, 60, SpringLayout.SOUTH, priceLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, addCompositeButton, 60, SpringLayout.SOUTH, t1);
        springLayout.putConstraint(SpringLayout.WEST, addCompositeButton, 120, SpringLayout.WEST, addBaseButton);
            
        springLayout.putConstraint(SpringLayout.SOUTH, editButton, 60, SpringLayout.SOUTH, priceLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, editButton, 60, SpringLayout.SOUTH, t1);
        springLayout.putConstraint(SpringLayout.WEST, editButton, 152, SpringLayout.WEST, addCompositeButton);
        
        springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, 60, SpringLayout.SOUTH, priceLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, 60, SpringLayout.SOUTH, t1);
        springLayout.putConstraint(SpringLayout.WEST, deleteButton, 118, SpringLayout.WEST, editButton);
               
        addBaseButton.addActionListener(this);
        addCompositeButton.addActionListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);       
        
		JScrollPane pa = new JScrollPane(table);
		
		models.addColumn("MenuItem Name");
		models.addColumn("MenuItem Price"); 
       
        pa.setPreferredSize(new Dimension(765, 315));
        pa.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        
        /* Deserialization used in order to load the data from file when the app starts. */
        items=RestaurantSerializator.deserialization();
        for(int i=0; i<items.size(); i++){
        	String name = null;
        	int price = 0;
            Class cls = items.get(i).getClass();
            if(cls.getName()=="business.BaseProduct"){
				Method methods;
				try {
					methods = cls.getMethod("getName");
					try {
						name=(String) methods.invoke(items.get(i));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					methods=cls.getMethod("getPrice");
					try {
						price=Integer.parseInt(methods.invoke(items.get(i)).toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}		
            }
            if(cls.getName()=="business.CompositeProduct"){
            	Method methods;
				try {
					methods = cls.getMethod("getName");
					try {
						name=(String) methods.invoke(items.get(i));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					methods=cls.getMethod("getPrice");
					try {
						price=Integer.parseInt(methods.invoke(items.get(i)).toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            models.addRow(new Object[] {name, price});
        }       
        panelOut.add(pa);
        frame.setVisible(true);
        }
   
   /* -If the admin wans to add a base product, a new base product with the specified characteristics is being added to the list of MenuItems.
    -If the admin wants to add a composite product, the list of items must be traced, get the class of each item through reflexion and
    get the method getName through reflexion from that class, and if the name is the same with the name selected by the admin in the table,
    then the new composite product is created and the table is being updated. Also, it is stored to file using serialization.
    -If the admin wants to edit a base product, then a base product is created using the specified characteristics and calls the editMenuItem
    method and then updates the table and serializes the new edited info to the file.
    -If the admin wants to delete an item from the menu, the list of items is traced and for each item it is obtained its class through reflexion
    and the method to get that item's name. The name is compared to the one selected by the admin in the table and if it is equal it is deleted.
     */
   public void actionPerformed(ActionEvent e) {
	    String itemName=getUserInput(t0);
		String itemPrice=getUserInput(t1);
	   	
	   	MenuItem m;
	   	Restaurant r = new Restaurant(items);
	
		if (e.getSource() == addBaseButton) {
			if(NameValidator.validate(itemName)==0)
				JOptionPane.showMessageDialog(this, "Wrong product name! Must contain only characters.");
			else { if(PriceValidator.validate(itemPrice)==0)
				JOptionPane.showMessageDialog(this, "Wrong product price! Must contain only digits.");
			else { 
			int price=Integer.parseInt(itemPrice);
			m=new BaseProduct(itemName, price);
			r.createMenuItem(m);
			RestaurantSerializator.serialization(r.getItems());
			models.addRow(new Object[] {itemName, itemPrice});
			}
			}
		}
		
		if (e.getSource() == addCompositeButton) {
			int[] rows=table.getSelectedRows();
			for(int i=0; i<r.getItems().size(); i++){
				Class cls = r.getItems().get(i).getClass(); 
				if(cls.getName()=="business.BaseProduct"){
					Method[] methods = cls.getDeclaredMethods();
					for(int j=0; j<rows.length; j++){
						for(int k=0; k<methods.length; k++){
							if(methods[k].getName()=="getName"){
								try {
									if(models.getValueAt(rows[j], 0).toString()==methods[k].invoke(r.getItems().get(i))){
										c.addProduct(r.getItems().get(i));
									}
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
					}				
				}
			}
				if(cls.getName()=="business.CompositeProduct"){
					Method[] methods = cls.getDeclaredMethods();
					for(int j=0; j<rows.length; j++){
						for(int k=0; k<methods.length; k++){
							if(methods[k].getName()=="getName"){
								try {
									if(models.getValueAt(rows[j], 0).toString()==methods[k].invoke(r.getItems().get(i))){
										c.addProduct(r.getItems().get(i));
									}
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
					}				
				}
			}
			}
			Integer p=c.computePrice();
			c=new CompositeProduct(itemName, c.getProducts());
			c.setPrice(p);
			r.getItems().add(c);
			RestaurantSerializator.serialization(r.getItems());
			models.addRow(new Object[] {itemName, p.toString() });
			c=new CompositeProduct(); 
		}
		
		if (e.getSource() == deleteButton) {
			int row=table.getSelectedRow();
			String nameToDelete=models.getValueAt(row, 0).toString();
			for(int i=0; i<r.getItems().size(); i++){
				Class cls = r.getItems().get(i).getClass(); 
				if(cls.getName()=="business.BaseProduct"){
					Method met;
					try {
						met = cls.getMethod("getName");
						try {
							String reflectedName=(String) met.invoke(r.getItems().get(i));
							if(reflectedName==nameToDelete )
							{
								r.deleteMenuItem(r.getItems().get(i));
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if(cls.getName()=="business.CompositeProduct"){
					Method met;
					try {
						met = cls.getMethod("getName");
						try {
							String reflectedName=(String) met.invoke(r.getItems().get(i));
							if(reflectedName==nameToDelete )
							{
								r.deleteMenuItem(r.getItems().get(i));
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			RestaurantSerializator.serialization(r.getItems());
			models.removeRow(row);
		}
		
		if (e.getSource() == editButton) {
			if(PriceValidator.validate(itemPrice)==0)
				JOptionPane.showMessageDialog(this, "Wrong product price! Must contain only digits.");
			else { 
			int price=Integer.parseInt(itemPrice);
			BaseProduct b=new BaseProduct(itemName, price);
			r.editMenuItem(b, itemName, r.getItems());
			int rw=table.getSelectedRow();
			models.setValueAt(itemName, rw, 0);
			models.setValueAt(price, rw, 1);
					
			RestaurantSerializator.serialization(r.getItems());	
			}
		}
		}
		
   public static List<MenuItem> getItems() {
	return items;
}

/**
    * Method to create a frame
    * @param frame_text which is the frame name
    * @return frame
    */
   public JFrame makeFrame(String frame_text)
   {
	    JFrame frame = new JFrame(frame_text);
        frame.setPreferredSize(new Dimension(1600, 400));
        frame.setLayout(new GridLayout(1, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) /20);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) /20);
        frame.setLocation(x, y);
        frame.setVisible(true);
        return frame;
   }
 	   
   /**
    * Method to create a label
    * @param label_text which is the label name
    * @return the label
    */
   public JLabel makeLabels(String label_text)
   {
        JLabel label = new JLabel();
        label.setText(label_text); 
        label.setFont(new java.awt.Font("Times New Roman", 1, 15));
        return label;
   }
   
   /**
    * Method to create a text field
    * @return the text field
    */
   public JTextField makeTextFields()
   {
        JTextField t= new JTextField();
        t.setPreferredSize(new Dimension(300, 30));
        return t;
   }
   	  
   /**
    * Method to create a panel
    * @param panel_name which is the panel name
    * @return the panel
    */
   public JPanel makePrincipalPanels(String panel_name)
   {
	   JPanel panel=new JPanel();
	   panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), panel_name));
       panel.setBounds(500, 400, 500, 1020);   
	   return panel;
   }
   
   /**
    * Method to create a button
    * @param button_text which is the button name
    * @return the button
    */
   public JButton makeButtons(String button_text)
   {
        JButton button = new JButton(button_text);		        
        return button;        
   }
   
   /**
    * Method to get the text from the textfield
    * @param t which is the text field
    * @return the string from text field
    */
   public String getUserInput(JTextField t)
   {
	   return t.getText();
   }

}
