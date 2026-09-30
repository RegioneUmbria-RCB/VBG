package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "DYN2_MASSIVE")
public class Dyn2Massive implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8744467056119520923L;
    private PkId id;
    private String descrizione;
    private Date dataInizio;
    private Date dataFine;
    private Software software;
    private Boolean flgEliminata;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "DYN2_MASSIVE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @Column(name = "DESCRIZIONE", length = 100)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INIZIO", length = 7)
    public Date getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_FINE", length = 7)
    public Date getDataFine() {

	return dataFine;
    }

    public void setDataFine(Date dataFine) {

	this.dataFine = dataFine;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE", nullable = false)
    public Software getSoftware() {

	return this.software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    @Column(name = "FLG_ELIMINATA", precision = 1, scale = 0)
    public Boolean getFlgEliminata() {

	return flgEliminata;
    }

    public void setFlgEliminata(Boolean flgEliminata) {

	this.flgEliminata = flgEliminata;
    }
}
