package com.tgt.myretail.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "ProductPricing")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPricing {
	
	@Id
	private String id;

	@JsonIgnore
	private String productId;
	
	@JsonProperty("value")
	private double currentPrice;
	
	@JsonProperty("currency_code")
	private String currencyName;
	
	public ProductPricing(){		
	}
	
	public ProductPricing(String productId, double currentPrice, String currencyName) {
		this.productId = productId;
		this.currentPrice = currentPrice;
		this.currencyName = currencyName;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public double getCurrentPrice() {
		return currentPrice;
	}
	
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	
}
