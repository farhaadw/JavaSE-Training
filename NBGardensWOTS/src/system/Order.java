/**
 *  Abstract order class that holds order information. 
 */
package system;

import java.util.List;

/**
 * @author Administrator
 *
 */
public abstract class Order implements Info {

	/**
	 * The ID of the order
	 */
	private int orderID;

	/**
	 * List of products added to an order.
	 */
	private List<Product> products;

	/**
	 * Status of the order
	 */
	private String status;

	/**
	 * Default constructor
	 */
	public Order() {
	}

	/**
	 * Constructor to initialise an order object with fields.
	 * 
	 * @param products
	 *            - A list of products.
	 * @param status
	 *            - status of a product.
	 */
	public Order(int orderID, List<Product> products, String status) {
		super();
		this.orderID = orderID;
		this.products = products;
		this.status = status;
	}

	public int getOrderID() {
		return orderID;
	}

	public List<Product> getProducts() {
		return products;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order [orderID=" + orderID + ", status=" + status
				+ ", products=/n");
		if (products != null) {
			for (Product prod : products) {
				sb.append(prod.toString() + "/n");
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
