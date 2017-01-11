package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import system.Product;

public class ProductTest {

	@Test
	public void testProduct() {
		assertNotNull(new Product());
	}

	@Test
	public void testProductIntStringIntFloatFloatBoolean() {
		Product product = new Product(1, "test", 3, 1, 2, true);
		assertNotNull(product);
		assertEquals(1, product.getProductID());
		assertEquals("test", product.getProductName());
		assertEquals(3, product.getQuantity());
		assertEquals(1, product.getRow());
		assertEquals(2, product.getColumn());
		assertEquals(true, product.getRequiresPorousware());
	}

	@Test
	public void testGetProductID() {
		assertEquals(1, new Product(1, "test", 3, 1, 2, true).getProductID());
	}

	@Test
	public void testGetProductName() {
		assertEquals("test",
				new Product(1, "test", 3, 1, 2, true).getProductName());
	}

	@Test
	public void testGetQuantity() {
		assertEquals(3, new Product(1, "test", 3, 1, 2, true).getQuantity());
	}

	@Test
	public void testgetRow() {
		assertEquals(1, new Product(1, "test", 3, 1, 2, true).getRow());
	}

	@Test
	public void testgetColumn() {
		assertEquals(2, new Product(1, "test", 3, 1, 2, true).getColumn());
	}

	@Test
	public void testGetRequiresPorousware() {
		assertEquals(true,
				new Product(1, "test", 3, 1, 2, true).getRequiresPorousware());
	}

	@Test
	public void testToString() {
		Product product = new Product(1, "test", 3, 1, 2, true);
		assertEquals(
				"Product [productID=1, productName=test, quantity=3, row=1, column=2, requiresPorousware=true]",
				product.toString());
	}

	@Test
	public void testOutput() {
	}

	@Test
	public void testFindProductLocation() {
		Product product = new Product(1, "test", 3, 1, 2, true);
		assertEquals("Row: 1 Column: 2", product.findProductLocation());
	}

}
