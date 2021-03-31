
public abstract class DataTransfer {
	private String memberName;
	private String memberAddress;
	private String memberPhone;
	private String memberID;

	public DataTransfer() {
		reset();
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public void setMemberFields(Member member) {
		memberID = member.getId();
		memberName = member.getName();
		memberPhone = member.getPhone();
		memberAddress = member.getAddress();
	}

	private void reset() {
		memberName = "none";
		memberAddress = "none";
		memberPhone = "none";
		memberID = "none";
	}
}
