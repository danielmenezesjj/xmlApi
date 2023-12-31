package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.Dtos.ProdutoDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoRepository;
import com.xml.xmlApi.core.businessRule.FornecedorBusiness;
import com.xml.xmlApi.core.businessRule.FornecedorEnderecoBusiness;
import com.xml.xmlApi.core.businessRule.ProdutoBusiness;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.Produto.Produto;
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
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoBusiness produtoBusiness;



    @PostMapping
    public ResponseEntity postFornecedor (@RequestBody ProdutoDTO data) throws EntityAlreadyExistExceptionCdFornecedor {
            Produto produto = new Produto(data);
            produtoBusiness.postProduto(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produto);
        }


    @GetMapping
    public ResponseEntity getProdutos(Pageable pageable){
        return ResponseEntity.ok(produtoBusiness.getAll(pageable));
    }

    @GetMapping("/{cdproduto}")
    public ResponseEntity getOneProduto(@PathVariable String cdproduto) throws EntityNotExistExceptionCdFornecedor {
        return ResponseEntity.ok(produtoBusiness.getOne(cdproduto));
    }


    @PutMapping("/{cdproduto}")
    @Transactional
    public ResponseEntity putProduto(@PathVariable String cdproduto, @RequestBody ProdutoDTO data) throws EntityNotExistException{
        produtoBusiness.updateProduto(cdproduto, data);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{cdproduto}")
    public ResponseEntity deleteProduto(@PathVariable String cdproduto) throws  EntityNotExistExceptionCdFornecedor {
        produtoBusiness.deleteProduto(cdproduto);
        return ResponseEntity.noContent().build();

    }


}
