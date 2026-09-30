package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "APP_IO_CODA_STATI")
public class AppIoCodaStati implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4819535299996571294L;
    private AppIoCodaStatiId id;
    private String statoAppIo;
    private String messaggio;
    private AppIoCoda appIoCoda;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "guid", column = @Column(name = "FK_GUIDCODA", nullable = false, length = 40)),
	    @AttributeOverride(name = "stato", column = @Column(name = "STATO", nullable = false, length = 50)),
	    @AttributeOverride(name = "data", column = @Column(name = "DATA", nullable = false)) })
    public AppIoCodaStatiId getId() {

	return id;
    }

    public void setId(AppIoCodaStatiId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_GUIDCODA", referencedColumnName = "GUID", nullable = false, insertable = false, updatable = false) })
    public AppIoCoda getAppIoCoda() {

	return appIoCoda;
    }

    public void setAppIoCoda(AppIoCoda appIoCoda) {

	this.appIoCoda = appIoCoda;
    }

    @Length(max = 50)
    @Column(name = "STATO_APP_IO")
    public String getStatoAppIo() {

	return statoAppIo;
    }

    public void setStatoAppIo(String statoAppIo) {

	this.statoAppIo = statoAppIo;
    }

    @Length(min = 80, max = 10000)
    @Column(name = "MESSAGGIO")
    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }
}
