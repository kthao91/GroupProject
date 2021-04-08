package facade;

import java.util.HashMap;

import entities.*;

/**
 * DataTranfer are use in-between the Store class and the userInterface to
 * exchange data
 * 
 * @author Koua
 *
 */
public abstract class DataTransfer {
	private String productID;
	private String productName;
	private int productStock;
	private double productPrice;
	private int productReorderLevel;
	private String memberName;
	private String memberAddress;
	private String memberPhone;
	private String memberID;
	private String orderID;
	private HashMap<String, Integer> transaction = new HashMap<String, Integer>();
	private String date1;
	private String date2;

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

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductReorderLevel() {
		return productReorderLevel;
	}

	public void setProductReorderLevel(int productReorderLevel) {
		this.productReorderLevel = productReorderLevel;
	}

	public void setProductFeilds(Product product) {
		productID = product.getID();
		productName = product.getName();
		productStock = product.getStock();
		productPrice = product.getPrice();
		productReorderLevel = product.getReorderLevel();
	}

	public void addTransaction(String productID, int amount) {
		if (transaction.containsKey(productID)) {
			Integer currentAmount = (Integer) transaction.get(productID);
			currentAmount += amount;
			transaction.remove(productID, currentAmount);
		} else {
			transaction.put(productID, amount);
		}
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public HashMap<String, Integer> getTransaction() {
		return transaction;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	private void reset() {
		memberName = "none";
		memberAddress = "none";
		memberPhone = "none";
		memberID = "none";
		transaction = new HashMap<String, Integer>();
	}
}
