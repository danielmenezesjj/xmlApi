package com.xml.xmlApi.Adapters.Dtos;

import com.xml.xmlApi.core.domain.Empresa.Empresa;
import jakarta.validation.constraints.NotNull;

public record EstoqueDTO(String cdProduto, String dtfab, String dtval, String nlote, String qlote, @NotNull Empresa empresa_id) {
}
