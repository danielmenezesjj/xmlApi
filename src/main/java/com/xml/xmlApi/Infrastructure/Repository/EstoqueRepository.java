package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.Estoque.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    @Query("SELECT e FROM estoque e WHERE e.cdProduto = :cdProduto " +
            "AND e.nlote = :nlote " +
            "AND e.dtfab = :dfab " +
            "AND e.dtval = :dval ")
    Estoque findEstoqueByChaveComposta(
            @Param("cdProduto") String cdProduto,
            @Param("nlote") String nlote,
            @Param("dfab") String dFab,
            @Param("dval") String dVal
    );
}