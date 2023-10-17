package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoFornecedorRepository extends JpaRepository<CodigoDoFornecedor, Integer> {


    Optional<CodigoDoFornecedor> findBycdfornecedor(String cnpj);
}
