package com.tgt.myretail.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.tgt.myretail.Application;
import com.tgt.myretail.domain.ProductDetail;
import com.tgt.myretail.domain.ProductPricing;
import com.tgt.myretail.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ProductControllerUnitTest {

	 private MockMvc mockMvc;
	 
	 @Autowired
	 private WebApplicationContext webApplicationContext;
	 
	 @Autowired 
	 MockHttpSession session;
	 
	 public ProductDetail productDetail;
	 public ProductPricing productPricing;
	 
	 @Mock
	 private ProductService productService;
	 
	 @Before
	 public void setup() {
		 this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
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
		 
		 session.setAttribute("productDetail", productDetail);
	 }
	 	 
	 @Test
	 public void testGetProduct() throws Exception {		 		 		 
		 mockMvc.perform(get("/products/13860428").session(session).
				 			accept(MediaType.APPLICATION_JSON)).
		 					andExpect(status().isOk()).
		 					andExpect(jsonPath("$.id", is("13860428"))).
		 					andExpect(jsonPath("$.name", is("The Big Lebowski (Blu-ray)"))).
		 					andExpect(jsonPath("$.current_price.currency_code", is("USD")));
		  		 
	 }
	 
}
