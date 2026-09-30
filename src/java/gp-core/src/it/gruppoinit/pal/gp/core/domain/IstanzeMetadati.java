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
import javax.persistence.Transient;

import it.gruppoinit.pal.gp.core.features.istanze.metadati.IMetadatoIstanza;

@Entity
@Table(name = "ISTANZE_METADATI")
public class IstanzeMetadati implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9205980139230896314L;
    private IstanzeMetadatiId id;
    private String valore;
    private Istanze istanze;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codiceistanza", column = @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "chiave", nullable = false, precision = 60, scale = 0)) })
    public IstanzeMetadatiId getId() {

	return id;
    }

    public void setId(IstanzeMetadatiId id) {

	this.id = id;
    }

    @Column(name = "VALORE", nullable = false, length = 500)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEISTANZA", referencedColumnName = "CODICEISTANZA", nullable = false, insertable = false, updatable = false) })
    public Istanze getIstanze() {

	return this.istanze;
    }

    public void setIstanze(Istanze istanze) {

	this.istanze = istanze;
    }

    @Transient
    public static IstanzeMetadati fromMetadati(IMetadatoIstanza metadato) {

	if (metadato == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare IstanzeMetadati.fromMetadati senza passare il metadato di riferimento");
	}
	IstanzeMetadati retVal = new IstanzeMetadati();
	retVal.setId(new IstanzeMetadatiId(metadato.getIdComune(), metadato.getCodiceIstanza(), metadato.getChiave().toString()));
	retVal.setValore(metadato.getValore());
	return retVal;
    }
}
