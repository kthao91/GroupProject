package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

import business.facade.*;
import bussiness.entites.*;

public class UserInterface {
	private static UserInterface userInterface;
	private static Store store;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final int EXIT = 0;
	private static final int ENROLL_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int ADD_PRODUCT = 3;
	private static final int CHECK_ITEM = 4;
	private static final int PROCESS_SHIPMENT = 5;
	private static final int CHANGE_PRICE = 6;
	private static final int GET_PRODUCT_INFO = 7;
	private static final int GET_MEMBER_INFO = 8;
	private static final int PRINT_TRANSACTION = 9;
	private static final int LIST_OUTSTANDINGS = 10;
	private static final int LIST_ALL_MEMBERS = 11;
	private static final int LIST_ALL_PRODUCTS = 12;
	private static final int SAVE = 13;
	private static final int HELP = 14;

	private UserInterface() {
		if (getInput("Look for saved data and  use it? yes or no: ") == "yes") {
			// retrieve();
		} else {
			store = Store.instance();
		}
	}

	public void help() {
		System.out.println(EXIT + " Exit");
		System.out.println(ENROLL_MEMBER + " Enroll a Member");
		System.out.println(REMOVE_MEMBER + " Remove a Member");
		System.out.println(ADD_PRODUCT + " Add a product");
		System.out.println(CHECK_ITEM + " Check Item");
		System.out.println(PROCESS_SHIPMENT + " Process shipment");
		System.out.println(CHANGE_PRICE + " Change Price");
		System.out.println(GET_PRODUCT_INFO + " Get Product Info");
		System.out.println(GET_MEMBER_INFO + " Get Member Info");
		System.out.println(PRINT_TRANSACTION + " Print Transaction");
		System.out.println(LIST_OUTSTANDINGS + " List Outstanding");
		System.out.println(LIST_ALL_MEMBERS + " List all Member");
		System.out.println(LIST_ALL_PRODUCTS + " List all Products");
		System.out.println(SAVE + " Save");
		System.out.println(HELP + " Help");

	}

	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	private String getInput(String prompt) {
		while (true) {
			String line = null;
			try {
				System.out.println(prompt);
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return line;
		}
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt the string for prompting
	 * @return the integer corresponding to the string
	 * 
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	public double getDouble(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				double number = Double.parseDouble(item);
				return number;
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number");
			}
		} while (true);
	}

	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getInput(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	public void enrollMember() {
		Request.instance().setMemberName(getInput("Member Name: "));
		Request.instance().setMemberAddress(getInput("Member Address: "));
		Request.instance().setMemberPhone(getInput("Phone: "));
		Store.instance().addMember(Request.instance());
	}

	public void getMemberInfo() {
		Request.instance().setMemberName(getInput("Member name to search: "));
		Iterator<Member> m = Store.instance().getMembers(Request.instance());
		while (m.hasNext()) {
			Member member = m.next();
			System.out.println(member.getName());
		}
	}

	public void listAllMembers() {
		Request.instance().setMemberName("");
		Iterator<Member> m = Store.instance().getMembers(Request.instance());
		while (m.hasNext()) {
			Member member = m.next();
			System.out.println(member.getName());
		}
	}

	/**
	 * Ask user for memberID then pass it to the store class to remove the member
	 */
	public void removeMember() {
		Request.instance().setMemberID(getInput("Member ID: "));
		int code = Store.instance().removeMember(Request.instance()).getResultCode();
		if (code == 7) {
			System.out.println("Sucuessfully remove Member");
		} else if (code == 3) {
			System.out.println("Invaild Member ID");
		}
	}

	/**
	 * Method to be called for adding a product to the Store. Prompts the user for
	 * the appropriate values and uses the Store method for adding a product to the
	 * Store.
	 */
	public void addProduct() {
		Request.instance().setProductName(getInput("Enter product name"));
		Request.instance().setProductID(getInput("Enter product ID"));
		Request.instance().setProductStock(getNumber("Enter on hands"));
		Request.instance().setProductPrice(getDouble("Enter current price"));
		Request.instance().setProductReorderLevel(getNumber("Enter reorder level"));
		Result result = store.addProduct(Request.instance());
		if (result.getResultCode() == Result.OPERATION_COMPLETED) {
			System.out.println("Product Added!");
			System.out.println("New product: " + Request.instance().getProductReorderLevel() * 2 + " units ordered");
		}
	}

	/**
	 * Method to be called to retrieve information on any products that start with a
	 * certain String. Prompts the user for the appropriate values and uses the
	 * store method for retrieving that information
	 */
	private void retrieveProductInfo() {
		Request.instance().setProductName(getInput("Enter product name "));
		Iterator<Product> result = store.retrieveProductInfo(Request.instance());
		if (result.hasNext()) {
			System.out.println("Name \t ID \t Price \t On Hands \t Reorder Level");
			while (result.hasNext()) {
				Product pro = result.next();
				System.out.println(pro.getId() + "\t" + pro.getId() + "\t " + pro.getPrice() + "\t " + pro.getOnHands()
						+ "\t\t  " + pro.getReorderLevel());
			}
		} else {
			System.out.println("No item found ");
		}
	}

	/**
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for displaying
	 * transactions.
	 * 
	 */
	public void getTransactions() {
		Request.instance().setMemberID(getInput("Enter member id"));
		Request.instance().setFirstDate(getDate("Please enter the first date for which you want records as mm/dd/yy"));
		Request.instance().setSecondDate(getDate("Please enter the last date for which you want records as mm/dd/yy"));
		Iterator<Transaction> result = store.getTransactions(Request.instance());
		while (result.hasNext()) {
			Transaction transaction = (Transaction) result.next();
			System.out.println(transaction);
		}
		System.out.println("\n End of transactions \n");
	}

	public void changePrice() {
		Request.instance().setProductId(getInput("Enter id: "));
		Request.instance().setProductPrice(Double.parseDouble(getInput("Enter new price: ")));
		Result result = store.instance().changePrice(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Product price could not be changed.");
		} else {
			System.out.println("Product " + result.getProductName() + " price has been changed to "
					+ result.getProductPrice() + ".");
		}
	}

	/**
	 * Method to be called to find any outstanding (non-fulfilled) orders
	 */
	private void outstandingOrders() {
		Iterator<Order> result = store.retrieveOutstandingOrders();
		if (result.hasNext()) {
			System.out.println("ID \t Product \t date \t quantity");
			while (result.hasNext()) {
				Order order = result.next();
				System.out.println(order.getOrderNumber() + "\t" + order.getProductName() + "\t" + order.getDate()
						+ "\t" + order.getQuantity());
			}
		}
	}

	/**
	 * Method to be called for processing a new shipment of products. Prompts the
	 * user for the appropriate values and uses the appropriate Store method for
	 * processing a shipment
	 */
	private void processShipment() {
		Request.instance().setOrderID(getToken("Enter Order Number"));
		Result result = store.processShipment(Request.instance());
		if (result.getResultCode() == Result.OPERATION_COMPLETED) {
			System.out.println("Order Processed successfully!");
		} else {
			System.out.println("ERROR: Order not processed");
		}
	}

	/**
	 * Method to be called to allow a member to checkout their items. The cashier
	 * checkouts one item at a time and is prompted to input the appropriate values.
	 * Then the appropriate store methods are called to checkout the member and add
	 * a transaction object to the member
	 */
	public void checkout() {
		String output = "";
		Request.instance().setMemberID(getToken("Enter member ID"));
		Iterator member = store.getMembers(Request.instance());
		if (member.hasNext()) {
			System.out.println("ERROR: no such member");
		}
		do {
			Request.instance().setProductID(getToken("Enter the ID of the item"));
			Request.instance().setQuantity(getNumber("Enter the quantity of the item"));
			Result result = store.checkout(Request.instance());
			output = output + result.getProductID() + " " + Request.instance().getProductStock() + " "
					+ result.getProductPrice() + " " + result.getProductPrice() * Request.instance().getProductStock()
					+ "\n";
			System.out.println(output);
		} while (yesOrNo("Checkout more items?"));
		store.addTransaction(output, Request.instance());
	}

	/**
	 * Displays all products
	 */
	public void getProducts() {
		Iterator<Result> iterator = store.getProducts();
		System.out.println("List of products (name, ID, On Hands, current price, reorder level)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getProductName() + " " + result.getOrderID() + " " + result.getProductStock()
					+ " " + result.getProductPrice() + " " + result.getProductReorderLevel());
		}
		System.out.println("End of listing");
	}

	private void save() {
		if (store.save()) {
			System.out.println(" The store has been successfully saved in the file StoreData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	public void menu() {
		int command;
		help();
		while ((command = Integer.parseInt(getInput("Type in command: "))) != EXIT) {
			switch (command) {
			case EXIT:
				System.out.println(EXIT);
				break;
			case ENROLL_MEMBER:
				enrollMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case ADD_PRODUCT:
				addProduct();
				break;
			case CHECK_ITEM:
				System.out.println(CHECK_ITEM);
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case GET_PRODUCT_INFO:
				retrieveProductInfo();
				break;
			case GET_MEMBER_INFO:
				getMemberInfo();
				break;
			case PRINT_TRANSACTION:
				getTransactions();
				break;
			case LIST_OUTSTANDINGS:
				System.out.println(LIST_OUTSTANDINGS);
				break;
			case LIST_ALL_MEMBERS:
				listAllMembers();
				break;
			case LIST_ALL_PRODUCTS:
				getProducts();
				break;
			case SAVE:
				save();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	public static void main(String[] args) {
		UserInterface.instance().menu();
	}
}
