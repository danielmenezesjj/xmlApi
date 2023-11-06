package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.DocumentoDTO;
import com.xml.xmlApi.Adapters.Dtos.ProdutoDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.core.businessRule.DocumentoBusiness;
import com.xml.xmlApi.core.businessRule.ProdutoBusiness;
import com.xml.xmlApi.core.domain.Documento.Documento;
import com.xml.xmlApi.core.domain.Produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoBusiness documentoBusiness;



    @PostMapping
    public ResponseEntity postFornecedor (@RequestBody DocumentoDTO data) throws EntityAlreadyExistExceptionCdFornecedor, EntityAlreadyExistException {
            Documento documento = new Documento(data);
            documentoBusiness.postFornecedor(documento);
            return ResponseEntity.status(HttpStatus.CREATED).body(documento);
        }

    @PostMapping("/xml")
    public ResponseEntity<Map<String, Object>> postDocumentoXML(@RequestParam("file") MultipartFile file) throws EntityAlreadyExistException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Map<String, Object> xmlMap = xmlMapper.readValue(file.getInputStream(), Map.class);

            Map<String, Object> ide = (Map<String, Object>) xmlMap.get("NFe");
            Map<String, Object> chvAcesso = (Map<String, Object>) xmlMap.get("protNFe");

            if (ide != null && chvAcesso != null) {
                ide = (Map<String, Object>) ide.get("infNFe");
                ide = (Map<String, Object>) ide.get("ide");
                chvAcesso = (Map<String, Object>) chvAcesso.get("infProt");

                // Crie um novo mapa para combinar as informações de ide e chvAcesso
                Map<String, Object> combinedData = new HashMap<>();
                combinedData.putAll(ide);
                combinedData.putAll(chvAcesso);

                documentoBusiness.postDocumentoXML(combinedData);

                return new ResponseEntity<>(combinedData, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
