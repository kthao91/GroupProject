import java.util.LinkedList;
import java.util.List;

public class Member {
	private static int idCounter;
	private final String MEMBER_STRING = "M";
	private String name;
	private String address;
	private String phone;
	private String id;
	// private List<Transaction> transactions = new LinkedList<Transaction>();

	public Member(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		id = MEMBER_STRING + ++idCounter;
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

}
