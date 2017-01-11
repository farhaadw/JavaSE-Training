package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import system.Order;
import system.Product;
import system.PurchaseOrder;

public class OrderTest {

	@Test
	public void testToString() {
		PurchaseOrder ord = new PurchaseOrder(1, null, "CheckedOut", 2);
		Order ordd = ord;
		assertEquals("Order [orderID=1, status=CheckedOut, products=/n]",
				ordd.toString());

		List<Product> productList = new ArrayList<Product>();
		productList.add(new Product(1, "test", 3, 1, 2, true));
		ord = new PurchaseOrder(1, productList, "CheckedOut", 2);
		ordd = ord;

		assertEquals(
				"Order [orderID=1, status=CheckedOut, products=/nProduct [productID=1, productName=test, quantity=3, row=1, column=2, requiresPorousware=true]/n]",
				ordd.toString());

		ord = new PurchaseOrder(1, new ArrayList<Product>(), "CheckedOut", 2);
		ordd = ord;

		assertEquals("Order [orderID=1, status=CheckedOut, products=/n]",
				ordd.toString());

	}

}
