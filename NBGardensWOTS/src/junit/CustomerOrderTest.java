package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import system.CustomerOrder;
import system.Order;
import system.Product;
import system.CustomerOrder;

public class CustomerOrderTest {

	@Test
	public void testToString() {
		assertEquals("CustomerOrder [customerID=55]", new CustomerOrder(5,
				null, "checkedout", 55).toString());
	}

	@Test
	public void testCustomerOrder() {
		assertNotNull(new CustomerOrder());
	}

	@Test
	public void testCustomerOrderIntListOfProductStringInt() {
		ArrayList<Product> productList = new ArrayList<Product>();
		productList.add(new Product(1, "test", 3, 1, 2, true));
		productList.add(new Product(2, "test1", 3, 1, 2, true));

		CustomerOrder co = new CustomerOrder(1, productList, "CheckedOut", 5);
		assertEquals(1, co.getOrderID());
		assertEquals(2, co.getProducts().size());
		assertEquals("CheckedOut", co.getStatus());
		assertEquals(5, co.getCustomerID());
	}

	@Test
	public void testGetCustomerID() {
		assertEquals(5,
				new CustomerOrder(1, null, "CheckedOut", 5).getCustomerID());
	}

	@Test
	public void testOutput() {
	}

	@Test
	public void testOrder() {
		CustomerOrder ord = new CustomerOrder(1, null, "CheckedOut", 2);
		assertNotNull((Order) ord);
	}

	@Test
	public void testOrderIntListOfProductString() {
		CustomerOrder ord = new CustomerOrder(1, null, "CheckedOut", 2);
		assertEquals(1, ord.getOrderID());
		assertNull(ord.getProducts());
		assertEquals("CheckedOut", ord.getStatus());
		assertEquals(2, ord.getCustomerID());
	}

	@Test
	public void testGetOrderID() {
		assertEquals(1, new CustomerOrder(1, null, "CheckedOut", 2).getOrderID());
	}

	@Test
	public void testGetProducts() {
		assertNull(new CustomerOrder(1, null, "CheckedOut", 2).getProducts());
	}

	@Test
	public void testGetStatus() {
		assertEquals("CheckedOut", new CustomerOrder(1, null, "CheckedOut", 2).getStatus());
	}

	@Test
	public void testSetStatus() {
		CustomerOrder ord = new CustomerOrder(1, null, "CheckedOut", 2);
		ord.setStatus("OutForDelivery");
		assertEquals("OutForDelivery", ord.getStatus());
	}

}
