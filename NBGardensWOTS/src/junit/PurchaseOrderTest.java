package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import system.Order;
import system.PurchaseOrder;

public class PurchaseOrderTest {

	@Test
	public void testPurchaseOrder() {
		assertNotNull(new PurchaseOrder());
	}

	@Test
	public void testPurchaseOrderIntListOfProductStringInt() {
		assertNotNull(new PurchaseOrder(1, null, "CheckedOut", 2));
	}

	@Test
	public void testGetSupplierID() {
		assertEquals(2, new PurchaseOrder(1, null, "CheckedOut", 2).getSupplierID());
	}

	@Test
	public void testOutput() {
	}

	@Test
	public void testOrder() {
		PurchaseOrder ord = new PurchaseOrder(1, null, "CheckedOut", 2);
		assertNotNull((Order) ord);
	}

	@Test
	public void testOrderIntListOfProductString() {
		PurchaseOrder ord = new PurchaseOrder(1, null, "CheckedOut", 2);
		assertEquals(1, ord.getOrderID());
		assertNull(ord.getProducts());
		assertEquals("CheckedOut", ord.getStatus());
		assertEquals(2, ord.getSupplierID());
	}

	@Test
	public void testGetOrderID() {
		assertEquals(1, new PurchaseOrder(1, null, "CheckedOut", 2).getOrderID());
	}

	@Test
	public void testGetProducts() {
		assertNull(new PurchaseOrder(1, null, "CheckedOut", 2).getProducts());
	}

	@Test
	public void testGetStatus() {
		assertEquals("CheckedOut", new PurchaseOrder(1, null, "CheckedOut", 2).getStatus());
	}

	@Test
	public void testSetStatus() {
		PurchaseOrder ord = new PurchaseOrder(1, null, "CheckedOut", 2);
		ord.setStatus("OutForDelivery");
		assertEquals("OutForDelivery", ord.getStatus());
	}

}
