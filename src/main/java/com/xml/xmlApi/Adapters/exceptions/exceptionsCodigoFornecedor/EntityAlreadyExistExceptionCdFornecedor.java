package com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityAlreadyExistExceptionCdFornecedor extends Exception {

    private String message;
    public EntityAlreadyExistExceptionCdFornecedor(String cdFornecedor){
        this.message = "Código do fornecedor " + cdFornecedor + " já cadastrado.";

    }
    @Override
    public String getMessage() {
        return message;
    }
}
