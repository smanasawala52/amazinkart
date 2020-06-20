package amazinkart;

public class ProductTest {

	public static void main(String[] args) {
		System.setProperty("http.agent", "Chrome");
		ProductService ps = new ProductService();
		String base = "INR";
		String promotionSet = "promotionSetB";
		if (args.length > 0) {
			promotionSet = args[0];
		}
		ps.getProducts(base, promotionSet);
	}
}
