package com.insane.apiwtb.exception;

public class CategoryNotFoundException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException(String mensagem) {
        super(mensagem);
    }

    public CategoryNotFoundException(Integer categoryId) {
        this(String.format("Categoria com o código %d não encontrada!", categoryId));
    }

}
