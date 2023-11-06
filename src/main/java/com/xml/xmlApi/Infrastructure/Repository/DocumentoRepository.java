package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Documento.Documento;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

   Optional<Documento> findBychaveacesso(String chaveacesso);
}
