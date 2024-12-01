package com.juan_zubiri.monitoreo.security;

public class EmptyDatabaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public EmptyDatabaseException(String message) {
        super(message);
    }
}
