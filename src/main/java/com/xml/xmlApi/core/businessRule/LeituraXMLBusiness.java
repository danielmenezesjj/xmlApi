package com.xml.xmlApi.core.businessRule;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Infrastructure.Repository.CodigoFornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoCodigoFornecedorRepository;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.ProdutoCodigoFornecedor.ProdutoCodigoFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Service
public class LeituraXMLBusiness {


    @Autowired
    private ProdutoCodigoFornecedorRepository produtoCodigoFornecedorRepository;

    public List<String> getCodigosAssociados(List<String> cdProdutoList) {
        List<ProdutoCodigoFornecedor> associacoes = produtoCodigoFornecedorRepository.findByCdProdutoIn(cdProdutoList);

        List<String> codigosAssociados = new ArrayList<>();

        for (ProdutoCodigoFornecedor associacao : associacoes) {
            codigosAssociados.add(associacao.getCdFornecedor());
        }

        return codigosAssociados;
    }

    public List<String> processarXML(MultipartFile file) throws IOException {
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
            } else if (detalhes instanceof Map) {
                Map<String, Object> prod = (Map<String, Object>) ((Map<String, Object>) detalhes).get("prod");
                String cProd = (String) prod.get("cProd");
                cProdList.add(cProd);
            }

            return cProdList;
        }

        return Collections.emptyList();
    }

}
