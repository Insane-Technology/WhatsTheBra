package com.insane.apiwtb.exception;

public class ImageNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ImageNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ImageNotFoundException(Integer imageId) {
        this(String.format("Imagem com o código %d não encontrada!", imageId));
    }
}
