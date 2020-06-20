package amazinkart;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRates {
	private Map<String, Double> rates = new HashMap<String, Double>();
	private String base = "";
	private Date date = new Date();

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
