package com.insane.apiwtb.exception;

public class DressNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DressNotFoundException(String mensagem) {
        super(mensagem);
    }

    public DressNotFoundException(Integer dressId) {
        this(String.format("Vestido com o código %d não encontrado!", dressId));
    }

}
