/**
 *  Customer Order Class 
 */
package system;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class CustomerOrder extends Order {

	/**
	 * The ID of the customer.
	 */
	private int customerID;

	public CustomerOrder() {

	}

	public CustomerOrder(int orderID, List<Product> products, String status,
			int customerID) {
		super(orderID, products, status);
		this.customerID = customerID;
	}

	public int getCustomerID() {
		return customerID;
	}

	@Override
	public String toString() {
		return "customerID=" + customerID + ", " + super.toString();
	}

	@Override
	public void output() {
		// TODO Auto-generated method stub
	}

}
