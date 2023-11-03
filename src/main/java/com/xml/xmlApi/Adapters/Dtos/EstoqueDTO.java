package com.xml.xmlApi.Adapters.Dtos;

import jakarta.validation.constraints.NotNull;

public record EstoqueDTO(String cdProduto, String dtfab, String dtval, String nlote, String qlote, @NotNull Integer empresa_id) {
}
