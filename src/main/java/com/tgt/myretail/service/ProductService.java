package com.tgt.myretail.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.myretail.constant.MyRetailConstant;
import com.tgt.myretail.controller.ProductController;
import com.tgt.myretail.domain.ProductDetail;
import com.tgt.myretail.domain.ProductPricing;
import com.tgt.myretail.repository.ProductPricingRepository;
import com.tgt.myretail.util.Message;

@Service
public class ProductService {
	
	@Autowired
	ProductPricingRepository productPricingRepository;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	/**
	 * Update pricing information for specific productDetail in database
	 * @param productDetail
	 * @param productId 
	 * @return
	 */
	public Object updateProductPrice(ProductDetail productDetail, String productId) {
		try {
			ProductPricing productPricing = productPricingRepository.findByProductId(productId);
			if(null == productPricing){
				return productPricing;
			}
			

			if (productPricing.getProductId() != null) {
				double oldPrice = productPricing.getCurrentPrice();
				productPricing.setCurrentPrice(productDetail.getProductPricing().getCurrentPrice());
				productPricingRepository.save(productPricing);
			}
			
			productDetail.setProductPricing(productPricing);
			
			return productDetail;
			
		} catch (Exception e) {
			Message message = new Message();
		//	message.setStatus(MyRetailConstant.STATUS_ERROR);
		//	message.setMessage(MyRetailConstant.DB_ERROR_CANNOT_RETRIEVE_PRICING);
			return message;
		}
	}
	
	/**
	 * Get product detail from external API and retrieve pricing information from database
	 * @param productId
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public Object getProductSummary(String productId) throws JsonProcessingException, IOException {

			Object result = getProductDetail(productId); 
			if (result instanceof ProductDetail) {
				ProductPricing productPricing = productPricingRepository.findByProductId(productId);
				((ProductDetail) result).setProductPricing(productPricing);
			}
			return result;

	}
	
	/**
	 * Call external API to retrieve product detail by productId
	 * @param productId
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public Object getProductDetail(String productId) throws JsonProcessingException, IOException   {	
		ResponseEntity<String> response = null;

		try{
			
		
			ProductDetail productDetail = new ProductDetail();
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.getForEntity(constructURL(productId), String.class);	
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());					
			JsonNode tcin = root.path("product").path("item").path("tcin");			
			JsonNode title = root.path("product").path("item").path("product_description").path("title");			
			JsonNode message = root.path("product").path("item").path("message");
			productDetail.setTcin(tcin.asText());
			productDetail.setTitle(title.asText());		
			productDetail.setMessage(message.asText());			
			return productDetail;
		}

		catch(Exception e){
			
			return response;
		}
		
	}
	
	/**
	 * Return target URL
	 * @param productId
	 * @return
	 */
	private String constructURL(String productId) {
		return MyRetailConstant.REDSKY_ENDPOINT + "/" + productId;
	}
}
