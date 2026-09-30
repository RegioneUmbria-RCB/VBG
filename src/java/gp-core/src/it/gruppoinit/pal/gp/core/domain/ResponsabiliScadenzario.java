package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "RESPONSABILI_SCADENZARIO")
public class ResponsabiliScadenzario implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7749292515769860995L;
    private PkId id;
    private Responsabili responsabile;
    private String ambito;
    private String chiave;
    private String valore;

    public ResponsabiliScadenzario() {

	this.id = new PkId();
	this.responsabile = new Responsabili();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "RESPONSABILI_SCADENZARIO.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "CODICERESPONSABILE", referencedColumnName = "CODICERESPONSABILE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	this.responsabile = responsabile;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer responsabileId;

    @Column(name = "CODICERESPONSABILE")
    @SuppressWarnings("unused")
    private Integer getResponsabileId() {

	if (null != this.getResponsabile()) {
	    if (null != this.getResponsabile().getId()) {
		this.responsabileId = getResponsabile().getId().getCodice();
		return this.responsabileId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setResponsabileId(Integer responsabileId) {

	if (null != this.getResponsabile()) {
	    if (null != this.getResponsabile().getId()) {
		this.responsabileId = getResponsabile().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @Column(name = "AMBITO", length = 20)
    public String getAmbito() {

	return ambito;
    }

    public void setAmbito(String ambito) {

	this.ambito = ambito;
    }

    @Column(name = "CHIAVE", length = 25)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @Column(name = "VALORE", length = 50)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
