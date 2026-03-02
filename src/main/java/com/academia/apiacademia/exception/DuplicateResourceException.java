package com.academia.apiacademia.exception;

/**
 * Exceção lançada quando há tentativa de criar recurso com dados já existentes
 * Ex: Email duplicado, CPF duplicado, etc.
 */
public class DuplicateResourceException extends RuntimeException {

    private final String recurso;
    private final String campo;
    private final Object valor;

    public DuplicateResourceException(String recurso, String campo, Object valor) {
        super(String.format("%s com %s '%s' já existe", recurso, campo, valor));
        this.recurso = recurso;
        this.campo = campo;
        this.valor = valor;
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

