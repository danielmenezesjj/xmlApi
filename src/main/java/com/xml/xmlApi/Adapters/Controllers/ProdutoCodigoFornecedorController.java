package com.xml.xmlApi.Adapters.Controllers;


import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.Dtos.ProdutoCodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.CodigoFornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoCodigoFornecedorRepository;
import com.xml.xmlApi.core.businessRule.CodigoFornecedorBusiness;
import com.xml.xmlApi.core.businessRule.ProdutoCodigoFornecedorBusiness;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.ProdutoCodigoFornecedor.ProdutoCodigoFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/produtosassociados")
public class ProdutoCodigoFornecedorController {

    @Autowired
    private ProdutoCodigoFornecedorBusiness produtoCodigoFornecedorBusiness;

    @Autowired
    private CodigoFornecedorRepository codigoFornecedorRepository;




    @PostMapping
    public ResponseEntity postCodigoFornecedor(@RequestBody ProdutoCodigoFornecedorDTO data) throws EntityAlreadyExistExceptionCdFornecedor {
        // Obtenha o CodigoDoFornecedor com base nos dados do DTO
        Optional<CodigoDoFornecedor> codigoDoFornecedorOptional = codigoFornecedorRepository.findById(data.codigoFornecedorId());

        if (codigoDoFornecedorOptional.isPresent()) {
            CodigoDoFornecedor codigoDoFornecedor = codigoDoFornecedorOptional.get();

            // Chame o método associarProduto com o CodigoDoFornecedor e o ID do Produto
            CodigoDoFornecedor resultado = produtoCodigoFornecedorBusiness.associarProduto(codigoDoFornecedor, data.produtoId());
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } else {
            // Lida com o caso em que o CodigoDoFornecedor com o ID especificado não foi encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CodigoDoFornecedor não encontrado com o ID: " + data.codigoFornecedorId());
        }
    }


    @GetMapping
    public ResponseEntity getProdutosAssociados(Pageable pageable){
        return ResponseEntity.ok(produtoCodigoFornecedorBusiness.getAll(pageable));
    }



    @GetMapping("/{cdProduto}")
    public ResponseEntity getOnecdFornecedor(@PathVariable String cdProduto) throws EntityNotExistExceptionCdFornecedor {
        return ResponseEntity.ok(produtoCodigoFornecedorBusiness.getAllComParametro(cdProduto));
    }


//
//    @GetMapping("/{cdfornecedor}")
//    public ResponseEntity getOnecdFornecedor(@PathVariable String cdfornecedor) throws EntityNotExistExceptionCdFornecedor {
//        return ResponseEntity.ok(codigoFornecedorBusiness.getOne(cdfornecedor));
//    }
//
//    @GetMapping("/materiais/{cdempresa}")
//    public ResponseEntity getAllCdMaterias(@PathVariable Integer cdempresa) throws EntityNotExistExceptionCdFornecedor {
//        return ResponseEntity.ok(codigoFornecedorBusiness.getAllCodigosEmpresa(cdempresa));
//    }
//
//    @PutMapping("/{cdempresa}")
//    @Transactional
//    public ResponseEntity putFornecedor(@PathVariable String cdFornecedor, @RequestBody CodigoFornecedorDTO data) throws EntityNotExistException{
//        codigoFornecedorBusiness.updateCodigoFornecedor(cdFornecedor, data);
//        return ResponseEntity.ok().build();
//    }
//    @DeleteMapping("/{cdfornecedor}")
//    public ResponseEntity deleteCodigoFornecedor(@PathVariable String cdfornecedor) throws EntityNotExistExceptionCdFornecedor {
//        codigoFornecedorBusiness.deleteCodigoFornecedor(cdfornecedor);
//        return ResponseEntity.noContent().build();
//
//    }


}
