package bussiness.entites;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Transaction object to allow each purchase made by a member to be stored in
 * the Member class
 *
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private String receipt;
	private Calendar date;

	/**
	 * Creates the transaction with a given type and book title. The date is the
	 * current date.
	 * 
	 * @param type  The type of transaction
	 * @param title The title of the book
	 * 
	 */
	public Transaction(String receipt) {
		this.receipt = receipt;
		date = new GregorianCalendar();
	}

	/**
	 * Checks whether this transaction is on the given date
	 * 
	 * @param date The date for which transactions are being sought
	 * @return true if the dates match
	 */
	public boolean onDate(Calendar date) {
		return ((date.get(Calendar.YEAR) == this.date.get(Calendar.YEAR))
				&& (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH))
				&& (date.get(Calendar.DATE) == this.date.get(Calendar.DATE)));
	}

	/**
	 * Returns the date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getDate() {
		return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
	}

	/**
	 * String form of the transaction
	 * 
	 */
	@Override
	public String toString() {
		String output = getDate();
		output = output + "\n(name, quantity, price per item, total price)";
		output = output + "\n" + receipt;
		return output;
	}

}
