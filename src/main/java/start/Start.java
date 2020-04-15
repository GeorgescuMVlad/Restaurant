package start;

import presentation.AdministratorGUI;
import presentation.WaiterGUI;

/**
 * The driver class of the program that starts the application
 * @see AdministratorGUI
 * @see WaiterGUI
 */
public class Start {

	public static void main(String[] args) {
		new AdministratorGUI();
		new WaiterGUI();
	}

}
