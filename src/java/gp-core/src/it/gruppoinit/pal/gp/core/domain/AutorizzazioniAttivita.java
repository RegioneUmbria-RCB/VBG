package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

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
import org.hibernate.validator.Length;

@Entity
@Table(name = "AUTORIZZAZIONI_ATTIVITA")
public class AutorizzazioniAttivita implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5626522214955351433L;
    private PkId id;
    private Autorizzazioni autorizzazioni;
    private Attivita attivita;
    private String note;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "AUTORIZZAZIONI_ATTIVITA.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Length(max = 3000)
    @Column(name = "NOTE", length = 3000)
    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDAUTORIZZAZIONE", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazioni() {

	return this.autorizzazioni;
    }

    public void setAutorizzazioni(Autorizzazioni autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer autorizzazioniId;

    @Column(name = "FK_IDAUTORIZZAZIONE")
    @SuppressWarnings("unused")
    private Integer getAutorizzazioniId() {

	if (null != this.getAutorizzazioni()) {
	    if (null != this.getAutorizzazioni().getId()) {
		this.autorizzazioniId = getAutorizzazioni().getId().getCodice();
		return this.autorizzazioniId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAutorizzazioniId(Integer autorizzazioniId) {

	if (null != this.getAutorizzazioni()) {
	    if (null != this.getAutorizzazioni().getId()) {
		this.autorizzazioniId = getAutorizzazioni().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICEISTAT", referencedColumnName = "CODICEISTAT", nullable = false, insertable = false, updatable = false) })
    public Attivita getAttivita() {

	return this.attivita;
    }

    public void setAttivita(Attivita attivita) {

	this.attivita = attivita;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private String attivitaId;

    @Column(name = "FK_CODICEISTAT")
    @SuppressWarnings("unused")
    private String getAttivitaId() {

	if (null != this.getAttivita()) {
	    if (null != this.getAttivita().getId()) {
		this.attivitaId = getAttivita().getId().getCodiceistat();
		return this.attivitaId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAttivitaId(String attivitaId) {

	if (null != this.getAttivita()) {
	    if (null != this.getAttivita().getId()) {
		this.attivitaId = getAttivita().getId().getCodiceistat();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////
}
