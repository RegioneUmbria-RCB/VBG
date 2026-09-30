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

@Entity
@Table(name = "DOMANDESTC_ALLEGATI")
public class DomandestcAllegati implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1554876182854467517L;
    private PkId id;
    private Oggetti oggetti;
    private Domandestc domandestc;

    public DomandestcAllegati() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "DOMANDESTC_ALLEGATI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEOGGETTO", referencedColumnName = "CODICEOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Oggetti getOggetti() {

	return this.oggetti;
    }

    public void setOggetti(Oggetti oggetti) {

	this.oggetti = oggetti;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer oggettiId;

    @Column(name = "CODICEOGGETTO")
    @SuppressWarnings("unused")
    private Integer getOggettiId() {

	if (null != this.getOggetti()) {
	    if (null != this.getOggetti().getId()) {
		this.oggettiId = getOggetti().getId().getCodice();
		return this.oggettiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setOggettiId(Integer oggettiId) {

	if (null != this.getOggetti()) {
	    if (null != this.getOggetti().getId()) {
		this.oggettiId = getOggetti().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_DOMANDASTC_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Domandestc getDomandestc() {

	return domandestc;
    }

    public void setDomandestc(Domandestc domandestc) {

	this.domandestc = domandestc;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer domandestcId;

    @Column(name = "FK_DOMANDASTC_ID")
    @SuppressWarnings("unused")
    private Integer getDomandestcId() {

	if (null != this.getDomandestc()) {
	    if (null != this.getDomandestc().getId()) {
		this.domandestcId = getDomandestc().getId().getCodice();
		return this.domandestcId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setDomandestcId(Integer domandestcId) {

	if (null != this.getDomandestc()) {
	    if (null != this.getDomandestc().getId()) {
		this.domandestcId = getDomandestc().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
