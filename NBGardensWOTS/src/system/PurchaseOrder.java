/**
 * 
 */
package system;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class PurchaseOrder extends Order {

	/**
	 * The ID of supplier
	 */
	private int supplierID;

	public PurchaseOrder() {
	}

	public PurchaseOrder(int orderID, List<Product> products, String status,
			int supplierID) {
		super(orderID, products, status);
		this.supplierID = supplierID;
	}

	public int getSupplierID() {
		return supplierID;
	}

	@Override
	public void output() {
	}

}
