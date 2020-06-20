package amazinkart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProductService {
	private final static String productUrl = "https://api.jsonbin.io/b/5d31a1c4536bb970455172ca/latest";
	private final static String currenyExchangeUrl = "https://api.exchangeratesapi.io/latest";

	public void getProducts(String base, String promoCode) {
		List<ProductModel> productInputModels = getProductsInput();
		if (productInputModels != null && !productInputModels.isEmpty()) {
			Map<String, ExchangeRates> exchangeRates = getCurrencyExchangeRates(base);
			List<ProductModel> productOutputModels = getExchangeRatesPrice(productInputModels, exchangeRates, base);
			applydiscount(productOutputModels, promoCode);
			exportJson(productOutputModels);
		}
	}

	private void exportJson(List<ProductModel> productModels) {
		if (productModels != null && !productModels.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.writeValue(new File("c:\\amazinkart\\Products.json"), productModels);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				String jsonString = mapper.writeValueAsString(productModels);
				System.out.println(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void applydiscount(List<ProductModel> productModels, String promoCode) {
		if (productModels != null && !productModels.isEmpty()) {
			productModels.stream()
					.forEach(productModel -> ProductDiscountService.getInstance().getDiscount(productModel, promoCode));
			// for (ProductModel productModel : productModels) {
			// ProductDiscountService.getInstance().getDiscount(productModel,
			// promoCode);
			// }
		}
	}

	private List<ProductModel> getExchangeRatesPrice(List<ProductModel> productModels,
			Map<String, ExchangeRates> exchangeRates, String outputBase) {
		if (productModels != null && !productModels.isEmpty() && exchangeRates != null) {
			productModels.stream()
					.forEach(productModel -> getExchangeRatesProductModel(productModel, exchangeRates, outputBase));
		}
		return productModels;
	}

	private ProductModel getExchangeRatesProductModel(ProductModel productModel,
			Map<String, ExchangeRates> exchangeRates, String outputBase) {
		if (!productModel.getCurrency().equalsIgnoreCase(outputBase)
				&& exchangeRates.get(productModel.getCurrency()) != null
				&& exchangeRates.get(productModel.getCurrency()).getRates() != null
				&& !exchangeRates.get(productModel.getCurrency()).getRates().isEmpty()
				&& exchangeRates.get(productModel.getCurrency()).getRates().get(outputBase.toUpperCase()) != null) {
			double exRate = exchangeRates.get(productModel.getCurrency().toUpperCase()).getRates()
					.get(outputBase.toUpperCase());
			productModel.setPrice(productModel.getPrice() * exRate);
			productModel.setCurrency(outputBase);
		}
		return productModel;
	}

	private Map<String, ExchangeRates> getCurrencyExchangeRates(String base) {
		Map<String, ExchangeRates> exchangeRates = new HashMap<String, ExchangeRates>();
		ExchangeRates latestExchangeRate = getCurrencyExchange(base);
		if (latestExchangeRate != null && latestExchangeRate.getBase() != null
				&& !latestExchangeRate.getBase().isEmpty() && latestExchangeRate.getRates() != null
				&& !latestExchangeRate.getRates().isEmpty()) {
			exchangeRates.put(latestExchangeRate.getBase().toUpperCase(), latestExchangeRate);
			latestExchangeRate.getRates().keySet().stream().forEach(tempBase -> {
				ExchangeRates tempExchangeRate = getCurrencyExchange(tempBase);
				if (tempExchangeRate != null && tempExchangeRate.getBase() != null
						&& !tempExchangeRate.getBase().isEmpty() && tempExchangeRate.getRates() != null
						&& !tempExchangeRate.getRates().isEmpty()) {
					exchangeRates.put(tempExchangeRate.getBase().toUpperCase(), tempExchangeRate);
				}
			});
		}
		return exchangeRates;

	}

	private ExchangeRates getCurrencyExchange(String base) {
		ExchangeRates exchangeRate = new ExchangeRates();
		JSONParser parser = new JSONParser();
		try {
			String tempCurrenyExchangeUrl = currenyExchangeUrl;
			if (base != null && !base.isEmpty()) {
				tempCurrenyExchangeUrl = currenyExchangeUrl + "?base=" + base;
			}
			URL currExchangeUrlObj = new URL(tempCurrenyExchangeUrl);
			URLConnection currExchangeUrlConnection = currExchangeUrlObj.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(currExchangeUrlConnection.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				ObjectMapper mapper = new ObjectMapper();
				JSONObject js = (JSONObject) parser.parse(inputLine);
				if (js != null) {
					exchangeRate = mapper.readValue(String.valueOf(js), ExchangeRates.class);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return exchangeRate;
	}

	private List<ProductModel> getProductsInput() {
		List<ProductModel> productInputModels = new ArrayList<ProductModel>();
		JSONParser parser = new JSONParser();
		try {
			URL productUrlObj = new URL(productUrl);
			URLConnection productUrlConnection = productUrlObj.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(productUrlConnection.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				JSONArray productsRaw = (JSONArray) parser.parse(inputLine);
				for (Object productRaw : productsRaw) {
					try {
						ProductModel productInputModel = new ProductModel();
						JSONObject productInputRaw = (JSONObject) productRaw;
						productInputModel.setProduct((String) productInputRaw.get("product"));
						productInputModel.setCategory((String) productInputRaw.get("category"));
						productInputModel.setInventory((long) productInputRaw.get("inventory"));
						if (productInputRaw.get("rating") instanceof Double) {
							productInputModel.setRating((double) productInputRaw.get("rating"));
						} else if (productInputRaw.get("rating") instanceof Long) {
							productInputModel.setRating((long) productInputRaw.get("rating"));
						}
						productInputModel.setCurrency(((String) productInputRaw.get("currency")).toUpperCase());
						if (productInputRaw.get("price") instanceof Double) {
							productInputModel.setPrice((double) productInputRaw.get("price"));
						} else if (productInputRaw.get("price") instanceof Long) {
							productInputModel.setPrice((long) productInputRaw.get("price"));
						}
						productInputModel.setOrigin((String) productInputRaw.get("origin"));
						productInputModel.setArrival((String) productInputRaw.get("arrival"));

						productInputModels.add(productInputModel);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return productInputModels;
	}

}
