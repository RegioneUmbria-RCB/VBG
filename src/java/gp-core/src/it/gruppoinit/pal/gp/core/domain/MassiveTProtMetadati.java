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
@Table(name = "MASSIVE_TPROT_METADATI")
public class MassiveTProtMetadati implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9087644283586337495L;
    private PkId id;
    private MassiveTProtocollo massiveTProtocollo;
    private MassiveTestata massiveTestata;
    private Comuni comune;
    private String chiave;
    private String valore;

    public MassiveTProtMetadati() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MASSIVE_TPROT_METADATI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 24)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_MASSIVETPROT_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveTProtocollo getMassiveTProtocollo() {

	return massiveTProtocollo;
    }

    public void setMassiveTProtocollo(MassiveTProtocollo massiveTProtocollo) {

	this.massiveTProtocollo = massiveTProtocollo;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveTProtocolloId;

    @Column(name = "FK_MASSIVETPROT_ID")
    private Integer getMassiveTProtocolloId() {

	if (null != this.getMassiveTProtocollo()) {
	    if (null != this.getMassiveTProtocollo().getId()) {
		this.massiveTProtocolloId = this.getMassiveTProtocollo().getId().getCodice();
		return this.massiveTProtocolloId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveTProtocolloId(Integer massiveTProtocolloId) {

	if (null != this.getMassiveTProtocollo()) {
	    if (null != this.getMassiveTProtocollo().getId()) {
		this.massiveTProtocolloId = this.getMassiveTProtocollo().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_TESTATA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveTestata getMassiveTestata() {

	return this.massiveTestata;
    }

    public void setMassiveTestata(MassiveTestata massiveTestata) {

	this.massiveTestata = massiveTestata;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveTestataId;

    @Column(name = "FKID_TESTATA")
    private Integer getMassiveTestataId() {

	if (null != this.getMassiveTestata()) {
	    if (null != this.getMassiveTestata().getId()) {
		this.massiveTestataId = getMassiveTestata().getId().getCodice();
		return this.massiveTestataId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveTestataId(Integer massiveTestataId) {

	if (null != this.getMassiveTestata()) {
	    if (null != this.getMassiveTestata().getId()) {
		this.massiveTestataId = getMassiveTestata().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE", nullable = true, insertable = true, updatable = true)
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    @Column(name = "CHIAVE", nullable = false, length = 60)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @Column(name = "VALORE", nullable = true, length = 500)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
