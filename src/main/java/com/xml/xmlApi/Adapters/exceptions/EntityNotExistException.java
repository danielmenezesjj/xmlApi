package com.xml.xmlApi.Adapters.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotExistException extends Exception {
    private String message;

    public EntityNotExistException(String cnpj) {
        this.message = "Entidade com CNPJ " + cnpj + " não encontrada.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
