package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CodigoFornecedorRepository extends JpaRepository<CodigoDoFornecedor, Integer> {


    @Query("SELECT e from cdfornecedor e where e.fornecedor.id = :cdEmpresa ")
    List<CodigoDoFornecedor> findByCdEmpresa(Integer cdEmpresa);


    Optional<CodigoDoFornecedor> findBycdfornecedor(String cdfornecedor);
}
