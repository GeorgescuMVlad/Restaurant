package presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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

import business.MenuItem;
import business.Order;
import business.Restaurant;

/**
 * Class WaiterGUI is a presentation class which realises the UI and implements the listeners on buttons and constructs the JTable with the 
 * orders. Aslo, whenever it is needed to be decided which kind of MenuItem is the one at a specific moment, the reflexion technique is being used 
 * in order to get the class of that MenuItem(BaseProduct or CompositeProduct since the MenuItem is an abstract class) and to get the methods 
 * that need to be invoked from that class.
 * @author vladg
 * @see Restaurant
 * @see Order
 */
public class WaiterGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public JTextField t0=makeTextFields();
	public JTextField t1=makeTextFields();
	public JTextField t2=makeTextFields();
	
	static DefaultTableModel models = new DefaultTableModel();
	static JTable table = new JTable(models);
	
    JPanel panel=makePrincipalPanels("Buttons Panel");
    JPanel panelOut=makePrincipalPanels("JTable Data Panel");
    
	SpringLayout springLayout = new SpringLayout();
	
	JFrame frame=makeFrame("Window for Waiter");
    
    JLabel idLabel=makeLabels("OrderID: ");
    JLabel dateLabel=makeLabels("Date: ");
    JLabel tableLabel=makeLabels("Table: ");
    
    public JButton addButton=makeButtons("Add Order");
    public JButton billButton=makeButtons("Compute Bill");
    List<MenuItem> items = new ArrayList<MenuItem>();
    
   /** Constructor to setup the GUI */
   public WaiterGUI() {        
        frame.add(panel);
        frame.add(panelOut);
        
        panel.add(idLabel);
        panel.add(t0);
        panel.add(dateLabel);
        panel.add(t1);
        panel.add(tableLabel);
        panel.add(t2);
        
        panel.add(addButton);
        panel.add(billButton);
        
        panel.setLayout(springLayout);
        springLayout.putConstraint(SpringLayout.WEST, t0, 90, SpringLayout.WEST, idLabel);
   
        springLayout.putConstraint(SpringLayout.SOUTH, t1, 40, SpringLayout.SOUTH, t0);
        springLayout.putConstraint(SpringLayout.WEST, t1, 90, SpringLayout.WEST, dateLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, dateLabel, 40, SpringLayout.SOUTH, idLabel);
        
        springLayout.putConstraint(SpringLayout.SOUTH, tableLabel, 40, SpringLayout.SOUTH, dateLabel);
        springLayout.putConstraint(SpringLayout.WEST, t2, 90, SpringLayout.WEST, tableLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, t2, 40, SpringLayout.SOUTH, t1);
        
        springLayout.putConstraint(SpringLayout.SOUTH, addButton, 60, SpringLayout.SOUTH, tableLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, addButton, 60, SpringLayout.SOUTH, t2);
            
        springLayout.putConstraint(SpringLayout.SOUTH, billButton, 60, SpringLayout.SOUTH, tableLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, billButton, 60, SpringLayout.SOUTH, t2);
        springLayout.putConstraint(SpringLayout.WEST, billButton, 110, SpringLayout.WEST, addButton);
               
        models.addColumn("Order ID");
        models.addColumn("Date");
        models.addColumn("Table"); 
        models.addColumn("Total Order Price"); 
        
		JScrollPane pa = new JScrollPane(table);
       
        pa.setPreferredSize(new Dimension(765, 315));
        pa.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        
        addButton.addActionListener(this);
        billButton.addActionListener(this);
        
        panelOut.add(pa);
        frame.setVisible(true);
        }
   
   HashMap<Order, ArrayList<MenuItem>> map;
   public void actionPerformed(ActionEvent e) {
	   String orderId=getUserInput(t0);
	   String date=getUserInput(t1);
	   String guiTable=getUserInput(t2);
	   int id=Integer.parseInt(orderId);
	   int table=Integer.parseInt(guiTable);
	   items=AdministratorGUI.getItems();
	   Restaurant res= new Restaurant(items);
	   Order ord=new Order(id, date, table);
	   
	   /* -If the waiter wants to add a new order, the items from the admin table are taken and the list ot menu items is traced and
	    for each item it is taken its class and then the methods to get its name and price through reflexion. The price of order is incremented
	    with the priceof each selected item and the selected items are added into the order list of menu items. Then the order and the list of
	    ordered items are associated using a hash map that stores the orders and the table is being updated with the new created order.
	    - If the waiter wants to generate a bill for one order, then a new order with the selected order from the table is made and the keys
	    of the hash table are being traced and if the order selected from the table is equal with the one from the map, then the list of values
	    is traced and for each item is obtained the class and the method to get its name through reflexion and the name is added to a list
	    of strings containing the names of the order products. Then the method to compute the order is called and the order related info is
	    saved into a file.
	     */
	   if (e.getSource() == addButton)
	   {
		   List<MenuItem> associatedItems = new ArrayList<MenuItem>();
		   map = new HashMap<Order, ArrayList<MenuItem>>();
		   int[] rows=AdministratorGUI.table.getSelectedRows();
		   int orderPrice=0;
		   ArrayList<String> orderedProducts=new ArrayList<String>();
		   for(int j=0; j<rows.length; j++){
			   for(int i=0; i<res.getItems().size(); i++){
					Class cls = res.getItems().get(i).getClass(); 
					if(cls.getName()=="business.BaseProduct"){
						Method met;
						Method met2;
						try {
							met = cls.getMethod("getName");
							met2 = cls.getMethod("getPrice");
							try {
								String reflectedName=(String) met.invoke(res.getItems().get(i));
								
								if(AdministratorGUI.models.getValueAt(rows[j], 0).toString()==met.invoke(res.getItems().get(i)))
								{
									Object obj= met2.invoke(res.getItems().get(i));
									orderPrice+=(Integer) obj;
									associatedItems.add(res.getItems().get(i));
									orderedProducts.add(reflectedName);
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
						Method met2;
						try {
							met = cls.getMethod("getName");
							met2 = cls.getMethod("getPrice");
							try {
								String reflectedName=(String) met.invoke(res.getItems().get(i));
								
								if(AdministratorGUI.models.getValueAt(rows[j], 0).toString()==met.invoke(res.getItems().get(i)))
								{
									Object obj= met2.invoke(res.getItems().get(i));
									orderPrice+=(Integer) obj;
									associatedItems.add(res.getItems().get(i));
									orderedProducts.add(reflectedName);
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
		   map.put(ord, (ArrayList<MenuItem>) associatedItems);
		   models.addRow(new Object[] {ord.orderId, ord.date, ord.table, orderPrice });
		   JOptionPane.showMessageDialog(this, "CHEF NOTIFY! New order made! The products that must be cooked are: " + orderedProducts);
	   }
	   
	   if (e.getSource() == billButton) {
		   int rw=WaiterGUI.table.getSelectedRow();
		   List<MenuItem> l = new ArrayList<MenuItem>();
		   ArrayList<String> orderedProducts=new ArrayList<String>();
		   int ordPrice=Integer.parseInt(models.getValueAt(rw, 3).toString());
		   Restaurant r=new Restaurant();
		   Order o=new Order(Integer.parseInt(models.getValueAt(rw, 0).toString()), models.getValueAt(rw, 1).toString(), Integer.parseInt(models.getValueAt(rw, 2).toString()));
		   for (Order or : map.keySet()) {
			   if(or.equals(o)){
				   l=map.get(o);
				   for(int i=0; i<l.size(); i++){
					   Class cls = l.get(i).getClass(); 
						if(cls.getName()=="business.BaseProduct"){
							Method met;
							try {
								met = cls.getMethod("getName");
								try {
									String reflectedName=(String) met.invoke(l.get(i));
									orderedProducts.add(reflectedName);
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
									String reflectedName=(String) met.invoke(l.get(i));
									orderedProducts.add(reflectedName);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
			   }
				   r.generateBill(o, ordPrice, orderedProducts);
		   }
	   }
	   }		
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
        int x = (int) ((dimension.getWidth() - frame.getWidth()) /1.2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) /1.2);
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
