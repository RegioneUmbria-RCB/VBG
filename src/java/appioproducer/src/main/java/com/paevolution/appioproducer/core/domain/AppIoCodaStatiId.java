package com.paevolution.appioproducer.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AppIoCodaStatiId implements Serializable {

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    private String idcomune;
    @Column(name = "FK_GUIDCODA", length = 40, unique = true)
    private String fkGuidcoda;
    @Column(name = "STATO", length = 50)
    private String stato;
    @Column(name = "DATA", length = 16)
    private Date data;
}
