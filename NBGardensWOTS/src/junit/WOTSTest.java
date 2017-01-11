package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import system.CustomerOrder;
import system.PurchaseOrder;
import system.WOTS;

public class WOTSTest {

	@Test
	public void testWOTS() {
		assertNotNull(new WOTS(5));

		assertNotNull(new WOTS(5).getCheckedOutOrders());
		assertEquals(100, new WOTS(100).getEmployeeID());
	}

	@Test
	public void testGetEmployeeID() {
		assertEquals(5, new WOTS(5).getEmployeeID());
	}

	@Test
	public void testGetCheckedOutOrders() {
		WOTS wots = new WOTS(5);
		wots.setCheckedOutOrders(new ArrayList<CustomerOrder>());
		assertNotNull(wots);
		assertNotNull(wots.getCheckedOutOrders());
	}

	@Test
	public void testGetRecievedPurchaseOrder() {
		WOTS wots = new WOTS(5);
		wots.setRecievedPurchaseOrder(new PurchaseOrder(11, null, "Delivered",
				100));
		assertNotNull(wots.getRecievedPurchaseOrder());
		assertEquals(100, wots.getRecievedPurchaseOrder().getSupplierID());
	}

	@Test
	public void testCheckOutOrder() {
		WOTS wots = new WOTS(5);
		wots.checkOutCustomerOrder();
		assertNotNull(wots.getCheckedOutOrders());
		Boolean isTrue = false;
		for (CustomerOrder order : wots.getCheckedOutOrders()) {
			if (order.getOrderID() == 12) {
				isTrue = true;
			}
		}
		assertEquals(true, isTrue);
	}

	@Test
	public void testUpdateStock() {
		CustomerOrder custOrd = new CustomerOrder();

	}

	@Test
	public void testIncreaseStockByProduct() {
		Assert.fail("This is not yet implemented");
	}

	@Test
	public void testReduceStockByProduct() {
		Assert.fail("This is not yet implemented");
	}

}
