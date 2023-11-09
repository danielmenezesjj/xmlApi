package com.xml.xmlApi.core.domain.Documento;


import com.xml.xmlApi.Adapters.Dtos.DocumentoDTO;
import com.xml.xmlApi.core.domain.Empresa.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "documento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Documento {

    @Id
    @GeneratedValue
    @Column(name = "documento_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    private String nrdocumento;
    private String fornecedor;
    @Column(name = "DtEmissao")
    private String dtemissao;
    private String usuario;
    @Column(name = "nome_da_operacao")
    private String nmoperacao;
    @Column(name = "chave_de_acesso")
    private String chaveacesso;


    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Documento(DocumentoDTO data){
        this.nrdocumento = data.nrdocumento();
        this.fornecedor = data.fornecedor();
        this.dtemissao = data.dtemissao();
        this.usuario = data.usuario();
        this.nmoperacao = data.nmoperacao();
        this.chaveacesso = data.chaveacesso();
        this.createdDate = createAtautomatico();

    }

    public Date createAtautomatico(){
        Date createdDate = new Date();
        return createdDate;
    }




}

