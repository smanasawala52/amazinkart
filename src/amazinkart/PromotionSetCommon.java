/**
 * 
 */
package amazinkart;

/**
 * @author SManasawala
 *
 */
public class PromotionSetCommon implements PromotionSet {
	private static PromotionSet me = new PromotionSetCommon();

	public static PromotionSet getInstance() {
		return me;
	}

	@Override
	public void applyDiscount(ProductModel productInputModel, String promoCode) {
		Discount discount = productInputModel.getDiscount();
		if (productInputModel != null && discount == null && productInputModel.getPrice() >= 1000) {
			Discount tempDiscount = new Discount();
			tempDiscount.setAmount(0.02 * productInputModel.getPrice());
			tempDiscount.setDiscountTag("Get 2% off");
			if (discount == null || discount.getAmount() > tempDiscount.getAmount()) {
				discount = tempDiscount;
			}
		}
		if (discount != null && discount.getAmount() > 0) {
			productInputModel.setDiscount(discount);
			productInputModel.setPriceAfterDiscount(productInputModel.getPrice() - discount.getAmount());
		}
	}
}
