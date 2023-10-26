package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.businessRule.FornecedorBusiness;
import com.xml.xmlApi.core.businessRule.FornecedorEnderecoBusiness;
import com.xml.xmlApi.core.businessRule.LoteBusiness;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private LoteBusiness loteBusiness;



    @PostMapping("/entrada/lote/xml")
    public ResponseEntity<List<String>> postLoteXML(@RequestParam("file") MultipartFile file) throws EntityAlreadyExistException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Map<String, Object> xmlMap = xmlMapper.readValue(file.getInputStream(), Map.class);

            Map<String, Object> ide = (Map<String, Object>) xmlMap.get("NFe");
            if (ide != null) {
                ide = (Map<String, Object>) ide.get("infNFe");
                Object detalhes = ide.get("det");

                List<String> cProdList = new ArrayList<>();

                if (detalhes instanceof List) {
                    List<Map<String, Object>> detalhesList = (List<Map<String, Object>>) detalhes;
                    for (Map<String, Object> detalhe : detalhesList) {
                        Map<String, Object> prod = (Map<String, Object>) detalhe.get("prod");
                        String cProd = (String) prod.get("cProd");
                        cProdList.add(cProd);
                    }
                    return new ResponseEntity<>(cProdList, HttpStatus.OK);
                } else if (detalhes instanceof Map) {
                    Map<String, Object> prod = (Map<String, Object>) ((Map<String, Object>) detalhes).get("prod");
                    String cProd = (String) prod.get("cProd");
                    cProdList.add(cProd);
                    return new ResponseEntity<>(cProdList, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
