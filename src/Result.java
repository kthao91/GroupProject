
public class Result extends DataTransfer {
	public static final int CANNOT_ADD_MEMBER = 1;
	public static final int BOOK_NOT_ISSUED = 2;
	public static final int BOOK_HAS_HOLD = 3;
	public static final int BOOK_ISSUED = 4;
	public static final int HOLD_PLACED = 5;
	public static final int NO_HOLD_FOUND = 6;
	public static final int OPERATION_COMPLETED = 7;
	public static final int OPERATION_FAILED = 8;
	public static final int NO_SUCH_MEMBER = 9;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
