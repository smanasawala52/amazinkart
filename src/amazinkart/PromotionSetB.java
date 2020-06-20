/**
 * 
 */
package amazinkart;

/**
 * @author SManasawala
 *
 */
public class PromotionSetB implements PromotionSet {
	private static PromotionSet me = new PromotionSetB();

	public static PromotionSet getInstance() {
		return me;
	}

	@Override
	public void applyDiscount(ProductModel productInputModel, String promoCode) {
		Discount discount = null;
		if (productInputModel != null) {
			if (productInputModel.getInventory() > 20) {
				Discount tempDiscount = new Discount();
				tempDiscount.setAmount(0.12 * productInputModel.getPrice());
				tempDiscount.setDiscountTag("Get 12% off");
				if (discount == null || discount.getAmount() > tempDiscount.getAmount()) {
					discount = tempDiscount;
				}
			}
			if (productInputModel.getArrival() != null && !productInputModel.getArrival().isEmpty()
					&& "NEW".equalsIgnoreCase(productInputModel.getArrival())) {
				Discount tempDiscount = new Discount();
				tempDiscount.setAmount(0.07 * productInputModel.getPrice());
				tempDiscount.setDiscountTag("Get 7% off");
				if (discount == null || discount.getAmount() > tempDiscount.getAmount()) {
					discount = tempDiscount;
				}
			}
		}
		if (discount != null && discount.getAmount() > 0) {
			productInputModel.setDiscount(discount);
			productInputModel.setPriceAfterDiscount(productInputModel.getPrice() - discount.getAmount());
		}
	}

}
