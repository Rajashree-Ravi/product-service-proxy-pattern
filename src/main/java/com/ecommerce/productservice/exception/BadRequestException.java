package com.ecommerce.productservice.exception;

public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
