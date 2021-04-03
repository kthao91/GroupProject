package bussiness.entites;

import java.io.Serializable;

/**
 * Product class allows the store to hold information on each product they carry
 *
 */
public class Product implements Serializable {
	private static int count = 1;
	private String name;
	private String id;
	private int onHands;
	private double price;
	private int reorderLevel;

	/**
	 * Creates a product with the inputed name, id, onHands, price, and reorder
	 * level
	 * 
	 * @param name
	 * @param id
	 * @param onHands
	 * @param price
	 * @param reorderLevel
	 */
	public Product(String name, int onHands, double price, int reorderLevel) {
		this.name = name;
		this.id = "" + (++count);
		this.onHands = onHands;
		this.price = price;
		this.reorderLevel = reorderLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOnHands() {
		return onHands;
	}

	public void setOnHands(int onHands) {
		this.onHands = onHands;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	/**
	 * Allows the user to add an incoming order to the onHands number
	 * 
	 * @param quantity
	 */
	public void addOnHands(int quantity) {
		onHands = onHands + quantity;
	}

}
