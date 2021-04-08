package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import Test.Test;
import entities.*;
import facade.*;

/**
 * This is the main interface to run the program. The user will be prompted to
 * eneter a command in a form of an integer and will do the following
 * 
 * @author Koua
 *
 */
public class UserInterface {

	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final int EXIT = 0;
	private static final int ENROLL_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int ADD_PRODUCT = 3;
	private static final int CHECK_OUT_ITEM = 4;
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

	/**
	 * When the UserInterface is call it will ask the user to enter a yes to load
	 * the datebase
	 */
	private UserInterface() {
		if (getInput("Look for saved data and  use it? For yes type 'yes' or type anything for no: ").toLowerCase()
				.equals("yes")) {
			Store.instance().reload();
		} else if (getInput("Do you want to generate a test bed and invoke the functionality using asserts?: ")
				.toLowerCase().equals("yes")) {
			new Test();

		} else {
			Store.instance();
		}
	}

	/**
	 * This will show at the beginning and will display when prompted
	 */
	public void help() {
		System.out.println(EXIT + " Exit");
		System.out.println(ENROLL_MEMBER + " Enroll a Member");
		System.out.println(REMOVE_MEMBER + " Remove a Member");
		System.out.println(ADD_PRODUCT + " Add a product");
		System.out.println(CHECK_OUT_ITEM + " Check Out Item");
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

	/**
	 * Singleton Pattern
	 * 
	 * @return userInterface
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * This will ask the user to enter a string
	 * 
	 * @param prompt String Sentence that will be display to user
	 * @return String User input
	 */
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
	 * This will ask the user to enter a int and make sure its a int
	 * 
	 * @param prompt String Sentence that will be display to user
	 * @return int return user input
	 */
	private int getInputInt(String prompt) {
		int returnInt = 0;
		while (true) {
			String line = null;
			try {
				System.out.println(prompt);
				line = reader.readLine();
				returnInt = Integer.parseInt(line);
				return returnInt;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException ne) {
				System.out.println("Please type in a number: ");
			}
		}
	}

	/**
	 * This will ask the user to enter a double and make sure its a double
	 * 
	 * @param prompt String Sentence that will be display to user
	 * @return double return user input
	 */
	private double getInputDouble(String prompt) {
		double returnDouble = 0;
		while (true) {
			String line = null;
			try {
				System.out.println(prompt);
				line = reader.readLine();
				returnDouble = Double.parseDouble(line);
				return returnDouble;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException ne) {
				System.out.println("Please type in a number: ");
			}
		}
	}

	/**
	 * Ask user to enter Member name, address, and phone then pass it to the store
	 * class via the Request
	 */
	public void enrollMember() {
		Request.instance().setMemberName(getInput("Member Name: "));
		Request.instance().setMemberAddress(getInput("Member Address: "));
		Request.instance().setMemberPhone(getInput("Phone: "));
		Store.instance().addMember(Request.instance());
	}

	/**
	 * Ask user for memberID then pass it to the store class to remove the member
	 */
	public void removeMember() {
		Request.instance().setMemberID(getInput("Member ID: "));
		int code = Store.instance().removeMember(Request.instance()).getResultCode();
		if (code == Result.OPERATION_COMPLETED) {
			System.out.println("Sucuessfully remove Member");
		} else if (code == Result.OPERATION_FAILED) {
			System.out.println("Invaild Member ID");
		}
	}

	/**
	 * Ask user for product name, price, reorder level, and stock then pass it
	 * though to the store class to add
	 */
	public void addProduct() {
		Request.instance().setProductName(getInput("Product Name: "));
		Request.instance().setProductID(getInput("Produc ID: "));
		Request.instance().setProductPrice(getInputDouble("Product Price: "));
		Request.instance().setProductReorderLevel(getInputInt("Reorder Level: "));
		Request.instance().setProductStock(getInputInt("Product Stock: "));
		int code = Store.instance().addProduct(Request.instance()).getResultCode();
		if (code == 8) {
			System.out.println("Fail to add Product");
		} else if (code == 7) {
			System.out.println("Sucessfully to add Product");
		}
	}

	/**
	 * when checking out he user will have to type in the product id and amount and
	 * then save to transaction and will ask if the user wants to another to the
	 * transaction.
	 */
	public void checkOut() {
		String done = "no";
		String input = "YES";
		System.out.println("Type 'No' to end check out");
		Request.instance().setMemberID(getInput("Member ID to search: "));
		Iterator<Member> members = Store.instance().getMembers(Request.instance());
		if (members.hasNext()) {
			while (!input.equals(done)) {
				String productID = getInput("Product ID: ");
				int amount = getInputInt("Amount: ");
				Request.instance().addTransaction(productID, amount);
				input = getInput("For no type 'no' otherwise type anything for yes\nDo you want to add more: ")
						.toLowerCase();
			}
			Iterator<String> transaction = Store.instance().checkOut(Request.instance());
			while (transaction.hasNext()) {
				System.out.println(transaction.next());
			}
		} else {
			System.out.println("Member ID is Invaild");
		}

	}

	/**
	 * This will process a pending order then remove the order. After it will
	 * display the productID, product Name, stock
	 */
	public void processShippment() {
		String done = "Yes";
		while (!done.equals("no")) {
			Request.instance().setOrderID(getInput("Please type in your order ID: "));
			Result result = Store.instance().processShipment(Request.instance());
			String productID = Request.instance().getProductID();
			if (result.getResultCode() != Result.OPERATION_COMPLETED) {
				System.out.println("Invaild Order");
			} else {
				String productName = Request.instance().getProductName();
				int stock = Request.instance().getProductStock();
				System.out.println(productID + " " + productName + " " + stock);
			}
			done = getInput("For no type 'no' otherwise type anything for yes \nDo you want to process more?: ")
					.toLowerCase();

		}
	}

	/**
	 * Grab the member name and pass it the store to return the list of members with
	 * containing name then printer name, addres, feePaid, getID
	 */
	public void getMemberInfo() {
		// displays the member’s address, feepaid, and id
		Request.instance().setMemberName(getInput("Member name to search: "));
		Iterator<Member> members = Store.instance().getMembers(Request.instance());
		while (members.hasNext()) {
			Member member = members.next();
			System.out.println("Member Name: " + member.getName());
			System.out.println("Member Address: " + member.getAddress());
			System.out.println("Fee Paid: " + member.getFeePaid());
			System.out.println("Member ID: " + member.getId());

		}
	}

	/**
	 * return all the members that in the system.
	 */
	public void listAllMembers() {
		// Display the name, date joined, address, and phone number
		Request.instance().setMemberName("");
		Iterator<Member> m = Store.instance().getMembers(Request.instance());
		while (m.hasNext()) {
			Member member = m.next();
			System.out.println();
			System.out.println("Member Name: " + member.getName());
			System.out.println("Member Date Join: " + member.getDateJoin());
			System.out.println("Member Address: " + member.getAddress());
			System.out.println("Member Phone: " + member.getPhone());

		}
	}

	/**
	 * Ask the user to enter in the product ID and the price change. If there is a
	 * productID matching it will change the price. If not then it will print
	 * invaild id.
	 */
	public void changePrice() {
		// product name and the new price
		Request.instance().setProductID(getInput("Type in product ID to change price: "));
		Request.instance().setProductPrice(getInputDouble("Type in price change: "));
		Result result = Store.instance().changePrice(Request.instance());
		if (result.getResultCode() == Result.OPERATION_COMPLETED) {
			System.out.println();
			System.out.println("Product Name: " + Request.instance().getProductName());
			System.out.println("New Price: " + Request.instance().getProductPrice());
		} else if (result.getResultCode() == Result.PRODUCT_ID_INVAILD) {
			System.out.println("Product ID is not invaild");
		}
	}

	/**
	 * Asked for memeberID and dates. Print out what returns.
	 */
	public void printTransaction() {
		Request.instance().setMemberID(getInput("Please enter Member ID: "));
		Request.instance().setDate1(getInput("Please enter in the earlier date. mm/dd/yyyy: "));
		Request.instance().setDate2(getInput("Please enter in the later date. mm/dd/yyyy: "));
		Iterator<Transaction> tranactions = Store.instance().printTransaction(Request.instance());
		while (tranactions.hasNext()) {
			List<String> productTransaction = tranactions.next().getTransaction();
			for (String tranactionString : productTransaction) {
				System.out.println(tranactionString);
			}
		}
	}

	/**
	 * Return all outstanding orders
	 */
	public void listOutstandings() {
		Iterator<String> order = Store.instance().getAllOrder();
		System.out.println(order.hasNext());
		while (order.hasNext()) {
			System.out.println(order.next());
		}
	}

	/**
	 * ask for product name and will print everything with a similar name
	 */
	public void getProductInfo() {
		// List the product name, product id, price, stock in hand, and reorder level.
		Request.instance().setProductName(getInput("Product Name: "));
		Iterator<Product> products = Store.instance().getProduct(Request.instance());
		while (products.hasNext()) {
			// product name, product id, price, stock in hand, and reorder level.
			Product product = products.next();
			System.out.println();
			System.out.println("Product Name: " + product.getName());
			System.out.println("Product ID: " + product.getID());
			System.out.println("Product Price: " + product.getPrice());
			System.out.println("Product Stock: " + product.getStock());
			System.out.println("Product Reorder Level: " + product.getReorderLevel());

		}

	}

	/**
	 * Print all the product info.
	 */
	public void getAllProductInfo() {
		// product name, id, stock in hand, current price, and a reorder level
		Request.instance().setProductName("");
		Iterator<Product> products = Store.instance().getAllProduct();
		while (products.hasNext()) {
			// product name, product id, price, stock in hand, and reorder level.
			Product product = products.next();
			System.out.println();
			System.out.println("Product Name: " + product.getName());
			System.out.println("Product ID: " + product.getID());
			System.out.println("Product Stock: " + product.getStock());
			System.out.println("Product Price: " + product.getPrice());
			System.out.println("Product Reorder Level: " + product.getReorderLevel());

		}

	}

	/**
	 * Store all the infomation on disk
	 */
	public void save() {
		Store.instance().save();
	}

	/**
	 * ask for command and process the method corresponding to it.
	 */
	public void menu() {
		int command;
		help();
		while ((command = getInputInt("Type in command: ")) != EXIT) {
			switch (command) {
			case EXIT:
				System.exit(0);
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
			case CHECK_OUT_ITEM:
				checkOut();
				break;
			case PROCESS_SHIPMENT:
				processShippment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case GET_PRODUCT_INFO:
				getProductInfo();
				break;
			case GET_MEMBER_INFO:
				getMemberInfo();
				break;
			case PRINT_TRANSACTION:
				printTransaction();
				break;
			case LIST_OUTSTANDINGS:
				listOutstandings();
				break;
			case LIST_ALL_MEMBERS:
				listAllMembers();
				break;
			case LIST_ALL_PRODUCTS:
				getAllProductInfo();
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
