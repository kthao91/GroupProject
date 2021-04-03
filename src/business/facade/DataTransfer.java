package business.facade;

import bussiness.entites.*;

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
	private int quantity;
	private String orderID;

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

	public void setProductId(String input) {
		// TODO Auto-generated method stub

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

	/**
	 * Sets all product fields using a Product object
	 * 
	 * @param pro the product to set the fields to
	 */
	public void setProductFields(Product pro) {
		this.productName = pro.getName();
		this.productStock = pro.getOnHands();
		this.productID = pro.getId();
		this.productPrice = pro.getPrice();
		this.productReorderLevel = pro.getReorderLevel();
	}

	private void reset() {
		memberName = "none";
		memberAddress = "none";
		memberPhone = "none";
		memberID = "none";
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
}
