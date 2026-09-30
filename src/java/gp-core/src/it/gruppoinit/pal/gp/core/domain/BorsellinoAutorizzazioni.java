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

@Entity
@Table(name = "BORSELLINO_AUTORIZZAZIONI")
public class BorsellinoAutorizzazioni implements java.io.Serializable {

    private static final long serialVersionUID = 6412525809805246437L;
    private BorsellinoAutorizzazioniId id;
    private Borsellino borsellino;
    private Autorizzazioni autorizzazione;

    public BorsellinoAutorizzazioni() {

	this.id = new BorsellinoAutorizzazioniId();
	this.borsellino = new Borsellino();
	this.autorizzazione = new Autorizzazioni();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkIdBorsellino", column = @Column(name = "FKID_BORSELLINO", nullable = false, length = 10)),
	    @AttributeOverride(name = "fkIdAutorizzazioni", column = @Column(name = "FKID_AUTORIZZAZIONI", nullable = false, length = 10)) })
    public BorsellinoAutorizzazioniId getId() {

	return this.id;
    }

    public void setId(BorsellinoAutorizzazioniId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_BORSELLINO", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Borsellino getBorsellino() {

	return borsellino;
    }

    public void setBorsellino(Borsellino borsellino) {

	this.borsellino = borsellino;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_AUTORIZZAZIONI", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(Autorizzazioni autorizzazione) {

	this.autorizzazione = autorizzazione;
    }
}
