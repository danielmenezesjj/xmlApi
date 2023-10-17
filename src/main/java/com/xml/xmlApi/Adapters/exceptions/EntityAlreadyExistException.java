package com.xml.xmlApi.Adapters.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityAlreadyExistException extends Exception {

    private String message;
    public EntityAlreadyExistException(String cnpj){
        this.message = "Entidade com CNPJ " + cnpj + " já cadastrada.";

    }
    @Override
    public String getMessage() {
        return message;
    }
}
