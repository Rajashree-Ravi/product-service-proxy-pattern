package com.ecommerce.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.service.ProductService;
import com.ecommerce.sharedlibrary.exception.EcommerceException;
import com.ecommerce.sharedlibrary.model.ProductDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage products in e-commerce application")
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping
	@ApiOperation(value = "View all products", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all products"),
			@ApiResponse(code = 204, message = "Products list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<ProductDto>> getAllProducts() {

		List<ProductDto> products = productService.getAllProducts();
		if (products.isEmpty())
			throw new EcommerceException("no-content", "Products list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific product with the specified product id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved product with the product id"),
			@ApiResponse(code = 404, message = "Product with specified product id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id) {

		ProductDto product = productService.getProductById(id);
		if (product != null)
			return new ResponseEntity<>(product, HttpStatus.OK);
		else
			throw new EcommerceException("product-not-found", String.format("Product with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new product", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a product"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
		return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a product information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated product information"),
			@ApiResponse(code = 404, message = "Product with specified product id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") long id, @RequestBody ProductDto product) {

		ProductDto updatedProduct = productService.updateProduct(id, product);
		if (updatedProduct != null)
			return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		else
			throw new EcommerceException("product-not-found", String.format("Product with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a product", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted product information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {

		productService.deleteProduct(id);
		return new ResponseEntity<>("Product deleted successfully", HttpStatus.NO_CONTENT);
	}
}
