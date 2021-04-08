package Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import business.facade.*;
import bussiness.entites.*;
import ui.*;

public class AutomatedTester {
	Random random = new Random();
	String[] productnames = { "egg", "milk", "butter", "cheese", "ham", "hot dog", "beef", "frozen pancake", "sausages",
			"hot sauce", "orange juice", "bananas", "starberry", "blackberry", "blueberry", "yogert", "apple juice",
			"soda", "syrup", "chicken breast" };
	ArrayList<Product> product = new ArrayList<Product>();

	String[] memberNames = { "Dan", "Tony", "Mike" };
	String[] addresses = { "4 Ryan Street Frederick MD 21701", "152 Kent Street Peoria, IL 61604",
			"9710 Oak Meadow Lane Venice, FL 34293" };

	public AutomatedTester() {
		testAddMember();
		testAddProject();
		testRemoveMember();
		testCheckOutItem();
		testProcessShipment();
		testChangePrice();

	}

	/**
	 * test add member
	 */
	public void testAddMember() {
		for (int i = 0; i < memberNames.length; i++) {
			Request.instance().setMemberName(memberNames[i]);
			Request.instance().setMemberAddress(addresses[i]);
			Request.instance().setMemberPhone(((2000000000 - 1000000000) * random.nextInt()) + " ");
			Result result = Store.instance().addMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
		}

	}

	/**
	 * test add project
	 */
	public void testAddProject() {
		int i = 1;
		for (String productName : productnames) {
			Request.instance().setProductName(productName);
			Request.instance().setProductID("S" + i);
			Request.instance().setProductPrice(Math.abs(1 + (100.0 - 1.0) * random.nextDouble()));
			Request.instance().setProductStock(Math.abs(random.nextInt(50) - 1));
			Request.instance().setProductReorderLevel(Math.abs(random.nextInt(50) - 1));
			Result result = Store.instance().addProduct(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
		}
	}

	/**
	 * test remove member
	 */
	public void testRemoveMember() {
		Request.instance().setMemberID("M1");
		int code = Store.instance().removeMember(Request.instance()).getResultCode();
		assert code == Result.OPERATION_COMPLETED;
	}

	/**
	 * test check out items
	 */
	public void testCheckOutItem() {
		Request.instance().setMemberID("M2");
		Iterator<Member> members = Store.instance().getMembers(Request.instance());
		for (int i = 1; i < 15; i++) {
			Request.instance().addTransaction("S" + i, 2);
		}
		Iterator<String> transaction = Store.instance().checkOut(Request.instance());
		int i = 0;
		while (transaction.hasNext()) {
			String transactionString = transaction.next();
			assert transactionString.split(": ")[1].equals(productnames[i]);
			i++;
		}
	}

	/**
	 * create an order
	 */
	private void createOrder() {
		Request.instance().setMemberID("M2");
		Iterator<Member> members = Store.instance().getMembers(Request.instance());
		Request.instance().setProductID("S1");
		Iterator<Product> product = Store.instance().getProduct(Request.instance());
		if (product.hasNext()) {
			Request.instance().addTransaction("S1", product.next().getStock());
			Store.instance().checkOut(Request.instance());
		}
	}

	/**
	 * test Process shipment
	 */

	public void testProcessShipment() {
		createOrder();
		Request.instance().setOrderID("2");
		assert Store.instance().processShipment(Request.instance()).getResultCode() == Result.OPERATION_COMPLETED;
	}

	/**
	 * test change prices
	 */
	public void testChangePrice() {
		Request.instance().setProductID("S1");
		Request.instance().setProductPrice(0.0);
		assert Store.instance().changePrice(Request.instance()).getResultCode() == Result.OPERATION_COMPLETED;
	}

}
