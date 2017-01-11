/**
 *  Product class
 */
package system;

/**
 * @author Administrator
 *
 */
public class Product implements Info {

	/**
	 * The ID of the product.
	 */
	private int productID;

	/**
	 * The name of the product.
	 */
	private String productName;

	/**
	 * The quantity of same product.
	 */
	private int quantity;

	/**
	 * Location of product (row)
	 */
	private int row;

	/**
	 * Location of product (column)
	 */
	private int column;

	/**
	 * Determine if product requires porousware
	 */
	private Boolean requiresPorousware;

	/**
	 * Default constructor
	 */
	public Product() {

	}

	public Product(int productID, String productName, int quantity, int row,
			int column, Boolean requiresPorousware) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.quantity = quantity;
		this.row = row;
		this.column = column;
		this.requiresPorousware = requiresPorousware;
	}

	public int getProductID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Boolean getRequiresPorousware() {
		return requiresPorousware;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", productName="
				+ productName + ", quantity=" + quantity + ", row=" + row
				+ ", column=" + column + ", requiresPorousware="
				+ requiresPorousware + "]";
	}

	@Override
	public void output() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method to find location of the current product and display.
	 */
	public String findProductLocation() {
		return "Row: " + Integer.toString(getRow()) + " Column: "
				+ Integer.toString(getColumn());
	}
}
