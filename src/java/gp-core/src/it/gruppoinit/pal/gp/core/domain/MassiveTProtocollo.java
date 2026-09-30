package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MASSIVE_T_PROTOCOLLO")
public class MassiveTProtocollo implements Serializable {

    private static final long serialVersionUID = 4881103252548565380L;
    private PkId id;
    private MassiveTestata massiveTestata;
    private Comuni comuni;
    private Amministrazioni amministrazioni;
    private String classifica;
    private String tipodocumento;
    private Set<MassiveTProtMetadati> metadati = new HashSet<MassiveTProtMetadati>(0);

    public MassiveTProtocollo() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MASSIVE_T_PROTOCOLLO.ID") })
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
    @JoinColumn(name = "CODICECOMUNE")
    public Comuni getComuni() {

	return this.comuni;
    }

    public void setComuni(Comuni comuni) {

	this.comuni = comuni;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEAMMINISTRAZIONE", referencedColumnName = "CODICEAMMINISTRAZIONE", insertable = false, updatable = false) })
    public Amministrazioni getAmministrazioni() {

	return this.amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    private Integer amministrazioniId;

    @Column(name = "CODICEAMMINISTRAZIONE")
    @SuppressWarnings("unused")
    private Integer getAmministrazioniId() {

	if (null != this.getAmministrazioni() && null != this.getAmministrazioni().getId()) {
	    this.amministrazioniId = getAmministrazioni().getId().getCodice();
	    return this.amministrazioniId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAmministrazioniId(Integer amministrazioniId) {

	if (null != this.getAmministrazioni() && null != this.getAmministrazioni().getId()) {
	    this.amministrazioniId = getAmministrazioni().getId().getCodice();
	}
    }

    @Column(name = "CLASSIFICA", length = 50)
    public String getClassifica() {

	return classifica;
    }

    public void setClassifica(String classifica) {

	this.classifica = classifica;
    }

    @Column(name = "TIPODOCUMENTO", length = 50)
    public String getTipodocumento() {

	return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {

	this.tipodocumento = tipodocumento;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "massiveTProtocollo")
    public Set<MassiveTProtMetadati> getMetadati() {

	if (this.metadati == null) {
	    this.metadati = new HashSet<MassiveTProtMetadati>(0);
	}
	return this.metadati;
    }

    public void setMetadati(Set<MassiveTProtMetadati> metadati) {

	this.metadati = metadati;
    }
}
