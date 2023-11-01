package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Estoque.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
}
