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
@Table(name = "VW_ISTANZE_OPE_RUOLI")
public class VwIstanzeOpeRuoli implements Serializable {

    private static final long serialVersionUID = 3116452719410186837L;
    private VwIstanzeOpeRuoliId id;
    private Istanze istanza;
    private Responsabili responsabile;
    private Ruoli ruolo;

    public VwIstanzeOpeRuoli() {

	this.id = new VwIstanzeOpeRuoliId();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", length = 6)),
	    @AttributeOverride(name = "idruolo", column = @Column(name = "IDRUOLO", precision = 6, scale = 0)),
	    @AttributeOverride(name = "codiceresponsabile", column = @Column(name = "CODICERESPONSABILE", precision = 6, scale = 0)),
	    @AttributeOverride(name = "codiceistanza", column = @Column(name = "CODICEISTANZA", precision = 6, scale = 0)) })
    public VwIstanzeOpeRuoliId getId() {

	return id;
    }

    public void setId(VwIstanzeOpeRuoliId id) {

	this.id = id;
    }

    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEISTANZA", referencedColumnName = "CODICEISTANZA", insertable = false, updatable = false) })
    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "CODICERESPONSABILE", referencedColumnName = "CODICERESPONSABILE", insertable = false, updatable = false) })
    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	this.responsabile = responsabile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDRUOLO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Ruoli getRuolo() {

	return ruolo;
    }

    public void setRuolo(Ruoli ruolo) {

	this.ruolo = ruolo;
    }
}
