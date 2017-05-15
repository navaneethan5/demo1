package com.tgt.myretail.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgt.myretail.constant.MyRetailConstant;
import com.tgt.myretail.domain.ProductDetail;
import com.tgt.myretail.domain.ProductPricing;
import com.tgt.myretail.service.ProductService;
import com.tgt.myretail.util.Errors;
import com.tgt.myretail.util.Message;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	/**
	 * Request to get product summary along with the pricing This will call
	 * external API to get product detail and call database to retrieve pricing
	 * information
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findByProductDetail(@PathVariable String productId, @RequestParam("key") String key) {

		if (!key.equals("1234")) {
			Message message = new Message();

			message.setMessage("Unauthorized");
			message.setErrors(new Errors("key", "Invalid Key"));

			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}

		if (null == productId || "".equals(productId)) {
			Message message = new Message();

			message.setMessage("Invalid Params");
			message.setErrors(new Errors("productId", "Product id is null/empty"));

			return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
		}

		ProductDetail result = null;
		try {
			result = (ProductDetail) productService.getProductSummary(productId);
			if (null == result) {
				Message message = new Message();
				message.setMessage("Pricing details not found");
				message.setErrors(new Errors("productId", "Pricing details not found for the given product id"));
				return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			Message nessage = new Message();
			nessage.setMessage("IO Exception occured");
			nessage.setErrors(new Errors("productId", e.getMessage()));
			return new ResponseEntity<Message>(nessage, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProductDetail>(result, HttpStatus.OK);
	}

	/**
	 * Update product pricing. This request required HTTP request to have
	 * Content-Type as application/json
	 * 
	 * @param productId
	 * @param accept
	 * @param contentType
	 * @param productDetail
	 * @return
	 */
	@RequestMapping(value = "/{productId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProductPrice(@PathVariable String productId, @RequestHeader(value = "Accept") String accept,
			@RequestHeader(value = "Content-Type") String contentType, @Valid @RequestBody ProductDetail productDetail,
			@RequestParam("key") String key ) {

		if (!key.equals("1234")) {
			Message message = new Message();

			message.setMessage("Unauthorized");
			message.setErrors(new Errors("key", "Invalid Key"));

			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}

		ProductDetail result = (ProductDetail)productService.updateProductPrice(productDetail, productId);
		
		if (null == result) {
			Message message = new Message();
			message.setMessage("Pricing details not found");
			message.setErrors(new Errors("productId", "Pricing details not found for the given product id"));
			return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProductDetail>(result, HttpStatus.OK);

	}

	@RequestMapping
	public ResponseEntity<?> handleInvalidURL() {

		Message message = new Message();

		message.setMessage("Invalid URL/params");
		message.setErrors(new Errors("url", "URL path should be in the form of /products/{id}"));

		return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);

	}

}
