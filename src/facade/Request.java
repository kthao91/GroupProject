package facade;

/**
 * This Class is use to transfer data between the userinterface and the store
 * class. Use a singleton because we do not want multiple version of this class
 * as it can make bad reads
 * 
 * @author Koua
 *
 */
public class Request extends DataTransfer {
	private static Request request;

	private Request() {

	}

	/**
	 * Singleton
	 * 
	 * @return Request only instance
	 */
	public static Request instance() {
		if (request == null) {
			request = new Request();
		}
		return request;
	}

}
