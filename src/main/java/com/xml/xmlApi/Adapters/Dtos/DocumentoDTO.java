package com.xml.xmlApi.Adapters.Dtos;

import java.util.Date;

public record DocumentoDTO(String fornecedor, String dtemissao, String usuario, String nmoperacao, String chaveacesso, Integer nrdocumento, Date createdDate) {
}
