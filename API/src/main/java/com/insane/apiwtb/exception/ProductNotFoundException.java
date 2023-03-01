package com.insane.apiwtb.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ProductNotFoundException(Integer dressId) {
        this(String.format("Produto com o código %d não encontrado!", dressId));
    }

}
