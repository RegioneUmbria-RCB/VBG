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
@Table(name = "APP_IO_CODA_STATI")
public class AppIoCodaStati implements Serializable {

    private static final long serialVersionUID = -4759497000950455432L;
    private AppIoCodaStatiId id;
    @Column(name = "STATO_APP_IO", length = 50)
    private String statoAppIo;
    @Lob
    @Column(name = "MESSAGGIO")
    private String messaggio;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkGuidcoda", column = @Column(name = "FK_GUIDCODA", nullable = false, length = 40)),
	    @AttributeOverride(name = "stato", column = @Column(name = "STATO", nullable = false, length = 50)),
	    @AttributeOverride(name = "data", column = @Column(name = "DATA", nullable = false, length = 16)) })
    public AppIoCodaStatiId getId() {

	return this.id;
    }

    public void setId(AppIoCodaStatiId id) {

	this.id = id;
    }
}
