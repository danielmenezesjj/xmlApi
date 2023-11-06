package com.xml.xmlApi.Adapters.exceptions.ExceptionNotRastro;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
public class TraceDoesNotExist extends Exception {
    private String message;

    public TraceDoesNotExist() {
        this.message = "Esse XML não contem lote, favor da entrada manual!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
