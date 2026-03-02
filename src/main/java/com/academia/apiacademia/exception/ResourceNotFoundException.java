package com.academia.apiacademia.exception;

/**
 * Exceção genérica do aplicativo para casos de erro não encontrado
 * Utilizada quando um recurso não é encontrado no banco de dados
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String recurso;
    private final String campo;
    private final Object valor;

    public ResourceNotFoundException(String recurso, String campo, Object valor) {
        super(String.format("%s não encontrado com %s: '%s'", recurso, campo, valor));
        this.recurso = recurso;
        this.campo = campo;
        this.valor = valor;
    }

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
        this.recurso = null;
        this.campo = null;
        this.valor = null;
    }

    public String getRecurso() {
        return recurso;
    }

    public String getCampo() {
        return campo;
    }

    public Object getValor() {
        return valor;
    }
}

