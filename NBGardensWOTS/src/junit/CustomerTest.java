package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import system.Customer;

public class CustomerTest {

	@Test
	public void testCustomerIntStringStringString() {
		Customer customer = new Customer(22, "bob", "12 Grey Road, Manchester",
				"M5 6ae");
		assertEquals(22, customer.getCustomerID());
		assertEquals("bob", customer.getCustomerName());
		assertEquals("12 Grey Road, Manchester", customer.getAddress());
		assertEquals("M5 6ae", customer.getPostcode());

	}

	@Test
	public void testGetCustomerID() {
		assertEquals(22, new Customer(22, "bob", "12 Grey Road, Manchester",
				"M5 6ae").getCustomerID());
	}

	@Test
	public void testGetCustomerName() {
		assertEquals("bob", new Customer(22, "bob", "12 Grey Road, Manchester",
				"M5 6ae").getCustomerName());
	}

	@Test
	public void testGetAddress() {
		assertEquals("12 Grey Road, Manchester", new Customer(22, "bob",
				"12 Grey Road, Manchester", "M5 6ae").getAddress());
	}

	@Test
	public void testGetPostcode() {
		assertEquals("M5 6ae", new Customer(22, "bob",
				"12 Grey Road, Manchester", "M5 6ae").getPostcode());
	}

	@Test
	public void testToString() {
		Customer customer = new Customer(22, "bob", "12 Grey Road, Manchester",
				"M5 6ae");
		assertEquals(
				"Customer [customerID=22, customerName=bob, address=12 Grey Road, Manchester, postcode=M5 6ae]",
				customer.toString());
	}

	@Test
	public void testOutput() {
	
	}

}
