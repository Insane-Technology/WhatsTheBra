package com.insane.apiwtb.exception;

public class BraTypeNotFoundException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BraTypeNotFoundException(String mensagem) {
        super(mensagem);
    }

    public BraTypeNotFoundException(Integer braTypeId) {
        this(String.format("Tipo de sutiã com o código %d não encontrado!", braTypeId));
    }

}