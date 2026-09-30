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

import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoTestataHelper;

@Entity
@Table(name = "MOVIMENTI_ZIP_LOGICO_TESTATA")
public class MovimentiZipLogicoTestata implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2866751114604425915L;
    private MovimentiZipLogicoTestataId id;
    private Movimenti movimenti;
    private String guid;
    private String guidCollegato;
    private Integer codiceoggettoDocAll;

    public MovimentiZipLogicoTestata() {

	this.id = new MovimentiZipLogicoTestataId();
    }

    public MovimentiZipLogicoTestata(MovimentiZipLogicoTestataHelper testata) {

	this.id = new MovimentiZipLogicoTestataId(testata.getIdComune(), testata.getCodiceMovimento());
	this.guid = testata.getGuid();
	this.guidCollegato = testata.getGuidCollegato();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codicemovimento", column = @Column(name = "CODICEMOVIMENTO", nullable = false, precision = 8, scale = 0)) })
    public MovimentiZipLogicoTestataId getId() {

	return id;
    }

    public void setId(MovimentiZipLogicoTestataId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEMOVIMENTO", referencedColumnName = "CODICEMOVIMENTO", nullable = false, insertable = false, updatable = false) })
    public Movimenti getMovimenti() {

	return this.movimenti;
    }

    public void setMovimenti(Movimenti movimenti) {

	this.movimenti = movimenti;
    }

    @NotNull
    @Column(name = "GUID", length = 60)
    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    @Column(name = "GUID_COLLEGATO", length = 60)
    public String getGuidCollegato() {

	return guidCollegato;
    }

    public void setGuidCollegato(String guidCollegato) {

	this.guidCollegato = guidCollegato;
    }

    @Column(name = "CODICEOGGETTO_DOC_ALL", insertable = true, updatable = true)
    public Integer getCodiceoggettoDocAll() {

	return codiceoggettoDocAll;
    }

    public void setCodiceoggettoDocAll(Integer codiceoggettoDocAll) {

	this.codiceoggettoDocAll = codiceoggettoDocAll;
    }
}
