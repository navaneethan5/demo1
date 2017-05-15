package com.tgt.myretail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tgt.myretail.domain.ProductPricing;

public interface ProductPricingRepository extends MongoRepository<ProductPricing, String>{
	
	public ProductPricing findByProductId(String productId);

}
