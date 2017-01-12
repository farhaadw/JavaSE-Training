/**
 * Class that manages system operations.
 * Windows change.
 */
package system;

import java.sql.SQLException;
import java.util.List;

import org.junit.internal.runners.statements.Fail;

/**
 * @author Administrator
 *
 */
public class WOTS {

	/**
	 * Object reference of type database handler that handles connection to
	 * database.
	 */
	DatabaseHandler handler;

	/**
	 * The ID of the employee
	 */
	private int employeeID;

	/**
	 * List of checked out customer orders.
	 */
	private List<CustomerOrder> checkedOutOrders;

	/**
	 * List of assigned purchase orders.
	 */
	private PurchaseOrder recievedPurchaseOrder;

	/**
	 * Construct new WOTS object with attributes.
	 * 
	 * @param employeeID
	 *            - The id of the employee
	 * @param checkedOutOrders
	 *            - The List of checked out orders
	 * @param recievedPurchaseOrder
	 *            - The received purchase order
	 */
	public WOTS(int userId) {
		this.employeeID = userId;
		// this.checkedOutOrders = checkedOutOrders;
		// this.recievedPurchaseOrder = recievedPurchaseOrder;

		try {
			handler = new DatabaseHandler();
		} catch (SQLException e) {
			System.out.println("An SQL exception occured during setting up the database handler...");
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			System.out.println("A connection could not be made to the db...");
			e.printStackTrace();
		}
	}

	// -----------------------------------------
	// GETTERS AND SETTERS
	// -----------------------------------------
	
	public void setCheckedOutOrders(List<CustomerOrder> checkedOutOrders) {
		this.checkedOutOrders = checkedOutOrders;
	}

	public void setRecievedPurchaseOrder(PurchaseOrder recievedPurchaseOrder) {
		this.recievedPurchaseOrder = recievedPurchaseOrder;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public List<CustomerOrder> getCheckedOutOrders() {
		return checkedOutOrders;
	}

	public PurchaseOrder getRecievedPurchaseOrder() {
		return recievedPurchaseOrder;
	}

	// --------------------------------
	// Customer Order Methods
	// --------------------------------
	
	/**
	 * Method that checks out order using the order ID. If successful the
	 * checked out order is added to the list.
	 * 
	 * @param orderID
	 *            - The orderID to query for an order.
	 */
	public void checkOutCustomerOrder() {
		try {
			// Get the unchecked out order that has been waiting the longest
			CustomerOrder custOrd = handler.getUncheckedOutCustomerOrder();
			if (custOrd != null) {
				// Check the items on the order are still in stock
				boolean itemsInStock = checkItemsInStock(custOrd.getProducts());
	
				if (itemsInStock) {
					// check out the order in the database
					handler.checkOutCustomerOrder(getEmployeeID(), custOrd.getOrderID());
					// Reduce the stock level for the checked out order in the database
					for (Product prod : custOrd.getProducts())
						reduceStockByProduct(prod);
					// refresh the systems employees checked out orders list to
					// reflect the changes in the system
					retrieveCheckedOutOrders();
				}
				else {
					// Should set the customer order status to awaiting stock otherwise method will always look to check out the same order
					// Resulting in no customer orders being able to be checked out, so throw exception for now
					throw new Exception("Customer Order has product quantity requirements greater than stock.");
				}
			}
			else {
				// Need a way of feeding back to the interface that there were no orders to check out.
				throw new Exception("There were no customer orders to check out.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to uncheck out and employees checked out customer order by customer order id
	 * un checking out and order re adds all the stock that was removed for the order on
	 * check out.
	 * @param orderID The order id to uncheck out
	 */
	public void unCheckOutCustomerOrder(int orderID) {
		try {
			CustomerOrder ordToRemove = getCustomerOrderFromCheckedOut(orderID);
			if (ordToRemove != null) {
				handler.unCheckOutCustomerOrder(orderID);
				
				for (Product prod : ordToRemove.getProducts())
					increaseStockByProduct(prod);
			}
			// refresh the systems employees checked out orders list to
			// reflect the changes in the system
			retrieveCheckedOutOrders();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to be called on customer order completion. Sets status in db to complete and reflects change in system
	 * @param orderID
	 */
	public void completeCustomerOrder(int orderID) {
		try {
			handler.completeCustomerOrder(orderID);
			// Refresh systems checked out orders list to reflect changes in system
			retrieveCheckedOutOrders();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to get the customer order address for employee to write delivery address on order dispatch
	 * @param orderID
	 * @return The customer address or an error message to call accounts for address if not found
	 */
	public String getCustomerOrderAddress(int orderID) {
		String returnS = "Error getting address for customer orderID: " + orderID + ". Call accounts for address";
		try {
			CustomerOrder ord = getCustomerOrderFromCheckedOut(orderID);
			if (ord != null) {
				Customer cust = handler.getCustomer(ord.getCustomerID());
				if (cust != null)
					returnS = cust.getCustomerName() + ", " + ", " + cust.getAddress() + ", " + cust.getPostcode();
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return returnS;
	}
	
	public CustomerOrder pickCustomerOrder(int orderID) {
		CustomerOrder order = getCustomerOrderFromCheckedOut(orderID);
		if (order != null) {
			List<Product> productsOrdered = getShortestRouteForCustomerOrder(order.getProducts());
			order.getProducts().clear();
			order.getProducts().addAll(productsOrdered);
		}
		return order;
	}
	
	
	// --------------------------------
	// Purchase Order Methods
	// --------------------------------
	
	/**
	 *  Method to get the purchase order that the employee has checked out. 
	 *  This will get the order from the database as well as update the system 
	 *  retrieve purchase order. 
	 */
	public void retrievePurchaseOrder(){
		try {
			setRecievedPurchaseOrder(handler.getCheckedOutPurchaseOrder(getEmployeeID()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  Method that checks out a purchase order.
	 */
	public void checkOutPurchaseOrder(){
		// this will update the system so that it is in sync with the database. 
		retrievePurchaseOrder();
		// if null then the employee has not checked out a purchase order. 
		if(getRecievedPurchaseOrder() == null){
			// get unchecked out purchase order
			PurchaseOrder purchaseOrder;
			try {
				purchaseOrder = handler.getUncheckedOutPurchaseOrder();
			} catch (SQLException e1) {
				e1.printStackTrace();
				purchaseOrder = null;
			}
			if(purchaseOrder != null){
				try {
					handler.checkOutPurchaseOrder(getEmployeeID(), getRecievedPurchaseOrder().getOrderID());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			retrievePurchaseOrder();
		}
	}
	
	// --------------------------------
	// Travelling Salesman Methods
	// --------------------------------

	/**
	 * Currently contains no functionality.
	 * When implemented the method will return list of products in the order they 
	 * should be picked
	 * @param order
	 * @return CustomerOrder with products list ordered by 1st to last to be picked
	 */
	public List<Product> getShortestRouteForCustomerOrder(List<Product> products) {
		// Travelling salesman not currently implemented so returns the customer order as is 
		return products;
	}

	

	/**
	 * Method to complete the warehouse worker checkout process. Order status
	 * set to out for delivery Order removed from employees checked out order
	 * list Record made of employee who checked out order Accounts updated so
	 * aware of order dispatch
	 * 
	 * @param orderID
	 */
	public void orderDispatched(int orderID) {
		// Remove checked out order association with employee
		// Move order status to dispatched
		// Log order handles by employee
		// Update accounts
	}

	
	
	// ----------------------------------------------------
	// HELPER and WORKER METHODS
	// ----------------------------------------------------
	
	/**
	 * Helper Method to checked a list of Products stock levels in the database.
	 * Useful for checking if a list of products is in stock before allowing an 
	 * order to be check out and again before going to pick a checked out order.
	 * @param products
	 * @return if all items in the list are in stock
	 */
	public boolean checkItemsInStock(List<Product> products) {
		boolean itemsInStock = true;
		for (Product prod : products) {
			if (!checkItemInStock(prod)) {
				itemsInStock = false;
				break;
			}
		}
		return itemsInStock;
	}
	
	/**
	 * Helper Method to check a products stock levels in the database.
	 * 
	 * @param product
	 * @return item is in stock
	 */
	public boolean checkItemInStock(Product product) {
		return ((product.getQuantity() > checkStockLevel(product.getProductID())) ? false : true);
	}
	
	/**
	 * Method called to reduce to stock level in db for a specific product.
	 * 
	 * @param product
	 *            - The product object to reduce stock using quantity value.
	 */
	private void reduceStockByProduct(Product prod) {
		try {
			handler.reduceStock(prod.getProductID(), prod.getQuantity());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Method called to increase stock level in db for a specific product.
	 * 
	 * @param prod
	 *            - The product object to increase stock using quantity value.
	 */
	private void increaseStockByProduct(Product prod) {
		try {
			handler.increaseStock(prod.getProductID(), prod.getQuantity());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Method to retrieve the current checked out orders and update the systems
	 * checked out order array list. Acts as a refresh checked out orders list.
	 */
	private void retrieveCheckedOutOrders() {
		// Clear the systems checked out orders list
		this.getCheckedOutOrders().clear();
		try {
			// Retrieve the current checked out orders for the employee and put into systems list
			this.getCheckedOutOrders().addAll(handler.getCheckedOutCustomerOrders(employeeID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method called on a customer or purchase order check out. Calls reduce or
	 * increase stock update methods depending on the order instance (reduce
	 * stock if instance of customer order) (increase stock if instance of
	 * purchase order)
	 * 
	 * @param orderID
	 *            - Id of the order to have its stock levels updated
	 */
	public void updateStock(Order order) {
		if (order instanceof CustomerOrder) {
			CustomerOrder custOrd = (CustomerOrder) order;
			for (Product prod : custOrd.getProducts()) {
				reduceStockByProduct(prod);
			}
		} else if (order instanceof PurchaseOrder) {
			PurchaseOrder purchaseOrd = (PurchaseOrder) order;
			for (Product prod : purchaseOrd.getProducts()) {
				increaseStockByProduct(prod);
			}
		}
	}
	
	/**
	 * Method to return the stock level of a product by productID
	 * 
	 * @param productID
	 *            - The productID to return the stock level off
	 */
	public int checkStockLevel(int productID) {
		// set initial stock level to 0.
		int stockLevel = 0;
		try {
			// if product found update the variable with products stock level.
			stockLevel = handler.getProductStockLevel(productID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return stock level
		return stockLevel;
	}
	
	/**
	 * Method to remove all quantity of stock for a specific product
	 * 
	 * @param productID
	 *            - The productID to remove all stock for
	 */
	public void removeStock(int productID) {
		try{
			handler.reduceStock(productID, handler.getProductStockLevel(productID));
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to return a customer Order object from employees checked out orders by order id
	 * @param orderID
	 * @return Returns the customer order object otherwise returns null if not found
	 */
	public CustomerOrder getCustomerOrderFromCheckedOut(int orderID) {
		for (CustomerOrder ord : getCheckedOutOrders()) {
			if (ord.getOrderID() == orderID) {
				return ord;
			}
		}
		return null;
	}
}
