package amazinkart;

public class ProductDiscountService {
	private static ProductDiscountService me = new ProductDiscountService();

	public static ProductDiscountService getInstance() {
		return me;
	}

	public void getDiscount(ProductModel productModel, String promoCode) {
		PromotionSetEnum promo = PromotionSetEnum.valueOf(promoCode);
		PromotionSet promotionSet = null;
		if (promo == PromotionSetEnum.promotionSetA) {
			promotionSet = PromotionSetA.getInstance();
		} else if (promo == PromotionSetEnum.promotionSetB) {
			promotionSet = PromotionSetB.getInstance();
		}
		if (promotionSet != null) {
			promotionSet.applyDiscount(productModel, promoCode);
		}
		if (productModel.getDiscount() == null) {
			promotionSet = PromotionSetCommon.getInstance();
			promotionSet.applyDiscount(productModel, promoCode);
		}
	}
}
