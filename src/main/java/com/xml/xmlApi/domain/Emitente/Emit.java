package com.xml.xmlApi.domain.Emitente;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "emit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emit {
    @Id
    @GeneratedValue
    @Column(name = "id_emitente")
    private Integer id;
    private String CNPJ;
    private String xNome;
    private String IE;
    private String CRT;

    @OneToOne(cascade = CascadeType.ALL)
    private EnderEmit enderEmit;



}
