package com.ecommerce.productservice.service;

import java.util.List;

import com.ecommerce.sharedlibrary.model.ProductDto;

public interface ProductService {

	List<ProductDto> getAllProducts();

	ProductDto getProductById(long id);

	ProductDto createProduct(ProductDto product);

	ProductDto updateProduct(long id, ProductDto product);

	void deleteProduct(long id);
}
