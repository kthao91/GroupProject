import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

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
				System.out.println(REMOVE_MEMBER);
				break;
			case ADD_PRODUCT:
				System.out.println(ADD_PRODUCT);
				break;
			case CHECK_ITEM:
				System.out.println(CHECK_ITEM);
				break;
			case PROCESS_SHIPMENT:
				System.out.println(PROCESS_SHIPMENT);
				break;
			case CHANGE_PRICE:
				System.out.println(CHANGE_PRICE);
				break;
			case GET_PRODUCT_INFO:
				System.out.println(GET_PRODUCT_INFO);
				break;
			case GET_MEMBER_INFO:
				System.out.println(GET_MEMBER_INFO);
				break;
			case PRINT_TRANSACTION:
				System.out.println(PRINT_TRANSACTION);
				break;
			case LIST_OUTSTANDINGS:
				System.out.println(LIST_OUTSTANDINGS);
				break;
			case LIST_ALL_MEMBERS:
				listAllMembers();
				break;
			case LIST_ALL_PRODUCTS:
				System.out.println(LIST_ALL_PRODUCTS);
				break;
			case SAVE:
				System.out.println(SAVE);
				break;
			case HELP:
				System.out.println(HELP);
				break;
			}
		}
	}

	public static void main(String[] args) {
		UserInterface.instance().menu();
	}
}
