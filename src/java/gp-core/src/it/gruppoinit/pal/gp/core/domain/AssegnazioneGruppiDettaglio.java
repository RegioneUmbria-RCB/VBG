package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

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
@Table(name = "ASSEGNAZIONE_GRUPPI_DETTAGLIO")
public class AssegnazioneGruppiDettaglio implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1753297003717451037L;
    private AssegnazioneGruppiDettaglioId id;
    private AssegnazioneGruppiTestata assegnazioneGruppiTestata;
    private Istanze istanze;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "idTestata", column = @Column(name = "IDTESTATA", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "codiceIstanza", column = @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)) })
    public AssegnazioneGruppiDettaglioId getId() {

	return id;
    }

    public void setId(AssegnazioneGruppiDettaglioId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDTESTATA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public AssegnazioneGruppiTestata getAssegnazioneGruppiTestata() {

	return assegnazioneGruppiTestata;
    }

    public void setAssegnazioneGruppiTestata(AssegnazioneGruppiTestata assegnazioneGruppiTestata) {

	this.assegnazioneGruppiTestata = assegnazioneGruppiTestata;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "CODICEISTANZA", referencedColumnName = "CODICEISTANZA", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Istanze getIstanze() {

	return istanze;
    }

    public void setIstanze(Istanze istanze) {

	this.istanze = istanze;
    }
}
