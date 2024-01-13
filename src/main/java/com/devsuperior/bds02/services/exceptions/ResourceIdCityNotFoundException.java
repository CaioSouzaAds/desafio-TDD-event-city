package com.devsuperior.bds02.services.exceptions;

public class ResourceIdCityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceIdCityNotFoundException(String message) {
        super(message);
    }
}
