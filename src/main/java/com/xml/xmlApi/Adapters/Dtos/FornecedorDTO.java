package com.xml.xmlApi.Adapters.Dtos;

import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;

public record FornecedorDTO(String CNPJ, String xNome, String IE, String CRT, EnderFornecedor enderFornecedor) {
}
