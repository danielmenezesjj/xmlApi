package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.businessRule.*;
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

    @Autowired
    private ProdutoCodigoFornecedorBusiness produtoCodigoFornecedorBusiness;

    @Autowired
    private LeituraXMLBusiness leituraXMLBusiness;




    @PostMapping("/entrada/lote/xml")
    public ResponseEntity<List<String>> postLoteXML(@RequestParam("file") MultipartFile file) throws EntityAlreadyExistException {
        try {
            List<String> cProdList = leituraXMLBusiness.processarXML(file);

            if (!cProdList.isEmpty()) {
                List<String> codigosAssociados = leituraXMLBusiness.getCodigosAssociados(cProdList);

                if (!codigosAssociados.isEmpty()) {
                    // Códigos associados encontrados
                    return new ResponseEntity<>(codigosAssociados, HttpStatus.OK);
                } else {
                    // Nenhum código associado encontrado
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
