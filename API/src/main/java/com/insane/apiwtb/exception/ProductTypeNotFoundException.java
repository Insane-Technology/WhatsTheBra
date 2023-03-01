package com.insane.apiwtb.exception;

public class ProductTypeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductTypeNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ProductTypeNotFoundException(Integer productTypeId) {
        this(String.format("Tipo de produto com o código %d não encontrado!", productTypeId));
    }

}