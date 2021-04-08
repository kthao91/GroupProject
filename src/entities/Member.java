package entities;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * The class will be use to represent each member in the store.
 * 
 * @author Koua
 *
 */
public class Member {

	private static int idCounter;
	private final String MEMBER_STRING = "M";
	private String name;
	private String address;
	private String phone;
	private String id;
	private String dataJoin;
	private boolean feePaid;
	private List<Transaction> transactions = new LinkedList<Transaction>();

	/**
	 * When a new member is call it will have a name addres and phone
	 * 
	 * @param name    Member name
	 * @param address Member address
	 * @param phone   Member phone
	 */
	public Member(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		id = MEMBER_STRING + ++idCounter;
		feePaid = true;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		dataJoin = "" + dateFormatter.format(LocalDateTime.now());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateJoin() {
		return dataJoin;
	}

	public boolean getFeePaid() {
		return feePaid;
	}

	/**
	 * add a transaction to the member.
	 * 
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	/**
	 * This will return an iterator full of transaction the user has completed
	 * 
	 * @return Iterator<Transaction>
	 */
	public Iterator<Transaction> getTransaction() {
		return transactions.iterator();
	}

	public void setDate(String date) {
		dataJoin = date;
	}

	/**
	 * Override to string method for easy export.
	 */
	@Override
	public String toString() {
		String member = id + "," + name + "," + address + "," + phone + "," + dataJoin + "," + feePaid;
		return member;

	}

}
