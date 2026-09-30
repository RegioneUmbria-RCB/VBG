package it.gruppoinit.pal.gp.core.domain;

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

import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiId;

@Entity
@Table(name = "ALBEROPROC_METADATI")
public class AlberoprocMetadati implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6697462382004391679L;
    private Alberoproc alberoproc;
    private AlberoprocMetadatiId id;
    private String valore;

    public AlberoprocMetadati(Integer codiceIntervento, String chiave, String valore) {

	this.id = new AlberoprocMetadatiId(ORMHelper.getIdcomune(), codiceIntervento, chiave);
	this.valore = valore;
	this.alberoproc = new Alberoproc();
    }

    public AlberoprocMetadati() {

	this.id = new AlberoprocMetadatiId();
	this.alberoproc = new Alberoproc();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkScid", column = @Column(name = "FK_SCID", nullable = false, precision = 100, scale = 0)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "CHIAVE", nullable = false, precision = 100, scale = 0)) })
    public AlberoprocMetadatiId getId() {

	return id;
    }

    public void setId(AlberoprocMetadatiId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_SCID", referencedColumnName = "SC_ID", nullable = false, insertable = false, updatable = false) })
    public Alberoproc getAlberoproc() {

	return this.alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    @Column(name = "VALORE", nullable = false, length = 4000)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
