package com.xml.xmlApi.core.domain.Empresa;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xml.xmlApi.Adapters.Dtos.EmpresaDTO;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Documento.Documento;
import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue
    @Column(name = "id_empresa")
    private Integer id;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Documento> documentos = new ArrayList<>();
    private String cdEmpresa;
    private String cnpj;
    private String xNome;
    private String IE;
    private String CRT;

    @OneToOne(cascade = CascadeType.ALL)
    private EnderEmpresa enderEmpresa;

    private String generateRandomCdEmpresa() {
        // Gera um número aleatório
        Random random = new Random();
        int randomNumber = random.nextInt(100); // Você pode ajustar o intervalo conforme necessário

        // Formata o número aleatório como "LBVT01", "LBVT02", etc.
        String randomCdEmpresa = "LBVT" + String.format("%02d", randomNumber);

        return randomCdEmpresa;
    }
    public Empresa(EmpresaDTO data){
        this.cnpj = data.CNPJ();
        this.xNome = data.xNome();
        this.IE = data.IE();
        this.CRT = data.CRT();
        this.enderEmpresa = data.enderEmpresa();
        this.cdEmpresa = generateRandomCdEmpresa();
    }



    public void update(EmpresaDTO data) {
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
        if(data.enderEmpresa() != null){
            this.enderEmpresa = data.enderEmpresa();
        }
        if(data.cdEmpresa() != null){
            this.cdEmpresa = data.cdEmpresa();
        }

    }
}
