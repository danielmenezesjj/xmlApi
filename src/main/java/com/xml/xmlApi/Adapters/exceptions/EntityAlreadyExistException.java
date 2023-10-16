package com.xml.xmlApi.Adapters.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityAlreadyExistException extends Exception {
    public EntityAlreadyExistException(String cnpj){

    }
}
