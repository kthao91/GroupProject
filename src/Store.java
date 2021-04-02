import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Store implements Serializable {
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
		public boolean insertProdcut(Product product) {
			return products.add(product);
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

	public Result changePrice(Request request) {
		Result result = new Result();
		Product product = products.search(request.getProductID());
		if (product == null) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
			return result;
		}
		if (products.changePrice(request.getProductId(), request.getPrice())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
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

}
