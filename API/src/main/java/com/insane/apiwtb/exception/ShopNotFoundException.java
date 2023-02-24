package com.insane.apiwtb.exception;

public class ShopNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ShopNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ShopNotFoundException(Integer shopId) {
        this(String.format("Loja com código %d não encontrada!", shopId));
    }

}