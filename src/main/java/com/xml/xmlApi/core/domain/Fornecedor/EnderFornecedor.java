package com.xml.xmlApi.core.domain.Fornecedor;

import com.xml.xmlApi.Adapters.Dtos.EnderEmitDTO;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "enderfornecedor")
public class EnderFornecedor {

    @Id
    @GeneratedValue
    @Column(name = "id_enderfornecedor")
    private Integer id;

    @Column(name = "xLgr")
    private String xlgr;
    private String nro;
    private String xcpl;
    private String xbairro;
    private String cmun;
    private String xmun;
    private String uf;
    private String cep;
    private String cpais;
    private String xpais;
    private String fone;


    public EnderFornecedor(EnderEmitDTO data) {
        this.xlgr = data.xlgr();
        this.nro = data.nro();
        this.xcpl = data.xCpl();
        this.xbairro = data.xBairro();
        this.cmun = data.cMun();
        this.xmun = data.xMun();
        this.uf = data.UF();
        this.cep = data.CEP();
        this.cpais = data.cPais();
        this.xpais = data.xPais();
        this.fone = data.fone();
    }

}
