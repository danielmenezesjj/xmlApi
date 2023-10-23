package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.businessRule.FornecedorBusiness;
import com.xml.xmlApi.core.businessRule.FornecedorEnderecoBusiness;
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
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private FornecedorBusiness fornecedorBusiness;

    @Autowired
    private FornecedorEnderecoBusiness fornecedorEnderecoBusiness;


    @PostMapping("/xml")
    public ResponseEntity<Map<String, Object>> postFornecedorXML(@RequestParam("file") MultipartFile file) throws EntityAlreadyExistException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Map<String, Object> xmlMap = xmlMapper.readValue(file.getInputStream(), Map.class);

            Map<String, Object> ide = (Map<String, Object>) xmlMap.get("NFe");

            if (ide != null) {
                ide = (Map<String, Object>) ide.get("infNFe");
                ide = (Map<String, Object>) ide.get("emit");

                fornecedorBusiness.postFornecedorXML(ide);

                return new ResponseEntity<>(ide, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            // Trate ou registre a exceção de alguma forma, se necessário
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace(); // Você pode tratar ou registrar a exceção, se necessário
            throw e; // Lança a exceção personalizada
        }
    }

    @PostMapping
    public ResponseEntity postFornecedor (@RequestBody FornecedorDTO data) throws EntityAlreadyExistException {
            Fornecedor fornecedor = new Fornecedor(data);
            fornecedor.setEnderFornecedor(data.enderFornecedor());
            fornecedorBusiness.postFornecedor(fornecedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(fornecedor);
        }


    @GetMapping
    public ResponseEntity getFornecedor(Pageable pageable){
        return ResponseEntity.ok(fornecedorBusiness.getAll(pageable));
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity getOneFornecedor(@PathVariable String cnpj) throws EntityNotExistException {
        return ResponseEntity.ok(fornecedorBusiness.getOne(cnpj));
    }


    @PutMapping("/{cnpj}")
    @Transactional
    public ResponseEntity putFornecedor(@PathVariable String cnpj, @RequestBody FornecedorDTO data) throws EntityNotExistException{
        fornecedorBusiness.updateFornecedor(cnpj, data);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{cnpj}")
    public ResponseEntity deleteFornecedor(@PathVariable String cnpj) throws EntityNotExistException {
        fornecedorBusiness.deleteFornecedor(cnpj);
        return ResponseEntity.noContent().build();

    }


}
