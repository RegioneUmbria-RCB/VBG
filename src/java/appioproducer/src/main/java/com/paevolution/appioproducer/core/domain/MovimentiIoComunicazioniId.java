package com.paevolution.appioproducer.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MovimentiIoComunicazioniId implements Serializable {
    
    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    private String idcomune;
    @Column(name = "CODICECOMUNE", nullable = false, length = 5)
    private String codicecomune;
    @Column(name = "SOFTWARE", nullable = false, length = 2)
    private String software;
    @Column(name = "CODICEMOVIMENTO", precision = 8, scale = 0)
    private Integer codicemovimento;
    @Column(name = "CODICEANAGRAFE", precision = 6, scale = 0)
    private Integer codiceanagrafe;
}
