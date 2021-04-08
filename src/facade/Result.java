package facade;

/**
 * This class is use to print the result of operation. Sometime its not neededl
 * 
 * @author Koua
 *
 */
public class Result extends DataTransfer {
	public static final int CANNOT_ADD_MEMBER = 1;
	public static final int CANNOT_ADD_PRODUCT = 2;
	public static final int MEMBER_ID_INVAILD = 3;
	public static final int PRODUCT_ID_INVAILD = 4;
	public static final int OPERATION_COMPLETED = 7;
	public static final int OPERATION_FAILED = 8;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
