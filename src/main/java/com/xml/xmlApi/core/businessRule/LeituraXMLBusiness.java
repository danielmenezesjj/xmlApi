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
import com.xml.xmlApi.core.domain.Lote.Lote;
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
import java.util.stream.Collectors;

@Service
public class LeituraXMLBusiness {


    @Autowired
    private ProdutoCodigoFornecedorRepository produtoCodigoFornecedorRepository;





    public Map<String, List<Map<String, Object>>> getCodigosAssociadosENaoAssociados(List<Map<String, Object>> produtosList) {
        List<ProdutoCodigoFornecedor> associacoes = produtoCodigoFornecedorRepository.findByCdProdutoIn(produtosList.stream()
                .map(produtoInfo -> (String) produtoInfo.get("cProd"))
                .collect(Collectors.toList()));

        Map<String, List<Map<String, Object>>> resultado = new HashMap<>();
        List<Map<String, Object>> codigosAssociados = new ArrayList<>();
        List<Map<String, Object>> codigosNaoAssociados = new ArrayList<>();

        for (Map<String, Object> produtoInfo : produtosList) {
            String codigo = (String) produtoInfo.get("cProd");
            boolean encontrado = false;
            for (ProdutoCodigoFornecedor associacao : associacoes) {
                if (codigo.equals(associacao.getCdFornecedor())) {
                    // O produto está associado, então inclua o código do produto
                    produtoInfo.put("cdProduto", associacao.getProduto().getCdproduto());

                    codigosAssociados.add(produtoInfo);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                codigosNaoAssociados.add(produtoInfo);
            }
        }

        resultado.put("codigosAssociados", codigosAssociados);
        resultado.put("codigosNaoAssociados", codigosNaoAssociados);

        return resultado;
    }




    public List<Map<String, Object>> processarXML(MultipartFile file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Map<String, Object> xmlMap = xmlMapper.readValue(file.getInputStream(), Map.class);

        Map<String, Object> ide = (Map<String, Object>) xmlMap.get("NFe");
        if (ide != null) {
            ide = (Map<String, Object>) ide.get("infNFe");
            Object detalhes = ide.get("det");

            List<Map<String, Object>> produtosList = new ArrayList<>();

            if (detalhes instanceof List) {
                List<Map<String, Object>> detalhesList = (List<Map<String, Object>>) detalhes;
                for (Map<String, Object> detalhe : detalhesList) {
                    Map<String, Object> prod = (Map<String, Object>) detalhe.get("prod");
                    String cProd = (String) prod.get("cProd");

                    Map<String, Object> rastro = (Map<String, Object>) prod.get("rastro");
                    Lote rastroInfo = new Lote();
                    if (rastro != null) {
                        rastroInfo.setNLote((String) rastro.get("nLote"));
                        rastroInfo.setQLote((String) rastro.get("qLote"));
                        rastroInfo.setDFab((String) rastro.get("dFab"));
                        rastroInfo.setDVal((String) rastro.get("dVal"));
                    }

                    Map<String, Object> produtoInfo = new HashMap<>();
                    produtoInfo.put("cProd", cProd);
                    produtoInfo.put("rastroInfo", rastroInfo);

                    produtosList.add(produtoInfo);

                }
            } else if (detalhes instanceof Map) {
                Map<String, Object> prod = (Map<String, Object>) ((Map<String, Object>) detalhes).get("prod");
                String cProd = (String) prod.get("cProd");

                Map<String, Object> rastro = (Map<String, Object>) prod.get("rastro");
                Lote rastroInfo = new Lote();
                if (rastro != null) {
                    rastroInfo.setNLote((String) rastro.get("nLote"));
                    rastroInfo.setQLote((String) rastro.get("qLote"));
                    rastroInfo.setDFab((String) rastro.get("dFab"));
                    rastroInfo.setDVal((String) rastro.get("dVal"));
                }

                Map<String, Object> produtoInfo = new HashMap<>();
                produtoInfo.put("cProd", cProd);
                produtoInfo.put("rastroInfo", rastroInfo);

                produtosList.add(produtoInfo);
            }

            return produtosList;
        }

        return Collections.emptyList();
    }

}
