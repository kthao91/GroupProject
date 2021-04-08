package entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This is use to keep track of all the transaction that happen.
 * 
 * @author Koua
 *
 */
public class Transaction {
	private Calendar calendar = Calendar.getInstance();
	private List<String> fullTransaction = new LinkedList<String>();
	private Date transactionDate;
	private String date;

	public Transaction() {
		transactionDate = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		date = format.format(transactionDate);
	}

	/**
	 * This take the product and store it in a list because there can be more then
	 * one item per transaction
	 * 
	 * @param productName product Name
	 * @param price       Price
	 * @param amount      Amount
	 */
	public void addItem(String productName, double price, int amount) {

		String transactionString = "Product Name: " + productName + ", Amount: " + amount + ", Price: " + price
				+ ", Date: " + date;
		fullTransaction.add(transactionString);
	}

	/**
	 * Overload method when reloading from backup it easier to reload everything to
	 * string.
	 * 
	 * @param productName Product Name
	 * @param price       Price
	 * @param amount      Amount
	 * @param date        Date
	 */
	public void addItem(String productName, String price, String amount, String date) {

		String transactionString = "Product Name: " + productName + ", Amount: " + amount + ", Price: " + price
				+ ", Date: " + date;
		fullTransaction.add(transactionString);
	}

	public List<String> getTransaction() {
		return fullTransaction;
	}

	/**
	 * This coded to return the transaction date in string and not date vaible
	 * 
	 * @return String Transaction Date
	 */
	public String getDate() {
		String date = fullTransaction.get(0).split(", ")[3];
		return date.split(": ")[1];
	}

	public void setDate(String date) {
		this.date = date;
	}
}
