package entities;

/**
 * Class is use to represent product in Store class.
 * 
 * @author Koua
 *
 */
public class Product {
	private String name;
	private String id;
	private int stock;
	private double price;
	private int reorderLevel;

	public Product(String name, String id, int stock, double price, int reorderLevel) {
		this.name = name;
		this.stock = stock;
		this.price = price;
		this.reorderLevel = reorderLevel;
		this.id = id;
	}

	public Product(String name, String id, String stock, String price, String reorderLevel) {
		this.name = name;
		this.stock = Integer.parseInt(stock);
		this.price = Double.parseDouble(price);
		this.reorderLevel = Integer.parseInt(reorderLevel);
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
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

}
