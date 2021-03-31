
public class UserInterface {

	public static void main(String[] args) {
		Request.instance().setMemberName("test");
		Request.instance().setMemberAddress("777 add");
		Request.instance().setMemberPhone("888-888-8888");
		Store.instance().addMember(Request.instance());
	}
}
