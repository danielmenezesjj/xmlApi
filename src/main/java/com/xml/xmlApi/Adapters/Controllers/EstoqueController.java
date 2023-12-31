package com.xml.xmlApi.Adapters.Controllers;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.EstoqueDTO;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.ExceptionNotRastro.TraceDoesNotExist;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.businessRule.*;
import com.xml.xmlApi.core.domain.Empresa.Empresa;
import com.xml.xmlApi.core.domain.Estoque.Estoque;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.Lote.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/estoque")
public class EstoqueController {





    @Autowired
    private LeituraXMLBusiness leituraXMLBusiness;

    @Autowired
    private EstoqueBusiness estoqueBusiness;

    @Autowired
    private EmpresaBusiness empresaBusiness;



    @GetMapping
    public ResponseEntity getAllEstoque(Pageable pageable){
        var estoqueAll = estoqueBusiness.getAll(pageable);
        return ResponseEntity.ok(estoqueAll);
    }

    @GetMapping("/{cdEmpresa}")
    public ResponseEntity getAllEstoqueList(Pageable pageable, @PathVariable Integer cdEmpresa) throws EntityNotExistExceptionCdFornecedor, ChangeSetPersister.NotFoundException {
        var estoqueAll = estoqueBusiness.getEstoqueCdEmpresa(cdEmpresa);
        return ResponseEntity.ok(estoqueAll);
    }


    @PostMapping("/entrada/lote/xml")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> postLoteXML(
            @RequestParam("file") MultipartFile file,
            @RequestParam("empresa_id") Integer empresaId // Assume que empresa_id é uma string
    ) throws TraceDoesNotExist {
        try {
            List<Map<String, Object>> produtosList = leituraXMLBusiness.processarXML(file);

            if (!produtosList.isEmpty()) {
                Map<String, List<Map<String, Object>>> resultado = leituraXMLBusiness.getCodigosAssociadosENaoAssociados(produtosList);
                List<Map<String, Object>> codigosAssociados = resultado.get("codigosAssociados");

                if (!codigosAssociados.isEmpty()) {
                    // Busque a empresa com base no ID (empresaId)
                    Empresa empresa = empresaBusiness.getOneById(empresaId);

                    // Insira os códigos associados na tabela Estoque
                    for (Map<String, Object> codigoAssociado : codigosAssociados) {
                        Estoque estoque = new Estoque();
                        Lote rastroInfo = (Lote) codigoAssociado.get("rastroInfo");
                        // Mapeie os campos de código associado para Estoque aqui
                        estoque.setCdProduto((String) codigoAssociado.get("cdProduto"));
                        if (rastroInfo != null) {
                            estoque.setDtfab(rastroInfo.getDFab());
                            estoque.setDtval(rastroInfo.getDVal());
                            estoque.setNlote(rastroInfo.getNLote());
                            estoque.setQlote(rastroInfo.getQLote());
                        }

                        // Defina a empresa no objeto Estoque
                        estoque.setEmpresa(empresa);

                        // Insira o objeto Estoque na tabela Estoque
                        estoqueBusiness.postProduto(estoque);
                    }

                    // Retorna o resultado completo (incluindo ambas as listas) e as informações do rastro
                    return new ResponseEntity<>(resultado, HttpStatus.OK);
                } else {
                    // Nenhum código associado encontrado, mas ainda retorna ambas as listas
                    return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
                }
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (EntityAlreadyExistExceptionCdFornecedor | EntityNotExistException |
                 ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    }

