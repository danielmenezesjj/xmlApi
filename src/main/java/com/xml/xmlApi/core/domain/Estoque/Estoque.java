package com.xml.xmlApi.core.domain.Estoque;


import com.xml.xmlApi.core.domain.Empresa.Empresa;
import com.xml.xmlApi.core.domain.Lote.Lote;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @NotNull
    @JoinColumn(name = "empresa_id") // nome da coluna que armazena o ID da empresa no banco de dados
    private Empresa empresa;
    private String cdProduto;
    private String dtval;
    private String dtfab;
    private String nlote;
    private Float qlote;



}

