package amazinkart;

public class Discount {
	private double amount = 0;
	private String discountTag = "";

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the discountTag
	 */
	public String getDiscountTag() {
		return discountTag;
	}

	/**
	 * @param discountTag
	 *            the discountTag to set
	 */
	public void setDiscountTag(String discountTag) {
		this.discountTag = discountTag;
	}
}
