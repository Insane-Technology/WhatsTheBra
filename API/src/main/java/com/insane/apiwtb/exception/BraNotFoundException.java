package com.insane.apiwtb.exception;

public class BraNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BraNotFoundException(String mensagem) {
        super(mensagem);
    }

    public BraNotFoundException(Integer braId) {
        this(String.format("Sutiã com o código %d não encontrado!", braId));
    }
}
