package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

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

/**
 * StarConfigOneriService domain class for STAR_CONFIGONERI table
 */
@Entity
@Table(name = "STAR_CONFIGONERI")
public class StarConfigOneri implements java.io.Serializable {

    private static final long serialVersionUID = -8850418675445623830L;
    private PkId id;
    private Boolean flagOnlineEntiTerzi = Boolean.TRUE; 
    private String infoPagamenti;
    private Comuni comune;

    public StarConfigOneri() {

	this.id = new PkId();
	this.comune = new Comuni();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "STAR_CONFIGONERI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "FLG_ONLINE_ENTITERZI")
    public Boolean getFlagOnlineEntiTerzi() {

	return this.flagOnlineEntiTerzi;
    }

    public void setFlagOnlineEntiTerzi(Boolean isActive) {

	this.flagOnlineEntiTerzi = isActive;
    }

    @Column(name = "INFO_PAGAMENTI_FO")
    public String getInfoPagamenti() {

	return this.infoPagamenti;
    }

    public void setInfoPagamenti(String testo) {

	this.infoPagamenti = testo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICE_COMUNE", referencedColumnName = "CODICECOMUNE", nullable = true, insertable = true, updatable = true)
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

}
