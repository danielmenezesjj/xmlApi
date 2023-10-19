package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Empresa.Empresa;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Optional<Empresa> findBycnpj(String cnpj);

}
