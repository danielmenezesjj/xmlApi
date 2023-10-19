package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.EmpresaDTO;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.businessRule.EmpresaBusiness;
import com.xml.xmlApi.core.businessRule.EmpresaEnderecoBusiness;
import com.xml.xmlApi.core.businessRule.FornecedorBusiness;
import com.xml.xmlApi.core.businessRule.FornecedorEnderecoBusiness;
import com.xml.xmlApi.core.domain.Empresa.Empresa;
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
@RequestMapping("/empresa")
public class EmpresaController {


    @Autowired
    private EmpresaBusiness empresaBusiness;

    @Autowired
    private EmpresaEnderecoBusiness empresaEnderecoBusiness;


    @PostMapping("/xml")
    @Transactional
    public ResponseEntity<Map<String, Object>> postEmpresaXML(@RequestParam("file") MultipartFile file) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Map<String, Object> xmlMap = xmlMapper.readValue(file.getInputStream(), Map.class);

            Map<String, Object> ide = (Map<String, Object>) xmlMap.get("NFe");

            if (ide != null) {
                ide = (Map<String, Object>) ide.get("infNFe");
                ide = (Map<String, Object>) ide.get("emit");


                empresaBusiness.postEmpresaXML(ide);

                return new ResponseEntity<>(ide, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityAlreadyExistException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity postEmpresa(@RequestBody EmpresaDTO data) throws EntityAlreadyExistException {
            Empresa empresa = new Empresa(data);
            empresa.setEnderEmpresa(data.enderEmpresa());
            empresaBusiness.postEmpresa(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
        }


    @GetMapping
    public ResponseEntity getEmpresa(Pageable pageable){
        return ResponseEntity.ok(empresaBusiness.getAll(pageable));
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity getOneEmpresa(@PathVariable String cnpj) throws EntityNotExistException {
        return ResponseEntity.ok(empresaBusiness.getOne(cnpj));
    }


    @PutMapping("/{cnpj}")
    @Transactional
    public ResponseEntity putEmpresa(@PathVariable String cnpj, @RequestBody EmpresaDTO data) throws EntityNotExistException{
        empresaBusiness.updateEmpresa(cnpj, data);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{cnpj}")
    public ResponseEntity deleteEmpresa(@PathVariable String cnpj) throws EntityNotExistException {
        empresaBusiness.deleteEmpresa(cnpj);
        return ResponseEntity.noContent().build();

    }


}
