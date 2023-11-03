package com.xml.xmlApi.core.domain.Lote;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lote")
public class Lote {

    @Id
    @GeneratedValue
    private Integer id;
    private String nLote;
    private Float  qLote;
    private String dFab;
    private String dVal;

}
