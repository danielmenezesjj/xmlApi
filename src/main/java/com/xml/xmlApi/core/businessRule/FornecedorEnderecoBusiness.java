package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FornecedorEnderecoBusiness {

    public EnderFornecedor convertMapToEmit(Map<String, Object> ide) {
        EnderFornecedor enderFornecedor = new EnderFornecedor();
        enderFornecedor.setXlgr((String) ide.get("xLgr"));
        enderFornecedor.setNro((String) ide.get("nro"));
        enderFornecedor.setXcpl((String) ide.get("xCpl"));
        enderFornecedor.setXbairro((String) ide.get("xBairro"));
        enderFornecedor.setCmun((String) ide.get("cMun"));
        enderFornecedor.setXmun((String) ide.get("xMun"));
        enderFornecedor.setUf((String) ide.get("UF"));
        enderFornecedor.setCep((String) ide.get("CEP"));
        enderFornecedor.setCpais((String) ide.get("cPais"));
        enderFornecedor.setXpais((String) ide.get("xPais"));
        enderFornecedor.setFone((String) ide.get("fone"));
        return enderFornecedor;
    }

}
