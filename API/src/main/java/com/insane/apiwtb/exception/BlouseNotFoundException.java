package com.insane.apiwtb.exception;

public class BlouseNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BlouseNotFoundException(String mensagem) {
        super(mensagem);
    }

    public BlouseNotFoundException(Integer blouseId) {
        this(String.format("Blusa com o código %d não encontrada!", blouseId));
    }

}
