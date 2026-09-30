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

@Entity
@Table(name = "AMMINISTRAZIONI_COLLEGATE")
public class AmministrazioniCollegate implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4489227567506314943L;
    private AmministrazioniCollegateId id;
    private Amministrazioni ammCollegata;
    private Comuni comune;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codiceamministrazione", column = @Column(name = "CODICEAMMINISTRAZIONE", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "codicecomune", column = @Column(name = "CODICECOMUNE", nullable = false, length = 5)) })
    public AmministrazioniCollegateId getId() {

	return id;
    }

    public void setId(AmministrazioniCollegateId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICESOTTOAMMINISTRAZIONE", referencedColumnName = "CODICEAMMINISTRAZIONE", nullable = false, insertable = false, updatable = false) })
    public Amministrazioni getAmmCollegata() {

	return this.ammCollegata;
    }

    public void setAmmCollegata(Amministrazioni ammCollegata) {

	this.ammCollegata = ammCollegata;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer ammCollegataId;

    @Column(name = "CODICESOTTOAMMINISTRAZIONE")
    @SuppressWarnings("unused")
    private Integer getAmmCollegataId() {

	if (null != this.getAmmCollegata() && null != this.getAmmCollegata().getId()) {
	    this.ammCollegataId = getAmmCollegata().getId().getCodice();
	    return this.ammCollegataId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAmmCollegataId(Integer ammCollegataId) {

	if (null != this.getAmmCollegata() && null != this.getAmmCollegata().getId()) {
	    this.ammCollegataId = getAmmCollegata().getId().getCodice();
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE", nullable = false, insertable = false, updatable = false)
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }
}
