package business.facade;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bussiness.entites.*;
import business.entities.iterator.*;

public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Store store;
	private MemberList members = new MemberList();
	private OrderList orders = new OrderList();
	private ProductList products = new ProductList();

	private Store() {
	}

	public static Store instance() {
		if (store == null) {
			return store = new Store();
		} else {
			return store;
		}
	}

	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Member> members = new LinkedList<Member>();

		public Member search(String memberId) {
			for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
				Member member = iterator.next();
				if (member.getId().equals(memberId)) {
					return member;
				}
			}
			return null;
		}

		public boolean insertMember(Member member) {
			members.add(member);
			System.out.println(member.getId());
			return true;
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

		@Override
		public Iterator<Member> iterator() {
			return members.iterator();
		}
	}

	private class OrderList implements Iterable<Order>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Order> orders = new LinkedList<Order>();

		/**
		 * Searches for an order with a certain orderNumber
		 * 
		 * @param orderNumber the orderNumber of the order to be found
		 * @return order if the correct order is found
		 */
		public Order search(String orderNumber) {
			for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
				Order order = iterator.next();
				if (order.getOrderNumber().equals(orderNumber)) {
					return order;
				}
			}
			return null;
		}

		public boolean insertOrder(Order order) {
			return orders.add(order);
		}

		/**
		 * Searches for any outstanding (non-fulfilled) orders in the orders list
		 * 
		 * @return found a list with any non-fulfilled orders
		 */
		public ArrayList<Order> searchForOutstanding() {
			ArrayList<Order> found = new ArrayList<Order>();
			for (int i = 0; i < orders.size(); i++) {
				if (!orders.get(i).isFufilled()) {
					found.add(orders.get(i));
				}
			}
			return found;
		}

		@Override
		public Iterator<Order> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private class ProductList implements Iterable<Product>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Product> products = new LinkedList<Product>();

		/**
		 * Add a product return true if its able too.
		 * 
		 * @param product Product
		 * @return Boolean
		 */
		public boolean addProduct(Product product) {
			for (Product currentProduct : products) {
				if (currentProduct.getName().equals(product.getName())) {
					return false;
				}
			}
			products.add(product);
			return true;
		}

		/**
		 * Checks whether a product with a given product name exists.
		 * 
		 * @param productName the name of the product
		 * @return true if the product exists
		 * 
		 */
		public Product search(String productName) {
			for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
				Product pro = (Product) iterator.next();
				if (pro.getName().equals(productName)) {
					return pro;
				}
			}
			return null;
		}

		/**
		 * Checks whether a product with a certain ID exists
		 * 
		 * @param productID the id of the product
		 * @param price     the price of the product
		 * @return pro the product if found
		 */
		public Product search(String productID, double price) {
			for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
				Product pro = (Product) iterator.next();
				if (pro.getId().equals(productID)) {
					return pro;
				}
			}
			return null;
		}

		/**
		 * Inserts a product into the store
		 * 
		 * @param product the product to be inserted
		 * @return true if the product could be inserted, false if not
		 */
		public boolean insertProduct(Product product) {
			return products.add(product);
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
			Product product = search(productID);
			if (product != null) {
				product.setPrice(newPrice);
				return true;
			}
			return false;
		}

		/**
		 * Returns an iterator to all products
		 * 
		 * @return iterator to the collection
		 */
		public Iterator<Product> iterator() {
			return products.iterator();
		}

		/**
		 * Method to search for any products with the requested name
		 * 
		 * @param request the requested product
		 * @return found an ArrayList with all matching products
		 */
		public ArrayList<Product> searchForPInfo(Request request) {
			ArrayList<Product> found = new ArrayList<Product>();
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getName().toLowerCase().startsWith(request.getProductName().toLowerCase())) {
					found.add(products.get(i));
				}
			}
			return found;
		}

		/**
		 * String form of the collection
		 * 
		 */
		public String toString() {
			return products.toString();
		}
	}

	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhone());
		if (members.insertMember(member)) {
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
		Member member = members.search(request.getMemberID());
		if (members.removeMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.MEMBER_ID_INVAILD);
		}
		return result;

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
		Product product = new Product(name, stock, price, reorderLevel);

		if (products.addProduct(product)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Result changePrice(Request request) {
		Result result = new Result();
		Product product = products.search(request.getProductID());
		if (product == null) {
			result.setResultCode(Result.PRODUCT_ID_INVAILD);
			return result;
		}
		if (products.changePrice(request.getProductID(), request.getProductPrice())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Iterator<Member> getMembers(Request request) {
		LinkedList<Member> returnMembers = new LinkedList<Member>();
		for (Member currentMember : members) {
			System.out.println("currentMember is: " + currentMember);
			if (currentMember.getName().contains(request.getMemberName())) {
				returnMembers.add(currentMember);
			}
		}
		return returnMembers.iterator();
	}

	/**
	 * Checkout method to allow a cashier to checkout a member (also checks to see
	 * if the reorder level has been reached)
	 * 
	 * @param request the requested product and member ID to be checked-out
	 * @return result the product object being checked out
	 */
	public Result checkout(Request request) {
		Result result = new Result();
		Product product = products.search(request.getProductID(), request.getProductPrice());
		if (product != null) {
			result.setProductFields(product);
			product.setOnHands(product.getOnHands() - request.getQuantity());
		} else {
			System.out.println("ERROR: Unable to add this item");
		}
		if (product.getOnHands() <= product.getReorderLevel()) {
			Order reorder = new Order(product.getReorderLevel(), product.getName());
			orders.insertOrder(reorder);
			System.out.println(product.getName() + " reordered: " + product.getReorderLevel() + " units ordered");
		}
		return result;
	}

	/**
	 * Adds a transaction to a specific member
	 * 
	 * @param output  the "receipt" of the member's purchase
	 * @param request the member who the transaction should be added to
	 */
	public void addTransaction(String output, Request request) {
		Member member = members.search(request.getMemberID());
		Transaction transaction = new Transaction(output);
		member.addTransaction(transaction);
	}

	/**
	 * Returns an iterator to the transactions for a specific member on a certain
	 * date
	 * 
	 * @param memberId member id
	 * @return iterator to the collection
	 */
	public Iterator<Transaction> getTransactions(Request request) {
		Member member = members.search(request.getMemberID());
		if (member == null) {
			return new LinkedList<Transaction>().iterator();
		} else {
			return member.getTransactions(request);
		}
	}

	public Iterator<Product> retrieveProductInfo(Request request) {
		ArrayList<Product> found = new ArrayList<Product>();
		found = products.searchForPInfo(request);
		return found.iterator();
	}

	public Result processShipment(Request request) {
		Result result = new Result();
		Order holder = orders.search(request.getOrderID());
		if (holder != null && !holder.isFufilled()) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			holder.setFufilled(true);
			products.search(holder.getProductName()).addOnHands(holder.getQuantity());
		}
		return result;
	}

	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("StoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(store);
			Member.save(output);
			Order.save(output);
			file.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	public Iterator<Result> getProducts() {
		return new SafeIterator<Product>(products.iterator(), SafeIterator.PRODUCT);
	}

	/**
	 * Retrieves Order info from all orders that have not been fulfilled
	 * 
	 * @return found an iterator with all un-fulfilled orders
	 */
	public Iterator<Order> retrieveOutstandingOrders() {
		ArrayList<Order> found = new ArrayList<Order>();
		found = orders.searchForOutstanding();
		return found.iterator();
	}

}
