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


    @GetMapping
    public ResponseEntity getDocumento(Pageable pageable){
        var alldocumentos = documentoBusiness.getAll(pageable);
        return ResponseEntity.ok(alldocumentos);
    }


    @PostMapping
    public ResponseEntity postFornecedor (@RequestBody DocumentoDTO data) throws EntityAlreadyExistExceptionCdFornecedor, EntityAlreadyExistException {
            Documento documento = new Documento(data);
            documentoBusiness.postFornecedor(documento);
            return ResponseEntity.status(HttpStatus.CREATED).body(documento);
        }

    @PostMapping("/xml")
    public ResponseEntity<Map<String, Object>> postDocumentoXML(@RequestParam("file") MultipartFile file, @RequestParam("usuario") String usuario, @RequestParam("empresa_id") Integer empresa_id) throws EntityAlreadyExistException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Map<String, Object> xmlMap = xmlMapper.readValue(file.getInputStream(), Map.class);

            Map<String, Object> ide = (Map<String, Object>) xmlMap.get("NFe");
            Map<String, Object> chvAcesso = (Map<String, Object>) xmlMap.get("protNFe");
            Map<String, Object> fornecedor = (Map<String, Object>) xmlMap.get("NFe");

            if (ide != null && chvAcesso != null) {
                ide = (Map<String, Object>) ide.get("infNFe");
                ide = (Map<String, Object>) ide.get("ide");
                chvAcesso = (Map<String, Object>) chvAcesso.get("infProt");

                fornecedor = (Map<String, Object>) fornecedor.get("infNFe");
                fornecedor = (Map<String, Object>) fornecedor.get("emit");



                // Adicione a informação da tag <emit>
                // Crie um novo mapa para combinar as informações de ide, chvAcesso e emit
                Map<String, Object> selectedData = new HashMap<>();
                selectedData.put("fornecedor", fornecedor.get("xNome"));
                selectedData.put("nrdocumento", ide.get("nNF"));
                selectedData.put("nmoperacao", ide.get("natOp"));
                selectedData.put("dtemissao", ide.get("dhEmi"));
                selectedData.put("chaveacesso", chvAcesso.get("chNFe"));
                selectedData.put("usuario", usuario);
                selectedData.put("empresa_id", empresa_id);


                documentoBusiness.postDocumentoXML(selectedData);

                return new ResponseEntity<>(selectedData, HttpStatus.OK);
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
