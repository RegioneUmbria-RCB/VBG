package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "ASSEGNAZIONE_GRUPPI_TESTATA")
public class AssegnazioneGruppiTestata implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1400855093101392281L;
    private PkId id;
    private GruppiIstruttori gruppiIstruttori;
    private String ambito;
    private Date dataApertura;
    private Date dataChiusura;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ASSEGNAZIONE_GRUPPI_TESTATA.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "GRUPPO_ISTRUTTORI", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public GruppiIstruttori getGruppiIstruttori() {

	return gruppiIstruttori;
    }

    public void setGruppiIstruttori(GruppiIstruttori gruppiIstruttori) {

	this.gruppiIstruttori = gruppiIstruttori;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer gruppiIstruttoriId;

    @Column(name = "GRUPPO_ISTRUTTORI")
    @SuppressWarnings("unused")
    private Integer getGruppiIstruttoriId() {

	if (null != this.getGruppiIstruttori()) {
	    if (null != this.getGruppiIstruttori().getId()) {
		this.gruppiIstruttoriId = getGruppiIstruttori().getId().getCodice();
		return this.gruppiIstruttoriId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setGruppiIstruttoriId(Integer gruppiIstruttoriId) {

	if (null != this.getGruppiIstruttori()) {
	    if (null != this.getGruppiIstruttori().getId()) {
		this.gruppiIstruttoriId = getGruppiIstruttori().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @Length(max = 50)
    @Column(name = "AMBITO", length = 50)
    public String getAmbito() {

	return ambito;
    }

    public void setAmbito(String ambito) {

	this.ambito = ambito;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_APERTURA")
    public Date getDataApertura() {

	return dataApertura;
    }

    public void setDataApertura(Date dataApertura) {

	this.dataApertura = dataApertura;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_CHIUSURA")
    public Date getDataChiusura() {

	return dataChiusura;
    }

    public void setDataChiusura(Date dataChiusura) {

	this.dataChiusura = dataChiusura;
    }
}
