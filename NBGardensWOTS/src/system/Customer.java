/**
 * 
 */
package system;

/**
 * @author Administrator
 *
 */
public class Customer implements Info {

	/**
	 * The customer ID.
	 */
	private int customerID;

	/**
	 * The customer name
	 */
	private String customerName;

	/**
	 * The address of the customer.
	 */
	private String address;

	/**
	 * The postcode of the customer.
	 */
	private String postcode;

	public Customer(int customerID, String customerName, String address,
			String postcode) {
		this.customerID = customerID;
		this.customerName = customerName;
		this.address = address;
		this.postcode = postcode;
	}

	public int getCustomerID() {
		return customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAddress() {
		return address;
	}

	public String getPostcode() {
		return postcode;
	}

	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", customerName="
				+ customerName + ", address=" + address + ", postcode="
				+ postcode + "]";
	}

	@Override
	public void output() {
		// TODO Auto-generated method stub
	}
}
