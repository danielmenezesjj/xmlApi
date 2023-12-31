package com.xml.xmlApi.core.domain.ProdutoCodigoFornecedor;


import com.xml.xmlApi.Adapters.Dtos.ProdutoCodigoFornecedorDTO;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto_codigo_fornecedor")
public class ProdutoCodigoFornecedor {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "codigo_fornecedor_id")
    private CodigoDoFornecedor codigoFornecedor;
    private String cdProduto;

    private String cdFornecedor;


    public ProdutoCodigoFornecedor(ProdutoCodigoFornecedorDTO data){
        this.produto = new Produto();
        this.produto.setCdproduto(data.produtoId());
        this.codigoFornecedor = new CodigoDoFornecedor();
        this.codigoFornecedor.setCdfornecedor(data.codigoFornecedorId());
        this.cdProduto = data.produtoId();
        this.cdFornecedor = data.codigoFornecedorId();
    }

}
