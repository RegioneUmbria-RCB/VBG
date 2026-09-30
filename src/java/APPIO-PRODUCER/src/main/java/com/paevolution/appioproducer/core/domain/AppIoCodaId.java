package com.paevolution.appioproducer.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AppIoCodaId implements Serializable {
    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    private String idcomune;
    @Column(name = "GUID", length = 40, unique = true)
    private String guid;
}
