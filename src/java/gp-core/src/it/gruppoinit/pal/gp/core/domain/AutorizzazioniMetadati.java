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
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;

@Entity
@Table(name = "AUTORIZZAZIONI_METADATI")
public class AutorizzazioniMetadati implements java.io.Serializable {

    private static final long serialVersionUID = 5622183623457634676L;
    private AutorizzazioniMetadatiId id;
    private Autorizzazioni autorizzazione;
    private String valore;

    public AutorizzazioniMetadati() {

	this.id = new AutorizzazioniMetadatiId();
	this.autorizzazione = new Autorizzazioni();
    }

    public AutorizzazioniMetadati(Integer idAutorizzazione, String chiave, String valore) {

	this.id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), idAutorizzazione, chiave);
	this.valore = valore;
	this.autorizzazione = new Autorizzazioni();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkIdAutorizzazione", column = @Column(name = "FKIDAUTORIZZAZIONE", nullable = false, precision = 6, scale = 0)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "CHIAVE", nullable = false, precision = 100, scale = 0)) })
    public AutorizzazioniMetadatiId getId() {

	return id;
    }

    public void setId(AutorizzazioniMetadatiId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKIDAUTORIZZAZIONE", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(Autorizzazioni autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    @NotNull
    @Column(name = "VALORE", nullable = false, length = 4000)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
