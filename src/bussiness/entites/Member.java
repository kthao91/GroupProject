package bussiness.entites;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.iterator.*;
import business.facade.*;

public class Member {
	private static int idCounter;
	private final String MEMBER_STRING = "M";
	private String name;
	private String address;
	private String phone;
	private String id;
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private Date dateJoined;
	// private List<Transaction> transactions = new LinkedList<Transaction>();

	public Member(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		id = MEMBER_STRING + ++idCounter;
		dateJoined = new Date();
	}

	/**
	 * Gets an iterator to a collection of selected transactions
	 * 
	 * @param date the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */
	public Iterator<Transaction> getTransactionsOnDate(Calendar date) {
		return new FilteredIterator(transactions.iterator(), transaction -> transaction.onDate(date));
	}

	/**
	 * Returns the list of all transactions for this member.
	 * 
	 * @return the iterator to the list of Transaction objects
	 */
	public Iterator<Transaction> getTransactions(Request request) {
		ArrayList<Transaction> holder = new ArrayList<Transaction>();
		ArrayList<Calendar> datesInRange = new ArrayList<Calendar>();
		Calendar cal = request.getFirstDate();
		Calendar endCal = request.getSecondDate();
		while (cal.before(endCal)) {
			Calendar res = cal;
			datesInRange.add(res);
			cal.add(Calendar.DATE, 1);
		}
		for (int i = 0; i < transactions.size(); i++) {
			for (int j = 0; j < datesInRange.size(); j++) {
				if (transactions.get(i).onDate(datesInRange.get(j)) && !holder.contains(transactions.get(i))) {
					holder.add(transactions.get(i));
				}
			}
		}
		return holder.iterator();
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

	/**
	 * Saves the idCounter object
	 * 
	 * @param output file
	 * @throws IOException if idCounter cannot be saved
	 */
	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	/**
	 * Adds a transaction to the member's transaction list
	 * 
	 * @param tran the transaction to be added
	 */
	public void addTransaction(Transaction tran) {
		transactions.add(tran);
	}
}
