package com.xml.xmlApi.Adapters.Controllers;


import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.core.businessRule.CodigoFornecedorBusiness;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/codigofornecedor")
public class CodigoFornecedorController {

    @Autowired
    private CodigoFornecedorBusiness codigoFornecedorBusiness;




    @PostMapping
    public ResponseEntity postCodigoFornecedor (@RequestBody CodigoFornecedorDTO data) throws EntityAlreadyExistExceptionCdFornecedor {
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
    public ResponseEntity getOnecdFornecedor(@PathVariable String cdfornecedor) throws EntityNotExistExceptionCdFornecedor {
        return ResponseEntity.ok(codigoFornecedorBusiness.getOne(cdfornecedor));
    }

    @GetMapping("/materiais/{cdempresa}")
    public ResponseEntity getAllCdMaterias(@PathVariable Integer cdempresa) throws EntityNotExistExceptionCdFornecedor {
        return ResponseEntity.ok(codigoFornecedorBusiness.getAllCodigosEmpresa(cdempresa));
    }

    @PutMapping("/{cdempresa}")
    @Transactional
    public ResponseEntity putFornecedor(@PathVariable String cdFornecedor, @RequestBody CodigoFornecedorDTO data) throws EntityNotExistException{
        codigoFornecedorBusiness.updateCodigoFornecedor(cdFornecedor, data);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{cdfornecedor}")
    public ResponseEntity deleteCodigoFornecedor(@PathVariable String cdfornecedor) throws EntityNotExistExceptionCdFornecedor {
        codigoFornecedorBusiness.deleteCodigoFornecedor(cdfornecedor);
        return ResponseEntity.noContent().build();

    }


}
