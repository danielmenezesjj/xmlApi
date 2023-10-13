package com.xml.xmlApi.domain.Emitente;

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
@Entity(name = "enderEmit")
public class EnderEmit {

    @Id
    @GeneratedValue
    @Column(name = "id_enderemit")
    private Integer id;
    private String xLgr;
    private String nro;
    private String xBairro;
    private String cMun;
    private String xMun;
    private String UF;
    private String CEP;
    private String cPais;
    private String xPais;
    private String fone;


}
