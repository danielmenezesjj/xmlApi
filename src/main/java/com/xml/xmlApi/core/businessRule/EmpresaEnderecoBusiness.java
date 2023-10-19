package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.core.domain.Empresa.EnderEmpresa;
import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmpresaEnderecoBusiness {

    public EnderEmpresa convertMapToEmit(Map<String, Object> ide) {
        EnderEmpresa enderEmpresa = new EnderEmpresa();
        enderEmpresa.setXlgr((String) ide.get("xLgr"));
        enderEmpresa.setNro((String) ide.get("nro"));
        enderEmpresa.setXcpl((String) ide.get("xCpl"));
        enderEmpresa.setXbairro((String) ide.get("xBairro"));
        enderEmpresa.setCmun((String) ide.get("cMun"));
        enderEmpresa.setXmun((String) ide.get("xMun"));
        enderEmpresa.setUf((String) ide.get("UF"));
        enderEmpresa.setCep((String) ide.get("CEP"));
        enderEmpresa.setCpais((String) ide.get("cPais"));
        enderEmpresa.setXpais((String) ide.get("xPais"));
        enderEmpresa.setFone((String) ide.get("fone"));
        return enderEmpresa;
    }

}
