package com.xml.xmlApi.core.domain.Produto;


import com.xml.xmlApi.Adapters.Dtos.ProdutoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "produto")
public class Produto {

    @GeneratedValue
    @Id
    @Column(name = "id_produto")
    private Integer id;
    private String cdproduto;
    private String descricao;
    private Float vlunitario;
    private String unidadeMedida;

    public Produto(ProdutoDTO data) {

        this.cdproduto = data.cdproduto();
        this.descricao = data.descricao();
        this.vlunitario = data.vlunitario();;
        this.unidadeMedida = data.unidadeMedida();

    }

    public void update(ProdutoDTO data) {

        if(data.cdproduto() != null){
            this.cdproduto = data.cdproduto();
        }
        if(data.descricao() != null){
            this.descricao = data.descricao();
        }
        if(data.vlunitario() != null){
            this.vlunitario = data.vlunitario();
        }
        if(data.unidadeMedida() != null){
            this.unidadeMedida = data.unidadeMedida();
        }

    }
}
