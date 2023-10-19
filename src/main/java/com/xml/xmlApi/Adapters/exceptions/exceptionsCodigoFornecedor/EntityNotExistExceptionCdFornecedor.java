package com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotExistExceptionCdFornecedor extends Exception {
    private String message;

    public EntityNotExistExceptionCdFornecedor(String cdFornecedor) {
        this.message = "Produto do fornecedor não encontrado " + cdFornecedor;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
