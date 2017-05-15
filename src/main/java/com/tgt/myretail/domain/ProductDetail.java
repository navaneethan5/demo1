package com.tgt.myretail.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetail {
	
	@JsonProperty("name")
	public String title;
	
	@JsonProperty("id")
	public String tcin;

	@JsonProperty("current_price")
	public ProductPricing productPricing;
	
	@JsonIgnore
	public String message;
	
	public ProductDetail(){		
	}
	
	public ProductDetail(String title, String tcin, ProductPricing productPricing) {
		this.title = title;
		this.tcin = tcin;
		this.productPricing = productPricing;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTcin() {
		return tcin;
	}

	public void setTcin(String tcin) {
		this.tcin = tcin;
	}

	public ProductPricing getProductPricing() {
		return productPricing;
	}

	public void setProductPricing(ProductPricing productPricing) {
		this.productPricing = productPricing;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
