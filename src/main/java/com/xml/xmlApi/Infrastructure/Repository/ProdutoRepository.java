package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findBycdproduto(String cdproduto);
}
