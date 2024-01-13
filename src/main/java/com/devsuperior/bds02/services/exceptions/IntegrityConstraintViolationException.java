package com.devsuperior.bds02.services.exceptions;

public class IntegrityConstraintViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IntegrityConstraintViolationException(String message) {
        super(message);
    }
}
