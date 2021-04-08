package facade;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import entities.*;

/**
 * Class is to process that request of the user
 * 
 * @author Koua
 *
 */
public class Store implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Store store;
	private MemberList members = new MemberList();
	private Inventory inventory = new Inventory();

	/**
	 * This class to store all the members so that we can modify and pull from it
	 * when needed.
	 * 
	 * @author Koua
	 *
	 */
	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Member> members = new LinkedList<Member>();

		/**
		 * Add member to members
		 * 
		 * @param member Member
		 * @return boolean when added is completed
		 */
		public boolean addMember(Member member) {
			members.add(member);
			return true;
		}

		/**
		 * This will search for the member and return it if there is one.
		 * 
		 * @param MemberID Member ID
		 * @return Member
		 */
		public Member searchMember(String MemberID) {
			for (Member currentMember : members) {
				if (currentMember.getId().equals(MemberID)) {
					return currentMember;
				}
			}
			return null;
		}

		/**
		 * This will remove a member if it is found if not then it return false;
		 * 
		 * @param member Member
		 * @return Boolean
		 */
		public boolean removeMember(Member member) {
			if (member != null) {
				members.remove(member);
				return true;
			}
			return false;
		}

		/**
		 * Write member and transaction to disk.
		 */
		public void save() {
			try {
				FileWriter output = new FileWriter("Member.txt", false);
				String writeString = null;
				for (Member member : members) {
					writeString = member.toString() + "\n";
					Iterator<Transaction> membersTransaction = member.getTransaction();
					while (membersTransaction.hasNext()) {
						Transaction transaction = membersTransaction.next();
						List<String> stringTransaction = transaction.getTransaction();
						for (String currentTransaction : stringTransaction) {
							System.out.println(currentTransaction);
							writeString += currentTransaction + "\n";
						}
					}
					output.write(writeString);
				}
				output.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Reload each member and transaction from the Member.txt file
		 */
		public void reload() {
			try {
				File file = new File("Member.txt");
				Scanner scan = new Scanner(file);
				Member member = null;
				String StatsDate = null;
				Transaction transaction = new Transaction();
				while (scan.hasNext()) {
					String memberAdd = scan.nextLine();
					if (memberAdd.charAt(0) == 'M') {
						String[] currentMember = memberAdd.split(",");
						member = new Member(currentMember[1], currentMember[2], currentMember[3]);
						member.setDate(currentMember[4]);
						addMember(member);
					}
					if (memberAdd.charAt(0) == 'P') {
						String[] splitTransaction = memberAdd.split(",");
						String productName = splitTransaction[0].split(": ")[1];
						String price = splitTransaction[1].split(": ")[1];
						String amount = splitTransaction[2].split(": ")[1];
						String date = splitTransaction[3].split(": ")[1];
						if (!date.equals(StatsDate)) {
							transaction = new Transaction();
						}
						transaction.addItem(productName, price, amount, date);
						member.addTransaction(transaction);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		@Override
		public Iterator<Member> iterator() {
			return members.iterator();
		}
	}

	/**
	 * This class will hold all the products and orders that it will need to handle
	 * 
	 * @author Koua
	 *
	 */
	private class Inventory implements Iterable<Product>, Serializable {
		private static final long serialVersionUID = 1L;
		private int orderNumber = 1;
		private List<Product> inventory = new LinkedList<Product>();
		private List<String> order = new LinkedList<String>();

		/**
		 * Add a product return true if its able too.
		 * 
		 * @param product Product
		 * @return Boolean
		 */
		public boolean addProduct(Product product) {
			for (Product currentProduct : inventory) {
				if (currentProduct.getName().equals(product.getName())) {
					return false;
				}
			}
			inventory.add(product);
			return true;
		}

		/**
		 * Search inventory for product if product exist then return product
		 * 
		 * @param productID Product ID
		 * @return Product
		 */
		public Product searchProduct(String productID) {
			for (Product product : inventory) {
				if (product.getID().equals(productID)) {
					return product;
				}
			}
			return null;
		}

		/**
		 * change the price if it able to find the product with ID. return true if able
		 * and false if not
		 * 
		 * @param productID Product ID
		 * @param newPrice  Price
		 * @return Boolean
		 */
		public boolean changePrice(String productID, double newPrice) {
			Product product = searchProduct(productID);
			if (product != null) {
				product.setPrice(newPrice);
				return true;
			}
			return false;
		}

		/**
		 * Save product and order to disk
		 */
		public void saveProduct() {
			FileWriter output;
			try {
				output = new FileWriter("Products.txt", false);
				for (Product product : inventory) {
					System.out.println(product.getName());
					String outputString = product.getID() + "," + product.getName() + "," + product.getStock() + ","
							+ product.getPrice() + "," + product.getReorderLevel() + "\n";
					output.write(outputString);
				}
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * reload product and order from disk
		 */
		public void reloadProduct() {
			try {
				File file = new File("Products.txt");
				Scanner scan = new Scanner(file);
				while (scan.hasNext()) {
					String[] productText = scan.nextLine().split(",");
					Product product = new Product(productText[1], productText[0], productText[2], productText[3],
							productText[4]);
					addProduct(product);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		/**
		 * When the stock is lower the then reorder level this is create and store in
		 * order to order.
		 * 
		 * @param productID Product ID
		 */
		private void orderProduct(String productID) {
			Product product = searchProduct(productID);
			String productName = product.getName();
			int orderQuantity = product.getReorderLevel() * 2;
			String addOrder = (++orderNumber) + " " + productID + " " + productName + " " + orderQuantity;
			order.add(addOrder);

		}

		/**
		 * This process the order and add the ordered product to stock and remove from
		 * order
		 * 
		 * @param orderID OrderId
		 * @return Product
		 */
		public Product ProcessOrder(String orderID) {

			for (String currentOrder : order) {

				if (currentOrder.split(" ")[0].equals(orderID)) {
					Product product = searchProduct(currentOrder.split(" ")[1]);
					product.setStock(product.getStock() + (product.getReorderLevel() * 2));
					return product;
				}
				order.remove(currentOrder);
			}
			return null;
		}

		/**
		 * Save order to disk
		 */
		public void saveOrder() {
			try {
				FileWriter output = new FileWriter("Order.txt", false);
				for (String currentOrder : order) {
					output.write(currentOrder);
				}
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * reload order from disk
		 */
		public void reloadOrder() {
			try {
				File file = new File("Order.txt");
				Scanner scan = new Scanner(file);
				while (scan.hasNext()) {
					String[] scannedOrder = scan.nextLine().split(" ");
					String addOrder = (++orderNumber) + " " + scannedOrder[1] + " " + scannedOrder[2] + " "
							+ scannedOrder[3];
					order.add(addOrder);

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		public Iterator<String> getOrder() {
			return order.iterator();
		}

		@Override
		public Iterator<Product> iterator() {
			// TODO Auto-generated method stub
			return inventory.iterator();
		}

	}

	private Store() {
	}

	/**
	 * Singleton method
	 * 
	 * @return store
	 */
	public static Store instance() {
		if (store == null) {
			return store = new Store();
		} else {
			return store;
		}
	}

	/**
	 * Calls member method to add method and if member is not null return a success
	 * code if not return a fail code
	 * 
	 * @param request Request
	 * @return Result
	 */
	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhone());
		if (members.addMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Calls member method to remove method and if member is not null return a
	 * success code if not return a fail code
	 * 
	 * @param request Request
	 * @return
	 */
	public Result removeMember(Request request) {
		Result result = new Result();
		Member member = members.searchMember(request.getMemberID());
		if (members.removeMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.MEMBER_ID_INVAILD);
		}
		return result;

	}

	/**
	 * check to see if the request name is in a member name if it is add it the list
	 * and return the list
	 * 
	 * @param request Request
	 * @return Iterator<Member>
	 */
	public Iterator<Member> getMembers(Request request) {
		LinkedList<Member> returnMembers = new LinkedList<Member>();
		for (Member currentMember : members) {
			if (currentMember.getName().contains(request.getMemberName())) {
				returnMembers.add(currentMember);
			}
		}
		return returnMembers.iterator();
	}

	/**
	 * Calls inventory method to add product and if member is not null return a
	 * success code if not return a fail code
	 * 
	 * @param request Request
	 * @return Result
	 */
	public Result addProduct(Request request) {
		Result result = new Result();
		String name = request.getProductName();
		int stock = request.getProductStock();
		double price = request.getProductPrice();
		int reorderLevel = request.getProductReorderLevel();
		String productID = request.getProductID();
		Product product = new Product(name, productID, stock, price, reorderLevel);

		if (inventory.addProduct(product)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Search for product id if there a matching product change the price and set
	 * result to operation completed.
	 * 
	 * @param request Request
	 * @return Result
	 */
	public Result changePrice(Request request) {
		Result result = new Result();
		if (inventory.changePrice(request.getProductID(), request.getProductPrice())) {
			request.setProductName(inventory.searchProduct(request.getProductID()).getName());
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.PRODUCT_ID_INVAILD);
		return result;
	}

	/**
	 * If current product matches then add to list and return list.
	 * 
	 * @param request Request
	 * @return Iterator<Product>
	 */
	public Iterator<Product> getProduct(Request request) {
		LinkedList<Product> returnProduct = new LinkedList<Product>();
		for (Product currentProduct : inventory) {
			if (currentProduct.getName().contains(request.getMemberName())) {
				returnProduct.add(currentProduct);
			}
		}
		return returnProduct.iterator();
	}

	/**
	 * Return all product
	 * 
	 * @return allProduct
	 */
	public Iterator<Product> getAllProduct() {

		return inventory.iterator();
	}

	/**
	 * Once user typed in all the order then this method create a transaction and
	 * return the full transaction.
	 * 
	 * @param request Request
	 * @return transaction
	 */
	public Iterator<String> checkOut(Request request) {
		HashMap<String, Integer> info = request.getTransaction();
		Transaction transaction = new Transaction();
		for (Entry<String, Integer> currentInfo : info.entrySet()) {
			Product product = inventory.searchProduct(currentInfo.getKey());
			String productName = product.getName();
			int amount = currentInfo.getValue();
			double orginalPrice = product.getPrice();
			double totalCost = amount * orginalPrice;
			transaction.addItem(productName, totalCost, amount);
			product.setStock(product.getStock() - amount);
			if (product.getStock() < product.getReorderLevel()) {
				inventory.orderProduct(currentInfo.getKey());
			}
		}
		addTransaction(request.getMemberID(), transaction);
		return transaction.getTransaction().iterator();
	}

	/**
	 * This add the transaction to the member
	 * 
	 * @param MemberID    Member ID
	 * @param transaction Transaction
	 */
	private void addTransaction(String MemberID, Transaction transaction) {
		Member currentMember = members.searchMember(MemberID);
		if (currentMember != null) {
			currentMember.addTransaction(transaction);
		}
	}

	/**
	 * This class will go through the members transaction field and find the
	 * transaction between the two dates
	 * 
	 * @param request Request
	 * @return Iterator<Transaction>
	 */
	public Iterator<Transaction> printTransaction(Request request) {
		Member member = members.searchMember(request.getMemberID());
		Iterator<Transaction> transactions = member.getTransaction();
		LinkedList<Transaction> returnTransaction = new LinkedList<Transaction>();
		SimpleDateFormat datefomat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date after = datefomat.parse(request.getDate1());
			Date before = datefomat.parse(request.getDate2());

			while (transactions.hasNext()) {
				Transaction transaction = transactions.next();
				Date currentDate = datefomat.parse(transaction.getDate());
				if (currentDate.compareTo(after) >= 0 && currentDate.compareTo(before) <= 0) {
					returnTransaction.add(transaction);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnTransaction.iterator();
	}

	/**
	 * If the orderID was correct it will process the shipment and return a
	 * operation complete code if not it will state the the order id is invaild
	 * 
	 * @param request Request
	 * @return result
	 */
	public Result processShipment(Request request) {
		// product id, name, and the new stock.
		Result result = new Result();
		Product product = inventory.ProcessOrder(request.getOrderID());
		if (product == null) {
			result.setResultCode(Result.PRODUCT_ID_INVAILD);
			return result;
		}
		request.setProductID(product.getID());
		request.setProductName(product.getName());
		request.setProductStock(product.getStock());
		result.setResultCode(Result.OPERATION_COMPLETED);
		return result;
	}

	/**
	 * Save all information
	 */
	public void save() {
		members.save();
		inventory.saveOrder();
		inventory.saveProduct();
	}

	/**
	 * reload all information
	 */
	public void reload() {
		members.reload();
		inventory.reloadProduct();
		inventory.reloadOrder();
	}

	public Iterator<String> getAllOrder() {
		return inventory.getOrder();
	}

}
