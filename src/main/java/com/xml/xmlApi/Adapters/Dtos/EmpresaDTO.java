package com.xml.xmlApi.Adapters.Dtos;

import com.xml.xmlApi.core.domain.Empresa.EnderEmpresa;
import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;

public record EmpresaDTO(String CNPJ, String xNome, String IE, String CRT, String cdEmpresa ,EnderEmpresa enderEmpresa) {
}
