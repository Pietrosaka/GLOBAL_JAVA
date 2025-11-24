package com.futurotrabalho.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Object id) {
        super(String.format("%s não encontrado com id: %s", resourceName, id));
    }
}

