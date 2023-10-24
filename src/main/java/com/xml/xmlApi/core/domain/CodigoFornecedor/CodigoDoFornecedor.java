package com.xml.xmlApi.core.domain.CodigoFornecedor;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.Produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cdfornecedor")
public class CodigoDoFornecedor {
        @Id
        @GeneratedValue
        @Column(name = "id_cdfornecedor")
        private Integer id;
        private String cdfornecedor;
        private String materialfornecedor;

        @ManyToMany(mappedBy = "codigosFornecedores", cascade = CascadeType.ALL)
        private Set<Produto> produtos = new HashSet<>();

        @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        @JsonManagedReference
        @JoinColumn(name = "fornecedor_id")
        private Fornecedor fornecedor;

        public CodigoDoFornecedor(CodigoFornecedorDTO data) {
                this.cdfornecedor = data.cdfornecedor();
                this.materialfornecedor = data.materialfornecedor();

        }

        public void update(CodigoFornecedorDTO data) {

                if(data.cdfornecedor() != null){
                        this.cdfornecedor = data.cdfornecedor();
                }
                if(data.materialfornecedor() != null){
                        this.materialfornecedor = data.materialfornecedor();
                }
        }
}
