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

import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentoMetadato;

@Entity
@Table(name = "MOVIMENTI_METADATI")
public class MovimentiMetadati implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7068916054755886055L;
    private MovimentiMetadatiId id;
    private String valore;
    private Movimenti movimento;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codicemovimento", column = @Column(name = "CODICEMOVIMENTO", nullable = false, precision = 8, scale = 0)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "CHIAVE", nullable = false, precision = 60, scale = 0)) })
    public MovimentiMetadatiId getId() {

	return id;
    }

    public void setId(MovimentiMetadatiId id) {

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
    @JoinColumns({
	    @JoinColumn(name = "CODICEMOVIMENTO", referencedColumnName = "CODICEMOVIMENTO", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Movimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(Movimenti movimento) {

	this.movimento = movimento;
    }

    @Transient
    public static MovimentiMetadati fromMetadati(IMovimentoMetadato metadato) {

	if (metadato == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare MovimentiMetadati.fromMetadati senza passare il metadato di riferimento");
	}
	MovimentiMetadati retVal = new MovimentiMetadati();
	retVal.setId(new MovimentiMetadatiId(metadato.getIdComune(), metadato.getCodiceMovimento(), metadato.getChiave()));
	retVal.setValore(metadato.getValore());
	return retVal;
    }
}
