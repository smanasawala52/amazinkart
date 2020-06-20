package amazinkart;

public class ProductModel {
	private String category = "";
	private String arrival = "";
	private long inventory = 0;
	private double rating = 0;
	private String currency = "";
	private double price = 0;
	private String origin = "";
	private String product = "";
	private Discount discount = null;
	private double priceAfterDiscount = 0;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the discount
	 */
	public Discount getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	/**
	 * @return the priceAfterDiscount
	 */
	public double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	/**
	 * @param priceAfterDiscount
	 *            the priceAfterDiscount to set
	 */
	public void setPriceAfterDiscount(double priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	/**
	 * @return the arrival
	 */
	public String getArrival() {
		return arrival;
	}

	/**
	 * @param arrival the arrival to set
	 */
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

}
