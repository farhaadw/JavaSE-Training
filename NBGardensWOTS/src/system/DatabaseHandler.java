/**
 * 
 */
package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class DatabaseHandler {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/nbgardens_central_db";
	private static final String USER = "root";
	private static final String PASS = "password";
	private static Connection conn = null;
	private static Statement stmt = null;
	private static final String custOrderComplete = "Dispatched";
	private static final String custOrderUnCheckedOut = "UnCheckedOut";
	private static final String custOrderCheckedOut = "CheckedOut";
	private static final String purchOrderDelivered = "Delivered";
	private static final String purchOrderCheckedOut = "CheckedOut";
	private static final String purchOrderComplete = "Complete";

	/**
	 * Constructor that connects to mysql database on initialisation.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DatabaseHandler() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
	}

	// --------------
	/**
	 * Update Methods
	 */
	// --------------
	
	/**
	 * Method to reduce stock level.
	 * 
	 * @param productiD
	 * @throws SQLException
	 */
	public void reduceStock(int productId, int quantity) throws SQLException {
		stmt.executeUpdate("update products set stock_level = ( stock_level - " + quantity + " ) where product_id=" + productId);
	}

	/**
	 * Method to increase stock level.
	 * 
	 * @param productiD
	 * @throws SQLException
	 */
	public void increaseStock(int productId, int quantity) throws SQLException {
		stmt.executeUpdate("update products set stock_level = ( stock_level + " + quantity + " ) where product_id=" + productId);
	}
	
	/**
	 * Method to update customer order to checked out
	 * 
	 * @param employeeID - The employeeID checking out the order
	 * @param customerOrderID - The customerOrderID of the order being checked out
	 * @throws SQLException
	 */
	public void checkOutCustomerOrder(int employeeID, int customerOrderID) throws SQLException {
		stmt.executeUpdate("update customer_order set status =" + custOrderCheckedOut + " and employee_id=" + customerOrderID + "where customer_order_id=" + customerOrderID);
	}
	
	/**
	 * Method to update customer order to checked out
	 * 
	 * @param employeeID - The employeeID checking out the order
	 * @param purchaseOrderID - The purchaseOrderID of the order being checked out
	 * @throws SQLException
	 */
	public void checkOutPurchaseOrder(int employeeID, int purchaseOrderID) throws SQLException {
		stmt.executeUpdate("update purchase_order set status =" + purchOrderCheckedOut + "and employee_id=" + employeeID + "where purchae_order_id=" + purchaseOrderID);
	}
	
	/**
	 * Method to un check out a customer order. 
	 * Sets customer order status back to unchecked out, 
	 * removes assigned employeeid and 
	 * re adds stock to product stock levels
	 * @param CustomerOrder - CustomerOrder to uncheck out 
	 * @throws SQLException
	 */
	public void unCheckOutCustomerOrder(int custOrderID) throws SQLException {
		stmt.executeUpdate("update customer_order set status = " + custOrderUnCheckedOut + "and employee_id=" + null + "where customer_order_id="+custOrderID);
	}
	
	/**
	 * Method to uncheck out a purchase order.
	 * Sets purchase order status back to unchecked out,
	 * removes assingned employee id and
	 * re adds stock to product stock levels
	 * @param purchaseOrderID - Purchase Order to un check out
	 * @throws SQLException
	 */
	public void unCheckOutPurchaseOrder(int purchaseOrderID) throws SQLException {
		stmt.executeUpdate("update purchase_order set status = " + purchOrderDelivered + "and employeeID=" + null + "where purchase_order_id=" + purchaseOrderID);
	}
	
	/**
	 * Method to complete a checked out customer order setting its status to complete
	 * @param customerOrderID - The id of the customer order to be completed.
	 * @throws SQLException
	 */
	public void completeCustomerOrder(int customerOrderID) throws SQLException {
		stmt.executeUpdate("update customer_order set status = " + custOrderComplete + "where customer_order_id=" + customerOrderID);
	}
	
	public void completePurchaseOrder(int purchaseOrderID) throws SQLException {
		stmt.executeUpdate("update purchase_order set status = " + purchOrderComplete + "where purchase_order_id=" + purchaseOrderID);
	}
	
	// --------------
	/**
	* Get Methods
	*/
	// --------------
	
	/**
	 * Method to get product by id.
	 * **WARNING** THIS METHOD RETURNS THE PRODUCT STOCK LEVEL AS THE PRODUCT QUANTITY **WARNING**
	 * DO NOT CALL FOR ORDER CLASS CREATION
	 * @param productiD
	 * @throws SQLException
	 * 
	 */
	public Product getProduct(int productID) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from products where product_id=" + productID);
		rs.absolute(1);
		return new Product(rs.getInt("product_id"),
				rs.getString("product_name"), rs.getInt("stock_level"),
				rs.getInt("row_number"), rs.getInt("column_number"),
				rs.getBoolean("porousware"));
	}
	
	/**
	 * Method to get products stock level
	 * 
	 * @param productID
	 *            - The product id to query
	 * @return - The stock level of the product
	 * @throws SQLException
	 */
	public int getProductStockLevel(int productID) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from products where product_id=" + productID);
		rs.absolute(1);
		return rs.getInt("stock_level");
	}
	
	/**
	 * Method to get customer by customer id
	 * 
	 * @param customerID
	 * @throws SQLException
	 */
	public Customer getCustomer(int customerID) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from customers where customer_id = " + customerID);
		rs.absolute(1);
		return new Customer(rs.getInt("customer_id"), rs.getString("customer_name"), rs.getString("address"), rs.getString("postcode"));
	}
	
	/**
	 * Method to get all products in customer order.
	 * 
	 * @return Array list of products.
	 * @throws SQLException
	 */
	public List<Product> getProductsInCustomerOrder(int orderId) throws SQLException {
		List<Product> productList = new ArrayList<Product>();
		ResultSet rs = stmt.executeQuery("SELECT p.*, c.* FROM products p join customer_order_link c on p.product_id = c.product_id where c.customer_order_id = "+ orderId);
		while (rs.next()) {
			productList.add(new Product(rs.getInt("product_id"), rs
					.getString("product_name"), rs.getInt("quantity"), rs
					.getInt("row_number"), rs.getInt("column_number"), rs
					.getBoolean("porousware")));
		}
		return productList;
	}

	/**
	 * Method to get all products in purchase order.
	 * 
	 * @param orderId
	 * @return Array list of products.
	 * @throws SQLException
	 */
	public List<Product> getProductsInPurchaseOrder(int orderId) throws SQLException {
		List<Product> productList = new ArrayList<Product>();
		ResultSet rs = stmt.executeQuery("SELECT p.*, pp.* FROM products p JOIN purchase_order_link pp ON p.product_id = pp.product_id WHERE pp.purchaseOrderID = "+ orderId);
		while (rs.next()) {
			productList.add(new Product(rs.getInt("product_id"), rs
					.getString("product_name"), rs.getInt("quantity"), rs
					.getInt("row_number"), rs.getInt("column_number"), rs
					.getBoolean("porousware")));
		}
		return productList;
	}

	/**
	 * Method to get list of checked out orders for specified employee.
	 * 
	 * @param employeeID
	 * @return - The list of customer orders the employee is associated with.
	 * @throws SQLException
	 */
	public List<CustomerOrder> getCheckedOutCustomerOrders(int employeeID) throws SQLException {
		List<CustomerOrder> productList = new ArrayList<CustomerOrder>();
		Statement stmt2 = conn.createStatement();
		ResultSet resultSet = stmt2.executeQuery("SELECT * FROM customer_order where employee_id = " + employeeID + " AND status = \"checked out\" ");
		while (resultSet.next()) {
			int custOrdID = resultSet.getInt("customer_order_id");
			productList.add(new CustomerOrder(custOrdID,
					getProductsInCustomerOrder(custOrdID), resultSet
							.getString("status"), resultSet
							.getInt("customer_id")));
		}
		return productList;
	}

	/**
	 * Method to get checked out purchase order the employee is associated with.
	 * 
	 * @param employeeID
	 * @return - The purchase order object.
	 * @throws SQLException
	 */
	public PurchaseOrder getCheckedOutPurchaseOrder(int employeeID) throws SQLException {
		Statement stmt2 = conn.createStatement();
		ResultSet rs = stmt2.executeQuery("SELECT * FROM purchase_order where employeeid = " + employeeID + " AND status = \"CheckedOut\" ");
		rs.absolute(1);
		int purchaseOrderId = rs.getInt("purchase_order_id");
		return new PurchaseOrder(
				rs.getInt(purchaseOrderId),
				getProductsInPurchaseOrder(purchaseOrderId),
				rs.getString("status"), rs.getInt("supplierID"));
	}

	/**
	 * Method to get unchecked customer order by date.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public CustomerOrder getUncheckedOutCustomerOrder() throws SQLException {
		Statement stmt2 = conn.createStatement();
		ResultSet rs = stmt2.executeQuery("SELECT * FROM nbgardens_central_db.customer_order WHERE status = \"UnCheckedOut\" ORDER BY date_placed ASC LIMIT 1");
		rs.absolute(1);
		int customerOrderId = rs.getInt("customer_order_id");
		return new CustomerOrder(customerOrderId,
				getProductsInCustomerOrder(customerOrderId),
				rs.getString("status"), rs.getInt("customer_id"));
	}

	/**
	 * Method to get purchase order with status of delivered.
	 * 
	 * @return - An array list of purchase orders.
	 * @throws SQLException
	 * 
	 */
	public PurchaseOrder getUncheckedOutPurchaseOrder() throws SQLException {
		Statement stmt2 = conn.createStatement();
		ResultSet rs = stmt2.executeQuery("SELECT * FROM nbgardens_central_db.purchase_order WHERE status =  \"Delivered\" ORDER BY date_placed ASC LIMIT 1");
		int purchaseOrderId = rs.getInt("purchase_order_id");
		return new PurchaseOrder(purchaseOrderId,
				getProductsInPurchaseOrder(purchaseOrderId),
				rs.getString("status"), rs.getInt("supplierID"));
	}
}
