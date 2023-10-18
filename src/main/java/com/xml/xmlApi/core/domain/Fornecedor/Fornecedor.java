package com.xml.xmlApi.core.domain.Fornecedor;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {
    @Id
    @GeneratedValue
    @Column(name = "id_fornecedor")
    private Integer id;
    private String cnpj;
    private String xNome;
    private String IE;
    private String CRT;

    @OneToOne(cascade = CascadeType.ALL)
    private EnderFornecedor enderFornecedor;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CodigoDoFornecedor> codigos;

    public Fornecedor(FornecedorDTO data){
        this.cnpj = data.CNPJ();
        this.xNome = data.xNome();
        this.IE = data.IE();
        this.CRT = data.CRT();
        this.enderFornecedor = data.enderFornecedor();

    }



    public void update(FornecedorDTO data) {
        if(data.CNPJ() != null){
            this.cnpj = data.CNPJ();
        }
        if(data.xNome() != null){
            this.xNome = data.xNome();
        }
        if(data.IE() != null){
            this.IE = data.IE();
        }
        if(data.CRT() != null){
            this.CRT = data.CRT();
        }
        if(data.enderFornecedor() != null){
            this.enderFornecedor = data.enderFornecedor();
        }

    }
}
