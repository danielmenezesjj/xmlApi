package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Optional<Fornecedor> findBycnpj(String cnpj);
}
