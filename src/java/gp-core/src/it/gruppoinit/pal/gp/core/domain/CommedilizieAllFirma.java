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

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "COMMEDILIZIE_ALL_FIRMA")
public class CommedilizieAllFirma implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5398573400645850084L;
    private PkId id;
    private Integer fkCommedilizieAllegatiId;
    private CommedilizieAppello commedilizieAppello;
    private String hashSha256;
    private Date dataFirma;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "COMMEDILIZIE_ALL_FIRMA.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_COMMEDILIZIE_APPELLO_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public CommedilizieAppello getCommedilizieAppello() {

	return this.commedilizieAppello;
    }

    public void setCommedilizieAppello(CommedilizieAppello commedilizieAppello) {

	this.commedilizieAppello = commedilizieAppello;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer commedilizieAppelloId;

    @Column(name = "FK_COMMEDILIZIE_APPELLO_ID")
    @SuppressWarnings("unused")
    private Integer getCommedilizieAppelloId() {

	if (null != this.getCommedilizieAppello()) {
	    if (null != this.getCommedilizieAppello().getId()) {
		this.commedilizieAppelloId = getCommedilizieAppello().getId().getCodice();
		return this.commedilizieAppelloId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setCommedilizieAppelloId(Integer commedilizieAppelloId) {

	if (null != this.getCommedilizieAppello()) {
	    if (null != this.getCommedilizieAppello().getId()) {
		this.commedilizieAppelloId = getCommedilizieAppello().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @Column(name = "FK_COMM_ALLEGATI_ID", nullable = false)
    public Integer getFkCommedilizieAllegatiId() {

	return fkCommedilizieAllegatiId;
    }

    public void setFkCommedilizieAllegatiId(Integer fkCommedilizieAllegatiId) {

	this.fkCommedilizieAllegatiId = fkCommedilizieAllegatiId;
    }

    @Column(name = "HASH_SHA_256", length = 64)
    public String getHashSha256() {

	return hashSha256;
    }

    public void setHashSha256(String hashSha256) {

	this.hashSha256 = hashSha256;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_FIRMA")
    public Date getDataFirma() {

	return dataFirma;
    }

    public void setDataFirma(Date dataFirma) {

	this.dataFirma = dataFirma;
    }
}
