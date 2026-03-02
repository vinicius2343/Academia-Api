package com.academia.apiacademia.exception;

/**
 * Exceção lançada quando há erro de negócio no aplicativo
 * Ex: Tentativa de operação inválida, estado inválido, etc.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensagem) {
        super(mensagem);
    }

    public BusinessException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

