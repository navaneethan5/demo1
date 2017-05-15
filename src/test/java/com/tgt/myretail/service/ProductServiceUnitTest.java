package com.tgt.myretail.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.tgt.myretail.domain.ProductDetail;
import com.tgt.myretail.domain.ProductPricing;

@RunWith(SpringRunner.class)
public class ProductServiceUnitTest {
	
	
	private ProductPricing productPricing;
	private ProductDetail productDetail;
	private ProductDetail result;

	private ProductService productService = new ProductService();
	
	@Before
	public void setup() {
		//set productPricing
		 productPricing = new ProductPricing();
		 productPricing.setCurrencyName("USD");
		 productPricing.setCurrentPrice(19.99);
		 productPricing.setProductId("13860428");
		 
		 //set productDetail
		 productDetail = new ProductDetail();
		 productDetail.setTcin("13860428");
		 productDetail.setTitle("The Big Lebowski (Blu-ray)");
		 productDetail.setProductPricing(productPricing);	
	}
	
	@Test
	public void testGetProductDetail() throws Exception {
		result = (ProductDetail)productService.getProductDetail("13860428");
		assertEquals(result.getTcin(), productDetail.getTcin());
		assertEquals(result.getTitle(), productDetail.getTitle());		
	}

}
