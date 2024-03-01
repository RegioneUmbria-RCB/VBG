package com.paevolution.appioproducer.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "APP_IO_CODA")
public class AppIoCoda implements Serializable {

    private static final long serialVersionUID = 3067130201822919054L;
    private AppIoCodaId id;
    @Column(name = "IDENTIFICATIVO_SERVIZIO", length = 50, unique = true)
    private String identificativoServizio;
    @Column(name = "CODICEFISCALE", length = 16)
    private String codicefiscale;
    @Column(name = "OGGETTO", length = 120)
    private String oggetto;
    @Lob
    @Column(name = "MESSAGGIO")
    private String messaggio;
    @Column(name = "STATO", length = 50)
    private String stato;
    @Column(name = "STATO_DATA", length = 16)
    private Date statoData;
    @Lob
    @Column(name = "STATO_MESSAGGIO")
    private String statoMessaggio;
    @Column(name = "DATA_PREVISTA_ELABORAZIONE", length = 16)
    private Date dataPrevistaElaborazione;
    @Column(name = "ORDINE", precision = 10, scale = 0)
    private Integer ordine;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "guid", column = @Column(name = "GUID", nullable = false, length = 40)), })
    public AppIoCodaId getId() {

	return this.id;
    }

    public void setId(AppIoCodaId id) {

	this.id = id;
    }
}
