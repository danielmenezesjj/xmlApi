package com.xml.xmlApi.Adapters.Dtos;

import com.xml.xmlApi.domain.Emitente.EnderEmit;

public record EmitenteDTO(String CNPJ, String xNome, String IE, String CRT,String versao ,EnderEmit enderEmit) {
}
