import java.util.Calendar;

/**
 * This class is used for requesting many of the results of the Store system's
 * business logic to user interface. It is a singleton
 * 
 * @author Brahma Dathan
 *
 */
public class Request extends DataTransfer {
	private static Request request;
	private Calendar firstDate;
	private Calendar secondDate;

	/**
	 * This is a singleton class. Hence the private constructor.
	 */
	private Request() {

	}

	/**
	 * Returns the only instance of the class.
	 * 
	 * @return the only instance
	 */
	public static Request instance() {
		if (request == null) {
			request = new Request();
		}
		return request;
	}

	/**
	 * Gets the first date from a users request to getTransactions
	 * 
	 * @return the first date
	 */
	public Calendar getFirstDate() {
		return firstDate;
	}

	/**
	 * Sets the first date from a users request to getTransactions
	 */
	public void setFirstDate(Calendar date) {
		this.firstDate = date;
	}

	/**
	 * Gets the second date from a users' request to getTransactions
	 * 
	 * @return
	 */
	public Calendar getSecondDate() {
		return secondDate;
	}

	/**
	 * Sets the second date from a users request to getTransactions
	 */
	public void setSecondDate(Calendar secondDate) {
		this.secondDate = secondDate;
	}

}
