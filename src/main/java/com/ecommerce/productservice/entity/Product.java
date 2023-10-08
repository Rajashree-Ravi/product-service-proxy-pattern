package com.ecommerce.productservice.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String productCode;

	@NotBlank
	private String productName;

	@NotNull
	private BigDecimal price;

	private String description;

	@NotBlank
	private String category;

	public Product updateWith(Product product) {
		return new Product(this.id, product.productCode, product.productName, product.price, product.description,
				product.category);
	}
}
