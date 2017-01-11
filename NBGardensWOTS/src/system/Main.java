/**
 * 
 */
package system;

import java.sql.SQLException;

/**
 * @author Administrator
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseHandler handler = null;
		try {
			handler = new DatabaseHandler();
			handler.getProduct(1);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
