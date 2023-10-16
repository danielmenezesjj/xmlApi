package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderEmitRepository extends JpaRepository<EnderFornecedor, Integer> {
}
