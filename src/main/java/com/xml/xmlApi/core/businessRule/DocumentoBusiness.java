package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.DocumentoRepository;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.domain.Documento.Documento;
import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
public class DocumentoBusiness {


    @Autowired
    private DocumentoRepository documentoRepository;

    public Documento postFornecedor(Documento documento) throws EntityAlreadyExistException {
        Optional<Documento> optionalDocumento = documentoRepository.findBychaveacesso(documento.getChaveacesso());
        if(optionalDocumento.isPresent()){
            throw new EntityAlreadyExistException(documento.getFornecedor());
        }else{
            return documentoRepository.save(documento);
        }
    }

    public void postDocumentoXML(Map<String, Object> ide) throws EntityAlreadyExistException {

        // Convert Map to Fornecedor
        Documento documento = convertMapToDocumento(ide);
        Optional<Documento> optionalDocumento = documentoRepository.findBychaveacesso(documento.getChaveacesso());

    }



    public Documento convertMapToDocumento(Map<String, Object> ide) {
        Documento documento = new Documento();
        documento.setFornecedor((String) ide.get("CNPJ"));
        documento.setChaveacesso((String) ide.get("xNome"));

        return documento;
    }




}
