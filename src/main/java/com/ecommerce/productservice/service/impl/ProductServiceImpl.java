package com.ecommerce.productservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import com.ecommerce.productservice.exception.EcommerceException;
import com.ecommerce.productservice.model.ProductDto;

@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ProductDto> getAllProducts() {
		List<ProductDto> products = new ArrayList<>();
		productRepository.findAll().forEach(product -> {
			products.add(mapper.map(product, ProductDto.class));
		});
		return products;
	}

	@Override
	public ProductDto getProductById(long id) {
		Optional<Product> product = productRepository.findById(id);
		return (product.isPresent() ? mapper.map(product.get(), ProductDto.class) : null);
	}

	@Override
	public ProductDto createProduct(ProductDto productDto) {
		Product product = mapper.map(productDto, Product.class);
		return mapper.map(productRepository.save(product), ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(long id, ProductDto productDto) {
		Optional<Product> updatedProduct = productRepository.findById(id).map(existingProduct -> {
			Product product = mapper.map(productDto, Product.class);
			return productRepository.save(existingProduct.updateWith(product));
		});

		return (updatedProduct.isPresent() ? mapper.map(updatedProduct.get(), ProductDto.class) : null);
	}

	@Override
	public void deleteProduct(long id) {
		// Do not delete if the product is included any of the order with status like -
		// PROCESSING

		if (getProductById(id) != null) {
			productRepository.deleteById(id);
			LOGGER.info("Product deleted Successfully");
		} else {
			throw new EcommerceException("product-not-found", String.format("Product with id=%d not found", id),
					HttpStatus.NOT_FOUND);
		}
	}
}
