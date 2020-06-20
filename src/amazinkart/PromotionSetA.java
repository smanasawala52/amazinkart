/**
 * 
 */
package amazinkart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SManasawala
 *
 */
public class PromotionSetA implements PromotionSet {
	private static PromotionSet me = new PromotionSetA();

	public static PromotionSet getInstance() {
		return me;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see amazinkart.PromotionSet#getDiscount(amazinkart.ProductModel,
	 * java.lang.String)
	 */
	@Override
	public void applyDiscount(ProductModel productInputModel, String promoCode) {
		List<String> categories = new ArrayList<>();
		categories.add("electronics".toUpperCase());
		categories.add("furnishing".toUpperCase());
		Discount discount = null;
		if (productInputModel != null) {
			if ("Africa".equalsIgnoreCase(productInputModel.getOrigin())) {
				Discount tempDiscount = new Discount();
				tempDiscount.setAmount(0.07 * productInputModel.getPrice());
				tempDiscount.setDiscountTag("Get 7% off");
				discount = tempDiscount;
			}
			if (productInputModel.getRating() == 2) {
				Discount tempDiscount = new Discount();
				tempDiscount.setAmount(0.02 * productInputModel.getPrice());
				tempDiscount.setDiscountTag("Get 2% off");
				if (discount == null || discount.getAmount() > tempDiscount.getAmount()) {
					discount = tempDiscount;
				}
			}
			if (productInputModel.getRating() <= 2) {
				Discount tempDiscount = new Discount();
				tempDiscount.setAmount(0.08 * productInputModel.getPrice());
				tempDiscount.setDiscountTag("Get 8% off");
				if (discount == null || discount.getAmount() > tempDiscount.getAmount()) {
					discount = tempDiscount;
				}
			}
			if (productInputModel.getPrice() >= 500 && productInputModel.getCategory() != null
					&& !productInputModel.getCategory().isEmpty()
					&& categories.contains(productInputModel.getCategory().toUpperCase())) {
				Discount tempDiscount = new Discount();
				tempDiscount.setAmount(100);
				tempDiscount.setDiscountTag("Get Rs 100 off");
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
