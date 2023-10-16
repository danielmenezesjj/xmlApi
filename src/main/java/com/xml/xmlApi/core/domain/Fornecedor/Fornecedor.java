package com.xml.xmlApi.core.domain.Fornecedor;


import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import jakarta.persistence.*;
import lombok.*;

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

    public Fornecedor(FornecedorDTO data){
        this.cnpj = data.CNPJ();
        this.xNome = data.xNome();
        this.IE = data.IE();
        this.CRT = data.CRT();
        this.enderFornecedor = data.enderFornecedor();
    }

}
