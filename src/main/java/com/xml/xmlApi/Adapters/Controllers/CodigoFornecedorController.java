package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.businessRule.CodigoFornecedorBusiness;
import com.xml.xmlApi.core.businessRule.FornecedorBusiness;
import com.xml.xmlApi.core.businessRule.FornecedorEnderecoBusiness;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/codigofornecedor")
public class CodigoFornecedorController {

    @Autowired
    private CodigoFornecedorBusiness codigoFornecedorBusiness;




    @PostMapping
    public ResponseEntity postCodigoFornecedor (@RequestBody CodigoFornecedorDTO data) throws EntityAlreadyExistException {
            CodigoDoFornecedor codigoDoFornecedor = new CodigoDoFornecedor(data);
        // Salve o CodigoDoFornecedor
            codigoFornecedorBusiness.postCodigoFornecedor(codigoDoFornecedor, data.fornecedor_id());
            return ResponseEntity.status(HttpStatus.CREATED).body(codigoDoFornecedor);
        }


    @GetMapping
    public ResponseEntity getCodigoFornecedor(Pageable pageable){
        return ResponseEntity.ok(codigoFornecedorBusiness.getAll(pageable));
    }

    @GetMapping("/{cdfornecedor}")
    public ResponseEntity getOnecdFornecedor(@PathVariable String cdfornecedor) throws EntityNotExistException {
        return ResponseEntity.ok(codigoFornecedorBusiness.getOne(cdfornecedor));
    }


//    @PutMapping("/{cnpj}")
//    @Transactional
//    public ResponseEntity putFornecedor(@PathVariable String cnpj, @RequestBody FornecedorDTO data) throws EntityNotExistException{
//        fornecedorBusiness.updateFornecedor(cnpj, data);
//        return ResponseEntity.ok().build();
//    }
//    @DeleteMapping("/{cnpj}")
//    public ResponseEntity deleteFornecedor(@PathVariable String cnpj) throws EntityNotExistException {
//        fornecedorBusiness.deleteFornecedor(cnpj);
//        return ResponseEntity.noContent().build();
//
//    }


}
