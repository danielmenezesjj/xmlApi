package com.xml.xmlApi.Infrastructure.Repository;

import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.ProdutoCodigoFornecedor.ProdutoCodigoFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoCodigoFornecedorRepository extends JpaRepository<ProdutoCodigoFornecedor, Integer> {
    @Query("SELECT pcf FROM ProdutoCodigoFornecedor pcf WHERE pcf.produto.cdproduto = :cdProduto")
    List<ProdutoCodigoFornecedor> findByCdProduto(String cdProduto);


    @Query("SELECT pcf FROM ProdutoCodigoFornecedor pcf WHERE pcf.codigoFornecedor.cdfornecedor = :codigoFornecedor")
    List<ProdutoCodigoFornecedor> findByCodigoFornecedorList(@Param("codigoFornecedor") String codigoFornecedor);

    @Query("SELECT pcf FROM ProdutoCodigoFornecedor pcf WHERE pcf.codigoFornecedor.cdfornecedor = :codigoFornecedor")
    Optional<ProdutoCodigoFornecedor> findByCodigoFornecedor(@Param("codigoFornecedor") String codigoFornecedor);
}
