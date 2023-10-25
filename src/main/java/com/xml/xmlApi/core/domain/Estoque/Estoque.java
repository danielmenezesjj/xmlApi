package com.xml.xmlApi.core.domain.Estoque;


import com.xml.xmlApi.core.domain.Empresa.Empresa;
import com.xml.xmlApi.core.domain.Lote.Lote;
import jakarta.persistence.*;
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

    // Relacionamento com a entidade Empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id") // nome da coluna que armazena o ID da empresa no banco de dados
    private Empresa empresa;

    private Float quantidade;
    // Relacionamento com a entidade Lote
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;


}

